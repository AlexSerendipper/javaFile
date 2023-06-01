package aop.bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 @author Alex
 @create 2023-02-23-15:44
 */

//增强类
@Component
@Aspect  // 生成代理对象
public class UserProxy {
    @Pointcut(value = "execution(* aop.bean.User.add(..))")
    public void pointdemo() {

    }

    // 前置通知
    @Before(value = "execution(* aop.bean.User.add(..))")
    public void before(JoinPoint joinPoint) {
        // 除环绕通知外，其他通知也可以声明JoinPoint对象，代表当前执行的方法！！！并可以通过如下方法获取其类路径和方法名
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        System.out.println("前置通知UserProxy before......");
        System.out.println(method + "被执行了");
    }

    // 后置通知（返回通知）
    @AfterReturning(value = "execution(* aop.bean.User.add(..))")
    public void afterReturning() {
        System.out.println("后置通知AfterReturning.........");
    }

    // 最终通知
    @After(value = "pointdemo()")
    public void after() {
        System.out.println("最终通知After.........");
    }

    // 异常通知
    @AfterThrowing(value = "pointdemo())")
    public void afterThrowing() {
        System.out.println("异常通知afterThrowing.........");
    }


    // 环绕通知
    @Around(value = "pointdemo()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前Around.........");
        // 执行被增强的方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕之后Around.........");
    }

}
