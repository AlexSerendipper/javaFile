package useSSM.mapper;

import useSSM.pojo.Emp;

import java.util.List;

/**
 @author Alex
 @create 2023-03-17-13:06
 */
public interface EmpMapper {
    /**
     * 查询所有员工信息
     * @return
     */
    List<Emp> getAllEmp();
}
