package stack_queue;

import java.util.Stack;

/**
 * 给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法 表示的算术表达式。
 * 请你计算该表达式。返回一个表示表达式值的整数。
 *
 * 注意：
 * 有效的运算符为 '+'、'-'、'*' 和 '/' 。
 * 整数除法只保留整数部分。 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
 *
 * 逆波兰表达式：是一种后缀表达式，所谓后缀就是指运算符写在后面。
 * 平常使用的算式则是一种中缀表达式，如 ( 1 + 2 ) * ( 3 + 4 ) 。
 * 该算式的逆波兰表达式写法为 ( ( 1 2 + ) ( 3 4 + ) * ) 。
 *
 * 逆波兰表达式主要有以下两个优点：
 * 去掉括号后表达式无歧义，上式即便写成 1 2 + 3 4 + * 也可以依据次序计算出正确结果。
 * 适合用栈操作运算：遇到数字则入栈；遇到运算符则取出栈顶两个数字进行计算，并将结果压入栈中。
 *
 @author Alex
 @create 2023-07-04-8:34
 */
public class US05 {
    public static void main(String[] args) {
        int i = new US05().evalRPN(new String[]{"4", "13", "5", "/", "+"});
        System.out.println(i);
    }

    // leetcode 内置jdk 有问题，不能使用==判断字符串是否相等
    public int evalRPN(String[] tokens) {
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")){
                int num1 = st.pop();
                int num2 = st.pop();
                switch (tokens[i]){
                    case "+":
                        st.push(num1+num2);
                        break;
                    case "-":
                        st.push(num2-num1);
                        break;
                    case "*":
                        st.push(num1*num2);
                        break;
                    case "/":
                        st.push(num2/num1);
                        break;

                }
            }else {
                st.push(Integer.parseInt(tokens[i]));
            }
        }
        return st.pop();
    }
}
