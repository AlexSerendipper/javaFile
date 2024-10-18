package corefunction;

import corefunction.pojo.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 【主程序使用】
 *  @SpringBootApplication(scanBasePackages="",exclude = {})                                    # 设置于主启动类上，设置包扫描位置
 *                                                                                                  可以设置不加载某些自动配置类，如DataSourceAutoConfiguration.class
 *  @MapperScan(value = "")                                                                     # 设置mapper接口的包扫描路径
------------
@SpringBootApplication(scanBasePackages = "")
@MapperScan(value = "")
public class DataFunctionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataFunctionApplication.class, args);
    }
}
------------
 *  ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);    # 返回IOC容器
 *  String[] names = run.getBeanDefinitionNames();                                              # 查看容器中的组件
 *  User uu = run.getBean("uu", User.class);                                                    # 从容器中获取组件
 *  run.containsBean("zzj")                                                                     # 判断容器中是否包含名为zzj的组件
 *
--------------------------
// 1、 返回IOC容器
ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
// 2、查看容器中的组件
String[] names = run.getBeanDefinitionNames();
// 3、从容器中获取组件
UC03config uc03config = run.getBean(UC03config.class);
User zzj1 = run.getBean("zzj", User.class);
User zzj2 = run.getBean("zzj", User.class);
Pet hyq = run.getBean("hyq", Pet.class);

System.out.println(zzj1);
System.out.println("从容器中获取的组件是否为单实例: ");
System.out.println(zzj1==zzj2);
System.out.println("从容器中取出的config类对象: " + uc03config);
System.out.println("每次bean方法被调用，创建的是否为单实例对象，检测1: ");
System.out.println(zzj1.getPet() == hyq);
System.out.println("每次bean方法被调用，创建的是否为单实例对象，检测2: ");
System.out.println(uc03config.pp() == uc03config.pp());;
--------------------------
 *
 * 【Springboot底层注解】常用于配置类中！！！见UC03config
 * （1） @Configuration(proxyBeanMethods=false) ：配置类
 *      使用该注解，告诉SpringBoot这是一个配置类（代替了原SSM的.xml文件配置功能）
 *      配置类本身也是组件。可以获取，如：run.getBean(UC03config.class);
 *      full模式(即proxyBeanMethods设置为true时)，表示代理bean方法，每次 @Bean方法被调用 时，都会检查容器中是否已经存在实例，保证无论被调用多少次 返回都是单实例
 *        当配置类组件之间有依赖关系时，推荐使用Full模式。✔
 *      lite模式(即proxyBeanMethods设置为false时)，表示非代理bean，每个 @Bean方法被调用 时，保证每次调用 返回的组件都是新创建的
 *        当配置类组件之间无依赖关系，推荐用Lite模式，可以减少判断、加速容器启动过程。✔
 *
 * （2） @Bean(value="") ：普通装配
 *      给配置类中添加组件。✔默认注册的组件为 单实例的（ 即使用run.getBean()方法，获取到的都是同一个对象 ）
 *      默认方法名 作为组件的id (可通过value属性进行设置)
 *      返回值类型 就是组件类型。
 *      返回值 就是在容器中创建的实例。
 *      ✔原先配置组件的的标签 @Component、@Controller、@Service、@Repository仍可用 (即把当前类注册为组件，组件id为类名)
 *
 * （3）@import({xxx.class,xxx.class}) ：import装配
 *      import可以为 配置类 或 其他controller 导入组件，最后的结果与使用@bean相同
 *      import的组件默认id为，组件的全类名
 *
 * （4）@Conditional ： 条件装配✔✔
 *      该注解有非常多的派生注解，以@ConditionalOnBean(name="xxx")为例
 *       标记于 @Configuration配置类 之上时，表示当IOC中存在名为xxx的bean时，则装配该配置类中的所有bean组件
 *       标记于 @Bean之上时，表示当IOC中存在名为xxx的bean时，则装配该bean组件
 *       （若标记在配置类之上，就不要拿配置类中的bean组件作为判断条件啦，这时肯定都是不存在于IOC组件中的）
 *
 * （5）@ImportResource("classpath:UC01.xml")
 *      可能某些公司仍在使用老式装配bean对象的方式（.xml），使用该注解标记配置类 能够很方便的将.xml文件中bean全部装配到容器中
 *
 * （6）@ConfigurationProperties(prefix = "mycar") ：标记于javabean之上，用于读取到 properties文件中的内容，并且把它封装到JavaBean中（即 自动绑定）
 *      与 application.properties 中指定前缀的内容进行绑定。如 mycar.price = 100
 *      方式一： 为容器中的组件 (@Component) 添加 @ConfigurationProperties。就可以实现为 car 的price属性注入 100 这个值
 *      方式二： 为配置类(@Configuration) 添加 @EnableConfigurationProperties(Car.class)，即 开启 car 的自动绑定功能，并将 car 装配到容器中
 *
 * （7）@value: 可以读取到 properties文件中的内容(使用${}包裹)，并将其赋值给一个变量
 *      @Value("${wk.image.storage}")
 *       private String wkImageStorage;
 *
 @author Alex
 @create 2023-03-18-13:08
 */

@RestController
public class UC03 {
    @Autowired
    private Car car;

    // 读取application.properties中配置的内容到 Car类中
    @RequestMapping("/car")
    public Car cc(){
        return car;
    }
}
