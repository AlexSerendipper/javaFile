package acm;

import java.util.Scanner;

/**
 * Catcher是MCA国的情报员，他工作时发现敌国会用一些对称的密码进行通信，比如像这些ABBA，ABA，A，123321，
 * 但是他们有时会在开始或结束时加入一些无关的字符以防止别国破解。
 * 比如进行下列变化 ABBA->12ABBA,ABA->ABAKK,123321->51233214　。
 * 因为截获的串太长了，而且存在多种可能的情况（abaaab可看作是aba,或baaab的加密形式），
 * Cathcer的工作量实在是太大了，他只能向电脑高手求助，你能帮Catcher找出最长的有效密码串吗？
 *
 *
 * 输入描述：
 * 输入一个字符串（字符串的长度不超过2500）
 *
 * 输出描述：
 * 返回有效密码串的最大长度
 @author Alex
 @create 2023-08-19-11:15
 */


// 运行超时，利用动态规划解决应该比较好，但还没学过
public class HJ32______________ {
    // 对称的字符串，即翻转后与原字符串相同
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.nextLine();
        int len = 0;  // 记录最长密码长度
        // 判断所有的可能性，判断是否为对称字符串
        for (int i = 0; i < s.length() - 2; i++) {
            for (int j = i+2; j < s.length() + 1; j++) {
                String subStr = s.substring(i, j);
                if(subStr.length()>len){
                    if(isSymString(subStr)){
                        len = subStr.length()>len?subStr.length():len;
                    }
                }
            }
        }
        System.out.println(len);
    }

    /**
     * 判断是否为对称字符串
     * @param str
     * @return
     */
    public static boolean isSymString(String str){
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        if(str.equals(sb.toString())){
            return true;
        }else {
            return false;
        }
    }
}
