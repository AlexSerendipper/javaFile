package binaryTree;

import java.util.ArrayList;
import java.util.List;


/**
 * 222. 完全二叉树的节点个数：给你一棵完全二叉树的根节点root ，求出该树的节点个数。
 *
 * 完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，
 * 其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。
 * 若最底层为第 h 层，则该层包含 1~ 2h 个节点。
 @author Alex
 @create 2023-07-22-11:40
 */
public class UB09 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(5);
        rootNode.left = new TreeNode(4);
        rootNode.right = new TreeNode(6);
        rootNode.left.left = new TreeNode(1);
        rootNode.left.right = new TreeNode(2);
        rootNode.right.left = new TreeNode(7);
        rootNode.right.right = new TreeNode(8);
        System.out.println("new UB09().postorder(rootNode) = " + new UB09().postorder(rootNode));
    }

    // 实际上，普通二叉树使用前中后序遍历都可以求出完全二叉树节点的个数
    // 这里以后序遍历为例 来求普通二叉树的节点数量
    private int postorder(TreeNode root) {
        if(root==null){
            return 0;
        }
        int leftNodeNum = postorder(root.left);
        int rightNodeNum = postorder(root.right);
        // 节点的数量为左子树节点的数量加右子树节点的数量加上根节点
        return leftNodeNum + rightNodeNum + 1;
    }


    // 本题的意义在于如何利用完全二叉树的特性，来计算这颗二叉树节点的数量
    // 若一个二叉树是满二叉树，他的节点数量为： 2的深度次方-1

    // 完全二叉树只有两种情况，情况一：就是满二叉树，情况二：最后一层叶子节点没有满。
    // 对于情况一，可以直接用 2^树深度 - 1 来计算，注意这里根节点深度为1。
    // 对于情况二，分别递归左孩子，和右孩子，递归到某一深度一定会有左孩子或者右孩子为满二叉树，然后依然可以按照情况1来计算。
    private int totalPostOrder(TreeNode root){
        if (root == null) {
            return 0;
        }

        TreeNode left = root.left;
        TreeNode right = root.right;
        int leftDepth = 0, rightDepth = 0;  // 这里初始深度为0是有目的的，是为了下面求指数方便
        while (left != null) {  // 求左子树深度
            left = left.left;
            leftDepth++;
        }
        while (right != null) { // 求右子树深度
            right = right.right;
            rightDepth++;
        }
        if (leftDepth == rightDepth) {
            return (2 << leftDepth) - 1;  // 注意(2<<1) 相当于2^2，所以leftDepth初始为0
        }
        return totalPostOrder(root.left) + totalPostOrder(root.right) + 1;
    }



}
