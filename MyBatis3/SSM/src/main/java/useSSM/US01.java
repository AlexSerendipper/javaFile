package useSSM;

/**
 * 【SSM整合概述】
 *  整合的涵义？
 *   即让SpringMVC 和 Spring5各自创建自己的IOC，各自管理自己的组件
 *  ContextLoaderListener的使用！
 *   SpringMVC负责web层（控制层controller），即代替了传统的servlet功能
 *   其IOC容器是在dispatcherServlet初始化过程中进行创建的（又因为我们配置了load-on-startup标签，故初始化过程在服务器启动时完成）
 *   ✔✔✔Spring负责业务层（Service层），在SpringMVC的controller中，需要完成对service的自动装配（web层对service层的调用）
 *   故 spring中的IOC需要在springMVC的IOC创建前 完成创建
 *   根据javaWeb三大组件的执行顺序，可以将springMVC的IOC创建放于过滤器或监听器的初始化方法中。由于IOC只需要创建一次，所以使用监听器
 *   最终使用ContextLoaderListener（实现了ServletContextListener接口，负责监视servletContext对象的创建与销毁，servletContext在服务器打开时创建，关闭时销毁）
 *  具体使用方法，配置于web.xml
 *  为什么在SpringMVC中可以调用Spring的IOC？
 *   因为在SpringMVC创建IOC容器的方法中，即protected WebApplicationContext createWebApplicationContext()中，有一行wac.setParent(parent)
 *   即设置了Spring的IOC容器为父容器，实现的效果是子容器可以访问父容器中的bean对象（而父容器不能访问子容器中的bean对象）
 *
 * 【SSM整合流程】
 * 【第一步：导入相关依赖】
----------------------------
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
    <groupId>org.example</groupId>
    <artifactId>SSM</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <!--设置自定义属性，依赖中直接使用这里的自定义属性就好,实现根据springVersion导入依赖-->
        <spring.version>5.3.1</spring.version>
    </properties>

    <!--打包方式-->
    <packaging>war</packaging>

    <dependencies>
        <!--context上下文依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!--bean依赖，管理bean-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!--springmvc-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!--springmvc中的事务管理器 需要用到jdbc中的类-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!--切面 管理包-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!--测试整合包，整合Junit-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!-- Mybatis核心依赖 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.7</version>
        </dependency>
        <!--mybatis和spring的整合包-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.6</version>
        </dependency>
        <!-- 德鲁伊连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.9</version>
        </dependency>
        <!-- junit测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!-- MySQL驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
        </dependency>
        <!-- log4j日志 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <!-- 分页插件https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.2.0</version>
        </dependency>
        <!-- slf4j的具体日志实现，其中slf4j的门面 -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
        <!-- ServletAPI，前端控制器dispatcherServlet继承的就是这个依赖下的HttpServlet -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
        <scope>provided</scope>
        </dependency>
        <!-- 处理json数据的依赖 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.1</version>
        </dependency>
        <!-- 文件上传依赖 -->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>
        </dependency>
        <!-- Spring5和Thymeleaf整合包 -->
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
            <version>3.0.12.RELEASE</version>
        </dependency>
    </dependencies>
</project>
----------------------------
 *
 @author Alex
 @create 2023-03-16-16:21
 */
public class US01 {
}
