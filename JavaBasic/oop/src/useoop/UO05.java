package useoop;

import org.junit.Test;

import java.util.TreeSet;

/**
 * 【构造器】constructor，或叫构造方法
 *  主要用于创建对象，给对象进行初始化（每次实例化都会执行构造器中的代码）
 *  构造器具有与类相同的名称
 *  构造器不声明返回值类型。（与声明为void不同）
 *  不能被static、final、synchronized、abstract、native修饰，不能有return语句返回值
 *  Java语言中，每个类都至少有一个构造器
 *  默认构造器的修饰符与所属类的修饰符一致
 *  一旦显式定义了构造器，则系统不再提供默认构造器
 *  一个类可以创建多个重载的构造器
 *  父类的构造器不可被子类继承
 *
 * 【语法格式】
 * 修饰符 类名 (参数列表) {
 *      初始化语句；
 * }
 *
 * 【属性赋值先后顺序总结】按照先后顺序有：
 * 1）默认初始化        # 如 int类型默认为 0
 * 2）显式初始化        # 代码块初始化（与显式初始化同级，二者按先后顺序执行）
 * 3）构造器中初始化
 * 4）通过“对象.属性”或“对象.方法”的方式赋值
 * ✔总结:由父及子，静态先行
 *
 * 【javabean】
 *  JavaBean是一种Java语言写成的可重用组件。
 *  所谓javaBean，是指符合如下标准的Java类：
 *   1)类是公共的
 *   2)有一个无参的公共的构造器
 *   3)有属性，且有对应的get、set方法
 *  用户可以使用JavaBean将功能、处理、值、数据库访问和其他任何可以用Java代码创造的对象进行打包，
 *   并且其他的开发者可以通过内部的JSP页面、Servlet、其他JavaBean、applet程序或者应用来使用这些对象。
 *   用户可以认为JavaBean提供了一种随时随地的复制和粘贴的功能，而不用关心任何改变。
 *
 *
 @author Alex
 @create 2023-01-10-13:08
 */
public class UO05 extends Up{
    // 属性赋值先后顺序例1
    // 总结：由父及子，静态先行
    @Test
    public void test1() {
        // 代码块优先级高于构造器~，先代码块后构造器~~~
        // 先到Down构造器中 ==> Mid ==> Up构造器（随之所有静态属性被加载）→
        new Down();
    }

    // 赋值先后顺序例2
    public static void main(String[] args) {
        System.out.println("重点是main在此处也是一个静态方法,所以public类要先被加载");  // 又因为只加载了类，未创建实例，所以构造器不会被加载
        System.out.println("*****************");
        new UO05();  // 类已经被加载过了，所以静态结构不再重新加载
        System.out.println("*****************");
        new Up();
    }

    static {
        System.out.println("public类中的静态代码块");
    }

    {
        System.out.println("public类中的非静态代码块");
    }

    public UO05() {
        System.out.println("public类中的构造器");
    }

}


class Up {
    static {
        System.out.println("Up的静态初始化块");
    }

    {
        System.out.println("Up的普通初始化块");
    }

    public Up() {
        System.out.println("Up的无参数的构造器");
    }
}

class Mid extends Up {
    static {
        System.out.println("Mid的静态初始化块");
    }

    {
        System.out.println("Mid的普通初始化块");
    }

    public Mid() {
        System.out.println("Mid的无参数的构造器");
    }

    public Mid(String msg) {
        // 通过this调用同一类中重载的构造器
        this();
        System.out.println("Mid的带参数构造器，其参数值：" + msg);
    }
}

class Down extends Mid {
    static {
        System.out.println("Down的静态初始化块");
    }

    {
        System.out.println("Down的普通初始化块");
    }

    public Down() {
        // 通过super调用父类中有一个字符串参数的构造器
        super("尚硅谷");
        System.out.println("Down的构造器");
    }
}

