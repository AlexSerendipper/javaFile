package jsp;

/**
 * 【JSP概述】目前大多采用前后端分离技术，所以JSP已经被逐渐淘汰（被隐藏在框架中了），所以整章不要求熟练掌握✔
 *  JSP(全称 Java Server Pages)是由Sun公司专门为了解决动态生成 HTML文档的技术。
 *   其主要作用就是代替servlet程序回传html页面的数据（servlet回传数据非常繁琐）。如：writer.write("<!DOCTYPE html>\r\n");
 *                                                                            writer.write(" <html lang=\"en\">\r\n");...
 *  jsp页面执行时会被翻译成java文件，其源码中继承了HttpServlet类，所以：
 *   ✔jsp页面本质上是一个Servlet程序。其底层实现就是通过输出流。把 html页面数据回传给客户端
 *  jsp页面无法通过file://协议访问。只能通过http://协议访问。即只能通过浏览器访问Tomcat服务器再访问jsp页面。
 *
 * 【jsp头部page指令】了解即可
 * <%@ page contentType="text/html;charset=UTF-8" language="java" %>
 *  language 属性。表示 jsp 翻译后是什么语言文件。暂时只支持 java。
 *  contentType 属性。表示 jsp 返回的数据类型是什么。也是源码中 response.setContentType()参数值
 *  pageEncoding 属性。表示当前 jsp 页面文件本身的字符集。
 *  import 属性。跟 java 源代码中一样。用于导包，导类。=
 *  autoFlush 属性。供out输出流使用。设置当 out 输出流缓冲区满了之后，是否自动刷新冲级区。默认值是 true。
 *  buffer 属性。供out输出流使用。设置 out 缓冲区的大小。默认是 8kb
 *  errorPage 属性。设置当 jsp 页面运行时出错，自动跳转去的错误页面路径。
 *  isErrorPage 属性。设置当前 jsp 页面是否是错误信息页面。默认是 false。如果是 true 可以获取异常信息。
 *  session 属性。设置访问当前 jsp 页面，是否会创建 HttpSession对象。默认是 true。
 *  extends 属性。设置 jsp 翻译出来的 java 类默认继承谁
 *
 * 【jsp脚本】见UJ01.jsp
 * （1）声明脚本(极少使用)
 *  声明脚本的格式是： <%! 声明java代码 %>
 *  作用：可以给 jsp 翻译出来的 java 类定义属性和方法甚至是静态代码块、内部类等。
 * （2）表达式脚本（常用）
 *  表达式脚本的格式是：<%=表达式%>
 *  表达式脚本的作用是：✔在jsp页面上直接输出数据。
 *  表达式脚本的特点：
 *     所有的表达式脚本都会被翻译到_jspService()方法中（被翻译成的底层java文件中的方法）
 *     表达式脚本都会被翻译成为 out.print()输出到页面上
 *     由于表达式脚本翻译的内容都在_jspService() 方法中,所以_jspService()方法中的对象都可以直接使用（✔称为jsp内置对象，如request对象，response对象等）
 *     表达式脚本中的表达式不能以分号结束
 * （3）代码脚本（不常用，多用EL）
 *  代码脚本的格式是：<% java 语句%>
 *  代码脚本的作用是：✔可以在 jsp 页面中，编写我们自己需要的功能（写的是 java 语句）。
 *  代码脚本的特点是：
 *     代码脚本翻译之后都在_jspService 方法中
 *     代码脚本由于翻译到_jspService()方法中，所以在_jspService()方法中的现有对象都可以直接使用。
 *     代码脚本可以由多个代码脚本块组合完成一个完整的java语句。
 *     代码脚本还可以和表达式脚本一起组合使用，在jsp页面上输出数据（其实就是根据其上一个特点，然后将需要显示在页面上的内容露出来罢了）
 *
 * 【jsp内置对象】
 * （1）九大内置对象
 *  request          # 请求对象
 *  response         # 相应对象
 *  pageContext      # jsp的上下文对象
 *  session          # 会话对象
 *  application      # ServletContext对象
 *  config           # ServletConfig对象
 *  out              # jsp输出流对象
 *  page             # 指向当前jsp的对象
 *  exception        # 异常对象
 * （2）其中，四大域对象分别为✔✔✔
 *  pageContext      # PageContextImpl类。当前 jsp 页面范围内有效
 *  request          # HttpServletRequest类。一次请求内有效
 *  session          # HttpSession类。 一个会话范围内有效（只要浏览器不关闭，数据都在）
 *  application      # ServletContext类。整个web工程范围内都有效（只要web工程不停止，数据都在）
 *  域对象是可以像Map一样存取数据的对象。四个域对象功能一样。不同的是它们对数据的存取范围。
 *  虽然四个域对象都可以存取数据。在使用上它们是有优先顺序的。建议顺序是：
 *  pageContext ====>> request ====>> session ====>> application
 * （3）out输出和response输出的区别
 *  out.write()                     # 输出字符串没有问题
 *  out.print()                     # 输出任意数据都没有问题
 *  response.getWriter.write()      # ✔设置setContentType后输出任意数据都没有问题
 *  当jsp页面中的所有代码执行完成后，首先会将out缓冲区的数据追加到response缓冲区末尾，然后执行response的刷新操作，把所有数据写给客户端
 *  由于jsp翻译之后，底层源代码都是使用 out 来进行输出，所以建议，我们在 jsp 页面中统一使用out来进行输出。避免打乱页面输出内容的顺序
 *  ✔建议：在jsp页面中，可以统一使用 out.print()来进行输出
 *
 @author Alex

 @create 2023-02-01-18:55
 */
public class UJ01 {
}
