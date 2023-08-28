package acm;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * HJ20 密码验证合格程序：https://www.nowcoder.com/practice/184edec193864f0985ad2684fbc86841?tpId=37&tqId=21243&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 密码要求:
 * 1.长度超过8位
 * 2.包括大小写字母.数字.其它符号,以上四种至少三种
 * 3.不能有长度大于2的包含公共元素的子串重复 （注：其他符号不含空格或换行）
 *
 * 输入描述：
 * 一组字符串
 *
 * 输出描述：
 * 如果符合要求输出：OK，否则输出NG
 @author Alex
 @create 2023-07-21-9:14
 */
public class HJ20 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String pwd = in.nextLine();
            if(pwd.length()<=8){
                System.out.println("NG");
                continue;
            }

            // 若有重复子串， 遍历所有长度为3的字符子串
            if(StringRepeat(pwd,0,3)){
                System.out.println("NG");
                continue;
            }

            // 需要包括大小写字母.数字.其它符号,以上四种至少三种
            if(StringRepeat(pwd)){
                System.out.println("NG");
                continue;
            }

            System.out.println("OK");
        }
    }

    private static boolean StringRepeat(String pwd,int l,int r){
        if (r >= pwd.length()) {
            return false;
        }
        if (pwd.substring(r).contains(pwd.substring(l, r))) {
            return true;
        } else {
            return StringRepeat(pwd,l+1,r+1);
        }
    }

    private static boolean StringRepeat(String pwd){
        int count = 0;
        Pattern p1 = Pattern.compile("[A-Z]");
        if(p1.matcher(pwd).find()){
            count++;
        }
        Pattern p2 = Pattern.compile("[a-z]");
        if(p2.matcher(pwd).find()){
            count++;
        }
        Pattern p3 = Pattern.compile("[0-9]");
        if(p3.matcher(pwd).find()){
            count++;
        }
        // 特殊字符
        Pattern p4 = Pattern.compile("[^a-zA-Z0-9]");
        if(p4.matcher(pwd).find()){
            count++;
        }

        if(count >= 3){
            return false;
        }else{
            return true;
        }
    }


}