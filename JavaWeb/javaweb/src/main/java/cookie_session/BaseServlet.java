package cookie_session;

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
        // 解决相应乱码问题
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
        System.out.println("action = " + action);
//        if ("login".equals(action)) {
//            login(req, resp);
//        } else if ("regist".equals(action)) {
//            regist(req, resp);
//        }
        // 利用反射优化上述的if else操作
        try {
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
