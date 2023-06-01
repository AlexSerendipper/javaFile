package webfunction;

/**
 * 【spring整合发送邮件功能】
 * （1）开启邮箱的smtp功能。如qq邮箱在 设置==>账户==>开启smtp
 * （2）导入发送邮件starter
-----------------
<!--Srping Email-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
    <version>2.1.5.RELEASE</version>
</dependency>
-----------------
 *（3）邮箱参数配置application.properties
----------------
spring.mail.host=smtp.sina.com
# 基本上所有邮箱的端口都是465
spring.mail.port=465
# 注意输入的用户名为邮箱名
spring.mail.username=qq642671525@sina.com
# 注意输入的密码为开通pop3/smtp服务时所给的授权码
spring.mail.password=2141e113236a4d0e
# 表示启用安全协议
spring.mail.protocol=smtps
# 采用ssl安全连接的方式发送
spring.mail.properties.mail.smtp.ssl.enable=true
----------------
 * (4) 调用JavaMailSender发送邮件（通常会写成一个util工具类）
----------------
@Component
public class MailClient {
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
    @Autowired
    private JavaMailSender mailSender;
    // 发件人账号，直接关联配置文件
    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(String receiver,String subject,String content) throws Exception {
       // 1、创建mimemessage对象，使用它来实现发送邮件功能
       MimeMessage message = mailSender.createMimeMessage();
       // 2、使用MimeMessageHelper来帮助实现发送邮件功能
       MimeMessageHelper helper = new MimeMessageHelper(message);
       // 3. 设置发件人和收件人
       helper.setFrom(sender);
       helper.setTo(receiver);
       // 4. 设置邮件的主题 和 内容(第二个参数表示支持使用html文本，即支持传入各种标签)
       helper.setSubject(subject);
       helper.setText(content,true);
       // 5. 发送邮件
       mailSender.send(helper.getMimeMessage());
    }
}
----------------
 * (5) 使用thymeleaf发送邮件(测试案例见mailTest)
----------------
// thymeleaf模板引擎的context对象，我们要主动调用模板引擎 去加载页面（传入context对象 即 传入参数）
Context context = new Context();
// 传入参数
context.setVariable("username","zzj");
// 加载 templates/mail.html 到模板引擎中（传入context对象 即 传入参数），返回html格式的内容！
String content = templateEngine.process("/mail", context);
System.out.println(content);
// 发送邮件
mailClient.sendMail("642671525@qq.com","test",content);
----------------
 *
 *
 @author Alex
 @create 2023-04-04-21:42
 */
public class UW09 {
}
