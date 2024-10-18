package IoC;

import IoC.bean.Emp;
import IoC.service.AnnotationService;
import IoC.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【基于注解的bean管理】
 *  注解是代码特殊标记，格式：@注解名称(属性名称=属性值, 属性名称=属性值..)
 *   注解可以作用在类上面，方法上面，属性上面
 *   使用注解目的：简化 xml 配置
 *
 * 【基于注解创建对象】Bean管理：创建对象注解
 *  以下四个注解，都可以用来创建bean实例
 *    @Component        # 普通创建
 *    @Service          # 建议用于service层
 *    @Controller       # 建议用于web层
 *    @Repository       # 建议用于dao层
 *  创建对象流程
 *  （1）引入spring-aop-5.2.6.RELEASE.jar包
 *  （2）✔开启组件扫描（即设置用于创建对象的 包的位置，包中被注解的类用于创建对象和注入属性）
 *       引入context名称空间(见UJ07)
 *       <context:component-scan base-package="IoC"></context:component-scan>                           # 开启组件扫描, 默认filter扫描包中的所有内容。若扫描多个包，使用逗号隔开
 *       <context:component-scan base-package="com.atguigu" use-default-filters="false">
 *              <!-- 可以进一步细化，use-default-filters="false" 表示现在不使用默认filter，需自己配置filter
 *                   include-filter：表示根据注解(annotation)，扫描出带有controller类型的类
 *                   exclude-filter：不需要配置use-default-filters="false"。使用默认filter扫描包中所有类。除了不带有controller类型的类 -->
 *              <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
 *              <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
 *         </context:component-scan>
 *   (3) ✔创建类，在类上面添加创建对象注解
 *       @Component(value = "annotationService")
 *         注解中，value属性值相当于仔xml中配置的bean的id值
 *         value属性可以省略不写，默认值是类名称的小驼峰形式（首字母小写）
 *
 * 【基于注解注入属性】@Autowired, @Qualifier, @Resource, @Value
 * 【普通属性注入】part1
 * （1） @Value(value="")
 *     在需要注入的普通属性上使用注解，value值传入注入的数据
 * 【对象属性注入】part2
 *  ✔注入属性前，需要在相关类中添加 对象创建 注解（如在Userservice中使用@Service）
 *  ✔在需要注入的对象属性上使用注解（@Autowired）（✔在userservice中有一个userdao属性，由于userdao是接口，会自动寻找他的实现类进行赋值）
 *  ✔注意：不同xml配置，使用注解的方式不需要为对象属性设置set方法
 * （1） @Autowired✔✔
 *     根据bean类型进行自动装配(即byType)
 * （2） @Qualifier(value="")
 *     根据bean名称进行注入(即byName)，需要和 @Autowired配合使用
 *     当传入的属性有多个实现类时，需要根据名称进行区分（当userDao有多个实现类时可以使用）
 * （3） @Resource
 *     既可以根据类型注入，可以根据名称注入
 *     @Resource，即为根据属性类型进行自动装配，相当于 @Autowired
 *     @Resource(name="")，即为根据名称进行注入，相当于 @Autowired + @Qualifier
 *
 * 【纯注解开发】创建建配置类，替代xml配置文件，从而实现纯注解开发（实际开发中，常用springboot实现纯注解开发）
 *   @Configuration                                                # 在配置类上使用该注解
 *   @ComponentScan(basePackages = {"Ioc.IoC"})                    # 设置组件扫描路径
 *   new AnnotationConfigApplicationContext(SpringConfig.class);   # 加载配置类，等同于加载xml配置文件
--------------------------------
<bean name="article1" class="com.sss.Article">
    <property name="text" value="Content of the 1st Article" />
</bean>

<bean name="article2" class="com.sss.Article">
    <property name="text" value="Content of the 2nd Article" />
</bean>

@Qualifier(value="article1")
@Autowired
private Article firstArticle;

@Qualifier(value="article2")
@Autowired
private Article secondArticle;
--------------------------------
 @author Alex
 @create 2023-02-22-19:27
 */
public class UJ08 {
    // 测试基于注解的创建对象
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ08.xml");
        AnnotationService annotationService = context.getBean("annotationService", AnnotationService.class);
        annotationService.add();
    }

    // 测试基于注解的注入属性（普通属性以及对象属性）
    // 将userdao注入userservice
    // 可以看到userservice中使用的userdao类，而不是userdaoimpl类，因为在组件扫描时，会自动寻找他的实现类进行赋值（多态）
    // 但是可能一个dao对应了多个实现类，这时候就要用到@Qualifier，根据名称进行注入
    @Test
    public void test2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ08.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
        System.out.println(userService.name);
    }

    // 测试纯注解开发
    @Test
    public void test3(){
        //加载配置类
        ApplicationContext context= new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
        System.out.println(userService.name);
    }
}
