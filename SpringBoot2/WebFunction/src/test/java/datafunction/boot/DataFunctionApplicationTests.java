package datafunction.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootTest
class DataFunctionApplicationTests {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
        Long i = jdbcTemplate.queryForObject("select count(*) from springboot_emp", Long.class);
        System.out.println(dataSource.getClass());
        System.out.println(i);
    }

}
