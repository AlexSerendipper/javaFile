package jsp_view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 【SpringMVC对Jsp页面的访问】
 *  由于配置Thymeleaf视图解析器并不支持对jsp页面的访问，若要使用springMVC对jsp页面的访问，需要在springMVC.xml中配置InternalResourceViewResolver
 *  所以说此时只有两种视图，一种是InternalResourceView（转发视图）。即控制器方法中所设置的视图名称 以"forward:"为前缀时
 *                          （此时默认当控制器方法中所设置的视图名称没有任何前缀时，就是InternalResourceView（转发视图））
 *                         一种是RedirectView（重定向视图是）。即控制器方法中所设置的视图名称 以"redirect:"为前缀时，
------------------------------------------
<!-- 配置jsp视图解析器, 此处class别导错了 -->
<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <!-- 视图前缀，此时/代表的就是http:8080/jsp -->
    <property name="prefix" value="/WEB-INF/templates/"></property>
    <!-- 视图后缀 -->
    <property name="suffix" value=".jsp"></property>
</bean>
------------------------------------------
 *
 @author Alex
 @create 2023-03-04-13:48
 */
@Controller
public class UJV01 {
    @RequestMapping("/success")
    public String success(){
        return "success";
    }
}
