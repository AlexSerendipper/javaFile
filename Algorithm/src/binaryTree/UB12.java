package binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 513. 找树左下角的值：https://leetcode.cn/problems/find-bottom-left-tree-value/
 * 给定一个二叉树，在树的最后一行找到最左边的值。
 @author Alex
 @create 2023-08-06-8:54
 */
public class UB12 {


    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(5);
        rootNode.left = new TreeNode(4);
        rootNode.right = new TreeNode(6);
        rootNode.left.left = new TreeNode(1);
        rootNode.left.right = new TreeNode(2);
        rootNode.right.left = new TreeNode(7);
        rootNode.right.right = new TreeNode(8);
        List<List<Integer>> lists = ceilTraversal(rootNode, 0);
        System.out.println(lists.get(lists.size()-1).get(0));

/*        TreeNode rootNode = new TreeNode(0);
        List<List<Integer>> lists = ceilTraversal(rootNode, 0);
        System.out.println(lists.get(lists.size()-1).get(0));*/
    }


    // 存储层序遍历结果的二维数组
    public static List<List<Integer>> resList = new ArrayList<List<Integer>>();

    // 思路，就是使用层序遍历，然后最后一个数组的第一个元素就是答案
    public int findBottomLeftValue(TreeNode root) {
        List<List<Integer>> lists = ceilTraversal(root, 0);
        return lists.get(lists.size()-1).get(0);
    }

    // 层序遍历
    public static List<List<Integer>> ceilTraversal(TreeNode root,int deep){
        if(root==null){
            return null;
        }
        // 中
        deep++;
        // 当前层不存在，则创建。。否则直接添加
        if(resList.size()<deep){
            ArrayList<Integer> item = new ArrayList<>();
            resList.add(item);
        }

        resList.get(deep-1).add(root.val);


        // 前
        ceilTraversal(root.left,deep);
        // 后
        ceilTraversal(root.right,deep);

        return resList;
    }


}
