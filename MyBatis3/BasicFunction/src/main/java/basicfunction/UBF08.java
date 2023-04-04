package basicfunction;

import basicfunction.mappers.DeptMapper;
import basicfunction.mappers.EmpMapper;
import basicfunction.pojo.Dept;
import basicfunction.pojo.Emp;
import basicfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * 【处理 一对多 的映射关系】两种方式
 * (1) 使用 collection
-----------------
<resultMap id="DeptAndEmpResultMap" type="dept">
    <id property="did" column="did"></id>
    <result property="deptName" column="dept_name"></result>
    <!--使用collection专门处理一对多的映射关系，其中property表示进行处理的属性，ofType表示存储在集合中数据的类型的全类名 -->
    <collection property="emps" ofType="emp">
        <id column="eid" property="eid"></id>
        <result property="empName" column="emp_name"></result>
        <result property="age" column="age"></result>
        <result property="sex" column="sex"></result>
        <result property="email" column="email"></result>
        <result property="did" column="did"></result>
    </collection>
</resultMap>
-----------------
 * (2) 使用 分步查询
-----------------
<resultMap id="DeptEmpResultMap" type="dept">
    <id property="did" column="did"></id>
    <result property="deptName" column="dept_name"></result>
    <collection property="emps"
                select="basicfunction.mappers.EmpMapper.getDeptAndEmpTwo"
                column="did"
                >
    </collection>
</resultMap>
-----------------
 *
 @author Alex
 @create 2023-03-12-20:39
 */
public class UBF08 {
    // 使用 collection
    @Test
    public void test1() throws IOException {
        DeptMapper deptMapper = GetMapperUtils.getMapper1(DeptMapper.class);
        Dept deptAndEmp = deptMapper.getDeptAndEmp(1);
        System.out.println(deptAndEmp);
    }

    // 使用 分步查询
    @Test
    public void test2() throws IOException {
        DeptMapper deptMapper = GetMapperUtils.getMapper1(DeptMapper.class);
        Dept dept = deptMapper.getDeptAndEmpOne(1);
        System.out.println(dept);
    }

    // 测试延迟加载
    @Test
    public void test3() throws IOException {
        DeptMapper deptMapper = GetMapperUtils.getMapper1(DeptMapper.class);
        Dept dept = deptMapper.getDeptAndEmpOne(1);
        System.out.println(dept.getDeptName());
    }
}
