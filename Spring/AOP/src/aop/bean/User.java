package aop.bean;

import org.springframework.stereotype.Component;

/**
 @author Alex
 @create 2023-02-23-15:40
 */

// 被增强类
@Component
public class User {
    public void add() {
        System.out.println("add.......");
    }
}