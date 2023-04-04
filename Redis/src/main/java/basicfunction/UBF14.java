package basicfunction;

/**
 * 【集群配置】以集群模式的三主三从为例，6381主 ==> 6382从（可能最后slave并不是6382，以实际分配为主）
 *                                   6383主 ==> 6384从（以实际分配为主）
 *                                   6385主 ==> 6386从（以实际分配为主）
 *  创建集群配置目录 /myredis/cluster/redisCluster6381.conf
 *                  /myredis/cluster/redisCluster6382.conf
--------------------------
bind 0.0.0.0
daemonize yes
protected-mode no
port 6381
logfile "cluster6381.log"
pidfile cluster6381.pid
dir myredis/cluster
dbfilename dump6381.rdb
appendonly yes
appendfilename "appendonly6381.aof"
requirepass qqabcd
masterauth qqabcd

cluster-enabled yes                             # 是否开启集群
cluster-config-file nodes-6381.conf             # 集群的配置文件名
cluster-node-timeout 5000                       # 集群的超时时间
--------------------------
 *  创建集群实例
 *    redis-server myredis/cluster/redisCluster6381.conf
 *  构建集群间关系（--cluster-replicas 1 表示：接下来的ip以一主一从的方式创建集群关系）（构建后将显示每个节点负责的槽位数）
 *    redis-cli -a qqabcd --cluster create --cluster-replicas 1 ip地址:6381 ip地址:6382 ip地址:6383 ip地址:6384 ip地址:6385 ip地址:6386
 *  查看集群状态（以进入master 6381为例）
 *    cluster info                            # 查看集群的整体信息
 *    cluster nodes                           # 查看整个集群的从属信息
 *    redis-cli -a qqabcd --cluster check 192.168.131.129:6381          # 查看当前节点所属集群的的槽位分布情况 以及 节点ID
 *
 * 【集群功能的使用】
 *  需要以-c指定登录集群的任意节点，此时将以 哈希槽分片 的方式，自动将数据写入到分配的节点！✔✔
 *    redis-cli -a qqabcd -p 6381 -c
 *    CLUSTER KEYSLOT key                       # 查看某个键所属的槽位
 *  经过测试，当6381宕机后，6382将成功顺利上位。当6381重连后，以slave的方式从属于6382
 *   若希望6381仍作为master，6382作为slave，可以使用如下命令
 *    CLUSTER FAILOVER                          # 节点的从属关系调整
 *
 * 【集群扩容】如何将 6387主 ==> 6388从 , 添加到已有的三主三从集群中。扩容成四主四从集群
 * （1）首先创建 6387 和 6388集群实例
 * （2）将新增的 6387 作为 master 节点加入原有集群。。。。以原集群的任一节点作为切入点均可
 *      redis-cli -a 密码 --cluster add-node IP地址:6387 IP地址:6381
 * （3）根据master节点的数量，为当前集群重新分配槽位。。。。以原集群的任一节点作为切入点均可
 *      redis-cli -a 密码 --cluster reshard IP地址:6381
 *       （how many slots do you want to move? 系统询问要从原集群中匀出多少槽位？）4096
 *       （what is the receiving node ID?系统询问从原集群中匀出的槽位由哪个节点接收?）输入6387节点的ID即可
 *       （please enter all the source node IDs)
 *        (type all to use all the nodes as source nodes for the hash slots? type done when you finish.）输入匀出槽位的节点ID，输入all，表示从所有节点各自抽取一部分槽位给6387
 * （4）为新增主节点6387分配从节点6388
 *      redis-cli -a 密码 --cluster add-node ip地址:新slave端口 ip:新增主节点端口 --cluster-slave --cluster-master-id 新增主节点ID
 *
 * 【集群缩容】
 * （1）先删除从节点6388（要先查看得到6388节点ID）
 *      redis-cli -a 密码 --cluster del-node ip地址:6388 从机6388节点ID
 * （2）根据master节点的数量，为当前集群重新分配槽位。。。。以原集群的任一节点作为切入点均可。。。这里要清空节点6387的槽位，重新分配（示例全部分配给6381端口）
 *      redis-cli -a 密码 --cluster reshard IP地址:6381
 *       （how many slots do you want to move? 系统询问要从原集群中匀出多少槽位？）4096
 *       （what is the receiving node ID?系统询问从原集群中匀出的槽位由哪个节点接收?）输入6381节点的ID即可
 *       （please enter all the source node IDs)
 *        (type all to use all the nodes as source nodes for the hash slots? type done when you finish.）输入匀出槽位的节点ID，输入6387节点ID。。输入done完成
 * （3）删除6387节点
 *      redis-cli -a 密码 --cluster del-node ip地址:6387 6387节点ID
 *
 * 【集群细节补充】
 * （1）由于不同的键会存放到不懂槽位下，故 无法使用mset、mget等多键操作
 *      可以通过{}来定义同一个组的概念，{}内输入组名，可以将 相同组 的键值对，存放到一个槽位上
 *      如： mset k1{z} k2{z} k3{z}
 *      此时就可以使用mget将同一个组的数据取出：   mget k1{z} k2{z} k3{z}
 * （2）cluster.conf 中配置集群是否完整才能对外提供服务：即当一个主节点及其从节点都宕机时，是否对外提供服务？
 *      cluster-require-full-coverage yes                 　　# 默认 yes , 即需要集群完整才可对外提供服务
 * （3）比较少用的到的命令
 *      CLUSTER COUNTKEYSINSLOT 槽位号                        # 返回1表示槽位被占用，0表示槽位没有被占用
 *      CLUSTER KEYSLOT 键名称                                # 查看该键应该放在哪个槽位上
 *
 @author Alex
 @create 2023-04-01-13:03
 */
public class UBF14 {
}
