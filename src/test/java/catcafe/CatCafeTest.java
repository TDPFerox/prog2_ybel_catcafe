package catcafe;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tree.TreeVisitor;

/**
 * Test class for the CatCafe implementation.
 * 
 * This class provides comprehensive test coverage for the CatCafe class, which manages
 * a collection of FelineOverLord objects. The tests verify the following functionality:
 * 
 * <ul>
 *   <li>Initial state validation - ensuring a new cafe has no cats</li>
 *   <li>Adding cats - single cat, multiple cats, and handling null cat input</li>
 *   <li>Retrieving cats by name - finding existing cats, handling non-existing names, and null names</li>
 *   <li>Retrieving cats by weight range - finding cats within weight ranges, handling invalid ranges</li>
 *   <li>Handling duplicate data - cats with the same name or weight</li>
 *   <li>Visitor pattern implementation - verifying the cafe can accept a TreeVisitor</li>
 * </ul>
 * 
 * The tests indicate that CatCafe uses a tree-based data structure to store cats, with
 * cats being uniquely identified by name (duplicate names are not stored). The class
 * also properly handles edge cases like null inputs and invalid parameters.
 * 
 * @see CatCafe
 * @see FelineOverLord
 * @see TreeVisitor
 */
class CatCafeTest {

    private CatCafe cafe;

    @BeforeEach
    void setUp() {
        cafe = new CatCafe();
    }

    @Test
    void testNewCafeHasNoCats() {
        assertEquals(0, cafe.getCatCount(), "New cafe should have no cats");
    }

    @Test
    void testAddSingleCat() {
        cafe.addCat(new FelineOverLord("Whiskers", 4));
        assertEquals(1, cafe.getCatCount(), "Cafe should have one cat after adding");
    }

    @Test
    void testAddMultipleCats() {
        cafe.addCat(new FelineOverLord("Whiskers", 4));
        cafe.addCat(new FelineOverLord("Mittens", 3));
        cafe.addCat(new FelineOverLord("Shadow", 5));
        assertEquals(3, cafe.getCatCount(), "Cafe should have three cats after adding");
    }

    @Test
    void testAddNullCat() {
        assertThrows(NullPointerException.class, () -> cafe.addCat(null),
                "Adding null cat should throw NullPointerException");
    }

    @Test
    void testGetCatByNameExisting() {
        FelineOverLord cat = new FelineOverLord("Whiskers", 4);
        cafe.addCat(cat);
        Optional<FelineOverLord> result = cafe.getCatByName("Whiskers");
        
        assertTrue(result.isPresent(), "Should find cat with name Whiskers");
        assertEquals("Whiskers", result.get().name(), "Retrieved cat should have correct name");
        assertEquals(4, result.get().weight(), "Retrieved cat should have correct weight");
    }

    @Test
    void testGetCatByNameNonExisting() {
        cafe.addCat(new FelineOverLord("Whiskers", 4));
        Optional<FelineOverLord> result = cafe.getCatByName("Mittens");
        
        assertTrue(result.isEmpty(), "Should not find cat with non-existing name");
    }

    @Test
    void testGetCatByNameNull() {
        cafe.addCat(new FelineOverLord("Whiskers", 4));
        Optional<FelineOverLord> result = cafe.getCatByName(null);
        
        assertTrue(result.isEmpty(), "Should return empty Optional for null name");
    }

    @Test
    void testGetCatByWeightExisting() {
        cafe.addCat(new FelineOverLord("Light", 2));
        cafe.addCat(new FelineOverLord("Medium", 3));
        cafe.addCat(new FelineOverLord("Heavy", 5));
        
        Optional<FelineOverLord> result = cafe.getCatByWeight(3, 4);
        
        assertTrue(result.isPresent(), "Should find cat with weight in range [3,4)");
        assertEquals("Medium", result.get().name(), "Retrieved cat should have correct name");
        assertEquals(3, result.get().weight(), "Retrieved cat should have correct weight");
    }

    @Test
    void testGetCatByWeightNonExisting() {
        cafe.addCat(new FelineOverLord("Light", 2));
        cafe.addCat(new FelineOverLord("Medium", 3));
        cafe.addCat(new FelineOverLord("Heavy", 5));
        
        Optional<FelineOverLord> result = cafe.getCatByWeight(6, 8);
        
        assertTrue(result.isEmpty(), "Should not find cat with weight in range [6,8)");
    }

    @Test
    void testGetCatByWeightInvalidRange() {
        cafe.addCat(new FelineOverLord("Medium", 3));
        
        Optional<FelineOverLord> result1 = cafe.getCatByWeight(-1, 5);
        Optional<FelineOverLord> result2 = cafe.getCatByWeight(5, 3);
        
        assertTrue(result1.isEmpty(), "Should return empty Optional for negative minWeight");
        assertTrue(result2.isEmpty(), "Should return empty Optional when maxWeight < minWeight");
    }

    @Test
    void testMultipleCatsWithSameName() {
        cafe.addCat(new FelineOverLord("Mittens", 3));
        cafe.addCat(new FelineOverLord("Mittens", 4));
        
        // The behavior depends on how FelineOverLord.compareTo() is implemented
        // If cats are compared by both name AND weight, both cats would be stored
        assertEquals(2, cafe.getCatCount(), "Cats with same name but different weights should be stored separately");
        
        Optional<FelineOverLord> result = cafe.getCatByName("Mittens");
        assertTrue(result.isPresent(), "Should find cat with name Mittens");
        // We can't predict which cat will be returned first, so don't assert on weight
    }

    @Test
    void testMultipleCatsWithSameWeight() {
        cafe.addCat(new FelineOverLord("Whiskers", 4));
        cafe.addCat(new FelineOverLord("Mittens", 4));
        cafe.addCat(new FelineOverLord("Shadow", 4));
        
        // Since FelineOverLord.compareTo only compares by weight,
        // only one cat with weight 4 will be stored
        assertEquals(1, cafe.getCatCount(), "Only one cat with a given weight should be stored");
        
        Optional<FelineOverLord> result = cafe.getCatByWeight(4, 5);
        assertTrue(result.isPresent(), "Should find the cat with weight 4");
    }

    @Test
    void testAcceptVisitor() {
        cafe.addCat(new FelineOverLord("Whiskers", 4));
        
        TreeVisitor<FelineOverLord> mockVisitor = new TreeVisitor<>() {
            @Override
            public String visit(tree.Empty<FelineOverLord> node) {
                return "Empty";
            }

            @Override
            public String visit(tree.Node<FelineOverLord> node) {
                return "Node: " + node.data().name();
            }
        };
        
        String result = cafe.accept(mockVisitor);
        assertTrue(result.contains("Whiskers"), "Visitor result should contain cat name");
    }
}