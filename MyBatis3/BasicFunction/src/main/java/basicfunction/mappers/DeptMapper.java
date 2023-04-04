package basicfunction.mappers;

import basicfunction.pojo.Dept;

/**
 @author Alex
 @create 2023-03-12-14:22
 */
public interface DeptMapper {
    /**
     * 通过分步查询员工及其对应部门的信息
     * 第二步：通过did查询员工所对应的部门的信息
     */
    Dept getEmpAndDeptTwo(Integer did);


    /**
     * 根据部门id 获取部门 以及部门中所有员工的信息
     */
    Dept getDeptAndEmp(Integer did);


    /**
     * 通过分步查询部门及其对应员工的信息
     * 第一步：通过did所对应的部门的信息
     */
    Dept getDeptAndEmpOne(Integer did);
}
