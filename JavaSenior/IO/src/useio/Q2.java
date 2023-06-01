package useio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 练习：设计一个自定义类，能够完成类似scanner的功能，能够从键盘中读取int,
 * double, float, boolean, short, byte and String等类型的数据
 @author Alex
 @create 2022-12-18-15:26
 */
public class Q2 {
    // 1. read string
    public static String readString() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = bufferedReader.readLine();
        return str;
    }

    // 2. read int
    public static int readInt() throws IOException {
        return Integer.parseInt(readString());
    }

    // 3.read double
    public static double readDouble() throws IOException {
        return Double.parseDouble(readString());
    }

    // 4. 其余就完全类似拉！！、~~
    public static void main(String[] args) throws IOException {
        System.out.println("请输入数据：");
        String str = readString();
        System.out.println("您输入的数据是" + str);
    }
}
