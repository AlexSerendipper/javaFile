package corefunction;

import corefunction.boot.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 【yaml】
 *  yaml文件作为新型的标记语言，非常适合用来做以数据为中心的配置文件
 *  yaml配置文件 与 properties配置文件 一起生效，并不互斥（即二者只是语法规则不同，）
 *   但是 properties配置文件 的优先级更高，即二者配置了相同的内容，会优先生效 properties配置文件
 *
 * 【基本语法】
 *  key: value；kv之间有空格
 *  大小写敏感
 *  使用缩进表示层级关系（properties使用<></>表示层级关系）
 *  缩进不允许使用tab，只允许空格
 *  缩进的空格数不重要，只要相同层级的元素左对齐即可
 *  '#'表示注释
 *  字符串无需加引号，加了引号后可能被转义
 *   如 'zzj \n lzy' , 单引号 \n作为字符串输出
 *   如 "zzj \n lzy" , 双引号 \n作为换行输出
 *
 * 【数据类型】各种数据类型在yaml中的格式
 *  字面量：单个的、不可再分的值。date、boolean、string、number、null
 *     k: v
 *  对象：键值对的集合。map、hash、set、object
 *     k: {k1:v1,k2:v2,k3:v3}
 *     k:
 *         k1: v1
 *         k2: v2
 *         k3: v3
 *  数组：一组按次序排列的值。array、list、queue
 *      k: [v1,v2,v3]
 *     k:
 *        - v1
 *        - v2
 *        - v3
 *
 * 【配置提示】
 *  自定义的类和配置文件 进行绑定时一般没有提示
 *  可以通过配置提示依赖，完成此功能 (该功能必须在springBoot启动时才能使用)
 *  又因为该功能仅作为辅助功能，与业务无关，故官方建议不要将该功能打包
 *    故在打包插件中添加<configuration>，不打包配置提示依赖
------------------
<!--配置提示依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
------------------
------------------
 <!--配置该插件后，会把项目打成jar包（不打包配置提示依赖），可以直接在目标服务器执行-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <version>2.3.4.RELEASE</version>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-configuration-processor</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
        </plugin>
    </plugins>
</build>
------------------
 @author Alex
 @create 2023-03-19-14:00
 */


@RestController
public class UC05 {
    @Autowired
    private Person person;

    @RequestMapping(value = "/person")
    public Person pp() {
        return person;
    }
}
