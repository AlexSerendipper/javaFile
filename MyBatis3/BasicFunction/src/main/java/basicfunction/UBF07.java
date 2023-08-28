package basicfunction;

import basicfunction.mappers.EmpMapper;
import basicfunction.pojo.Emp;
import basicfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * 【resultMap 解决 一对多/多对一 的映射关系问题】
 *  首先因为表存在 一对多/多对一 的映射关系，所以对应的实体类也需要建立关系✔
 *   即 在'多'中建立'一'所对应的对象，如：private Dept dept;
 *      在'一'中建立'多'所对应的集合，如：private List<Emp> emps;
 *  我们使用多表查询时，我们查询出的字段 无法根据 属性名和表中字段名 进行赋值（因为此时属性如上！），所以只能通过resultMap来处理映射关系
 *
 * 【处理多对一的映射关系】三种方式
 * （1）级联属性赋值：少用
------------------
<resultMap id="EmpAndDeptResultMapFirst" type="emp">
    <id property="eid" column="eid"></id>
    <result property="empName" column="emp_name"></result>
    <result property="age" column="age"></result>
    <result property="sex" column="sex"></result>
    <result property="email" column="email"></result>
    <result property="did" column="did"></result>
    <!-- 级联属性赋值!! -->
    <result property="dept.did" column="did"></result>
    <result property="dept.deptName" column="dept_name"></result>
</resultMap>
------------------
 *
 * （2）使用association
-----------------
 <!--使用association专门处理多对一的映射关系，其中javaType属性为 需要进行赋值的属性的全类名-->
<resultMap id="EmpAndDeptResultMapFirst" type="emp">
    <id property="eid" column="eid"></id>
    <result property="empName" column="emp_name"></result>
    <result property="age" column="age"></result>
    <result property="sex" column="sex"></result>
    <result property="email" column="email"></result>
    <result property="did" column="did"></result>
    <!-- 使用association处理映射关系, 将查询出的字段（column），为javaType属性对应的类赋值，然后赋值给emp类中的属性dept(property=dept) -->
    <association property="dept" javaType="dept">
        <id column="did" property="did"></id>
        <result property="deptName" column="dept_name" ></result>
    </association>
</resultMap>
-----------------
 * （3）✔✔✔✔通过分步查询（相当于 子查询 写在where中的方式！！！）
 *                 如下：通过分步查询员工及其对应部门的信息
-----------------
<resultMap id="EmpAndDeptResultMapOne" type="emp">
         ...
         ...
    <!-- 使用association专门处理多对一的映射关系：分步查询 -->
    <!-- select为分步查询的结果，需要设置为mapper的全类名（即 namespace + id ） -->
    <!-- 此处的column为设置分步查询的条件，即输入到select语句中的值（此处为外层查询的结果did作为输入） -->
    <!-- fetchType属性，手动控制延迟加载效果，eager立即加载-->
    <association property="dept"  // 这个property就是把查询的结果赋值给emp类中的dept属性
                 select="basicfunction.mappers.DeptMapper.getEmpAndDeptTwo"
                 column="did">
    </association>
</resultMap>

<select id="getEmpAndDeptOne" resultMap="EmpAndDeptResultMapOne">  // 第一步查询的结果根据resultMap映射
    select * from mybatis_emp where eid = #{eid};
</select>


<select id="getEmpAndDeptTwo" resultType="dept">
    select * from mybatis_dept where did = #{did};
</select>
-----------------
 *
 * 【分步查询最大的好处就是延时加载】
 *  延时加载就是，根据需要的数据按需加载。即分步查询的每一步都能够 独立使用 进行查询功能✔
 *  延时加载需要在核心配置文件中设置全局配置信息
 *   配置后所有的分步操作都可以实现按需加载，即根据获取的数据是什么，就只会执行相应的sql（见下方test3）
 *  当配置了延时加载，若个别操作需要立即加载
 *   此时可通过 association 和 collection中 的 fetchType属性，设置当前的分步查询是否使用延迟加载。fetchType="lazy(延迟加载)|eager(立即加载)"
---------------------------------
<settings>
    <!--将表中字段的下划线自动转换为驼峰，如emp_name映射为empName-->
    <setting name="mapUnderscoreToCamelCase" value="true"/>
    <!--开启延迟加载-->
    <setting name="lazyLoadingEnabled" value="true"/>
</settings>
---------------------------------
 *
 @author Alex
 @create 2023-03-12-15:12
 */
public class UBF07 {
    // 使用association
    @Test
    public void test1() throws IOException {
        EmpMapper empMapper = GetMapperUtils.getMapper1(EmpMapper.class);
        List<Emp> empAndDept = empMapper.getEmpAndDept(1);
        System.out.println(empAndDept);
    }

    // 分步查询
    @Test
    public void test2() throws IOException {
        EmpMapper empMapper = GetMapperUtils.getMapper1(EmpMapper.class);
        Emp emp = empMapper.getEmpAndDeptOne(1);
        System.out.println(emp);
    }

    // 测试延时加载：当只加载了emp的名字，不需要执行第二步，所以根据延时加载，不会执行第二步操作
    // 若只需要加载根据传入id的部门信息，可以只执行第二步
    @Test
    public void test3() throws IOException {
        EmpMapper empMapper = GetMapperUtils.getMapper1(EmpMapper.class);
        Emp emp = empMapper.getEmpAndDeptOne(1);
        System.out.println(emp.getEmpName());
        System.out.println("****************************");
        System.out.println(emp.getDept());
    }

}
