package datafunction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 【数据库访问1】SQL
 * （1）引入JBDC场景依赖（自动完成数据源、jdbc、事务 的相关组件）
 *      DataSourceAutoConfiguration                         # 数据源的自动配置类（数据源相关配置绑定在spring.datasource中
 *                                                                                 自己容器中没有DataSource才自动配置连接池，
 *                                                                                 底层配置好的连接池是HikariDataSource✔）
 *      DataSourceTransactionManagerAutoConfiguration       # 事务管理器的自动配置类
 *      JdbcTemplateAutoConfiguration                       # JdbcTemplate的自动配置类，可以来对数据库进行crud(jdbc相关配置绑定在spring.jdbc中)
 *      JndiDataSourceAutoConfiguration                     # jndi的自动配置类
 *      XADataSourceAutoConfiguration：                     # 分布式事务相关的自动配置类
 * （2）导入mysql驱动依赖，注意这里一定要指定版本（按照springboot的版本仲裁可能出现问题）
-------------------------
<!--JDBC场景依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
<!--mysql驱动依赖-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.11</version>
</dependency>
-------------------------
 *（3）修改数据源配置(默认数据源)
 *   spring.datasource.url=jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true
 *   spring.datasource.username=root
 *   spring.datasource.password=qqabcd
 *   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 *   spring.datasource.type=com.zaxxer.hikari.HikariDataSource
 *
 * 【整合druid数据源】
 *   方式一：自定义
 *  （1）引入依赖
------------
<!--druid数据源依赖-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.17</version>
</dependency>
------------
 *  （2）druid监控功能：创建配置类（依据官方文档修改信息, 如监控页、等。https://github.com/alibaba/druid）
------------
 // 底层配置HikariDataSource时，是根据@ConditionalOnMissingBean(DataSource.class)
 // 所以我们为容器中添加组件，就是使用我们自己添加的组件
 // ✔✔与配置文件绑定,创建druid数据源,如果不需要其他功能,只用这个就好了
@ConfigurationProperties("spring.datasource")
@Bean
public DataSource dataSource(){
     DruidDataSource dataSource = new DruidDataSource();
     // 开启数据监控功能stat（开启后才能使用监控页功能）
     // 开启防火墙功能wall
     dataSource.setFilters("stat,wall");
     return dataSource;
    }

// 添加druid的监控页功能
@Bean
public ServletRegistrationBean statViewServlet(){
     StatViewServlet statViewServlet = new StatViewServlet();
     ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
     registrationBean.addInitParameter("loginUsername", "admin");
     registrationBean.addInitParameter("loginPassword", "admin");
     return registrationBean;
}

// 添加druid的web应用监控
@Bean
public FilterRegistrationBean WebStatFilter(){
    WebStatFilter webStatFilter = new WebStatFilter();
    FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<WebStatFilter>(webStatFilter);
    registrationBean.setUrlPatterns(Arrays.asList("/*"));
    registrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    return registrationBean;
}
------------
 *   方式二（推荐）：使用官方的 starter (自动配置好  监控SpringBean（默认关闭）、监控页（默认关闭）、 web监控（默认关闭）、各种filter组件（stat、wall等）)
------------
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.17</version>
</dependency>
------------
------------
# 常见配置如下
# 监控页功能
spring.datasource.druid.stat-view-servlet.enabled=true
spring.datasource.druid.stat-view-servlet.login-username=zzj
spring.datasource.druid.stat-view-servlet.login-password=zzj
# web监控功能
spring.datasource.druid.web-stat-filter.enabled=true
spring.datasource.druid.web-stat-filter.url-pattern=/*
spring.datasource.druid.web-stat-filter.exclusions="*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
# filter(stat是sql监控功能,wall是防火墙功能)
spring.datasource.druid.filters=stat,wall
# 监控springbean功能,aop(设置监控的包的全路径)
spring.datasource.druid.aop-patterns=datafunction.*
------------
 *
 @author Alex
 @create 2023-03-23-14:31
 */
@RestController
public class UD01 {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/sql")
    public String count(){
        Long i = jdbcTemplate.queryForObject("select count(*) from springboot_emp", Long.class);
        return i.toString();
    }
}
