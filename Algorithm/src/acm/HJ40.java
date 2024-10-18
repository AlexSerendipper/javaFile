package acm;

import java.util.Scanner;

/**
 * HJ40 统计字符
 * 描述：输入一行字符，分别统计出包含英文字母、空格、数字和其它字符的个数。
 *
 * 数据范围：输入的字符串长度满足1≤n≤1000
 *
 * 输入描述：输入一行字符串，可以有空格
 *
 * 输出描述：统计其中英文字符，空格字符，数字字符，其他字符的个数
 *
 * 示例1
 * 输入：1qazxsw23 edcvfr45tgbn hy67uj m,ki89ol.\\/;p0-=\\][
 *
 * 输出：
 * 26
 * 3
 * 10
 * 12
 @author Alex
 @create 2024-03-08-13:58
 */
public class HJ40 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        String s1=str.replaceAll("[A-Z]+|[a-z]", "");
        System.out.println(str.length()-s1.length());  // 中英文的长度
        String s2=s1.replaceAll(" ", "");
        System.out.println(s1.length()-s2.length());  // 空格的长度
        String s3=s2.replaceAll("[0-9]+", "");  // 数字字符的长度
        System.out.println(s2.length()-s3.length());
        System.out.println(s3.length());
    }
}
