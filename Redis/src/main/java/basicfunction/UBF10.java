package basicfunction;

/**
 * 【Redis发布订阅（pub/sub）】✔✔✔了解即可！不需要实操，知道有这个东西就行
 *  pub/sub发展概述：消息中间件市面上最常用的是MQ，kafka,RabbitMQ。而redis stream是研发出来抢占消息中间件市场份额的东西~ 而pub/sub是redis stream是前身
 *  pub/sub介绍：是一种消息通信模式：发送者（PUBLISH）发送消息。订阅者（SUBSCRIBE）接收消息，可以实现进程间的消息传递
 *                理论上pub/sub可以实现消息中间件MQ的功能，通过发布订阅实现消息的引导和分流功能
 *                但是不建议使用该功能，专业的事情就交给专业的中间件来做，redis就做好分布式缓存功能就好
 *  pub/sub的功能：Redis 客户端 可以订阅任意数量的频道，类似我们微信关注多个公众号。当频道发出消息，客户端会收到消息
 *  pub/sub 其实是一个轻量的队列，只不过数据不会被持久化，一般用来处理实时性较高的异步消息
 *
 * 【常用命令】
 *  要先订阅再发布消息，订阅前发布的消息是收不到的
 *    订阅的用户可以收到三个参数信息：消息的种类、始发频道的名称、实际的消息内容
 * （1）基本命令
 *  SUBSCRIBE channel [channel]              # 订阅多个频道
 *  PSUBSCRIBE pattern [pattern...]          # 按照模式批量订阅，订阅一个或多个符合给定模式（支持*号?号之类的）的频道
 *  PUBLISH channel message                  # 对指定频道发布信息
 * （2）统计命令
 *  PUSUB CHANNELS                           # 由活跃频道组成的列表
 *  PUSUB NUMSUB channel [channel...]        # 某个频道有几个订阅者
 *  PUBSUB NUMPAT                            # 统计使用PUBSCRIBE命令订阅的频道的数量。
 * （3）退订命令
 *  UNSUBSCRIBE channel [channel...]         # 取消订阅
 *  PUNSUBSCRIBE pattern [pattern...]        # 退订所有给定模式的频道
 *
 @author Alex
 @create 2023-03-31-18:44
 */
public class UBF10 {
}
