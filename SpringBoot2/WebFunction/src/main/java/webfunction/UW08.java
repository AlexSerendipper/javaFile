package webfunction;

/**
 * 【web原生组件注入】Servlet、Filter、Listener (创建web原生组件的做法见javaWeb)，见webcomponent
 * 【方式一：使用注解注入（推荐使用）】
 *  @ServletComponentScan(basePackages = "")           # 在主程序类上添加该注解，指定扫描原生web组件的位置
 *  @WebServlet(urlPatterns = "/servlet")              # 在创建的原生servlet上添加该注解，urlPatterns指定访问路径
 *                                                        使用原生的servlet访问，不会经过Spring的拦截器。原因是当有多个servlet对路径进行处理，优先处理路径更精确的servlet
 *                                                        例如此处dispatcherServlet处理 "/"，而原生servlet处理 "/servlet"。优先处理 "/servlet"
 *                                                        又因为拦截器是负责对控制器方法执行的拦截，使用原生servlet处理被没有经过控制器方法，故不会触发控制器！
 *  @WebFilter(urlPatterns={"/aaa.png"})               # 在创建的原生filter上添加该注解，urlPatterns指定拦截路径。如此处设置为访问静态资源aaa.png时触发
 *  @WebListener                                       # 在创建的原生listener添加该注解
 *
 * 【方式二：使用配置类注入】
 *  即在原生web组件的包下创建配置类，将原生web组件配置到容器中
 *  注意这里使用配置类时，要保证单实例，避免组件冗余。 @Configuration(proxyBeanMethods = true)
------------------------------------
@Configuration
public class MyRegistConfig {
    @Bean
    public ServletRegistrationBean myServlet(){
        UW08_Servlet myServlet = new UW08_Servlet();
        return new ServletRegistrationBean(myServlet,"/servlet");  // 传入urlPattern
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        UW08_Filter myFilter = new UW08_Filter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/aaa.png"));
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        UW08_Listener myListener = new UW08_Listener();
        return new ServletListenerRegistrationBean(myListener);
    }
}
------------------------------------
 *
 @author Alex
 @create 2023-03-23-13:04
 */

public class UW08 {
}
