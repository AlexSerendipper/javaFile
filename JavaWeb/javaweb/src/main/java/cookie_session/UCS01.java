package cookie_session;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 【cookie】见cookie.html
 *  Cookie是服务器在客户端保存键值对的一种技术。
 *  客户端有了Cookie后，每次请求都会将cookie发送给服务器检查✔。
 *  每次请求可以保存多个cookie, 每个Cookie的大小不能超过4kb
 *    Cookie cookie = new Cookie("key1", "value1");    # 创建 Cookie 对象
 *    resp.addCookie(cookie);                          # 通知客户端保存 Cookie
 *    Cookie[] cookies = req.getCookies();             # 服务器端获取保存的cookie
 *
 * 【cookie值的修改】
 *  方案一：
 *   1、先创建一个要修改的同名（key）的 Cookie 对象
 *   2、在构造器，同时赋于新的 Cookie 值。
 *   3、调用 response.addCookie( Cookie )
 *  方案二：
 *   1、先查找到需要修改的 Cookie 对象
 *   2、调用 setValue()方法赋于新的 Cookie 值。
 *   3、调用 response.addCookie()通知客户端保存修改
 *  注：修改的cookie值不能为中文
 *   若需要保存中文值，需要使用base64编码（文件上传时讲过）
 *
 * 【Cookie的生命控制】
 *  指的是如何管理 Cookie 什么时候被销毁（删除）
 *     setMaxAge()
 *      正数，表示在指定的秒数后过期
 *      负数，表示浏览器一关，Cookie 就会被删除（默认值是-1）
 *      零，表示马上删除 Cookie
 *
 * 【cookie的path属性】
 *  Cookie的path属性是通过请求的地址来进行有效的过滤。过滤哪些 Cookie可以发送给服务器。哪些不发。
 *                              如：CookieA的path=/工程路径（默认）
 *                                  CookieB的path=/工程路径/abc
 *                              若请求地址为：http://ip:port/工程路径/a.html。cookieB将不会被客户端存储
 *                              若请求地址为：http://ip:port/工程路径/abc/a.html。cookieA和cookieB都会被客户端存储
 *  path属性的默认值为当前工程的web路径
 *
 @author Alex
 @create 2023-02-13-13:06
 */

// /cookieServlet
public class UCS01 extends BaseServlet {
    // 创建cookie
    protected void createCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 创建 Cookie 对象
        Cookie cookie1 = new Cookie("key1", "value1");
        //2 通知客户端保存 Cookie
        resp.addCookie(cookie1);
        //3 创建 Cookie 对象
        Cookie cookie2 = new Cookie("key2", "value2");
        //4 通知客户端保存 Cookie
        resp.addCookie(cookie2);
        resp.getWriter().write("Cookie 创建成功");
    }

    // 获取cookie
    protected void getCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            resp.getWriter().write("cookie[" + cookie.getName() + "=" + cookie.getValue() + "] <br/>");
        }

        // 查找指定名称的cookie
        Cookie iWantCookie = CookieUtils.findCookie("key1", cookies);
        // 如果不等于null，说明赋过值，也就是找到了需要的Cookie
        if (iWantCookie != null) {
            resp.getWriter().write("找到了需要的 Cookie");
        }
    }

    // 修改cookie
    protected void updateCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 方案1
        Cookie cookie = new Cookie("key1", "111");
        resp.addCookie(cookie);
        // 方案2
        Cookie[] cookies = req.getCookies();
        Cookie cookie1 = CookieUtils.findCookie("key2", cookies);
        if(cookie1!=null){
            cookie1.setValue("222");
            resp.addCookie(cookie1);
        }
        resp.getWriter().write("cookie修改成功");
    }

    // cookie默认存活时间（session级别，浏览器关闭则删除）
    protected void defaultCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("defaultLife", "defaultLife");
        // 设置存活时间
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);
        resp.getWriter().write("已经创建了一个默认存活时间的 Cookie");
    }

    // 马上删除的cookie
    protected void deleteNow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = CookieUtils.findCookie("key1", req.getCookies());
        if(cookie!=null){
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        resp.getWriter().write("已经创建了一个立即被删除的 Cookie");
    }

    // 设置存活1个小时的 Cookie
    protected void life3600(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("life3600", "life3600");
        cookie.setMaxAge(60 * 60); // 设置 Cookie 一小时之后被删除
        resp.addCookie(cookie);
        resp.getWriter().write("已经创建了一个存活一小时的 Cookie");
    }

    // Cookie的有效路径Path设置
    protected void testPath(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("path1", "path1");
        cookie.setPath(req.getContextPath() + "/abc" ); // ===>>>> /工程路径/abc
        resp.addCookie(cookie);
        resp.getWriter().write("创建了一个带有 Path 路径的 Cookie");
        // 此时只有将请求地址改为http://localhost:8080/javaweb/abc/cookie.html才能看到我们添加的cookie
    }
}
