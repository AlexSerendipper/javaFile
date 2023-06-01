package index;

/**
 * 【数据库优化步骤概述】✔✔✔图见xmind
 *  首先在s1部分，我们需要观察服务器的状态是否存在周期性波动，如果存在周期性波动，有可能是周期性节点的原因，比如双十一、促销活动等，可以通过增加缓存（A1）这一步骤解决（即通常使用redis那一步骤）
 *   如果缓存策略没有解决，就需要开启慢查询，定位执行慢的SQL语句，并使用查询分析工具（EXPLAIN/show profile）进一步分析查询延迟和卡顿的原因
 *    如果是SQL等待时间长，我们可以 调优服务器的参数 ，比如适当增加数据库的缓冲池！！
 *    如果是SQL的执行时间长，我们可以考虑 是索引设计的问题 还是 查询关联的数据表过多，还是因为一些数据表的字段存在设计问题，在这些方面进行调整
 *   如果不是上述问题，我们就需要考虑是否是数据库自身的SQL查询性能已经达到了瓶颈，如果达到了性能瓶颈，我们可以考虑 增加服务器（采用读写分离架构） 或者对数据库进行分库分表（垂直分库、垂直分表、水平分表等..）
 *  总结：从效果来说 调整SQL语句和索引优化 > 调整数据表结构 > 调整系统配置（调优服务器参数） > 硬件调整（增加服务器等）
 *         而从成本来说 调整SQL语句和索引优化 < 调整数据表结构 < 调整系统配置（调优服务器参数） < 硬件调整（增加服务器等）
 *    即如果要对数据库进行优化，一定要优先考虑调整SQL语句和索引优化，因为它成本最低，效果最好
 *
 *
 * 【数据库调优】
 *  在数据库调优中，我们的目标是响应时间更快，吞吐量更大。。利用宏观的监控工具和微观的日志分析 可以帮助我们快速找到调优的思路
 *  SHOW [GLOBAL|SESSION] STATUS LIKE '参数';       # 查看系统性能参数，常用参数如下
 *    - Connections：                                              # 连接MySQL服务器的次数。
 *    - Uptime：                                                   # MySQL服务器的上线时间。
 *    - Slow_queries：                                             # 慢查询的次数。
 *    - Innodb_rows_read：                                         # Select查询返回的行数
 *    - Innodb_rows_inserted：                                     # 执行INSERT操作插入的行数
 *    - Innodb_rows_updated：                                      # 执行UPDATE操作更新的行数
 *    - Innodb_rows_deleted：                                      # 执行DELETE操作删除的行数
 *    - Com_select：                                               # 查询操作的次数。
 *    - Com_insert：                                               # 插入操作的次数。对于批量插入的 INSERT 操作，只累加一次。
 *    - Com_update：                                               # 更新操作的次数。
 *    - Com_delete：                                               # 删除操作的次数
 *  SHOW STATUS LIKE 'last_query_cost';;                          # 统计SQL的查询成本
 *    - 可以在执行完某条SQL语句后，通过查看 last_query_cost变量值来得到当前查询的成本。它通常也是我们 评价一个查询的执行效率的一个常用指标。这个查询成本对应的是 SQL语句所需要读取的页的数量 。
 *    - 当我们查询 id=900001 的记录，以及查询 id 在 900001 到 9000100 之间的学生记录。然后看查询成本，发现虽然页的数量是刚才的 20 倍，但是查询的效率并没有明显的变化（查询的时间基本相同）
 *      原因是：位置决定效率。如果页就在数据库缓冲池中，那么效率最高，否则还需要从内存或者磁盘中进行读取，效率就低的多。
 *              批量决定效率。如果我们从磁盘中对单一页进行随机读，那么效率是很低的（差不多10ms），而采用顺序读取的方式，批量对页进行读取，平均一页的读取效率就会提升很多，甚至要快于单个页面在内存中的随机读取。
 *
 * 【慢查询日志】
 *  MySQL的慢查询日志，用来记录在MySQL中响应时间超过阈值的语句，具体指运行时间超过 long_query_time 的值的SQL，则会被记录到慢查询日志中。long_query_time的默认值为10，意思是运行10秒以上（不含10秒）的语句，认为是超出了我们的最大忍耐时间值。
 *   它的主要作用是，帮助我们发现那些执行时间特别长的SQL查询语句，并且有针对性地进行优化（结合explain进行全面分析），从而提高系统的整体效率
 *   默认情况下，MySQL数据库没有开启慢查询日志，需要我们手动来设置这个参数。如果不是调优需要的话，一般不建议启动该参数，因为开启慢查询日志会或多或少带来一定的性能影响。
 *  SET GLOBAL slow_query_log = on                                # 开启慢查询日志
 *  SHOW VARIABLES LIKE '%slow%'                                  # 查看慢查询日志所在目录
 *  SHOW GLOBAL STATUS LIKE '%Slow_queries%';                     # 查看慢查询日志相关信息（是否开启/条数）
 *  SHOW variables like 'min%';                                   # 这个值默认是0（通常不做修改）。与long_query ]ime=10合在一起，表示只要查询的执行时间超过10秒钟，哪怕一个记录也没有扫描过，都要被记录到慢查询日志中。
 *  SET GLOBAL long_query_time = 1                                # 修改超时时间（仅新建会话有效）
 *   SET long_query_time = 1                                        # 修改超时时间（当前会话有效）
 *  mysqladmin -root -p flush-logs slow                           # 创建一个新的慢查询日志文件。一旦执行了这个命令，慢查询日志都只存在于新的日志文件中（旧的查询日志需要备份好 或是删除）
--------------------------
[mysqld]
slow_query_log=ON                                                  # 配置文件 开启慢查询日志的开关
slow.query_log_file=/var/lib/mysq1/atguigu-slow.log                # 配置文件 修改慢查询日志的目录和文件名信息
long.query_time=3                                                  # 配置文件 设置慢查询的阈值为3秒，超出此设定值的SQL即被记录到慢查询日志
log_output=FILE
-------------------------
 *
 * 【慢查询日志分析工具】mysqldumpslow，可以查看到具体的SQL语句
-------------------------
 mysqldumpslow 命令的具体参数如下：
 -a: 不将数字抽象成N，字符串抽象成S
 -s: 是表示按照何种方式排序：
     c: 访问次数
     l: 锁定时间
     r: 返回记录
     t: 查询时间
     al:平均锁定时间
     ar:平均返回记录数
     at:平均查询时间 （默认方式）
     ac:平均查询次数
 -t: 即为返回前面多少条的数据；
 -g: 后边搭配一个正则匹配模式，大小写不敏感的

 mysqldumpslow 命令使用举例
mysqldumpslow -s r -t 10 /var/lib/mysql/atguigu1-slow.log                        #得到返回记录集最多的10个SQL
mysqldumpslow -s c -t 10 /var/lib/mysql/atguigu1-slow.log                        #得到访问次数最多的10个SQL
mysqldumpslow -s t -t 10 -g "left join" /var/lib/mysql/atguigu1-slow.log         #得到按照时间排序的前10条里面含有左连接的查询语句
mysqldumpslow -s r -t 10 /var/lib/mysql/atguigu-slow.log | more                  #另外建议在使用这些命令时结合 | 和more 使用 ，否则有可能出现爆屏情况
-------------------------
 *
 * 【SQL执行流程可视化分析】分析出具体的SQL语句是在哪一个环节执行慢....
 *   set profiling = 1                                # 设置记录SQL执行过程中的细节
 *   SHOW PROFILES                                    # 查看近期执行的SQL语句
 *   SHOW PROFILE FOR QUERY 4                         # 查看具体某次SQL语句的细节
 *   SHOW PROFILE cpu,block io FOR QUERY 4            # 查看具体某次SQL语句的细节, 额外查询cpu io阻塞等参数情况
-------------------------
 show profile的常用查询参数：
① ALL：显示所有的开销信息。
② BLOCK IO：显示块IO开销。
③ CONTEXT SWITCHES：上下文切换开销。
④ CPU：显示CPU开销信息。
⑤ IPC：显示发送和接收开销信息。
⑥ MEMORY：显示内存开销信息。
⑦ PAGE FAULTS：显示页面错误开销信息。
⑧ SOURCE：显示和Source_function，Source_file，Source_line相关的开销信息。
⑨ SWAPS：显示交换次数开销信息。
 日常开发需注意的结论: 如果在show profile诊断结果中出现了以上4条结果中的任何一条，则sql语句需要优化。
① converting HEAP to MyISAM:查询结果太大，内存不够，数据往磁盘上搬了。
② Creating tmp table:创建临时表。先拷贝数据到临时表，用完后再删除临时表。
③ Copying to tmp table on disk:把内存中临时表复制到磁盘上，警惕!
④ locked。
 SHOW PROFILE命令将被弃用，我们可以从information_schema中的profiling数据表进行查看。
-------------------------
 *
 *
 @author Alex
 @create 2023-05-20-10:46
 */
public class UI10 {
}
