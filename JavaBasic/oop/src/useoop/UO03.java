package useoop;

import org.junit.Test;

/**
 * 【递归方法例题】
 @author Alex
 @create 2023-01-10-14:30
 */
public class UO03 {
    //需求1：计算1-100之间所有自然数的和
    public int getSum(int n) {
        if(n==1) {
            return 1;
        }
        else {
            return n + getSum(n-1);
        }
    }
    //需求2：请用Java写出递归求阶乘(n!)的算法
    public int getSum1(int n) {
        if(n==1) {
            return 1;
        }
        else {
            return n * getSum(n-1);
        }
    }
    //需求3：已知有一个数列：f(0) = 1,f(1) = 4,f(n+2)=2*f(n+1) + f(n),其中n是大于0的整数，求f(10)的值。
    public int getSum3(int n) {
        if(n==0) {
            return 1;
        }
        else if(n==1) {
            return 4;
        }
        else {
            //		return getSum3(n+2) - 2*getSum3(n+1); 只知道f(0)f(1),不能往上递归呀!!!!!
            return 2*getSum3(n-1) + getSum3(n-2);
        }
    }
    //需求4：已知一个数列：f(20) = 1,f(21) = 4,f(n+2) = 2*f(n+1)+f(n),其中n是大于0的整数，求f(10)的值。
    public int getSum4(int n) {
        if(n==20) {
            return 1;
        }
        else if(n==21) {
            return 4;
        }
        else {
            return getSum4(n+2) - 2*getSum4(n+1);

        }
    }
    //需求5：输入一个数据n，计算斐波那契数列(Fibonacci)的第n个值。并将整个数列打印出来
    //      斐波那契数列。即，1  1  2  3  5  8  13  21  34  55规律：一个数等于前两个数之和
    // 即f(1)=1,f(2)=1,f(n) = f(n-1) + f(n-2)，其中n>2
    @Test
    public int getSum5(int n) {
        if(n==1) {
            return 1;
        }
        else if(n==2) {
            return 1;
        }
        else {
            return getSum5(n-1) + getSum5(n-2);
        }
    }
}
