package basicfunction.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 @author Alex
 @create 2023-04-02-11:10
 */
@Service
@Slf4j
public class OrderService {
    // RedisTemplate  ===>  StringRedisTemplate
    @Autowired
    private RedisTemplate redisTemplate;
    //    private StringRedisTemplate redisTemplate;

    public static  final String ORDER_KEY = "ord:";

    public void addOrder(){
        // 生成随机数
        int keyId = ThreadLocalRandom.current().nextInt(1000)+1;
        // 生成随机序列
        String serialNo = UUID.randomUUID().toString();

        String key = ORDER_KEY+keyId;
        String value = "京东订单" + serialNo;
        redisTemplate.opsForValue().set(key,value);

        log.info("============key:{}",key);
        log.info("============value:{}",value);
    }

    public String getOrderId(Integer keyId){
        return (String) redisTemplate.opsForValue().get(ORDER_KEY + keyId);
    }
}
