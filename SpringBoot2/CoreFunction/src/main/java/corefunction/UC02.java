package corefunction;

/**
 * 【依赖管理 与 自动版本仲裁机制】
 *  依赖管理：通过 parent标签 实现依赖管理，
 *            首先通过parent标签所依赖的父项目为 spring-boot-starter-parent（ctrl点击），版本号为 2.3.4.RELEASE
 *            点击后看到所依赖的父项目为 spring-boot-dependencies（ctrl点击），
 *            在这其中，就几乎声明了当前版本springboot会用到的所有依赖。
 *  自动版本仲裁机制：在通过 dependency 标签 引入依赖时，可以不写版本号。可以根据自动版本仲裁机制，自动引入 spring-boot-dependencies 中规定的依赖版本。
 *  当我们所需依赖的版本与默认版本不同时：
 * （1）可以在dependency标签中使用version标签指定其版本号
 * （2）也可以使用如下方式抽取版本号到properties标签中（该步骤可以通过选中version标签的值，右键==>refactor==>property完成快速抽取）
 *      springboot会根据就近原则，加载我们声明的配置
-------------------
<properties>
    <java.version>8</java.version>
    <druid.version>1.1.17</druid.version>
</properties>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>${druid.version}</version>
</dependency>
-------------------
 * 【场景启动器 starter】
 *  引用依赖时可以直接使用 场景启动器starter，只要引入starter，这个场景的所需要的依赖都自动引入。。。目前starter几乎已经支持所有常用的场景
 *  spring-boot-starter-*                       # 官方提供的场景启动器。 *就代表某种场景。如web等..
 *  *-spring-boot-starter                       # 第三方提供的场景启动器。
 *  pom.xml右键 - diagrams - show dependency    # 查看依赖树，可以看到 所有场景启动器最底层都依赖 spring-boot-starter
 *
 * 【场景启动器的自动配置】引入了相关场景启动器将实现相关的自动配置，以web-starter为例，详见UC04.java
 * （1）自动配置tomcat
 *  (2) 自动配置了web.xml的功能（字符集编码问题等）
 * （3）自动配置了springMVC.xml的功能（thymeleaf视图解析器，文件上传解析器等）
 * （4）✔自动配置了默认的包扫描结构： 主程序所在包 及其 下面的所有子包 的组件都会被扫描
 *      若要改变默认的包扫描位置：在主程序中使用 @SpringBootApplication(scanBasePackages="") 即可
 *      或者使用 @ComponentScan指定扫描路径，但是 @SpringBootApplication注解中已经使用了该注解了，不能重复注解，所以硬要用的话可以用 @SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan 来标记主程序
 * （5）自动的配置都拥有默认值
 *      默认配置最终都是映射到某个类上，如：MultipartProperties
 *      配置文件的值最终会绑定每个类上，这个类会在容器中创建对象
 *
 @author Alex
 @create 2023-03-18-11:01
 */

public class UC02 {

}
