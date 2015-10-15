package parser.tree;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class TreeNode {
    protected final String value;
    protected TreeNode parent = null;
    protected TreeNode[] child = new TreeNode[2];

    public TreeNode(String value) {
        this.value = value;
    }

    /**
     * Get the value of this node.
     *
     * @return the string value of this node.
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the left child node.
     *
     * @return the left child node.
     */
    public TreeNode getLeftNode() {
        return child[0];
    }

    /**
     * Set the left child node.
     *
     * @param node: The node that should be the left child.
     * @return this node.
     */
    public TreeNode setLeftNode(TreeNode node) {
        this.child[0] = node;
        node.setParent(this);
        return this;
    }

    /**
     * Get the right child node.
     *
     * @return the right child node.
     */
    public TreeNode getRightNode() {
        return child[1];
    }

    /**
     * Set the right child node.
     *
     * @param node: The node that should be the right child.
     * @return this node.
     */
    public TreeNode setRightNode(TreeNode node) {
        this.child[1] = node;
        node.setParent(this);
        return this;
    }

    /**
     * Check if the node is a leaf.
     *
     * @return check if both children are absent.
     */
    public boolean hasChildren() {
        return child[0] != null || child[1] != null;
    }

    /**
     * Check if this node has a parent.
     *
     * @return if parent is null.
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Returns the parent of this node.
     *
     * @return the parent.
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * Sets the parent.
     *
     * @param node: Node that should be the parent.
     * @return this node.
     */
    public TreeNode setParent(TreeNode node) {
        this.parent = node;
        return this;
    }

    /**
     * Returns both children.
     *
     * @return Array containing both children.
     */
    public TreeNode[] getChildren() {
        return child;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String s;
        if (getLeftNode() != null | getRightNode() != null) {
            s = value + "[";
            if (getLeftNode() != null) {
                s = s + getLeftNode().toString();
            }
            s = s + ",";
            if (getRightNode() != null) {
                s = s + getRightNode().toString();
            }
            s = s + "]";
        } else {
            s = value;
        }
        return s;
    }
}
