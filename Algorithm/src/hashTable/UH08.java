package hashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和：https://leetcode.cn/problems/3sum/
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * 请你返回所有和为 0 且不重复的三元组。
 *
 @author Alex
 @create 2023-06-23-9:32
 */
public class UH08 {
    public static void main(String[] args) {
        int[] nums ={-1,0,1,2,2,-1,-4};
        int[] nums2 ={-2,0,3,-1,4,0,3,4,1,1,1,-3,-5,4,0};
        List<List<Integer>> lists = new UH08().threeSum(nums2);

        for(List i :lists){
            System.out.println(i);
        }
    }


    public List<List<Integer>> threeSum(int[] nums) {
        ArrayList<List<Integer>> lists = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            // 排序之后如果第一个元素已经大于零，那么无论如何组合都不可能凑成三元组，直接返回结果就可以了！！这里是要返回结果的！
            if (nums[i] > 0) {
                return lists;
            }


            // 去重a (i>0应该是为了保证 i=0 时i-1不存在)
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }


            // 定义 i 和 左右指针
            int left = i+1;
            int right = nums.length-1;

            while(left<right){
                if(nums[i] + nums[left] + nums[right] < 0){
                    left++;
                }else if(nums[i] + nums[left] + nums[right] > 0){
                    right--;
                }else {
                    lists.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    // 在找到第一组三元数之后，对b 和 c进行去重操作（这里一定得是while，因为可能会有多个重复值需要去重）
                    while(left<right && nums[right]==nums[right-1]){
                        right--;
                    }
                    while(left<right && nums[left]==nums[left+1]){
                        left++;
                    }

                    // 找到答案时，双指针同时收缩
                    right--;
                    left++;
                }
            }
        }
        return lists;
    }
}
