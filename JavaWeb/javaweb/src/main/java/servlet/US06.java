package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 *【base标签的作用】base标签写在title标签下面
 *  所有的相对路径在进行跳转时，都是以浏览器当前的地址为参照进行跳转
 *   如：使用请求转发跳转到http://localhost:8080/javaweb/jump。以此为参照进行跳转
 *       如使用该命令：../../index.html，跳转到的页面是http://localhost:8080/index.html这样肯定是不对的
 *  ✔base标签可以设置当前html页面所有的相对路径工作时的参照路径
 *   如：使用<base href="http://localhost:8080/javaweb/jump_forUS06/jump/index.html">
 *       如使用该命令：../../index.html，跳转到的页面是http://localhost:8080/javaweb/index.html这样就对了
 *  base标签通常设置到工程路径，即http://localhost:8080/javaweb/                      # 最后一个/一定要加上
 *  在已有的页面中加入base标签，往往需要修改css，js的引入（因为这些文件路径很可能是用相对路径）
 *   又由于我们base标签设置的是web路径，导致了页面css，js的引入也只在服务器中打开才有效~~~
 *
 *【页面跳转】
 *  目前web阶段使用： base标签 + 相对路径
 *  框架阶段使用：绝对路径
 *
 *【表单重复提交】表单重复提交有三种常见的情况
 * 一：提交完表单。服务器使用请求转来进行页面跳转。这个时候，用户按下功能键F5，就会发起最后一次的请求。
 *     造成表单重复提交问题。解决方法：使用重定向来进行跳转
 * 二：用户正常提交服务器，但是由于网络延迟等原因，迟迟未收到服务器的响应，这个时候，用户以为提交失败，
 *    就会着急，然后多点了几次提交操作，也会造成表单重复提交。
 * 三：用户正常提交服务器。服务器也没有延迟，但是提交完成后，用户回退浏览器。重新提交。也会造成表单重复提交。
 *
 * 【使用验证码解决表单重复提交】
 * 一：当用户第一次访问表单的时候，就要给表单生成一个随机的验证码字符串（把验证码生成验证码图片显示在表单中）
 * 二：把验证码保存在session域当中
 * 三：用户提交了表单后跳转到RegistServlet程序中
 * 四：获取表单中的验证码信息和session域中的验证码，然后删除session域中的验证码信息
 * 五：比较验证码信息，相等则允许操作，不相等禁止操作
 *
 * 【谷歌验证码kaptcha的使用】见index.html
 * 1、添加jar包
 * 2、在 web.xml中去配置用于生成验证码的Servlet程序
 * 3、访问KaptchaServlet，就能够生成验证码及验证码图片，并且会将验证码保存到session域当中 !!
 * 4、在表单中使用img标签去显示验证码图片并使用它
 * 5、在服务器获取session域中的验证码（谷歌生成的）和客户端发送过来的验证码进行比较。然后删除session中的验证码
 *     String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);  # 获取 Session 中的验证码
 *     req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);   # 删除 Session 中的验证码
 ------------------------------------------
 <servlet>
     <servlet-name>KaptchaServlet</servlet-name>
     <servlet-class>com.google.code.kaptcha.servlet.KaptchaServlet</servlet-class>
 </servlet>
 <servlet-mapping>
     <servlet-name>KaptchaServlet</servlet-name>
     <url-pattern>/kaptcha.jpg</url-pattern>
 </servlet-mapping>
 -------------------------------------------

 @author Alex
 @create 2023-01-29-14:31
 */

// /jump
public class US06 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jump_forUS06/jump/index.html").forward(req,resp);
        // 可以看见jump目录的index文件中，设置了base标签，故跳转正常
    }

    // 验证码的使用，目前只要验证码输入正确就能登录成功
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        // 获取 Session 中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        // 获取用户输入的验证码
        String code = req.getParameter("code");
        // 获取用户名
        String username = req.getParameter("username");
        if (token != null && token.equalsIgnoreCase(code)) {
            System.out.println("保存到数据库：" + username);
            req.getSession().setAttribute("user",username);
            resp.getWriter().write("注册成功，点击普通跳转到jump目录，即可顺利跳转");
        } else {
            System.out.println("请不要重复提交表单");
            resp.getWriter().write("请不要重复提交表单");
        }
    }
}
