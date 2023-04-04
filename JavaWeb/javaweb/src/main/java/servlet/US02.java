package servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 【servlet继承树】
 * Interface Servlet               # 只定义了servlet程序的访问规范
 *       ↑实现
 * Class GenericServlet            # 利用ServletConfig类实现了Servlet的部分接口（一些是空实现）
 *       ↑继承
 * Class HttpServlet               # 实现了service()方法，并实现了请求的分发处理，但是HttpServlet的doGet和doPost只能抛出异常
 *       ↑继承
 *     用户程序                     # 我们只需要根据自己的业务重写doGet和doPost方法即可（✔重写时建议取消对父类的调用，否则可能出错）
 *
 * 【✔✔✔继承servlet】一般在实际项目开发中都是使用继承HttpServlet类的方式
 * （1）编写一个类去继承HttpServlet类
 * （2）根据业务需要重写doGet或doPost方法
 *      取消对父类doget/dopost的调用，否则出错
 *      doGet方法在收到get请求的时候调用
 *      doPost方法在收到post请求的时候调用
 * （3）到 web.xml 中配置 Servlet 程序的访问地址
 *
 * 【ServletConfig类】见US01，该类比较少用
 *   ServletConfig是 Servlet 程序的配置信息类，在Servlet程序创建时自动生成对应的ServletConfig对象。
 *   ServletConfig类有如下作用
 *     servletConfig.getServletName()                    # 可以获取 Servlet程序别名 servlet-name 的值
 *     servletConfig.getInitParameter("username")        # 获取初始化参数 init-param
 *     servletConfig.getServletContext()                 # 获取 ServletContext 对象
 *   其中，初始化参数在web.xml文件中通过<init-param>配置。见下方
 *   servlet实现时，ServletConfig类主要用在init初始化方法中
 *   servlet继承时：如果继承时，重写了init方法，必须要加上super.init(config)。。。。原因。。。再说把。。。
 *     getServletConfig();                               # 方法获取servletConfig实例
 *     servletConfig.getServletName()                    # 可以获取 Servlet程序别名 servlet-name 的值
 *     servletConfig.getInitParameter("username")        # 获取初始化参数 init-param。配置见下方
 *     servletConfig.getServletContext()                 # 获取 ServletContext 对象
 *
 * 【ServletContext类】常用
 *  ServletContext是一个接口，表示Servlet上下文对象
 *  一个web工程，只有一个ServletContext 对象实例。
 *    这意外着ServletContext是一个全局变量。在web工程部署启动时创建。在web工程停止时销毁✔
 *    也意味着同一个工程下的多个资源可以共享ServletContext中的数据✔，只要tomcat没有被重新部署✔
 *  ServletContext对象是一个域对象。javaWeb中有四大域对象，见jsp
 *    域对象，是可以像 Map一样存取数据的对象，这里的域指的是存取数据的操作范围
 *                           存数据             取数据                删数据
 *               Map          put()              get()               remove()
 *              域对象    setAttribute()      getAttribute()      removeAttribute()
 *  ServletContext类有如下作用
 *    ServletContext context = getServletConfig().getServletContext();    # 创建一个ServletContext实例
 *      ServletContext context = getServletContext();                      # 创建一个ServletContext实例法二，推荐使用
 *      context.getInitParameter("键")                                     # 获取上下文参数context-param。配置见下方
 *    context.getContextPath()                                           # ✔获取当前工程路径，即当前工程在web中的地址
 *    context.getRealPath("/")                                           # 获取工程的实际部署地址(即tomcat中设定的伪影地址)
 *    setAttribute()/getAttribute()/removeAttribute()               　  # ✔像Map一样存取数据
 *
 ------------------------------------------------------------
 <servlet>
 <servlet-name>HelloServlet</servlet-name>
 <servlet-class>servlet.US01</servlet-class>
 <!--配置初始化参数-->
 <init-param>
 <!--参数名-->
 <param-name>username</param-name>
 <!--参数值-->
 <param-value>root</param-value>
 </init-param>
 </servlet>
 <!--配置上下文参数(它属于整个 web 工程)-->
 <!--context-param与<servlet>平级，一般放在上方-->
 <context-param>
 <param-name>username</param-name>
 <param-value>context</param-value>
 </context-param>

 <context-param>
 <param-name>password</param-name>
 <param-value>root</param-value>
 </context-param>
 -------------------------------------------------------------

 @author Alex
 @create 2023-01-28-15:58
 */

// /extend
public class US02 extends HttpServlet {
    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    // 建议取消对父类doget的调用，否则可能出错
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        System.out.println("servlet继承");
        ServletConfig servletConfig = getServletConfig();
        System.out.println("程序的别名是:" + servletConfig.getServletName());
        System.out.println("初始化参数 username 的值是;" + servletConfig.getInitParameter("username"));
        System.out.println(servletConfig.getServletContext());
    }

    // ServletContext类类的演示
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        System.out.println("servlet继承");
        // 1、获取 web.xml 中配置的上下文参数 context-param
        ServletContext context = getServletConfig().getServletContext();
        String username = context.getInitParameter("username");
        String password = context.getInitParameter("password");
        System.out.println("context-param 参数 username 的值是:" + username);
        System.out.println("context-param 参数 password 的值是:" + password);
        // 2、获取当前的工程路径，格式: /工程路径
        System.out.println( "当前工程路径:" + context.getContextPath() );
        // 3、获取工程部署后在服务器硬盘上的绝对路径
        System.out.println("工程部署的路径是:" + context.getRealPath(""));
        // 4、存取数据
        System.out.println("保存之前: context中的值是:"+ context.getAttribute("name"));
        context.setAttribute("name","zzj");
        System.out.println("context中的数据是" + context.getAttribute("name"));

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
