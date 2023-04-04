package employee_project.controller;

import employee_project.dao.EmployeeDao;
import employee_project.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 @author Alex
 @create 2023-03-05-14:31
 */

// 实现对员工信息的增删改查。
/*
            功能                      URL 地址                  请求方式
            访问首页√                  /                        GET
            查询全部数据√              /employee                GET
            删除√                     /employee/2               DELETE
            跳转到添加数据页面√        /toAdd                    GET
            执行保存√                 /employee                 POST
            跳转到更新数据页面√        /employee/2               GET
            执行更新√                 /employee                 PUT
*/
@Controller
public class EmployeeController {
    // 由于不设置service层，所以直接通过控制层，控制持久层
    @Autowired
    private EmployeeDao employeeDao;

    // 1、通过配置视图解析器view-controller，实现输入 / ，完成首页跳转的功能
    // 2、查看员工信息功能
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getEmployeeList(Model model){
        Collection<Employee> employeeList = employeeDao.getAll();
        // 使用model向request域中共享数据
        model.addAttribute("employeeList", employeeList);
        return "employee_list";
    }

    // 3、根据id删除员工功能
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id){
        System.out.println("删除员工功能");
        employeeDao.delete(id);
        return "redirect:/employee";
    }

    // 4、实现添加员工功能员工功能
    //    通过配置视图解析器view-controller，实现输入 /toAdd ，完成添加页面跳转的功能
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String addEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/employee";
    }

    // 5、实现查找员工功能 和 修改员工信息功能
    // 为了更好的用户体验，修改功能是先使用控制器方法获取employee对象，然后跳转到修改页面（回显employee对象的信息），然后调用修改的控制器进行修改
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public String getEmployeeById(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee",employee);
        return "employee_update";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public String updateEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/employee";
    }
}
