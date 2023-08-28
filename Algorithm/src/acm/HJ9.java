package acm;

import java.util.HashSet;
import java.util.Scanner;

/**
 * 提取不重复的整数：https://www.nowcoder.com/practice/253986e66d114d378ae8de2e6c4577c1?tpId=37&tqId=21232&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 *
 * 输入一个 int 型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。保证输入的整数最后一位不是 0 。
 * 输入：9876673
 * 输出：37689
 @author Alex
 @create 2023-07-02-11:06
 */
public class HJ9 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            String b = a + "";
            HashSet set = new HashSet();
            String result="";
            for(int i=b.length()-1;i>=0;i--){
                boolean x = set.add(b.charAt(i));
                if(x){
                    result += b.charAt(i);
                }
            }
            System.out.println(Integer.parseInt(result));
        }
    }
}
