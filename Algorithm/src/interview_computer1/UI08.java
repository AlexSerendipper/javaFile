package interview_computer1;

import java.util.Scanner;

/**
 * 9.17字节跳动测开第一题
 * 小红有两个长度为n的数组a,b,他通过以下手段得到第三个长度为n的数组c:
 *
 * 例2
 * 对于每个i∈[1,n]，ci=max(ai,bi)。请注意，c数组是动态修改的，也就是说a和b数组发生变化时c也会发生变化。
 * 接下来有9次操作每行输入三个整数op,x,y
 * op=1表示操作数组a，交换ax和ay。
 * op=2表示操作数组b，交换bx和by
 *
 * 每次操作完成后，输出c数组的和。
 *
 * 输入描述
 * 第一行输入两个整数n,g(1n,50000)
 * 第二行输入n个整数ai;(1 <ai<10)。
 * 第三行输入n个整数bi;(1<b;109)。
 * 接下来q行，每行三个整数表示op,x,y(1 <op<2,1<x,y < n).。
 *
 * 输出描述
 * q行，表示答案
-----
输入
3 3
1 5 6
1 4 2
1 1 3
2 2 3
1 2 1

输出
13
15
15
-----
 *
 @author Alex
 @create 2023-09-09-19:24
 */


public class UI08 {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            b[i] = sc.nextInt();
        }

        for (int i = 0; i < q; i++) {
            int flag = sc.nextInt();
            // 表示操作数组a
            if(flag==1){
                int x = sc.nextInt()-1;
                int y = sc.nextInt()-1;
                // 交换数组值
                int temp = a[x];
                a[x] = a[y];
                a[y] = temp;
                System.out.println(compareC(a,b,n));
            }else if(flag==2){
                int x = sc.nextInt()-1;
                int y = sc.nextInt()-1;
                // 交换数组值
                int temp = b[x];
                b[x] = b[y];
                b[y] = temp;
                System.out.println(compareC(a,b,n));
            }else {
                System.out.println("");
            }
        }

    }

    // 比较出数组c的值之和
    public static int compareC(int[] a, int[] b,int n){
        int res = 0;
        for (int i = 0; i < n; i++) {
            int temp  = a[i]>=b[i]?a[i]:b[i];
            res += temp;
        }
        return res;
    }
}
