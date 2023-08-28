package useSSM.controller;

import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import useSSM.Service.EmpService;
import useSSM.pojo.Emp;

import java.util.List;


/**
 * /emp-->get请求-->查询所有用户信息
 * /emp/page/1-->get请求-->查询所有用户信息，分页信息
 * /emp/1-->get请求-->根据id查询用户信息
 * /emp/add -->get请求 --> 跳转到添加页面
 * /emp-->post请求-->添加用户信息
 * /emp/1-->delete请求-->根据id删除用户信息
 * /emp-->put请求方式-->更新用户信息
 @author Alex
 @create 2023-03-16-18:21
 */

@Controller
public class EmpController {
    @Autowired
    private EmpService empService;
    @RequestMapping(value = "/emp",method = RequestMethod.GET)
    public String getAllEmployee(Model model){
        // 查询所有的员工信息
        List<Emp> empList = empService.getAllEmp();
        // 将员工信息共享到请求域中
        model.addAttribute("empList",empList);
        System.out.println("员工信息为：" + empList);
        // 跳转
        return "emp_list";
    }


    @RequestMapping(value = "/emp/page/{pageNo}",method = RequestMethod.GET)
    public String getAllEmployeePage(@PathVariable("pageNo") Integer pageNo,Model model){
        // 获取员工的分页信息
        PageInfo<Emp> pageInfo = empService.getAllEmployeePage(pageNo);
        // 将员工信息共享到请求域中
        model.addAttribute("pageInfo",pageInfo);
        // 跳转
        return "emp_page";
    }
}
