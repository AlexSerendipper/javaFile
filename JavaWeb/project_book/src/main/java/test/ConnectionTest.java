package test;

import org.junit.Test;
import utils.JdbcUtils;

import java.sql.Connection;

/**
 @author Alex
 @create 2023-02-01-12:58
 */
public class ConnectionTest {
    @Test
    public void test1() throws Exception{
        JdbcUtils jdbcUtils = new JdbcUtils();
        Connection connection = jdbcUtils.getConnection();
        System.out.println(connection);
    }
}
