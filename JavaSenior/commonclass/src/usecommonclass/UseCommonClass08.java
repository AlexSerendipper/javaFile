package usecommonclass;

import org.junit.Test;

/**
 * 【system类】
 * 1) 由于该类的构造器是private的，所以无法创建该类的对象。但内部的成员变量和成员方法都是static的，所以调用方便
 * 2) 成员变量: System类内部包含in、out和err三个成员变量，分别代表 标准输入流(键盘输入)、标准输出流(显示器)和标准错误输出流(显示器)。
 * 3) 常用方法
 *     System.currentTimeMillis()：返回时间戳
 *     System.exit(status)：该方法的作用是退出程序。其中status的值为0代表正常退出，非零代表异常退出。该方法可以在图形界面编程中实现程序的退出功能等。
 *     System. gc()：该方法的作用是请求系统进行垃圾回收。
 *     System.getProperty(String key)：获得系统中属性名为key的属性对应的值（常见属性见下方）
 * @author Alex
 * @create 2022-12-01-14:37
 */
public class UseCommonClass08 {
    @Test
    public void test() {
        String javaVersion = System.getProperty("java.version");
        System.out.println("java的version:" + javaVersion);
        String javaHome = System.getProperty("java.home");
        System.out.println("java的home:" + javaHome);
        String osName = System.getProperty("os.name");
        System.out.println("os的name:" + osName);
        String osVersion = System.getProperty("os.version");
        System.out.println("os的version:" + osVersion);
        String userName = System.getProperty("user.name");
        System.out.println("user的name:" + userName);
        String userHome = System.getProperty("user.home");
        System.out.println("user的home:" + userHome);
        String userDir = System.getProperty("user.dir");
        System.out.println("user的dir:" + userDir);
    }
}
