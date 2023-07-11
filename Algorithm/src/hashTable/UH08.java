package hashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]]
 * （若两个四元组元素一一对应，即四个元素相同，则认为两个四元组重复）：
 * a、b、c 和 d 互不相同  且  nums[a] + nums[b] + nums[c] + nums[d] == target
 @author Alex
 @create 2023-06-24-10:06
 */
public class UH08 {
    public static void main(String[] args) {
        int[] nums ={1,0,-1,0,-2,2};
        int[] nums2 ={2,2,2,2,2};
        List<List<Integer>> lists = new UH08().fourSum(nums2,8);

        for(List i :lists){
            System.out.println(i);
        }
    }
// 四数之和，和15.三数之和 (opens new window)是一个思路，都是使用双指针法, 基本解法就是在15.三数之和 (opens new window)的基础上再套一层for循环。
// 但是有一些细节需要注意，例如： 不要判断nums[k] > target 就返回了，三数之和 可以通过 nums[i] > 0 就返回了，因为 0 已经是确定的数了，
// 四数之和这道题目 target是任意值。比如：数组是[-4, -3, -2, -1]，target是-10，不能因为-4 > -10而跳过。
// 但是我们依旧可以去做剪枝，逻辑变成nums[i] > target && (nums[i] >=0 || target >= 0)就可以了。
    public List<List<Integer>> fourSum(int[] nums, int target) {
        ArrayList<List<Integer>> lists = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            // 剪枝：排序之后, 满足下述条件时，则不再能组成四数之和了，直接返回结果就可以了！！
            if (nums[i] > target && (nums[i] >=0 || target >= 0)) {
                return lists;
            }

            // 去重
            if(i>0 && nums[i] == nums[i-1]){
                continue;
            }

            for (int j = i+1; j < nums.length; j++) {
                // 去重，j>i+1是为了保证，如果i后的所有数相同，如[2,2,2,2,2]，不至于报错
                if(j>i+1 && nums[j] == nums[j-1]){
                    continue;
                }

                int left = j+1;
                int right = nums.length-1;

                while(left<right){
                    int sum = nums[i]+nums[j]+nums[left]+nums[right];
                    if(sum>target){
                        right--;
                    }else if(sum<target){
                        left++;
                    }else {
                        ArrayList<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[left]);
                        temp.add(nums[right]);
                        lists.add(temp);

                        // 去重:第一次找到后进行去重
                        while(left<right && nums[left+1]==nums[left]){left++;}
                        while(left<right && nums[right-1]==nums[right]){right--;}
                        left++;
                        right--;
                    }
                }
            }
        }
        return lists;
    }
}
