package datafunction;

/**
 * 【springboot指标监控功能】后续会集成到springcloud中
 *  每一个微服务在云上部署以后，我们都需要对其进行监控、追踪、审计、控制等。
 *   SpringBoot就抽取了Actuator场景，使得我们每个微服务快速引用即可获得生产级别的应用监控、审计等功能。
 *
 * 【指标监控功能的使用】
 * （1）依赖引入
--------------------
<!--引入监控功能-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
-------------------
 * （2）开启端点（默认只开启health和info端点）（在application.yaml中配置）
-------------------
management:
    endpoints:
        enabled-by-default: true  # 暴露所有端点信息
        web:
            exposure:
                include: '*'  # 以web方式暴露所有端点
------------------
 * （3）使用http://localhost:8080/actuator/health，输入端点查看监控指标
 *
 * 【最常用的EndPoint】可以对每个端口进行详细配置，也可以不暴露所有端点信息，对需要开启的端口进行开启✔✔
 *  Health：监控状况
------------------
management:
    endpoints:
        enabled-by-default: true  # 暴露所有端点信息
        web:
            exposure:
                include: '*'  # 以web方式暴露所有端点
    endpoint:
        health:
            show-details: always  # 显示健康端口的详细信息
------------------
 *  Metrics：运行时指标
 *  http://localhost:8080/actuator/metrics/jvm.buffer.memory.used          # 可以输入具体指标查看详细信息
 *  Loggers：日志记录
 *
 * 【定制EndPoint】定制EndPoint及其之后的功能就用到时候再看了，即p79集之后的~
 @author Alex
 @create 2023-04-02-21:37
 */
public class UD05 {
}
