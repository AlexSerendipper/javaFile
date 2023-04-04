package extendfunction;

import extendfunction.mappers.EmpMapper;
import extendfunction.pojo.Emp;
import extendfunction.pojo.EmpExample;
import extendfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * 【正向工程】
 *  先创建Java实体类，由框架负责根据实体类生成数据库表。之前的学习均为正向工程
 *  逆向工程：先创建数据库表，由框架负责根据数据库表，反向生成 如Java实体类、Mapper接口、Mapper映射文件等资源
 *
 *【逆向工程创建流程】
 *（1）在pom.xml中添加插件
--------------------
<!-- 控制Maven在构建过程中相关配置 -->
<build>
<!-- 构建过程中用到的插件 -->
<plugins>
<!-- 具体插件，逆向工程的操作是以构建过程中插件形式出现的 -->
    <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.0</version>
        <!-- 插件的依赖 -->
        <dependencies>
            <!-- 逆向工程的核心依赖 -->
            <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.2</version>
            </dependency>
            <!-- 数据库连接池 -->
            <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.2</version>
            </dependency>
            <!-- MySQL驱动（一定要按照你用的mysql作修改啊） -->
            <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
            </dependency>
        </dependencies>
    </plugin>
</plugins>
-----------
 *（2）创建MyBatis的核心配置文件
 *（3）创建逆向工程的配置文件：文件名必须为 generatorConfig.xml
----------------------
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
            PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
            "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--
         ✔✔✔targetRuntime: 执行生成的逆向工程的版本
        （1）MyBatis3Simple: 生成基本的CRUD（清新简洁版）：自动生成 增删改 加上 查询所有数据，查询一条数据的功能，其余功能需要自己写
        （2）MyBatis3: 生成带条件的CRUD（奢华尊享版）：
    -->
    <context id="DB2Tables" targetRuntime="MyBatis3Simple">
        <!-- 数据库的连接信息（需要更改） -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/javastudy?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true"
                        userId="root"
                        password="qqabcd">
        </jdbcConnection>
        <!-- javaBean的生成策略，即表所对应的实体类的生成策略 -->
        <!-- 在targetProject目录下，将生成的javabean放置在targetPackage包下(需要提前创好包) -->
        <javaModelGenerator targetPackage="extendfunction.pojo"
                            targetProject=".\src\main\java">
            <!-- 决定能否使用子包 -->
            <property name="enableSubPackages" value="true" />
            <!-- 当将表中的字段名，自动转换为实体类的属性时，可以去掉字段名前后的空格 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- SQL映射文件的生成策略（即mapper.xml） -->
        <sqlMapGenerator targetPackage="extendfunction.mappers"
                        targetProject=".\src\main\resources">
        <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>
        <!-- Mapper接口的生成策略（即mapper接口） -->
        <javaClientGenerator type="XMLMAPPER"
                targetPackage="extendfunction.mappers"
                targetProject=".\src\main\java">
                <property name="enableSubPackages" value="true" />
        </javaClientGenerator>
        <!-- 逆向分析的表 -->
        <!-- tableName属性指定表名。。domainObjectName属性指定生成出来的实体类的类名-->
        <!-- mapper接口以及映射文件的名字，都是通过实体类的类名自动生成 -->
        <!-- tableName设置为*号，可以对应所有表，此时不写domainObjectName -->
        <table tableName="mybatis_emp" domainObjectName="Emp"/>
        <table tableName="mybatis_dept" domainObjectName="Dept"/>
    </context>
</generatorConfiguration>
----------------------
 *（4）使用逆向工程一键生成 Mapper接口，映射文件，pojo
 *  maven - plugins - mybatis_generator — genereate
 *  建议重复执行逆向工程时，把之前生成的 Mapper接口，映射文件，pojo 先删掉
 *
 *（5）MyBatis3（豪华尊享版）使用教程，见下方示例(实现了 动态sql + 单表查询不需要写sql语句)
 @author Alex
 @create 2023-03-15-19:16
 */


public class UEF02 {
    // 查询功能1：查询所有数据(没有条件就是查询所有)
    @Test
    public void test1() throws IOException {
        EmpMapper mapper = GetMapperUtils.getMapper1(EmpMapper.class);
        List<Emp> emps = mapper.selectByExample(null);
        for(Emp emp:emps){
            System.out.println(emp);
        }
    }

    // 查询功能2：根据条件查询
    // （1）创建emample对象：EmpExample example = new EmpExample();
    // （2）添加条件：example.createCriteria() . (and + 字段名 + 具体要实现的功能)
    // （3）根据条件查询：mapper.selectByExample(example)
    @Test
    public void test2() throws IOException {
        EmpMapper mapper = GetMapperUtils.getMapper1(EmpMapper.class);
        EmpExample example = new EmpExample();
        example.createCriteria().andEmpNameEqualTo("李四").andAgeGreaterThan(20);
        example.or().andSexEqualTo("女");
        List<Emp> emps = mapper.selectByExample(example);
        System.out.println(emps);
    }

    // 修改功能3：根据主键进行修改：若传入对象的属性中存在Null值，会直接将相应字段直接赋值为null值
    @Test
    public void test3() throws IOException {
        EmpMapper mapper = GetMapperUtils.getMapper1(EmpMapper.class);
        int i = mapper.updateByPrimaryKey(new Emp(14, "xiugai", null, "女", null, null));
        System.out.println(i);
    }

    // 修改功能4：✔✔✔选择性修改：若传入对象的属性中存在Null值，则不会修改相应字段的值
    @Test
    public void test4() throws IOException {
        EmpMapper mapper = GetMapperUtils.getMapper1(EmpMapper.class);
        int i = mapper.updateByPrimaryKeySelective(new Emp(14, "xiugai", null, "女", null, null));
        System.out.println(i);
    }
}
