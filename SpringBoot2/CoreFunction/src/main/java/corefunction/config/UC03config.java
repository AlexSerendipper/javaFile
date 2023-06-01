package corefunction.config;

import corefunction.pojo.Pet;
import corefunction.pojo.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 @author Alex
 @create 2023-03-18-13:20
 */


@Configuration(proxyBeanMethods = true)
@ConditionalOnBean(name = "UC01")
@ImportResource("classpath:UC01.xml")
public class UC03config {
    @Bean
    public User zzj(){
        User zzj = new User("zzj", 18);
        zzj.setPet(pp());
        return zzj;
    }

    @Bean(value = "hyq")
    public Pet pp(){
        return new Pet("hyq");
    }
}
