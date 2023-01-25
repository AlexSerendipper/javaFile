package useoop;

import org.junit.Test;

/**
 * 【代码块】总体来说使用频率不高，因为初始化的操作完全可以被构造器平替
 *  代码块(或初始化块)的作用：对Java类或对象进行初始化
 *  static代码块通常用于初始化static的属性
 *      class Person {
 *          public static int total;
 *          static {
 *              total = 100;//为total赋初值
 *          }
 *      }
 *
 * 【代码块(或初始化块)的分类】
 *     1） 一个类中代码块若有修饰符，则只能被static修饰，称为静态代码块
 *         可以有输出语句。
 *         可以对类的属性、类的声明进行初始化操作。
 *         不可以对非静态的属性初始化。即：不可以调用非静态的属性和方法。
 *         若有多个静态的代码块，那么按照从上到下的顺序依次执行。
 *         静态代码块的执行要先于非静态代码块。
 *         ✔静态代码块随着类的加载而加载，且只执行一次。
 *     2）没有使用static修饰的，为非静态代码块。
 *         可以有输出语句。
 *         可以对类的属性、类的声明进行初始化操作。
 *         除了调用非静态的结构外，还可以调用静态的变量或方法。
 *         若有多个非静态的代码块，那么按照从上到下的顺序依次执行。
 *         ✔每次创建对象的时候，都会执行一次。且先于构造器执行。
 *
 * 【属性赋值先后顺序总结】按照先后顺序有
 *  1） 默认初始化
 *  2） 显式初始化                代码块初始化（与显式初始化同级，二者按先后顺序执行）
 *  3） 构造器中初始化
 *  4） 通过“对象.属性”或“对象.方法”的方式赋值
 *
 @author Alex
 @create 2023-01-10-18:40
 */
public class UO12 {
    // 代码块使用,主要用于初始化
    @Test
    public void test(){
        Man p = new Man();
        System.out.println(p.toString());
        System.out.println(Man.desc);
    }
}


class Man {
    String name;
    int age;
    static String desc = "我是一个人";

    public Man() {
    }

    public Man(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    // 静态代码块
    static {
        System.out.println("静态代码块。随着类的加载而执行");
        desc = "我是一个爱学习的人";
    }

    { // 非静态代码块
        System.out.println("非静态代码块。随着对象的创建而执行");
        age = 10;
        desc = "嘿嘿，又被我改了"; // 非静态代码块调用静态属性，合情合理
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }

}