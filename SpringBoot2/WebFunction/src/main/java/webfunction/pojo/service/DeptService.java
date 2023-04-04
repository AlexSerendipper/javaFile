package webfunction.pojo.service;

import datafunction.mapper.DeptMapper;
import datafunction.mapper.EmpMapper;
import datafunction.pojo.Dept;
import datafunction.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 @author Alex
 @create 2023-03-24-12:03
 */
@Service
public class DeptService {
    @Autowired
    DeptMapper deptMapper;

    public Dept getDept(){
        return deptMapper.getDeptByDid();
    }
}
