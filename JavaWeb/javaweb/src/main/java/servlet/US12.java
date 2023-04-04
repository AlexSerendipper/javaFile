package servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 【FilterConfig类】比较少用到
 *  FilterConfig类见名知义，是 Filter 过滤器的配置文件类。其作用是获取 filter 过滤器的配置内容
 *  Tomcat 每次创建 Filter 的时候，也会同时创建一个 FilterConfig 类，这里包含了 Filter 配置文件的配置信息。
 *    filterConfig.getFilterName()                      # 获取Filter的名称，即filter-name的内容
 *    filterConfig.getInitParameter("username")         # 获取在Filter中配置的init-param初始化参数username
 *    filterConfig.getServletContext()                  # 获取ServletContext对象
 -------------------------------------------
 <!--配置初始化参数，需要写在filter标签中-->
 <filter>
     <init-param>
     <param-name>username</param-name>
     <param-value>root</param-value>
     </init-param>
     <init-param>
     <param-name>url</param-name>
     <param-value>jdbc:mysql://localhost3306/test</param-value>
     </init-param>
 </filter>
 -------------------------------------------

 *【FilterChain类】过滤器链类。
 *  该类主要实现多个过滤器一起工作
 *  ✔✔✔当执行filterChain.doFilter()方式时，如果有下一个Filter过滤器，就执行。如果没有下一个Filter过滤器就执行目标资源
 *           目标资源包括：html页面、jsp页面、txt文本、jpg图片、servlet程序等！ 即访问这些资源时都可以触发filterChain
 *  ✔当有多个filter，它们的执行顺序是由它们在web.xml中从上到下的配置顺序决定的
 *  多个filter特点：
 *    （1）所有的filter和目标资源默认都执行在用一个线程中
 *    （2）✔所有的filter共享request对象
 *
 @author Alex
 @create 2023-02-16-20:34
 */

// /filter2
// filter1和filter2都是拦截 /jump_forUS06/*
public class US12 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filterUS12被执行了");
        System.out.println("filterUS12的线程为" + Thread.currentThread().getName());  // 所有的filter和目标资源默认都执行在用一个线程中
        System.out.println("在filterUS12中取出filterUS11存储的数据为" + servletRequest.getAttribute("username"));  // 所有的filter共享request对象

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        // 如果等于 null，说明还没有登录
        // 在US06中，当用户成功登录，会将User对象存入session域中
        if (user == null) {
            // 没有登录成功
            // 若访问jump目录，则会被转发到hello jsp网页
            servletRequest.getRequestDispatcher("/index.jsp").forward(servletRequest,servletResponse);
            return;
        } else {
            // 让程序继续往下访问用户的目标资源(放行)
            // 即若访问jump目录，可以顺利访问不被拦截
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
