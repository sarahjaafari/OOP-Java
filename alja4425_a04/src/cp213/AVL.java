package cp213;

/**
 * Implements an AVL (Adelson-Velsky Landis) tree. Extends BST.
 *
 * @author Sara Aljaafari
 * @author David Brown
 * @version 2023-11-20
 */
public class AVL<T extends Comparable<T>> extends BST<T> {

    /**
     * Returns the balance item of node. If greater than 1, then left heavy, if less
     * than -1, then right heavy. If in the range -1 to 1 inclusive, the node is
     * balanced. Used to determine whether to rotate a node upon insertion.
     *
     * @param node The TreeNode to analyze for balance.
     * @return A balance number.
     */
    private int balance(final TreeNode<T> node) {
    	if (node == null) {
            return 0;
        }
        int leftHeight;
        int rightHeight;
        if (node.getLeft() == null) {
            leftHeight = 1;
        } else {
            leftHeight = node.getLeft().getHeight() + 1;
        }

        if (node.getRight() == null) {
            rightHeight = 1;
        } else {
            rightHeight = node.getRight().getHeight() + 1;
        }

        return leftHeight - rightHeight;
    }

    /**
     * Rebalances the current node if its children are not balanced.
     *
     * @param node the node to rebalance
     * @return replacement for the rebalanced node
     */
    private TreeNode<T> rebalance(TreeNode<T> node) {
    	
    	int balance = balance(node);

        // Left Heavy
        if (balance > 1) {
            if (balance(node.getLeft()) >= 0) {
                // Left-Left Case
                return rotateRight(node);
            } else {
                // Left-Right Case
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
        }
        // Right Heavy
        else if (balance < -1) {
            if (balance(node.getRight()) <= 0) {
                // Right-Right Case
                return rotateLeft(node);
            } else {
                // Right-Left Case
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
        }
        // Balanced
        return node;
    }

    /**
     * Performs a left rotation around node.
     *
     * @param node The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateLeft(final TreeNode<T> node) {
    	
    	TreeNode<T> pivot = node.getRight();
        node.setRight(pivot.getLeft());
        pivot.setLeft(node);
        pivot.updateHeight();
        node.updateHeight();
        return pivot;
    }

    /**
     * Performs a right rotation around node.
     *
     * @param node The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateRight(final TreeNode<T> node) {
    	TreeNode<T> pivot = node.getLeft();
        node.setLeft(pivot.getRight());
        pivot.setRight(node);
        pivot.updateHeight();
        node.updateHeight();

        return pivot;
    }

    /**
     * Auxiliary method for insert. Inserts data into this AVL.
     *
     * @param node The current node (TreeNode).
     * @param data Data to be inserted into the node.
     * @return The inserted node.
     */
    @Override
    protected TreeNode<T> insertAux(TreeNode<T> node, final CountedItem<T> data) {
    	if (node == null) {
            // Base case - add a new node containing the data.
            node = new TreeNode<T>(data);
            node.getdata().incrementCount();
            this.size++;
        } else {
            // Compare the node data against the insert data.
            final int result = node.getdata().compareTo(data);

            if (result > 0) {
                // General case - check the left subtree.
                node.setLeft(insertAux(node.getLeft(), data));
            } else if (result < 0) {
                // General case - check the right subtree.
                node.setRight(insertAux(node.getRight(), data));
            } else {
                // Base case - data is already in the tree, increment its count
                node.getdata().incrementCount();
            }

            // Rebalance the node after insertion
            node = rebalance(node);
        }

        node.updateHeight();
        return node;
    }

    /**
     * Auxiliary method for valid. Determines if a subtree based on node is a valid
     * subtree. An AVL must meet the BST validation conditions, and additionally be
     * balanced in all its subtrees - i.e. the difference in height between any two
     * children must be no greater than 1.
     *
     * @param node The root of the subtree to test for validity.
     * @return true if the subtree base on node is valid, false otherwise.
     */
    @Override
    protected boolean isValidAux(final TreeNode<T> node, TreeNode<T> minNode, TreeNode<T> maxNode) {
    	if (node == null) {
            return true;
        }

        int leftHeight = node.getLeft() != null ? node.getLeft().getHeight() : 0;
        int rightHeight = node.getRight() != null ? node.getRight().getHeight() : 0;

        if (((minNode != null && node.getdata().compareTo(minNode.getdata()) <= 0)
                || (maxNode != null && node.getdata().compareTo(maxNode.getdata()) >= 0))
                || (node.getHeight() != Math.max(leftHeight, rightHeight) + 1)
                || ((this.balance(node) > 1 || this.balance(node) < -1))) {
            return false;
        }

        boolean isLeftValid = isValidAux(node.getLeft(), minNode, node);
        boolean isRightValid = isValidAux(node.getRight(), node, maxNode);

        return isLeftValid && isRightValid;
    }

    /**
     * Determines whether two AVLs are identical.
     *
     * @param target The AVL to compare this AVL against.
     * @return true if this AVL and target contain nodes that match in position,
     *         item, count, and height, false otherwise.
     */
    public boolean equals(final AVL<T> target) {
    	return super.equals(target);
    }

}
