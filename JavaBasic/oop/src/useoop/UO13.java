package useoop;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.junit.Test;

/**
 *
 *【final关键字】
 * 1）在Java中声明类、变量和方法时，可使用关键字final来修饰,表示“最终的”。
 * 2） final标记的类不能被继承。（提高代码安全性，提高程序的可读性）
 * 3） final标记的方法不能被子类重写。
 * 4） ✔final标记的变量(成员变量或局部变量)即称为常量。名称大写，且只能被赋值一次。
 *       常量必须赋值后才能使用（显式赋值、构造器/代码块赋值），其并没有默认值
 *       finnal标记的方法内的变量，会将其直接定义成常量，无法再重新赋值
 *       finnal标记的形参，在调用方法时会给形参赋值成常量，之后在方法内部使用无法再重新赋值
 * 5）static final标记的变量称为：全局常量。全局常量只能在声明时直接显式赋值，且不能更改
 *
 *
 * 【abstract】
 *  有时将一个父类设计得非常抽象，以至于它没有具体的实例，这样的类叫做抽象类
 *  用abstract关键字来修饰一个类，这个类叫做抽象类。
 *  用abstract来修饰一个方法，该方法叫做抽象方法。
 *   抽象方法：只有方法的声明，没有方法的实现。以分号结束。如：public abstract void talk();
 *  含有抽象方法的类必须被声明为抽象类。
 *  ✔抽象类不能被实例化。抽象类是用来被继承的，抽象类的子类必须重写父类的抽象方法，并提供方法体。
 *    若没有重写全部的抽象方法，仍需要把子类定义为抽象类。
 *  不能用abstract修饰变量、代码块、构造器；
 *  ✔不能用abstract修饰私有方法、静态方法、final的方法、final的类。
 *  在声明抽象类子类时直接对其方法重写，称为(抽象类的)匿名类
 *
 * 【模板方法设计模式(TemplateMethod)】
 *  当功能内部一部分实现是确定的，一部分实现是不确定的。这时可以把不确定的部分暴露出去，让子类去实现。
 *  换句话说，在软件开发中实现一个算法时，整体步骤很固定、通用，这些步骤已经在父类中写好了。
 *   但是某些部分易变，易变部分可以抽象出来，供不同子类实现。这就是一种模板模式。
 *
 @author Alex
 @create 2023-01-10-18:57
 */
public class UO13 {
    /**
     * finnal用法示例
     */
    public static void show(final int num) {
        final int NUM = 10;  // 常量
        // num = 20;  // finnal形参，只能传入实参的时候赋值，并且不能再被重新赋值~
        System.out.println(num);
    }

    // 模板方法设计模式演示
    @Test
    public void test1(){
        Template code = new Code();
        code.spendTime();  // 多态性，虚拟方法调用；运行到code()处时，执行子类重写的方法
    }

    /**
     * 模板方法设计模式，对外包络code接口
     */
    abstract class Template {
        // 计算某段代码执行所要花费的时间，大部分程序的固定的，只能执行的程序的易变的
        public void spendTime() {
            long start = System.currentTimeMillis();
            this.code();
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为" + (end - start) + "ms");
        }
        public abstract void code();
    }

    /**
     * 模板方法对外暴露的代码
     */
    class Code extends Template {
        // 计算1000以内的质数(除自身外无法被整除的数)
        @Override
        public void code() {
            for (int i = 2; i <= 1000; i++) {
                boolean flag = true;
                for (int j = 2; j < i; j++) {  // 这里可以优化为j<i     ==>    j<=Math.sqrt(i)
                    if (i % j == 0) {
                        flag = false;
                        break;
                    }
                }
                if (flag == true) {
                    System.out.println(i);
                }
            }
        }
    }
}


