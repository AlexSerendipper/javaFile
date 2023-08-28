package acm;

import java.util.HashSet;
import java.util.Scanner;

/**
 * HJ81 字符串字符匹配：https://www.nowcoder.com/practice/22fdeb9610ef426f9505e3ab60164c93?tpId=37&tqId=21304&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=4
 * 输入描述：
 * 输入两个字符串。第一个为短字符串，第二个为长字符串。两个字符串均由小写字母组成。
 *
 * 输出描述：
 * 如果短字符串的所有字符均在长字符串中出现过，则输出字符串"true"。否则输出字符串"false"。
 *
 * 解析：这并不需要短字符串 按顺序 在长字符串中存在！！！
 *      短字符串中的字符只需要在长字符串中都存在即可
 *      所以不需要双指针，用hashset就好了
 *
 @author Alex
 @create 2023-07-29-23:13
 */
public class HJ81 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String a = in.next();
        String b = in.next();
        char[] shorts = a.toCharArray();
        char[] longs = b.toCharArray();
        HashSet set = new HashSet();
        for(int i=0;i<longs.length;i++){
            set.add(longs[i]);
        }
        for(int i=0;i<shorts.length;i++){
            if(!set.contains(shorts[i])){
                System.out.println(false);
                return;
            }
        }
        System.out.println(true);
    }
}
