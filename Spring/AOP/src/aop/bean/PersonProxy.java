package aop.bean;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 @author Alex
 @create 2023-02-23-21:26
 */

@Component
@Aspect
@Order(1)
public class PersonProxy {
    // 前置通知
    @Before(value = "execution(* aop.bean.User.add(..))")
    public void before() {
        System.out.println("前置通知PersonProxy before......");
    }

}
