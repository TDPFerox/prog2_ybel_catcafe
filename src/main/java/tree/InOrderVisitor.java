package tree;

/**
 * Visitor implementation that traverses a binary tree in-order (left, node, right).
 *
 * @param <T> parametric type of the binary search tree elements
 */
public class InOrderVisitor<T extends Comparable<T>> implements TreeVisitor<T> {

    @Override
    public String visit(Empty<T> node) {
        return "";
    }

    @Override
    public String visit(Node<T> node) {
        String leftResult = node.leftChild().accept(this);
        String nodeData = node.data().toString();
        String rightResult = node.rightChild().accept(this);
        
        // Build result string with appropriate spacing
        StringBuilder result = new StringBuilder();
        
        if (!leftResult.isEmpty()) {
            result.append(leftResult).append(", ");
        }
        
        result.append(nodeData);
        
        if (!rightResult.isEmpty()) {
            result.append(", ").append(rightResult);
        }
        
        return result.toString();
    }
}