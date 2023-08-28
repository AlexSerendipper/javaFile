package binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 257. 二叉树的所有路径：https://leetcode.cn/problems/binary-tree-paths/
 * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
 * 说明: 叶子节点是指没有子节点的节点。
 @author Alex
 @create 2023-07-23-9:47
 */
public class UB13 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(1);
        rootNode.left = new TreeNode(2);
        rootNode.right = new TreeNode(3);
        rootNode.left.right = new TreeNode(5);
        List<String> strings = new UB13().binaryTreePaths(rootNode);

    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();  // 存最终的结果
        if (root == null) {
            return res;
        }
        List<Integer> paths = new ArrayList<>();  // 作为结果中的路径
        traversal(root, paths, res);
        return res;
    }

    // 递归法实现（前序遍历）
    private void traversal(TreeNode root, List<Integer> paths, List<String> res) {
        paths.add(root.val);  // 前序遍历，中。。。为什么中写在这里，是因为如果写在中止条件后面，遇到叶子节点就返回，则叶子节点就不会添加到路径中！

        // 中止条件：遇到叶子结点
        if (root.left == null && root.right == null) {
            StringBuilder sb = new StringBuilder();  // StringBuilder用来拼接字符串，速度更快
            // 这里使用的是paths.size()-1，因为最后一个节点不需要->这个符号
            for (int i = 0; i < paths.size()-1; i++) {
                sb.append(paths.get(i)).append("->");
            }
            sb.append(paths.get(paths.size() - 1));  // 记录最后一个节点
            res.add(sb.toString());  // 收集一个路径
            return;
        }

        // 递归和回溯是同时进行，所以要放在同一个花括号里
        if (root.left != null) {  // 左
            traversal(root.left, paths, res);
            paths.remove(paths.size() - 1);  // 回溯
        }
        if (root.right != null) {  // 右
            traversal(root.right, paths, res);
            paths.remove(paths.size() - 1);  // 回溯
        }
    }
}
