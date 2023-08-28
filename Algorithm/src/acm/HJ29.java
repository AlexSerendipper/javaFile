package acm;

import java.util.Scanner;

/**
 * 描述
 * 对输入的字符串进行加解密，并输出。
 * 加密方法为：
 * 当内容是英文字母时则用该英文字母的后一个字母替换，同时字母变换大小写,如字母a时则替换为B；字母Z时则替换为a；
 * 当内容是数字时则把该数字加1，如0替换1，1替换2，9替换0；
 * 其他字符不做变化。
 *
 * 解密方法为加密的逆过程。
 * 数据范围：保证输入的字符串都是只由大小写字母或者数字组成
 *
 * 输入描述：
 * 第一行输入一串要加密的密码
 * 第二行输入一串加过密的密码
 *
 * 输出描述：
 * 第一行输出加密后的字符
 * 第二行输出解密后的字
 *
 * 输入：
 * abcdefg
 * BCDEFGH
 *
 * 输出：
 * BCDEFGH
 * abcdefg
 @author Alex
 @create 2023-08-18-9:46
 */
public class HJ29 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String str1 = sc.next();
        String str2 = sc.next();
        System.out.println(Encryption(str1));
        System.out.println(Decryption(str2));


    }

    public static String Encryption(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        char[] strs = str.toCharArray();

        for (int i = 0; i < strs.length; i++) {
            if ((strs[i] + "").matches("[a-y]")) {
                sb.append((char) (strs[i] - 32 + 1));
            } else if ((strs[i] + "").matches("[z]")) {
                sb.append('A');
            } else if ((strs[i] + "").matches("[A-Y]")) {
                sb.append((char) (strs[i] + 32 + 1));
            } else if ((strs[i] + "").matches("[Z]")) {
                sb.append('a');
            } else if((strs[i] + "").matches("[0-8]")){
                int num = Integer.parseInt(strs[i] + "");
                num++;
                sb.append(num);
            }else if((strs[i] + "").matches("[9]")){
                sb.append('0');
            }else {
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }

    public static String Decryption(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        char[] strs = str.toCharArray();

        for (int i = 0; i < strs.length; i++) {
            if ((strs[i] + "").matches("[b-z]")) {
                sb.append((char) (strs[i] - 32 - 1));
            } else if ((strs[i] + "").matches("[A]")) {
                sb.append('z');
            } else if ((strs[i] + "").matches("[B-Z]")) {
                sb.append((char) (strs[i] + 32 - 1));
            } else if ((strs[i] + "").matches("[a]")) {
                sb.append('Z');
            } else if((strs[i] + "").matches("[1-9]")){
                int num = Integer.parseInt(strs[i] + "");
                num--;
                sb.append(num);
            }else if((strs[i] + "").matches("[0]")){
                sb.append('9');
            }else {
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }
}
