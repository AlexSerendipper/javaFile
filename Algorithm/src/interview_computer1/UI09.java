package interview_computer1;

import java.util.Scanner;
import java.util.Stack;


/**
 * 小米9.23，编程第二题
 * 给出一个由小写字母组成的字符串，找出两个相邻且相同的字母，并删除它们。在该字符串上反复执行上述操作，直到无法继续删除
 * 在完成所有重复项删除操作后返回最终的字符串
 *
 * 输入
 * 小写字母组成的字符串
 * dbbdut
 * 输出描述
 * 删除后的字符串
 * ut
 *
 @author Alex
 @create 2023-09-23-14:19
 */
public class UI09 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        Stack<Character> st = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(!st.isEmpty() && chars[i] == st.peek()){
                st.pop();
            }else {
                st.push(chars[i]);
            }
        }
        String ss = "";
        for (int i = 0; i < st.size(); i++) {
            ss = ss + st.get(i);
        }
        System.out.println(ss);
    }
}
