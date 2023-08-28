package acm;

import java.util.*;

/**
 * 描述：
 * 定义一个单词的 “兄弟单词” 为：交换该单词字母顺序（注：可以交换任意次），而不添加、删除、修改原有的字母就能生成的单词。
 * 兄弟单词要求和原来的单词不同。例如： ab 和 ba 是兄弟单词。 ab 和 ab 则不是兄弟单词。
 * 现在给定你 n 个单词，另外再给你一个单词 x ，让你寻找 x 的兄弟单词里，按字典序排列后的第 k 个单词是什么？
 * 注意：字典中可能有重复单词。
 *
 * 输入描述：输入只有一行。 先输入字典中单词的个数n，再输入n个单词作为字典单词。 然后输入一个单词x 最后后输入一个整数k
 * 输出描述：第一行输出查找到x的兄弟单词的个数m 第二行输出查找到的按照字典顺序排序后的第k个兄弟单词，没有符合第k个的话则不用输出
 *
 * 输入：6 cab ad abcd cba abc bca abc 1
 * 输出：
 * 3
 * bca
 * 说明：abc的兄弟单词有cab cba bca，所以输出3
 *      经字典序排列后，变为bca cab cba，所以第1个字典序兄弟单词为bca
 @author Alex
 @create 2023-08-18-7:42
 */
public class HJ27 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] sss = in.nextLine().split(" ");

        int k = Integer.parseInt(sss[sss.length - 1]);  // 第k个
        String target = sss[sss.length-2];  // 单词x


        ArrayList<String> dictWord = new ArrayList<>();// 用于存储字典单词

        for (int i = 1; i < sss.length - 2; i++) {
            ArrayList<Character> chars = new ArrayList<>();
            // 思路是把字典单词所有字符加到set中，若target有相同的字符则删，无相同的字符则增
            // 上述思路有问题，比如target为"dacbb"，字典单词为"acd"，此时操作后dictWord仍为空，因为b和b和约掉
            // 所以思路应改为，把长的字符串先加到set中，用短的字符串去删，如果能删光代表正确
            String l = sss[i].length()>target.length()?sss[i]:target;  // 长的
            String s = sss[i].length()>target.length()?target:sss[i];  // 短的

            for (Character c : l.toCharArray()) {
                chars.add(c);
            }

            for (Character c : s.toCharArray()) {
                if(chars.contains(c)){
                    chars.remove(c);
                }else {
                    chars.add(c);
                }
            }

            // 注意字典单词中可能包含与target相同的元素
            if(chars.isEmpty() && !sss[i].equals(target)){
                dictWord.add(sss[i]);
            }
        }

        System.out.println(dictWord.size());
        // string默认实现了排序
        Collections.sort(dictWord);
        if(dictWord.size()>=k){
            System.out.println(dictWord.get(k-1));  // 索引，所以是第k-1个元素
        }else {
            System.out.println(" ");
        }
    }
}
