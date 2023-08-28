package acm;

import java.util.Scanner;

/**
 * 功能:输入一个正整数，按照从小到大的顺序输出它的所有质因子（重复的也要列举）（如180的质因子为2 2 3 3 5 ）
 * (提示：一个数能被分成若干质数的乘积，这些质数即为质数因子)
 * 输入描述：输入一个整数
 * 输出描述：按照从小到大的顺序输出它的所有质数的因子，以空格隔开
 @author Alex
 @create 2023-06-29-11:05
 */
public class HJ6 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLong()) { // 注意 while 处理多个 case
            long num = in.nextLong();
            // 从2开始， 能被整除说明是质数因子，除后继续找质数因子。。
            for (int i = 2; i <= Math.sqrt(num); i++) {
                while (num % i == 0) {
                    System.out.print(i+" ");
                    num /= i;
                }
            }
            // 最后剩下的数，如果不是1和2，也是质数因子
            if (num > 2) {
                System.out.print(num);
            }

        }
    }

}