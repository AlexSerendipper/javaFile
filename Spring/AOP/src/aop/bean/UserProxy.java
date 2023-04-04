package aop.bean;

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
    public void before() {
        System.out.println("前置通知UserProxy before......");
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
