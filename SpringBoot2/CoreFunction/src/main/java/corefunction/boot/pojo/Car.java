package corefunction.boot.pojo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 @author Alex
 @create 2023-03-18-22:03
 */

@Component
@ConfigurationProperties(prefix = "mycar")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {
    private String brand;
    private Integer price;

}
