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

    public String getValue() {
        return value;
    }

    public TreeNode getLeftNode(){
        return child[0];
    }

    public TreeNode setLeftNode(TreeNode node) {
        this.child[0] = node;
        node.setParent(this);
        return this;
    }

    public TreeNode getRightNode(){
        return child[1];
    }

    public TreeNode setRightNode(TreeNode node) {
        this.child[1] = node;
        node.setParent(this);
        return this;
    }

    public boolean hasChildren() {
        return child[0] != null || child[1] != null;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public TreeNode getParent(){
        return parent;
    }

    public TreeNode setParent(TreeNode node) {
        this.parent = node;
        return this;
    }

    public TreeNode[] getChildren(){
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
        if(getLeftNode() != null | getRightNode() != null){
            s = value + "[";
            if(getLeftNode() != null){
                s = s + getLeftNode().toString();
            }
            s = s + ",";
            if(getRightNode() != null){
                s = s + getRightNode().toString();
            }
            s = s + "]";
        } else {
            s=value;
        }
        return s;
    }
}
