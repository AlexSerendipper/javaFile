package springsecurity;

/**
 * 【thymeleaf 对 spring security的支持】
 * （1）引入相关依赖
---------------
<!-- thymeleaf 对 spring security的支持 -->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
---------------
 * （2）在html界面中，利用thymeleaf语法，获取到用户权限，实现不同的用户权限显示不同的数据
 *  xmlns:sec="http://www.thymeleaf.org/extras/spring-security"                 # 在namespace处 引入sec命名空间
 *  sec:authorize="hasAnyAuthority('moderator')"                                # thymeleaf语法，仅指定权限的用户显示标签
 *
 @author Alex
 @create 2023-04-23-16:48
 */
public class US03 {
}
