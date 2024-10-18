package IoC;

import IoC.bean.Book;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 【IOC（概念和原理）】
 *  什么是IOC（Inversion of Control）
 *    IOC控制反转是一种设计思想，而不是一个具体的技术实现。即 IoC 的思想就是将原本在程序中手动创建对象的控制权，交由 Spring 框架来管理
 *    使用IOC目的（好处）：将对象之间的相互依赖关系交给 IoC 容器来管理，并由 IoC 容器完成对象的注入。这样可以很大程度上简化应用的开发，
 *                        降低代码耦合度，大大增加了项目的可维护性且降低了开发难度。把应用从复杂的依赖关系中解放出来。
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
 * 【IOC的理解】
 *  对象 a 依赖了对象 b，当对象 a 需要使用对象 b 的时候必须自己去创建。
 *   但是当系统引入了 IOC 容器后， 对象 a 和对象 b 之间就失去了直接的联系。
 *   这个时候，当对象 a 需要使用 对象 b 的时候，我们可以指定 IOC 容器去创建一个对象 b 注入到对象 a 中。
 *   对象 a 获得依赖对象 b 的过程, 由主动行为变为了被动行为，控制权反转，这就是控制反转名字的由来
 *
 * 【依赖注入】
 *  依赖注入是实现控制反转的一种设计模式，在传统的程序设计过程中，通常由调用者来创建`被调用者`的实例。
 *   但在Spring里，创建`被调用者`的工作不再由调用者来完成，创建`被调用者`实例的工作通常由Spring容器来完成，然后注入调用者，这个过程也称为依赖注入。
 *  依赖注入的3种实现方式分别是：接口注入（interface injection）、Set注入（setter injection）和构造注入（constructor injection）
 *
 * 【bean管理】
 *  Bean管理指的是两个操作：（1）Spring创建对象（2）Spirng注入属性
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
