package servlet;

import jdk.internal.dynalink.linker.LinkerServices;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 【servlet】
 *  Servlet就 JavaWeb 三大组件之一。三大组件分别是：Servlet程序、Filter过滤器、Listener监听器。
 *  Servlet运行在服务器上，主要负责接收客户端发送过来的请求，并响应数据给客户端。
 *  Servlet作为一个接口，定义了发送和相应请求的规范
 *
 * 【✔实现servlet】继承的方式更为常见
 * （1）编写一个类去实现 Servlet接口
 * （2）实现service方法，处理请求，并响应数据（GET和POST请求都会触发service方法）
 * （3）到web.xml中去配置servlet程序的访问地址。配置文本见下方
 *
 * 【servlet执行过程】生命周期
 *（1）执行 Servlet 构造器方法，仅创建servlet时调用
 *（2）执行 init 初始化方法, 仅创建servlet时调用
 * (3) 执行 service 方法，每次访问都会调用。
 *（4）执行 destroy 销毁方法。web 工程停止的时候调用。
 *
 * 【WEB-INF --> web.xml配置】直接删掉重写一个，或者复制一下。。配置完建议重启tomcat
 ------------------------------------------------------------
 <?xml version="1.0" encoding="UTF-8"?>
 <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
 http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
 version="4.0">
 <!-- servlet标签：配置一个Servlet程序 -->
 <servlet>
     <!--servlet-name标签：为Servlet程序起一个别名（一般是类名） -->
     <servlet-name>HelloServlet</servlet-name>
     <!--servlet-class标签：指定Servlet程序的全类名！-->
     <servlet-class>servlet.US01</servlet-class>
 </servlet>
 <!--servlet-mapping标签：配置访问servlet程序的地址，我们需要将它映射到webapp目录下-->
 <servlet-mapping>
     <!--servlet-name标签：指定使用哪一个Servlet程序，一般上面定义哪个就用哪个-->
     <servlet-name>HelloServlet</servlet-name>
     <!--url-pattern标签：配置访问Servlet程序在web中的路径（即浏览器输入的地址），'/'通常映射为'http://ip:port/工程名,故访问地址为http://ip:port/工程目录/hello-->
     <url-pattern>/hello</url-pattern>
 </servlet-mapping>
 </web-app>
 -------------------------------------------------------------

 @author Alex
 @create 2023-01-28-13:07
 */

// /implement
public class US01 implements Servlet{
    // servletConfig类在 实现servlet 的使用
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 1、可以获取 Servlet 程序的别名 servlet-name 的值
        System.out.println("程序的别名是:" + servletConfig.getServletName());
        // 2、获取初始化参数 init-param
        System.out.println("初始化参数 username 的值是;" + servletConfig.getInitParameter("username"));
        // 3、获取 ServletContext 对象
        System.out.println(servletConfig.getServletContext());
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod();
        if ("GET".equals(method)) {
            doGet();
        } else if ("POST".equals(method)) {
            doPost();
        }
    }

    public void doGet(){
        System.out.println("get 请求");
        System.out.println("get 请求");
    }

    public void doPost(){
        System.out.println("post 请求");
        System.out.println("post 请求");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

