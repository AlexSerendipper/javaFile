package binaryTree;

import java.util.*;

/**
 * 二叉树的层序遍历：https://leetcode.cn/problems/binary-tree-level-order-traversal/
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 @author Alex
 @create 2023-07-13-8:49
 */
public class UB04 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(5);
        rootNode.left = new TreeNode(4);
        rootNode.right = new TreeNode(6);
        rootNode.left.left = new TreeNode(1);
        rootNode.left.right = new TreeNode(2);
        rootNode.right.left = new TreeNode(7);
        rootNode.right.right = new TreeNode(8);
        new UB04().levelOrder(rootNode);
    }

    // 二维数组存储层序遍历的结果，每一层的结果都是一个数组
    public List<List<Integer>> resList = new ArrayList<List<Integer>>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        checkFun01(root,0);
        // checkFun02(root);

        return resList;
    }

    // DFS--递归方式(前序遍历)
    public void checkFun01(TreeNode node, Integer deep) {
        if (node == null) return;

        deep++;  // 1.中
        // deep为当前层数，如果当前层存在数组了，就无需创建了
        if (resList.size() < deep) {
            // 当层级增加时，list的Item也增加，利用list的索引值进行层级界定
            List<Integer> item = new ArrayList<Integer>();
            resList.add(item);
        }
        // 为当前层的数组添加元素（索引是从0开始，所以这里deep-1）
        resList.get(deep - 1).add(node.val);


        checkFun01(node.left, deep);  // 2.左
        checkFun01(node.right, deep);  // 3.右
    }

    // BFS--迭代方式--借助队列
    public void checkFun02(TreeNode node) {
        if (node == null) return;
        Queue<TreeNode> que = new LinkedList<TreeNode>();
        que.offer(node);

        while (!que.isEmpty()) {
            List<Integer> itemList = new ArrayList<Integer>();
            int len = que.size();  // 这里的长度实际上就是当前层的长度

            while (len > 0) {  // 遍历当前层，添加当前层的下一层的所有元素 并 弹出当前层元素
                TreeNode tmpNode = que.poll();
                itemList.add(tmpNode.val);

                if (tmpNode.left != null) que.offer(tmpNode.left);
                if (tmpNode.right != null) que.offer(tmpNode.right);
                len--;
            }

            resList.add(itemList);
        }

    }



    // 借助队列
    public List<List<Integer>> levelOrder6(TreeNode root) {
        List<List<Integer>> resList = new ArrayList<List<Integer>>();
        if(root == null){
            return resList;
        }

        Deque que = new LinkedList<TreeNode>();
        que.offer(root);

        while(!que.isEmpty()){
            List<Integer> itemList = new ArrayList<Integer>();
            int len = que.size();  // 这里的长度实际上就是当前层的长度

            while(len>0){
                TreeNode tmpNode = (TreeNode) que.poll();
                itemList.add(tmpNode.val);

                if (tmpNode.left != null) que.offer(tmpNode.left);
                if (tmpNode.right != null) que.offer(tmpNode.right);
                len--;
            }

            resList.add(itemList);
        }
        return resList;
    }

}
