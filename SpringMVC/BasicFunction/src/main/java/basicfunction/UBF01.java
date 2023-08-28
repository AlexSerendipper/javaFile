package basicfunction;

/**
 * 【SpringMVC概述】
 *  SpringMVC 是 Spring 为表述层（web层）开发提供的一整套完备的解决方案。
 *
 * 【SpringMVC的特点】
 *  Spring家族原生产品，与IOC容器等基础设施无缝对接
 *  基于原生的Servlet，通过了功能强大的前端控制器DispatcherServlet，对请求和响应进行统一处理
 *  表述层各细分领域需要解决的问题全方位覆盖，提供全面解决方案
 *  代码清新简洁，大幅度提升开发效率
 *  内部组件化程度高，可插拔式组件即插即用，想要什么功能配置相应组件即可
 *  性能卓著，尤其适合现代大型、超大型互联网项目要求
 *
 * 【创建maven工程】maven:能够对需要导入的jar包进行统一管理
 * （0）配置本地maven仓库
 *  D:\IDEA\IntelliJ IDEA 2022.2.3\plugins\maven\lib\maven3\conf\settings.xml              # 在idea文件路径下找到settings文件，进行修改
-------------------
  大概在55行左右的位置配置本地仓库位置
<localRepository>D:/repository</localRepository>

 大概在160行左右，被</mirrors>标签包裹，配置下载镜像为阿里云，可以加快下载速度
<mirror>
    <id>nexus-aliyun</id>
    <mirrorOf>central</mirrorOf>
    <name>Nexus aliyun</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public</url>
</mirror>

 大概在254行左右, 被</profiles>标签包裹, 绑定jdk版本
<profile>
    <id>jdk-1.8</id>
    <activation>
    <activeByDefault>true</activeByDefault>
    <jdk>1.8</jdk>
    </activation>
    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
    </properties>
</profile>
---------------------
 *（1）配置maven,创建工程
 *  setting - build,execution,deployment - bulid tools-Maven - Maven home path   # 设置Maven路径(使用默认的maven无需进行该步配置)
 *  user settings file                                                           # 需要将maven的配置文件设置为重写的文件
 *  local repository                                                             # 可以看到，此处文件存储路径已经变成了我们在配置文件中设置的
 *  new project - Maven - GroupID/ArtifactID               # 新建Maven工程，其中GroupID 是项目的组织唯一的标识符（公司名）。而ArtifactID就是项目的唯一的标识符，实际对应项目名称
 *
 * （2）pom.xml 引入引入依赖
---------------------------------------
<!--设置打包方式为war包-->
<packaging>war</packaging>
<!--引入依赖-->
<dependencies>
<!-- SpringMVC依赖 -->
<!-- 由于 Maven 的传递性，我们不必将所有需要的包全部配置依赖，而是配置最顶端的依赖，其他靠传递性导入。 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.1</version>
</dependency>
<!-- 日志依赖 -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
</dependency>
<!-- ServletAPI依赖 -->
<!-- tomcat中内含的api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <!-- provided代表已被提供，不会拷贝到webapp-lib下（即artifact下） -->
    <scope>provided</scope>
</dependency>
<!-- Spring5和Thymeleaf整合包依赖 -->
<!-- Thymeleaf视图技术,负责页面跳转，替代jsp的功能 -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
    <version>3.0.12.RELEASE</version>
    </dependency>
</dependencies>
---------------------------------------
 *
 * 【引入web模块】（若选择web模板，则不需要此操作）
 *  在main目录下创建webapp目录
 *  project structure - modules(web resource directory为我们创建的webapp的路径) - deployment descriptors（在此处添加我们的web.xml配置文件）
 *                                               注意设置路径为 D:\IdeaWorkspace\SpringMVC\BasicFunction\src\main\webapp\WEB-INF\web.xml
 *
 *
 * 【web.xml配置】
 *  配置前端控制器：配置DispatcherServlet，用来对请求和响应进行统一处理
---------------------------------------------------------
<!-- 默认配置DispatcherServlet，该配置方式，SpringMVC的配置文件默认位于WEB-INF下，文件名为(servletname标签中设置的值)：SpringMVC-servlet.xml -->
<!-- 由于我们想把配置文件统一放在resources中进行管理，所以默认配置方式并不实用 -->
<servlet>
    <servlet-name>springMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>springMVC</servlet-name>
    <!-- / 表示所匹配的请求可以是/login或.html或.js或.css方式的请求路径。但是不能匹配.jsp请求路径的请求 -->
    <!-- /* 则表示匹配所有的请求，包括.jsp请求 -->
    <url-pattern>/</url-pattern>
</servlet-mapping>

<!-- 扩展配置DispatcherServlet，该配置方式可以自定义 SpringMVC 的配置文件的位置和名字 -->
<!-- 配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理 -->
<servlet>
    <servlet-name>springMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 通过初始化参数指定SpringMVC配置文件的位置和名称 -->
    <init-param>
    <!-- contextConfigLocation为固定值 -->
        <param-name>contextConfigLocation</param-name>
        <!-- 使用classpath:表示从类路径查找配置文件，即从src目录下查找 -->
        <!-- 需要在resources文件夹下提前创建好springMVC.xml 文件 -->
        <param-value>classpath:springMVC.xml</param-value>
    </init-param>
    <!--
    作为框架的核心组件，在启动过程中有大量的初始化操作要做。而这些操作放在第一次请求时才执行会严重影响访问速度
    因此需要通过此标签将启动控制DispatcherServlet的初始化时间提前到服务器启动时
    -->
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>springMVC</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
-----------------------------------------------------------------*
 *
 @author Alex
 @create 2023-03-01-14:07
 */

public class UBF01 {
}
