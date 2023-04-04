package basicfunction.mappers;

import basicfunction.pojo.Dept;
import basicfunction.pojo.Emp;

import java.util.List;

/**
 @author Alex
 @create 2023-03-12-14:22
 */
public interface EmpMapper {
    /**
     * 查询所有的员工信息
     */
    List<Emp> getAllEmp();

    /**
     * 根据id查询员工及其对应部门的信息
     */
    List<Emp> getEmpAndDept(Integer eid);

    /**
     * 通过分步查询员工及其对应部门的信息
     * 第一步：查询员工信息
     */
    Emp getEmpAndDeptOne(Integer eid);


    /**
     * 通过分步查询员工及其对应部门的信息
     * 第二步：查询员工信息
     */
    List<Emp> getDeptAndEmpTwo(Integer did);


}
