package acm;

import java.util.Scanner;

/**
 * 对字符串中的所有单词进行倒排。
 * 说明：
 * 1、构成单词的字符只有26个大写或小写英文字母；
 * 2、非构成单词的字符均视为单词间隔符；
 * 3、要求倒排后的单词间隔符以一个空格表示；如果原字符串中相邻单词间有多个间隔符时，倒排转换后也只允许出现一个空格间隔符；
 * 4、每个单词最长20个字母；
 *
 * 输入描述：输入一行，表示用来倒排的句子
 * 输出描述：输出句子的倒排结果
 *
 * 输入：I am a student
 * 输出：student a am I
 *
 * 输入：$bo*y gi!r#l
 * 输出：l r gi y bo
 @author Alex
 @create 2023-08-19-11:01
 */

public class HJ31 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        String[] strs = str.split("[^a-zA-Z]+");
        StringBuilder sb = new StringBuilder();
        for (int i = strs.length-1; i >=0; i--) {
            sb.append(strs[i]).append(" ");
        }

        System.out.println(sb.toString().trim());
    }
}
