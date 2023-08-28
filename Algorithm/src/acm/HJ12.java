package acm;

import java.util.Scanner;

/**
 * HJ12 字符串反转:https://www.nowcoder.com/practice/e45e078701ab4e4cb49393ae30f1bb04?tpId=37&tqId=21235&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 接受一个只包含小写字母的字符串，然后输出该字符串反转后的字符串。（字符串长度不超过1000）
 * 输入描述：
 * 输入一行，为一个只包含小写字母的字符串。
 *
 * 输出描述：
 * 输出该字符串反转后的字符串。
 @author Alex
 @create 2023-07-07-10:23
 */
public class HJ12 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String str = in.nextLine();
            char[] chr = str.toCharArray();
            for(int i=0,j=chr.length-1;i<=j;i++,j--){
                char temp = chr[i];
                chr[i] = chr[j];
                chr[j] = temp;
            }
            System.out.println(new String(chr));
        }
    }
}
