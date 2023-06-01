package usereflection;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 【**本节内容仅作了解**】
 * 【类的加载过程】
 * 1）加载load
 *    编译：程序经过javac.exe命令后，会生成一个或多个字节码文件（以.class结尾），
 *    运行：使用java.exe命令，对某个字节码文件进行解释运行。相当于将某个字节码文件加载到内存中(此过程称为类的加载)
 *    ✔✔加载到内存中的类，我们称为运行时类，此运行时类，就作为Class类的一个实例。。。即一个类本身也是一个对象。。。换句话说，Class类的实例，就对应着一个运行时类
 *    注意：加载到内存中的运行时类，会缓存一定的时间。。。在此时间内我们可以通过不同的方式，获取的都是同一个运行时类，他们地址值相同
 * 2）链接link
 *    将类的二进制数据合并到JRE中
 * 3)初始化initialize
 *    执行类构造器<clinit>()方法的过程
 *    类构造器<clinit>()方法是由编译期自动收集类中所有类变量的赋值动作和静态代码块中的语句合并产生的。是构造类信息的构造器，不是构造该类对象的构造器
 *
 * 【什么时候会发生类初始化？】了解
 * 1）类的主动引用（一定会发生类的初始化）
 *    当虚拟机启动，先初始化main方法所在的类
 *    new一个类的对象
 *    调用类的静态成员（除了final常量）和静态方法
 *    使用java.lang.reflect包的方法对类进行反射调用
 *    当初始化一个类，如果其父类没有被初始化，则先会初始化它的父类
 * 2）类的被动引用（不会发生类的初始化）
 *    当访问一个静态域时，只有真正声明这个域的类才会被初始化
 *    当通过子类引用父类的静态变量，不会导致子类初始化
 *    通过数组定义类引用，不会触发此类的初始化
 *    引用常量不会触发此类的初始化（常量在链接阶段就存入调用类的常量池中了）
 *
 *【类加载器classloader】
 * （1）作用
 *    classloader把类(class)装载进内存的, 主要用于类的加载过程中的运行阶段。
 *     具体功能就是将class文件字节码内容加载到内存中，并将这些静态数据转换成方法区的运行时数据结构，然后在堆中生成一个代表这个类的java.lang.Class对象。
 * （2）分类
 *    对于自定义类，使用系统类加载器
 *    对于扩展jar包，使用扩展类加载器
 *    JAVA的核心类库，使用引导类加载器（无法直接获取）
 * （3）
 *    .getClassLoader()                           # 获取当前类的类加载器
 *     classloader = classloader.getParent()      # 获取当前类加载器的父类加载器
 *    .getResourceAsStream(String str) 　　　　　  # ✔获取类路径下的指定文件的输入流(在项目被编译后，所有文件都会被编译到target/classes目录下，所以可以直接读取其中的文件（如properties文件）)
 *                                                                                    （使用类的加载器获取流，默认路径在当前module/src目录下）
 @author Alex
 @create 2022-12-22-10:38
 */

public class UseReflection02 {
    // 类加载器的分类
    @Test
    public void test(){
        // 对于自定义类，使用系统类加载器
        Class class1 = UseReflection02.class;
        ClassLoader classLoader = class1.getClassLoader();
        System.out.println(classLoader);
        System.out.println("*****************");
        // 使用getparent方法，获得扩展类加载器
        ClassLoader parent = classLoader.getParent();
        System.out.println(parent);
        System.out.println("*****************");
        // 引导类加载器，主要用于加载JAVA的核心类库，存在，但无法直接获取
        System.out.println(parent.getParent());
    }

    // 集合中的properitys:用来读取配置文件
    // 标准做法如下
    @Test
    public void test1() throws IOException {
        Properties properties = new Properties();
        // 用单元测试方法，默认路径在当前module下
        FileInputStream fileInputStream = new FileInputStream(new File("jdbc.properties"));
        properties.load(fileInputStream);
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");
        System.out.println("name = " + name);
        System.out.println("password = " + password);
    }


    // 集合中的properitys:用来读取配置文件
    // 使用类的加载器获取流的方法如下
    @Test
    public void test2() throws IOException {
        Properties properties = new Properties();
        Class class1 = UseReflection02.class;
        ClassLoader classLoader = class1.getClassLoader();
        // 使用类的加载器获取流，默认路径在当前module/src目录下
        InputStream is = classLoader.getResourceAsStream("jdbc1.properties");
        properties.load(is);
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");
        System.out.println("name = " + name);
        System.out.println("password = " + password);
    }

}


