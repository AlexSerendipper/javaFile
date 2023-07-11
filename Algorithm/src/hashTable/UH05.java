package hashTable;

import java.util.HashMap;

/**
 * 四数相加 II：https://leetcode.cn/problems/4sum-ii/
 *
 * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
 * 0 <= i, j, k, l < n
 * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
 *
 * // 只需要返回个数即可
 *
 *
 @author Alex
 @create 2023-06-22-9:46
 */
public class UH05 {
    //    本题解题步骤：
    //    首先定义 一个unordered_map，key放a和b两数之和，value 放a和b两数之和出现的次数。
    //    遍历大A和大B数组，统计两个数组元素之和，和出现的次数，放到map中。
    //    定义int变量count，用来统计 a+b+c+d = 0 出现的次数。
    //    在遍历大C和大D数组，找到如果 0-(c+d) 在map中出现过的话，就用count把map中key对应的value也就是出现次数统计出来。
    //    最后返回统计值 count 就可以了
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // 统计两个数组中的元素之和，同时统计出现的次数，放入map
        for (int i : nums1) {
            for (int j : nums2) {
                int sum = i + j;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }

        int count=0;
        for (int i : nums3) {
            for (int j : nums4) {
                // 两数之和为-i-j, 即相加为0
                if(map.containsKey(-i-j)){
                    count += map.get(-i-j);
                }
                // count += map.getOrDefault(0 - i - j, 0);
            }
        }

        return count;
    }
}
