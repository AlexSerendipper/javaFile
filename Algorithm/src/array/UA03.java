package array;

import java.util.Arrays;

/**
 * 3. 移除元素：https://leetcode.cn/problems/remove-element/
 *
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素
 *
 * 注意：只是移除了就行，没说要保证移除后数组的正确性！！！
 @author Alex
 @create 2023-06-04-12:47
 */
public class UA03 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,5,2,4};
        UA03 test = new UA03();
        int i = test.removeElement(nums, 2);
        System.out.println(i);
    }


    // 暴力解法：两层for循环，一个for循环遍历数组元素 ，第二个for循环更新数组
    public int removeElement(int[] nums, int val) {
        int size = nums.length;
        for (int i = 0; i < size; i++) {
            if (nums[i] == val) {  // 发现需要移除的元素，就将数组集体向前移动一位
                for (int j = i + 1; j < size; j++) {
                    nums[j - 1] = nums[j];
                }
                i--;  // 因为下标i以后的数值都向前移动了一位，所以i也向前移动一位（下一次仍应判断当前位置）
                size--;  // 此时数组的大小-1
            }
        }
        System.out.println(Arrays.toString(nums));
        return size;
    }

    // 快慢指针法
    public int removeElement2(int[] nums, int val) {
        int slowIndex = 0;  // 慢指针：指向更新 新数组下标的位置

        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {  // 快指针：寻找新数组的元素 ，新数组就是不含有目标元素的数组
            if (val != nums[fastIndex]) {
                nums[slowIndex++] = nums[fastIndex];
            }
        }

        return slowIndex;
    }

}
