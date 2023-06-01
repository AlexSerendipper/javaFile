package useoop;

import org.junit.Test;

import java.util.Arrays;

/**
 *【方法】
 *  方法 = 成员方法 = 函数 = method
 *  方法是类或对象行为特征的抽象，用来完成某个功能操作。在某些语言中也称为函数或过程。
 *  将功能封装为方法的目的是，可以实现代码重用，简化代码
 *  Java里的方法不能独立存在，所有的方法必须定义在类里。
 *
 *【声明格式】
 * 修饰符 返回值类型 方法名（参数类型 形参1, 参数类型 形参2, ….）｛
 *     方法体程序代码
 *     return 返回值;
 * ｝
 *  修饰符：public,缺省,private, protected等。其他修饰符static/final/abstract修饰
 *  返回值类型：没有返回值：void。有返回值则为相应返回值类型
 *
 * 【调用】
 *  通过方法名被调用，且只有被调用才会执行
 *  方法被调用一次，就会执行一次
 *  没有具体返回值的情况，返回值类型用关键字void表示，那么方法体中可以不必使用return语句。如果使用，仅用来结束方法。
 *  定义方法时，方法的结果应该返回给调用者，交由调用者处理。
 *  方法中只能调用方法或属性，不可以在方法内部定义方法。
 *
 * 【方法的重载】
 *  在同一个类中，允许存在一个以上的同名方法，只要它们的参数个数或者参数类型不同即可。
 *  重载与返回值类型无关，只看参数列表✔，且参数列表必须不同。(参数个数或参数类型)。调用时，根据方法参数列表的不同来区别
 *
 * 【可变形参个数方法】
 *  public static void test(int a ,String[] books);   JDK 5.0以前：采用数组形参来定义方法，传入多个同一类型变量
 *  public static void test(int a ,String…books);    JDK5.0：采用可变个数形参来定义方法，传入多个同一类型变量
 *  可变个数形参的方法与同名的方法之间，彼此构成重载
 *  可变参数方法的使用与方法参数部分使用数组是一致的（二者书写形式不同但其实完全相同
 *  方法的参数部分有可变形参，需要放在形参声明的最后
 *  在一个方法的形参位置，最多只能声明一个可变个数形参
 *
 * 【方法的值传递机制】详见xmind图
 *  形参：方法声明时的参数
 *  实参：方法调用时实际传给形参的参数值
 *  值传递。 即将实际参数值的副本（复制品）传入方法内，而参数本身不受影响
 * ✔形参是基本数据类型：将实参基本数据类型变量的“数据值”传递给形参
 * ✔形参是引用数据类型：将实参引用数据类型变量的“地址值”传递给形参
 *
 * 【递归(recursion)方法】
 *  递归方法：一个方法体内调用它自身
 *
 @author Alex
 @create 2023-01-10-0:06
 */
public class UO02 {
    // 将冒泡排序中的交换次序封装成方法
    // 关键点在于变量的值传递机制。直接传递基本数据类型是错误的
    @Test
    public void test1() {
        int temp;
        int[] array1 = new int[]{43, 32, 76, -98, 0, 64, 33, -21};
        for (int i = 0; i < array1.length - 1; i++) {
            for (int j = 0; j < array1.length - 1 - i; j++) {
                if (array1[j] > array1[j + 1]) {
//					temp = array1[j];
//					array1[j] = array1[j+1];
//					array1[j+1] = temp;
                    Swap(array1, j, j + 1);
                }
            }
        }
        System.out.println(Arrays.toString(array1));
    }

    public void Swap(int[] array1, int i, int j) {
        int temp = array1[i];
        array1[i] = array1[j];
        array1[j] = temp;
    }

    // 变量的值传递机制
    // 编写method方法，打印出a=100,b=200
    @Test
    public void test2(){
        int a = 10;
        int b = 10;
        method(a,b);
        System.out.println("a="+a);
        System.out.println("b="+b);
    }

    public static void method(int x,int y) {
        x = x * 10;
        y = y * 20;
        System.out.println("a="+x);
        System.out.println("b="+y);
        System.exit(0);  // 退出整个程序
    }

}
