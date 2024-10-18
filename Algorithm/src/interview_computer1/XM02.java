package interview_computer1;

import java.util.HashSet;
import java.util.Scanner;

/**
 * 小米：3/12
 * 题目：小李天生偏爱一些字符，对于一个字符串，他总是想把字符串中的字符变成他偏爱的那些字符。
 * 如果字符串中某个字符不是他所偏爱的字符，称为非偏爱字符，那么他会将该非偏爱字符替换为字符串中距离该字符最近的一个偏爱的字符。
 * 这里的距离定义即为字符在字符串中的对应下标之差的绝对值。如果有不止一个偏爱的字符距离非偏爱字符最近，
 * 那么小李会选择最左边的那个偏爱字符来替换该非偏爱字符，这样就保证了替换后的字符串是唯一的。
 * 小李的所有替换操作是同时进行的。假定小李有m个偏爱的字符，依次为c,c.….cm，当小李看到一个长度为n的字符串s时，
 * 请你输出小李在进行全部替换操作后形成的字符串。
 *
 * 输入：
 * 12 4
 * Z G B A
 * ZQWEGRTBYAAI
 *
 * 输出：
 * ZZZGGGBBBAAA
 *
 * 解释：
 * 字符0为非偏爱字符，且偏爱字符z距离它最近，所以替换成z;同理E距离c最近，替换成G:对于字符w，偏爱字符z和G与其距离相同，所以替换为左边的2:
 * 对于字符 1，右边没有偏爱字符，左边第一个偏爱字符是2，所以替换成字符A同一个偏爱字符可能会在字符串中出现多次。
 @author Alex
 @create 2023-08-26-9:20
 */
public class XM02 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();

//        char[] chars = new char[m];
        HashSet<Character> chars = new HashSet<>();

        for (int i = 0; i < m; i++) {
            String temp = in.next();
            chars.add(temp.charAt(0));
        }

        String str = in.next();
//        String ans = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            if(chars.contains(c)){
                sb.append(c);
            } else{
                int right = rightIdx(str.substring(i,str.length()),chars);
                int left = leftIdx(str.substring(0,i),chars);
                if(left<right){
                    sb.append(str.charAt(i-left));
                }else if(left>right){
                    sb.append(str.charAt(i+right));
                }else{
                    sb.append(str.charAt(i-left));
                }
            }

        }
        System.out.println(sb.toString());


    }

    // 右边最近的偏爱字符的距离
    public static int rightIdx(String s,HashSet<Character> chars){
        int distance = 99;
        for (int i = 0; i < s.length(); i++) {
            if(chars.contains(s.charAt(i))){
                distance = i;
                break;
            }
        }
        return distance;
    }

    // 左边最近的偏爱字符的距离
    public static int leftIdx(String s,HashSet<Character> chars){
        int distance = 99;
        for (int i = s.length()-1; i >= 0; i--) {
            if(chars.contains(s.charAt(i))){
                distance = s.length()-i;
                break;
            }
        }
        return distance;
    }

}

