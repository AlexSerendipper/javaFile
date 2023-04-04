package basicfunction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 @author Alex
 @create 2023-04-02-11:07
 */


/**
 *  主要解决序列化问题：
 * 因为键值都是通过Spring提供的Serializer序列化到数据库中的，其中 RedisTemplate 默认使用的是JdkSerializationRedisSerializer
 *                                                         其中 StringRedisTemplate 默认使用的是StringRedisSerializer
 * 所以当我们在客户端去查阅redis中存储的键值看到的是序列化后的数据，是非常不便的
 *
 *  解决方案一：使用 StringRedisTemplate，这样就无需修改RedisConfig
 *  解决方案二：使用 RedisTemplate，
 *
 */
@Configuration
public class RedisConfig {
    /**
     * redis序列化的工具配置类，下面这个请一定开启配置
     * 1) "ord:102"  序列化过
     * 2) "\xac\xed\x00\x05t\x00\aord:102"   野生没有序列化过
     * @param lettuceConnectionFactory
     * @return
     */
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
}
