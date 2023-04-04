package webfunction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

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
