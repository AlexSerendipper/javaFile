package webflux;

/**
 * 【Spring5新功能4】整合测试框架
 *  整合junit4
 *   (1) 引入测试相关依赖spring-test-5.2.6.RELEASE.jar
 *                      junit-4.13.1.jar
 *   (2) 创建测试类，使用注解方式完成
 *       @RunWith(SpringJUnit4ClassRunner.class)              # 使用单元测试框架JUnit4，让测试运行于spring环境
 *       @ContextConfiguration("classpath:UW03.xml")          # 加载配置文件，两个标签一般配合使用
 *
 @author Alex
 @create 2023-02-28-11:27
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import webflux.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:UW03.xml")
public class UW03 {
    // 示例，进行两个用户之间的转账操作(考虑事务)
    // 传统做法，需要手动读取xml文件
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UW03.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }


    // 示例，进行两个用户之间的转账操作(考虑事务)
    // 现在做法，使用@ContextConfiguration读取xml文件
    @Autowired
    private UserService userService;
    @Test
    public void test2(){
        userService.accountMoney();
    }
}
