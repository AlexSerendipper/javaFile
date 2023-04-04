package cookie_session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 【session】
 *  Session是一个接口（HttpSession）。是四大域对象之一
 *  Session就是会话。是用来维护一个客户端和服务器之间关联的一种技术。
 *  每个客户端都有自己的一个Session会话（即通常一个session代表着一个客户端）
 *  Session会话中，我们经常用来保存用户登录之后的信息。即当客户端关闭后失效✔
 *
 * 【Session常用方法】每个会话都有一个唯一的ID值
 *  request.getSession()                                  # Session的创建和获取
 *    第一次调用是：创建 Session 会话
 *    之后调用都是：获取前面创建好的 Session 会话对象。
 *  isNew(); 判断到底是不是刚创建出来的（新的）
 *  getId() 得到会话的id值
 *  session.setAttribute("key1", "value1");              # 存数据（域对象）
 *  session.getAttribute("key1");                        # 取数据（域对象）
 *
 * 【session声明周期】
 *  session默认的超时时间长为 30 分钟。在web.xml配置文件中做如下配置。可以修改你的web工程所有 Seession的默认超时时长。
 *  session的超时时间指的是客户端 连续两次请求 的时间间隔（所以当我们连续发起请求，并不会触发超时）
 *    session.setMaxInactiveInterval(int interval)         # 设置当前Session超时时间（s），超过指定的时长Session就会被销毁
 *                                                          # 值为正数的时候，设定 Session 的超时时长。负数表示永不超时（极少使用）
 *    session.getMaxInactiveInterval()                    # 获取 Session 的超时时间
 *    session.invalidate()                                # 让当前Session会话马上超时（无效）
 *
 *  【session底层原理】见xmind
 *  为什么当关闭浏览器，不论生命周期设置了多少，当前session都会无效？
 *  原因是：当我们创建session时，都会将session的id以cookie的形式保存在客户端（所以session的底层是cookie）
 *   并且cookie的声明周期为默认。当我们再次创建session，服务器会通过cookie中的id值找到之前创建好的session对象~
 *   所以当关闭浏览器，亦或是删除了cookie，无法再找到先前的session，再次创建session就会创建一个新的session
 ---------------------------------------------------------------------
 <!--设置当前web工程创建出的所有Session默认是20分钟超时时长-->
 <session-config>
     <session-timeout>20</session-timeout>
 </session-config>
 ---------------------------------------------------------------------

 @author Alex
 @create 2023-02-13-17:15
 */

// /sessionServlet
public class UCS03 extends BaseServlet {
    // 创建和获取Session
    protected void createOrGetSession(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 创建和获取
        HttpSession session = req.getSession();
        boolean isNew = session.isNew();
        String id = session.getId();
        resp.getWriter().write("session的id是" + id + "<br/>");
        resp.getWriter().write("这个session是否是新创建的" + isNew);

    }

    // Session域数据的存
    protected void setAttribute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("key1", "value1");
        resp.getWriter().write("已经往 Session 中保存了数据");
    }

    // Session域数据的取
    protected void getAttribute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object attribute = req.getSession().getAttribute("key1");
        resp.getWriter().write("从 Session 中获取出 key1 的数据是：" + attribute);
    }

    // Session域的声明周期
    protected void defaultLife(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int maxInactiveInterval = req.getSession().getMaxInactiveInterval();
        resp.getWriter().write("session的默认超时时常为" + maxInactiveInterval + "s");

    }

    // Session3秒自动销毁
    protected void life3(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 先获取 Session 对象
        HttpSession session = req.getSession();
        // 设置当前 Session3 秒后超时
        session.setMaxInactiveInterval(3);
        resp.getWriter().write("当前 Session 已经设置为 3 秒后超时");
    }

    // Session域的声明周期
    protected void deleteNow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 先获取 Session 对象
        HttpSession session = req.getSession();
        // 让 Session 会话马上超时
        session.invalidate();
        resp.getWriter().write("Session 已经设置为超时（无效）");
    }
}
