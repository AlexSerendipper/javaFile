package binaryTree;

import java.util.*;

/**
 * // 迭代法
 * 144.二叉树的前序遍历（中左右）(opens new window)：https://leetcode.cn/problems/binary-tree-preorder-traversal/
 * 145.二叉树的后序遍历（左中右）(opens new window)：https://leetcode.cn/problems/binary-tree-postorder-traversal/
 * 94.二叉树的中序遍历（左右中）：https://leetcode.cn/problems/binary-tree-inorder-traversal/
 @author Alex
 @create 2023-07-07-10:39
 */
public class UB02 {
    // 前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> arr = new ArrayList<>();
        if(root == null){
            return arr;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            arr.add(node.val);
            // 这里迭代时，先添加右元素，再添加左元素
            if(node.right!=null){
                stack.push(node.right);
            }
            if(node.left!=null){
                stack.push(node.left);
            }
        }
        return arr;
    }



    // 后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> arr = new ArrayList<>();
        if(root == null){
            return arr;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            arr.add(node.val);
            // 这里迭代时，先添加左元素，再添加右元素，最后将结果反转即可
            if(node.left!=null){
                stack.push(node.left);
            }
            if(node.right!=null){
                stack.push(node.right);
            }
        }
        Collections.reverse(arr);
        return arr;
    }



    // 中序遍历：中序迭代和之前差异较大，需要借助指针
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> arr = new ArrayList<>();
        if(root == null){
            return arr;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur!=null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                arr.add(cur.val);
                cur = cur.right;
            }
        }
        return arr;
    }



}
