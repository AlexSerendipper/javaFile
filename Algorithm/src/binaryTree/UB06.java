package binaryTree;

/**
 * 101. 对称二叉树：给你一个二叉树的根节点 root ， 检查它是否轴对称。
 *
 @author Alex
 @create 2023-07-17-15:07
 */
public class UB06 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(1);
        rootNode.left = new TreeNode(2);
        rootNode.right = new TreeNode(2);
        rootNode.left.left = new TreeNode(3);
        rootNode.left.right = new TreeNode(4);
        rootNode.right.left = new TreeNode(4);
        rootNode.right.right = new TreeNode(3);
        new UB06().isSymmetric(rootNode);
    }


    /**
     * 递归法
     */
    public boolean isSymmetric(TreeNode root) {
        return compare(root.left, root.right);
    }

    private boolean compare(TreeNode left, TreeNode right) {

        if (left == null && right != null) {
            return false;
        }
        if (left != null && right == null) {
            return false;
        }

        if (left == null && right == null) {
            return true;
        }
        if (left.val != right.val) {
            return false;
        }
        // 比较外侧
        boolean compareOutside = compare(left.left, right.right);
        // 比较内侧
        boolean compareInside = compare(left.right, right.left);
        return compareOutside && compareInside;
    }
}
