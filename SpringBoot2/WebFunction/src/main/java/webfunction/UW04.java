package webfunction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 【视图解析 与 模板引擎 的使用】和springMVC并无区别
 * （1）引入thymeleaf视图解析
----------------------
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
----------------------
 * (2) thymeleaf将自动配置
 *  所有 thymeleaf 的配置值都在 ThymeleafProperties 中
 *   配置好了 SpringTemplateEngine 模板引擎
 *   配好了 ThymeleafViewResolver 视图解析器
 *  我们只需要直接开发页面
-----------------------
 * (3) 相关配置
 *  server.servlet.context-path=/zzj              # 配置当前工程的上下文路径
 *
 * 【视图解析源码分析】以后用到再说把 , p47集
 *
 @author Alex
 @create 2023-03-20-20:36
 */
@Controller
public class UW04 {
    @GetMapping("success")
    public String hello(Model model){
        model.addAttribute("msg","zzj666");
        return "success";
    }
}
