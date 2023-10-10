package interview_computer1;

import java.util.Scanner;

/**
 * 9.8度小满笔试题1：
 *
 * 题目描述:
 * 现在一个数组被定义为 好的，如果该数组中的最大值是最小值的k倍。
 * 现在给你一个数组，你的任务是计算有多少个子数组是好的。
 * 其中，子数组定义为原数组中一段连续的数组。例如: [4，3，2，7]有以下几个了数组:
 * [4], [4, 3], [4, 3, 2], [4, 3, 2, 7],[3], [3,2], [3,2,7], [2], [2,7], [7]
 *
 *
 @author Alex
 @create 2023-09-06-19:28
 */
public class UI05 {
    // 思路，数组无法排列，直接双指针遍历
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(kth(nums,i,j,n,k)){
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    /**
     * @param nums：传入的数组
     * @param begin：开始索引
     * @param end：结束索引
     * @param n：数组长度
     * @param l：倍数，最小值为最大值的多少倍
     * @return
     */
    public static boolean kth(int[] nums,int begin,int end,int n,int l){
        int[] temp = new int[n];
        int j = 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int k = begin; k <= end; k++) {
            if(nums[k]>max){
                max = nums[k];
            }

            if(nums[k]<min){
                min = nums[k];
            }
        }

        if(max == min * l){
            return true;
        }else {
            return false;
        }
    }
}
