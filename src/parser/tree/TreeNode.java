package parser.tree;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class TreeNode {
    protected TreeNode parent = null;
    protected TreeNode[] child = new TreeNode[2];
    protected final String value;

    public TreeNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public TreeNode getLeftNode(){
        return child[0];
    }

    public TreeNode getRightNode(){
        return child[1];
    }

    public TreeNode setLeftNode(TreeNode node) {
        this.child[0] = node;
        node.setParent(this);
        return this;
    }

    public TreeNode setRightNode(TreeNode node) {
        this.child[1] = node;
        node.setParent(this);
        return this;
    }

    public TreeNode getParent(){
        return parent;
    }

    public TreeNode setParent(TreeNode node) {
        this.parent = node;
        return this;
    }
}
