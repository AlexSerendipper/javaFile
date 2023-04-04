package datafunction.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "datafunction")
@MapperScan(value = "datafunction")
public class DataFunctionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataFunctionApplication.class, args);
    }

}
