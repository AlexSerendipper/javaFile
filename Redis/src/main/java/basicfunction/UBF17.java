package basicfunction;

import basicfunction.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 【redis集成RedisTemplate】✔
 *（1）引入依赖
 --------------
<!--SpringBoot与Redis整合依赖（两个）-->
<!--spring-boot-starter-data-redis中自带Lettuce包-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
 -------------
 *
 * (2) 配置application.properties文件
 *
-----------------------
# ========================logging=====================
logging.level.root=info
logging.level.com.atguigu.redis7=info
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger- %msg%n

logging.file.name=D:/mylogs2023/redis7_study.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger- %msg%n

# ========================redis单机=====================
# redis16个库默认用第几号库
spring.redis.database=0
# 修改为自己真实IP
spring.redis.host=192.168.131.129
spring.redis.port=6379
spring.redis.password=qqabcd
# lettuce连接池配置
spring.redis.lettuce.pool.max-active=8
spring.redis.lettuce.pool.max-wait=-1ms
spring.redis.lettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=0
-----------------------
 *
 * （3）创建配置类
 *  主要解决序列化问题：
 *    因为键值都是通过Spring提供的Serializer序列化到数据库中的，其中 RedisTemplate 默认使用的是JdkSerializationRedisSerializer
 *                                                            其中 StringRedisTemplate 默认使用的是StringRedisSerializer
 *    所以当我们在客户端去查阅redis中存储的键值看到的是序列化后的数据，是非常不便的
 *  解决方案一：使用 StringRedisTemplate，这样就无需修改RedisConfig
 *  解决方案二：使用 RedisTemplate，需要进行如下配置类配置
------------------------
@Bean
public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
    RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
    // 设置key序列化方式string
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    // 设置value的序列化方式json，使用GenericJackson2JsonRedisSerializer替换默认序列化
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

    redisTemplate.afterPropertiesSet();

    return redisTemplate;
}
------------------------
 *
 * 【RedisTemplate的使用】
 *  redisTemplate.opsForValue().set(key,value);                # 普通键值对操作
 *  redisTemplate.opsForHash().put("key1","field1","value");   # 哈希类型操作
 *
 @author Alex
 @create 2023-04-02-10:56
 */


//@RestController
//@Slf4j
public class UBF17 {
//    @Autowired
//    private OrderService orderService;
//
//    @RequestMapping(value = "/addorder", method = RequestMethod.GET)
//    public void addOrder(){
//        orderService.addOrder();
//    }
//
//    @RequestMapping(value = "/getorder/{keyId}", method = RequestMethod.GET)
//
//    public void getOrderId(@PathVariable Integer keyId){
//        log.info("获取的订单号为" + orderService.getOrderId(keyId));
//    }
//
}