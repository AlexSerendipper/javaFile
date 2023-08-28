package acm;

import java.util.Scanner;

/**
 * HJ65：查找两个字符串a,b中的最长公共子串。：https://www.nowcoder.com/practice/181a1a71c7574266ad07f9739f791506?tpId=37&tqId=21288&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 若有多个，输出在较短串中最先出现的那个。
 *
 * 输入描述:输入两个字符串
 * 输出描述:
 * 返回重复出现的字符
 * 示例1
 * 输入
 * abcdefghijklmnop
 * abcsafjklmnopqrstuvw
 * 输出
 * jklmnop
 *
 * HJ65 查找两个字符串a,b中的最长公共子串
 @author Alex
 @create 2023-08-01-15:34
 */
public class HJ65 {
    public static void main(String[]args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s1 = sc.nextLine();
            String s2 = sc.nextLine();
            longString(s1, s2);
        }
    }

    // 还是双指针法 不断进行匹配
    public static void longString(String s1, String s2) {
        String shortStr = s1.length() < s2.length() ? s1 : s2;
        String longStr = s1.length() > s2.length() ? s1 : s2;
        int shortLen = shortStr.length();

        int maxLen = 0;
        String result = "";
        for (int i = 0; i < shortLen; i++) {
            // 剪枝，子串长度已经不可能超过maxLen，退出循环
            if (shortLen - i + 1 <= maxLen) {
                break;
            }
            // 左指针j，右指针k, 右指针逐渐向左逼近（因为使用了substring所以不是k并不是shortLen-1）
            for (int k = shortLen; k > i; k--) {
                String subStr = shortStr.substring(i, k);
                if (longStr.contains(subStr) && maxLen < subStr.length()) {  // 如果是子串 并且长度比其他子串更大
                    result = subStr;  // 则记录子串
                    maxLen = subStr.length();
                    // 找到就立即跳出当前循环，即对应着若有多个，输出在较短串中最先出现的那个。
                    break;
                }
            }
        }
        System.out.println(result);
    }
}
