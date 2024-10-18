package acm;

import java.util.Scanner;

/**
 * HJ37: 有一种兔子，从出生后第3个月起每个月都生一只兔子，小兔子长到第三个月后每个月又生一只兔子。
 *       例子：假设一只兔子第3个月出生，那么它第5个月开始会每个月生一只兔子。
 *             一月的时候有一只兔子，假如兔子都不死，问第n个月的兔子总数为多少？
 *
 * 数据范围：输入满足1≤n≤31
 * 输入描述：
 * 输入一个int型整数表示第n个月
 *
 * 输出描述：
 * 输出对应的兔子总数
 @author Alex
 @create 2024-03-06-13:21
 */
public class HJ37 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        // 实际上不就是
        // 月份：     1    2    3    4    5    6     ...    n
        //该月总兔数：1    1    2    3    5    8    ...    f(n)
        // 规律为f(n) = f(n-2)+f(n-1)
        System.out.print(fx(n));
    }

    // 输入n需要>3
    public static int fx(int n){
        if(n==1){
            return 1;
        }
        if(n==2){
            return 1;
        }
        return fx(n-2)+fx(n-1);
    }
}
