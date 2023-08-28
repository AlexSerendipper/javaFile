package basicfunction.mappers;

import basicfunction.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 @author Alex
 @create 2023-03-13-15:58
 */
public interface DynamicSQLMapper {
    /**
     * 多条件查询之使用恒等式
     */
    List<Emp> getEmpByEqual(Emp emp);

    /**
     * 多条件查询之使用Where
     */
    List<Emp> getEmpByWhere(Emp emp);

    /**
     * 多条件查询之使用Trim
     */
    List<Emp> getEmpByTrim(Emp emp);

    /**
     * 多条件修改之使用Trim
     */
    int updateEmpByTrim(Emp emp);

    /**
     * 多条件查询之使用choose
     */
    List<Emp> getEmpByChoose(Emp emp);

    /**
     * 通过数组实现批量删除，使用where in()的语法（还能使用where...or的语法）
     */
    int deleteEmpByArray(@Param(value = "eids") Integer[] eids);


    /**
     * 通过集合实现批量添加，使用where in()的语法
     */
    int InsertEmpByCollection(@Param(value = "emps") List<Emp> emps);

}
