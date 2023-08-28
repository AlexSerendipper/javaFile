package restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 【RESTful概述】
 *  RESTful概述是一种设计风格！即引入一种大家都遵守的风格，方便大家互相之间能看得懂代码！
 *  REST：Representational State Transfer，表现层资源状态转移
 *   资源: 资源是一个抽象的概念，实际上部署到tomcat服务器上的东西，都可以称为资源。例如HTML/XML/JSON/纯文本/图片/视频/音频等等
 *         资源是以名词为核心来组织命名的，
 *   状态转移：：在客户端和服务器端之间转移（transfer）代表资源状态的表述。通过转移和操作资源的表述，来间接实现操作资源的目的
 *  ✔✔表现形式 (1) 我们对同一个资源的操作（如对数据表的增删改查），使用同一个数据描述（同一个URL地址），通过不同的操作方法进行区分（post/delete/put/get）
 *                 (2) 不使用问号键值对的方式携带请求参数，而是将要发送给服务器的数据作为 URL 地址的一部分（占位符方式），以保证整体风格的一致性
 *            ---------------------------------------------------------------------
 *               操作                        传统方式                       REST风格
 *             查询操作                getUserById?id=1                user/1-->get请求方式
 *             保存/添加操作           saveUser                        user-->post请求方式
 *             删除操作                deleteUser?id=1                 user/1-->delete请求方式
 *             更新操作                updateUser                      user-->put请求方式
 *
 *
 * 【RESTful实现】
 *  HTTP协议中，有四种基本操作：post/delete/put/get，分别对应着 新建资源/删除资源/更新资源/获取资源
 *  由于浏览器只支持发送get和post方式的请求，SpringMVC提供了 HiddenHttpMethodFilter 帮助我们将 POST 请求转换为 DELETE 或 PUT 请求
 *  HiddenHttpMethodFilter 处理 put和delete 请求的条件✔✔：
 *    当前请求的请求方式必须为 post
 *    当前请求必须传输请求参数 _method
 *   只要满足以上两个条件，过滤器就会将当前请求的请求方式转换为请求参数_method的值
 *  在web.xml中注册HiddenHttpMethodFilter
 *  注意：在web.xml中注册时，必须先注册 CharacterEncodingFilter过滤器，再注册 HiddenHttpMethodFilter
 *   因为注册多个过滤器时，是按照注册的先后顺序执行，而CharacterEncodingFilter过滤器，必须在获取到请求参数之前设置编码格式，否则设置无效
 *   而 HiddenHttpMethodFilter过滤器中恰好是需要获取请求参数 _method的
 *
 * 【RESTful的实现 需要配合 @RequestMapping的value属性】
 *  value属性支持路径占位符✔✔✔✔✔
 *    SpringMVC路径中的占位符常用于RESTful风格中，即把请求参数均通过路径的方式传输到服务器中
 *    在Value属性中通过占位符{xxx}表示传输的数据，如 /get/{name}/{id}
 *     在通过 @PathVariable("占位符")注解，将占位符所表示的数据赋值给控制器方法的形参, 如 public String getName(@PathVariable("name") username)
 *     还可以 将占位符中所有的数据 全部自动放在map中，如public String getName(@PathVariable Map<String,String> pv)
 *    若使用了路径占位符，则在请求路径中 必须为占位符处赋值
 *  @PathVariable传入空参数，如：@PathVariable(value = "pageNum",required = false)
 *                            不传参则默认值为null，注意此时必须在@RequestMapping中指定多个访问路径，如@GetMapping(value = {"/index/{pageNum}","/index"})
 *
 * 【源码分析】
 * 查看HiddenHttpMethodFilter类中的doFilterInternal方法即可
 *
---------------------------------------
<!--配置HiddenHttpMethodFilter过滤器，帮助发送Put和delete请求-->
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
---------------------------------------
 @author Alex
 @create 2023-03-04-14:55
 */

@Controller
public class UR01 {
    /**
     * 使用RESTFul模拟用户的增删改查操作
     * user-->get请求-->查询所有用户信息
     * user/1-->get请求-->根据id查询用户信息
     * user-->post请求-->添加用户信息
     * user/1-->delete请求-->根据id删除用户信息
     * user-->put请求方式-->更新用户信息
     */

    @RequestMapping(value = "/UR01.html", method = RequestMethod.GET)
    public String index(){
        System.out.println("查询所有用户信息");
        return "UR01";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getAllUser(){
        System.out.println("查询所有用户信息");
        return "success";
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(){
        System.out.println("根据id查询用户信息");
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String addUser(String username,String password){
        System.out.println("添加用户信息:" + "username:" + username + "password:" + password);
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String updateUser(String username,String password){
        System.out.println("修改用户信息为:" + "username:" + username + "password:" + password);
        return "success";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String deleteUser(){
        System.out.println("根据id删除用户信息");
        return "success";
    }
}
