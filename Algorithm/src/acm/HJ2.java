package acm;

import java.util.Scanner;

/**
 * 计算某字符出现次数：https://www.nowcoder.com/practice/a35ce98431874e3a820dbe4b2d0508b1?tpId=37&tqId=21225&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 写出一个程序，接受一个由字母、数字和空格组成的字符串，和一个字符，然后输出输入字符串中该字符的出现次数。（不区分大小写字母）
 * 输入描述：第一行输入一个由字母、数字和空格组成的字符串，第二行输入一个字符（保证该字符不为空格）。
 * 输出描述：输出输入字符串中含有该字符的个数。（不区分大小写字母）
 @author Alex
 @create 2023-06-24-10:50
 */
public class HJ2 {
    // 没错 用哈希更好其实！时间复杂度更低
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            int a = in.nextInt();
//            int b = in.nextInt();
//            System.out.println(a + b);
//        }

        String str = in.nextLine();
        str.replace(" ","");
        String  c = in.next();
        int count=0;
        for (int i = 0; i < str.length(); i++) {
            char s = str.charAt(i);
            String s1 = String.valueOf(s);
            if(c.equalsIgnoreCase(s1)){
                count++;
            }
        }
        System.out.println(count);
    }
}
