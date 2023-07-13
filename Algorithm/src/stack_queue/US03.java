package stack_queue;

import java.util.Stack;

/**
 * 【有效的括号】https://leetcode.cn/problems/valid-parentheses/
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 *
 *
 @author Alex
 @create 2023-07-03-14:47
 */
public class US03 {
    public static void main(String[] args) {
        String s = "()[]{}";
        new US03().isValid(s);
    }
    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();

        // 如果s的长度为奇数，一定不符合要求
        if (st.size() % 2 != 0) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i)=='('){
                st.push(')');
            }else if(s.charAt(i)=='{'){
                st.push('}');
            }else if(s.charAt(i)=='['){
                st.push(']');
            }
            // 第三种情况：遍历字符串匹配的过程中，栈已经为空了，没有匹配的字符了，说明右括号没有找到对应的左括号 return false
            // 第二种情况：遍历字符串匹配的过程中，发现栈里没有我们要匹配的字符。所以return false
            else if(st.isEmpty() || s.charAt(i)!=st.peek()){
                return false;
            }

            else if(s.charAt(i)==st.peek()){
                st.pop();
            }
        }
        // 第一种情况：此时我们已经遍历完了字符串，但是栈不为空，说明有相应的左括号没有右括号来匹配，所以return false，否则就return true
        return st.isEmpty();
    }
}
