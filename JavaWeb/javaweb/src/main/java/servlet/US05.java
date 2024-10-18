package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 【HttpServletRequest类】part2
 * 【请求转发】见下方示例
 *  请求转发是指，服务器收到请求后，从一次资源跳转到另一个资源的操作叫请求转发
 *  如客户端请求tomcat服务器先要经过servlet1处理再经过servlet2处理，最后返回给客户端。
 *   从servlet1跳到servlet2的过程就叫做请求转发。
 *
 * 【常用方法】
 *  RequestDispatcher requestDispatcher = req.getRequestDispatcher("/path") ;  ✔1）获取请求转发对象（ '/'通常映射为'http://ip:port/工程名）
 *   requestDispatcher.forward(req,resp);                                       ✔2）请求转发，跳转到对应的servlet上（path地址，即为跳转的servlet地址）
 *  setAttribute(key, value);                                 设置域数据(同ServletContext类，同个工程下的资源，可以共享其中的域数据)
 *  getAttribute(key);                                        获取域数据
 *
 * 【请求转发的特点】
 * （1）浏览器的地址栏不会变化
 * （2）一次性请求
 * （3）共享request域中的数据
 * （4）可以转发到WEB-INF目录下，利用req.getRequestDispatcher("/WEB-INF/xxx.html")
 *     当输入http://ip:port/工程名，默认映射到webapp这个目录。但无法方法WEB-INF目录。而是用请求转发可以访问到WEB-INF目录下
 * （5）不可以访问工程以外的资源
 * （6）转发后仍保留转发前的请求参数
 * @author Alex
 @create 2023-01-29-13:29
 */

// servlet1
public class US05 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数（办事的材料）查看
        String username = req.getParameter("username");
        System.out.println("在 Servlet2（柜台 2）中查看参数（材料）：" + username);
        // 查看 柜台 1 是否有盖章
        Object key1 = req.getAttribute("key1");
        System.out.println("柜台 1 是否有章：" + key1);
        // 处理自己的业务
        System.out.println("servlet2处理中");
    }
}

