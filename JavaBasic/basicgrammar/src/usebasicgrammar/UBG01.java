package usebasicgrammar;

import org.junit.Test;

/**
 * 【关键字、保留字、标识符】
 *  关键字(key word)：已经被java语言赋予了特殊含义的字符串，均为小写。
 *  保留字(reserved word)：未来可能被java赋予特殊含义的字符串。
 *  标识符(identifier)：凡是自己起的名字都是标识符，java严格区分大小写，不能含有空格。
 *
 * 【变量概述】用于在内存中保存数据。使用变量名来访问这块区域的数据
 *  变量是程序中最基本的存储单元。包含变量类型、变量名和存储的值
 *  Java中每个变量必须先声明，后使用
 *  变量的作用域：其定义所在的一对{ }内
 *  变量只有在其作用域内才有效
 *  同一个作用域内，不能定义重名的变量
 *
 * 【变量的分类1】按照数据类型分类
 *  1）基本数据类型(primitive type)
 *     数值型：byte,short,int,long（整型常量默认为int型，声明long型常量须后加‘l’或‘L’）
 *     浮点型：float,double（浮点型常量默认为double型，声明float型常量，须后加‘f’或‘F’）
 *     字符型：char (用单引号括起来的单个字符,也可以是转义字符。用2字节存储)
 *                  （char类型是可以进行运算的。因为它都对应着Unicode码。如char 65代表A）
 *     布尔型：boolean
 *  2）引用数据类型(reference type)
 *     类(class)：string包含其中
 *     接口(interface)
 *     数组([ ])
 *
 * 【变量的分类2】按声明的位置的不同
 *  1）在方法体外，类体内声明的变量称为成员变量
 *      实例变量（不以static修饰）
 *      类变量（以static修饰）
 *  2）在方法体内部声明的变量称为局部变量
 *      形参（方法、构造器中定义的变量）
 *      方法局部变量（在方法内定义）
 *      代码块局部变量（在代码块内定义）
 *
 * 【基本数据类型转换】当容量小的数据类型与容量大的数据类型做运算(任何运算，包括比较)，结果自动使用容量大的数据类型接收
 *  byte,short,char之间不会相互转换，他们三者在计算时首先转换为int类型
 *  boolean类型不能与其它数据类型运算。
 *  任何基本数据类型的值和字符串(String)进行连接运算时(+)，基本数据类型的值都自动转化为字符串(String)类型
 *  总结：byte(1)/char(2)/short(2) ==> int(4) ==> long(16) ==>float(32)==>double(64)。 括号内为字节，一字节8位二进制数
 *
 * 【强制数据类型转换】将容量大的数据类型转换为容量小的数据类型。使用时要加上强制转换符：()，但可能造成精度降低或溢出
 *  字符串不能直接转换为基本类型，但通过基本类型对应的包装类则可以实现把字符串转换成基本类型
 *  boolean类型不可以转换为其它的数据类型
 *
 * 【进制问题】
 *  所有数字在计算机底层都以二进制形式存在。
 *  对于整数，有四种表示方式：
 *     二进制(binary)：0,1 ，满2进1.以0b或0B开头。
 *     十进制(decimal)：0-9 ，满10进1。
 *     八进制(octal)：0-7 ，满8进1. 以数字0开头表示。
 *     十六进制(hex)：0-9及A-F，满16进1. 以0x或0X开头表示。此处的A-F不区分大小写。
 *  对于二进制，有如下三种形式：
 *     原码：直接将一个数值换成二进制数。最高位是符号位
 *     负数的反码：是对原码逐位取反，最高位（符号位）确定为1。
 *     负数的补码：其反码加1。
 *  计算机以二进制补码的形式保存所有的整数。
 *    正数的原码、反码、补码都相同
 *    负数的补码是其反码+1 （让符号位也能参与运算的问题）
 *
 @author Alex
 @create 2023-01-08-20:41
 */
public class UBG01 {
    //  任意进制转换为十进制
    // (10)32.75 ==> 3*10^1 + 2*10^0 + 7*10^-1 + 5*10^-2
    // (2)1010.01 ==> 1*2^3 + 1*2^1 + 1*2^-2
    // (16)1A6.3B8 ==> 1*16^2 + A*16^1 + 6 + 3*16^-1 + B*16^-2 + 8*16^-3
    //  十进制转换为任意进制
    // 259 ==> (8)403      8|259    ...3
    //                       8|32   ...0
    //                         8|4  ...4
    //  反码的作用
    // 原码：+5 = 0 0101       -5 = 1 0101
    // 补码：+5 = 0 0101       -5 = 1 1011
    // 利用补码做加减运算有。0 0101 + 1 1011 = 0000, 让符号位也能参与运算
    @Test
    public void test1(){
        int a = 128;  // 底层为0000 0000 0000 0000 0000 0000 1000 0000
        byte b = (byte) a;  // 底层为1000 0000
        System.out.println(b);  // 由于计算机底层由反码组成，首位为符号位，故输出-127
        System.out.println("*****************");
        byte c = 127;  // 底层为0111 1111
        System.out.println(++c);   // 底层为0111 1111 + 0000 0001 = 1000 000，故输出128
        System.out.println("*****************");
        char d = 65;
        System.out.println(d);
    }
}
