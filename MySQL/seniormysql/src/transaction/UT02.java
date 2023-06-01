package transaction;

/**
 * 【MySQL的事务日志】
 *  事务有4种特性：原子性、一致性、隔离性和持久性。到底是基于什么机制实现呢？
 *  事务的隔离性由锁机制实现。
 *  事务的原子性、一致性和持久性由事务的 redo日志和undo日志 来保证。
 *   REDO LOG 称为重做日志，提供再写入操作，恢复提交事务修改的页操作，用来保证事务的持久性。
 *   UNDO LOG 称为回滚日志，回滚行记录到某个特定版本，用来保证事务的原子性、一致性。
 *  UNDO似乎是REDO的逆过程，其实不然。REDO和UNDO都是一种 恢复操作，他们具体的区别如下：
 *   redo log：是存储引擎层(innodb)生成的日志，记录的是"物理级别"上的页修改操作，比如页号xx、偏移量yyy写入了'zzz'数据。主要为了保证数据的可靠性;
 *   undo log：是存储引擎层(innodb)生成的日志，记录的是 逻辑操作 日志，比如对某一行数据进行了INSERT语句操作，那么undo log就记录一条与之相反的DELETE操作。主要用于 事务的回滚(undo log记录的是每个修改操作的 逆操作 )和 一致性非锁定读(undo log回滚行记录到某种特定的版本---MVCC，即多版本并发控制)。
 *
 * 【redo日志】
 *  为什么需要REDO日志：innoDB存储引擎是以 页为单位 来管理存储空间的。在真正访问页面之前，需要把在 磁盘 上的页缓存到内存中的 Buffer Pool 之后才可以访问。
 *                      所有的变更都必须先更新缓冲池中的数据，然后缓冲池中的 脏页 会以一定的频率被刷入磁盘（checkPoint机制），通过缓冲池来优化CPU和磁盘之间的鸿沟。
 *                      所以最坏的情况就是事务提交后，刚写完缓冲池，数据库宕机了，那么这段数据就是丢失的，无法恢复。
 *                      ✔InnoDB引擎的事务采用了WAL技术(Write-Ahead Logging )，这种技术的思想就是 先写日志，再写磁盘，只有日志写入成功，才算事务提交成功，这里的日志就是redo log。
 *                      当发生宕机且数据未刷到磁盘的时候，可以通过redo log来恢复，保证ACID中的D，这就是redo log的作用✔✔✔。
 *  好处：
 *    redo日志降低了刷盘频率
 *    redo日志占用的空间非常小：存储表空间ID、页号、偏移量以及需要更新的值，所需的存储空间是很小的，刷盘快。
 *  特点
 *    redo日志是顺序写入磁盘的
 *    事务执行过程中，redo log不断记录
 *      redo log 跟bin log的区别，redo log是 存储引擎层 产生的，而bin log是 数据库层 产生的。假设一个事务，对表做10万行的记录插入，在这个过程中，一直不断的往redo log顺序记录，而bin log不会记录，直到这个事务提交，才会一次写入到bin log文件中。
 *
 * 【Redo日志的工作流程】
 *  可以简单分为以下两个部分：重做日志的缓冲 和 重做日志文件，
 *    重做日志的缓冲 (redo log buffer)，保存在内存中，是易失的。
 *    重做日志文件 (redo log file)，保存在硬盘中，是持久的。REDO日志文件默认在数据库的根路径下，其中的 ib_logfile8和 ib_logfile1即为REDO日志。
 * （1）先将原始数据从磁盘中读入内存中来，修改数据的内存拷贝
 * （2）生成一条重做日志并写入redo log buffer，记录的是数据被修改后的值
 * （3）刷盘：当事务commit时，将redo log buffer中的内容刷新到 redo log file，对 redo log file采用追加写的方式
 * （4）定期将内存中修改的数据刷新到磁盘
 *
 *  刷盘：InnoDB引擎会在写redo log的时候先写redo log buffer，之后以一定的频率 刷入到真正的redo log file中
 *    set global innodb_flush_log_at_trx_commit=0                # 表示每次事务提交时不进行刷盘操作。（系统默认master thread每隔1s进行一次重做日志的同步，即先同步到page cache中后，再同步给redo log file）
 *    set global innodb_flush_log_at_trx_commit=1                # 表示每次事务提交时都将进行同步，刷盘操作（默认值）
 *    set global innodb_flush_log_at_trx_commit=2                # 表示每次事务提交时都只把 redo log buffer 内容写入 page cache，不进行同步。由os自己决定什么时候同步到磁盘文件
 *   在一次性插入多条数据（如一次性插入3万条数据）时，可以将innodb_flush_log_at_trx_commit为0或2来提高事务提交的性能，但需清楚，这种设置方法丧失了事务的ACID特性。
 *
 * 【redo日志的底层原理】了解，与课程的171节...以后有需要再阅读
 *
 *
 @author Alex
 @create 2023-05-22-15:46
 */
public class UT02 {
}
