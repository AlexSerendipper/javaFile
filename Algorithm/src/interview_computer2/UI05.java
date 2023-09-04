package interview_computer2;

import java.util.Scanner;

/**
 * 23/9/2美团测试开发工程师第一题
 * 已知各种进制的前缀如下
 * 0b/0B是二进制的前缀
 * 0下八进制的前缀
 * 十进制无前缀
 * 0x或者0X是十六进制的前缀
 *
 * 我们现在输入是一个由英文字母和数字组成的字符串，字等串里可能是不同的进制，
 * 请输出这个字符串转化后代表的十进制数字如果转化失败的话，输出error。
 *
 * 输入：0b1
 * 输出：1
 *
 @author Alex
 @create 2023-09-02-19:18
 */
public class UI05 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        String substr = "";
        // 可能是二进制或者16进制或者错误或者8进制或者十进制
        try {
            if(str.length()>=3){
                substr = str.substring(0, 2);
                if(substr.equals("0b") || substr.equals("0B")){
                    System.out.println(Integer.parseInt(str.substring(2,str.length()),2));
                }else if(substr.equals("0x")||substr.equals("0X")){
                    System.out.println(Integer.parseInt(str.substring(2,str.length()),16));
                }else if(str.charAt(0)=='0'){
                    System.out.println(Integer.parseInt(str.substring(1,str.length()),8));
                }else if(str.matches("\\d+")){
                    System.out.println(Integer.parseInt(str));
                }
            }else {
                // 只能是八进制、十进制或者错误
                if(str.charAt(0)=='0'){
                    System.out.println(Integer.parseInt(str.substring(1,str.length()),8));
                }else if(str.matches("\\d+")){
                    System.out.println(Integer.parseInt(str));
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("error");
        }


    }
}
