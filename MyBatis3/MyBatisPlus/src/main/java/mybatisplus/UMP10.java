package mybatisplus;

import mybatisplus.service.ProductService;
import mybatisplus.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 【多数据源】
 *  某些时候，我们会将不同的表放置在不同的数据库中，如以下场景：纯粹多库、 读写分离、一主多从、 混合模式等
 *
 * 【使用流程】
 * （1）引入相关依赖
-----------------
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
    <version>3.5.0</version>
</dependency>
-----------------
 * （2）在配置文件applicaiton.yml中配置数据源（注意缩进，很容易错）
-----------------
spring:
    # 配置数据源信息
    datasource:
        dynamic:
            # 设置默认的数据源或者数据源组, 默认值即为master
            primary: master
            # 是否严格匹配数据源, 默认设置为false，表示未指定数据源则使用默认数据源。 true表示严格匹配，表示未指定数据源则时抛异常
            strict: false
            datasource:
                master:
                    url: jdbc:mysql://localhost:3306/javastudy?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true
                    driver-class-name: com.mysql.cj.jdbc.Driver
                    username: root
                    password: 123456
                slave_1:
                    url: jdbc:mysql://localhost:3306/zzjbook?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true
                    driver-class-name: com.mysql.cj.jdbc.Driver
                    username: root
                    password: 123456
-----------------
 * （3）创建service
 *  @DS("master")      # 对不同的ServiceImpl使用该注解，指定使用的数据源
 *                      # 实际上，@DS可以注解在方法或类上，注解在类上表明该类所有方法都使用该数据源
 *                                                       注解在方法上表明仅该方法使用该数据源
 *                                                       同时存在遵循就近原则，方法注解优先于类注解
 *
 @author Alex
 @create 2023-03-28-13:56
 */
public class UMP10 {
    // 模拟从javastudy数据库和zzjbook数据库中分别取出 mybatisplus_user 和 mybatisplus_product2表 中的数据
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Test
    public void testDynamicDataSource(){
        System.out.println(userService.getById(1L));
        System.out.println(productService.getById(1L));
    }
}
