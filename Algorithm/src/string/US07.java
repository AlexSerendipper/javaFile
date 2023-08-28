package string;

import org.junit.Test;

/**
 * 重复的子字符串：https://leetcode.cn/problems/repeated-substring-pattern/
 * 给定一个非空的字符串 s ，检查是否可以通过由它的一个子串重复多次构成。
 *
 * 输入: s = "abab"
 * 输出: true
 * 解释: 可由子串 "ab" 重复两次构成。
 *
 @author Alex
 @create 2023-06-30-9:44
 */
public class US07 {
    public static void main(String[] args) {
        String s = "abab";
        new US07().repeatedSubstringPattern(s);
    }


    // KMP算法解决，可以看看视频，其实还是算简单的
    // 实际上。最长相等前后缀不包含的子串就是最小重复子串
    // 如果len % (len - (next[len - 1]) == 0 ，则说明数组的长度正好可以被 (数组长度-最长相等前后缀的长度) 整除 ，说明该字符串有重复的子字符串。
    public boolean repeatedSubstringPattern(String s) {
        int[] next = new int[s.length()];
        getNext(next,s);
        int len = s.length();
        // 若next[len-1]==0,则是一定没有重复子串滴
        if(next[len-1]!=0 && len % (len - (next[len-1]))==0){
            return true;
        }
        return false;
    }



/**
 // 前缀：所有包含首字母，不包含尾字母的子串都称为前缀
 // 后缀：所有包含尾字母，不包含首字母的子串都称为后缀
 // 如现在有一个问题，即问文本串aabaabaaf 中，是否出现了模式串aabaaf
------------------------------------------
如aabaaf，求所有子串的最长相等前后缀
a => 0 (没有前缀也没有后缀)
aa => 1
aab ==> 0（没有与前缀相等的后缀）
aaba ==> 1
aabaa ==> 2（前后缀最长相等的长度为2）
aabaaf ==> 0
------------------------------------------
如此一来，我们便得到了模式串aabaaf的前缀表：010120
文本串aabaabaaf
模式串aabaaf
      010120
当匹配到f时，出现冲突，此时查询冲突的前一位的前缀表为2，代表着后缀子串aa的前面有一个与之相等的前缀aa
我们只需要从与之相等的前缀的后边开始匹配即可，即从b开始匹配即可
 */
// 如何使用KMP解决上述文本串与模式串匹配的问题(感觉可能就是String.contain()的底层)
@Test
public void test(){
    String s1= "aabaabaaf";
    String s2= "aabaaf";
    int[] next = new int[s2.length()];
    getNext(next,s2);
}


    // 构造next数组（前缀表。 主要有如下三步）：
    // （1）初始化：定义两个指针i和j，j指向前缀末尾位置，i指向后缀末尾位置。
    // ✔✔注意,j不仅指向前缀末尾位置，而且表示i之前（包括i）这个子串的最长相等前后缀的长度！！直接理解为表示i之前（包括i）这个子串的最长相等前后缀的长度就完事了！！
    // （2）处理前后缀不相同的情况
    // （3）处理前后缀相同的情况
    // （4）更新Next数组的值
    private static void getNext(int[] next, String s) {
        int j = 0;
        next[0] = 0;
        // 不相同
        for (int i = 1; i <= s.length()-1; i++) {
            while(j>0 && s.charAt(i)!=s.charAt(j)){
                j = next[j-1];
            }

            // 相同
            if(s.charAt(i)==s.charAt(j)){
                j++;
            }

            next[i] = j;
        }
    }
}
