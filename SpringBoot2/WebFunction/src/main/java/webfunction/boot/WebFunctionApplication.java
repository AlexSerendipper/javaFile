package webfunction.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan(basePackages = "webfunction/webcomponent")
@SpringBootApplication(scanBasePackages = "webfunction")
public class WebFunctionApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFunctionApplication.class, args);
    }

}
