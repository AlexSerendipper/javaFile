package binaryTree;

/**
 * 给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 *
 @author Alex
 @create 2023-07-21-9:01
 */
public class UB08 {
    // 后序遍历求高度，求左右节点的最小高度返回给上一层

    // 注意：在单层递归逻辑中，与求最大深度并不一样，
    // 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。，注意是叶子节点。
    // 什么是叶子节点，左右孩子都为空的节点才是叶子节点！

    // 所以，如果左子树为空，右子树不为空，说明最小深度是 1 + 右子树的深度。
    // 反之，右子树为空，左子树不为空，最小深度是 1 + 左子树的深度。 最后如果左右子树都不为空，返回左右子树深度最小值 + 1 。

    public int minDepth(TreeNode root) {
        if(root==null){
            return 0;
        }

        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        if(root.left == null && root.right!=null){
            return 1+rightDepth;
        }

        if(root.right == null && root.left!=null){
            return 1+leftDepth;
        }


        return 1+Math.min(leftDepth,rightDepth);
    }
}
