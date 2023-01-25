package useio;

import org.junit.Test;

import java.io.*;

/**
 * 【转换流概述】
 * （1）Java API提供了两个转换流：属于字符流（操作的都是char型数组）
 *     InputStreamReader：将InputStream转换为Reader（实现将字节的输入流按指定字符集转换为字符的输入流）（解码）
 *       public InputStreamReader(InputStream in)
 *       public InputSreamReader(InputStream in,String charsetName)
 *     OutputStreamWriter：将Writer转换为OutputStream（实现将字符的输出流按指定字符集转换为字节的输出流。）（编码）
 *       public OutputStreamWriter(OutputStream out)
 *       public OutputSreamWriter(OutputStream out,String charsetName)
 * （2）很多时候我们使用转换流来处理文件乱码问题。实现编码和解码的功能。
 * （3）字符集
 *     ASCII：美国标准信息交换码。 用一个字节的7位可以表示。
 *     ISO8859-1：拉丁码表。欧洲码表 用一个字节的8位表示。
 *     GB2312：中国的中文编码表。最多两个字节编码所有字符
 *     GBK：中国的中文编码表升级，融合了更多的中文文字符号。最多两个字节编码
 *     Unicode：国际标准码，融合了目前人类使用的所有字符。为每个字符分配唯一的字符码。所有的文字都用两个字节来表示。
 *     UTF-8：变长的编码方式，可用1-4个字节来表示一个字符。
 *
 * 【转换流的编码应用】
 *  可以将字符按指定编码格式存储
 *  可以对文本数据按指定编码格式来解读
 *  指定编码表的动作由构造器完成
 @author Alex
 @create 2022-12-18-11:02
 */
public class UseIO06 {
    // 用了UTF-8读取文件，然后以GBK文件格式写出
    @Test
    public void test() throws IOException {
        // 1. 转换流
        // 不声明则使用系统默认的字符集
        // 具体使用哪个字符集，取决于当初文件保存时使用的字符集
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("hello.txt")),"UTF-8");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File("hello1.txt")), "GBK");
        // 2. 文件操作
        char[] c = new char[5];
        int len;
        while ((len = inputStreamReader.read(c))!=-1){
            outputStreamWriter.write(c,0,len);
            String str = new String(c,0,len);
            System.out.print(str);
        }
        // 3. 关闭流
        inputStreamReader.close();
        outputStreamWriter.close();
    }

}
