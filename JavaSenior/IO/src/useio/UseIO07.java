package useio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 【标准的输入、输出流】
 * (1) System.in  代表了系统标准的输入，默认输入设备是：键盘
 *     System.out代表了系统标准的输入输出，默认输出设备是：控制台，显示器
 *     System.out返回的是一个PrintStream的实例
 * (2)重定向：通过System类的setIn，setOut方法对默认设备进行改变。
 *     public static void setIn(InputStream in)
 *     public static void setOut(PrintStream out)
 @author Alex
 @create 2022-12-18-12:01
 */
public class UseIO07 {
    // 需求：从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续
    // 进行输入操作，直至当输入“e”或者“exit”时，退出程序。
    public static void main(String[] args) throws IOException {
        // 方法1：使用scanner实现,调用next(),返回一个字符串
        // 方法2：使用system.in实现， ==> 转换流 ==> 调用.readline方法
        // 1. 创建转换流（不是操作文件，操作的是键盘输入）✔
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);  // 将输入流转换为字符流
        // 2. 创建缓冲流
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  // 调用.readline方法
        // 3. 操作程序
        while(true){
            System.out.println("请输入数据：");
            String data = bufferedReader.readLine();
            if("e".equalsIgnoreCase(data) || "exit".equalsIgnoreCase(data)){
                System.out.println("程序结束");
                break;
            }
            System.out.println(data.toUpperCase());
        }
        // 4. 关闭流
        bufferedReader.close();
    }
}
