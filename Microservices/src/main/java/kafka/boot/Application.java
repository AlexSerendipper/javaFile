package kafka.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 @author Alex
 @create 2023-04-14-13:04
 */
@SpringBootApplication(scanBasePackages="kafka",exclude = {DataSourceAutoConfiguration.class})

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
