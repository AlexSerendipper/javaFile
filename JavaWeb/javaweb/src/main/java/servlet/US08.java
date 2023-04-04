package servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 【监听器】
 *  Listener监听器是JavaWeb的三大组件之一。是一个接口，即JavaEE的规范，
 *  监听器的作用是，监听某种事物的变化。然后通过回调函数，反馈给客户（程序）去做一些相应的处理。
 *  三大组件的执行顺序为：监听器 ==> 拦截器 ===> Servlet
 *
 * 【ServletContextListener】监听器实际上有八个，随着技术的变革，绝大部分的监听器已经用不上了
 *  ServletContextListener它可以监听 ServletContext对象的创建和销毁✔（web工程启动的时候创建，在web工程停止的时候销毁）
 *  监听到创建和销毁之后都会分别调用 ServletContextListener 监听器的方法
 *     public void contextInitialized(ServletContextEvent sce);        # 在 ServletContext 对象创建之后马上调用，做初始化
 *     public void contextDestroyed(ServletContextEvent sce);          # 在 ServletContext 对象销毁之后调用
 *
 * 【ServletContextListener使用流程】
 * （1）编写一个类去实现 ServletContextListener
 * （2）实现其两个回调方法
 * （3）到 web.xml 中去配置监听器：如下
---------------------------------------------------------------
 <!--配置监听器-->
 <listener>
     <listener-class>servlet.US08</listener-class>
 </listener>
 -------------------------------------------------------------
 @author Alex
 @create 2023-02-02-13:51
 */
public class US08 implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("US08中的监听器，监听到了 ServletContext 对象被创建了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("US08中的监听器，监听到了 ServletContext 对象被销毁了");
    }
}
