package extendfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 【SpringMVC异常处理器】
 *  ✔我们希望对异常进行统一的处理，所以当异常出现在数据层和业务层时，我们都将其向上抛到表现层，在表现层进行统一的异常处理✔
 *  SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver。该接口的实现类有：
 *    DefaultHandlerExceptionResolver，默认处理器，springmvc编写，针对会遇到的异常，已经被springmvc处理（如显示404，405）
 *    SimpleMappingExceptionResolver✔，自定义处理期，自己编写，可以实现针对指定的异常，跳转到指定的页面
 *
 * 【SimpleMappingExceptionResolver】SpringMVC.xml配置实现
-----------------------------------
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
        <!--
        properties的键表示处理器方法执行过程中出现的异常的全类名
        properties的值表示若出现指定异常时，设置一个新的视图名称，跳转到指定页面
        -->
        <prop key="java.lang.ArithmeticException">error</prop>
        </props>
    </property>
    <!--exceptionAttribute的属性值(ex)，即为异常信息的键，能够将异常信息在存储在请求域中，方便共享-->
    <property name="exceptionAttribute" value="ex"></property>
</bean>
-----------------------------------

 * 【SimpleMappingExceptionResolver】注解实现
 *    @ControllerAdvice(annotations = Controller.class)               # 将当前类标识为异常处理的组件, 只扫描带有controller注解的组件，出现异常时捕获
 *     (1) @ExceptionHandler(value = {ArithmeticException.class}), 用于修饰方法，该方法会在controller出现异常后被调用，用于处理捕获到的异常
 *     (2) @ModelAttribute, 不常用，用于修饰方法，该方法会在controller方法执行前被调用，用于为model对象绑定参数
 *     (3) @DataBinder,不常用，用于修饰方法，该方法会在controller方法执行前被调用，用于绑定参数的转换器
-------------------
@ControllerAdvice
public class ExceptionController {
    // @ExceptionHandler用于设置所标识方法处理的异常
    @ExceptionHandler(value = {ArithmeticException.class, ArrayIndexOutOfBoundsException.class})
    // ex表示当前请求处理中出现的异常对象
    public String handleException(Exception ex, Model model){
        // 将异常存储在请求域中(概述)
        model.addAttribute("ex", ex);
        // 记录错误的详细信息
        for(StackTraceElement element : ex.getStackTrace()){
            model.addAttribute("eex", element.toString());
        }
        return "error";
    }
}
-------------------
 *
 * 【SimpleMappingExceptionResolver】配置类实现
 * 见WebConfig
 *
 @author Alex
 @create 2023-03-09-13:49
 */
@Controller
public class UEF05 {
    @RequestMapping("/error")
    public String testError() {
        System.out.println(10/0);
        return "success";
    }

    @RequestMapping("/error2")
    public String testError2() {
        int[] array = {1,2,3};
        System.out.println(array[6]);
        return "success";
    }
}
