package basicfunction;

import basicfunction.pojo.User;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 【SpringMVC获取请求参数】
 * (1) 方式一：通过ServletAPI获取（原生API），一般不用原生API，因为SpringMVC已将其封装为方式二了
 *  将 HttpServletRequest req 作为控制器方法的形参
 *  req.getParameter(key)               # 此时HttpServletRequest类中封装了获取当前请求的方法
 *  req.getParameterValues(key)         # 若有多个同名参数，使用该方法获取
 * 注意：若使用路径占位符的方式请求，无法使用方式一，因为此时没有请求的键值
 *
 * (2) ✔✔方式二：通过 控制器方法的形参 获取请求参数（基于原生API封装）
 *  在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，DispatcherServlet中就会将请求参数赋值给相应的形参
 *  ✔若请求所传输的请求参数中有多个同名的请求参数，可以在控制器方法的形参中设置 字符串类型的形参 或者 字符串数组 接收
 *    若使用字符串类型的形参，此参数的值为每个数据中间使用逗号拼接的结果
 *    若使用字符串数组类型的形参，此参数的数组中包含了每一个数据
 *
 *（3）✔✔方式三： 通过POJO获取请求参数
 *  若客户端传输的 请求参数的参数名 和 某实体类（通常对应一个数据库表） 中的属性名一致
 *   则可以通过在控制器方法的形参位置设置一个 实体类类型的形参 的方式，此时请求参数会自动为该属性赋值
 *
 * 【获取请求参数映射问题 及 特殊请求参数获取】
 *（1）@RequestParam：设置 控制器方法的形参 与 请求参数之间的映射关系
 *  即当 控制器方法的形参 与 请求参数 不同名时，通过为 控制器方法的形参 添加该注解，创建映射关系
 *  还可以 将所有的请求过来的数据 全部自动放在map中， 如 @RequestHeader Map<String,String> params,
 *    value属性，用于指定请求参数名
 *    required属性，默认值为true, 表示必须传入 value指定的请求参数值。若没有传入，且没有设置defaultValue属性，则报错
 *                   若设置为false, 表示当没有 value指定的请求参数值，控制器方法的形参显示null值
 *    defaultValue：当 value指定的请求参数值 没有传入时（不论required属性值为true或false，），使用默认值为形参赋值
 *
 *（2）@RequestHeader：用法同@RequestParam
 *  请求头 与 控制器方法的形参没有默认的映射关系，故，若想获得请求头信息，必须使用该注解创建映射关系。如 @RequestHeader(value = "Host", defaultValue = "默认值") String host
 *  还可以 将请求头中所有的数据 全部自动放在map中， 如 @RequestHeader Map<String,String> headers
 *  三个属性与用法和@RequestParam相同
 *
 *（3）@CookieValue：用法同@RequestParam
 *  cookie 与 控制器方法的形参没有默认的映射关系，故，若想获得cookie信息，必须使用该注解创建映射关系
 *  还可以 拿到具体的cookie对象，如 @CookieValue("JSESSIONID") Cookie jsessionID
 *  三个属性与用法和@RequestParam相同
 *
 *（4）@RequestBody：用法同@RequestParam
 *  当请求方式为post时，可以获取提交的请求体信息。如 @RequestBody String content
 *
 *（5）@RequestPart：用法同@RequestParam
 *  设置 控制器方法的形参 与 上传的文件 的映射关系
 *
 * 【获取请求参数乱码问题】springMVC的编码过滤器CharacterEncodingFilter
 *  get请求若出现乱码通常与tomcat设置有关。tomcat8及以上都默认使用utf-8，不会出现乱码问题
 *  post请求乱码问题，可以使用SpringMVC提供的编码过滤器CharacterEncodingFilter
 *   必须在web.xml中进行注册，因为前端控制器DispatcherServlet已经获取参数了，所以使用过滤器（服务器启动时就加载），在前端控制器之前设置编码格式
 *   （✔必须在获取到请求参数之前设置编码格式，否则设置无效，如 使用servlet处理时，必须在BaseServlet中设置编码格式）
 *  SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效
 *  源码解析(了解)：查看 CharacterEncodingFilter 类中的 doFilterInternal 方法，因为 request.getCharacterEncoding() == null 始终满足（并未为其设置编码格式）
 *                  故只要设置了 encoding 为 UTF-8 即可。即设置了请求的编码格式为UTF-8
 *                  若要设置响应的编码格式为UTF-8，则需要设置 forceResponseEncoding 为 true;
--------------------------------------------------
<!--配置springMVC的编码过滤器-->
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceResponseEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
------------------------------------------------
 *
 @author Alex
 @create 2023-03-02-15:20
 */
@Controller
public class UBF04 {
    // 方式一：通过ServletAPI获取请求参数，并且通过创建一个session，由于session的底层是cookie，从而创建一个键值为JSEESIONID的cookie
    // 第一次创建session，服务器会将cookie以响应报文的形式返回给客户端中，之后每次访问，cookie都是以请求报文的形式发送给服务器端
    @RequestMapping("/UBF04")
    public String testParam(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        System.out.println("username:" + username + ",password:" + password);
        return "UBF04";
    }

    // 方式二：通过 控制器方法的形参 获取请求参数(参数、请求头、cookie)
    @RequestMapping("/test1/UBF04")
    public String testParam1(@RequestParam(value = "user_name", defaultValue = "默认值") String username,
                             String password,
                             @RequestHeader(value = "Host", defaultValue = "默认值") String host,
                             @CookieValue(value = "JSESSIONID", defaultValue = "默认值") String cookie) {
        System.out.println("username:" + username + ",password:" + password);
        System.out.println("请求头Host为" + host);
        System.out.println("浏览器中的cookie JSESSIONID：" + cookie);
        return "UBF04";
    }

    // 若请求所传输的请求参数中有多个同名的请求参数时
    @RequestMapping("/test2/UBF04")
    public String testParam2(String username, String password, String hobby) {
        System.out.println("username:" + username + ",password:" + password + ",hobby:" + hobby);
        return "UBF04";
    }

    // 通过pojo获取请求参数
    @RequestMapping("/test3/UBF04")
    public String testParam3(User user){
        System.out.println(user);
        return "UBF04";
    }

}
