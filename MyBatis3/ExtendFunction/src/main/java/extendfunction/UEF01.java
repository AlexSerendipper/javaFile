package extendfunction;

import extendfunction.mappers.CacheMapper;
import extendfunction.pojo.Emp;
import extendfunction.utils.GetMapperUtils;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * 【Mybatis缓存】
 *
 * 【MyBatis的一级缓存】✔
 *  一级缓存Mybatis默认开启
 *  一级缓存是SqlSession级别的，通过同一个SqlSession查询的数据会被缓存，下次查询相同的数据！，就会从缓存中直接获取，不会从数据库重新访问
 *  一级缓存失效的四种情况：
 *    不同的SqlSession
 *    同一个SqlSession但是查询条件不同
 *    同一个SqlSession两次查询期间执行了任何一次增删改操作✔
 *    同一个SqlSession两次查询期间手动清空了缓存 —— sqlsession.clearCache()
 *
 * 【MyBatis的二级缓存】✔
 *  二级缓存需要手动开启
 *  二级缓存是SqlSessionFactory级别，通过同一个SqlSessionFactory创建的SqlSession查询的结果会被 缓存；此后若再次执行相同的查询语句，结果就会从缓存中获取
 *  如何开启二级缓存：
 *  （1）在核心配置文件中，设置全局配置属性（setting标签中），设置cacheEnabled="true"，默认为true，不需要设置
 *  （2）在映射文件中（cachemapper.xml）中，设置 <cache /> 标签
 *   (3) 二级缓存必须在SqlSession 关闭 或 手动提交 之后有效
 *        sqlsession.commit()
 *        sqlsession.close()
 *   (4) 查询的数据所转换的实体类类型（resullType）必须实现序列化的接口（serializable）
 *  使二级缓存失效的情况：两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效
 *
 * 【二级缓存的相关配置】了解。在 <cache />中进行配置
 *  eviction属性：缓存回收策略
 *    LRU（Least Recently Used）      最近最少使用的：移除最长时间不被使用的对象。默认使用LRU。
 *    FIFO（First in First out）      先进先出：按对象进入缓存的顺序来移除它们。
 *    SOFT                           软引用：移除基于垃圾回收器状态和软引用规则的对象。
 *    WEAK                           弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。
 *  flushInterval属性：刷新间隔，单位毫秒
 *    默认情况是不设置，也就是没有刷新间隔，缓存仅仅 两次查询中调用增删改语句 时刷新
 *  size属性：引用数目，正整数
 *    代表缓存最多可以存储多少个对象，太大容易导致内存溢出
 *  readOnly属性：只读，true/false
 *    true：只读缓存；会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了很重要的性能优势。
 *    false：读写缓存；会返回缓存对象的拷贝（通过序列化）。这会慢一些，但是安全，因此默认是false。
 *
 * 【MyBatis缓存查询的顺序】
 *  先查询二级缓存，因为二级缓存中可能会有其他程序已经查出来的数据，可以拿来直接使用。
 *   如果二级缓存没有命中，再查询一级缓存
 *   如果一级缓存也没有命中，则查询数据库
 *   SqlSession关闭之后，一级缓存中的数据会写入二级缓存（如果开启了二级缓存的话）
 *
 * 【EHCache】第三方缓存接口 用于 代替MyBatis的二级缓存 。。。简单了解即可
 * （1）配置依赖
-------------------------------
<!-- Mybatis EHCache整合包 -->
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.2.1</version>
</dependency>
<!-- slf4j日志门面的一个具体实现，日志门面就是一个接口，具体实现根据需求决定，类似于jbdc提供接口，具体实现由不同厂商决定 -->
<!-- 这里的具体实现就是logback-classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
</dependency>
-------------------------------
 * （2）创建EHCache的配置文件ehcache.xml
-------------------------------
<?xml version="1.0" encoding="utf-8" ?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:noNamespaceSchemaLocation="../config/ehcache.xsd">
<!-- 磁盘保存路径 -->
<diskStore path="D:\atguigu\ehcache"/>
<defaultCache
    maxElementsInMemory="1000"
    maxElementsOnDisk="10000000"
    eternal="false"
    overflowToDisk="true"
    timeToIdleSeconds="120"
    timeToLiveSeconds="120"
    diskExpiryThreadIntervalSeconds="120"
    memoryStoreEvictionPolicy="LRU">
</defaultCache>
</ehcache>
-------------------------------
 * （3）设置二级缓存的类型
 *  <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
 * （4）配置logback日志功能
 *  使用SLF4J时，简易日志的log4j将失效，此时我们需要借助SLF4J的具体实现logback来打印日志。
 *  创建logback的配置文件logback.xml
-------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
<!-- 指定日志输出的位置 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
        <!-- 日志输出的格式 -->
        <!-- 按照顺序分别是：时间、日志级别、线程名称、打印日志的类、日志主体内容、换行 -->
        <pattern>[%d{HH:mm:ss.SSS}] [%-5level] [%thread] [%logger] [%msg]%n</pattern>
        </encoder>
    </appender>
    <!-- 设置全局日志级别。日志级别按顺序分别是：DEBUG、INFO、WARN、ERROR -->
    <!-- 指定任何一个日志级别都只打印当前级别和后面级别的日志。 -->
    <root level="DEBUG">
        <!-- 指定打印日志的appender，这里通过“STDOUT”引用了前面配置的appender -->
        <appender-ref ref="STDOUT" />
    </root>
    <!-- 根据特殊需求指定局部日志级别 -->
    <logger name="com.atguigu.crowd.mapper" level="DEBUG"/>
</configuration>
-------------------------------



 @author Alex
 @create 2023-03-15-16:20
 */
public class UEF01 {
    // 一级缓存测试
    @Test
    public void test1() throws IOException {
        CacheMapper mapper = GetMapperUtils.getMapper1(CacheMapper.class);
        Emp emp = mapper.GetEmpById(1);
        System.out.println(emp);
        System.out.println("**************可以看到，sql只被输出了一次**************");
        System.out.println(emp);
    }

    // 二级缓存测试
    @Test
    public void test2() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 同一个sqlsessionFactory创建的两个sqlsession
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
        CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
        System.out.println(mapper1.GetEmpById(1));
        sqlSession1.close();
        System.out.println("****************************");
        System.out.println(mapper2.GetEmpById(1));
    }


}
