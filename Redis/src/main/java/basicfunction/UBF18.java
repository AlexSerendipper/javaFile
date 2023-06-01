package basicfunction;

import basicfunction.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *【RedisTemplate连接集群配置】以集群模式的三主三从为例，6381主 ==> 6382从（可能最后slave并不是6382，以实际分配为主）
 *                                                  6383主 ==> 6384从（以实际分配为主）
 *                                                  6385主 ==> 6386从（以实际分配为主）
 * 配置application.properties
----------------------------
server.port= 7777
spring.application.name=redis7_study01
# ========================logging=====================
logging.level.root=info
logging.level.com.atguigu.redis7=info
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger- %msg%n

logging.file.name=D:/mylogs2023/redis7_study.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger- %msg%n
# ========================redis集群===================
spring.redis.password=qqabcd
# 获取失败，最大重定向（重试）次数
spring.redis.cluster.max-redirects=3
# lettuce连接池配置
spring.redis.lettuce.pool.max-active=8
spring.redis.lettuce.pool.max-wait=-1ms
spring.redis.lettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=0
# 按照一主一从的方式配置集群
spring.redis.cluster.nodes=192.168.131.129:6381,192.168.131.129:6382,192.168.131.129:6383,192.168.131.129:6384,192.168.131.129:6385,192.168.131.129:6386
----------------------------
 *
 * 【集群功能测试】
 *  经过测试，只需要经过配置并在redis端开启集群，就可以正常使用集群功能
 *  经过测试，在redis服务器端，当6381宕机后，6382将成功顺利上位。当6381重连后，以slave的方式从属于6382
 *   而在springboot通过redistemplate访问redis时，当6381宕机后，会报错Connection refused: no further information: /192.168.131.129:6381
 *   即springboot客户端并没有动态感知到redisCluster集群的最新动态信息
 *   （原因是springboot2中默认使用Lettuce，redis集群的变化，lettuce默认是不会刷新节点拓扑的）
 * （1）解决方式一：使用Jedis
 * （2）解决方式二：刷新节点集群的拓扑动态感应（application.properties配置）
 *                添加如下配置后虽然还是会不断尝试连接6381，但不影响6382已成功上位
----------------------------
# 支持集群拓扑动态感应刷新,自适应拓扑刷新是否使用所有可用的更新，默认false关闭
spring.redis.lettuce.cluster.refresh.adaptive=true
# 定时刷新
spring.redis.lettuce.cluster.refresh.period=2000
----------------------------
 *
 @author Alex
 @create 2023-04-02-13:23
 */

@RestController
@Slf4j
public class UBF18 {
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

}