package string;

/**
 * 反转字符串：https://leetcode.cn/problems/reverse-string/
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 *
 @author Alex
 @create 2023-06-25-9:58
 */
public class US01 {
    public static void main(String[] args) {
        char[] s = new char[]{'h','e','l','l','o'};
        new US01().reverseString(s);
    }


    public void reverseString(char[] s) {
        for (int i = 0,j=s.length-1; i <= j; i++,j--) {
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
}
