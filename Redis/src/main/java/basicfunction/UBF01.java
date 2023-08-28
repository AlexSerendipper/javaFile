package basicfunction;

/**
 * 【redis 概述】https://github.com/antirez，原作者github
 *   Remote Dictionary Server(远程字典服务)是完全开源的，遵守BSD协议，是一个高性能的Key-Value 内存 数据库（数据是存在内存中的）✔
 *    提供了丰富的数据结构，例如String、Hash、List、Set、SortedSet等等。✔
 *    同时Redis支持事务、持久化、LUA脚本、发布/订阅、缓存淘汰、流技术等多种功能特性提供了主从模式、Redis Sentinel和Redis Cluster集群架构方案
 *   redis 属于非关系型数据库（NOSQL），即内部数据使用key-value方式存储
 *   redis 是分布式缓存，是挡在mysql数据库前的一道屏障✔✔✔
 *    由于项目中80%的诉求都是查询操作，所以我们希望为其减负，让其专门负责20%的写入数据
 *    所以使用了redis技术，将数据存储在内存中而不是硬盘中，在请求来到时首先查询redis中的数据，查询速度更快！！！
 *    如果在redis中查询不到，再去MySQL中查询，然后将查询结果再写入redis中
 *
 * 【redis 的主要功能】
 *  内存存储和持久化（RDB+AOF）：redis支持异步将内存中的数据写到硬盘上，同时不影响继续服务✔
 *  高可用架构搭配保证（支持多个redis共用，防止一个瘫痪整个sql系统瘫痪）
 *   即可以预防：缓存穿透、击穿、雪崩等问题
 *  支持分布式锁
 *  队列：Reids提供list和set操作，这使得Redis能作为一个很好的消息队列平台来使用。
 *         常通过Reids的队列功能做购买限制。比如到节假日或者推广期间，进行一些活动，对用户购买行为进行限制，限制今天只能购买几次商品或者一段时间内只能购买一次。
 *  排行榜+点赞：在互联网应用中，有各种各样的排行榜，如电商网站的月度销量排行榜、社交APP的礼物排行榜、小程序的投票排行榜等等。Redis提供的set数据类型能够快速实现这些复杂的排行榜。
 *
 * 【优势】
 *  性能极高 -Redis能读的速度是110000次/秒，写的速度是81000次/秒
 *  Redis数据类型丰富，不仅仅支持简单的key-value类型的数据，同时还提供list，zset，set，hash等数据结构的存储
 *  Redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用
 *  Redis支持数据的备份，即master-slave模式的数据备份
 *   生成dump.rpb文件(可以在配置文件中改) 默认生成在redis.conf同级目录
 *
 * 【redis下载】在https://redis.io/中下载redis7。。。。https://github.com/microsoftarchive/redis中是微软提供的windows安装包
 *  https://try.redis.io           # redis在线测试使用网址
 *  https://doc.redisfans.com      # redis命令参考手册
 *
 * 【Redis 和 memcached的异同】
 *  都是内存数据库，memcached还可以存图片、视频。
 *  存储数据类型：memcached的value只能是String，redis支持string/hash/list/set/sortedSet等数据结构。
 *  虚拟内存：redis当内存用完时，可以把很久没用的value放到磁盘。
 *  redis是单线程，memcached是多线程的。
 *  Redis更可靠：memcached不支持持久化；redis可通过RDB快照和AOF日志持久化，所以支持灾难恢复，支持主从数据备份。
 *  应用场景：redis除了可以做NoSQL数据库使用，还可以做消息队列、分布式锁等。
 *
 * 【redis为什么快？】
 * 1. 内存存储：Redis是使用内存存储数据，避免了磁盘IO上的开销。数据存储在内存中。
 * 2. 单线程实现：Redis使用单个线程处理请求，避免了多个线程之间线程切换和锁资源争用的开销（redis6.0以前）
 * 3. 非阻塞IO：Redis使用多路复用IO技术，将epoll作为I/O多路复用技术的实现，再加上Redis自身的事件处理模型将epoll中的连接、读写、关闭都转换为事件，不在网络I/O上浪费时间。
 * 4. 优化的数据结构：Redis有诸多可以直接应用的优化数据结构的实现，应用层可以直接使用原生的数据结构提升性能
 *
 * 要知道，redis的快 是相对于mysql这种数据库来说，要快得多，其原因不单单是因为内存存储
 * 就算Mysql使用了内存，它也还是慢，原因是Mysql作为一种关系型数据库，其中存在了很多集合操作，如group,join等。。。这些操作是很慢的
 * 而redis中也有集合操作（交集，并集），如果在redis中使用大量的集合操作，redis也是很慢的
 * 所以，当数据量不大的时候，我们应尽量避免集合操作。如果数据量过大，我们将使用redis集群，由于集群的约束，此时将不再支持集合操作
 *
 *
 * 【redis7新特性】了解，大体和之前redis版本保持一致和稳定，主要是自身底层性能和资源利用率上的优化和提高
 *  Redis function: 了解，现在主要用luna
 *  client-eviction
 *  Multi-part AOD
 *  ACLV2
 *  listpack代替ziplist
 *  底层性能优化
 *
 @author Alex
 @create 2023-03-29-13:08
 */
public class UBF01 {
}
