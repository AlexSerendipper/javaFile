package usecommonclass;

import org.junit.Test;

/**
 * 【关于StringBuffer和StringBuilder的使用】
 *  StringBuffer和StringBuilder代表可变的字符序列，可以对字符串内容进行增删，此时不会产生新的对象。
 *  其中StringBuffer(JDK1.0)：可变字符序列、效率低、线程安全
 *    StringBuilder(JDK 5.0)：可变字符序列、效率高、线程不安全
 *
 * 【源码分析】详见StringBufferJDK8.java
 *  String str = new String()                      // 底层是new char[0]
 *  String str1 = new String("abc")                // 底层是new char[]{'a','b','c'}
 *  StringBuffer str2 = new StringBuffer();        // 底层是new char[16], 可以看源码的空参构造器
 *  str2.append('a');                              // value[0] = 'a';
 *  StringBuffer str3 = new StringBuffer("abc");   // 底层是new char["abc".length() + 16], 可以看源码的带参构造器
 *
 * 【StringBuffer中的常用方法（stringbuilder同）】
 *  StringBuffer append(xxx)：                                  # ✔提供了很多的append()方法，用于进行字符串拼接
 *  StringBuffer delete(int start,int end)：                    # ✔删除指定位置的内容（左闭右开）
 *  StringBuffer replace(int start, int end, String str)：      #  把[start,end)位置替换为str
 *  StringBuffer insert(int offset, xxx)：                      # ✔在指定位置插入xxx
 *  StringBuffer reverse() ：                                   # ✔ 把当前字符序列逆转
 *  public int indexOf(String str)：                            # 返回指定子字符串在此字符串中第一次出现处的索引
 *  public String substring(int start,int end)：                # 返回一个[start,end)的子字符串，这个要接受返回值，并不是直接在原数据上修改
 *  public int length()
 *  public char charAt(int n )：                                # 返回某索引处的字符return value[index]
 *  public void setCharAt(int n ,char ch)
 *
 * 【String与StringBuffer、StringBuilder的转换】
 *  new StringBuffer(String str);                    # 将String转换为 StringBuffer
 *  StringBuffer.toString()                          # 将StringBuffer转换为String
 *
 * 【StringBuffer和StringBuilder和String的异同】
 *  异：1). StringBuffer（JDK1.0, 线程安全，效率低）和 StringBuilder（JDK5.0, 非线程安全，效率高）是可变的字符序列
 *       2). String是不可变的字符序列
 *  同：1). 三者底层都是用char[]存储
 *
 * 【String与StringBuffer、StringBuilder的开发效率对比】
 *  StringBuilder>StringBuffer>String
 * @author Alex
 * @create 2022-11-21-13:24
 */

public class UseCommonClass03 {
    @Test
    public void test1() {
        // 问题1：底层源码问题
        StringBuffer str2 = new StringBuffer();
        System.out.println("str2.length() = " + str2.length());  // 这里输出的可不是16啊
        // 问题2：扩容问题，如果要添加的数据底层数组存不下
        // 默认情况下扩容位原来容量的2倍 + 2，同时将原有数组的元素复制到新的数组中
        // 指导意义：开发中建议使用StringBuffer(int capacity)先指定数组容量，尽量避免扩容，提高效率
    }

    // String与StringBuffer、StringBuilder的转换
    @Test
    public void test2() {
        // String → StringBuffer、StringBuilder
        String str1 = new String("abc");
        StringBuffer sb1 = new StringBuffer(str1);

        // StringBuffer、StringBuilder → String
        String str2 = new String(sb1);  // 方法1：调用构造器
        String str3 = sb1.toString();  // 方法2：调用方法
    }

    // 对比三者的开发效率, 效率：StringBuilder>StringBuffer>String
    @Test
    public void test3() {
        // 初始设置
        long startTime = 0L;
        long endTime = 0L;
        String text = "";
        StringBuffer buffer = new StringBuffer("");
        StringBuilder builder = new StringBuilder("");

        // StringBuffer的执行时间
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            buffer.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuffer的执行时间：" + (endTime - startTime));

        // StringBuilder的执行时间
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            builder.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder的执行时间：" + (endTime - startTime));

        // String的执行时间
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            text = text + i;
        }
        endTime = System.currentTimeMillis();
        System.out.println("String的执行时间：" + (endTime - startTime));

    }
}
