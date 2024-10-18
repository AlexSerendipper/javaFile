package interview_computer1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 2024.3.9 假设美团的工号是由18位数字组成的，由以下规则组成:
 * 1）前面6位代表是哪个部门
 * 2）7-14位代表是出生日期，范围是1900.01.01-2023.12.31
 * 3）15-17位代表是哪个组，不能是完全一样的3位数字
 * 4）18位是一位的校验和，假设是x，则需要满足 (x+a1+a2+as+a4+...+a17) mod 8=1,
 *    a1-a17 代表了前面的17位数字
 * 现在需要写一份代码，判断输入的工号是否符合对应的规则。
 * 提示:出生日期这里需要判断闰年。闰年判断的条件是能被4整除，但不能被100整除 或 能被400整除。
 *
 * 输入描述
 * 第一行输入一个整数n(1 < n< 10)
 * 接下来输入n行字符串，每行代表一个合法部门
 * 如果工号不属于合法部门中，输出error
 * 接下来输入一个整数m(1 < m < 10)
 * 接下来m行，每行输入一个字符串，表示需要验证的工号，
 *
 * 输出描述
 * 如果不满足上述任一个规则，输出"error"，都满足的话输出“ok"
 @author Alex
 @create 2023-08-26-9:02
 */
public class MT02 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String i1 = in.nextLine();
        int n = Integer.parseInt(i1);
        HashSet<String> set = new HashSet<>();
        // 添加部门
        for (int i = 0; i < n; i++) {
            String num = in.nextLine();
            set.add(num);
        }
        String i2 = in.nextLine();
        int m = Integer.parseInt(i2);

        ArrayList<String> list = new ArrayList<>();
        // 验证，这里很搞！如果直接判断然后输出，就读不到下一行了，因为光标定到输出之后的了
        for (int i = 0; i < m; i++) {
            String temp = in.nextLine();
            if(judge(temp,set)){
                list.add(i,"ok");
            }else{
                list.add(i,"error");
            };
        }

        for (String str:list){
            System.out.println(str);
        }
    }

    public static boolean judge(String temp,HashSet set){
        // 长度不合符
        if(temp.length()!=18){
            return false;
        }
        // 判断部门
        String dept = temp.substring(0, 6);
        if(!set.contains(dept)){
            return false;
        }
        // 判断出生日期
        String i3 = temp.substring(6, 14);
        int date = Integer.parseInt(i3);
        if(!judge(date)){
            return false;
        }
        // 判断组
        String t1 = temp.substring(14, 15);
        String t2 = temp.substring(15, 16);
        String t3 = temp.substring(16, 17);
        if(t1.equals(t2) && t2.equals(t3)){
            return false;
        }
        // 判断校验和
        int sum=0;
        for (int j = 0; j < 18; j++) {
            String ttemp = temp.substring(j, j+1);
            int i4 = Integer.parseInt(ttemp);
            sum = sum+i4;
        }
        if(sum%8!=1){
            return false;
        }
        return true;
    }
    // 判断年份是否合法
    public static boolean judge(int date){
        int year = date/10000;
        int month = (date%10000)/100;
        int day = date % 100;

        // 正常月份的合法天数
        int[] arr = {31,28,31,30,31,30,31,31,30,31,30,31};



        // 判断平闰年
        if((year%4)==0 && year%100!=0||(year%400==0)){
            arr[1]=29;  // 闰年2月29天
        }
        // 判断年份范围
        if(year>=1900 && year<=2023){
            if(month>0 && month<13){
                if(day<=arr[month-1] && day>0){
                    return true;  // 合法
                }
            }
        }
        return false;

    }

}
