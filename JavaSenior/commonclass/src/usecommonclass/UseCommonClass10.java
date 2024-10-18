package usecommonclass;

import org.junit.Test;

import java.util.Scanner;

/**
 *【Math类】java.lang.Math提供了一系列静态方法用于科学计算。其方法的参数和返回值类型一般为double型
 *  abs                          # 绝对值
 *  acos,asin,atan,cos,sin,tan   # 三角函数
 *  sqrt                         # 平方根
 *  pow(double a,doble b)        # a的b次幂
 *  log                          # 自然对数
 *  exp                          # e为底指数
 *  max(double a,double b)
 *  min(double a,double b)
 *  random()                     #  返回0.0到1.0的随机数
 *  long round(double a)         #  double型数据a转换为long型（四舍五入）
 *  toDegrees(double angrad)     #  弧度—>角度
 *  toRadians(double angdeg)     # 角度—>弧度
 *
 * 【scanner类】
 *  Scanner scanner = new Scanner(System.in);   # 获取键盘输入。
 *  scanner.hasNextInt()                        # 判断控制台接收是否为整型，返回布尔型数据，如果是，再执行其他操作！
 *  scanner.nextInt();                          # 获取输入整数数据。输入读取完之后，会将光标放在读取数字后面，并且是同一行！！！
 *  scanner.hasNext()                           # 判断控制台接收的字符串接下来是否有非空字符！！！（注意输入的最末默认是含有\n的！！因为我们也是按enter输入的），如果有则返回TRUE
 *                                                  所以通常使用该方法进行循环输入，如输入【7 15 9 5】当处理完9后，仍会处理5，并停留在5后的/n之前，进入等待读取状态
 *  scanner.next();                             # 获取输入字符串数据，以空格、换行符为结束标记✔✔✔输入读取完之后，会将光标放在读取 结束标记 之前✔✔✔
 *                                                  next()方法无法读取空格和/n，若输入数据以空格或/n开头，会被忽略
 *  scanner.hasNextLine()                       # 判断控制台接收的字符串是否有下一行（使用特殊的行匹配模式），返回布尔型数据，这里并不会把光标定到下一行
 *  scanner.nextLine()；                        # 读取本行的的输入，仅以换行符为结束标记✔✔✔（注意输入的最末默认是含有\n的！！）。读取输入后，将光标定位在下一行（即/n之后）✔✔✔
 *
 * 注意点0：应默认所有的数据输入，最后都是有一个换行符。
 * 注意点1：建议如果用了nextXXX(),那么后边一定要用nextXXX()。。。如果此时用nextLine()，会出现第一次使用nextLine()方法获取到的是空数据
 * 注意点2：建议如果用 nextLine()，就一直使用nextLine()
 * 注意点3：关于循环输入问题
--------------------
例1：使用.next()方法能方便读取数据
 public static void main(String[] args) {
     Scanner in = new Scanner(System.in);
     int n = in.nextInt();
     while(n>0){
         String test = in.next();
         System.out.println("test is :"+test);
         n--;
     }
 }
输入：2 a b
输出: test is :a
      test is :b
讲解1：（1）in.nextInt将读入的数字初始化给n，现在光标在"2"和"空格a空格b换行"之间
      （2）in.next()方法读取光标之后的"空格a",由于空格是结束标记✔，所以遇到第二个空格就停止读取✔，并且第一个空格被忽略。此时光标在第二个空格之前
      （3）以同样的方式读入b读取后，光标置于b后
--------------------
例2：.next()与.nextLine()方法混用问题
public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    while(n>0){
        String test = in.nextLine();
        System.out.println("test is :"+test );
        n--;
    }
}

输入：2
      3
输出: test is :
讲解： （1）in.nextInt将读入的数字初始化给n，现在光标在"2"和"\n"之间
       （2）接下来，in.nextLine()将读取内容，直到结束符 \n，由于当前光标后马上就是/n，所以因为在控制台中显示为空，并不会输出3
--------------------
例3：循环输入该使用.hasNextLine()还是.hasNext()呢
while (in.hasNextLine()) {
    String s = in.nextLine();
    System.out.println(s);
}
如 输入1
       2
此处想 按行输出1
              2
第一次读取时，光标在1的上一行，下一行存在数据（即1这一行）故输出1，并将光标移动到1\n之后，即2之前
第二次循环，由于此时2处的下一行已经没有数据，故没法输出2
 【此处想实现上述效果应使用while (in.hasNext()✔】第一次读取在1之前，接下来有非空字符串故读取1，光标在 /n2 之前，接下来还存在非空字符串，故继续读取2
------------
例4：循环输入，测评中若进入等待状态，是会跳出循环的
while (in.hasNext()) {
    String s = in.next();
    System.out.println(s);
}
System.out.println("down");
      输入：2
            3
      输出: 2
            3
✔✔实际上"down是不会被输出的"，但是在测评的acm模式中，如果进入等待状态，是会跳出循环的，也就是测评中是会输出down的
------------

 @author Alex
 @create 2023-01-04-18:47
 */

public class UseCommonClass10 {
    // math类测试
    @Test
    public void test() {
        double num = Math.sqrt(49);
        System.out.println(num);
    }

    /**
     请求：第一行输入矩阵大小，接着输入矩阵的元素，把对应元素存入二维矩阵中！
     5 5
     1 2 3 4 5
     16 17 18 19 6
     15 24 25 20 7
     14 23 22 21 8
     13 12 11 10 9
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int R = scanner.nextInt();  // 读取第一个元素，光标在5之后
        int C = scanner.nextInt();  // 读取第二个元素，光标在5之后
        int[][] matrix = new int[R][C];  // 二维矩阵

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                matrix[i][j] = scanner.nextInt();  // 每次读取一个元素，赋值给矩阵
            }
        }
        System.out.println(matrix);
    }
}

