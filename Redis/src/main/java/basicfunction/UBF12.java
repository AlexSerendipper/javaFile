package basicfunction;

/**
 * 【redis哨兵】哨兵的出现就是为了解决主从复制的缺点
 *  哨兵 巡查监控后台 master主机 是否故障，如果故障了根据 投票数 自动将某一个 slave从库 转换为新主库，继续对外服务，俗称无人值守运维
 *  哨兵的四个功能
 * （1）主从监控：监控主从redis库 (包括master和slave) 运行是否正常
 * （2）消息通知：哨兵可以将故障转移的结果发送到客户端
 * （3）故障转移：如果master异常，则会进行主从切换，将其中一个slave作为新master
 * （4）配置中心：客户端通过连接哨兵来获得当前Redis服务的主节点地址
 *
 * 【redis哨兵使用流程】
 * （1）在src下myredis/ 目录下新建 sentinel.conf文件。。。。。设置其基本配置，方式与redis.conf相同，常用配置如下
-----------------------
bind 0.0.0.0
daemonize yes
protected-mode no
dir myredis/
port 26379
pidfile /var/run/redis_26379.pid
logfile "sentinel26379.log"                                         # 这里建议使用绝对路径，我这里生成的路径就是用相对路径拼接起来的myredis/sentinel26379.log
sentinel monitor mymaster 192.168.131.129 6379 2
sentinel auth-pass mymaster qqabcd
-----------------------
 *  sentinel monitor <master-name> <ip> <redis-port> <quorum>      # 用于指定哨兵监控的master的名字、ip、以及端口号。。。其中quorum用于指定 同意故障迁移的法定票数。
 *                                                                   # 因为网络是不可靠的，有时候一个sentinel会因为网络堵塞而误以为一个master redis已经死掉了，在sentinel集群环境下需要多个sentinel互相沟通来确认某个master是否真的死了。
 *                                                                     quorum这个参数是进行客观下线的一个依据，意思是至少有quorum个sentinel认为这个master有故障，才会对这个master进行下线以及故障转移。
 *  sentinel auth-pass <master-name> <password>                    # 通过密码连接master
 *  sentinel down-after-millseconds <master-name> <millseconds>    # 指定多少毫秒后 master 没有答复哨兵，哨兵将认为 master下线
 * （2）开启哨兵
 *  redis-sentinel myredis/sentinel26379.conf                      # 由于我使用的是相对路径，需要在src目录下使用该路径
 * （3）模仿master宕机
 *     master宕机后会从其他从机选出一个新的master！
 *     宕机的master重连回来后，将直接变成新master的从机（所以建议主机也要设置masterauth，因为后续随时可能变成从机）
 *    （主从机的conf文件实际上在运行期间，都会被sentinel动态修改）
 *
 * 【哨兵的运行流程以及选举原理】面试
 * (1) SDOWN主观下线: 即单个sentinel 自己主观上检测到的关于master的状态，从sentinel的角度来看，如果发送了PING心跳后，在一定时间内没有收到合法的回复，就达到了SDOWN的条件
 *（2）ODOWN客观下线： ODOWN需要一定数量的sentinel（quorum），多个哨兵达成一致意见才能认为一个master客观上已经宕机
 * (3) 当主节点被判断客观下线以后，各个哨兵节点会进行协商，选举出一个 领导者哨兵（哨兵代表） 节点
 *     并由该领导者节点进行故障迁移（Raft算法 选出领导者节点，基本思路就是先到先得~）
 *（4）领导者哨兵依次比较 所有slave的 priority/replication offset/run id， 谁的更大谁就是新的mater (如果priority相同再比较replication offset)
 *     slave priority越低，优先级就越高
 *     slave复制了越多的数据，offset越靠后， 优先级就越高
 *     如果一个slave跟master断开连接已经超过了down-after-milliseconds的10倍，那么slave就会认为其不适合被选举为master.
 * (5) Sentinel leader会对新选举出来的新master执行slaveof no one操作，将其提升为master节点
 *     Sentinel leader然后向其他slave发送命令，让剩余的slave成为新的matster的slave
 *     若 原master重新上线，Sentinel leader会让原master降级为slave恢复正常工作
 *
 * 【哨兵使用建议】
 *  哨兵节点的数量应为多个，哨兵本身应该集群，保证高可用
 *  哨兵节点的数量应该是奇数个
 *  各个哨兵节点的配置应该一致
 *  如果哨兵节点部署在Docker等容器里，要注意端口的正确映射
 *  哨兵集群 + 主从复制，并不能保证数据零丢失（选出新master需要时间，所以引出集群的概念）
 *
 @author Alex
 @create 2023-03-31-20:39
 */
public class UBF12 {
}
