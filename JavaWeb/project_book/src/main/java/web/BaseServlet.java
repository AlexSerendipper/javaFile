package web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 【Servlet基类】
 * 由于所有的Servlet都是进行相同的功能
 *  1、获取action参数
 *  2、通过反射获取action对应的业务方法
 *  3、通过反射调用业务方法
 @author Alex
 @create 2023-02-03-12:31
 */
public abstract class BaseServlet extends HttpServlet {
    // 让doget和dopost做一样的事就好啦
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        // 一定要设置 呜呜呜 不然请求的参数都是乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
//        if ("login".equals(action)) {
//            login(req, resp);
//        } else if ("regist".equals(action)) {
//            regist(req, resp);
//        }
        // 利用反射优化上述的if else操作
        try {
            // 当请求发送，如 /bookServlet?action=add
            // 首先就是执行bookServlet中的doPost方法，然后执行到父类，也就是baseServlet中的doPost方法
            // 然后这里this.getClass()指向的是子类bookServlet中重写的getClass方法（默认重写）
            // 故最后执行的是bookServlet中的add方法
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);  // 把异常抛给 Filter 过滤器
        }
    }
}
