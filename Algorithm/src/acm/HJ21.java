package acm;


import java.util.Scanner;

/**
 * 现在有一种密码变换算法。
 * 九键手机键盘上的数字与字母的对应： 1--1， abc--2, def--3, ghi--4, jkl--5, mno--6, pqrs--7, tuv--8 wxyz--9, 0--0，
 * 把密码中出现的小写字母都变成九键键盘对应的数字，如：a 变成 2，x 变成 9.
 * 而密码中出现的大写字母则变成小写之后往后移一位，如：X ，先变成小写，再往后移一位，变成了 y ，例外：Z 往后移是 a 。
 * 数字和其它的符号都不做变换。
 *
 * 示例1输入：
 * YUANzhi1987
 * 输出：
 * zvbo9441987
 @author Alex
 @create 2023-08-11-9:15
 */
public class HJ21 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        char[] strs = str.toCharArray();
        StringBuilder sb = new StringBuilder(str.length());

        for (int i = 0; i < strs.length; i++) {
            if((strs[i]+"").matches("[a-z]")){
                if ((strs[i]+"").matches("[abc]")){
                    sb.append("2");
                } else if ((strs[i]+"").matches("[def]")) {
                    sb.append("3");
                }else if ((strs[i]+"").matches("[ghi]")) {
                    sb.append("4");
                }else if ((strs[i]+"").matches("[jkl]")) {
                    sb.append("5");
                }else if ((strs[i]+"").matches("[mno]")) {
                    sb.append("6");
                }else if ((strs[i]+"").matches("[pqrs]")) {
                    sb.append("7");
                }
                else if ((strs[i]+"").matches("[tuv]")) {
                    sb.append("8");
                }else if ((strs[i]+"").matches("[wxyz]")) {
                    sb.append("9");
                }
            }else if((strs[i]+"").matches("[A-Y]")){
                sb.append((char)(strs[i]+32+1));
            }else if((strs[i]+"").matches("[Z]")){
                sb.append('a');
            }else {
                sb.append(strs[i]);
            }
        }

        System.out.println(sb.toString());
    }

}
