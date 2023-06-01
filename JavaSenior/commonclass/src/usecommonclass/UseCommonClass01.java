package usecommonclass;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 【String类概述】
 *  String类的不可变性：对现有的字符串进行增删改查等操作，不会改变现有的字符串，会在内存中新建一个内存结构
 *  string声明为final，不可被继承，代表不可变的字符序列。
 *  string实现了serializable接口，表示字符串是支持序列化的
 *        实现了comparable接口，表示string可以比较大小
 *  string内部定义了Final char[] value，表示String对象的字符内容是存储在一个字符数组value[]中的。
 *  可以通过字面量 亦或 是new新对象的方式为String赋值
 *  ✔常量池中不会存储相同内容的字符串
 *
 * 【String对象的创建】
 *  String str1 = "abd";                                                # 字面量赋值，字符串存储在常量池中，目的在于共享
 *  String str2 = new String("abc");                                    # new对象，对象存储在堆中，其value值abc指向常量池
 *  String str3 = new String(char[] a,int startIndex,int count);        # 传入char[]或byte[]
 *  常量与常量的拼接结果在常量池。且常量池中不会存在相同内容的常量。
 *  只要其中有一个是变量，结果就在堆中
 *  如果拼接的结果调用intern()方法，返回值就在常量池中
 *
 * 【String与Byte[]和char[]之间的转换】
 *  char[] charArray1 = str1.toCharArray()                       # String与char[] 转换：调用String类的方法，将String转换为char[]
 *  new String(charArray1);                                      # String与char[] 转换：调用String类的构造器，将char[]转换为String
 *  byte[] byteArray1 = str1.getBytes()                          # String与byte[] 转换：调用String类的方法，使用默认字符集将String转换为byte[]数组
 *  byte[] byteArray1 = str1.getBytes(String charsetName)        # String与byte[] 转换：使用指定的字符集将String 编码到 byte 数组
 *  new String(byteArray1);                                      # String与byte[] 转换：调用String类的构造器，将byte[]转换为String
 *
 * @author Alex
 * @create 2022-11-19-15:47
 */
public class UseCommonClass01 {
    // 字符串的不可变性
    @Test
    public void test1() {
        String s1 = "abc";  // 字面量的定义方式，可以直接赋值，不用new
        String s2 = s1 + "def";  // 字符串不可变性，不论是拼接、替换还是啥的，都是重新开辟空间

        System.out.println(s1);
        System.out.println(s2);
    }

    // 字符串的创建
    @Test
    public void test2() {
        String s1 = "jj";
        String s2 = "jj";
        String s3 = new String("jj");
        String s4 = new String("jj");
        char[] chars = {'a','b','j','j','c','d'};
        String s5 = new String(chars,2,2);

        System.out.println(s5);
        System.out.println(s1 == s2);
        System.out.println(s3 == s4);
        System.out.println(s1 == s3);
    }


    // 【String与byte[] 转换】
    @Test
    public void test3() throws UnsupportedEncodingException {
        String str1 = "abc123中国";
        byte[] bytes = str1.getBytes();  // 编码：使用默认的字符集进行转换，三位表示一个汉字
        System.out.println(Arrays.toString(bytes));

        byte[] gbks = str1.getBytes("gbk");  // 使用指定的字符集进行转换，两位表示一个汉字
        System.out.println(Arrays.toString(gbks));

        String str2 = new String(bytes);
        System.out.println(str2);

        String str3 = new String(gbks, "gbk");
        System.out.println(str3);
    }


    // 字符串的创建细节题
    @Test
    public void test4() {
        String s1 = "zz";
        String s2 = "jj";

        String s3 = "zz" + "jj";  // 字面量拼接，返回值指向方法区
        String s4 = s1 + s2;  // 只要有变量名参与，返回值指向堆空间
        String s5 = "zzjj";  // 字面量拼接，返回值指向方法区
        String s6 = s1 + "jj";  // 只要有变量名参与，返回值指向堆空间

        final String s7 = "zz";  // 声明s7为常量
        String s8 = s7 + "jj";  // 常量和常量拼接，结果仍然是常量,结果指向方法区
        System.out.println(s3 == s8);

        System.out.println(s3 == s5);
        System.out.println(s3 == s4);
        System.out.println(s3 == s6);
        System.out.println(s6.intern() == s3);  // 如果拼接的结果调用intern()方法，返回值就指向常量池中
    }

}

