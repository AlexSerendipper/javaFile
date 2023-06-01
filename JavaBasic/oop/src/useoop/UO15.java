package useoop;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 【接口冲突问题】
 *  若一个接口中定义了一个默认方法，而另外一个接口中也定义了一个同名同参数的方法（不管此方法是否是默认方法），
 * 在实现类同时实现了这两个接口时，会出现：接口冲突。
 *     解决办法：实现类必须覆盖接口中同名同参数的方法，来解决冲突。
 *  在实现类实现的某一个接口中定义了一个默认方法，而其父类中也定义了一个 与接口同名同参数的非抽象方法，则不会出现冲突问题。
 * 因为此时遵守类优先原则。即优先执行类中的同名方法。
 *
 @author Alex
 @create 2023-01-11-15:27
 */
public class UO15 extends C implements A,B{
    @Test
    public void test(){
        // 问题一：类C，接口AB中都有同名属性，如何调用
        // System.out.println(x);  // 出错：类和接口并列，所以说编译器不知道这个x是来自接口还是类
        System.out.println(super.x);  // 输出父类的x
        System.out.println(A.x); // 输出接口的x
        System.out.println(B.x); // 输出接口的x
        System.out.println("*****************");
        // 问题二：由于类中父类中也定义了一个同名同参数的非抽象方法，不会出现冲突。此时遵守：类优先原则。
        new UO15().y();
        System.out.println("*****************");
        // 问题三：如果接口中定义了同名同参数的方法，编译器不知道执行哪个，报错，必须重写方法
        new D().y();
        System.out.println("*****************");
        // 问题四：方法已经被重写，如何调用接口中的方法
        new D().method();
    }

}


interface A {
    String  x = "A";
    default void y() {
        System.out.println("接口A默认方法");
    };
}

interface B {
    String  x = "B";
    default void y() {
        System.out.println("接口B默认方法");
    };
}

class C {
    String  x = "C";
    public void y(){
        System.out.println("类C中的同名方法");
    }
}

class D implements A,B{
    @Override
    public void y() {
        System.out.println("类D中重写的方法");
    }
    public void method(){
        y();  // 调用了重写后的y方法
        A.super.y();  //调用接口中的方法
        B.super.y();
    }
}