package basicfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 【SpringMVC的视图】
 *  SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户
 *  SpringMVC视图的种类很多，默认有 转发视图 和 重定向视图
 *  此外，还存在着一些其他的视图
 *  （1）如 当工程引入jstl的依赖时，转发视图会自动转换为JstlView
 *  （2）若 在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，则由此视图解析器解析之后所得到的是 ThymeleafView
 *
 * 【Thymeleaf & InternalResourceView & RedirectView】代替jsp
 * （1）ThymeleafView
 *  ✔✔✔当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，
 *          视图名称拼接视图前缀和视图后缀所得到的最终路径，通过转发的方式实现跳转（以ThymeleafView的形式）
 * （2）InternalResourceView（转发视图）
 *  SpringMVC中默认的转发视图是 InternalResourceView(不同于ThymeleafView)
 *  ✔当控制器方法中所设置的视图名称 以"forward:"为前缀时，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，
 *         而是会将前缀"forward:"去掉，直接将剩余部分作为最终路径 转发（以InternalResourceView形式）
 *  无法使用forward跳转到具体的资源，因为 / 会直接被前端控制器DispatcherServlet处理。。所以只能请求另一前端控制器✔
 * （3）RedirectView（重定向视图是）
 *  ✔当控制器方法中所设置的视图名称以"redirect:"为前缀时，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，
 *    而是会将前缀"redirect:"去掉，直接将剩余部分作为最终路径 重定向（RedirectView形式）
 *  无法使用redirect跳转到具体的资源，因为 / 会直接被前端控制器 DispatcherServlet处理。。所以只能请求另一前端控制器✔
 *
 * 【view-controller】视图控制器，在SpringMVC中进行配置
 *  当某个 控制器方法 仅仅用来实现页面跳转( 即只返回视图名称，无需其他操作 )，可以将该处理器方法使用view-controller代替
 *  当配置了view-controller后，其他的控制器将全部失效，此时需要配置开启 mvc注解驱动已解决该问题
 *
 * 【访问静态资源失效问题】
 *  当我们访问web工程中的静态资源时，例如，vue.js，图片，jquery.js等.....   如http://localhost:8080/restful/static/vue.js
 *   会出现请求失效的情况，这是由于我们所有的请求都交予SpringMVC的前端控制器统一处理，根据路径去找相应的控制器
 *   要想解决该问题，需要配置 <mvc:default-servlet-handler/>，表示当使用SpringMVC的前端控制器没有找到资源时，使用系统默认servlet进行查找（即直接根据web路径进行查找）
 *   同样的，当配置了default-servlet-handler后，需要配置开启 mvc注解驱动，否则所有的请求都将由default-servlet处理
 *  原理解释：在tomcat安装目录下的web.xml文件中配置了default-servlet，其 url-pattern 为 /，此处的配置适用于所有使用了tomcat服务器的资源
 *             实际上 webapp中的web.xml 与 tomcat安装目录下的web.xml 是继承关系~
 *             由于 webapp中的web.xml中配置了前端控制器，其 url-pattern 也是 /，相当于重写了tomcat安装目录下的web.xml的相关信息
 *             所以需要进行配置开启静态资源的访问
------------------------------------------
<!--1、配置视图控制器，path：设置处理的请求地址，view-name：设置请求地址所对应的视图名称-->
<mvc:view-controller path="/1" view-name="index"></mvc:view-controller>

<!--2、使用默认的default-servlet，开启静态资源访问-->
<mvc:default-servlet-handler/>

<!--3、开启mvc注解驱动-->
<mvc:annotation-driven />
------------------------------------------
 *
 @author Alex
 @create 2023-03-04-10:45
 */

@Controller
public class UBF06 {
    // 当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，通过转发的方式实现跳转
    @RequestMapping("/test1/UBF06")
    public String testView1() {
        return "UBF06";
    }

    // 当控制器方法中所设置的视图名称 以"forward:"为前缀时，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，直接将剩余部分作为最终路径通过转发的方式实现跳转
    // 该控制器创建了两个视图，先创建了InternalResourceView然后创建了ThymeleafView
    @RequestMapping("/test2/UBF06")
    public String testView2() {
        return "forward:/test1/UBF06";
    }

    @RequestMapping("/test3/UBF06")
    public String testView3() {
        return "redirect:/test1/UBF06";
    }

}
