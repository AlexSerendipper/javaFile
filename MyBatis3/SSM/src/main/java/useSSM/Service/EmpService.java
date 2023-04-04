package useSSM.Service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import useSSM.pojo.Emp;

import java.security.PrivateKey;
import java.util.List;

/**
 @author Alex
 @create 2023-03-16-21:42
 */
public interface EmpService {
    /**
     * 查询所有员工信息
     * @return
     */
    List<Emp> getAllEmp();

    /**
     * 查询所有员工信息(分页)
     * @param pageNo
     * @return
     */
    PageInfo<Emp> getAllEmployeePage(Integer pageNo);
}
