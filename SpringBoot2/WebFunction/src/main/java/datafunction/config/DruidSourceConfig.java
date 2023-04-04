package datafunction.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;

/**
 @author Alex
 @create 2023-03-23-15:49
 */

// @Configuration
public class DruidSourceConfig {
    // 底层配置HikariDataSource时，是根据@ConditionalOnMissingBean(DataSource.class)
    // 所以我们为容器中添加组件，就是使用我们自己添加的组件
    // 与配置文件绑定
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setFilters("stat,wall");
        return dataSource;
    }

    // 添加druid的监控页功能
    @Bean
    public ServletRegistrationBean statViewServlet(){
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
        registrationBean.addInitParameter("loginUsername", "admin");
        registrationBean.addInitParameter("loginPassword", "admin");
        return registrationBean;
    }

    // 添加druid的web应用监控
    @Bean
    public FilterRegistrationBean WebStatFilter(){
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<WebStatFilter>(webStatFilter);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return registrationBean;
    }
}
