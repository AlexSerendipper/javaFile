package webflux;

import com.sun.javaws.Main;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.Nullable;
import webflux.entity.User;


/**
 * 【Spring5新功能2】框架核心容器支持@Nullable注解，见User类
 *  @Nullable 注解可以使用在方法上面，属性上面，参数上面
 *   注解用在方法上面，表示方法返回值可以为空
 *   注解使用在属性上面，表示属性值可以为空
 *   注解用在参数值上面，表示参数值可以为空
 *  但是好像本来就可以传入null值，不知道这个注解的意义何在...
 *
 * 【Spring5新功能3】Spring5核心容器支持函数式风格（即lambda表达式）GenericApplicationContext，一般不使用✔
 *  我们手动new的对象，spring是没法进行管理的，可以通过GenericApplicationContext，将我们手动new的对象注册到spring中，方便spring进行统一管理
 *
 @author Alex
 @create 2023-02-28-10:52
 */
public class UW02 {
    // 测试@Nullable注解
    @Test
    public void test1(){
        User uu = new User();
        uu.setName(null);
        System.out.println(uu.getName());
    }


    // 测试函数式风格GenericApplicationContext
    @Test
    public void test2(){
        //1 创建 GenericApplicationContext 对象
        GenericApplicationContext context = new GenericApplicationContext();
        //2 调用 context 的方法对象注册
        context.refresh();
        context.registerBean("user1", User.class, () -> new User());
        //3 获取在 spring 注册的对象（当不设置beanName时，使用类的全路径进行注册）
        // User user = (User)context.getBean("com.atguigu.spring5.test.User");
        User user = (User)context.getBean("user1");
        System.out.println(user);
    }
}
