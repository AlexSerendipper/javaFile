package commonclassexample;

/**
 * 一. 如何理解String类的不可变性
 * 对现有的字符串进行增删改查等操作，不会改变现有的字符串，会在内存中新建一个内存结构
 *
 * 二. StringBuffer和StringBuilder和String的异同
 * 异：1). StringBuffer（JDK1.0, 线程安全，效率低）和 StringBuilder（JDK5.0, 非线程安全，效率高）是可变的字符序列
 *    2). String是不可变的字符序列
 * 同：1). 三者底层都是用char[]存储
 *
 * 三. String s = new String("abc")，在内存中创建了几个对象？
 *     两个。一个是堆空间中new的结构，value对应的常量池中的数据
 *
 * @author Alex
 * @create 2022-11-21-14:58
 */
public class CommonClassExample00 {
}
