package acm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * HJ38：https://www.nowcoder.com/practice/2f6f9339d151410583459847ecc98446?tpId=37&tqId=21261&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 求小球落地5次后所经历的路程和第5次反弹的高度
 * 描述
 * 假设一个球从任意高度自由落下，每次落地后反跳回原高度的一半; 再落下, 求它在第5次落地时，共经历多少米?第5次反弹多高？
 *
 * 数据范围：输入的小球初始高度满足1≤n≤1000，且保证是一个整数
 *
 * 输入描述：
 * 输入起始高度，int型
 *
 * 输出描述：
 * 分别输出第5次落地时，共经过多少米以及第5次反弹多高。
 * 注意：你可以认为你输出保留六位或以上小数的结果可以通过此题。
 *
 @author Alex
 @create 2024-03-07-13:08
 */
public class HJ38 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double height = sc.nextDouble();  // 高度
        double Length = 0;  // 路程（距离）

        double h = height;
        for (int i = 0; i < 5; i++) {
            ArrayList<Double> arr = ball(h);
            Double L = arr.get(0);
            h = arr.get(1);
            height = h;
            Length = Length + L;
        }
        System.out.println(Length-height);  // 因为求的是最后一次落地的距离，要减去最后一次的height，写的是回到初始状态所经历的长度
        System.out.println(height);
    }

    // 一次落地，并回到初始状态
    public static ArrayList<Double> ball(double height){
        double L = height + height/2;
        double h = height/2;
        ArrayList<Double> arr = new ArrayList<>();
        arr.add(L);
        arr.add(h);
        return arr;
    }
}
