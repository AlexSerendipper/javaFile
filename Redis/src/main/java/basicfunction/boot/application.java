package basicfunction.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 @author Alex
 @create 2023-04-01-21:50
 */
@SpringBootApplication
@ComponentScan("basicfunction")
public class application {
    public static void main(String[] args){
        SpringApplication.run(application.class,args);
    }
}
