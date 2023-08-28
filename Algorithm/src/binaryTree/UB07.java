package binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 104. 二叉树的最大深度：https://leetcode.cn/problems/maximum-depth-of-binary-tree/
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 *
 * 559. N 叉树的最大深度：https://leetcode.cn/problems/maximum-depth-of-n-ary-tree/
 * 给定一个 N 叉树，找到其最大深度。
 * 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
 * N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。
 @author Alex
 @create 2023-07-18-9:01
 */
public class UB07 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(3);
        rootNode.left = new TreeNode(9);
        rootNode.right = new TreeNode(20);

        rootNode.right.left = new TreeNode(15);
        rootNode.right.right = new TreeNode(7);

        new UB07().maxDepth(rootNode);
    }
    /**
     * 递归法（前序遍历求深度）
     * ✔（后序遍历用于求高度）
     * 这里使用了后序遍历，传入根节点，因为根节点的高度就是二叉树的最大深度
     *
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;  // 因为是求高度，这里的意思就是将左孩子和右孩子的高度+1，返回给上一层父节点。
    }


    // 求N叉树的最大深度
    /*递归法，后序遍历求root节点的高度*/
    public int maxDepth(Node root) {
        if (root == null) return 0;

        int depth = 0;
        if (root.children != null){
            for (Node child : root.children){
                depth = Math.max(depth, maxDepth(child));
            }
        }

        return depth + 1; //中节点
    }
}
