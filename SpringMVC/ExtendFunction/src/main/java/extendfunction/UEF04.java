package extendfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 【SpringMVC拦截器】
 *  根据SpringMVC的执行过程
 *   过滤器是在前端控制器dispatchServlet之前的
 *   而SpringMVC拦截器，是作用在前端控制器dispatchServlet之后，负责对控制器方法执行的拦截
 *   原因是 dispatchServlet 满足javaEE的规范，其本质上还是属于servlet，故拦截器可以对其进行拦截！
 *
 * 【拦截器的三个方法】
 *  preHandle：控制器方法执行之前执行preHandle()，
 *              该方法中的Object handler对象，代表的是被拦截的对象
 *              ✔preHandle方法的返回值（boolean类型）表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法
 *  postHandle：控制器方法执行之后执行postHandle()
 *  afterCompletion：处理完视图和模型数据，渲染视图完毕之后执行afterComplation()
 *
 * 【Object handler对象的妙用】
 *  if(handler instanceof HandlerMethod)                     # 判断拦截到的对象是不是一个方法，HandlerMethod是Springmvc提供的对象
 *  HandlerMethod handlerMethod = (HandlerMethod) handler;
 *   Method method = handlerMethod.getMethod();               # 如果是一个方法，我们可以把这个方法抽取出来进行其他操作
 *  LoginRequired loginRequired = method.getAnnotation(xxx.class);            # 例如可以获取该方法的注解信息
 *
 * 【拦截器的使用流程】
 * （1）创建的拦截器需要实现HandlerInterceptor接口, 使用alt+shift+p，重写其接口中的方法
 * （2）SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置
 *     （访问首页时，通过视图解析器实际上也是通过一个控制器，所以也会被拦截）
---------------------------------
<!--配置拦截器方式1，内部引用。所有请求都会被拦截-->
<mvc:interceptors>
    <bean class="extendfunction.interceptor.UEF04"></bean>
</mvc:interceptors>

<!--配置拦截器方式2，外部引用（扫描）:为拦截器添加@component。 所有请求都会被拦截-->
<mvc:interceptors>
    <ref bean="UEF04"></ref>
</mvc:interceptors>

<!--配置拦截器方式3，✔✔推荐使用，可以设计当前拦截规则-->
<mvc:interceptors>
    <mvc:interceptor>
        <!--设置拦截地址, 与filter不同，/*仅表示拦截任意一层目录，/**表示拦截所有 -->
        <mvc:mapping path="/*"/>
        <!--设置不拦截地址 -->
        <mvc:exclude-mapping path="/"/>
        <ref bean="UEF04"></ref>
    </mvc:interceptor>
</mvc:interceptors>
---------------------------------
 *
 * 【多个拦截器的执行顺序】了解，只与拦截器在springMVC中的配置顺序有关
 *  若每个拦截器的preHandle()都返回true
 *    preHandle()会按照配置的顺序执行，
 *    postHandle()和afterComplation()会按照配置的反序执行
 *  若某个拦截器的preHandle()返回了false
 *    返回false的拦截器 以及 其之前的拦截器 的preHandle()都会执行
 *    postHandle()都不执行
 *    返回false的拦截器之前的拦截器 的afterComplation()会执行
 *
 * 【多个拦截器的执行顺序】源码分析：了解一下，
 * （1）若每个拦截器的preHandle()都返回true（我们的程序设置interceptor和interceptor2返回true）
 * 531：mappedHandler.applyPreHandle()        # preHandle执行，可以看到方法中是按照interceptorList的i++执行,所以prehandle是按照配置顺序顺序输出，并且interceptorIndex最终返回2
 * ↓                                            (方法中interceptorList就是指的是拦截器列表, 因为有一个系统默认的拦截器，故在我们的程序中共有三个)
 * 535：mv = ha.handle();                     # 返回model and vie
 * ↓
 * 541：mappedHandler.applyPostHandle();      # afterHandle执行，可以看到方法中是按照interceptorList的i--执行，所以afterhandle是按照配置顺序倒叙输出
 * ↓
 * 548: this.processDispatchResult()          # 后续处理，进入！
 * ↓
 * 591：this.render(mv, request, response);   # 渲染视图
 * ↓
 * 601：mappedHandler.triggerAfterCompletion();       # afterCompletion执行, 可以看到方法中，是按照interceptorIndex的i--执行，所以afterCompletion是按照是按照配置顺序倒叙输出
 *
 * （2）若某个拦截器的preHandle()都返回false(我们的程序中设置interceptor2返回false)
 * 531：mappedHandler.applyPreHandle()                # preHandle执行，可以看到方法中是按照interceptorList的i++执行, 当i=2，interceptorIndex=1时，直接进入循环，执行afterCompletion执行。故此时输出了两个prehandle
 * ↓
 * 601：mappedHandler.triggerAfterCompletion();       # afterCompletion执行, 可以看到方法中，是按照interceptorIndex的i--执行，所以afterCompletion只输出了一个，而posthandle没有被执行
 @author Alex
 @create 2023-03-07-19:49
 */

@Controller
public class UEF04 {
    @RequestMapping("/**/interceptor")
    public String testInterceptor(){
        return "success";
    }

}
