package acm;

import java.util.Scanner;

/**
 * 进制转换:https://www.nowcoder.com/practice/8f3df50d2b9043208c5eed283d1d4da6?tpId=37&tqId=21228&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 写出一个程序，接受一个十六进制的数，输出该数值的十进制表示。
 @author Alex
 @create 2023-06-28-9:53
 */
public class HJ5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String str = in.next();
            // 所含的16进制数有0x，这两位是没用滴
            int result = Integer.parseInt(str.substring(2), 16);
            System.out.println(result);
        }
    }
}
