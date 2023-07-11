package string;

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

    // （1）构造next数组
    // 其实就是计算模式串s，前缀表的过程。 主要有如下三步：
    // 初始化：定义两个指针i和j，j指向前缀末尾位置，i指向后缀末尾位置。。。✔✔注意,j不仅指向前缀末尾位置，而且表示i之前（包括i）这个子串的最长相等前后缀的长度！！！！！！！！！！！！！！,直接理解为表示i之前（包括i）这个子串的最长相等前后缀的长度就完事了
    // 处理前后缀不相同的情况
    // 处理前后缀相同的情况
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
