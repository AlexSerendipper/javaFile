package web;

import bean.User;
import com.google.gson.Gson;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * 实际开发中，通常一整个功能对应一个servlet，所以我们需要将loginservlet和registservlet合并一下
 * 又因为今后很有可能会在UserServlet中新增很多servlet，所以利用反射优化大量else if代码：
 @author Alex
 @create 2023-02-03-10:46
 */
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    // 登录
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 利用第三方包将数据快速写入javabean
        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());
        // 调用 userService.login()登录处理业务
        User loginUser = userService.login(user);
        // 如果等于 null,说明登录 失败!
        // 把错误信息和需要回显的表单信息报错到request域中
        if (loginUser == null) {
            req.setAttribute("msg", "登录失败，用户名或密码错误");
            req.setAttribute("username", username);  // 需要回显的信息
            // 跳回登录页面
            System.out.println("登录失败");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            // 登录成功。跳到成功页面 login_success.jsp
            // 保存用户信息到session域当中。session域通常用来保存用户登录后的信息
            req.getSession().setAttribute("user", loginUser);
            System.out.println("登录成功");
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }

    // 注册
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        // 利用第三方包将数据快速写入javabean
        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());
        // 2、检查 验证码是否正确 === 写死,要求验证码为:abcde
        resp.setContentType("text/html; charset=UTF-8");
        // 获取 Session 中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        if (token != null && token.equalsIgnoreCase(code)) {
            // 3、检查 用户名是否可用
            if (userService.existsUsername(username)) {
                System.out.println("用户名[" + username + "]已存在!");
                // 保存回显信息
                req.setAttribute("msg", "用户名已存在");
                req.setAttribute("username", username);
                req.setAttribute("email", email);
                // 跳回注册页面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                // 调用 service 保存到数据库
                userService.registUser(user);
                // 保存user到session域中，用于回显数据
                req.getSession().setAttribute("user",user);
                // 跳到注册成功页面 regist_success.jsp
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        }else{
            // 保存回显信息
            req.setAttribute("msg", "验证码输入错误");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            System.out.println("验证码[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
        }

    // 注册升级版
    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数 username
        String username = req.getParameter("username");
        // 调用 userService.existsUsername();
        boolean existsUsername = userService.existsUsername(username);
        // 把返回的结果封装成为 map 对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername",existsUsername);
        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);
    }

    /**
     * 注销
     * 1、销毁 Session 中用户登录的信息（或者销毁 Session）
     * 2、重定向到首页（或登录页面）。
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、销毁 Session 中用户登录的信息（或者销毁 Session）
        req.getSession().invalidate();
        // 2、重定向到首页（或登录页面）。
        resp.sendRedirect(req.getContextPath());
    }
    }

