package acm;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 @author Alex
 @create 2023-08-26-11:06
 */
public class Main4 {
    // 求黑色部分的面积
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Double r = in.nextDouble();
        Double n = in.nextDouble();
        Double calculate = calculate(r, n);
        BigDecimal b = new BigDecimal(calculate);
        double v = b.setScale(7, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(v);

    }


    public static double calculate(double r,double n){
        if(n==1){
            return  Math.PI * r * r * 3 / 4;
        }else {
            double r1 = r/Math.pow(2,n-1);
//            System.out.println(Math.pow(-1,n));
//            double r2 = r/Math.pow(2,n-2);
            return calculate(r,n-1) - Math.pow(-1,n) * (Math.PI * r1 * r1 * 3 / 4);
        }
    }
}
