package array;

import org.junit.jupiter.api.Test;

/**
 * 螺旋矩阵：https://leetcode.cn/problems/spiral-matrix-ii/
 * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
 @author Alex
 @create 2023-06-08-16:44
 */
public class UA06 {
    @Test
    public void test(){
        int nums = 3;
        int[][] array1 = new UA06().generateMatrix(nums);

        // 输出数组
        for(int i1=0;i1<nums;i1++) {
            for(int k1=0;k1<array1[i1].length;k1++) {
                System.out.print(array1[i1][k1]+"\t");
            }
            System.out.println();
        }
        int[][] arr = new int[2][3];
        int n = arr.length;
    }


    public int[][] generateMatrix(int n) {
        int[][] array1 = new int [n][n];
        /* 因为方向一直都是顺时针，所以思路如下：
         * k = 1:向右 k = 2:向下 k = 3:向左 k = 4:向上
         */
        int sum = n * n;
        int k=1,i=0,j=0; // i控制行数，j控制列数
        for(int m=1;m<=sum;m++) {
            // 向右
            if(k==1){
                if(j<n && array1[i][j]==0) {
                    array1[i][j++] = m;  // j++保证能退出循环
                }
                else {
                    k = 2;
                    j--;
                    m--;
                    i++;
                    continue;
                }
            }

            // 向下
            if(k==2) {
                if(i<n && array1[i][j]==0) {
                    array1[i++][j] = m;
                }
                else {
                    k = 3;
                    i--;
                    m--;
                    j--;
                    continue;
                }
            }

            // 向左
            if(k==3) {
                if(j>=0 && array1[i][j]==0) {
                    array1[i][j--] = m;
                }
                else {
                    k = 4;
                    i--;
                    m--;
                    j++;
                    continue;
                }
            }

            // 向上
            if(k==4) {
                if(i>=0 && array1[i][j]==0) {
                    array1[i--][j] = m;
                }
                else {
                    k = 1;
                    i++;
                    m--;
                    j++;
                    continue;
                }
            }
        }
        return array1;
    }

}
