package useabnormal;

import org.junit.Test;

import java.util.Date;
import java.util.Scanner;

/**
 * 【异常概述】
 *  异常：在Java语言中，将程序执行中发生的不正常情况称为“异常” (开发过程中的语法错误和逻辑错误不是异常)
 *  Java程序在执行过程中所发生的异常事件可分为两类
 *  1）Error：错误。Java虚拟机无法解决的严重问题。如：JVM系统内部错误、资源耗尽等严重情况。比如：StackOverflowError和OOM。一般不编写针对性的代码进行处理。
 *  2）Exception: 异常。其它因编程错误或偶然的外在因素导致的一般性问题，可以使用针对性的代码进行处理。例如：
 *     空指针访问
 *     试图读取不存在的文件
 *     网络连接中断
 *     数组角标越界
 *  对于这些异常，一般有两种解决方法：一是遇到错误就终止程序的运行。另一种方法是由程序员在编写程序时，就考虑到错误的
 *   检测、错误消息的提示，以及错误的处理。
 *  捕获错误最理想的是在编译期间，但有的错误只有在运行时才会发生。所以异常可以分为。编译时异常！和运行时异常！
 *
 * 【编译时异常】非受检异常
 *  编译器要求必须处置的异常。即程序在运行时由于外界因素造成的一般性异常。编译器要求Java程序必须捕获或声明所有编译时异常。
 *
 * 【运行时异常】受检异常
 *  是指编译器不要求强制处置的异常。一般是指编程时的逻辑错误，是程序应该积极避免其出现的异常。
 *  如java.lang.RuntimeException类及它的子类都是运行时异常。
 *
 @author Alex
 @create 2023-01-11-16:23
 */
public class UA01 {
    // Error情况举例
    @Test
    public void test0() {
        Integer[] arr = new Integer[1024 * 1024 * 1024];
    }

    //*****************************************常见运行时异常举例***************************//
    //空指针：NullPointerException
    @Test
    public void test1() {
        int[] arr1 = null;
        System.out.println(arr1[3]);
    }

    //角标越界：ArrayIndexOutOfBoundsException
    @Test
    public void test2() {
        int[] arr1 = new int[3];
        System.out.println(arr1[3]);
    }

    //强制转换异常：ClassCastException
    @Test
    public void test3() {
        Object obj = new Date();
        String str = (String) obj;
    }

    //包装类转换异常NumberFormatException
    @Test
    public void test4() {
        String str = "a23";
        int num = Integer.parseInt(str);
    }

    //输入不匹配异常InputMismatchExceptio
    @Test
    public void test5() {
        Scanner scanner = new Scanner(System.in);
        int score = scanner.nextInt();  // 此时输入字符串类型，出现输入不匹配
        System.out.println(score);
    }

    //算数异常ArithmeticException
    @Test
    public void test6() {
        int a = 0;
        int b = 10;
        System.out.println(b / a);
    }
}
