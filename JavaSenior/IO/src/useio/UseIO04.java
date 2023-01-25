package useio;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * ✔字符流不能处理图片、视频等非文本文件，需要使用字节流
 * ✔字节流也不能处理字符的文本数据，需要使用字符流
 * 【注】
 * 用字节流也可以实现对文本文件的复制（但是用字符流是不可能实现对非文本文件的复制的），
 * 因为有的中文5个字节存不下，byte[] b = new byte[5]，这种情况下如果只是搬运不会出错，如果要看输出可能会出错
 *
 @author Alex
 @create 2022-12-17-14:43
 */
public class UseIO04 {
    // 测试字节流无法处理文本数据的情况(能够处理英文字符，因为底层一个英文字符用一个字节能存)
    // 无法处理中文等其他字符
    @Test
    public void test() throws IOException {
        File file = new File("hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] b = new byte[5];
        int len;
        while ((len = fileInputStream.read(b)) != -1) {
            String str = new String(b, 0, len);
            System.out.print(str);
        }
    }

    // 使用字节流复制图片
    @Test
    public void test1() throws IOException {
        File file = new File("111.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] b = new byte[7];
        int len;
        // 写入操作
        File file1 = new File("222.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        while ((len = fileInputStream.read(b)) != -1) {
            fileOutputStream.write(b, 0, len);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
