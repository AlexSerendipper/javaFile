package useSSM.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import useSSM.Service.EmpService;
import org.springframework.stereotype.Service;
import useSSM.mapper.EmpMapper;
import useSSM.pojo.Emp;

import java.util.List;

/**
 @author Alex
 @create 2023-03-16-21:42
 */

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Emp> getAllEmp() {
        return empMapper.getAllEmp();
    }

    @Override
    public PageInfo<Emp> getAllEmployeePage(Integer pageNo) {
        // 开启分页功能
        Page<Object> page = PageHelper.startPage(pageNo, 3);
        // 查询员工信息
        List<Emp> empList = empMapper.getAllEmp();
        PageInfo<Emp> pageInfo = new PageInfo<>(empList, 5);
        return pageInfo;
    }
}
