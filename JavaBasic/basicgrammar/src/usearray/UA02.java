package usearray;

import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

/**
 *【数组中涉及到的常见算法】1
 * 1. 数组元素的赋值(杨辉三角、回形数等)
 * 2. 求数值型数组中元素的最大值、最小值、平均数、总和等
 * 3. 数组的复制、反转、查找(线性查找、二分法查找)
 * 4. 数组元素的排序算法
 @author Alex
 @create 2023-01-09-15:59
 */
public class UA02 {
    // 数组元素的赋值：杨辉三角
    //【提示】第一行有 1 个元素, 第 n 行有 n 个元素
    //       每一行的第一个元素和最后一个元素都是 1
    //       从第三行开始, 对于非第一个元素和最后一个元素的元素。有：yanghui[i][j] = yanghui[i-1][j-1] + yanghui[i-1][j];
    @Test
    public void test1(){
        int[][] yanghuiArray = new int[10][];
        // 这里是关键，一定要给第二层数组赋值，否则必错!!
        for(int i=0;i<10;i++) {
            yanghuiArray[i] = new int[i+1];
        }
        for(int i=0;i<10;i++) {
            for(int j=0;j < 1 + i; j++) {
                // 第一和最后为1	（同时保证了前两行都为1）
                if(j==0 || j==i) {
                    yanghuiArray[i][j]=1;
                }
                else {
                    // 其余列按照这个规律
                    yanghuiArray[i][j] = yanghuiArray[i-1][j-1] + yanghuiArray[i-1][j];
                }
            }
        }
        // 输出杨辉三角
        for(int i=0;i<yanghuiArray.length;i++) {
            for(int j=0;j<yanghuiArray[i].length;j++) {
                System.out.print(yanghuiArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 数组元素的赋值问题: 创建一个长度为6的int型数组，要求数组元素的值都在1-30之间，且是随机赋值。同时要求元素的值各不相同
    @Test
    public void test2(){
        int[] arr1 = new int[6];
        for(int i=0;i<6;i++) {
            // 转换为整型
            arr1[i] = (int)(Math.random() * 30 + 1);

            // 这里是难点，如果数字和前面相同，该数重新赋值!
            for(int j=0; j<i; j++) {
                if(arr1[i] == arr1[j]) {
                    i--;
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(arr1));
    }

    // 数组元素的赋值问题:回形数格式方阵
    // 从键盘输入一个整数（1~20）.以该数字为矩阵的大小，把1,2,3…n*n的数字按照顺时针螺旋的形式填入其中
    // 例如：输入数字2，则程序输出：1 2
    //                           4 3
    //       输入数字3，则程序输出：1 2 3
    //                            8 9 4
    //                            7 6 5
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个数字：");
        int nums = scanner.nextInt();
        int[][] array1 = new int [nums][nums];
        /* 因为方向一直都是顺时针，所以思路如下：
         * k = 1:向右 k = 2:向下 k = 3:向左 k = 4:向上
         */
        int sum = nums*nums;
        int k=1,i=0,j=0; // i控制行数，j控制列数
        for(int m=1;m<=sum;m++) {
            // 向右
            if(k==1){
                if(j<nums && array1[i][j]==0) {
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
                if(i<nums && array1[i][j]==0) {
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

        // 输出数组
        for(int i1=0;i1<nums;i1++) {
            for(int k1=0;k1<array1[i1].length;k1++) {
                System.out.print(array1[i1][k1]+"\t");
            }
            System.out.println();
        }
    }

    // 数值型数组的求值问题：定义一个int型的一维数组，包含10个元素，分别赋一些随机整数(要求为两位数)，然后求出所有元素的最大值，最小值，和值，平均值，并输出出来。
    @Test
    public void test3(){
        int[] array1 = new int[10];
        int max = 0;
        int min = 101;
        int sum = 0;
        int average;

        for(int i=0; i<10; i++) {
            array1[i] = (int)(Math.random()*90 + 10);
            System.out.print(array1[i] + "  ");
            // 求最大值
            if(array1[i]>max) {
                max = array1[i];
            }
            // 求最小值
            if(array1[i]<min) {
                min = array1[i];
            }
            // 求总和
            sum += array1[i];
        }
        // 求平均数
        average = sum / 10;
        System.out.println("");
        System.out.println("最大值为:"+max+" 最小值为:"+min+" 总合为:"+sum+" 平均值为:"+average);
    }



}

