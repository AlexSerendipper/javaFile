package binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * // 递归法
 * 144.二叉树的前序遍历（中左右）(opens new window)：https://leetcode.cn/problems/binary-tree-preorder-traversal/
 * 94.二叉树的中序遍历（左中右）：https://leetcode.cn/problems/binary-tree-inorder-traversal/
 * 145.二叉树的后序遍历（左右中）(opens new window)：https://leetcode.cn/problems/binary-tree-postorder-traversal/
 *
 @author Alex
 @create 2023-07-07-9:40
 */
public class UB01 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(5);
        rootNode.left = new TreeNode(4);
        rootNode.right = new TreeNode(6);
        rootNode.left.left = new TreeNode(1);
        rootNode.left.right = new TreeNode(2);
        rootNode.right.left = new TreeNode(7);
        rootNode.right.right = new TreeNode(8);

        List<Integer> list = new UB01().inorderTraversal(rootNode);
        for(Integer i : list){
            System.out.printf(i+" ");
        }
    }

    // 前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> arr = new ArrayList<>();
        preorder(root,arr);
        return arr;
    }

    // 确定迭代的参数和返回值
    public static void preorder(TreeNode root, ArrayList<Integer> arr){
        // 结束条件
        if (root == null) {
            return;
        }
        // 单次遍历
        arr.add(root.val);
        preorder(root.left,arr);
        preorder(root.right,arr);
    }

    // 后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> arr = new ArrayList<>();
        postorder(root,arr);
        return arr;
    }

    public static void postorder(TreeNode root, ArrayList<Integer> arr){
        if(root==null){
            return;
        }

        postorder(root.left,arr);
        postorder(root.right,arr);
        // 后序遍历实际上就是先进入递归，然后再添加元素
        arr.add(root.val);
    }

    // 中序遍历（左中右）
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> arr = new ArrayList<>();
        inorder(root,arr);
        return arr;
    }

    public static void inorder(TreeNode root, ArrayList<Integer> arr){
        if(root==null){
            return;
        }

        inorder(root.left,arr);
        // 中序遍历实际上就是先遍历左边的节点，遍历完后添加中间节点
        arr.add(root.val);
        inorder(root.right,arr);
    }
}
