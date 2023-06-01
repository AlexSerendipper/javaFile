package webfunction.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import webfunction.util.MailClient;

/**
 @author Alex
 @create 2023-04-04-21:56
 */

@SpringBootTest
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void test1() throws Exception {
        mailClient.sendMail("642671525@qq.com","test","66666666666");
    }

    @Test
    public void testHtmlMail() throws Exception {
        // thymeleaf模板引擎的context对象，我们要主动调用模板引擎 去加载页面（传入context对象 即 传入参数）
        Context context = new Context();
        // 传入参数
        context.setVariable("username","zzj");
        // 加载 templates/mail.html 到模板引擎中（传入context对象 即 传入参数），返回html格式的内容！
        String content = templateEngine.process("/mail", context);
        System.out.println(content);
        // 发送邮件
        mailClient.sendMail("642671525@qq.com","test",content);
    }

}
