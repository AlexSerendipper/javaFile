package acm;

import java.util.Scanner;

/**
 * 描述
 * 某商店规定：三个空汽水瓶可以换一瓶汽水，允许向老板借空汽水瓶（但是必须要归还）。
 * 小张手上有n个空汽水瓶，她想知道自己最多可以喝到多少瓶汽水。
 *
 * 注意：本题存在多组输入。输入的 0 表示输入结束，并不用输出结果。
 * 输入描述：
 * 输入文件最多包含 10 组测试数据，每个数据占一行，仅包含一个正整数 n（ 1<=n<=100 ），表示小张手上的空汽水瓶数。n=0 表示输入结束，你的程序不应当处理这一行。
 *
 * 输出描述：
 * 对于每组测试数据，输出一行，表示最多可以喝的汽水瓶数。如果一瓶也喝不到，输出0
 *
 @author Alex
 @create 2023-08-12-9:15
 */
public class HJ22 {
    // 题目描述中有讲到：剩2个空瓶子时，可以先找老板借一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。
    // 也就是说2个空瓶子即可换一瓶汽水喝，而且喝完之后手里也没有空瓶子。求解时直接把空瓶数除以2，即可得到正解。
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            int dividend = Integer.parseInt(in.next());
            if(dividend==0){
                break;
            }

            int bottle = maxDivision(dividend, 3);

            int bottles  = bottle + dividend%3;

            int sum = maxDivision(dividend,3) + bottles/2;

            System.out.println(sum);
        }
    }


    /**
     *
     * @param dividend：被除数
     * @param divisor：除数
     * @return：最大能上几
     */
    public static int maxDivision(int dividend,int divisor){
        int count =0;
        while(dividend>=0){
            dividend -= divisor;
            if(dividend>=0){
                count++;
            }
        }
        return count;
    }
}
