package useSSM;

/**
 * 【第三步：配置SpringMVC.xml】
---------------------
 <?xml version="1.0" encoding="UTF-8"?>
 <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

     <!--扫描组件-->
     <context:component-scan base-package="controller"></context:component-scan>

     <!--配置视图解析器-->
     <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
         <property name="order" value="1"/>
         <property name="characterEncoding" value="UTF-8"/>
         <property name="templateEngine">
             <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                 <property name="templateResolver">
                     <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                         <!-- 视图前缀 -->
                         <property name="prefix" value="/WEB-INF/templates/"/>
                         <!-- 视图后缀 -->
                         <property name="suffix" value=".html"/>
                         <property name="templateMode" value="HTML5"/>
                         <property name="characterEncoding" value="UTF-8" />
                      </bean>
                  </property>
             </bean>
         </property>
     </bean>

     <!-- 配置视图控制器访问首页 -->
     <mvc:view-controller path="/" view-name="index"></mvc:view-controller>

     <!-- 配置默认servlet处理静态资源 -->
     <mvc:default-servlet-handler />

     <!-- 开启MVC的注解驱动 -->
     <mvc:annotation-driven />

     <!-- 配置文件上传解析器 -->
     <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>

     <!-- 配置异常处理器 -->
     <!-- 配置拦截器 -->
 </beans>
---------------------
 @author Alex
 @create 2023-03-16-18:18
 */
public class US03 {
}