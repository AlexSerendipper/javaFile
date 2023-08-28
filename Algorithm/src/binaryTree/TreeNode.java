package binaryTree;

/**
 * 【二叉树的概念】
 * 满二叉树：如果一棵二叉树只有度为0的结点和度为2的结点，并且度为0的结点在同一层上，则这棵二叉树为满二叉树。
 * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。
 * 二叉搜索树：前面介绍的树，都没有数值的，而二叉搜索树是有数值的了，二叉搜索树是一个有序树。
 *            若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 *            若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 *            它的左、右子树也分别为二叉排序树
 * 平衡二叉搜索树：又称为AVL树，且具有以下性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 *
 * 【二叉树的存储方式】
 * 二叉树可以链式存储，也可以顺序存储。那么链式存储方式就用指针， 顺序存储的方式就是用数组。
 * 顾名思义就是顺序存储的元素在内存是连续分布的，而链式存储则是通过指针把分布在各个地址的节点串联一起。
 * 用数组来存储二叉树如何遍历的呢？
 *
 * 如果父节点的数组下标是 i，那么它的左孩子就是 i * 2 + 1，右孩子就是 i * 2 + 2。
 * 但是用链式表示的二叉树，更有利于我们理解，所以一般我们都是用链式存储二叉树。
 * 所以大家要了解，用数组依然可以表示二叉树。
 *
 * 【二叉树的定义】见下
 *
 @author Alex
 @create 2023-07-06-11:50
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}