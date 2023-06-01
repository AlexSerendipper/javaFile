package useio;

import org.junit.Test;

import java.io.*;

/**
 * 【数据流】 DataInputStream 和 DataOutputStream
 *  (用于读取和写出基本数据类型、String类的数据）
 *  (✔数据保存的时候带有原来的数据类型，只能用DataInputStream读取）
 *
 * 【DataInputStream中常用方法】DataOutputStream中的只需要将下列方法的read改为相应的write即可
 *   boolean readBoolean()
 *   byte readByte()
 *   char readChar()
 *   float readFloat()
 *   double readDouble()
 *   short readShort()
 *   long readLong()
 *   int readInt()
 *   String readUTF()
 *   void readFully(byte[] b)
 *
 @author Alex
 @create 2022-12-18-16:18
 */
public class UseIO08 {
    @Test
    public void test() throws IOException {
        // 声明处理流
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File("hello1.txt")));
        // 处理
        dataOutputStream.writeUTF("钟钟");
        dataOutputStream.flush();  // 刷新缓存区，每次调用就把缓存区的中变量写入文件中
        dataOutputStream.writeInt(66);
        dataOutputStream.writeBoolean(true);
        // 关闭流
        dataOutputStream.close();
    }

    // 将文件中存储的基本数据类型变量和字符串读取到内存中，保存在变量中
    @Test
    public void test1() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File("hello1.txt")));
        // 必须按照写入的顺序来读数据，很麻烦啊
        String name = dataInputStream.readUTF();
        int age = dataInputStream.readInt();
        boolean isMale = dataInputStream.readBoolean();
        System.out.println(name + age + isMale);
        dataInputStream.close();
    }
}
