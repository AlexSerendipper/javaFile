package acm;

import java.util.Scanner;

/**
 * HJ85 最长回文子串：
 * 描述
 * 给定一个仅包含小写字母的字符串，求它的最长回文子串的长度。
 * 所谓回文串，指左右对称的字符串。
 * 所谓子串，指一个字符串删掉其部分前缀和后缀（也可以不删）的字符串
 *
 * 输入描述：
 * 输入一个仅包含小写字母的字符串。如输入：cdabbacc
 *
 * 输出描述：
 * 返回最长回文子串的长度。如输出： 4
 @author Alex
 @create 2023-07-29-23:13
 */
public class HJ85 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String a = in.next();
            // 遍历查找最长
            int m = Integer.MIN_VALUE;
            for(int i=0;i<a.length();i++){
                // 由于取子串是左闭右开，这里直接用lenth就好
                for(int j=a.length();j>i;j--){
                    String subString = a.substring(i,j);
                    if(isPalindromeString(subString)){
                        m = Math.max(m,j-i);  // 这里j-i==subString.lenght()
                    }
                }
            }
            System.out.println(m);
        }


    }

    // 判断是否为回文子串的方法本质就是比较字符翻转后是否和原字符串相同
    private static boolean isPalindromeString(String str){
        return str.equals(reverseStr(str));
    }

    // 翻转字符串
    private static String reverseStr(String str){
        char[] strs = str.toCharArray();
        for(int i=0,j=str.length()-1;i<=j;i++,j--){
            char temp = strs[i];
            strs[i] = strs[j];
            strs[j] = temp;
        }
        return new String(strs);
    }
}
