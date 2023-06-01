package useannotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Date;

/**
 * 【注解概述】
 *  Annotation其实就是代码里的特殊标记, 这些标记可以在编译, 类加载, 运行时被读取, 并执行相应的处理。可用于修饰包,类,构造器, 方法, 成员变量, 参数, 局部变量的声明
 *  未来的开发模式都是基于注解的，注解是一种趋势，一定程度上可以说：框架 = 注解 + 反射 + 设计模式
 *
 * 【注解的使用】使用 Annotation 时要在其前面增加@符号, 并把该Annotation当成个修饰符使用。用于修饰它支持的程序元素
 * 【示例一：文档注释的注解】
 *   @author 标明开发该类模块的作者，多个作者之间使用,分割
 *   @version 标明该类模块的版本
 *   @see 参考转向，也就是相关主题
 *   @since 从哪个版本开始增加的
 *   @param 只用于方法，对方法中某参数的说明，如果没有参数就不能写。格式要求：@param 形参名 形参类型 形参说明
 *   @return 只用于方法，对方法返回值的说明，如果方法的返回值类型是void就不能写。格式要求：@return 返回值类型 返回值说明
 *   @exception 只用于方法，对方法可能抛出的异常进行说明 ，如果方法没有用throws显式抛出的异常就不能写。格式要求：@exception 异常类型 异常说明
 *   @param和@exception可以并列多个
 *
 * 【示例二：在编译时进行格式检查(JDK内置的三个基本注解)】
 *   @Override: 限定重写父类方法, 该注解只能用于方法
 *   @Deprecated: 用于表示所修饰的元素(类, 方法等)已过时。通常是因为所修饰的结构危险或存在更好的选择
 *   @SuppressWarnings: 抑制编译器警告
 *
 * 【示例三：跟踪代码依赖性，实现替代配置文件功能（具体在反射、框架中使用）】
 *  Servlet3.0提供了注解(annotation),使得不再需要在web.xml文件中进行Servlet的部署。
 * @author Alex
 * @create 2022-12-03-10:39
 */


// jdk8之前，要重复注解，只能如下方式
//@UseAnnotation03({@UseAnnotation02(), @UseAnnotation02(VV = "xs")})
// jdk8新增了重复注解的方式,使用@repeatable接口实现~~~
@UseAnnotation02()
@UseAnnotation02(VV = "hi")
public class UseAnnotation01 {
    @Test
    public void test() {
        // date类中的构造器是@deprecated的，表示该构造方式已经过时了
        Date date = new Date(2020, 10, 11);

        // idea中以灰色的形式给用户警报，表示该变量未使用，可以使用@supresswarning抑制系统警告
        @SuppressWarnings("unused")
        int num = 10;

        // 用反射的方式体会注解的继承性
        Class clazz = Student.class;
        Annotation[] annotations = clazz.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            System.out.println(annotations[i]);
        }
    }
}

@UseAnnotation02(VV = "hello")
// jdk1.8新特性，可以用注解修饰泛型。ElementType.TYPE_PARAMETER
class Person<@UseAnnotation02 Integer> {
    private String name;
    // jdk1.8新特性，表示该注解能写在使用类型的任何语句中ElementType.TYPE_USE
    private int age = (@UseAnnotation02 int) 10L;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void walk() {
        System.out.println("走路");
    }

    /**
     * 文档注释示例
     * @param food String 食物
     * @return String 人吃什么食物
     */
    public String eat(String food) {
        System.out.println("人吃" + food);
        return food;
    }
}

class Student extends Person {
    // 未加文档注释不意味着这不是重写，文档注释是在编译期间对其进行校验（写错了会报错）！！！
    @Override
    public void walk() {
        System.out.println("学生走路");
    }
}
