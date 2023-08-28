package useoop;

import org.junit.Test;

/**
 *【包装类】
 *  针对八种基本数据类型定义相应的引用类型—包装类（封装类）
 *  有了类的特点，就可以调用类中的方法，Java才是真正的面向对象
------------------------------------------------------------------
             基本数据类型                  包装类
                byte                       Byte
                short                      Short
                int                        Integer
                long                       Long
                float                      Float
                double                     Double
                boolean                    Boolean
                char                       Character
------------------------------------------------------------------
 *  手动装箱、拆箱
 *   Integer num2 = new Integer(10)    # 基本数据类型转换为包装类。通过包装类的构造器实现：
 *   .xxxValue()                       # 获得包装类对象中包装的基本类型变量。调用包装类的.xxxValue()方法
 *  ✔JDK1.5之后，支持自动装箱，自动拆箱。但类型必须匹配。
 *   Integer num2 = 10;                # 自动装箱，不需要new，直接赋值
 *   int num3 = num2;                  # 自动拆箱，不需要xxx.value，直接使用
 *  ✔有了自动装箱，自动拆箱。基本数据类型在很多情况下都可以看作是包装类了
 *
 * 【包装类的常用方法】
 *  包装类.parseXXX(binary, 2)            # 二进制转换为十进制，传入二进制字符串binary
 *  包装类.toBinaryString(num);           # 十进制转换为二进制，传入数字
 *
 *【String与包装类的转换】数据类型必须匹配
 *  包装类 + ""                          # 包装类==>String。方法1：连接运算
 *  String.valueOf(包装类)               # 包装类==>String。方法2：如 String.valueOf(Integer)
 *
 *  包装类.parseXXX(String s)            # String==>包装类。方法1：如 Integer.parseInt(String s)
 *                                                                   Integer.parseInt(String s, int radix)，在转换时，可以指定进制呢
 *  new 包装类(String s);                # String==>包装类。方法2：通过包装类的构造器实现
 *
 *
 @author Alex
 @create 2023-01-10-16:13
 */
public class UO10 {
    @Test
    public void test1(){
        Object o1 = true ? new Integer(1) : new Double(2.0);
        // ✔✔✔三元运算符要求输出为同一个类型，所以先进行了自动类型提升，并且string重写过toString方法，故输出1.0
        System.out.println(o1);
        System.out.println("*****************");
        // 没有自动类型提升，故输出1
        Object o2;
        if (true)
            o2 = new Integer(1);
        else
            o2 = new Double(2.0);
        System.out.println(o2);
    }

    @Test
    public void test2(){
        Integer i = new Integer(1);
        Integer j = new Integer(1);
        System.out.println(i == j);  // 引用数据类型肯定比的是地址拉，new出来的地址值不同
        System.out.println("*****************");
        // 知识点1：Integer中有一个IntegerCache方法，预先加载了-128-127的数组,旨在提高效率
        // cache为缓存，所以自动装箱时直接将1的地址值赋给m和n。故地址值相同
        Integer m = 1;
        Integer n = 1;
        System.out.println(m == n);
        System.out.println("*****************");
        Integer x = 128;  // 128不在缓存范围内，所以是new出来的！！！！！
        Integer y = 128;
        System.out.println(x == y);
    }


}
