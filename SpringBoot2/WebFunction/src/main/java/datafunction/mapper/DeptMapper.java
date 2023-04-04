package datafunction.mapper;

import datafunction.pojo.Dept;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 @author Alex
 @create 2023-03-24-12:00
 */

public interface DeptMapper {
    @Select("select * from springboot_dept where did=1")
    public Dept getDeptByDid();
}
