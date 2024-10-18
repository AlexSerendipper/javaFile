package extendfunction;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 【HttpMessageConverter】
 *  HttpMessageConverter，报文信息转换器，可以实现将请求报文转换为Java对象，或将Java对象转换为响应报文
 *  HttpMessageConverter提供了两个注解和两个类型：@RequestBody，@ResponseBody
 *                                               RequestEntity，ResponseEntity
 *  实际上在SpringMVC中，我们获取请求参数已经非常容易了。故@RequestBody很少使用
 *   实际中更常用@ResponseBody获取响应报文对象
 *
 * 【@RequestBody】获取请求体
 *  在控制器方法设置一个形参，使用@RequestBody进行标识，当前请求的 请求体 就会为当前注解所标识的形参赋值
 *
 * 【RequestEntity】获取请求报文
 *  在控制器方法的形参中设置该类型的形参，当前请求的请求报文就会赋值给该形参，
 *   可以通过getHeaders()获取请求头信息
 *   通过getBody()获取请求体信息
 *
 * 【@ResponseBody】✔✔输出响应报文
 *  ✔对于传统的方法，有两种方法输出相应  1.是通过请求转发和重定向 2.是通过resp.getWriter.write()方法
 *  ✔@ResponseBody 用于标识一个控制器方法，可以将该方法的返回值直接作为响应报文的响应体响应到浏览器
-------------------------------------
<mvc:annotation-driven>
    <mvc:message-converters>
    <!-- 处理响应中文内容乱码 -->
    <bean class="org.springframework.http.converter.StringHttpMessageConverter">
        <property name="defaultCharset" value="UTF-8" />
        <property name="supportedMediaTypes">
            <list>
                <value>text/html</value>
                <value>application/json</value>
            </list>
        </property>
    </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
-------------------------------------

 * 【@RestController】很常用
 *  @RestController注解是springMVC提供的一个复合注解，标识在控制器的类上，就相当于为类添加了@Controller注解，并且为其中的每个方法都添加了@ResponseBody注解
 * 
 * 【ResponseEntity】见UEF03
 *  用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文
 *  常用于文件下载功能
 *    new ResponseEntity<>(bytes, headers,statusCode);       # bytes为响应体（对应被下载文件的字节数组），header对应响应头，statusCode对应相应状态码
 *
 @author Alex
 @create 2023-03-05-18:01
 */
@Controller
public class UEF01 {
    @RequestMapping("/requestBody")
    public String testRequestBody(@RequestBody String requestBody){
        System.out.println("requestBody:"+requestBody);
        return "success";
    }

    @RequestMapping("/requestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity){
        System.out.println("requestHeader:"+requestEntity.getHeaders());
        System.out.println("requestBody:"+requestEntity.getBody());
        return "success";
    }

    // 传统方法响应数据
    @RequestMapping("/response")
    public void testResponse(HttpServletResponse response) throws Exception{
        response.getWriter().write("hello response");
    }

    @RequestMapping("/responseBody")
    @ResponseBody
    public String testResponseBody(){
        return "通过@ResponseBody响应数据";
    }
}
