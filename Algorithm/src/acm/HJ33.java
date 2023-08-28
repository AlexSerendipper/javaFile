package acm;

import java.util.Scanner;

/**
 * HJ33 整数与IP地址间的转换:https://www.nowcoder.com/practice/66ca0e28f90c42a196afd78cc9c496ea?tpId=37&tqId=21256&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D1%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 原理：ip地址的每段可以看成是一个0-255的整数，把每段拆分成一个二进制形式组合起来，然后把这个二进制数转变成一个长整数。
 * 举例：一个ip地址为10.0.3.193
 * 每段数字             相对应的二进制数
 * 10                   00001010
 * 0                    00000000
 * 3                    00000011
 * 193                  11000001
 * 组合起来即为：00001010 00000000 00000011 11000001,转换为10进制数就是：167773121，即该IP地址转换后的数字就是它了。
 *
 * 输入描述：
 * 1 输入IP地址
 * 2 输入10进制型的IP地址
 *
 * 输出描述：
 * 1 输出转换成10进制的IP地址
 * 2 输出转换后的IP地址
 *
 * 示例输入：
 * 10.0.3.193
 * 167969729
 * 输出：
 * 167773121
 * 10.3.3.193
 *
 @author Alex
 @create 2023-08-19-11:51
 */
public class HJ33 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String str1 = in.nextLine();
            String str2 = in.nextLine();

            // part1: 转换为十进制
            String[] str1s = str1.split("\\.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str1s.length; i++) {
                int num = Integer.parseInt(str1s[i]);
                String binaryString = Integer.toBinaryString(num); // 内置方法，十进制转换为二进制
                while(binaryString.length()<8){
                    binaryString = "0"+binaryString;
                }
                sb.append(binaryString);
            }

            String binary = sb.toString();
            Long decimal = Long.parseLong(binary, 2);  // 内置方法，二进制转换为十进制
            System.out.println(decimal);


            // part2：转换为ip地址
            String s = Long.toBinaryString(Long.parseLong(str2));
            while(s.length()<32){
                s = "0"+s;
            }
            StringBuilder stringBuilder = new StringBuilder();
//            System.out.println(s);
            for (int i = 0; i < s.length(); i=i+8) {
                if(i==24){
                    int d = Integer.parseInt(s.substring(i, i + 8), 2);  // 内置方法，二进制转换为十进制
                    stringBuilder.append(d);
                }else{
                    int d = Integer.parseInt(s.substring(i, i + 8), 2);  // 内置方法，二进制转换为十进制
                    stringBuilder.append(d+".");
                }
            }
            System.out.println(stringBuilder.toString());

        }
    }
}
