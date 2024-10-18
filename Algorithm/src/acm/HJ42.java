package acm;

import java.util.Scanner;

/**
 * HJ42 学英语
 * 描述
 * Jessi初学英语，为了快速读出一串数字，编写程序将数字转换成英文：
 *
 * 具体规则如下:
 * 1.在英语读法中三位数字看成一整体，后面再加一个计数单位。从最右边往左数，三位一单位，例如12,345 等
 * 2.每三位数后记得带上计数单位 分别是thousand, million, billion.
 * 3.公式：百万以下千以上的数 X thousand X, 10亿以下百万以上的数：X million X thousand X, 10 亿以上的数：X billion X million X thousand X. 每个X分别代表三位数或两位数或一位数。
 * 4.在英式英语中百位数和十位数之间要加and，美式英语中则会省略，我们这个题目采用加上and，百分位为零的话，这道题目我们省略and
 *
 * 下面再看几个数字例句：
 * 22: twenty two
 * 100:  one hundred
 * 145:  one hundred and forty five
 * 1,234:  one thousand two hundred and thirty four
 * 8,088:  eight thousand (and) eighty eight (注:这个and可加可不加，这个题目我们选择不加)
 * 486,669:  four hundred and eighty six thousand six hundred and sixty nine
 * 1,652,510:  one million six hundred and fifty two thousand five hundred and ten
 *
 * 说明：
 * 数字为正整数，不考虑小数，转化结果为英文小写；
 * 保证输入的数据合法
 * 关键字提示：and，billion，million，thousand，hundred。
 *
 * 数据范围：1≤n≤2000000
 *
 *
 * 输入描述：
 * 输入一个long型整数
 *
 * 输出描述：
 * 输出相应的英文写法
 @author Alex
 @create 2024-03-14-13:00
 */
public class HJ42 {
    public static String[] ones = new String[]{"zero","one","two","three","four","five","six","seven","eight","nine"};  // 0-9
    public static String[] tens = new String[]{"ten","eleven","twelve","thirteen","forteen","fifteen","sixteen","seventeen","eighteen","nineteen"};  // 10-19
    public static String[] twieties = new String[]{"zero","ten","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"};  // 20-99
    public static int[] range = new int[]{(int)1e2,(int)1e3,(int)1e6,(int)1e9,(int)1e12};  // 100百，1000千，1000000百万，1000000000十亿....
    public static String[] ranges = new String[]{"hundred","thousand","million","billion"};

    public static void main(String[] args){
        System.out.println(120/100);
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int num = sc.nextInt();
            System.out.println(transfer(num));
        }
    }
    public static String transfer(int num){
        if(num <= 9)return ones[num];  // 0-9

        if(num <= 19)return tens[num%10];  // 10-19

        if(num <= 99)return twieties[num/10] + (num%10 == 0 ? "" : " " + ones[num%10]);  // 20-99，这里就是 十位 加上 个位

        for(int i = 0; i<4; i++){
            if(num < range[i + 1]){
                // java中整数除法会舍弃小数部分，只剩下整数部分。所以这里transfer(num/range[i])就是按照三位三位输出
                // 1. 如果数字小于thousand，那就让它除以100，保留百位数字进行后续处理
                //    如果数字小于million,那就让他除以1000，保留前三位数字进行后续处理
                // num % range[i] == 0的情况是刚好的100，1000整数的情况
                // 2. 如果刚好是整数，输出中止
                // i==0说明是百，需要加上and
                // 3. 对于剩下的数字进行下一轮判断，所以用余数的形式~
                return transfer(num/range[i]) + " " + ranges[i] + (num % range[i] == 0? " " :(i!=0?" " : " and ") + transfer(num % range[i]));
            }
        }
        return "";
    }
}
