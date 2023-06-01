package backups;

/**
 * 【二进制日志(bin log)】
 *  binlog可以说是MySQL中比较 重要 的日志了，在日常开发及运维过程中，经常会遇到。
 *   binlog即binary log，二进制日志文件，也叫作变更日志（update log）。它记录了数据库所有执行的DDL 和 DML 等数据库更新事件的语句✔，但是不包含没有修改任何数据的语句（如数据查询语句select、show等）。
 *   它以事件形式 记录并保存在 二进制文件 中。通过这些信息，我们可以再现数据更新操作的全过程。
 *  binlog主要应用场景：
 *   一是用于 数据恢复：如果MySQL数据库意外停止，可以通过二进制日志文件来查看用户执行了哪些操作，对数据库服务器文件做了哪些修改，然后根据二进制日志文件中的记录来恢复数据库服务器。
 *   二是用于 数据复制：由于日志的延续性和时效性，master把它的二进制日志传递给slaves来达到 master-slave数据一致的目的。
 *                     可以说MySQL数据库的 数据备份、主备、主主、主从都离不开binlog，需要依靠binlog来同步数据，保证数据一致性。
 *    show variables like '%log_bin%';
 *      log_bin                                     # 是否开启log_bin
 *      log_bin_basename                            # 是binlog日志的默认路径及默认文件名
 *      log_bin_index                               # 是binlog文件的索引文件，这个文件管理了所有的binlog文件目录
 *      log_bin_trust_function_creators             # 限制存储过程，前面我们已经讲过了，这是因为二进制日志的一个重要功能是用于主从复制，而存储函数有可能导致主从的数据不一致。所以当开启二进制日志后，需要限制存储函数的创建、修改、调用
 *                                                    （ 比如主机上使用了一个函数Now(), 而主机同步到从机需要一定的时间，从机上使用的now() 和 主机上的now()指的不是同一个时间 ）
 *      log_bin_use_v1_row_events                   # 此只读系统变量已弃用。ON表示使用版本1二进制日志行，OFF表示使用版本2二进制日志行(MySQL 5.6的默认值为2)
 *      sql_log_bin                                 # 是否将 对数据库更新事件的sql语句 记录到bin_log文件当中
---------------------
[mysqld]
# 启用二进制日志
log-bin="/var/lib/mysql/binlog/atguigu-bin"         # 改变binlog日志的默认路径及默认文件名
binlog_expire_logs_seconds=600                      # binlog日志的保存时间
max_binlog_size=100M                                # binlog日志文件的默认大小
---------------------
 *
 * 【使用Bin log】
 * （1）查看bin log
 *  MySQL服务每重新启动一次 ，以“.000001”为后缀的 bin log文件就会增加一个，并且按照后缀名递增。即日志文件的个数与MySQL服务启动的次数相同；并且如果日志长度超过了 max_binlog_size 的上限（默认是1GB），就会创建一个新的日志文件。
 *    show binary logs                                               # 查看生成的日志文件信息
 *    mysqlbinlog -v "/var/lib/mysql/binlog/atguigu-bin.000002"      # 1、方式一：将指定的bin log文件，以 伪SQL的形式 表现
 *    show binlog events in 'atguigu-bin.000002';                    # 2、方式二：将指定的binlog日志文件，分成有效事件行的方式返回
 *     show binlog events in 'atguigu-bin.000002' from 391 limit 5    #    查询指定的bin log文件，从pos点391开始查起，查询五条
 * （2）使用日志恢复数据方式一
 *    flush logs                                               # 由于使用 bin log 恢复数据，还会执行数据库更新语句，所以需要将接下来的操作写入新的bin log文件中，故使用该命令
 *    show binlog events in 'atguigu-bin.000002';              # 将指定的binlog日志文件，分成有效事件行的方式返回，找出需要恢复的开始位置和结束位置
 *    /usr/bin/mysqlbinlog --starF-position='开始位置' --stop-position='结束位置' --database='数据库' 'big-log文件路径'  /usr/bin/mysql -uroot -pabc123 -v '数据库'                # 依据恢复的开始位置和结束位置 进行数据库恢复
 * （3）使用日志恢复数据方式二
 *    flush logs                                                 # 由于使用 bin log 恢复数据，还会执行数据库更新语句，所以需要将接下来的操作写入新的bin log文件中，故使用该命令
 *    mysqlbinlog -v "/var/lib/mysql/binlog/atguigu-bin.000002"  # 找出需要恢复数据的开始时间和结束时间
 *    /usr/bin/mysqlbinlog --start-datetime='开始时间' --stop-datetime="2022-01-85 15:40∶19" --database='数据库' 'big-log文件路径'  /usr/bin/mysql -uroot -pabc123 -v '数据库'    # 指定恢复数据库的起始时间点和结束时间点来恢复数据
 * （4）删除bin log
 *    PURGE {MASTER | BINARY} LOGS TO "指定日志文件名"             # 指定日志文件名前面的binlog日志全删除。。如binlog.000005，在binlog.000001~binlog.000004全被删除
 *    PURGE {MASTER | BINARY} LOGS BEFORE "指定日期"               # 指定时间之前的binlog全删除
 *    RESET MASTER;                                               # 删除所有的二进制日志文件
 * （5）其他场景
 * 二进制日志可以通过数据库的 全量备份 和二进制日志中保存的 增量信息 ，完成数据库的 无损失恢复 。
 * 但是，如果遇到数据量大、数据库和数据表很多（比如分库分表的应用）的场景，用二进制日志进行数据恢复，是很有挑战性的，因为起止位置不容易管理。
 * 在这种情况下，一个有效的解决办法是 配置主从数据库服务器 ，甚至是 一主多从 的架构，把二进制日志文件的内容通过 中继日志，同步到从数据库服务器中，这样就可以有效避免数据库故障导致的数据异常等问题。
 *
 * 【binlog与redo log对比】
 *  redo log 它是物理日志，记录内容是“在某个数据页上做了什么修改”，属于 InnoDB 存储引擎层产生的。
 *    binlog 是逻辑日志，记录内容是语句的原始逻辑，类似于“给 ID=2 这一行的 c 字段加 1”，属于MySQL Server 层。
 *  虽然它们都属于持久化的保证，但是侧重点不同。
 *    redo log 让InnoDB存储引擎拥有了崩溃恢复能力。
 *    binlog保证了MySQL集群架构的数据一致性
 *
 * 【两阶段提交】
 * 在执行更新语句过程，会记录redo log与binlog两块日志，以基本的事务为单位，redo log在事务执行过程中可以不断写入，而binlog只有在提交事务时才写入
 * 假设执行过程中写完redo log日志后，binlog日志写期间发生了异常，会出现什么情况呢?
 * 出现的问题，主库依据redo log完成了数据的提交（数据已更新），从库进行恢复 所依据的binlog里面没有对应的修改记录，从库会少一次更新，
 * 为了解决两份日志之间的逻辑一致问题，InnoDB存储引擎使用 两阶段提交 方案。
 * 原理很简单，将redo log的写入拆成了两个步骤 prepare和commit，这就是 两阶段提交。
 * 即在写入Binlog前，redo log都是处于prepare阶段，当写入binlog成功时，redo log变为commit阶段
 * 此时若在binlog日志写期间发生了异常，主库检测到redo log处于prepare阶段，就会对事务就行回滚。这就保证了主从库数据的一致性
 *
 * 【中继日志】
 *  中继日志只在主从服务器架构的从服务器上存在。从服务器为了与主服务器保持一致，要从主服务器读取二进制日志的内容，并且把读取到的信息写入本地的日志文件中，这个从服务器本地的日志文件就叫中继日志。
 *    从服务器读取中继日志，并根据中继日志的内容对从服务器的数据进行更新，完成主从服务器的数据同步。
 *  搭建好主从服务器之后，中继日志默认会保存在从服务器的数据目录下。
 *    从服务器名 -relay-bin.序号           # 中继日志的文件名格式
 *    从服务器名 -relay-bin.index          #  中继日志索引文件，用来定位当前正在使用的中继日志。
 *  恢复的典型错误：
 *   如果从服务器宕机，有的时候为了系统恢复，要重装操作系统，这样就可能会导致你的服务器名称与之前不同。
 *   而中继日志里是包含从服务器名的。在这种情况下，就可能导致你恢复从服务器的时候，无法从宕机前的中继日志里读取数据，以为是日志文件损坏了，其实是名称不对了。
 *   解决的方法也很简单，把从服务器的名称改回之前的名称。
 @author Alex
 @create 2023-05-27-15:26
 */
public class UB02 {
}
