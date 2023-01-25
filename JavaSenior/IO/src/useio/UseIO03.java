package useio;

import org.junit.Test;

import java.io.*;

/**
 * 【IO流概述】
 *   I/O是Input/Output的缩写。用于处理设备之间的数据传输。如读/写文件，网络通讯等。
 *   java中对于数据的输入/输出操作都是以“流(stream)” 的方式进行
 *   java.io包下提供了各种“流”类和接口，用以获取不同种类的数据，并通过标准的方法输入或输出数据。
 *
 * 【流的分类】
 *  按操作数据单位不同分为：字节流(8 bit)，字符流(16 bit)(以char储存)
 *   字节流操作字节，比如：.mp3，.avi，.rmvb，mp4，.jpg，.doc，.ppt
 *   字符流操作字符，只能操作普通文本文件。最常见的文本文件：.txt，.java，.c，.cpp 等语言的源代码
 *  按数据流的流向不同分为：输入流，输出流
 *   输入input：读取外部数据（磁盘、光盘、网络中等存储设备的数据）到程序（内存）中。（我们是站在程序的视角来看输入输出）
 *   输出output：将程序（内存）数据输出到磁盘、光盘等存储设备中。
 *  按流的角色的不同分为：节点流（直接作用在待处理文件上），处理流（作用在已有的节点流之上的）
 *
 * 【✔抽象基类】（Java的IO流共涉及40多个类)，都是从如下4个抽象基类派生的，方法也是重载了抽象基类的方法
 *  输入流： InputStream(字节流)
 *          Reader（字符流）
 *  输出流： OutputStream(字节流)
 *          Writer（字符流）
 * (1)InputStream常用方法
 *    int read()：从输入流中读取数据的下一个字节。返回0-255范围内的int字节值。若到达流末尾而没有可用的字节，则返回值-1。
 *    int read(byte[] b)：从输入流中一次读取b.length个字节的数据，返回读取的字节数✔。若到达流末尾而没有可用的字节，则返回值-1。
 *    int read(byte[] b, int off, int len)
 * (2)Reader常用方法
 *    int read(): 读取单个字符。若到达流末尾而没有可用的字符，则返回值-1。
 *    int read(char [] c)：从输入流中一次读取c.length个字符的数据，返回读取的字符数✔。若到达流末尾而没有可用的字节，则返回值-1。
 *    int read(char [] c, int off, int len):
 * (3)OutputStream常用方法
 *    void write(int b): 将指定的字节写入此输出流。
 *    void write(byte[] b): 将 b.length个字节写入此输出流
 *    void write(byte[] b,int off,int len): 将指定byte数组中从偏移量off开始的len个字节写入此输出流
 *    void flush():　刷新此输出流并强制写出所有缓冲的输出字节
 * (4)Writer常用方法
 * 　　void write(int c)：写入单个字符
 * 　　void write(char[] cbuf)：写入cbuf.length长度到字符数组中
 * 　　void write(char[] cbuf,int off,int len)：写入字符数组的某一部分。从off开始，写入len个字符
 * 　　void write(String str)：写入字符串。
 * 　　void write(String str,int off,int len)：写入字符串的某一部分。从off开始，写入len个字符
 * 　　void flush()：刷新该流的缓冲，则立即将它们写入预期目标
 *
 *  【✔节点流（或叫文件流）】四个，基本上其余都是处理流，处理流作用在已有的节点流之上的
 *  (1)汇总
 *    FileInputStream(.read(byte[] b))
 *    FileReader(.read(char[] c))
 *    FileOutputStream(.write(char[],0,len))
 *    FileWriter(.write(char[],0,len))
 *  (2)文件写出注意事项
 *   数据写出时，如果文件不存在，会创建一个新的文件
 *   如果文件存在，指定FileWriter为append为true(默认为false)，新增内容(默认覆盖内容)
 *
 * 【✔流的使用流程】
 *  文件==>节点流==>（处理流）==>关闭流（JVM无法自动回收流）
 @author Alex
 @create 2022-1-6
 */

// 【正规写法都应该用try catch，close操作要放在finally中，防止上述过程出现错误抛出异常后流未被关闭。
//  为简洁，后续在IO这章我会用throws的方式处理~但是实际中都应该用try-catch
public class UseIO03 {
    // 一、数据读取：read()方法单个字符读取(读入的文件一定要存在)
    @Test
    public void test() {
        FileReader fileReader = null;
        try {
            // 1. 指明要操作的文件
            File file = new File("hello.txt");  // junit相对路径，是相对于当前module
            // 2. 提供具体流
            fileReader = new FileReader(file);
            // 3. 数据的读入, 这样不高效
            // int data = fileReader.read();
            // while(data != -1){
            //     System.out.print((char)data);
            //     data = fileReader.read();
            // }
            // 3. 数据读入，这样写比较美
            int data;
            while ((data = fileReader.read()) != -1) {
                System.out.print((char) data);  // data是int，转换为char
            }
            // 4. 流的关闭操作(对于其他物理连接，数据库的连接、流的连接，JVM无法自动回收)
            // 为了防止更早出现异常，先判断是否有filereader对象，再close
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileReader != null) {  // 这个一定要加上，避免空指针异常
                try {
                    fileReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    // 二、✔数据读入，使用read(char[] cbuf)
    @Test
    public void test1() throws IOException {
        File file = new File("hello.txt");
        FileReader fileReader = new FileReader(file);
        char[] cbuf = new char[5];  // ✔每次读五个字符
        int data;
        while ((data = fileReader.read(cbuf)) != -1) {  // 返回每次读入数组中的字符个数，到文件末尾返回-1
            // System.out.println(cbuf);  // ✔经典错误，假设八个字符abcdefgh，第一次输出abcde，第二次装的时候是覆盖原先的数据成fghde~~~
            // 方式一：低效
            // for (int i = 0; i < data; i++) {  // 正确的做法是读进去几个就输出几个
            //     System.out.print(cbuf[i]);
            // }
            // 方式二：高效
            String str = new String(cbuf, 0, data);  // 转换成string，每次输出data长度的个数~
            System.out.print(str);
        }
        fileReader.close();
    }

    // 三、数据写出
    @Test
    public void test2()throws IOException{
        // 1.提供文件和处理流
        FileWriter fileWriter = new FileWriter(new File("hello1.txt"),true);
        // 2.写出
        fileWriter.write("i have a dream\n");
        fileWriter.write("i have a apple");
        // 3.关闭流
        fileWriter.close();
    }

    // 四、实现文件复制
    @Test
    public void test3() throws IOException{
        // 读取
        FileReader fileReader = new FileReader(new File("hello.txt"));
        int len;
        char[] c = new char[5];

        // 写出
        FileWriter fileWriter = new FileWriter(new File("hello1.txt"));
        while((len=fileReader.read(c))!=-1){
            String s = new String(c, 0, len);
            fileWriter.write(s);
        }

        // 关闭
        fileWriter.close();
        fileReader.close();
    }

}

