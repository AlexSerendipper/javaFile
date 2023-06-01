package corefunction;

import org.springframework.web.util.HtmlUtils;

/**
 * 【常用开发工具】
 * 【Lombok】能够简化JavaBean开发
 * （1）引入依赖
----------------
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
----------------
 * （2）使用方式
 *  ✔@NoArgsConstructor                # 重写类中的无参构造器
 *  ✔@AllArgsConstructor               # 重写类中的带参构造器（带全部参数）（如果需要其他的构造器自己配置一下就好啦）
 *  ✔@Data                             # 重写类中的get和set方法和equals和hashcode和toString方法
 *
 *  @ToString                         # 重写类中的toString方法
 *  @EqualsAndHashCode                # 重写类中的equals和hashcode
 *  @Slf4j                            # 可以在类中使用log.info("")输出日志信息
 *
 * 【dev-tools】
 *  项目代码修改以后，按Ctrl+F9 可以将springboot重新启动.....如果没有修改，则不会重新启动
 *  静态页面页面以后，按Ctrl+F9 直接进行热部署，不用重新启动
------------------
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
-------------------
 * 【Spring Initailizr】项目初始化向导
 *  创建模块：创建时选择我们需要的开发场景 和 开发工具
 *  （注意选择springboot版本,如果版本不对可以先创建一个，然后手动降低版本）
 *  （注意创建完项目看一下 <java.version>8</java.version>）
 *  使用Spring Initailizr创建模块，将自动完成 依赖引入、项目结构创建、编写好主配置类
 *
 * 【commons-lands】
 *  StringUtils.isBlank()              # 判断是否为空值（包括null,""等）
 *  CharUtils.isAsciiAlphanumeric(c)   # 判断字符是否为普通字符（普通字符返回true,特殊字符返回false）
 *
 *  HtmlUtils.htmlEscape(json1)        # 将json字符串中的特殊符号进行转义（org.springframework.web.util.HtmlUtils）
 *  HtmlUtils.htmlUnescape(json1)      # 将json字符串中的特殊符号进行反转义（org.springframework.web.util.HtmlUtils）
--------------------
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.9</version>
</dependency>
--------------------
 *
 * 【UUID】用于生成随机字符串
 *  UUID.randomUUID().toString().replaceAll("-","");      # java自带工具，默认生成的字符串有横线，可以全部替换为空字符串
 *
 * 【MD5】加密算法，只能加密不能解密，每次加密都是同样的结果
 *  DigestUtils.md5DigestAsHex(key.getBytes());
 *
 * 【wkhtmltopdf】https://wkhtmltopdf.org/downloads.html 处下载。
 *  该工具无法用于打印百度文库等有权限的网址。
 *  使用该工具，必须得 提前建好文件存放目录！
 *  pc端运行：使用bin目录下的命令，输入 wkhtmltoimage --quality 75 http://www.nowcoder.com d:/data/wk-pdf/2.png                      # 其中--quality 75表示把原图片按照75%压缩保存
 *  java端运行： String cmd = "d:/wkhtmltopdf/bin/wkhtmltoimage --quality 75 http://www.nowcoder.com d:/data/wk-pdf/1.png"         #
 *                Runtime.getRuntime().exec(cmd);
 *
 @author Alex
 @create 2023-03-19-13:03
 */
public class UC06 {

}
