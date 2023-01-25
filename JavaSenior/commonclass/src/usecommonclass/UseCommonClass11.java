package usecommonclass;

import org.junit.Test;

/**
 *【Math类】java.lang.Math提供了一系列静态方法用于科学计算。其方法的参数和返回值类型一般为double型
 * abs                          # 绝对值
 * acos,asin,atan,cos,sin,tan   # 三角函数
 * sqrt                         # 平方根
 * pow(double a,doble b)        # a的b次幂
 * log                          # 自然对数
 * exp                          # e为底指数
 * max(double a,double b)
 * min(double a,double b)
 * random()                     #  返回0.0到1.0的随机数
 * long round(double a)         #  double型数据a转换为long型（四舍五入）
 * toDegrees(double angrad)     #  弧度—>角度
 * toRadians(double angdeg)     # 角度—>弧度
 *
 * 【scanner类】
 *  Scanner scanner = new Scanner(System.in);   # 获取键盘输入
 *  scanner.nextInt();                          # 获取输入整数数据
 @author Alex
 @create 2023-01-04-18:47
 */

public class UseCommonClass11 {
    @Test
    public void test() {
        double num = Math.sqrt(49);
        System.out.println(num);
    }
}
