package binaryTree;

/**
 * 翻转二叉树
 @author Alex
 @create 2023-07-14-8:47
 */
public class UB05 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(5);
        rootNode.left = new TreeNode(4);
        rootNode.right = new TreeNode(6);
        rootNode.left.left = new TreeNode(1);
        rootNode.left.right = new TreeNode(2);
        rootNode.right.left = new TreeNode(7);
        rootNode.right.right = new TreeNode(8);
        new UB05().invertTree(rootNode);
    }

    /**
     * 前后序遍历都可以
     * 中序不行，因为先左孩子交换孩子，再根交换孩子（做完后，右孩子已经变成了原来的左孩子），再右孩子交换孩子（此时其实是对原来的左孩子做交换）
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        swapChildren(root);
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    private void swapChildren(TreeNode root) {
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }
}
