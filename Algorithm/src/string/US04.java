package string;

/**
 * 反转字符串中的单词: https://leetcode.cn/problems/reverse-words-in-a-string/
 * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
 * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
 * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
 * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
 *
 * 输入：s = "  hello world  "
 * 输出："world hello"
 * 解释：反转后的字符串中不能存在前导空格和尾随空格。
 *
 * 输入：s = "a good   example"
 * 输出："example good a"
 * 解释：如果两个单词间有多余的空格，反转后的字符串需要将单词间的空格减少到仅有一个。
 *
 * 思考：不使用辅助空间，空间复杂度要求为O(1)
 @author Alex
 @create 2023-06-27-14:20
 */
public class US04 {
    public static void main(String[] args) {

    }

    // 方法2：不使用辅助空间，空间复杂度要求为O(1)（即不使用Java内置方法实现）
    // 所以解题思路如下：
    // 1、移除多余空格
    // 2、将整个字符串反转
    // 3、将每个单词反转
    public String reverseWords(String s){
        // 1.去除首尾以及中间多余空格
        StringBuilder sb = removeSpace(s);
        // 2.反转整个字符串
        reverseString(sb, 0, sb.length() - 1);
        // 3.反转各个单词
        reverseEachWord(sb);
        return sb.toString();
    }

    // 将整个字符串翻转
    private void reverseString(StringBuilder sb, int start, int end) {
        while(start<end){
            char temp = sb.charAt(start);
            sb.setCharAt(start,sb.charAt(end));
            sb.setCharAt(end,temp);
            start++;
            end--;
        }
    }

    // 返回不包含首位空格，并且单词之间只有一个空格
    private StringBuilder removeSpace(String s) {
        int start = 0;
        int end = s.length()-1;
        // 去除首尾空格
        while(s.charAt(start)==' '){start++;}
        while(s.charAt(end)==' '){end--;}

        StringBuilder sb = new StringBuilder();
        while(start<=end){
            char c = s.charAt(start);
            // sb.charAt(sb.length()-1)!=' ', 即可保证了字符串中，若有两个空格，可以只保留一个
            if(c!=' ' || sb.charAt(sb.length()-1)!=' '){
                sb.append(c);
            }
            start++;
        }
        
        return sb;
    }

    // 翻转字符串中的每个单词
    private void reverseEachWord(StringBuilder sb) {
        int start = 0;
        int end = 1;
        int n = sb.length();
        while (start < n) {
            while (end < n && sb.charAt(end) != ' ') {
                end++;
            }
            reverseString(sb, start, end - 1);
            start = end + 1;
            end = start + 1;
        }
    }


    // 方法1：使用split库函数，分隔单词，然后定义一个新的string字符串，最后再把单词倒序相加
    public String reverseWords2(String s) {
        String[] s1 = s.split(" ");
        String str = "";

        // 倒序遍历
        for (int i = s1.length-1; i > 0; i--) {
            if(!s1[i].equals("")) {
                str = str + s1[i] + " ";
            }
        }
        String ss = str.trim();
        return ss;
    }
}
