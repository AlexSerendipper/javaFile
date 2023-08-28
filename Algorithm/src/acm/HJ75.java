package acm;

import java.util.Scanner;

/**
 * HJ75 公共子串计算:https://www.nowcoder.com/practice/98dc82c094e043ccb7e0570e5342dd1b?tpId=37&tqId=21298&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 *
 * 给定两个只包含小写字母的字符串，计算两个字符串的最大公共子串的长度。注：子串的定义指一个字符串删掉其部分前缀和后缀（也可以不删）后形成的字符串。
 * 输入描述：输入两个只包含小写字母的字符串
 * 输出描述：输出一个整数，代表最大公共子串的长度
 *
 @author Alex
 @create 2023-08-04-10:24
 */
public class HJ75 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.next();
        String l = in.next();

        if (s.length() > l.length()) {
            String temp = l;
            l = s;
            s = temp;
        }

        int maxLength = 0;
        for (int i = 0; i <= s.length(); i++) {
            for (int j = s.length(); j >= i; j--) {
                String subStr = s.substring(i, j);
                if (l.contains(subStr) && subStr.length()>maxLength) {
                    maxLength = subStr.length();
                }
            }
        }
        System.out.println(maxLength);

    }
}
