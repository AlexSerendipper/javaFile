package basicfunction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 【SpringMVC共享域对象数据之 存数据】
 *  由于不使用jsp技术，故仅有三大域对象：request、session、application
 *
 * 【request域】
 *（1）方式一：使用ServletAPI向request域对象 存数据
 *   将 HttpServletRequest req            # 作为控制器方法的形参（自动注入）
 *   req.setAttribute(key,value1);        # 向request域中存储数据
 *   通过Thymeleaf语法，在页面中回显数据
 *
 *（2）✔✔✔方式二：使用ModelAndView向request域对象 存数据，官方推荐做法
 *  ModelAndView 有Model和View的功能。Model主要用于向请求域共享数据。View主要用于设置视图，实现页面跳转
 *  ModelAndView对象，必须作为该方法的返回值返回
 *    ModelAndView mav = new ModelAndView();
 *    mav.addObject("key", "value2");          # 向请求域共享数据
 *    mav.setViewName("success");              # 设置视图，实现页面跳转
 *
 *（3）✔✔方式三：使用Model向request域对象 存数据
 *  相当于实现了ModelAndView中的model功能
 *    将 Model model                         # 作为控制器方法的形参（自动注入）
 *    model.addAttribute(key,value3);        # 向request域中存储数据
 *
 *（4）方式四：使用map向request域对象 存数据
 *    将 Map<String, Object> map             # 作为控制器方法的形参（自动注入）
 *    map.put("key", "value4");              # 向request域中存储数据
 *
 *（5）方式五：使用ModelMap向request域对象 存数据
 *    将 ModelMap modelMap                             # 作为控制器方法的形参（自动注入）
 *    modelMap.addAttribute("key", "value5");          # 向request域中存储数据
 *
 * 【Model、ModelMap、Map的关系】
 *  通过查看，model/modelMap/map.getClass().getName()，发现，三者底层的实现类（多态）均为 BindingAwareModelMap
 *  三者均实现了相同的功能，并且底层源码中最终都在执行后将把相关信息封装成ModelAndView对象✔✔✔。所以推荐直接使用ModelAndView向request域对象共享数据
 *   详见dispatcherServlet源码第535行，mv = ha.handle(processedRequest, response, mappedHandler.getHandler()); 即把相关信息封装成ModelAndView对象
 *
 * 【session域】建议使用ServletAPI向session域 存数据
 *   将 HttpSession session               # 作为控制器方法的形参（自动注入）
 *   session.setAttribute(key,value6);    # 向session域中存储数据
 *
 * 【application域】建议使用ServletAPI向application域 存数据
 *   将 HttpSession session                                                    # 作为控制器方法的形参（自动注入）
 *   ServletContext application = session.getServletContext();                 # 通过session 可以 获取application域（即servletContext对象）
 *   application.setAttribute(key,value7);                                     # 向application域中存数据
 *  --------------------------------------------------------------------------------------------------------------------------------------------------------
 *
 *  【SpringMVC共享域对象数据之 取数据】
 *  向request域中存的数据 在html页面处肯定是可以通过Thymeleaf语法正常调用的。。。。这里主要说的是使用 InternalResourceView（转发视图） 转发到另一前端控制器时
 *   此时在另一前端控制器中 无法通过Model来获取 存于request域中的属性，只能通过：
 * （1）方式一：使用ServletAPI向request域对象 取数据
 *      将 HttpServletRequest req            # 作为控制器方法的形参（自动注入）
 *      req.getAttribute(key,value1);        # 向request域中取出数据
 * （2）方式二：通过为 控制器方法的形参 添加注解 @RequestAttribute 创建映射关系。完成自动注入
 *
 @author Alex
 @create 2023-03-03-11:16
 */
@Controller
public class UBF05 {
    // 方式一：通过ServletAPI向request域中存储数据
    @RequestMapping("/test1/UBF05")
    public String testScope1(HttpServletRequest req) {
        req.setAttribute("key", "value1");
        return "UBF05";
    }

    // 方式二：使用ModelAndView向request域对象共享数据
    @RequestMapping("/test2/UBF05")
    public ModelAndView testScope2() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("key", "value2");
        mav.setViewName("UBF05");
        return mav;
    }

    // 方式三：使用Model向request域对象共享数据
    @RequestMapping("/test3/UBF05")
    public String  testScope3(Model model) {
       model.addAttribute("key","value3");
       return "UBF05";
    }


    // 方式四：使用map向request域对象共享数据
    @RequestMapping("/test4/UBF05")
    public String testScope4(Map<String, Object> map) {
        map.put("key", "value4");
        return "UBF05";
    }

    // 方式五：使用ModelMap向request域对象共享数据
    @RequestMapping("/test5/UBF05")
    public String testScope5(org.springframework.ui.ModelMap modelMap) {
        modelMap.addAttribute("key", "value5");
        return "UBF05";
    }

    // 使用ModelMap向request域对象共享数据
    @RequestMapping("/test6/UBF05")
    public String testScope6(HttpSession session ) {
        session.setAttribute("key", "value6");
        return "UBF05";
    }

    // 使用ModelMap向request域对象共享数据
    @RequestMapping("/test7/UBF05")
    public String testScope7(HttpSession session ) {
        ServletContext context = session.getServletContext();
        context.setAttribute("key", "value7");
        return "UBF05";
    }






    // 测试获取request域中的数据两种方式
    @RequestMapping("/test8/UBF05")
    public String testScope8(HttpServletRequest req, Model model) {
        req.setAttribute("username","zzj");
        model.addAttribute("password","qqabcd");
        return "forward:/test9/UBF05";
    }

    // 测试获取request域中的数据两种方式
    @RequestMapping("/test9/UBF05")
    public String testScope9(HttpServletRequest req, Model model, @RequestAttribute("username") String name) {
        Object password = model.getAttribute("password");
        Object password1 = req.getAttribute("password");
        System.out.println(password);
        System.out.println(password1);
        System.out.println(name);
        return "UBF05";
    }
}
