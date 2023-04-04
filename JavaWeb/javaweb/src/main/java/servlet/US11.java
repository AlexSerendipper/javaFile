package servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.spec.XPathType;
import java.io.IOException;


/**
 * 【filter过滤器】
 *  Filter过滤器是JavaWeb的三大组件之一。
 *  Filter过滤器是 JavaEE的规范(也就是接口)，其主要作用为：拦截请求，过滤响应。
 *  常用操作有：1、权限检查 2、日记操作 3、事务管理……等等
 *
 *【filter的使用】
 * （1）导入filter（注意别导入错包了），编写类实现filter接口
 *       import javax.servlet.Filter
 * （2）实现拦截方法doFilter，✔✔注意我们需要删除init方法和destroy中对父类方法的引用
 *       filterChain.doFilter(servletRequest,servletResponse);               # ✔放行
 * （3）配置xml
 * （4）访问目标资源时触发filter。目标资源包括：html页面、jsp页面、txt文本、jpg图片、servlet程序✔等！
 --------------------------------------------------
 <!--filter 标签用于配置一个 Filter 过滤器-->
 <filter>
 <!--filter别名-->
 <filter-name>AdminFilter</filter-name>
 <!--配置filter全类名-->
 <filter-class>servlet.US11</filter-class>
 </filter>
 <!--mapping配置Filter的拦截路径-->
 <filter-mapping>
 <!--指明使用哪个filter,一般和别名相同-->
 <filter-name>AdminFilter</filter-name>
 <!--url-pattern配置filter的web路径。其中：/ 表示：http://ip:port/工程路径/  -->
 <!--当访问web路径的中的资源时，触发filter -->
 <url-pattern>/admin/*</url-pattern>
 </filter-mapping>
----------------------------------------------------------
 * 【filter匹配方式】
 * （1）精确匹配
 *  <url-pattern>/target.jsp</url-pattern>        # 表示只有请求访问target.jsp会被拦截：http://ip:port/工程路径/target.jsp
 * （2）目录匹配
 * <url-pattern>/admin/*</url-pattern>            # 表示请求地址为admin下的所有文件都会被拦截：http://ip:port/工程路径/admin/*
 * （3）后缀名匹配
 * <url-pattern>*.html</url-pattern>              # 表示请求地址必须以.html 结尾才会拦截到
 * <url-pattern>*.do</url-pattern>                # 注意，配置后缀名匹配时，不能以斜杠开头！~
 * <url-pattern>*.action</url-pattern>
 *
 * 【filter注意事项】
 *  filter并不能拦截请求转发的访问
 *  当访问图片没有被filter拦截时，很可能是因为浏览器直接读取缓存
 *  html资源中若引用了相关路径下的资源，如引用了jquery、css文件等，也会被filter拦截
 *
 * 【filter的生命周期】了解
 * （1）构造器方法
 * （2）init初始化方法
 *     第1，2步，在web工程启动的时候执行（Filter 已经创建）
 * （3）doFilter 过滤方法
 *     第3步，每次拦截到请求，就会执行
 * （4）destroy 销毁
 *     第4步，停止web工程的时候，就会执行（停止web工程，也会销毁Filter过滤器）
 *
 *
 @author Alex
 @create 2023-02-16-15:58
 */
// /filter1
// filter1和filter2都是拦截 /jump_forUS06/*
public class US11 implements Filter {

    public US11() {
        System.out.println("构造器方法，最先被执行");
    }

    // 构造器方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(" Filter的 init(FilterConfig filterConfig)初始化");
        // 1、获取 Filter 的名称 filter-name 的内容
        System.out.println("filter-name 的值是：" + filterConfig.getFilterName());
        // 2、获取在 web.xml 中配置的 init-param 初始化参数
        System.out.println("初始化参数 username 的值是：" + filterConfig.getInitParameter("username"));
        System.out.println("初始化参数 url 的值是：" + filterConfig.getInitParameter("url"));
        // 3、获取ServletContext对象
        System.out.println("创建了ServletContext对象：" + filterConfig.getServletContext());
    }

    /**
     * doFilter方法，专门用来拦截请求。做权限检查。这里以webapp目录中的index.html中为例，发现filter并不能拦截请求转发的访问
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        System.out.println("filterUS11被执行了");
        System.out.println("filterUS11的线程为" + Thread.currentThread().getName());
        servletRequest.setAttribute("username","小钟钟");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    // 销毁方法
    @Override
    public void destroy() {

    }
}
