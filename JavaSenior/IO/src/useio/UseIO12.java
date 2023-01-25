package useio;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 【随机(任意)存取文件流】 RandomAccessFile
 *  RandomAccessFile 类支持 “随机访问” 的方式，程序可以直接跳到文件的任意地方来读、写文件
 *  randomaccessFile直接继承于object类，实现了datainput和dataoutput接口
 *  randomAccessFile既可以作为一个输入流，又可以作为一个输出流
 *
 *
 * 【常用方法】
 *  public RandomAccessFile(File file, String mode)：构造器
 *  public RandomAccessFile(String name, String mode)：构造器
 *  long getFilePointer()：获取文件记录指针的当前位置
 *  void seek(long pos)：将文件记录指针定位到 pos 位置
 *     访问模式mode有r、rw、rwd、rws
 *     r: 以只读方式打开。如果读取的文件不存在则会出现异常
 *     rw：打开以便读取和写入。如果文件不存在则会去创建文件，如果存在则不会创建（写入时会对原有的文件内容进行覆盖，默认从头开始覆盖）
 *     rwd:打开以便读取和写入；同步文件内容的更新
 *     rws:打开以便读取和写入；同步文件内容和元数据的更新
 *
 * 【用途】
 * 我们可以用RandomAccessFile这个类，来实现一个多线程断点下载的功能，用过下载工具的朋友们都知道，下载前都会建立两个临时文件，一个是与
 * 被下载文件大小相同的空文件，另一个是记录文件指针的位置文件，每次暂停的时候，都会保存上一次的指针，然后断点下载的时候，会继续从上
 * 一次的地方下载，从而实现断点下载或上传的功能，有兴趣的朋友们可以自己实现下
 *
 * 【字节数组输出流】ByteArrayOutputStream
 * public void write(byte[] b,int off,int len)： 写入数据，写入其中的数据即使有中文，及时显示也不会出现异常
 *
 @author Alex
 @create 2022-12-19-11:40
 */
public class UseIO12 {
    // 使用随机存储文件流实现从指定位置开始覆盖文件
    @Test
    public void test() throws IOException {
        // 1. 与普通处理流不同，不需要传入节点流
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File("hello.txt"), "r");
        RandomAccessFile randomAccessFile1 = new RandomAccessFile(new File("hello1.txt"), "rw");
        byte[] b = new byte[5];
        int len;
        // 指定写入的位置，从第三个位置开始写入，write是覆盖的效果。第三个位置后全部被覆盖
        randomAccessFile1.seek(3);
        while ((len = randomAccessFile.read(b)) != -1) {
            randomAccessFile1.write(b, 0, len);
        }

        randomAccessFile.close();
        randomAccessFile1.close();
    }


    // 使用随机存储文件流实现插入的效果，使用StringBuilder
    @Test
    public void test1() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File("hello1.txt"), "rw");

        byte[] b = new byte[5];
        int len;
        // 思考：可以将StringBuilder替换为ByteArrayOutputStream
        StringBuilder stringBuilder = new StringBuilder((int) new File("hello1.txt").length());  // 指定长度。不会长于这个长度。
        randomAccessFile.seek(3);
        while ((len = randomAccessFile.read(b)) != -1) {
            // 把要插入位置之后的数据先存在StringBuilder中
            stringBuilder.append(new String(b,0,len));
        }
        System.out.println(stringBuilder.toString());
        // 把指针调回来
        randomAccessFile.seek(3);
        randomAccessFile.write("zzj".getBytes());  // 只能写入byte
        randomAccessFile.write(stringBuilder.toString().getBytes());
        randomAccessFile.close();
    }

    // 使用随机存储文件流实现插入的效果并实现及时显示，使用字节数组输出流
    @Test
    public void test2() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File("hello1.txt"), "rw");
        byte[] b = new byte[5];
        int len;
        // ByteArrayOutputStream。写入数据，写入其中的数据即使有中文，及时显示也不会出现异常
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        randomAccessFile.seek(3);
        while ((len = randomAccessFile.read(b)) != -1) {
            byteArrayOutputStream.write(b,0,len);
        }

        System.out.println(byteArrayOutputStream.toString());
        // 把指针调回来
        randomAccessFile.seek(3);
        randomAccessFile.write("zzj".getBytes());  // 只能写入byte
        randomAccessFile.write(byteArrayOutputStream.toString().getBytes());
        randomAccessFile.close();
    }
}
