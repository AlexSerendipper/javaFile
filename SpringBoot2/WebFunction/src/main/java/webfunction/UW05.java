package webfunction;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.Handler;

/**
 * 【拦截器的使用】与springMVC并无区别
 *  web-starter中已经配置好了文件上拦截器相关配置
 * （1）步骤一：创建拦截器，创建的拦截器需要实现HandlerInterceptor接口, 使用alt+shift+p，重写其接口中的方法
 * （1）步骤二：装配拦截器，需要在配置类中配置拦截器（把拦截器添加到容器中，并指定拦截规则）
---------------------
// 添加拦截器。指定拦截规则。如果是拦截所有/**，静态资源也会被拦截
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new UW05())
    .addPathPatterns("/success")  // 拦截路径，若拦截所有，静态资源也会被拦截
    .excludePathPatterns("/","/css/**","/js/**");  // 放行路径，不能拦截静态资源
}
---------------------
 * 【拦截器工作原理】其实讲springMVC的时候讲过，p49
 *
 @author Alex
 @create 2023-03-23-10:24
 */
public class UW05 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("登录成功啦~");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
