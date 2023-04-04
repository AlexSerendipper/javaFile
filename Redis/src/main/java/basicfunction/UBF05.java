package basicfunction;

/**
 * 【redis持久化】
 *  redis持久化主要使用两种方式， RDB（Redis Data Base） 和 AOF（append only file）
 *  可以使用一种方式，或使用 RDB-AOF混合持久化
 *
 * 【RDB】
 *  RDB（Redis 数据库）：RDB 持久性以指定的时间间隔执行数据集的时间点快照。
 *  实现类似照片记录效果的方式，就是把某一时刻的数据和状态以文件的形式写到磁盘上，也就是快照。这样一来即使故障宕机，快照文件也不会丢失，数据的可靠性也就得到了保证。
 *   这个快照文件就称为RDB文件(dump.rdb)，其中，RDB就是Redis DataBase的缩写。
 *
 * 【RDB自动保存机制】✔✔✔✔✔✔✔✔
 * （1）配置文件修改。redis.conf中
 *     sava 5 2              # 看snapshotting栏，可以看到自动保存的时间。在433行，可以自定义保存时间（这里设置的是5秒钟两次修改则触发）
 *     dir myredis/          # 在505行，指定根目录（默认dump.rdb生成在根目录下）
 *                              (在ubuntu中我都是使用相对路径, 以启动路径src目录为根，所以都需要在src目录下打开终端~~~✔)
 *                             （如果使用绝对路径，需要使用 /home/alex/opt/redis-7.0.10/src/myredis/）
 *     dbfilename dump.rdb   # 在482行，可以配置dump文件的名字
 * （2）备份恢复
 *     将备份文件 dump.rdb 移动到安装目录下 并重新启动服务器
 *  由于在执行flushall/flushdb命令时，会立刻产生dump.rdb文件，但是里面是空的，毫无意义
 *   并且当执行shutdown命令时，也会立刻保存关机前的快照
 *  所以我们一定要将 dump.rdb 里面有值的时候将其备份好，然后在执行以上命令之后，替换dump.rdb回来，才能在开启服务时完成备份恢复！
 *
 * 【RDB手动保存机制】✔
 * （1）手动保存命令
 *     Save                         # 在主线程中执行会阻塞redis服务器，直到持久化工作完成才能处理其他命令，线上禁止使用✔
 *     BGSAVE（默认）               # 在后台异步进行快照操作，允许朱进程同时可以修改数据
 *     lastsava                     # 获得最后一次快照的时间戳
 * （2）Rbd修复命令：需要在当前目录的src目录下
 *     redis-check-rdb myredis/dumpfiles/dump.rdb        # 修复传输中受损的rdb文件
 *
 *  【RBD的优劣势】
 *  优势
 *   适合大规模的数据恢复
 *   按照业务定时备份
 *   对数据完整性和一致性要求不高
 *   RDB文件在内存中的加载速度比AOF快得多
 *  劣势
 *   在一定间隔时间做一次备份，如果redis意外down机，就会丢掉最近一次快照到down机时的数据
 *   内存数量的全量同步，如果数据量过大会导致IO严重影响服务器性能
 *   RDB依赖于主进程的 fork，在更大的数据集中，这可能会导致服务器请求的瞬间延迟
 *   fork的时候内存中的数据被克隆了一份，大致2倍的膨胀性，需要考虑
 *
 *  【触发RDB快照的情况】
 *  RDB自动保存机制
 *  手动 save/bgsave 命令
 *  执行 flush / flushdb 命令也会产生 dump.rdb 文件，但里面是空的，无意义
 *  执行 shutdown 且没有设置开启 AOF 持久化
 *  主从复制时，主节点自动触发
 *
 * 【禁用RDB快照的方式】
 *  在配置文件中搜索Snapshotting can be completely disabled，然后开启 save"" 即可
 *
 @author Alex
 @create 2023-03-30-15:26
 */
public class UBF05 {
}
