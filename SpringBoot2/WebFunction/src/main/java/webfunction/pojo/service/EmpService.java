package webfunction.pojo.service;

import datafunction.mapper.EmpMapper;
import datafunction.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 @author Alex
 @create 2023-03-23-22:04
 */
@Service
public class EmpService {
    @Autowired
    EmpMapper empMapper;

    public Emp getEmpById(Integer eid){
        return empMapper.getEmp(eid);
    }
}
