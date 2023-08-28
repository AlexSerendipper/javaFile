package webfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 【Web开发常用功能使用】
 *
 * 【静态资源访问】
 * （1）普通资源
 *  默认需要将静态资源 放在 类路径下的固定名称文件夹中。/static。/public。/resources。/META-INF/resources
 *   此时即可直接通过 上下文路径 + 静态资源名 实现访问静态资源
 *  无需像之前那样配置 defaultServlet，但是实现效果相同！！
 *   即请求进来，如果没有适配的controller，再将请求交给静态资源处理器。静态资源也找不到则响应404页面
 *  spring.resources.static-locations= [classpath:/haha/]    # ✔改变默认的静态资源路径（默认为static文件夹）
 *  spring.mvc.static-path-pattern = /static/**              # ✔设置静态资源访问前缀（默认为/**  ==>  对应上下文路径+静态资源名实现访问静态资源）
 *
 * （2）webjar：了解：将前端要用到的一些资源打包成jar包。https://www.webjars.org/
 * i:引入相关依赖
 * ii:引入后可以在External libraries中查看，可直接通过： 上下文路径/webjars/具体包路径
 *                                       （/webjars/** 自动映射  到 /META-INF/resources中）（http://localhost:8080/webjars/jquery/3.5.1/jquery.js）
----------------
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.5.1</version>
</dependency>
----------------
 *
 *  【欢迎页支持】注意：欢迎页支持不可以配置静态资源的访问前缀。否则将导致失效✔
 * （1）默认处理规则：index.html
 *      当输入web上下文路径，自动访问 静态资源路径下的（static文件夹） index.html
 * （2）controller处理：/index
 *
 * 【favicon支持】注意：favicon支持不可以配置静态资源的访问前缀。否则将导致失效✔
 *  把 favicon.ico（名字固定） 放在静态资源目录下（static文件夹）。此时访问页面将使用对应的favicon
 *
 * 【配置当前项目的上下文路径】application.properties
 *  server.servlet.context-path=/web
 *
 @author Alex
 @create 2023-03-19-16:42
 */


public class UW01 {

}
