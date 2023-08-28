package acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * HJ14 字符串排序: //www.nowcoder.com/practice/5af18ba2eb45443aa91a11e848aa6723?tpId=37&tqId=21237&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 给定 n 个字符串，请对 n 个字符串按照字典序排列。
 * 输入描述：输入第一行为一个正整数n(1≤n≤1000),下面n行为n个字符串(字符串长度≤100),字符串中只含有大小写字母。
 * 输出描述：数据输出n行，输出结果为按照字典序排列的字符串。
 @author Alex
 @create 2023-07-13-9:57
 */
public class HJ14 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String num = in.nextLine();
        String [] strArray = new String[Integer.parseInt(num)];
        for(int i=0;i<Integer.parseInt(num);i++){
            String str = in.nextLine();
            strArray[i] = str;
        }
        Arrays.sort(strArray);
        for(String s:strArray){
            System.out.println(s);
        }
    }
}
