package basicfunction;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

/**
 * 【redis集成lettuce】
 *  Jedis连接redis服务器的时候，每个线程都要拿自己创建的jedis实例去连接redis客户端。并且线程是不安全的
 *   Lettuce发现了这些毛病，底层使用了Netty, 当有多个线程都需要redis服务器时可以保证只创建一个Lettuce连接
 *   使所有线程共享这一个Lettuce，而且这种方式也是线程安全的。
 *
 * 【集成lettuce步骤】
 *（1）引入依赖
--------------
<!--lettuce-->
<dependency>
     <groupId>io.lettuce</groupId>
     <artifactId>lettuce-core</artifactId>
</dependency>
-------------
 * (2) 连接redis服务器并使用
 *  RedisURI uri = RedisURI.builder()                                           # 创建RedisURI
 *                 .withHost("192.168.131.129")
 *                 .withPort(6379)
 *                 .withAuthentication("default", "qqabcd")
 *                 .build();
 *  RedisClient redisClient = RedisClient.create(uri);                           # 创建客户端
 *   StatefulRedisConnection<String, String> conn = redisClient.connect();         # 连接客户端
 *  RedisCommands<String, String> commands = conn.sync();                        # 创建操作命令的command
 *  conn.close();                                                                # 各种资源的关闭
 *   redisClient.shutdown();
 *
 *
 @author Alex
 @create 2023-04-02-10:20
 */


public class UBF16 {
//    public static void main(String[] args) {
//        // 1 使用构建器链式编程来builder我们的RedisURI
//        RedisURI uri = RedisURI.builder()
//                        .withHost("192.168.131.129")
//                        .withPort(6379)
//                        .withPassword("qqabcd")
//                        .build();
//        // 2 创建客户端，连接客户端
//        RedisClient redisClient = RedisClient.create(uri);
//        StatefulRedisConnection<String, String> conn = redisClient.connect();
//
//        // 3 创建操作的command, 通过conn 创建
//        RedisCommands<String, String> commands = conn.sync();
//
//        // string
//        commands.set("k1","v1");
//        commands.set("k2","v2");
//        System.out.println("==========================="+commands.get("k1"));
//        System.out.println("==========================="+commands.mget("k1","k2"));
//        List<String> keys = commands.keys("*");
//        for (String key : keys) {
//            System.out.println("========================="+key);
//        }
//
//        // list
//        commands.lpush("list01","1","2","3");
//        List<String> list01 = commands.lrange("list01", 0, -1);
//        for (String s : list01) {
//            System.out.println("========================="+s);
//        }
//        System.out.println("========================="+ commands.rpop("list01"));
//
//        // hash
//        commands.hset("hash","k1","v1");
//        commands.hset("hash","k2","v2");
//        commands.hset("hash","k3","v3");
//        System.out.println("========================="+commands.hgetall("hash"));
//        Boolean hexists = commands.hexists("hash", "v2");
//        System.out.println("========================="+hexists);
//
//        // set
//        commands.sadd("s1","1","2");
//        System.out.println("=================================" + commands.smembers("s1"));
//        System.out.println("=========================" + commands.sismember("s1", "1"));
//        System.out.println("=========================" + commands.scard("s1"));
//
//        // zset
//        commands.zadd("a1",100,"v1");
//        commands.zadd("a1",80,"v2");
//        System.out.println("=========================" + commands.zrange("a1", 0, -1));
//        System.out.println("========================="+commands.zcount("a1", "90", "100"));
//
//        // 4 各种关闭释放资源  先开后关
//        conn.close();
//        redisClient.shutdown();
//
//    }
}
