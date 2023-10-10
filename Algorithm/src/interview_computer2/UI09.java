package interview_computer2;

import java.util.Scanner;

/**
 * 23/9/12 百度测开笔试第一题
 * 给定一个长度为n的序列a1,a2,,a，你可以选择删去其中最多n-1个数，得到一个新序列b1,b2..,bm(1<=m<=n)
 * (只是删去后的序列，不改变原来的相对顺序)现在你希望删去某些数使得新序列的第i个数的值恰好为i，即 b;=i。
 * 现在你想知道最少需要删去多少个数使得新序列满足条件。如果无论如何都不能做到，请输出-1.
 *
 * 输入描述
 * 第一行一个正整数n，表示序列长度
 * 接下来一行n个空格隔开的数字 a1,a2...an
 *
 * 输出描述
 * 输出一个整数表示最少需要删去的个数，如果做不到，输出 -1
 *
 * 输入:
 * 5
 * 1 4 2 3 5
 * 输出：
 * 2
 *
 @author Alex
 @create 2023-09-09-14:33
 */
public class UI09 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        int target = 1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == target){
                target++;
            }else {
                count++;
            }
        }
        count = count==n?-1:count;
        System.out.println(count);


    }
}
