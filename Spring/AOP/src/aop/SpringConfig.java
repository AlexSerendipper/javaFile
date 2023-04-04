package aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 @author Alex
 @create 2023-02-23-13:13
 */
@Configuration
@ComponentScan(basePackages = {"aop"})
@EnableAspectJAutoProxy
public class SpringConfig {

}
