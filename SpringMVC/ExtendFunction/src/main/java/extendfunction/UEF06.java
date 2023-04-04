package extendfunction;

/**
 * 【SpringMVC全注解开发】
 *  使用配置类和注解代替web.xml和SpringMVC配置文件的功能
 *
 * 【一、创建初始化类，代替web.xml】见WebInit
 *  初始化类必须继承 AbstractAnnotationConfigDispatcherServletInitializer 类
 *   这样，容器会在类路径中查找实现初始化类，并用它来配置Servlet容器。
 *  指定spring5的配置类
 *  指定SpringMVC的配置类
 *  指定DispatcherServlet的映射规则
 *  添加过滤器
 *
 * 【二、创建SpringConfig配置类，代替Spring的配置文件】见SpringConfig
 *  ssm整合之后，spring的配置信息写在此类中
 *
 * 【三、创建WebConfig配置类，代替SpringMVC的配置文件】见WebConfig。原xml文件中用bean配置，在配置类中也可以用@bean配置
 *                                                              原xml文件中不是bean配置，在配置类中需要实现 WebMvcConfigurer 接口, 该接口提供了实现
 * （1） 扫描组件
 * （2） 配置Thymeleaf
-------------------------
// bean注解的返回值就是ioc容器中的一个bean
// 配置Thymeleaf：1、配置生成模板解析器（xml配置中的最内层bean）
@Bean
public ITemplateResolver templateResolver() {
    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
    // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过 WebApplicationContext 的方法获得
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(webApplicationContext.getServletContext());
    templateResolver.setPrefix("/WEB-INF/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    return templateResolver;
}

// 配置Thymeleaf：2、生成模板引擎并为模板引擎注入模板解析器（xml配置中的中间层bean）
// 可以看到这里参数输入的是ITemplateResolver类型，spring会根据ioc中的bean自动完成注入（按照属性）
@Bean
public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    return templateEngine;
}

// 配置Thymeleaf：3、生成视图解析器并未解析器注入模板引擎（xml配置中的最外层层bean）
@Bean
public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setCharacterEncoding("UTF-8");
    viewResolver.setTemplateEngine(templateEngine);
    return viewResolver;
}
-----------------------------------------------
 * （3） 配置视图解析器
 * （4） 配置default-servlet-handler
 * （5） 开启mvc注解驱动（还不知道如何配置响应乱码问题）
 * （6） 配置文件上传解析器
 * （7） 配置拦截器
 * （8） 配置异常处理
 *
 @author Alex
 @create 2023-03-09-15:03
 */
public class UEF06 {
}
