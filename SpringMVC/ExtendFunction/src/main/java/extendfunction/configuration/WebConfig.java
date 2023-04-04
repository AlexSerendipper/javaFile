package extendfunction.configuration;

import extendfunction.interceptor.Interceptor;
import extendfunction.interceptor.Interceptor2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Properties;

/**
 @author Alex
 @create 2023-03-09-15:15
 */

@Configuration // 将当前类标识为配置类
// 扫描组件
@ComponentScan("extendfunction")
// 开启MVC注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
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


    // 使用默认的servlet处理静态资源
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    // 配置视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }


    // 配置文件上传解析器
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }

    // 配置拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        Interceptor interceptor = new Interceptor();
//        Interceptor2 interceptor2 = new Interceptor2();
//        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/");
//        registry.addInterceptor(interceptor2).addPathPatterns("/**").excludePathPatterns("/");
//
//    }

    // 配置异常处理映射
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties prop = new Properties();
        prop.setProperty("java.lang.ArithmeticException", "error");
        prop.setProperty("java.lang.ArrayIndexOutOfBoundsException", "error");

        // 设置异常映射
        exceptionResolver.setExceptionMappings(prop);
        // 设置共享异常信息的键
        exceptionResolver.setExceptionAttribute("ex");
        resolvers.add(exceptionResolver);
    }
}