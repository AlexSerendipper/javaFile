package interview_computer2;

import java.util.HashSet;
import java.util.Set;

/**
 * 最长连续序列
 *
 * 描述:给定一个未排序的整数数组nums，找出数字连续的最长序列(不要求序列元素在原数组中连续)的长度
 * 请你设计并实现时间复杂度为O(n)的算法解决此问题
 *
 * 示例 1:
 * 输入: nums =[1004,200,1,3,2]
 * 输出:4
 * 解释: 最长数字连续序列是[1,2,3,4]。它的长度为4。
 *
 * 示例2:
 * 输入: nums =[0,3,7,2,5,8,4,6,0,1]
 * 输出:9
 *
 @author Alex
 @create 2023-08-20-15:05
 */
public class UI04 {
    public static void main(String[] args) {
        int[] i = {0,3,7,2,5,8,4,6,0,1};
        System.out.println("longestConsecutive(i) = " + longestConsecutive(i));
    }


    public static int longestConsecutive(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return 1;
        }
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }
        int longestStreak = 0;
        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        return longestStreak;
    }

}
