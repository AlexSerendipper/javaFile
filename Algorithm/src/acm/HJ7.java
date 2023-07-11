package acm;

import java.util.Scanner;

/**
 * 描述
 * 写出一个程序，接受一个正浮点数值，输出该数值的近似整数值。如果小数点后数值大于等于 0.5 ,向上取整；小于 0.5 ，则向下取整。
 * 数据范围：保证输入的数字在 32 位浮点数范围内
 * 输入描述：输入一个正浮点数值
 * 输出描述：出该数值的近似整数值
 @author Alex
 @create 2023-06-30-10:16
 */
public class HJ7 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextFloat()) { // 注意 while 处理多个 case
//            int a = in.nextInt();
//            int b = in.nextInt();
//            System.out.println(a + b);
            float a = in.nextFloat();
            // 截位
            int b = (int) a;
            if(a-b>=0.5){
                System.out.println(b+1);
            }else{
                System.out.println(b);
            }
        }
    }
}
