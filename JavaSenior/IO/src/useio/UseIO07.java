package useio;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 【打印流（了解即可）】可以实现将基本数据类型的数据格式转化为字符串输出(所有重载的print方法都输出转换后的string)
 *  PrintStream和PrintWriter
 *  ✔System.out返回的是PrintStream的实例
 *  打印流提供了一系列重载的print()和println()方法，用于多种数据类型的输出
 *  PrintStream和PrintWriter的输出不会抛出IOException异常
 *  PrintStream和PrintWriter有自动flush功能
 *  PrintStream 打印的所有字符都使用平台的默认字符编码转换为字节。在需要写入字符而不是写入字节的情况下，应该使用 PrintWriter 类。

 @author Alex
 @create 2022-12-18-15:47
 */
public class UseIO07 {
    @Test
    public void test() throws FileNotFoundException {
        File file = new File("hello1.txt");
        // 创建打印输出流，设置为自动刷新模式（写入换行符或'\n'时，都会刷新输出缓冲区）
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        PrintStream printStream = new PrintStream(fileOutputStream, true);

        // 设置打印输出的位置，默认为控制台
        System.setOut(printStream);

        // 输出ASCII字符
        for (int i = 0; i <= 255; i++) {
            System.out.print((char)i);
            if(i == 50){  // 每50个数据换行
                System.out.println();
            }
        }
    }
}
