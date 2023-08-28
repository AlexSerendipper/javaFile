package webflux;

/**
 * 【springboot整合验证码功能】其实是对javaweb中导入jar包方式的一种提升
 *（1）导入kaptcha依赖
--------------
<!--Kaptcha-->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>
--------------
 *（2）编写配置类
--------------
@Configuration
public class KaptchaConfig {
    @Bean
    public Producer kaptchaProducer(){
        // Producer是一个Kaptcha的一个接口，他有一个默认实现类DefaultKaptcha
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        // 向DefaultKaptcha中传入config类修改其配置
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width","100");
        properties.setProperty("kaptcha.image.height","40");
        properties.setProperty("kaptcha.textproducer.font.size","32");
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        // 这里配置的是从哪些字符中生成验证码
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // 配置验证码长度
        properties.setProperty("kaptcha.textproducer.char.length","5");
        // 配置验证码的干扰，防止机器人暴力破解，默认不干扰就挺好
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");

        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
--------------
 *（3）生成随机字符、根据字符生成图片（在controller中，需要自动装配 Producer kaptchaProducer 对象）
--------------
// 生成验证码
String text = kaptchaProducer.createText();
// 生成图片
BufferedImage image = kaptchaProducer.createImage(text);
// 存入session
session.setAttribute("kaptchaText",text);
// 将图片输出给浏览器,指定输出的格式
response.setContentType("image/png");
ServletOutputStream os = response.getOutputStream();
// 使用java自带的图片输出工具，指定输出的图片，类型，以及流
ImageIO.write(image,"png",os);
--------------
 @author Alex
 @create 2023-04-05-16:54
 */
public class UW06 {
}
