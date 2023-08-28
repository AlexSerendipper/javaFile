package binaryTree;

/**
 * 654.最大二叉树：https://leetcode.cn/problems/maximum-binary-tree/
 *
 * 给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：
 *
 * 二叉树的根是数组中的最大元素。
 * 左子树是通过数组中最大值左边部分构造出的最大二叉树。
 * 右子树是通过数组中最大值右边部分构造出的最大二叉树。
 * 通过给定的数组构建最大二叉树，并且输出这个树的根节点。
 *
 @author Alex
 @create 2023-08-20-9:00
 */
public class UB19 {
    // 通常涉及到构造二叉树的题目，我们都是使用前序遍历，即先构造根节点
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTree1(nums, 0, nums.length);  // 传入索引，由于是左闭右开，所以传入nums.length
    }

    public TreeNode constructMaximumBinaryTree1(int[] nums, int leftIndex, int rightIndex) {
        // 终止条件
        if (rightIndex - leftIndex < 1) {  // 没有元素了，中止
            return null;
        }

        if (rightIndex - leftIndex == 1) {  // 如果传入的数组只有一个元素，说明其即为根节点，也为叶子节点，就无需进行后面的判断
            return new TreeNode(nums[leftIndex]);
        }


        int maxIndex = leftIndex;  // 最大值索引，这里只是赋值了初始值
        int maxVal = nums[maxIndex];  // 最大值
        for (int i = leftIndex + 1; i < rightIndex; i++) {
            if (nums[i] > maxVal){
                maxVal = nums[i];
                maxIndex = i;
            }
        }

        TreeNode root = new TreeNode(maxVal);
        // 单层循环，根据maxIndex划分左右子树
        root.left = constructMaximumBinaryTree1(nums, leftIndex, maxIndex);  // 左闭右开，所用使用maxIndex即可
        root.right = constructMaximumBinaryTree1(nums, maxIndex + 1, rightIndex);  // 左闭右开，所用使用maxIndex即可
        return root;
    }

}
