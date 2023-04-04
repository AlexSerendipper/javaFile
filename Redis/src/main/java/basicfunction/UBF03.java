package basicfunction;

/**
 * 【redis10大数据类型】这里的数据类型是value的数据类型，key的类型都是字符串
 *  1、redis 字符串（String）
 *  2、redis 列表（List）：按照插入顺序，可以添加一个元素到列表的最左侧 或 最右侧（说明底层是双端链表）
 *  3、redis 哈希表（Hash）
 *  4、redis 集合（Set）：无序集合
 *  5、redis 有序集合（Zset）：sorted set，可排序集合，set中元素仍然不可重复，是通过为每个元素关联一个double类型的分数，从而根据分数实现排序（分数可排序）
 *  6、redis 地理空间（GEO）：了解，主要存储地理位置信息
 *  7、redis 基数统计（HyperLogLog）：了解，在输入元素的数量或体积非常大时，计算基数所需的空间总是固定且很小的
 *  8、redis 位图（bitmap）：了解, 一个仅有0 1构成的二进制bit数组
 *  9、redis 位图（bitfiled）：了解，可以一次操作多个比特位域（多个连续的比特位）
 *  10、redis 流（Stream）：了解, 主要用于消息队列
 *
 * 【redis常见命令】http://www.redis.cn/commands
 *  ✔命令不区分大小写，而key是区分大小写的
 *  ✔帮助命令 help @类型
 *   keys *  		              # 当前库的所有key
 *   exists key                 # 判断某个key是否存在
 *   type key                   # 查看你的key是什么类型
 *   del key                    # 删除指定的key数据
 *   unlink key                 # 非阻塞删除，仅仅将keys从keyspace元数据中删除，没真正的删除会在后续异步中操作
 *   ttl key                    # 查看还有多少秒过期 -1表示永不过期 -2 表示已过期
 *   expire key 秒              # 给key设置过期时间
 *   move key dbindex[0-15]     # 将当前数据库的key移动到指定的数据库中  redis默认是有16个数据库✔
 *   select dbindex[0-15]       # 切换数据库[0-15]，默认为0✔
 *   dbsize   			         # 查看当前数据库key的数量
 *   flushdb       		      # 清空当前库
 *   flushall 			          # 清空16个数据库 慎用
 *
 * 【redis常见命令————String】点赞
 *   string是redis最基本的类型，一个key对应一个value
 *   string类型是二进制安全的，意思是redis的string可以包含任何数据，比如jpg图片或者序列化的对象 。
 *   string类型是Redis最基本的数据类型，一个redis中字符串value最多可以是512M
 *   set key value               # 基本命令
 *   set key value [NX|XX] [GET] [EX seconds|PX milliseconds|EXAT unix-time-seconds|PXAT unix-time-milliseconds|KEEPTTL]    # 完整命令
 *     （1）NX：键不存在时设置键值，若已存在返回nil
 *          XX: 键已存在时覆盖键值
 *     （2）GET：设置值前先返回原先存储的值， set k1 v1 get == getset k1 v1
 *     （3）EX：以秒为单位设置过期时间
 *     （4）PX：以毫秒为单位设置过期时间
 *     （5）EXAT：了解，设置以秒为单位的 UNIX时间戳 为过期时间
 *     （5）PXAT：了解，设置以毫秒秒为单位的 UNIX时间戳 为过期时间
 *     （6）KEEPTTL：保留设置前指定键的生存时间。如set k1 v1 ex 30
 *                                          此时set k1 v2 keepttl,会继续之前的过期时间30s，设置keepttl则会默认用永不过期替代设置的过期时间
 *   mset k1 v1 k2 v2             # 同时设置多个键值
 *    mget k1 k2                　 # 同时获取多个键值
 *    msetnx k1 v1 k3 v3           # 键不存在时, 同时设置多个键值。如果设置的键值中已存在，返回0，设置失败
 *   getrange k1 begin end        # 类似于java中的的substring，获取指定范围的值
 *    setrange k1 offset value    # 从值的第offset位置开始，使用value值进行替代
 *   incr k1 step                 # 使当前k1以步长step自增，只有数字才能进行增减操作
 *    decr k1 step                # 使当前k1以步长step自减，只有数字才能进行增减操作
 *   strlen k1                    # 获取当前字符串长度
 *    append k1 value              # 在当前k1后追加value字符串
 *
 * 【redis常见命令————List】
 *   单key多value结构
 *   lpush [list1] [value] ...     # 往列表（左边）放入元素
 *   Rpush [list1] [value] ...     # 往列表（右边）放入元素
 *   lrange [list1] 0 -1           # 从左边开始遍历列表，0 -1表示取出所有元素，只能从左边遍历✔
 *   lpop [list1]                  # 最左边的出栈，也就是lrange遍历的第一个
 *   rpop [list1]                  # 最右边的出栈,也就是lrange遍历的最后一个
 *   lindex [list1] [index]        # 通过索引值获取值
 *   llen [list1]                  # 获得元素个数
 *   lrem [list1] [num] [value]    # 从左往右删除 （num个 值为 value的元素），常用于去重复值
 *   lrem [list1] 0 [value]        # 从左往右删除所有值为value的值
 *   ltrim [list1] [开始] [结束]    # 截取指定范围的值后再赋给[list1]
 *   RPOPLPUSH [list1] [list2] [value]               # 移除列表list1的最后一个元素，并将该元素添加到list2列表的第一个并返回
 *   lset [list1] [index] [value]                    # 将 list1 的第 index 个索引值改为value
 *   linsert [key] brfore/after [value]              # 在list某个已有值的 前/后 再添加具体值
 *
 * 【redis常见命令————Hash】购物车
 *   k-v 模式不变，但v 又是一个新的键值对。类似于java中的Map<String, Map<Object,Object>
 *   hset key field value [field value]              # 如hset user name zzj age 17
 *    hget key field                                   # 如hget user age
 *    hgetall key                                      # 遍历一个hash，如hgetall user
 *    hdel key field                                   # 删除一个hash中的field
 *   hlen                                            # 获取在某个key内的field的数量
 *   hexists [key] [field1]                          # 判断key中是否有field1这个键
 *   hkeys [key]                                     # 获取key里面的所有field
 *   hvals [key]                                     # 获取key里面的所有value
 *   hincrby [key] field1 [step]                     # key里面field1的值增长 step 整数
 *   hincrbyfloat [key] field1 [step]                # key里面field1的值增长 step 小数
 *   hsetnx [key] field1 [value]                     # 不存在field1,则赋值，若存在了field1则赋值无效
 *
 * 【redis常见命令————Set】猜你喜欢
 *   单key多value结构，与list的区别是 无重复数据
 *     SADD Set1 member                        # 添加元素
 *     SMEMBERS Set1                           # 遍历key中所有元素
 *     SISMEMBER Set1 member                   # 判断元素 member 是否在集合中
 *     SREM Set1 member                        # 删除 member 元素
 *     SCARD  Set1                             # 获取集合长度
 *     SRANDMEMBER Set1 m                      # 从set集合里面随机取出m个。 不会删除元素
 *     SPOP Set1 m                             # 从集合中随机弹出一个元素。 删除元素
 *     SMOVE Set1 Set2                         # 将Set1的已存在的某个值赋给Set2
 *   Set能够进行交并集的运算操作✔✔✔
 *     SDIFF Set1 Set2                             # A - B, 属于A但不属于B的元素构成的集合
 *     SUNION Set1 Set2                            # A U B, 属于A或者属于B的元素
 *     SINTER Set1 Set2                            # A ∩ B, 属于A同时属于B
 *     SINTERCARD numkeys Set1 Set2 [LIMIT limit]  # 了解，不返回结果集，只返回结果的基数（numkeys设定set个数，limit设置显示基数的限制）
 *
 * 【redis常见命令————ZSet】根据销量对商品进行排序
 *  Zset就是在set的基础上加了一个score分数值。  Set1 v1 v2 v3  ==> ZSet1 score1 v1 score2 v2
 *    ZADD zset1 score member [score member]      # 添加元素
 *    ZRANGE zset1 start stop [WITHSCORES]        # 返回元素分数从小到大的顺序。返回索引从start到stop之间的所有元素（withscopes会将分数一同遍历）
 *    ZREVRANGE zset1 0 - 1 [WITHSCORES]          # 反序。返回元素分数从大到小的顺序
 *    ZRANGEBYSCORE zset1 min max [WITHSCORES] [LIMIT offset count]      # 获取指定分数范围的元素（左开右开） 。limit是返回个数限制
 *    ZSCORE zset1 member                         # 获取指定元素member的分数
 *    ZCARD zset1                                 # 获取集合中元素的数量
 *    ZREM zset1 member                           # 删除指定元素member
 *    ZMPOP numkeys zset1 <MIN | MAX> [COUNT count]                 # 了解，弹出zset1中弹出 numkeys个分数 最大/最小 的member值
 *    ZINCRBY zset1 increment member              # 增加某个元素的分数
 *    ZCOUNT zset1 min max                        # 获得指定分数范围内的元素个数
 *    ZRANK zset1 member                          # 获得member的下标值
 *    ZREVRANK zset1 member                       # 逆序获得下标值
 *
 @author Alex
 @create 2023-03-30-11:42
 */
public class UBF03 {
}
