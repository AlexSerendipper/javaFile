package extendfunction;

import extendfunction.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 【SpringMVC处理json】当我们需要响应一个对象给客户端时
 * (1) 导入 jackson 的依赖
---------------
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.1</version>
</dependency>
---------------
 * (2) 在SpringMVC的核心配置文件中开启mvc的注解驱动，<mvc:annotation-driven />
 *     此时在HandlerAdaptor中会自动装配一个消息转换器：MappingJackson2HttpMessageConverter，
 *     可以将响应到浏览器的Java对象转换为Json格式的字符串
 * (3) 在处理器方法上使用@ResponseBody注解进行标识
 *     将Java对象直接作为控制器方法的返回值返回，将会自动转换为Json格式的字符串✔
 *
 * 【SpringMVC处理ajax请求】
 *  前端正常使用ajax发送数据，后端正常接收即可
 *
 @author Alex
 @create 2023-03-06-21:40
 */
@Controller
public class UEF02 {
    @RequestMapping("/json")
    @ResponseBody
    public User testResponseUser(){
        return new User(1001,"admin","123456",23,"男");
    }

    @RequestMapping("/ajax")
    @ResponseBody
    public String testAjax(String username, String password){
        System.out.println("username:"+username+",password:"+password);
        return "hello,ajax";
    }
}

