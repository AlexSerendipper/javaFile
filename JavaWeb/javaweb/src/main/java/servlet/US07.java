package servlet;

import jsp.Student;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 【HttpServletResponse类】
 *  与HttpServletRequest类似。每次请求进来，Tomcat服务器都会创建一个Response对象供Servlet程序去使用。
 *    当需要设置返回给客户端的信息，都可以通过HttpServletResponse对象来进行设置
 *  HttpServletResponse类通过流的形式回传数据给客户端
 *    不能同时使用字节流和字符流，否则报错
 *
 *【HttpServletResponse类的使用】
 *  resp.setContentType("text/html; charset=UTF-8");         # 解决乱码方式一（推荐使用）：默认使用的字符集为ISO-8859字符集，
 *                                                            # ✔该方法可以同时设置服务器和客户端都使用UTF-8字符集，还设置了响应头
 *                                                            # 注意：此方法一定要在获取流对象之前调用才有效
 *  resp.setCharacterEncoding("UTF-8");                            # 解决乱码方式二（不推荐使用）: 设置服务器使用UTF-8字符集
 *   resp.setHeader("Content-Type", "text/html; charset=UTF-8");    # 通过响应头，设置浏览器也使用 UTF-8 字符集
 *  resp.getOutputStream();                                 # ✔获取字节流（传递二进制数据）
 *  resp.getWriter();                                       # ✔获取字符流（回传字符串）
 *
 * 【请求重定向】
 *  请求重定向：是指客户端给服务器发请求，然后服务器告诉客户端说。我给你一些地址。你去新地址访问。
 *   因为随着项目的更新，原先的servlet1接口要被servlet2接口取代，此时需要重定向操作
 *   当客户访问servlet1接口的时候，servlet1有义务告知客户端已废弃的消息（通过响应码：302）。并且通过响应头告知客户端servlet2的地址
 *   客户端通过解析服务器返回的消息（响应码302和新地址）得知旧地址已经废弃，然后访问新地址
 *  请求重定向的第一种方案（不建议使用）：
 *    resp.setStatus(302);                                      # 设置响应状态码 302 ，表示重定向，（已搬迁）
 *    resp.setHeader("Location", "http://localhost:8080");      # 设置响应头，说明 新的地址在哪里
 *  请求重定向的第二种方案（推荐使用）：
 *   ✔✔✔在绝大多数javaweb程序中，'/'通常映射为'http://ip:port/工程名'。但在重定向中 "/" 表示为 http://ip:port/
 *    resp.sendRedirect(req.getContextPath() + "jump_forUS06/jump/index.html");
 *
 * 【请求重定向的特点】建议与请求转发对比，请求转发是先后通过servlet1和servelet2处理。而请求重定向是：
 * （1）浏览器的地址栏会发生变化
 * （2）两次请求
 * （3）不共享request域中的数据
 * （4）不可以重定向到到WEB-INF目录下
 * （5）可以访问工程以外的资源
 *
 * 【数据封装工具包BeanUtils】如果不用工具包，就需要一个个把bean对象的属性set过去
 *  需要导入commons-beanutils-1.8.0.jar，commons-logging-1.1.1.jar两个包
 *  BeanUtils工具类，它可以一次性的把所有请求的参数注入到JavaBean中。
 *  （将请求参数以map的形式作为形参传递给BeanUtils）
 *  底层实现：选取请求参数中的 key值与bean对象重名的属性，调用bean对象的set方法设置属性
 *    BeanUtils.populate(req.getParameterMap(), bean对象)
 *
 @author Alex
 @create 2023-01-29-16:00
 */

// /response
public class US07 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write("使用流返回给客户端数据");
        System.out.println("****************************");
        resp.sendRedirect(req.getContextPath() + "jump_forUS06/jump/index.html");
    }

    // 数据封装工具包BeanUtils的使用
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student stu = WebUtils.copyParamToBean(req.getParameterMap(), new Student());
        System.out.println(stu);
    }
}

class WebUtils{
    public static <T> T copyParamToBean(Map value , T bean){
        try {
            // 把所有请求的参数都注入到 user 对象中
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}