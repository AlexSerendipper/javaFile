package acm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * HJ34 图片整理：https://www.nowcoder.com/practice/2de4127fda5e46858aa85d254af43941?tpId=37&tqId=21257&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * Lily上课时使用字母数字图片教小朋友们学习英语单词，每次都需要把这些图片按照大小（ASCII码值从小到大）排列收好。请大家给Lily帮忙，通过代码解决。
 * Lily使用的图片使用字符"A"到"Z"、"a"到"z"、"0"到"9"表示。
 *
 *
 * 输入描述：一行，一个字符串，字符串中的每个字符表示一张Lily使用的图片。
 *
 * 输出描述：Lily的所有图片按照从小到大的顺序输出
 *
 * 示例1
 * Ihave1nose2hands10fingers
 *
 * 输出：
 * 0112Iaadeeefghhinnnorsssv
 @author Alex
 @create 2023-08-20-8:01
 */
public class HJ34 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        // 将char数组转换为character类型数组
        char[] chars = str.toCharArray();
        Character[] chs = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            chs[i] = chars[i];
        }

        // 定制排序
        Arrays.sort(chs, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return Integer.compare((int)o1,(int)o2);
            }
        });

        // character数组是对象，如果直接打印toString是地址值
        String s = "";
        for (int i = 0; i < chs.length; i++) {
            s+=chs[i];
        }
        System.out.println(s);

//        for (int i = 0; i < asciiArr.length; i++) {
//            System.out.println("ASCII value of " + str.charAt(i) + " is " + asciiArr[i]);
//        }


    }

}
