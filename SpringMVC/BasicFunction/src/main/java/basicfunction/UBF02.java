package basicfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 【springMVC.xml配置】配置Thymeleaf视图解析器
 ----------------------------------------
 <!-- 配置Thymeleaf视图解析器,用于转发页面，替代jsp -->
 <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
     <!-- 设置视图解析器的优先级 -->
     <property name="order" value="1"/>
     <property name="characterEncoding" value="UTF-8"/>
     <property name="templateEngine">
     <!--使用内部bean赋值-->
     <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
         <property name="templateResolver">
             <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                 <!-- 视图前缀，这里的/还是表示http://8080/basicfunction -->
                 <property name="prefix" value="/WEB-INF/templates/"/>
                 <!-- 视图后缀 -->
                 <property name="suffix" value=".html"/>
                 <property name="templateMode" value="HTML5"/>
                 <property name="characterEncoding" value="UTF-8" />
             </bean>
         </property>
         </bean>
     </property>
 </bean>
 ------------------------------------------
 * 【SpringMVC使用流程】
 * （1）配置视图解析器
 * （2）创建请求 控制器
 *   传统的方式，是创建不同servlet类处理不同的请求
 *   在Spring MVC中，由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器
 *   请求控制器类需要交给Spring的IoC容器管理（@controller），此时SpringMVC才能够识别控制器的存在
 *   在请求控制器中创建处理请求的方法，使用@RequestMapping注解：处理请求和控制器方法之间的映射关系
 *      @RequestMapping(value = "/")注解。value属性可以根据配置的请求地址 匹配 不同的请求，/表示的当前工程的上下文路径（当前工程在web中的路径，浏览器输入的地址）
 * （3）创建页面，需要添加th名称空间于html页面中 xmlns:th="http://www.thymeleaf.org,才能使用th语法
 *
 *  【执行过程】非常重要✔✔✔✔✔
 *  首先请求地址若满足前端控制器设置的<url-pattern>（我们设置/，表示除了.jsp的请求都会被处理），该请求就会被DispatcherServlet处理
 *  前端控制器会读取 SpringMVC 的核心配置文件，通过扫描组件寻找 具体的控制器：
 *          当浏览器输入@RequestMapping注解中配置的路径（即发起get请求）
 *          或超链接中利用Thymeleaf语法进行设置，如 <a th:href="@{/target}">（表示用Thymeleaf解析发起get请求）
 *  则匹配成功，请求到具体的请求控制器。返回视图名称（逻辑视图）。根据配置的Thymeleaf视图解析器，访问 视图前缀+逻辑视图+视图前缀后缀 的资源（请求转发✔）
 *
 * 【Thymeleaf基本语法总结】见RESTful-template-employee_list
 * （0）thymeleaf语法检查时经常报错，但其实并不影响使用，可以在setting-editor-inspection-thymeleaf-expression variables validation(×)
 * （1）创建页面：需要添加th名称空间于html页面中 xmlns:th="http://www.thymeleaf.org,才能使用th语法。
 * （2）发起请求：在链接标签前添加th:, 并将链接用 @{} 包裹，表示该链接由Thymeleaf解析发起get请求。
 *               超链接拼接写法1："@{/employee/} + ${employee.id}"
 *               超链接拼接写法2："@{'/employee/' + ${employee.id}}"
 * （3）共享域数据（数据回显）：对于 文本标签（需要显示文本的标签, 如a标签、p标签等）, 使用 th:text="${}" 的方式，将需要回显的文本内容用 ${} 包裹 (不同于EL表达式可以直接写在标签中）。
 *                                                                                 th:utext="${}" 的方式，普通text不会翻译特殊字符（如<会翻译成&lt） utext会自动翻译特殊字符
 *                              对于 request域中的数据，使用th:value="${param.username}"，相当于 req.getParameter()
 *                              对于 input标签，使用 th:value="${}" 的方式。见RESTful-template-employee_update
 *                              对于 单选框标签，使用 th:filed="${}" 的方式。若单选框的value值和回显内容的值一致，则自动添加checked="checked"属性！
 * （4）公共页抽取：类似于jsp:include，用于引入公共页面。如：首先将公共部分放在公共页面 /template/commons/footer.html中（不同于jsp的是，可以把引用css、js和html的内容分别放在header和body标签中）
 *                                                      然后在各公共部分使用fragment什么声明，th:fragment="copy"
 *                                                      在需要引入的地方添加，th:insert="footer :: copy"，即引入footer页面下的copy片段（插入，不会包含公共部分的父标签）
 *                                                      在需要引入的地方添加，th:replace="footer :: copy"，即引入footer页面下的copy片段（替换，会包含公共部分的父标签）
 * （5）判断语法，如： th:if="${page.hasPreviousPage}" ，将需要判断的条件使用${}包裹，如果条件判断结果为true，那么标签将显示，否则标签不显示
 * （6）循环语法，如： th:each="employee,status : ${employeeList}" ，将被循环的存储在域中的数据使用${}包裹，status表示被遍历的状态（存储着当前遍历的次数和索引等信息）
 * （7）循环语法2，如：<div th:switch="${user.role}">
 *                          <p th:case="'admin'">User is an administrator</p>
 *  (7) 数据复用，首先，为模板标签取名，如为index.html的头部取名，th:fragment="header" ，
 *                然后，在其他html文件中复用，如 th:replace="index::header"
 *  (7) 动态样式：如 th:class="page-item active" ,当前页码是否高亮取决于activa样式，我们可以用||包裹静态样式，并使用${}加上动态样式
 *                th:class="|page-item ${pageNo==pageInfo.pageNum?'active':''}|
 *
 *  (7) 时间格式化，thymeleaf内置时间格式化工具，可以直接引用使用，如：th:text="${#dates.format(时间信息,'yyyy-MM-dd  HH:mm:ss')}"
 *      对于java8的事件格式数据，如localdatetime,需要额外导入thymeleaf-extras-java8time包。语法如 th:text="${#temporals.format(时间信息,'yyyy-MM-dd  HH:mm:ss')}"
 *      
 @author Alex
 @create 2023-03-01-15:30
 */


// 实现首页访问/WEB-INF/templates/abc.html
@Controller
public class UBF02 {
    @RequestMapping("/")
    public String index() {
    //设置视图名称
        return "index";
    }

    @RequestMapping("/UBF02")
    public String helloSpringMVC(){
        return "UBF02";
    }
}
