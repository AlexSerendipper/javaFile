package basicfunction;

/**
 * 【为什么需要复制功能】✔✔
 *  Redis 需要有高可用架构保证（即支持多个redis共用，防止一个瘫痪整个sql系统瘫痪），所有接下来三章 复制、哨兵、集群都是来解决这个问题的
 *
 * 【Redis复制（replica）】
 *  Redis复制主要指的是 主从复制 功能，即master以写为主，slave以读为主
 *   当master数据变化时，自动将新的数据异步同步到其他slave数据库
 *   主从复制能够解决如下问题，读写分离、down机恢复、数据备份、水平扩容支撑高并发
 *  配置细节：配从不配主
 *   当master配置了requirepass登录密码。slave需要配置masterauth来设置检验密码，否则的话master会拒绝slave的访问请求
 *
 * 【常用命令】
 *  info replication                     # 查看复制节点的主从关系和配置信息
 *  replicaof 主库IP 主库端口             # 写入进redis.conf配置文件内（自动建立主从关系）
 *  slaveof 主库IP 主库端口               # 在运行期间修改slave节点的信息（手动建立主从关系），如果该数据库已经是某数据库的从数据库，那么会立即与 新的主数据库 建立同步关系
 *  slaveof no one                       # 使当前数据库停止与其他数据库的同步，升级为主数据库
 *
 * 【具体配置】redis.conf完整配置流程总结，结合了之前学习的配置内容。实际操作至少需要开启三台虚拟机进行操作，建议以后换电脑再看
 --------------------------防火墙配置（这部分由csdn博主提供，具体尚未研究）----------------------------------
启动： systemctl start firewalld
关闭： systemctl stop firewalld
查看状态： systemctl status firewalld
开机禁用  ： systemctl disable firewalld
开机启用  ： systemctl enable firewalld

添加 ：firewall-cmd --zone=public --add-port=80/tcp --permanent    （--permanent永久生效，没有此参数重启后失效）
重新载入： firewall-cmd --reload
查看： firewall-cmd --zone= public --query-port=80/tcp
删除： firewall-cmd --zone= public --remove-port=80/tcp --permanent
-----------------------------------------------------------------------------------------------------------
 * （1） daemonize yes                       # 允许redis后台运行
 * （2） 注释掉 bind 127.0.0.1                # 默认只能本机访问，影响远程IP连接
 * （3） protected-mode no                   # 关闭保护模式，允许其他机器连接
 * （4） port 6379                           # 指定端口号
 * （5） dir myredis/                        # 指定根目录，log文件以及pid文件生成在该目录下
 * （6） pidfile /var/run/redis_6379.pid     # 指定进程id文件地址（使用默认路径暂时不作修改）
 * （7） logfile "6379.log"                  # 建议使用绝对路径了已经~~这里全用相对路径可读性比较差
 * （8） requirepass                         # 设置主机密码
 * （9） dbfilename dump6379.rdb             # 设置rdb文件名
 * （10）appendonly yes                      # 打开aof功能
 *       appendfilename appendonly.aof
 *
 * 【自动建立主从关系】需要进行配置文件的配置
 * （i）replicaof 192.168.111.185 6379       # 配置从机，指定主机的ip和端口号（自动建立主从关系）
 * （ii）masterauth "qqabcd"                 # 配置从机，指定主机的密码，用于配对连接
 *
 * 【手动指定主从关系】以上配置文件的方式是 自动建立主从关系
 *  slaveof 主库IP 主库端口               # 在运行期间修改slave节点的信息（手动建立主从关系），如果该数据库已经是某数据库的从数据库，那么会立即与 新的主数据库 建立同步关系
 *  使用该命令指定主从关系，当从机宕机重启后，主从关系将不复存在
 *
 * 【常见细节】
 *  从机不能执行写命令，只能执行读的命令
 *  当从机发生宕机，在 从机宕机期间 主机的写入命令，会在从机连接回来之后全部被执行
 *  当主机发生宕机，从机将原地待命，从机数据可以正常使用，等待主机重启！（主机重启后，主从关系恢复！）
 *  ✔上一个slave可以是下一个slave的master（使用自动建立或手动指定 的方式均可），
 *    这样做的好处在于：同样可以接收其他slaves的连接和同步请求，可以有效减轻主master的写压力✔
 *  使用 slaveof no one 指定，将当前数据库升级为主数据库。该操作会保留之前同步的数据
 *
 * 【主从复制的原理解析】面试
 * （1）首次全新连接master
 *     slave启动成功连接到master后会发送一个sync命令
 *     master节点收到sync命令后会在后台开始保存快照（即RDB持久化，主从复制会触发RDB），并将rdb快照文件和缓存的命令发送到所有slave
 *     而slave服务在接收到快照文件后，一次完全同步（全量复制）将被自动执行，slave自身原有数据会被master数据覆盖清除
 *（2）保持通信
 *     repl-ping-replica-period 10。。。。。master发出PING包的周期，默认是10秒
 *     master将 新的所有收集到的修改命令（默认10s内的） 自动一次传给slave，完成同步
 *（3）重连续传
 *    当从机宕机时，主机 master 会检查backlog里面的offset、
 *    master只会把offset后面的数据赋值给slave，类似断电续传
 *
 * 【主从复制的缺点】
 *（1）复制延时，信号衰减
 *    由于所有的写操作都是先在Master上操作，然后同步更新到Slave上，所以从Master同步到Slave机器有一定的延迟。
 *    当系统很繁忙的时候，延迟问题会更加严重，Slave机器数量的增加也会使这个问题更加严重
 *（2）master挂了
 *    默认情况下不会在slave节点自动重选一个master。 整个系统处于半瘫痪状态。
 *
 @author Alex
 @create 2023-03-31-19:11
 */
public class UBF11 {
}
