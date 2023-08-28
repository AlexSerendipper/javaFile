package acm;

import java.util.Scanner;

/**
 * 描述
 * 蛇形矩阵是由1开始的自然数依次排列成的一个矩阵上三角形。
 *
 * 例如，当输入5时，应该输出的三角形为：
 * 1 3 6 10 15
 * 2 5 9 14
 * 4 8 13
 * 7 12
 * 11
 *
 *
 * 输入描述：
 * 输入正整数N（N不大于100）
 *
 * 输出描述：
 * 输出一个N行的蛇形矩阵。
 @author Alex
 @create 2023-08-20-8:45
 */

public class HJ35 {
    // 首先找到规律，是按照左下到右上的对角线进行赋值的
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()){
            int n = in.nextInt();  // 读入正整数n

            int[][] result = new int[n][];  // 建立数组（n行）
            int t = 1;  // 记录依次赋予的数组值
            for(int i=0; i < n; i++){
                result[i] = new int[n-i];  // 数组第i行有n-i个元素

                for(int j=0; j < i+1; j++){  // ✔难点1：对第i个对角线赋值
                    result[i-j][j] = t;
                    t++;
                }
            }

            // ✔难点2：输出数组值
            for(int[] a : result){  // 遍历每一行
                for(int a1 : a){
                    // 输出每一个
                    System.out.print(a1 + " ");
                }
                System.out.println();
            }
        }
    }
}
