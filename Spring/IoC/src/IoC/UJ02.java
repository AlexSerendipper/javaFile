package IoC;

import IoC.bean.Book;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【IOC（概念和原理）】
 *  什么是IOC
 *    控制反转，把对象创建和对象之间的调用过程，交给Spring进行管理
 *    使用IOC目的：为了耦合度降低
 *  IOC底层原理: 见xmind演变流程
 *    xml解析 + 工厂模式 + 反射
 *
 * 【IOC原理解析】BeanFactory接口
 *  IOC思想基于IOC容器完成，IOC容器底层就是对象工厂（即xml解析 + 工厂模式 + 反射）
 *  Spring 提供 IOC 容器实现两种方式：（两个接口）
 *   （1）BeanFactory：IOC容器基本实现，是Spring内部的使用接口✔，不提供给开发人员进行使用。
 *       特点是加载配置文件的时候不会创建对象，在获取对象（使用）才去创建对象
 *   （2）ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用✔
 *       特点是加载配置文件时候就会创建对象（创建在配置文件中配置的对象）
 *       spring一般部署在javaee中，故在tomcat启动时，读取配置文件将对象都创建好的方式，比BeanFactory更有利（把耗时的工作交给服务器启动时完成）
 *  ApplicationContext 接口有两个主要实现类，用于读取配置文件✔
 *     ApplicationContext context = new FileSystemXmlApplicationContext()                   # 输入配置文件在系统盘中的位置（D:\...）
 *     ApplicationContext context = new ClassPathXmlApplicationContext()                    # 输入配置文件在src下的位置（./）
 *     context.getBean("别名"，类)                                                           # 获取创建的对象
 *
 * 【bean管理】
 *  Bean 管理指的是两个操作：（1）Spring创建对象（2）Spirng注入属性
 *  ✔Bean管理操作有两种方式：（1）基于xml配置文件方式实现（2）基于注解方式实现
 *
 * 【基于xml的bean管理】part1:注入普通属性
 * （1）创建对象：<bean id="别名" class="bean的全类名"></bean>
 *     在spring配置文件中，使用 bean标签，标签里面添加对应属性，就可以实现对象创建
 *     创建对象时，默认执行无参数构造方法完成对象创建
 *     *在bean标签有很多属性，如name属性，与id属性作用相同，区别是name属性中可以添加特殊符号
 * （2）注入属性（见下）：DI：Dependency Injection，即表示注入属性
 *     set方式注入，通过<property></property>，相当于调用了bean对象的set方法✔
 *     构造器注入，通过<constructor-arg></constructor-arg>，相当于调用了bean对象的带参构造器
 *     p名称空间注入（了解，基本不用）：使用p名称空间注入，底层仍是set方式注入，可以简化基于xml配置方式。
 *       第一步 ✔添加p名称空间在配置文件中
 *       第二步 在bean标签里面进行进行属性注入操作
 * （3）注入其他类型属性
 *     null 值
 *      <property name="属性值">
 *           <null/>
 *      </property>
 *     属性值中包含特殊符号，如需要注入 <<北京>>
 *      方式一：把<>进行转义 &lt; &gt;
 *      方式二：把带特殊符号的内容写到CDATA中（value属性是可以当作标签单独写的✔）
 *      <property name="address">
 *           <value><![CDATA[<<南京>>]]></value>
 *      </property>
 ------------------------
 <?xml version="1.0" encoding="UTF-8"?>
 <beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 <!-- 添加p名称空间配置开始 -->
 xmlns:p="http://www.springframework.org/schema/p"
 <!-- 添加p名称空间配置结束 -->
 xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

 <!-- 1、set 方法注入属性-->
 <bean id="book" class="IoC.bean.Book">
     <!--使用 property 完成属性注入 name：类里面属性名称 value：向属性注入的值-->
     <property name="bname" value="易筋经"></property>
     <property name="bauthor" value="达摩老祖"></property>
 </bean>

 <!-- 2、有参数构造注入属性-->
 <bean id="orders" class="IoC.bean.Book">
     <constructor-arg name="bname" value="666"></constructor-arg>
     <constructor-arg name="bauthor" value="777"></constructor-arg>
 </bean>

 <!-- 3、p空间注入属性-->
 <bean id="book" class="IoC.bean.Book" p:bname="九阳神功" p:bauthor="无名氏"></bean>
 -------------------------
 *
 @author Alex
 @create 2023-02-21-9:43
 */
public class UJ02 {
    // 基于xml 创建对象以及属性注入
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ02.xml");
        Book book = context.getBean("book", Book.class);
        System.out.println(book);
    }
}
