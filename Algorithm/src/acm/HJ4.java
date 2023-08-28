package acm;
import java.util.Scanner;
/**
 * 字符串分隔：https://www.nowcoder.com/practice/d9162298cb5a437aad722fccccaae8a7?tpId=37&tqId=21227&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 输入一个字符串，请按长度为8拆分每个输入字符串并进行输出；
 * 长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。
 * 输入：abc
 * 输出：abc00000
 @author Alex
 @create 2023-06-26-10:42
 */
public class HJ4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            int a = in.nextInt();
//            int b = in.nextInt();
//            System.out.println(a + b);
//        }
        String str = in.next();
        for (int i = 0; i < str.length(); i+=8) {
            // 剩余字符长度大于等于8
            if(str.length() - i >= 8){
                String substring = str.substring(i, i + 8);
                System.out.println(substring);
            // 剩余字符不足8个
            }else{
                String substring = str.substring(i, str.length());
                // 差几个到8个
                int m = 8 - (str.length() - i);
                for (int j = 0; j < m; j++) {
                    substring += "0";
                }
                System.out.println(substring);
            }
        }
    }
}
