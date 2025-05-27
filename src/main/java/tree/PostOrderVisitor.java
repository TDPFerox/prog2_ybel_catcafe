package tree;

/**
 * Visitor implementation that traverses a binary tree post-order (left, right, node).
 *
 * @param <T> parametric type of the binary search tree elements
 */
public class PostOrderVisitor<T extends Comparable<T>> implements TreeVisitor<T> {

    @Override
    public String visit(Empty<T> node) {
        return "";
    }

    @Override
    public String visit(Node<T> node) {
        String leftResult = node.leftChild().accept(this);
        String rightResult = node.rightChild().accept(this);
        String nodeData = node.data().toString();
        
        // Build result string with appropriate spacing
        StringBuilder result = new StringBuilder();
        
        if (!leftResult.isEmpty()) {
            result.append(leftResult).append(", ");
        }
        
        if (!rightResult.isEmpty()) {
            result.append(rightResult);
            if (!result.toString().endsWith(", ")) {
                result.append(", ");
            }
        }
        
        result.append(nodeData);
        
        return result.toString();
    }
}