package acm;

import java.util.Scanner;

/**
 * 字符串最后一个单词的长度: https://www.nowcoder.com/practice/8c949ea5f36f422594b306a2300315da?tpId=37&tqId=21224&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 *
 * 描述: 计算字符串最后一个单词的长度，单词以空格隔开，字符串长度小于5000。（注：字符串末尾不以空格为结尾）
 * 输入描述：输入一行，代表要计算的字符串，非空，长度小于5000。
 * 输出描述：输出一个整数，表示输入字符串最后一个单词的长
 *
 @author Alex
 @create 2023-06-23-10:37
 */
public class HJ1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }

        String str = in.nextLine();
        String[] s = str.split(" ");
        int length = s[s.length - 1].length();
        System.out.println(length);
    }
}
