package transaction;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import transaction.service.UserService;

/**
 * 【声明式事务管理】基于xml配置文件方式，了解。
 *                 由于没教用xml的方式实现jdbctemplate中注入datasource,所以只能通过关闭UserService中的@Transactional注解的方式实现
 *  第一步：配置事务管理器
 *  第二步：配置通知（此处事务即为我们要增强的部分，即为通知）
 *  第三步：配置切入点和切面（把事务加到具体哪个方法上）
-----------------------
 <!--1 创建事务管理器-->
 <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
     <!--注入数据源-->
     <property name="dataSource" ref="dataSource"></property>
 </bean>

 <!--2 配置通知-->
 <tx:advice id="txadvice">
     <!--配置事务参数-->
     <tx:attributes>
         <!--指定方法上面添加事务,相当于在该方法上添加注解-->
         <tx:method name="accountMoney" propagation="REQUIRED"/>
         <!--或者用下面的形式，表示方法以account开头的都添加事务-->
         <!--<tx:method name="account*"/>-->
     </tx:attributes>
 </tx:advice>

 <!--3 配置切入点和切面-->
 <aop:config>
     <!--配置切入点-->
     <aop:pointcut id="pt" expression="execution(* transaction.service.UserService.*(..))"/>
     <!--配置切面-->
     <aop:advisor advice-ref="txadvice" pointcut-ref="pt"/>
 </aop:config>
-----------------------------------------
 *
 @author Alex
 @create 2023-02-27-13:37
 */
public class UT02 {
    // 声明式事务管理：基于xml配置文件方式
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UT02.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }
}
