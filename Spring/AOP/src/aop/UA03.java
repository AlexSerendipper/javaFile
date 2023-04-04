package aop;

import aop.bean.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【AOP操作流程】了解，很少使用。基于AspectJ配置文件
 * (1) 在 spring 配置文件中创建两个类对象
 * (2) 在 spring 配置文件中配置切入点
-------------------------------------
 <!--创建对象-->
 <bean id="user" class="aop.bean.User"></bean>
 <bean id="userProxy" class="aop.bean.UserProxy"></bean>
 <!--配置 aop 增强-->
 <aop:config>
     <!--配置切入点, id为别名，表示对user中的add方法进行增强-->
     <aop:pointcut id="p" expression="execution(* aop.bean.User.add())"/>
     <!--配置切面，配置userproxy用于增强user类-->
     <aop:aspect ref="userProxy">
         <!--把userproxy中的before方法，以前置通知的方式，用于增强切入点-->
         <aop:before method="before" pointcut-ref="p"/>
     </aop:aspect>
 </aop:config>
-------------------------------------

 @author Alex
 @create 2023-02-23-21:36
 */
public class UA03 {
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UA03.xml");
        User user = context.getBean("user", User.class);
        user.add();
    }
}
