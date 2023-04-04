package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 【HttpServletRequest类】part1
 *  每次只要有请求进入Tomcat服务器，Tomcat服务器就会把请求过来的HTTP协议信息解析好封装到HttpServletRequest对象中。
 *   我们可以通过HttpServletRequest对象，获取到所有请求的信息✔
 *
 * 【常用方法】
 *  getRequestURI() 获取请求的资源路径
 *  getRequestURL() 获取请求的统一资源定位符（绝对路径）
 *  getRemoteHost() 获取客户端的ip地址
 *  getHeader("") 获取请求头，输入请求头的信息，如user agent
 *  getParameter() 获取请求的参数✔
 *  getParameterValues() 获取请求的参数（多个值的时候使用）
 *  req.getParameterMap() 获取请求的参数（以map的形式存储）✔，常用于BeanUtils工具包
 *  getMethod() 获取请求的方式 GET 或 POST
 *  getContextPath() 获取工程路径：http:localhost:8080/javaweb/
 *
 * 【POST请求时的获取请求参数中文乱码解决】
 *  req.setCharacterEncoding("UTF-8"); 设置服务器字符集为 UTF-8，从而解决 post 请求的中文乱码问题
 *
 @author Alex
 @create 2023-01-29-11:03
 */

// /request
public class US04 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // i.getRequestURI() 获取请求的资源路径
        System.out.println("URI => " + req.getRequestURI());
        // ii.getRequestURL() 获取请求的统一资源定位符
        System.out.println("URL => " + req.getRequestURL());
        // iii.getRemoteHost() 获取客户端的 ip 地址
        System.out.println("客户端 ip 地址 => " + req.getRemoteHost());
        // iv.getHeader() 获取请求头
        System.out.println("请求头 User-Agent ==>> " + req.getHeader("User-Agent"));
        // vii.getMethod() 获取请求的方式 GET 或 POST
        System.out.println( "请求的方式 ==>> " + req.getMethod() );
        System.out.println("****************************");
        // 获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] hobby = req.getParameterValues("hobby");  // 当有多个选项时使用这个方法
        System.out.println("用户名：" + username);
        System.out.println("密码：" + password);
        System.out.println("兴趣爱好：" + Arrays.asList(hobby));
    }

    // 请求转发示例，见US05.java
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数（办事的材料）查看
        String username = req.getParameter("username");
        System.out.println("在 Servlet1（柜台 1）中查看参数（材料）：" + username);
        // 给材料 盖一个章，并传递到 Servlet2（柜台 2）去查看
        req.setAttribute("key1","柜台 1 的章");
        // 怎么去Servlet2
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/servlet1");
        // 怎么去WEB-INF目录
        // RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/index.html");
        // 走向 Sevlet2（柜台 2）
        requestDispatcher.forward(req,resp);
    }
}
