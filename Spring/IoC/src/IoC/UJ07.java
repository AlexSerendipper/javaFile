package IoC;

import IoC.bean.Course;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.TreeSet;

/**
 * 【基于xml的bean的其他配置】part2
 *
 * 【利用外部配置文件配置bean.xml】常用于配置数据库连接池
 *  1）在 spring 配置文件中引入名称空间context
 *  2）配置xml文件,利用<context>标签引入外部属性文件
---------------------------------------------------
 <?xml version="1.0" encoding="UTF-8"?>
 <beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xmlns:p="http://www.springframework.org/schema/p"
 xmlns:util="http://www.springframework.org/schema/util"
 xmlns:context="http://www.springframework.org/schema/context"
 xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                     http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
                     http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
 <!--引入 context 名称空间结束-->
 <!--引入外部属性文件-->
 <context:property-placeholder location="classpath:druid.properties"/>
 <!--配置连接池-->
 <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
     <!--Spring专用表达式，可不是EL表达式-->
     <property name="driverClassName" value="${driveClassName}"></property>
     <property name="url" value="${url}"></property>
     <property name="username" value="${username}"></property>
     <property name="password" value="${password}"></property>
 </bean>
 ---------------------------------------------------------
 @author Alex
 @create 2023-02-22-15:47
 */
public class UJ07  {
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ07.xml");
        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
    }

}
