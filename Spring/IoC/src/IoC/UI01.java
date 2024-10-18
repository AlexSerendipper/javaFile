package IoC;

/**
 * 【Spring Framework简介】
 *  Spring Framework是一个开源容器框架，可以接管web层、service层、dao层，并且可以配置各种bean，
 *   和维护bean与bean之间的关系的一个分层的轻量级Java EE开源框架。其目的是用于简化Java企业级应用的开发难度和开发周期
 *  Spring Framework有两个核心部分：IOC和Aop
 *   IOC：控制反转，把创建对象过程交给 Spring 进行管理
 *   Aop：面向切面，不修改源代码进行功能增强！
 *
 * 【spring简介】
 *  ✔ 广义上：spring 泛指以 Spring Framework为核心/基础的Spring技术栈 (spring生态)
 *               即 Spring Framework 最初为仅包含 IOC和AOP模块的框架
 *               经过十多年的发展，Spring Framework逐渐发展成为一个由多个不同模块组成的成熟技术。但是Spring Framework始终是其他子模块的基础
 *               主要包含：Spring AOP，对面向切面编程的支持
 *                        Spring ORM，包含Hibernate、Mybaties的支持、jdbc封装、事务相关
 *                        Spring DAO，包含JDBC、DAO等功能的支持
 *                        Spring Context， spring配置文件的支持，联系上下文，对UI、JNDI、EJB、邮件等的支持
 *                        Spring Web，依托于context，增加对web的支持，Struts那类的
 *                        Spring Web MVC，MVC 容纳了大量视图技术，Jsp那类的
 *                        Spring Core，提供框架基本功能，IOC等
 *              ✔✔✔所以我们约定：Spring指 包含多个模块的，以Spring Framework为基础的技术系统
 *
 *  × 狭义上：Spring特指Spring Framework。建议使用广义解释Spring（区分Spring 与 Spring Framework）
 *  说明：（1）Spring MVC = Spring Web MVC = Web MVC。是SpringFrameWork的后续产品，已经融合在Spring Web Flow里面。
 *              SpringMVC是一种web层mvc框架，用于替代servlet（处理请求，获取表单参数，表单校验等）
 *         （2）Spring boot 是Spring团队提供的全新框架，延续了spring框架的核心思想IOC和AOP，
 *              Spring Boot简化了Spring应用的创建、运行、调试、部署等，使用开发者可以专注于Spring应用的开发，而无需过多关注XML的配置。
 *
 * 【spring特点】了解
 *  方便解耦，简化开发
 *  Aop编程支持
 *  方便程序测试：集成了Junit
 *  方便和其他框架进行整合:如Mybatis等
 *  方便进行事务操作
 *  降低API开发难度：对很多java EE api进行了封装，如jdbc等
 *
 * 【spring下载】下载后根据不同模块的使用需求，导入不同的jar包即可
 *  得到下载地网址：spring.io ==> github ==> 最下方access to binaries ==> Downloading a Distribution ==> https://repo.spring.io
 *  得到下载地地址：https://repo.spring.io ==> artifacts ==> release ==> org ==> springframework ==> spring ==> url to file ==> https://repo.spring.io/artifactory/release/org/springframework/spring/
 *  下载5.2.6：https://repo.spring.io/artifactory/release/org/springframework/spring/ ==> spring-5.2.6.RELEASE-dist.zip
 *
 @author Alex
 @create 2023-02-20-16:17
 */
public class UI01 {
}
