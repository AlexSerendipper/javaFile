package datafunction;

import datafunction.pojo.Dept;
import datafunction.pojo.Emp;
import datafunction.service.DeptService;
import datafunction.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 【mybatis整合1】手动配置
 * (1) 引入starter依赖(SqlSessionFactory、SqlSession都自动配置好了
--------------
<!--druid数据源依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>
--------------
 * (2) 创建mybatis核心配置文件（可以不创建核心配置文件，其内容通过绑定了mybatis.configuration进行配置即可）
 *     默认没有开启类的别名功能 以及 将表中字段的下划线自动转换为驼峰功能✔✔✔
 * (3) 创建mapper接口以及mapper接口映射文件 (与之前做法相同)
 *     ✔需要在在主启动类上添加mapper接口扫描位置 @MapperScan(value = "datafunction"),
 *     ✔配合在配置文件中指定mapper映射文件的位置，这样就不需要像之前一样把mapper接口和映射文件放在同样的路径下啦
 * (3) 配置文件 application.properties
---------------
# 不需要配置核心配置文件的位置，直接通过mybatis.configuration进行配置核心配置文件✔
# 如果配置了核心配置文件的位置，则不能使用mybatis.configuration进行配置（需要直接在核心配置文件中进行配置）✔
# mybatis.config-location=classpath:mybatis/mybatis-config.xml
# 将表中字段的下划线自动转换为驼峰
mybatis.configuration.map-underscore-to-camel-case=true
# 设置某个包 类名与类的全类名的 映射关系
mybatis.type-aliases-package=datafunction.pojo
# 设置mapper映射文件的位置
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
---------------
 *
 * 【mybatis整合2】注解方式
 * （1）创建mapper接口，使用@Select等标签进行增删改操作，如 @Select("select * from springboot_dept where did=1")
 *      还可以使用{}的方式，此时{}内的字符串会自动完成拼接操作，增加可读性。如@Insert({"insert into user(user_id,status) "
 *                                                                              "values(#{userId},#{ticket})"})
 * （2）设置自动递增的主键
 *      原先在select标签中属性设置，使用@option标签进行配置 @Options(useGeneratedKeys = true,keyProperty = "did")
 *  (3) 动态sql：若使用动态sql，必须写在script标签中。如@Insert({"<script>"
 *                                                           "insert into user(user_id,status) "
 *                                                           "values(#{userId},#{ticket})"})"
 *                                                           "<if test="ticket!=null">"
 *                                                           "and 1=1"
 *                                                           "<if/>"
 *                                                           "</script>"
 *  注解方式不需要创建mapper接口映射文件。但是 处理复杂sql语句困难
 *  （故推荐简单sql语句时使用注解方式，复杂sql时使用手动配置方式，混合开发✔✔✔）
 *
 * 【mybatis整合3】spring initializr
 *  使用初始化向导，sql-mybatis framework 自动进行配置
 *
 * 【springboot2整合mybatisplus】见mybatisplus.xmind
 *  其中数据源 使用的是 自己配置的数据源（如使用druid-spring-boot-starter引入的数据源）
 *
 @author Alex
 @create 2023-03-23-14:32
 */
@Controller
public class UD02 {
    @Autowired
    EmpService empService;

    @Autowired
    DeptService deptService;

    @ResponseBody
    @RequestMapping("/emp")
    public Emp getEmp(){
        Emp emp = empService.getEmpById(1);
        System.out.println(emp);
        return emp;
    }

    @ResponseBody
    @RequestMapping("/dept")
    public Dept getDept(){
        Dept dept = deptService.getDept();
        return dept;
    }
}
