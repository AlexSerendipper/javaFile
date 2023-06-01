package useabnormal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 【抓】抓
 *  Java提供的是异常处理的抓抛模型
 *  Java程序的执行过程中如出现异常，会生成一个异常类对象（抓）
 *  一旦异常对象抛出后，其后的代码不再执行
 *
 * 【抛】
 *  将'抓'到的异常对象提交给Java运行时系统，这个过程称为抛出(throw)异常，常见的有两种抛出方式。
 *   （1）✔自动抛出：由虚拟机自动生成：程序运行过程中，虚拟机检测到程序发生了问题，如果在当前代码中没有找到相应的处理程序，就会在后台自动创建一个对应异常类的实例对象并抛出
 *   （2）✔手动抛出：由开发人员手动创建：throw new ClassCastException(); 若创建好的异常对象不主动抛出对程序没有任何影响，和创建一个普通对象一样
 *  程序员通常只能处理Exception，而对Error无能为力。
 *  ✔✔✔RuntimeException类或是它的子类，这些类的异常的特点是：即使没有使用try和catch捕获，Java自己也能捕获，并且编译通过(运行时会发生异常使得程序运行终止)
 *  ✔✔✔如果抛出的异常是IOException等类型的非运行时异常，则必须捕获，否则编译错误。也就是说，
 *      我们可以通过处理（try-catch）编译时异常，将异常进行捕捉，转化为运行时异常
 *
 * 【异常处理机制一：try-catch-finally】实际中都用ctrl+alt+z自动生成
 *          try{
 *              ...... //可能产生异常的代码
 *          }
 *          catch( ExceptionName1 e ){
 *              ...... //当产生ExceptionName1型异常时的处置措施
 *          }
 *          catch( ExceptionName2 e ){
 *              ...... //当产生ExceptionName2型异常时的处置措施。如果明确知道产生的是何种异常，可以用该异常类作为catch的参数；也可以用其父类作为catch的参数。
 *          }
 *          finally{
 *              ...... //无论是否发生异常，都无条件执行的语句
 *          }
 *  可以访问一个异常对象的成员变量或调用它的方法。
 *    getMessage() 获取异常信息，返回字符串
 *    printStackTrace() 获取异常类名和异常信息，以及异常出现在程序中的位置。返回值void。
 *  不论在try代码块中是否发生了异常事件，catch语句是否执行，catch语句是否有异常，catch语句中是否有return，finally块中的语句都会被执行。finally语句和catch语句是任选的
 *  例如数据库连接、输入输出流、Socket连接，JVM无法自动回收，我们需要自己手动进行资源释放，通常都声明在finally中
 *
 *
 @author Alex
 @create 2023-01-11-16:39
 */
public class UA02 {
    public static void main(String[] args) {
        try {
            UA02.method2();  // 再往上抛就到虚拟机了，不合适。用try catch处理
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());  // 可用方法1
            e.printStackTrace();  // 可用方法2
            System.out.println("没有找到文件");
        }catch(IOException e){
            e.printStackTrace();
        }

    }


    public static void method2() throws IOException {
        method1();
    }

    public static void method1() throws IOException{  // 遇到异常向上抛
        FileInputStream in = new FileInputStream("666.txt");
        int b;
        b = in.read();
        while (b != -1) {
            System.out.print((char) b);
            b = in.read();
            in.close();
        }}
}

