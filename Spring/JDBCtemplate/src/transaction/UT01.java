package transaction;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import transaction.service.UserService;

/**
 * 【事务】
 *  要么成功，要么回滚：事务是数据库操作最基本单元，逻辑上一组操作，要么都成功，如果有一个失败所有操作都失败

 * 【事务四个特性（ACID）】
 *  原子性：整体性，一组操作中要么都成功，要么都失败
 *  一致性：操作前和操作后总量不变
 *  隔离性：多事务操作，彼此不会产生影响
 *  持久性：事务成功后，表中数据切实发生变化
 *
 * 【事务管理操作的两种方式】
 *  事务添加到 JavaEE 三层结构里面 Service 层（业务逻辑层）
 *  在Spring进行事务管理操作有两种方式：编程式事务管理和声明式事务管理（使用）
 *    编程式事务管理即根据具体的程序代码，按照（1）开启事务（2）进行业务操作（3）没有发生异常则提交事务（4）发生异常，回滚 的流程编写。✔实际中很少使用
 *
 * 【声明式事务管理】
 *  在 Spring 进行声明式事务管理，底层使用 AOP 原理
 *  Spring框架中提供了一个接口，代表事务管理器，这个接口针对不同的框架提供不同的实现类
 *   如：针对jdbctemplate和mybatis框架，都是使用DataSourceTransactionManager这个实现类✔
 *  有两种方式实现声明式的事务管理，基于xml配置方式 和 基于注解方式
 *    一般都是用注解方式进行事务管理
 *
 * 【声明式事务管理】基于注解方式，配置见下方
 * (1)引入名称空间 tx
 * (2)开启事务注解,使用<tx:annotation-driven></tx:annotation-driven>
 * (3)创建事务管理器
 * (4)为service中的类上面（或者为service类中的方法上面）添加事务注解@Transactional
 *    如果把这个注解添加类上面，这个类里面所有的方法都添加事务
 *    如果把这个注解添加方法上面，为这个方法添加事务
 *
 * 【声明式事务管理参数配置】@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
 *                         @Transactional，这个注解里面可以配置事务相关参数
 *（1）propagation：事务传播行为：当一个事务方法被另外一个事务方法调用时候，这个事务方法如何进行
 *     REQUIRED（默认）:  当事务A中存在方法1，当事务B中存在方法2，当方法1调用方法2。
 *                         默认事务A和事务B都为REQUIRED
 *                         单独使用方法2，则单独开启事务B
 *                         若先调用方法1，则会开启事务A，方法1调用方法2时，方法2也会加入到当前的事务A中来✔。即事务A出现问题，方法1和方法2都不被执行
 *     REQUIRED_NEW：当事务A中存在方法1，当事务B中存在方法2，当事务C中存在方法3，当方法1调用方法2，方法2调用方法3。
 *                    当设置事务B为REQUIRED_NEW
 *                    单独使用方法2/方法3，则单独开启事务B/事务C
 *                    此时先调用方法1，则会开启事务A，方法1调用方法2时，方法2不会加入到当前的事务A中来✔，当执行到方法3时，方法3会加入到事务A中来
 *                    即事务A出现问题时，方法1和方法3会回滚，方法2仍被执行
 *     SUPPORTS:当事务A中存在方法1，当事务B中存在方法2，当方法1调用方法2。
 *               设置事务B为SUPPORTS
 *               单独使用方法2，不会开启事务B✔，以单独的程序执行
 *               若先调用方法1，则会开启事务A，方法1调用方法2时，方法2也会加入到当前的事务A中来✔。即事务A出现问题，方法1和方法2都不被执行
 * (2) 设置隔离级别（见jdbc中事务的隔离性）
 *  数据库的隔离等级：不同的等级限制了不同的并发范围，实际上隔离等级越高，数据一致性就越好, 但并发性越弱。一般情况下，READ COMMITED就够了，脏读和幻读被认为是可以接受的
 *    READ UNCOMMITED: 所有并发问题未解决
 *    READ COMMITED：只解决了脏读。oracle默认使用该隔离等级
 *    REPEATABLE READ：解决了脏读和不可重复读。MySQL默认使用该隔离等级
 *    SERIALIZABLE：解决了所有并发问题，一般不使用，影响性能
 * (3) timeout：超时时间，设置事务在多长时间内必须进行提交，如果不提交进行回滚。默认值是-1（s）,表示不超时。
 * (4) readOnly：是否只读，readOnly默认值 false，表示可以进行增删改查操作。设置readOnly为true时，只能进行查询操作
 * (5) rollbackFor：回滚。设置出现哪些异常进行事务回滚
 * (6) noRollbackFor：不回滚。设置出现哪些异常不进行事务回滚
---------------------------------------------
<!--创建事务管理器-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
     <!--注入数据源-->
     <property name="dataSource" ref="dataSource"></property>
</bean>

<!--开启事务注解驱动，引用创建的事务管理器。将使用@Transaction标识的类和方法进行事务管理-->
<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
-------------------------------------------------
 *
 * 【纯注解开发】创建建配置类(见SpringConfig，需要创建三个方法)，替代xml配置文件，从而实现纯注解开发（实际开发中，常用springboot实现纯注解开发）
 *   @Configuration                                                # 在配置类上使用该注解
 *   @ComponentScan(basePackages = {"transaction"})                # 设置组件扫描路径
 *   @EnableTransactionManagement                                  # 开启事务。由于在注解扫描时，碰到该注解就会创建一个事务管理器
 *                                                                             所以可能会出现有两个事务管理器的异常
 *   new AnnotationConfigApplicationContext(transaction.SpringConfig.class);   # 加载配置类，等同于加载xml配置文件
 *
 @author Alex
 @create 2023-02-26-16:41
 */
public class UT01 {
    // 示例，进行两个用户之间的转账操作(考虑事务)
    // 运行时，需关闭SpringConfig中的@EnableTransactionManagement注解
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UT01.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }


    // 声明式事务管理：全注解开发
    // 运行时，需开启SpringConfig中的@EnableTransactionManagement注解
    @Test
    public void test2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }
}
