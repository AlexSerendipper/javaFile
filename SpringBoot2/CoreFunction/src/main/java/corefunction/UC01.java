package corefunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 【SpringBoot 概述】了解
 *  SpringBoot底层实际上是SpringFramework，是整合Spring技术栈的一站式框架，是简化Spring技术栈的快速开发脚手架
 *  随着Spring5的重大升级（即引入了响应式编程），SpringBoot2引入了两套方案，一套是传统技术栈Servlet Stack（即SSM），另一套是Reactive Stack（响应式开发）
 *  SpringBoot优点：
 *   能快速创建出生产级别的Spring应用、创建独立Spring应用
 *   内嵌web服务器（内置tomcat）
 *   自动依赖（starter），简化构建配置
 *   自动配置Spring以及第三方功能
 *   提供生产级别的监控、健康检查及外部化配置
 *   无代码生成、无需编写XML
 *  SpringBoot缺点：迭代快，需要时刻关注变化
 *                    封装太深，内部原理复杂，不容易精通
 *  SpringBoot应用场景：微服务是一种架构风格，即将一个应用拆分为一组小型服务，每个服务运行在自己的进程内，也就是可独立部署和升级。
 *                      要实现微服务，就会出现分布式的一些问题....而分布式的解决方案就是 SpringBoot + SpringCloud
 *                      SpringBoot负责创建原生应用，SpringCloud原生应用如何上云
 *
 * 【SpringBoot使用流程】
 * （1）引入依赖（pom.xml）
 *      ✔注意：如果是当前module不是project，是project下的子module，配置父标签时需要加上<relativePath />
------------------
<!--springboot通用依赖模块-->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.4.RELEASE</version>
    <relativePath />
</parent>

<!--springboot提供的启动器，包含所有web开发所需的资源-->
<dependencies>
    <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
-------------------
 *
 * （2）创建主程序：使用 @SpringBootApplication 对主程序进行标记
------------------
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
------------------
 * （3）创建控制层（默认需要在主程序包下）
 *  直接运行main方法即可进行访问
 *
 * （4）方便修改配置：利用springboot的核心配置文件
 *  所有的相关配置（tomcat、ssm等）都抽取在 application.properties 中
 *  server.port=8888，如可以直接修改tomcat的端口号
 *
 * （5）简化部署（pom.xml）
 *  配置该插件后，会把项目打成jar包，打包后的文件可以直接在目标服务器执行
 *  打包后的文件见 target-corefunction-2.3.4RELEASE.jar
--------------------
<!--配置该插件后，会把项目打成jar包，可以直接在目标服务器执行-->
<!--注意，这里引入的版本号需要和springboot的版本号一致，否则报错-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <version>2.3.4.RELEASE</version>
        </plugin>
    </plugins>
</build>
--------------------
 *
 *【SpringBoot部署 与 传统的SSM部署的区别】
 * 1、引入相关场景的启动器后，会引入相关场景下的所有依赖，无需像SSM一样需要在pom.xml中导入
 * 2、引入相关场景的启动器后，会自动加载自动配置类进行配置（见UC02.java），
 *    无需像SSM一样，配置web.xml、spring.xml、springMVC.xml等，
 *    常见的需要修改的配置都抽取到了application.properties中
 * 3、springboot项目通过主程序启动项目，SSM通过tomcat服务器启动项目
 *
 @author Alex
 @create 2023-03-17-22:23
 */

// 可以使用@RestController（相当于在类上添加了 @Controller  和 @ResponseBody）
@Controller
@ResponseBody
public class UC01 {
    @RequestMapping("/hello")
    public String handle01(){
        return "Hello, Spring Boot 2!";
    }

}
