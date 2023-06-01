package useoop;

import org.junit.Test;


/**
 *【面向过程(POP) 与 面向对象(OOP)】
 *  二者都是一种思想，面向对象是相对于面向过程而言的。面向过程，强调的是功能行为，以函数为最小单位，考虑怎么做。
 * 面向对象，将功能封装进对象，强调具备了功能的对象，以类/对象为最小单位，考虑谁来做。
 *  万事万物皆对象如何理解
 *   1） 在JAVA语言中，我们都将功能、结构等都封装到类中，通过类的实例化来调用具体的功能（最基础array、string等也都是对象，可以看到他们都有自己的方法和属性）
 *   2） 当JAVA语言和前端HTML、后台数据库交互时，在Java处都体现为类、对象
 *
 *【类与对象】
 *  类是对一类事物的描述，是抽象的、概念上的定义
 *  对象是实际存在的该类事物的每个个体，因而也称为实例(instance)
 *  在一个类中的访问机制：类中的方法可以直接访问类中的成员变量
 *  ✔创建类的对象 = 类的实例化 = 实例化类
 *    class 类名{}                   创建类
 *    类名 对象名 = new 类名();      创建对象
 *    对象名.对象成员                访问对象成员（包括属性和方法）
 *    类名1 对象名1 = 对象名2        该步骤与数组类似，仅改变了对象1指向的地址
 *    new 类名1().方法1              匿名对象：当创建的新对象没有为其赋予变量名时，即为匿名对象，匿名对象只能调用一次（每次都重新new）
 *                                   我们经常将匿名对象作为实参传递给一个方法调用
 *
 *【属性】
 *  格式：修饰符 数据类型 属性名 = 初始化值
 *  ✔属性 = 成员变量 = field = 域、字段
 *  常用的权限修饰符有：private、缺省、protected、public
 *  其他修饰符：static、final、abstract等
 *  数据类型可以是任何基本数据类型(如int、Boolean) 或 任何引用数据类型
 *  属性名属于标识符，符合命名规则和规范即可。
 *  在一个类中声明属性为一个类的对象，称为关联(对象属性)！
 *
 *【变量的分类2】变量分类1见java基础，按声明的位置的不同
 *  1）在方法体外，类 内声明的变量称为成员变量
 *      实例变量（不以static修饰）
 *      类变量（以static修饰）
 *  2）在方法体内部声明的变量称为局部变量
 *      形参（方法、构造器中定义的变量）
 *      方法局部变量（在方法内定义）
 *      代码块局部变量（在代码块内定义）
 *
 *【成员变量与局部变量】
 * 1）区别：
 *                                             成员变量                                                    局部变量
 * 声明的位置：                              直接声明在类中                                       形参、方法局部变量、代码块局部变量
 * 修饰符：                           private、public、static、final等                         不能用权限修饰符修饰，可以用final修饰
 * 初始化值：                            有默认初始化值（与一维数组中的初始值相同）               没有默认初始化值，必须显式赋值，方可使用
 * 内存加载位置：                               堆空间                                                   静态域内 栈空间
 * 2）相同点：
 * 二者定义格式相同，数据类型1 变量名1 = 变量值1
 * 二者都有其对应的作用域，成员变量在类中属性，局部变量在方法、代码块、构造器内使用
 * 二者均有其生命周期
 *
 * 【匿名类与匿名对象】
 * 匿名类：指的是在创建抽象类或接口的实例时，在创建时对其重写抽象方法
 * 匿名对象：指的是创建实例对象时，没有为其显式命名接收
 *
 * 【权限修饰符】从小到大为：private、缺省(默认)、protected 、public
 *
 @author Alex
 @create 2023-01-09-17:48
 */
public class UO01 {
    @Test
    public void test(){
        // 非匿名对象
        Son son = new Son();
        show(son);
        System.out.println("*****************");
        // 匿名对象
        show(new Son());
        System.out.println("*****************");
        // 匿名类+非匿名对象
        Father f = new Father() {
            @Override
            public void show() {
                System.out.println("匿名类");
            }
        };
        show(f);
        System.out.println("*****************");
        // 匿名类+匿名对象
        show(new Father() {
            @Override
            public void show() {
                System.out.println("匿名类+匿名对象");
            }
        });
    }


    public void show(Father father){
        father.show();
    }


    public abstract class Father{
        public abstract void show();
    }

    public class Son extends Father{
        @Override
        public void show() {
            System.out.println("son");
        }
    }
}



