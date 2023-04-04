package IoC;

import IoC.bean.Course;
import IoC.bean.Orders;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【基于xml的bean的其他配置】part1
 *
 * 【工厂bean】不常用
 *  Spring有两种类型的bean，一种是普通bean，另外一种是工厂bean（FactoryBean）
 *  普通bean：在配置文件中定义的bean，其返回值类型与 class属性定义的类型一致
 *  工厂bean：在配置文件定义的bean，其返回值类型可以与 class属性定义的类型不一致
 *   (1)第一步：创建类，让这个类作为工厂 bean，实现接口 FactoryBean
 *   (2)第二步：实现接口里面的方法，在实现的方法中定义返回的 bean 类型
 *        getObject()                     # 重写此方法，在此方法中定义返回值类型
 *
 * 【多实例bean】
 *  在Spring中，默认情况下，创建的bean是单实例对象
 *  在 spring 配置文件 bean 标签里面有属性（scope）用于设置单实例还是多实例
 *     singleton(默认值)，表示是单实例对象
 *     prototype，表示是多实例对象
 *  区别：设置scope值是singleton时候，加载 spring 配置文件时候就会创建单实例对象~
 *         设置scope值是prototype时候，不是在加载 spring 配置文件时候创建 对象，而是在调用 getBean 方法时候创建多实例对象~
 *
 *【bean的声明周期】了解
 *  生命周期：从对象创建到对象销毁的过程
 *  （1）通过构造器创建 bean 实例（无参数构造）
 *  （2）为 bean 的属性设置值（调用set方法 或 内/外部bean引用等）
 *       ==> 若配置了后置处理器，会将bean实例传递给后置处理器，然后执行后置处理器的postProcessBeforeInitialization方法
 *  （3）调用 bean 的初始化的方法（在xml中,通过init-method属性配置初始化的方法）
 *       ==> 若配置了后置处理器，则会执行后置处理器的 postProcessAfterInitialization方法
 *  （4）成功获取bean对象
 *  （5）当容器关闭时候，调用 bean 的销毁的方法（在xml中，通过destroy-method属性配置销毁的方法）（需要使用context.close()手动销毁对象)
 *
 *
 @author Alex
 @create 2023-02-21-16:32
 */
public class UJ06 implements FactoryBean<Course>, BeanPostProcessor {
    // --------------重写FactoryBean接口方法------------------
    // 在此方法中定义返回值类型
    @Override
    public Course getObject() throws Exception {
        Course course = new Course();
        course.setCname("语文");
        return course;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    // --------------重写BeanPostProcessor接口方法------------------
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第2.5步 在初始化之前执行的方法");
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第4.5步 在初始化之后执行的方法");
        return bean;
    }
    // ---------------------------------------------------------------









    // 工厂bean：返回值类型可以与xml中配置的不一样
    // 设置scope属性多实例对象、
    // 注意：当xml文件被读取时，xml中的所有bean对象都被创建，(除非将 orders bean设置为多例模式)。否则orders类被创建的信息也被输出~
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ06.xml");
        Course uj06 = context.getBean("uj06", Course.class);
        Course copy = context.getBean("uj06", Course.class);
        System.out.println("****************************");
        System.out.println(uj06);
        System.out.println(copy);
    }


    // bean对象的声明周期演示
    @Test
    public void test2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ06.xml");
        Orders orders = context.getBean("orders", Orders.class);
        System.out.println("第四步 获取创建 bean 实例对象");
        System.out.println(orders);
        //手动让 bean 实例销毁
        context.close();
    }

}

