package acm;

import java.util.Scanner;

/**
 * HJ11 数字颠倒：https://www.nowcoder.com/practice/ae809795fca34687a48b172186e3dafe?tpId=37&tqId=21234&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 输入一个整数，将这个整数以字符串的形式逆序输出
 * 程序不考虑负数的情况，若数字含有0，则逆序形式也含有0，如输入为100，则输出为001
 @author Alex
 @create 2023-07-05-10:59
 */
public class HJ11 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            String b = a+"";
            char[] c = b.toCharArray();
            // 双指针法
            for(int i=0,j=c.length-1; i<=j; i++,j--){
                char temp = c[i];
                c[i] = c[j];
                c[j] = temp;
            }
            System.out.println(new String(c));
        }
    }
}
