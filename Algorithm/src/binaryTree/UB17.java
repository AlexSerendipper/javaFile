package binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 112. 路径总和：https://leetcode.cn/problems/path-sum/
 *
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
 * 如果存在，返回 true ；否则，返回 false 。
 @author Alex
 @create 2023-08-06-9:31
 */
public class UB17 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(1);
        rootNode.left = new TreeNode(2);
        rootNode.right = new TreeNode(3);
        rootNode.left.right = new TreeNode(5);
        new UB17().hasPathSum(rootNode,0);
    }
    // 这里就还是按照之前求二叉树的所有路径来求解就好，随想录中其他的方法以后在学吧
    public boolean hasPathSum(TreeNode root, int targetSum) {
        List<Integer> res = binaryTreePathsSums(root);
        if(res.contains(targetSum)){
            return true;
        }else {
            return false;
        }
    }

    public static List<Integer> binaryTreePathsSums(TreeNode root) {
        List<Integer> res = new ArrayList<>();  // 存最终的结果，每一个结果就是一条路径的和
        if (root == null) {
            return res;
        }

        List<Integer> paths = new ArrayList<>();  // 作为结果中的路径
        traversal(root, paths, res);
        return res;
    }

    // 前序遍历求所有路径
    private static void traversal(TreeNode root, List<Integer> paths, List<Integer> res) {
        // 中
        paths.add(root.val);
        // 中止条件：遇到叶子结点
        if (root.left == null && root.right == null) {
            int sum = 0;
            for (int i = 0; i < paths.size(); i++) {
                sum+=paths.get(i);
            }
            res.add(sum);
        }


        // 左
        if(root.left!=null){
            traversal(root.left,paths,res);
            paths.remove(paths.size()-1);
        }

        // 右
        if(root.right!=null){
            traversal(root.right,paths,res);
            paths.remove(paths.size()-1);
        }
    }
}
