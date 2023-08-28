package binaryTree;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 * 注意: 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3] 返回如下的二叉树：
 *
 @author Alex
 @create 2023-08-15-11:04
 */

    // 第一步：如果数组大小为零的话，说明是空节点了。
    // 从后序遍历数组中得知根节点为3
    // 此时找到后序数组最后一个元素在中序数组的位置，作为切割点（分为左右两子数）
    // 第四步：切割中序数组，切成中序左数组和中序右数组(切割成中序左数组和中序右数组) （顺序别搞反了，一定是先切中序数组）
    // 第五步：切割后序数组（利用左右子树，这里利用左子树的长度来切割），切成 后序左数组 和 后序右数组
    // 第六步：递归处理左区间和右区间

class UB18 {
    public static void main(String[] args) {
        int [] inorder = {9,3,15,20,7};
        int [] postorder = {9,15,7,20,3};
        new UB18().buildTree(inorder,postorder);
    }
    Map<Integer, Integer> map;  // 方便根据数值查找位置
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {  // 用map保存中序序列的数值对应位置
            map.put(inorder[i], i);
        }
        return findNode(inorder,  0, inorder.length, postorder,0, postorder.length);  // 前闭后开。所以传入的是.length
    }

    /**
     *
     * @param inorder
     * @param inBegin：中序 开始
     * @param inEnd：中序 结束
     * @param postorder：后序 开始
     * @param postBegin：后序 结束
     * @param postEnd
     * @return
     */
    // 注意循环条件为 传入一颗树的中序和后序数组
    public TreeNode findNode(int[] inorder, int inBegin, int inEnd, int[] postorder, int postBegin, int postEnd) {
        // 第一步：终止条件，（参数里的范围都是前闭后开）
        if (inBegin >= inEnd || postBegin >= postEnd) {  // 不满足左闭右开，说明没有元素，返回空树
            return null;
        }

        int rootIndex = map.get(postorder[postEnd - 1]);   // 第二步：找到后序遍历的最后一个元素 在中序遍历中的位置（以此分割左子树和右子数），即根据3将inorder分为[9]和[15,20,7]
        TreeNode root = new TreeNode(inorder[rootIndex]);   // 构造结点
        int lenOfLeft = rootIndex - inBegin;  // 第三步：保存中序左子树个数，用来确定后序数列的个数

        // 按照要求，这里传入的应该是左子树的中序和后序数组，即传入左中序和左后序
        root.left = findNode(inorder, inBegin, rootIndex, postorder, postBegin, postBegin + lenOfLeft);
        // 按照要求，这里传入的应该是右子树的中序和后序数组，即传入右中序和右后序
        root.right = findNode(inorder, rootIndex + 1, inEnd, postorder, postBegin + lenOfLeft, postEnd - 1);
        return root;
    }


}

