package webfunction;


/**
 * 【web开发场景，常用功能源码分析】
 *  查看 WebMvcAutoConfiguration.class 。可以看到其中配置了一些常用组件如 hiddenHttpMethodFilter 等
 *  主要查看 public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer {} 该内部配置类
 *   发现配置文件的相关属性进行了绑定。WebMvcProperties==spring.mvc、ResourceProperties==spring.resources
 *   ✔又发现这个配置类只有一个有参构造器，当一个配置类只有一个有参构造器，意味着，有参构造器所有参数的值都会从容器中确定
 *    ResourceProperties resourceProperties；    # 获取和spring.resources绑定的所有的值的对象
 *    WebMvcProperties mvcProperties             # 获取和spring.mvc绑定的所有的值的对象
 *    ListableBeanFactory beanFactory            # Spring的beanFactory
 *    HttpMessageConverters                      # 找到所有的HttpMessageConverters
 *    ResourceHandlerRegistrationCustomizer      # 找到 资源处理器的自定义器✔
 *    DispatcherServletPath
 *    ServletRegistrationBean                    # 给应用注册Servlet、Filter....
 *
 * 【静态资源管理源码分析】即ResourceHandlerRegistrationCustomizer模块， 主要分析下列方法
 * (1) 静态资源访问
---------------------------------
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 按照前述分析 ResourceProperties==spring.resources
    // 意味着当我们设置 spring.resources.add-mappings=false相当于禁用了访问静态资源的功能
    if (!this.resourceProperties.isAddMappings()) {
       logger.debug("Default resource handling disabled");
    } else {
        // 意外着可以通过设置spring.resources.cache.period=，来设置静态资源缓存的存在时间
        Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
        CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
        // 注册 /webjars/** 的映射规则，明显看到映射的是 classpath:/META-INF/resources/webjars/
        if (!registry.hasMappingForPattern("/webjars/**")) {
            this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
    }
    // 普通静态资源映射规则注册，拿到spring.mvc.static-path-pattern值，默认值为 /**
    String staticPathPattern = this.mvcProperties.getStaticPathPattern();
    // 是将spring.mvc.static-path-pattern的值 映射到 this.resourceProperties.getStaticLocations()
    // 即映射的位置通过spring.resources.static-locations=设置，默认值为之前提及的4个
    if (!registry.hasMappingForPattern(staticPathPattern)) {
        this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(WebMvcAutoConfiguration.getResourceLocations(this.resourceProperties.getStaticLocations())).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
    }
    }
 }
---------------------------------
 *（2）欢迎页支持
---------------------------------
// 从public WelcomePageHandlerMapping welcomePageHandlerMapping（）方法 进入到 new WelcomePageHandlerMapping()中
WelcomePageHandlerMapping(TemplateAvailabilityProviders templateAvailabilityProviders, ApplicationContext applicationContext, Optional<Resource> welcomePage, String staticPathPattern) {
    // 当欢迎页存在，且映射路径为/**，则跳转到欢迎页
    if (welcomePage.isPresent() && "/**".equals(staticPathPattern)) {
        logger.info("Adding welcome page: " + welcomePage.get());
        this.setRootViewName("forward:index.html");
    // 否则，寻找相应的Controller
    } else if (this.welcomeTemplateExists(templateAvailabilityProviders, applicationContext)) {
        logger.info("Adding welcome page template: index");
        this.setRootViewName("index");
    }
}
---------------------------------
 *
 *
 *
 * 【请求处理源码分析】
 * （1）REST风格处理，自动配置HiddenHttpMethodFilte (仅用于客户端表单发送请求，如PostMan直接发送Put、delete等方式请求，无需Filter)
---------------------------------
@Bean
@ConditionalOnMissingBean({HiddenHttpMethodFilter.class})
@ConditionalOnProperty( prefix = "spring.mvc.hiddenmethod.filter",
                        name = {"enabled"},
                        // ✔✔✔✔✔✔意味着该功能默认关闭，需要手动开启。spring.mvc.hiddenmethod.filter.enabled=true
                        matchIfMissing = false )
public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new OrderedHiddenHttpMethodFilter();
}
---------------------------------
 * （2）请求映射原理
 * --HttpServlet                     # 其中的doget/dopost方法是一定要被重写的
 *   --HttpServletBean
 *     --FrameworkServlet            # 在该类中重写了doget/dopost方法，并调用了processRequest方法（其中调用了doservice方法）
 *       --DispatcherServlet         # 实现了doservice方法，在其中调用dodispatch
 *  故请求映射原理分析，从doDispatch开始~
-----------------------------
// 查看doDispatch，通过getHandler()方法 找到处理当前请求的handler(controller)
    mappedHandler = this.getHandler(processedRequest);
// 进入该方法，方法中的handlerMappings，实际上存储了当前项目中的所有请求映射（包括用户自定义的handlerMapping以及欢迎页的handlerMapping），
// 遍历后找到处理当前请求的handler
// 当需要一些自定义的映射处理，我们也可以自定义 HandlerMapping
-----------------------------
 * （3）获取请求参数原理，呈上，具体原理分析以后在学把 p32集
-----------------------------
// 查看doDispatch，通过getHandler()方法 找到处理当前请求的handler(controller)后，返回 HandlerAdapter ha 适配器对象，
    mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
-----------------------------
 * （4）如何实现ServletAPI的自动装配。具体原理分析以后在学把 p33集
 *  如：WebRequest、ServletRequest、MultipartRequest、 HttpSession、javax.servlet.http.PushBuilder、Principal、InputStream、Reader、HttpMethod、Locale、TimeZone、ZoneId
 * （5）其他参数的自动装配。具体原理分析以后在学把 p34集
 *  如：Map、Model（里面的数据会被放在request的请求域）、Errors/BindingResult、RedirectAttributes（重定向携带数据）、ServletResponse、SessionStatus、UriComponentsBuilder、ServletUriComponentsBuilder
 * （6）为何能通过POJO获取请求参数。具体原理分析以后在学把 p35集
 * （7）为何能响应json数据。具体原理分析以后在学把 p37/p38集
 * （8）内容协商功能：根据浏览器的处理能力的不同，返回特定类型的数据，p39/p40/p41集
 *  要返回xml格式数据，前提是需要导入jackson-dataformat-xml依赖
 *  通过postman，设置请求头中的accept，即表示当前客户端的处理能力。设置为：application/xml，则请求后返回xml数据。设置为application/json，则请求后返回json数据。这就是内容协商功能
 *  若要返回自定义类型的数据，可以自定义 MessageConverter 组件
 *
 *
 @author Alex
 @create 2023-03-20-10:50
 */
public class UW02 {

}
