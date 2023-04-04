package basicfunction;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 【sprintboot集成redis】
 *  就如sprintboot集成mysql需要中间驱动jdbc一样，springboot集成redis也需要中间驱动包
 *  中间驱动包发展：Jedis ==> lettuce ==> RedisTemplate。 lettuce是对jedis的改进，RedisTemplate是又对lettuce的进一步封装
 *
 * 【集成Jedis步骤】
 *（1）引入依赖
--------------
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>4.3.1</version>
</dependency>
-------------
 * (2) 连接redis服务器并使用
 *  Jedis jedis = new Jedis("192.168.131.129", 6379);          # 通过指定ip和端口号 获取连接
 *  jedis.auth("qqabcd");                                      # 指定访问服务器密码
 *  jedis.ping()                                               # 可以像jdbc一样访问redis
 *
 @author Alex
 @create 2023-04-01-21:15
 */


public class UBF15 {
//    public static void main(String[] args) {
//
//        // 1 connection 连接，通过指定ip和端口号
//        Jedis jedis = new Jedis("192.168.131.129", 6379);
//
//        // 2 指定访问服务器密码
//        jedis.auth("qqabcd");
//
//        //  3 获得了Jedis客户端，可以像jdbc一样访问redis
//        System.out.println(jedis.ping());
//
//        // keys
//        Set<String> keys = jedis.keys("*");
//        System.out.println(keys);
//
//        // string
//        jedis.set("k3","v3");
//        System.out.println(jedis.get("k3"));
//        System.out.println("k3的寿命为:" + jedis.ttl("k3"));
//        System.out.println("****************************");
//
//        // list
//        jedis.lpush("list","11","22","33");
//        List<String> list = jedis.lrange("list", 0, -1);
//        for (String s : list) {
//            System.out.println(s);
//        }
//        System.out.println("最左侧出栈" + jedis.rpop("list"));
//        System.out.println("最右侧出栈" + jedis.lpop("list"));
//        System.out.println("****************************");
//
//        // hash
//        jedis.hset("hset1","k1","v1");
//        Map<String,String> hash = new HashMap<>();
//        hash.put("k1","1");
//        hash.put("k2","2");
//        hash.put("k3","3");
//        jedis.hmset("hset2",hash);  // 相当于一次性为field-value赋值
//        System.out.println(jedis.hmget("hset2","k1","k2","k3"));
//        System.out.println(jedis.hget("hset1", "k1"));
//        System.out.println(jedis.hexists("hset2","k2"));
//        System.out.println(jedis.hkeys("hset2"));
//        System.out.println("****************************");
//
//        // set
//        jedis.sadd("set1","1","2","3");
//        jedis.sadd("set2","4");
//        System.out.println(jedis.smembers("set1"));
//        System.out.println("set长度为"+jedis.scard("set1"));
//        System.out.println("随机弹出一个元素"+jedis.spop("set1"));
//        jedis.smove("set1","set2","1");
//        System.out.println(jedis.smembers("set1"));
//        System.out.println(jedis.smembers("set2"));
//        System.out.println(jedis.sinter("set1", "set2"));  // 交集
//        System.out.println(jedis.sunion("set1","set2"));   // 并集
//        System.out.println("****************************");
//
//        // zset
//        jedis.zadd("zset1",100,"v1");
//        jedis.zadd("zset1",80,"v2");
//        jedis.zadd("zset1",60,"v3");
//
//        List<String> zset1 = jedis.zrange("zset1", 0, -1);
//        for (String s : zset1) {
//            System.out.println(s);
//        }
//        List<String> zset11 = jedis.zrevrange("zset1", 0, -1);
//        for (String s : zset11) {
//            System.out.println(s);
//        }
//    }
}

