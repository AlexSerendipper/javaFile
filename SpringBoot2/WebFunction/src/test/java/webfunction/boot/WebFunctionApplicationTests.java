package webfunction.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import webfunction.util.MailClient;

import java.time.temporal.Temporal;

@SpringBootTest
class WebFunctionApplicationTests {
    @Autowired
    static JdbcTemplate jdbcTemplate;


    @Test
    void contextLoads() {
        Long i = jdbcTemplate.queryForObject("select count(*) from springboot_emp", Long.class);
        System.out.println(i);
    }


}
