package stack_queue;

import java.util.Stack;

/**
 * 删除字符串中的所有相邻重复项: https://leetcode.cn/problems/remove-all-adjacent-duplicates-in-string/
 *
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 @author Alex
 @create 2023-07-03-15:15
 */
public class US04 {
    public static void main(String[] args) {
        String s = "abbaca";
        new US04().removeDuplicates(s);
    }
    public String removeDuplicates(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if(st.isEmpty() || st.peek() != s.charAt(i)){
                st.push(s.charAt(i));
            }else {
                st.pop();
            }
        }

        // 弹出剩余元素
        String str = "";
        int size = st.size();
        for (int i = 0; i < size; i++) {
            Character c = st.pop();
            str  += c;
        }


        // 反装字符串
        char[] chars = str.toCharArray();
        for (int i = 0, j = str.length() - 1; i <=j; i++, j--) {
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }
}
