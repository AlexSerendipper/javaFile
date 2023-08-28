package useSSM;

/**
 * 【第四步：配置Spring.xml】
---------------------
 <?xml version="1.0" encoding="UTF-8"?>
 <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
     <!--扫描组件（除控制层外的IOC都由Spring管理，控制层交由SpringMVC扫描）-->
     <context:component-scan base-package="useSSM">

     <!--这里是通过注解 扫描除controller注解以外的-->
     <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
     </context:component-scan>

     <!-- 引入jdbc.properties -->
     <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>

     <!-- 配置Druid数据源 -->
     <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
         <property name="driverClassName" value="${jdbc.driveClassName}"></property>
         <property name="url" value="${jdbc.url}"></property>
         <property name="username" value="${jdbc.username}"></property>
         <property name="password" value="${jdbc.password}"></property>
     </bean>

     <!--创建事务管理器-->
     <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
         <!--注入数据源-->
         <property name="dataSource" ref="dataSource"></property>
     </bean>

     <!--开启事务注解-->
     <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>

     <!-- 将SqlSessionFactory对象交给spring管理-->
     <!-- 配置用于创建SqlSessionFactory的工厂bean，配置后可以直接获取SqlSessionFactory对象(自动装配) -->
     <bean class="org.mybatis.spring.SqlSessionFactoryBean">
         <!-- 方式二：将核心配置文件中的内容，部分在此处进行配置（）。两种方式二选一即可-->
         <!-- 设置数据源 -->
         <property name="dataSource" ref="dataSource"></property>
         <!--设置类型别名所对应的包-->
         <property name="typeAliasesPackage" value="useSSM.pojo"></property>
         <!--配置全局设置，将表中字段的下划线自动转换为驼峰-->
         <property name="configuration">
             <bean class="org.apache.ibatis.session.Configuration">
                 <property name="mapUnderscoreToCamelCase" value="true"></property>
             </bean>
         </property>
         <!--配置插件，也可以在mybatis-config中进行配置-->
         <property name="plugins">
             <array>
                <bean class="com.github.pagehelper.PageInterceptor"></bean>
             </array>
         </property>
         <!-- 设置映射文件的路径
         实际上若设置了mapper接口扫描功能，映射文件所在路径和mapper接口所在路径一致，则此标签默认存在，不需要设置-->
         <!-- <property name="mapperLocations" value="classpath:mapper/*.xml"></property>-->
     </bean>

     <!--
     mapper接口扫描，
     由mybatis-spring提供，value值设置为mapper接口所对应的包的位置，
     可以将指定包下所有的mapper接口创建动态代理，并将这些动态代理作为IOC容器的bean管理。配置后可以直接获取 mapper接口的实现类 对象(自动装配)
     -->
     <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="useSSM.mapper"></property>
     </bean>
 </beans>
---------------------
 @author Alex
 @create 2023-03-16-21:41
 */
public class US04 {
}
