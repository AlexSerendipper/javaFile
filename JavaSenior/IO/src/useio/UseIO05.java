package useio;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;

/**
 *【缓冲流】属于处理流的一种，能够提高 流 读写文件的效率
 * BufferedInputStream(.read(byte[] b))
 * BufferedReader(.read(byte[] b)) (.readline())  # 新增了readline()，可以读取一行数据
 * BufferedOutputStream(.write(byte[],0,len))
 * BufferedWriter(.write(char[],0,len))
 *
 *【缓冲流能提高读写速度的原因】
 *  使用缓冲流读取文件时，会一次性从文件中读取8192个(8Kb)，存在缓冲区中，直到缓冲区装满了，才重新从文件中读取下一个8192个字节数组。
 *  使用缓冲流写入字节时，不会直接写到文件，先写到缓冲区中直到缓冲区写满，才会把缓冲区中的数据一次性写到文件里。
 *  使用方法flush()可以强制将缓冲区的内容全部写入输出流
 *
 @author Alex
 @create 2022-12-17-15:14
 */
public class UseIO05 {
    // 使用缓冲流实现对图片的加密
    @Test
    public void test1() throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("111.png")));
        int len;
        byte[] b = new byte[20];
        // 写入
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("222.png")));
        while ((len = bufferedInputStream.read(b)) != -1) {
            // 对字节数据进行修改，加密
            //  for(byte b : len){  // 错误，增强for循环会创造一个新变量，不会对原变量进行修改
            //      b = (byte) (b ^ 5);
            //  }
            for (int i = 0; i < len; i++) {
                b[i] = (byte) (b[i] ^ 5);
            }
            bufferedOutputStream.write(b, 0, len);
        }
        // 关闭流,关闭外层流的同时，内层流会自动关闭(所以fileInputStream和fileOutputStream不需要关闭了)
        bufferedOutputStream.close();
        bufferedInputStream.close();
    }


    // 使用缓冲流实现对文本文件的复制。新方法readline()的使用
    @Test
    public void test2() throws IOException {
        // 1. 处理流
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("hello.txt")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("hello1.txt")));

        // 2. 读取一行的数据，不再包含换行符
        String data;
        while ((data = bufferedReader.readLine()) != null) {
            // data中不再包含换行符,需要拼接上转义字符
            bufferedWriter.write(data + "\n");
            // 或者使用bufferedWriter.newline()方法，和直接加换行效果一致
        }

        // 3. 关闭流,关闭外层流的同时，内层流会自动关闭~~~
        bufferedReader.close();
        bufferedWriter.close();

    }

    // 使用缓冲流实现对图片的解密
    @Test
    public void test3() throws IOException{
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("222.png")));
        int len;
        byte[] b = new byte[20];
        // 写入
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("33.png")));
        while ((len = bufferedInputStream.read(b)) != -1) {
            for (int i = 0; i < len; i++) {
                b[i] = (byte) (b[i] ^ 5);  // 对加密的数据再次异或就是复原解密了
            }
            bufferedOutputStream.write(b, 0, len);
        }

        // 关闭流,关闭外层流的同时，内层流会自动关闭(所以fileInputStream和fileOutputStream不需要关闭了)
        bufferedOutputStream.close();
        bufferedInputStream.close();
    }

    // 获取文本上每个字符出现的次数
    // 提示：遍历文本的每一个字符；字符及出现的次数保存在Map中（键存字符，值存次数）；将Map中数据写入文件
    // 建议：使用泛型
    @Test
    public void test4() throws IOException{
        File file = new File("hello.txt");
        FileReader fileReader = new FileReader(file);
        HashMap<Character, Integer> hashMap = new HashMap<>();

        int data;
        int count;
        while((data=fileReader.read())!=-1){
            char d = (char) data;
            if(!hashMap.containsKey(d)){
                hashMap.put(d,1);
            }else{
                count = hashMap.get(d);
                hashMap.put(d,++count);
            }
        }
        System.out.println(hashMap);
    }
}
