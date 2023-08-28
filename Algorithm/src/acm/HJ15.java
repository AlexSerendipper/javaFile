package acm;

import java.util.Scanner;

/**
 * HJ15 求int型正整数在内存中存储时1的个数: https://www.nowcoder.com/practice/440f16e490a0404786865e99c6ad91c9?tpId=37&tqId=21238&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D1%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 描述:输入一个 int 型的正整数，计算出该 int 型数据在内存中存储时 1 的个数。
 * 数据范围：保证在 32 位整型数字范围内
 * 输入描述：输入一个整数（int类型）
 * 输出描述：这个数转换成2进制后，输出1的个数
 @author Alex
 @create 2023-07-13-10:10
 */

// 先把十进制数字转成二进制数字的字符串，再将字符串中的“1”全部替换为空，再让替换前字符串的长度减去替换后字符串的长度就是1的个数
public class HJ15 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        String str = Integer.toBinaryString(num);
        int length = str.length();
        String newStr = str.replaceAll("1", "");
        System.out.println(length - newStr.length());
    }
}
