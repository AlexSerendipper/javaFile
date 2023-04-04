package useSSM;

/**
 * 【第五步：配置MyBatis】
 * （1）创建MyBatis的核心配置文件mybatis-config.xml
 *     （方式一：仍由核心配置文件管理相应的内容，推荐使用✔）
 *     （方式二：将核心配置文件中的内容在spring.xml中进行配置）
 *     （注意：若使用方式二，只设置了部分内容，仍需要设置MyBatis核心配置文件的路径）
-----------------------------
<!-- 将SqlSessionFactory对象交给spring管理-->
<!-- 配置用于创建SqlSessionFactory的工厂bean，可以直接在spring的IOC中获取SqlSessionFactory对象(自动装配) -->
<bean class="org.mybatis.spring.SqlSessionFactoryBean">
    <!-- 方式一：设置MyBatis核心配置文件的路径 -->
    <property name="configLocation" value="classpath:mybatis-config.xml"></property>
</bean>
-----------------------------
 * （2）创建mapper接口 以及 映射文件
 * （3）将sqlsession交给Spring管理（在spring.xml中配置）
 *    （方式二：将核心配置文件中的内容在spring.xml中进行配置）
 * （4）配置mapper接口扫描功能
 *  (5) 创建日志文件log4j.xml
----------------------------------
<!-- 将SqlSessionFactory对象交给spring管理-->
<!-- 配置用于创建SqlSessionFactory的工厂bean，可以直接在spring的IOC中获取SqlSessionFactory对象(自动装配) -->
<bean class="org.mybatis.spring.SqlSessionFactoryBean">
    <!-- 方式二：将核心配置文件中的内容，部分在此处进行配置。两种方式二选一即可-->
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
该功能为mapper接口扫描功能，value值 设置mapper接口所对应的包，
由mybatis-spring提供，可以将指定包下所有的mapper接口创建动态代理，并将这些动态代理作为IOC容器的bean管理
配置后可以直接获取 mapper接口的实现类 对象(自动装配)
-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     <property name="basePackage" value="useSSM.mapper"></property>
</bean>
----------------------------------
 * （6）创建事务管理器（引入事务）
----------------------------------
<!--创建事务管理器-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <!--注入数据源-->
    <property name="dataSource" ref="dataSource"></property>
</bean>

<!--开启事务注解-->
<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
----------------------------------
 @author Alex
 @create 2023-03-17-10:30
 */
public class US05 {
}
