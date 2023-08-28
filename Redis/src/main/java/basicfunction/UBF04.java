package basicfunction;

/**
 * 【redis常见命令————bitmap】了解。。。（常用于存储大量的连续布尔值的数据，如：用户的签到）
 *   位图本质是数组，可以将其想象为一个仅有0 1构成的二进制bit数组，故其占用空间非常小。
 *    该数组由多个二进制位组成，每个二进制位都对应一个偏移量(索引)。
 *    它是用String类型作为底层数据结构实现的一种统计二值状态的数据类型。
 *     SETBIT key offset value    # 将第offset的值设为value。value只能是0或1。offset 从0开始
 *     GETBIT key offset          # 获得第offset位的值
 *     STRLEN key                 # 得出占多少字节。超过8位后自己按照8位一组一byte再扩容。
 *     BITCOUNT key               # 得出该key里面含有几个1
 *     BITOP and destKey key1 key2    # 对一个或多个 key 求逻辑并，并将结果保存到 destkey
 *     BITOP or destKey key1 key2     # 一个或多个 key 求逻辑或，并将结果保存到 destkey
 *     BITOP XOR destKey key1 key2    # 对一个或多个 key 求逻辑异或，并将结果保存到 destkey
 *     BITOP NOT destKey key1 key2    # 对一个或多个 key 求逻辑非，并将结果保存到 destkey
 *
 * 【redis常见命令————HyperLogLog】了解，在输入元素的数量或体积非常大时，计算基数所需的空间总是固定且很小的（如计算网站的访客数时）
 *   HyperLogLog结构仍然是使用String类型作为底层数据结构
 *   基数：是一种数据集，去重复后的真实个数。。。
 *   基数统计，就是用于统计一个集合中不重复的元素个数，就是对集合去重复后剩余元素的计算
 *   HyperLogLog功能: HyperLogLog 是一种用于统计基数的数据集合类型。它使用了一种 去重复统计功能 的基数估计算法
 *                     使用HyperLogLog，只需要花费12KB内存，就能计算 2的64次方=18446744073709551616 个不同元素的基数！！
 *                     HyperLogLog基数统计算法 是一个不精确的统计算法，标准误差为0.81%
 *     PFADD loglog1 element[element]         # 添加元素
 *     PFCOUNT loglog1                        # 返回基数
 *     PFMERGE distResult  loglog1,loglog2    # 将多个hyperloglog合并为一个 并 返回基数
 *
 * 【redis常见命令————GEO】了解，主要存储地理位置信息
 *   主要用于存储地理位置信息，并对存储的信息进行操作
 *    底层使用zset类型作为底层数据结构
 *   地球上的地理位置是使用二维的经纬度表示，经度范围 (-180, 180]，纬度范围 (-90, 90]，只要我们确定一个点的经纬度就可以名取得他在地球的位置。
 *     GEOADD geo1 "经纬度" "地名"             # 添加经纬度坐标
 *     GEOPOS geo1 "地名"                     # 获得经纬度
 *      redis -cli -a qqabcd --raw             # 登陆时使用该命令，可解决登陆时中文乱码的问题
 *     GEOHASH geo1 "地名"                    # 返回经纬度的hash映射~将经纬度一维表示
 *     GEODIST geo1 "地名1" "地名2" km         # 返回两地之间的距离
 *     GEORADIUS geo1 "当前经纬度" "距离" withdist withcoord withhash count 10 desc   # 返回距离当前设定经纬度的，距离内的元素信息
 *                                                                                      withdist：返回元素与中心 的距离
 *                                                                                      withcoord：返回元素的经纬度
 *                                                                                      withhash：返回元素的经纬度的哈希编码
 *                                                                                      count：限定返回的记录数
 *
 * 【redis常见命令————Stream】了解！！！, 主要用于消息队列。这里先不学了，感觉根本用不到P24-26集
 *   就是redis版本的MQ，消息中间件。 实现消息队列的功能
 *   Stream流实现消息队列，它支持消息的持久化、支持自动生成全局唯一 ID、支持ack确认消息的模式、支持消费组模式等，让消息队列更加的稳定和可靠
 *
 * 【redis常见命令————bitfiled】了解！！！，可以一次操作多个比特位域（多个连续的比特位）。这里先不学了，感觉根本用不到P27集
 @author Alex
 @create 2023-03-30-14:36
 */
public class UBF04 {
}
