package array;

import java.util.Arrays;

/**
 * 4. 有序数组的平方：https://leetcode.cn/problems/squares-of-a-sorted-array/
 * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
 @author Alex
 @create 2023-06-06-15:05
 */
public class UA04 {
    public static void main(String[] args) {
        int[] arrya1 = new int[]{-4, -1, 0, 3, 10};
        int[] ints = new UA04().sortedSquares2(arrya1);
        System.out.println(Arrays.toString(ints));
    }


    // 方式一：数组中每个数平方后，直接排序不就好了
    public int[] sortedSquares(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nums[i] * nums[i];
        }

        Arrays.sort(nums);
        return nums;
    }

    // 方式二：双指针法
    public int[] sortedSquares2(int[] nums) {
        int[] result = new int[nums.length];
        int i = 0,j=nums.length-1,k=nums.length-1;  // i指向数组的起始位置，j指向终止位置, k指向新数组的终止位置
        while (k >= 0) {
            if(nums[i] * nums[i] < nums[j] * nums[j]) {
                result[k--] = nums[j] * nums[j];
                j--;
            }else{
                result[k--] = nums[i] * nums[i];
                i++;
            }
        }
        return result;
    }

}
