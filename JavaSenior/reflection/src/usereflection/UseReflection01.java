package usereflection;

import org.junit.Test;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 【反射概述】
 *  Reflection（反射）是被视为动态语言的关键，反射机制允许程序在执行期借助于Reflection API取得任何类的内部信息，并能直接操作任意对象的内部属性及方法
 *  动态语言：在运行时代码可以根据某些条件改变自身结构的语言
 *  反射，即通过对象看到完整的类的结构。
 *    正常过程：引入需要的”包类”名称 ==> 通过new实例化 ==> 取得实例化对象
 *    反射过程：实例化对象 ==> getClass()方法 ==> 得到完整的“包类”名称
 *  Java反射机制提供的功能（了解）
 *   1）在运行时判断任意一个对象所属的类
 *   2）在运行时构造任意一个类的对象
 *   3）在运行时判断任意一个类所具有的成员变量和方法
 *   4）在运行时获取泛型信息
 *   5）在运行时调用任意一个对象的成员变量和方法
 *   6）在运行时处理注解
 *   7）生成动态代理
 *  常用API（了解）
 *  java.lang.Class:代表一个类（所有的类的通用类，类都有属性方法构造器这些共性）
 *  java.lang.reflect.Method:代表类的方法
 *  java.lang.reflect.Field:代表类的成员变量
 *  java.lang.reflect.Constructor:代表类的构造器
 *
 * 【Class类的常用方法】java.lang.Class类是Java反射的源头，针对任何你想动态加载、运行的类，唯有先获得相应的Class对象
 *   public final Class getClass()
 *   static Class forName(String name)                       # 返回指定类名name的 Class 对象
 *   Object newInstance()                                    # 调用缺省构造函数，返回该Class对象的一个实例
 *   getName()                                               # 返回此Class对象所表示的实体（类、接口、数组类、基本类型或void）名称
 *   Class getSuperClass()                                   # 返回当前Class对象的父类的Class对象
 *   Class [] getInterfaces()                                # 获取当前Class对象的接口
 *   ClassLoader getClassLoader()                            # 返回该类的类加载器
 *   Class getSuperclass()                                   # 返回表示此Class所表示的实体的父类的Class
 *   Constructor[] getConstructors()                         # 返回一个包含某些Constructor对象的数组
 *   Field[] getDeclaredFields()                             # 返回Field对象的一个数组
 *   Method getMethod(String name,Class … paramTypes)        # 返回一个Method对象，此对象的形参类型为paramType
 *
 * 【获取Class实例的四种方式】
 *   Person.class;                                      # 调用运行时类的属性，如
 *   p.getClass();                                      # 通过运行时类的对象，如
 *   Class.forName("usereflection.Person");             # 调用Class的静态方法（使用全类名————包含包名的类名）(最常用)
 *   classLoader.loadClass("usereflection.Person");     # 使用类的加载器classloader(了解)
 *
 * 【哪些类型可以有Class对象？】
 *   1. class：外部类，成员(成员内部类，静态内部类)，局部内部类，匿名内部类
 *   2. interface：接口
 *   3. []：数组
 *   4. enum：枚举
 *   5. annotation：注解@interface
 *   6. primitive type：基本数据类型
 *   7. void
 *
 * 【常见疑问】
 * ✔疑问1：通过直接new对象的方式，或通过反射的方式都可以调用公共的结构，开发中用哪个？
 *    建议：直接new的方式。仅在编译中无法确定要new哪个类的对象时，用反射
 * ✔疑问2：反射机制与面向对象中的封装性是不是矛盾的？如何看待这两个技术？
 *   不矛盾，封装性体现的是:【建议】你使用公共的方法和属性，通常在公共的方法和属性中已经使用了私有的方法和属性。而反射体现的是【能不能】调用
 @author Alex
 @create 2022-12-22-10:38
 */

public class UseReflection01 {
    /**
     * 获取Class实例的四种方式
     */
    @Test
    public void test1() throws ClassNotFoundException {
        // 1. 方式1: 调用运行时类的属性
        Class<Person> class1 = Person.class;
        System.out.println(class1);  // 输出当前类本身
        // 2. 方式2：通过运行时类的对象
        Person p = new Person();
        Class class2 = p.getClass();
        System.out.println(class2);
        // 3. 方式3：调用Class的静态方法（使用全类名————包含包名的类名）(最常用)
        Class class3 = Class.forName("usereflection.Person");
        Class class4 = Class.forName("java.lang.String");  // java提供的类也作为Class类的实例
        System.out.println(class3);
        System.out.println(class4);
        //4. 方式4：使用类的加载器classloader(了解)
        ClassLoader classLoader = UseReflection01.class.getClassLoader();
        Class class5 = classLoader.loadClass("usereflection.Person");
        System.out.println(class5);
        System.out.println(class1 == class2);
        System.out.println(class1 == class3);
    }

    // 哪些类型可以有Class对象？
    @Test
    public void test2() {
        Class c1 = Object.class;
        Class c2 = Comparable.class;
        Class c3 = String[].class;
        Class c4 = int[][].class;
        Class c5 = ElementType.class;  // 枚举类
        Class c6 = Override.class;  // 注解
        Class c7 = int.class;
        Class c8 = void.class;
        Class c9 = Class.class;  // Class类本身也是一个类
        int[] a = new int[10];
        int[] b = new int[100];
        Class c10 = a.getClass();
        Class c11 = b.getClass();
        // 只要数组的类型与维度一样，就是同一个Class
        System.out.println(c10 == c11);
        System.out.println(c10 == c4);
    }
}

class Creature<T> implements Serializable {
    private char gender;
    public double weight;

    private void breath() {
        System.out.println("生物呼吸");
    }

    private void eat() {
        System.out.println("生物吃东西");
    }
}

@MyAnnotation(value="hi")
class Person extends Creature<String> implements Comparable<String>,Myinterface{
    private String name;
    public static int age;
    int birth;
    @MyAnnotation(value="6")
    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MyAnnotation
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    private String showNation(String nation, int age) throws RuntimeException,NullPointerException{
        System.out.println("我的国籍是：" + nation);
        System.out.println("我的年纪是：" + age);
        return nation;
    }

    private static void showDesc(){
        System.out.println("我是一个静态方法");
    }

    @Override
    public void show() {
        System.out.println("你好，我是一个人");
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }
}

interface Myinterface{
    void show();
}

@Target({TYPE,FIELD,METHOD, PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation{
    String value() default "hello";
}