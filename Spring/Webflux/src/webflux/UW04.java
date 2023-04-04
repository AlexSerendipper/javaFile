package webflux;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webflux.service.UserService;

/**
 *  整合junit5: (暂时不考虑，junit5相对于junit4的提升)
 *   (1) 引入测试相关依赖spring-test-5.2.6.RELEASE.jar
 *                      junit-5.8.1.jar
 *                      若已经导入过junit4，可以再project structure-module中删除后，再自动导入junit5
 *   (2) 创建测试类，使用注解方式完成
 *       @ExtendWith(SpringExtension.class)                    # 使用单元测试框架JUnit5，让测试运行于spring环境
 *       @ContextConfiguration("classpath:UW03.xml")           # 加载配置文件，两个标签一般配合使用
 *       @SpringJUnitConfig(locations = "classpath:UW03.xml")  # ✔使用一个复合注解替代上面两个注解
 *
 @author Alex
 @create 2023-02-28-13:50
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:UW03.xml")
public class UW04 {
    @Autowired
    private UserService userService;
    @Test
    public void test(){
        userService.accountMoney();
    }
}
