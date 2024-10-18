package webfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import webfunction.pojo.Person;

/**
 * 【异常处理机制】springboot解决方案与springMVC有差异，但是同样推荐使用springMVC中的 SimpleMappingExceptionResolver 方法
 *  默认情况下，SpringBoot提供 /error 处理所有错误的映射
 *  发生异常时：对于机器客户端(postman)，它将生成 JSON 响应，其中包含错误，HTTP状态和异常消息的详细信息。
 *              对于浏览器客户端，响应一个 "whitelabel" 错误视图，以HTML格式呈现相同的数据
 *  springboot中，异常处理只需要在 /template/error下 新建 4xx，5xx页面，错误页面就会被自动解析；
 *   当有精确的错误页面就精确匹配（如匹配404.html）
 *   如果没有精确的错误页面就找 4xx.html（模糊查找）。如果都没有就触发白页
 *
 * 【异常处理源码解析】有需要在学，见p53-55
 *
 @author Alex
 @create 2023-03-23-12:37
 */
@Controller
public class UW07 {
    // 模仿除零异常
    @GetMapping ("/error1")
    public String error1(){
        System.out.println(10/0);
        return "success";
    }

}
