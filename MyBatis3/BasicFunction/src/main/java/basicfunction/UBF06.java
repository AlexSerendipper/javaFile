package basicfunction;

import basicfunction.mappers.EmpMapper;
import basicfunction.mappers.UserMapper;
import basicfunction.pojo.Emp;
import basicfunction.pojo.User;
import basicfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * 【自定义映射resultMap】
 *  resultType：自动映射关系，即用于属性名和表中字段名一致的情况
 *  resultMap：自定义映射关系，即用于一对多或多对一的表关系 或 字段名和属性名不一致 的情况
 *
 * 【resultMap 解决 字段名和属性名不一致的问题】✔✔✔推荐使用方法1和方案2，  resultMap主要用来处理 多对一 以及 一对多 的映射关系！！！
 *  解决方案一：为查询的字段设置别名，保证与属性名一致。如 select eid,emp_name empName,age,sex,email from mybatis_emp
 *  解决方案二：在mybatis-config中开启全局配置<settings>，将表中字段的下划线自动转换 为 小驼峰形式
---------------------------------
<settings>
    <!--将表中字段的下划线自动转换为驼峰，如emp_name映射为empName-->
    <setting name="mapUnderscoreToCamelCase" value="true"/>
    <!--开启延迟加载-->
    <setting name="lazyLoadingEnabled" value="true"/>
</settings>
---------------------------------
 *  解决方案三：设定自定义映射resultMap处理字段和属性的映射关系
 *               注意：若设置了resultMap，则需要为 所有的属性映射相应的字段
---------------------------------
<!-- resultMap标签：设置自定义映射，type属性：查询的数据要映射的实体类的类型, 即select标签查询结果的类型 -->
<resultMap id="empResultMap" type="emp">
    <!--id标签设置主键的映射关系-->
    <!--property为属性名（type属性中实体类 类型的属性），column为字段名（sql语句中查询出的字段名）-->
    <id property="eid" column="eid"></id>
    <!--result标签设置普通字段的映射关系-->
    <result property="empName" column="emp_name"></result>
    <result property="age" column="age"></result>
    <result property="sex" column="sex"></result>
    <result property="email" column="email"></result>
</resultMap>

<select id="getAllEmp" resultMap="empResultMap">
    select * from mybatis_emp;
</select>
---------------------------------
 *
 @author Alex
 @create 2023-03-12-13:50
 */
public class UBF06 {
    // 当字段名和属性名不一致时, 设定自定义映射resultMap处理字段和属性的映射关系
    @Test
    public void test1() throws IOException {
        EmpMapper empMapper = GetMapperUtils.getMapper1(EmpMapper.class);
        List<Emp> allEmp = empMapper.getAllEmp();
        System.out.println(allEmp);
    }

}
