package datafunction.mapper;

import datafunction.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;

/**
 @author Alex
 @create 2023-03-23-21:55
 */

//@Mapper
public interface EmpMapper {
    public Emp getEmp(Integer eid);
}
