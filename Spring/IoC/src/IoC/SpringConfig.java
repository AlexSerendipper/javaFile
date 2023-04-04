package IoC;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 @author Alex
 @create 2023-02-23-13:13
 */
@Configuration
@ComponentScan(basePackages = {"IoC"})
public class SpringConfig {

}
