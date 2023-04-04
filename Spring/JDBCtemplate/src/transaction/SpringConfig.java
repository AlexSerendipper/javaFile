package transaction;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;

/**
 @author Alex
 @create 2023-02-23-13:13
 */
@Configuration
@ComponentScan(basePackages = {"transaction"})
//@EnableTransactionManagement
public class SpringConfig {
    // 创建数据库连接池,jbdc部分详细讲过
    @Bean
    public DataSource getDruidDataSource() throws Exception{
        // 读取配置文件的信息
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("D:\\IdeaWorkspace\\Spring\\JDBCtemplate\\src\\druid2.properties");  // 自定义配置文件，main方法中默认在workspace下
        properties.load(fis);  // 加载对应的流文件
        // 创建一个druid数据库连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        return dataSource;

//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/user_db?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true");
//        dataSource.setUsername("root");
//        dataSource.setPassword("qqabcd");
//        return dataSource;
    }

    //创建 JdbcTemplate 对象
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) throws Exception {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // 此处可以通过getDruidDataSource()方法注入dataSource对象，但是这种方式实际上会创建两个dataSource对象
        // jdbcTemplate.setDataSource(getDruidDataSource());
        // 实际上，有了getDruidDataSource()方法后，在ioc容器中，就存有他创建的dataSource对象
        // 所以，可以直接注入dataSource（自动到IOC容器中寻找匹配的dataSource ,类似于autowired）
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    //创建事务管理器
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
