package aop;

import aop.bean.User;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.annotation.Annotation;

/**
 * 【AOP操作准备工作】基于AspectJ实现AOP操作
 *  Spring 框架一般都是结合AspectJ实现AOP操作
 *  AspectJ 是独立的AOP框架，并不是Spring组成部分
 *  基于AspectJ实现AOP操作，可以通过基于xml配置文件实现、也可以通过基于注解方式实现（使用）
 *                          实际开发中一般都是使用基于注解的方式实现
 *  在项目工程里面引入AOP相关依赖
 *    com.springsource.net.sf.cglib-2.2.0.jar
 *    com.springsource.org.aopalliance-1.0.0.jar
 *    com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
 *
 * 【切入点表达式的使用】✔
 *    切入点表达式的作用：配置对哪个类里面的哪个方法进行增强
 *    语法结构： execution([权限修饰符] [返回类型] [类全路径] [方法名称]([参数列表]))
 *               通常权限修饰符写 * ，表示任意的权限修饰符
 *               返回值类型省略，类全路径与方法名称写一起
 *               用..表示任意形参
 *    举例1：对com.atguigu.dao.BookDao类里面的 add 进行增强
 *            execution(* com.atguigu.dao.BookDao.add(..))
 *    举例2：对com.atguigu.dao包里面所有类，类里面所有方法进行增强
 *            execution(* com.atguigu.dao.*.* (..))
 *
 * 【AOP操作流程】常用见UserProxy。基于AspectJ注解
 * (1) 创建被增强类(被代理类): User
 * (2) 创建增强类（编写增强逻辑）: UserProxy，在增强类中创建方法，让不同方法代表不同通知类型
 * (3) AOP使用配置
 *      引入aspect名称空间，并在spring配置文件中利用 <aop:aspectj-autoproxy> 开启生成代理对象
 *      通过配置文件 或 配置类开启注解扫描（增强类和被增强类需要交由IOC管理）
 *      增强类上添加 @Aspect 注解
 * (4) 在增强类中，为作为 通知的方法上面 添加通知类型注解，使用切入点表达式配置
 *      @Before(value = "execution(* aop.bean.User.add(..))")           # 前置通知，增强User中的add方法
 *      @AfterReturning(value = )                                       # 后置通知
 *      @After(value = )                                                # 最终通知
 *      @AfterThrowing(value = )                                        # 异常通知
 *      @Around(value = )                                               # 环绕通知
 * (5)其他操作0：环绕通知中需要声明ProceedingJoinPoint proceedingJoinPoint，代表被增强的方法！使用如下方法执行被增强的方法
 *                 proceedingJoinPoint.proceed();
 *               其他所有通知中可以声明JoinPoint对象，代表被增强的方法！！！并可以通过如下方法获取其类路径和方法名
 *                String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
 * (6)其他操作1：相同切入点抽取✔
 *      在增强类中创建一个方法，并添加注解 @Pointcut(value = "execution(* aop.bean.User.add(..))")
 *      为作为 通知的方法上面 添加通知类型注解时，使用该方法作为值即可。@Before(value = "方法")
 * (7)其他操作2：当有多个增强类对同一个方法进行增强（如userproxy和personproxy都对user类的add方法进行增强），可以设置增强类优先级
 *      在增强类上面添加注解 @Order(数字类型值)，数字类型值越小优先级越高
-------------------------------------------------
 <?xml version="1.0" encoding="UTF-8"?>
 <beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xmlns:context="http://www.springframework.org/schema/context"
 xmlns:aop="http://www.springframework.org/schema/aop"
 xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                     http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                     http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

 <!-- 开启注解扫描 -->
 <context:component-scan base-package="aop"></context:component-scan>

 <!-- 开启 Aspect 生成代理对象-->
 <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
--------------------------------------------------
--------------------------------------------------
 @Component
 @Aspect  // 生成代理对象
 public class UserProxy {
     @Pointcut(value = "execution(* aop.bean.User.add(..))")
     public void pointdemo() {

     }

     // 前置通知
     @Before(value = "execution(* aop.bean.User.add(..))")
     public void before(JoinPoint joinPoint) {
         // 除环绕通知外，其他通知也可以声明JoinPoint对象，代表当前执行的方法！！！并可以通过如下方法获取其类路径和方法名
         String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
         System.out.println("前置通知UserProxy before......");
         System.out.println(method + "被执行了");
     }

     // 环绕通知
     @Around(value = "pointdemo()")
     public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
         System.out.println("环绕之前Around.........");
         // 执行被增强的方法
         proceedingJoinPoint.proceed();
         System.out.println("环绕之后Around.........");
     }
 }
--------------------------------------------------
 *
 *
 * 【纯注解开发】创建建配置类，替代xml配置文件，从而实现纯注解开发（实际开发中，常用springboot实现纯注解开发）
 *   @Configuration                                                # 在配置类上使用该注解
 *   @ComponentScan(basePackages = {"aop"})                        # 设置组件扫描路径
 *   @EnableAspectJAutoProxy                                       # 开启 Aspect 生成代理对象
 *   new AnnotationConfigApplicationContext(SpringConfig.class);   # 加载配置类，等同于加载xml配置文件
 *
 @author Alex
 @create 2023-02-23-15:13
 */
public class UA02 {
    // 1、查看5个类型的通知
    // 2、查看前置注解的优先级
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UA02.xml");
        User user = context.getBean("user", User.class);
        user.add();
    }

    // 体会纯注解开发
    @Test
    public void test2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        User user = context.getBean("user", User.class);
        user.add();
    }
}
