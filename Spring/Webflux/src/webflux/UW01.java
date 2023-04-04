package webflux;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 【Spring5新功能1】日志框架整合
 *  整个Spring5框架的代码基于Java8，运行时兼容JDK9，许多不建议使用的类和方法在代码库中删除
 *  Spring5框架自带了通用的日志封装 Log4j2（移除了旧版的Log4jConfigListener）
 *
 * 【Log4j2使用流程】
 * (1) 引入相关jar包（4个）
 * (2) 创建log4j2.xml（固定名称，不能用其他名字，内容基本是固定的）（创建后会被自动读取）
 * (3) private static final Logger log = LoggerFactory.getLogger(UW01.class)            # 创建logger，以当前类为参数传入，即指定了logger的名字（输出时知道出处）
 *    （注意引入的是org.slf4j下的相关类）（此时在类中运行的程序就会自动给输出相关日志）
 * (4) log.info/warn                                   # 手动输出相关级别的日志
 *
 * 【日志简介】
 * 日志功能的实现 需要用日志门面来解决系统与日志实现框架的耦合性。 SLF4J就是最常用日志门面。
 *  SLF4J，即简单日志门面（Simple Logging Facade for Java），它不是一个真正的日志实现，而是一个抽象层（ abstraction layer）
 * 日志框架的实现有 Log4j、Logback、log4j2
 * 在springboot中默认整合的是logback日志功能，其使用方式与log4j2非常类似
 *
 @author Alex
 @create 2023-02-28-10:31
 */
public class UW01 {
    private static final Logger log = LoggerFactory.getLogger(UW01.class);

    // 自动输出日志
    // 报错信息，需要将日志级别设置为ERROR
    @Test
    public void test1() {
        System.out.println(10 / 0);
    }

    // 手动输出日志
    public static void main(String[] args) {
        log.info("hello log4j2");
        log.warn("hello log4j2");
    }
}
