package cookie_session;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 利用cookie实现免账号密码登录(再次登录无需重复输入账号密码)，见login.jsp
 @author Alex
 @create 2023-02-13-16:51
 */

// /loginServlet
public class UCS02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if ("zzj".equals(username) && "qqabcd".equals(password)) {
            //登录成功,返回账号和密码
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(60 * 60 * 24 * 7);  //当前 Cookie 一周内有效
            resp.addCookie(cookie);
            Cookie cookie1 = new Cookie("password", password);
            cookie1.setMaxAge(60 * 60 * 24 * 7);  //当前 Cookie 一周内有效
            resp.addCookie(cookie1);
            System.out.println("登录 成功");
        } else {
            // 登录 失败
            System.out.println("登录 失败");
        }
    }
}
