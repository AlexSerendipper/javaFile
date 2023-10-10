package interview_computer1;

import java.util.Scanner;

/**
 * 小米9.23，编程第一题
 * 矩阵岛屿
 * 题目描述:
 * 给一个01矩阵，1代表是陆地，0代表海洋，如果两个1相邻，那么这两个1属于同一个岛。我们只考虑上下左右为相邻。
 * 岛屿: 相邻陆地可以组成一个岛屿 (相邻:上下左右) 判断岛屿个数
 *
 * 输入描述 01矩阵
 * [[1,1,0,0,0],[0,1,0,1,1],[0,0,0,1,1],[0,0,0,0,0],[0,0,1,1,1]]
 *
 * 输出描述 岛屿个数
 * 3
 @author Alex
 @create 2023-09-23-16:51
 */
public class UI10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String s1 = s.replace("[", "");
        String[] strs = s1.split("]");
        int M = strs.length;
        String[] temp = strs[0].split(",");
        int N = temp.length;
        int[][] nums = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if(i==0){
                    String[] sss = strs[i].split(",");
                    nums[i][j] = Integer.parseInt(sss[j]);
                }else {
                    String strss = strs[i].substring(1, 2*N);
                    String[] sss = strss.split(",");
                    nums[i][j] = Integer.parseInt(sss[j]);
                }
            }
        }

        // 以上能够从输入的字符串得到正确的二维矩阵nums
        // 接下来的思路就是，遍历，然后用到递归的思想，如果有1就把其周围的1全部找到，然后变为0，count++
        // 直到整个二维矩阵变为0

    }
}
