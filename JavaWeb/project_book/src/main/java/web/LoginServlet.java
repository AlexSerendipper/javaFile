package web;

import bean.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 @author Alex
 @create 2023-02-01-18:29
 */
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 调用 userService.login()登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        // 如果等于 null,说明登录 失败!
        // 把错误信息和需要会显的表单信息报错到request域中
        if (loginUser == null) {
            req.setAttribute("msg","登录失败，用户名或密码错误");
            req.setAttribute("username",username);  // 需要回显的信息
        // 跳回登录页面
            System.out.println("登录失败");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
        // 登录 成功
        //跳到成功页面 login_success.jsp
            System.out.println("登录成功");
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }
}

