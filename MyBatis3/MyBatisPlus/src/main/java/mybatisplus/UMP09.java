package mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.Collections;

/**
 * 【代码生成器】mybatis的逆向工程也是一个代码生成器。
 *             但在mybatisplus中，代码生成器生成的东西更多（包括controller、mapper、pojo、service等~）。
 * （1）添加相关依赖
---------------
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.5.1</version>
</dependency>
<dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
    <version>2.3.31</version>
</dependency>
---------------
 * （2）创建测试类，执行以下代码即可
---------------
@Test
public void test1(){
    // 配置数据库✔
    FastAutoGenerator.create("jdbc:mysql://localhost:3306/javastudy?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true", "root", "qqabcd")
                    // 全局配置
                    .globalConfig(builder -> {
                        builder.author("zzj")  // 设置作者
                            //.enableSwagger()  // 开启 swagger 模式
                            .fileOverride()  // 覆盖已生成文件✔
                            .outputDir("D://桌面存放");  // 指定输出目录（具体路径，在该路径下创建包）✔
                    })
                    // 设置包路径
                    .packageConfig(builder -> {
                        builder.parent("zzj")  // 设置父包名✔
                            .moduleName("mybatisplus")  // 设置父包模块名，即最后生成的（service/pojo/controller/mapper都在 全局配置路径+父包+模块名下, 即D://桌面存放/zzj/mybatisplus）✔
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://桌面存放/zzj"));  // 设置mapperXml生成路径（具体路径）✔
                    })
                    // 策略配置（即逆向配置）
                    .strategyConfig(builder -> {
                        builder.addInclude("mybatisplus_user")  // 设置根据哪个表进行逆向工程✔
                            .addTablePrefix("mybatisplus_", "c_");  // 设置过滤表前缀，生成的文件将去掉表的前缀，可以设置多个前缀✔
                    })
                    // 设置引擎模板
                    .templateEngine(new FreemarkerTemplateEngine())  // 配置使用Freemarker引擎模板，默认的是Velocity引擎模板
                    .execute();
}
---------------
 *
 *
 @author Alex
 @create 2023-03-28-13:30
 */
public class UMP09 {
    @Test
    public void test1(){
        // 配置数据库✔
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/javastudy?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true", "root", "qqabcd")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("zzj")  // 设置作者
                            //.enableSwagger()  // 开启 swagger 模式
                            .fileOverride()  // 覆盖已生成文件✔
                            .outputDir("D://桌面存放");  // 指定输出目录（具体路径，在该路径下创建包）✔
                })
                // 设置包路径
                .packageConfig(builder -> {
                    builder.parent("zzj")  // 设置父包名✔
                            .moduleName("mybatisplus")  // 设置父包模块名，即最后生成的（service/pojo/controller/mapper都在 全局配置路径+父包+模块名下, 即D://桌面存放/zzj/mybatisplus）✔
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://桌面存放/zzj"));  // 设置mapperXml生成路径（具体路径）✔
                })
                // 策略配置（即逆向配置）
                .strategyConfig(builder -> {
                    builder.addInclude("mybatisplus_user")  // 设置根据哪个表进行逆向工程✔
                            .addTablePrefix("mybatisplus_", "c_");  // 设置过滤表前缀，生成的文件将去掉表的前缀，可以设置多个前缀✔
                })
                // 设置引擎模板
                .templateEngine(new FreemarkerTemplateEngine())  // 配置使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

