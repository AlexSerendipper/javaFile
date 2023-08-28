package hashTable;

import java.util.HashSet;

/**
 * 两个数组的交集：https://leetcode.cn/problems/intersection-of-two-arrays/
 *
 * 给定两个数组 nums1 和 nums2 ，返回 它们的交集 。输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。
 * 1 <= nums1.length, nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 1000
 *
 *
 @author Alex
 @create 2023-06-20-10:17
 */
public class UH03 {

    // 方法一：哈希表
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[0];
        }

        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();

        // 遍历nums1 暂存到set1中
        for (int i : nums1) {
            set1.add(i);
        }
        // 遍历数组2的过程中判断哈希表中是否存在该元素
        for(int i : nums2){
            if(set1.contains(i)){
                set2.add(i);
            }
        }

        //另外申请一个数组存放set2中的元素,最后返回数组
        int[] arr = new int[set2.size()];
        int j = 0;
        for(int i : set2){
            arr[j++] = i;
        }
        return arr;
    }


    // 方法二：哈希数组，由于定义了数值的大小，所以才可以使用哈希数组
    public int[] intersection2(int[] nums1, int[] nums2) {
        int[] arr1 = new int[1000];
        HashSet<Integer> set1 = new HashSet<>();

        for (int i : nums1) {
            // nums1中出现的字母在arr1数组中做记录
            arr1[i] = 1;
        }

        // nums2中出现，arr2记录
        int j = 0;
        for(int i : nums2){
            if(arr1[i] == 1){
                set1.add(i);
            }
        }

        //另外申请一个数组存放set2中的元素,最后返回数组
        int[] arr = new int[set1.size()];
        j = 0;
        for(int i : set1){
            arr[j++] = i;
        }
        return arr;
    }
}
