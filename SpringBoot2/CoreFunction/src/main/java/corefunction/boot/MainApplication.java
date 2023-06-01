package corefunction.boot;

import corefunction.pojo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 主程序类
 * 其中，使用@SpringBootApplication告知系统这是一个SpringBoot应用
 @author Alex
 @create 2023-03-18-10:01
 */
@SpringBootApplication(scanBasePackages = "corefunction")
public class MainApplication {
    public static void main(String[] args) {
        // 1、 返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
        // 2、查看容器中的组件
        String[] names = run.getBeanDefinitionNames();
//        for(String name:names){
//            System.out.println(name);
//        }
        // 3、从容器中获取组件
        System.out.println("run.containsBean(\"zzj\") = " + run.containsBean("zzj"));
        System.out.println("run.containsBean(\"hyq\") = " + run.containsBean("hyq"));
        User user = (User) run.getBean("haha");
        System.out.println(user);
    }

}
