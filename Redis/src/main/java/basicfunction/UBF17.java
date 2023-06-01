package basicfunction;

import basicfunction.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * (2) 配置application.properties文件
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
    // 设置key序列化方式为 string
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    // 设置value的序列化方式为 json，使用GenericJackson2JsonRedisSerializer替换默认序列化
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    // 设置hash的key的序列化方式为 string
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    // 设置hash的value的序列化方式为 json
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

    redisTemplate.afterPropertiesSet();

    return redisTemplate;
}
------------------------
 *
 * 【RedisTemplate的使用】
 * 【常见命令————通用命令】
 *  redisTemplate.delete(key);                                 # 删除键
 *  redisTemplate.hasKey(key);                                 # 是否存在key
 *  redisTemplate.expire(key,time,timeUnit);                   # 设置key多少time后过期，timeUnit输入时间单位，如：TimeUnit.SECONDS
 *
 * 【常见命令————String】
 *  redisTemplate.opsForValue().set(key1,value);                # string类型操作（普通键值对）
 *  redisTemplate.opsForValue().increment(key1)                 # 为当前键的值累加1
 *
 * 【常见命令————Hash】
 *  redisTemplate.opsForHash().put("key1","field1","value");   # 哈希类型操作
 *
 * 【常见命令————List】
 *  redisTemplate.opsForList().leftPush("key1",101);           # 列表类型操作
 *  redisTemplate.opsForList().size("key1");                   # 列表中数据个数
 *  redisTemplate.opsForList().index("key1",0);                # 列表中索引为0的数据
 *  redisTemplate.opsForList().range("key1",0,2);              # 列表中索引为0到2的数据
 *  redisTemplate.opsForList().leftPop("key1");                # 弹出列表中的数据
 *
 * 【常见命令————Set】
 *  redisTemplate.opsForSet().add("key1","刘备","关羽");        # Set类型操作
 *  redisTemplate.opsForSet().size("key1");                    # Set中数据个数
 *  redisTemplate.opsForSet().pop("key1");                     # 弹出Set中的数据
 *  redisTemplate.opsForSet().members("key1");                 # 输出Set中的所有数据
 *
 * 【常见命令————Zset】
 *  redisTemplate.opsForZSet().add("key1","刘备",55);           # ZSet类型操作
 *  redisTemplate.opsForZSet().zCard("key1");                   # ZSet中数据个数
 *  redisTemplate.opsForZSet().score("key1","刘备");            # 查询分数
 *  redisTemplate.opsForZSet().rank("key1","刘备");             # 查询排名（正序）
 *  redisTemplate.opsForZSet().reverseRank("key1","刘备");      # 查询排名（倒序）
 *  redisTemplate.opsForZSet().range("key1",0,2);               # 查询前三名的数据（正序）
 *  redisTemplate.opsForZSet().reverseRange("key1",0,2);        # 查询前三名的数据（倒序）
 *
 * 【常见命令————hyperloglog】
 *  redisTemplate.opsForHyperLogLog().add(key1,value1)          # 存储数据到HyperLogLog中 (通常用于往此key1中存入大量的value)
 *  redisTemplate.opsForHyperLogLog().size(key1)                # 统计key1下value的个数（不重复的元素个数）
 *  String redisKey = "keyUnion"                                # 以keyUnion 存储 合并后数据，在hyperloglog数据合并前需要提前声明
 *   redisTemplate.opsForHyperLogLog().union(redisKey,key1,key2) # 将key1,key2中的数据合并到 键keyUnion当中
 *
 * 【常见命令————bitmap】因为bitmap实际上底层就是string,所以直接用opsForvalue
 *  String redisKey = "bitmapKey1"                              # 需要先声明存储的key值
 *  redisTemplate.opsForValue().setBit(redisKey,1,true);        # 利用bitmap存储第offset个位置的布偶值
 *  redisTemplate.opsForValue().getBit(redisKey,1);             # 取出第offset个位置的布偶值
 *  Object obj = redisTemplate.execute(new RedisCallback(){     # 利用redis底层的回调函数，可以统计bitmap中的数据(1的个数)
 *          @Override
 *          public Object doInRedis(RedisConnection redisConnection) throws DataAccessException{
 *              return redisConnection.bitCount(redisKey.getBytes());  // 统计个数
 *          }
 *   })
 *  Object obj = redisTemplate.execute(new RedisCallback(){  // 利用redis底层的回调函数，对多个bitmap作 与或非运算
 *          @Override
 *          public Object doInRedis(RedisConnection redisConnection) throws DataAccessException{
 *              redisConnection.bitOp(RedisStringCommands.BitOperation.OR, redisKey.getBytes(), rediskey.getBytes(), key1.getBytes(), key2.getBytes());  // 指定OR运算符，将key1和key2的数据进行or运算后合并到rediskey当中
 *              return redisConnection.bitCount(redisKey.getBytes());  // 统计个数
 *          }
 *   })
 *
 * 【其他命令】
 *  BoundValueOperations operations = redisTemplate.boundValueOps("key1");              # 相当于为redisTemplate绑定了一个key，使用该对象访问redis，均访问的是同一个key1
 *    operations.increment();
 *  编程式事务支持 redis事务✔✔
 *   注意查询语句要放在事务之外执行（即在事务开启前先执行查询语句），否则是查询不到的
 *   在事务内，使用RedisOperations实例来代替RedisTemplate执行操作
---------------
public Object testTransaction(){
    Object obj = redisTemplate.execute(new SessionCallback() {
        @Override
        public Object execute(RedisOperations redisOperations) throws DataAccessException {
            redisOperations.multi();  // 开启事务
                redisOperations.opsForSet().add("set1", "刘备");
                redisOperations.opsForSet().add("set1", "关羽");
                redisOperations.opsForSet().add("set1", "张飞");
                // 事务，语句队列执行，所有肯定是查询不到值的
                System.out.println(redisOperations.opsForSet().members("set1"));
                // 输出了每条执行语句影响的行数 + 所有数据
            return redisOperations.exec();  // 执行事务
        }
    });
    return obj;
}
---------------
 @author Alex
 @create 2023-04-02-10:56
 */


@RestController
@Slf4j
public class UBF17 {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/addorder", method = RequestMethod.GET)
    public void addOrder(){
        orderService.addOrder();
    }

    @RequestMapping(value = "/getorder/{keyId}", method = RequestMethod.GET)
    public void getOrderId(@PathVariable Integer keyId){
        // 只需要输入id值即可
        log.info("获取的订单号为" + orderService.getOrderId(keyId));
    }

    @GetMapping("/transaction")
        public void transaction(){
        Object o = orderService.testTransaction();
        System.out.println(o);
    }


}