package cp213;

/**
 * Implements a Popularity Tree. Extends BST.
 *
 * @author Sara Aljaafari
 * @author David Brown
 * @version 2023-11-20
 */
public class PopularityTree<T extends Comparable<T>> extends BST<T> {

    /**
     * Auxiliary method for valid. May force node rotation if the retrieval count of
     * the located node item is incremented.
     *
     * @param node The node to examine for key.
     * @param key  The item to search for. Count is updated to count in matching
     *             node item if key is found.
     * @return The updated node.
     */
    private TreeNode<T> retrieveAux(TreeNode<T> node, final CountedItem<T> key) {
    	if (node == null) {
            return null;
        }

        int compare = node.getdata().compareTo(key);
        this.comparisons++;

        if (compare == 0) {
            node.getdata().incrementCount();
            return node;
        } else if (compare > 0) {
            TreeNode<T> foundNode = this.retrieveAux(node.getLeft(), key);
            if (foundNode != null) {
                if (node.getLeft().getdata().getCount() < foundNode.getdata().getCount()) {
                    node.setLeft(foundNode);
                }
                if (node.getdata().getCount() < foundNode.getdata().getCount()) {
                    TreeNode<T> newRoot = this.rotateRight(node);
                    if (node.getdata().compareTo(this.root.getdata()) == 0) {
                        this.root = newRoot;
                    }
                }
            }

            return foundNode;

        } else {
            TreeNode<T> foundNode = this.retrieveAux(node.getRight(), key);

            if (foundNode != null) {
                if (node.getRight().getdata().getCount() < foundNode.getdata().getCount()) {
                    node.setRight(foundNode);
                }
                if (node.getdata().getCount() < foundNode.getdata().getCount()) {
                    TreeNode<T> newRoot = this.rotateLeft(node);
                    if (node.getdata().compareTo(this.root.getdata()) == 0) {
                        this.root = newRoot;
                    }
                }
            }

            return foundNode;
        }
    }

    /**
     * Performs a left rotation around node.
     *
     * @param parent The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateLeft(final TreeNode<T> parent) {
    	TreeNode<T> pivot = parent.getRight();
        parent.setRight(pivot.getLeft());
        pivot.setLeft(parent);
        return pivot;
    }

    /**
     * Performs a right rotation around {@code node}.
     *
     * @param parent The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateRight(final TreeNode<T> parent) {
    	TreeNode<T> pivot = parent.getLeft();
        parent.setLeft(pivot.getRight());
        pivot.setRight(parent);
        return pivot;
    }

    /**
     * Replaces BST insertAux - does not increment count on repeated insertion.
     * Counts are incremented only on retrieve.
     */
    @Override
    protected TreeNode<T> insertAux(TreeNode<T> node, final CountedItem<T> data) {
    	if (node == null) {
            // Base case - add a new node containing the data.
            node = new TreeNode<T>(data);
            this.size++;
        } else {
            // Compare the node data against the insert data.
            final int result = node.getdata().compareTo(data);

            if (result > 0) {
                // General case - check the left subtree.
                node.setLeft(this.insertAux(node.getLeft(), data));
            } else if (result < 0) {
                // General case - check the right subtree.
                node.setRight(this.insertAux(node.getRight(), data));
            } else {
                // Base case - data is already in the tree, increment its count
            }
        }
        node.updateHeight();
        return node;
    }

    /**
     * Auxiliary method for valid. Determines if a subtree based on node is a valid
     * subtree. An Popularity Tree must meet the BST validation conditions, and
     * additionally the counts of any node data must be greater than or equal to the
     * counts of its children.
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

        int leftCount = node.getLeft() != null ? node.getLeft().getdata().getCount() : 0;
        int rightCount = node.getRight() != null ? node.getRight().getdata().getCount() : 0;

        if (((minNode != null && node.getdata().compareTo(minNode.getdata()) <= 0)
                || (maxNode != null && node.getdata().compareTo(maxNode.getdata()) >= 0))
                || (node.getHeight() != Math.max(leftHeight, rightHeight) + 1)
                || (node.getdata().getCount() < leftCount)
                || (node.getdata().getCount() < rightCount)) {
            return false;
        }

        boolean isLeftValid = isValidAux(node.getLeft(), minNode, node);
        boolean isRightValid = isValidAux(node.getRight(), node, maxNode);

        return isLeftValid && isRightValid;
    }

    /**
     * Determines whether two PopularityTrees are identical.
     *
     * @param target The PopularityTree to compare this PopularityTree against.
     * @return true if this PopularityTree and target contain nodes that match in
     *         position, item, count, and height, false otherwise.
     */
    public boolean equals(final PopularityTree<T> target) {
	return super.equals(target);
    }

    /**
     * Very similar to the BST retrieve, but increments the data count here instead
     * of in the insertion.
     *
     * @param key The key to search for.
     */
    @Override
    public CountedItem<T> retrieve(CountedItem<T> key) {
    	TreeNode<T> rnode = retrieveAux(this.root, key);
        CountedItem<T> r = rnode != null ? rnode.getdata() : null;
        heights(this.root);
        return r;
    }

    private void heights(TreeNode<T> root) {
        if (root == null) {
            return;
        }

        heights(root.getLeft());
        heights(root.getRight());
        root.updateHeight();

        return;
    }

}
