package transaction;

/**
 * 【Undo日志】
 *  redo log是事务持久性的保证，undo log是事务原子性的保证✔✔✔。在事务中更新数据（DML操作，除了查询）的前置操作其实是要先写入一个undo log。
 *  你插入一条记录时，至少要把这条记录的主键值记下来，之后回滚的时候只需要把这个主键值对应的记录删掉就好了。(对于每个INSERT，InnoDB存储引擎会完成一个DELETE)
 *   你删除了一条记录，至少要把这条记录中的内容都记下来，这样之后回滚时再把由这些内容组成的记录插入到表中就好了。(对于每个DELETE，InnoDB存储引擎会执行一个INSERT)
 *   你修改了一条记录，至少要把修改这条记录前的旧值都记录下来，这样之后回滚时再把这条记录更新为旧值就好了。(对于每个UPDATE，InnoDB存储引擎会执行一个相反的UPDATE，将修改前的行放回去)
 *   MySQL把这些为了回滚而记录的这些内容称之为 撤销日志 或者 回滚日志(即undo log )。注意，由于查询操作(SELECT）并不会修改任何用户记录，所以在查询操作执行时，并不需要记录 相应的undo日志。
 *   此外，undo log 会产生redo log，也就是undo log的产生会伴随着redo log的产生，这是因为undo log也需要持久性的保护。
 *  Undo日志的作用
 *    回滚数据
 *     undo用于将数据库物理地恢复到执行语句或事务之前的样子。但事实并非如此。undo是逻辑日志，因此只是将数据库逻辑地恢复到原来的样子。所有修改都被逻辑地取消了，但是数据结构和页本身在回滚之后可能大不相同。
 *     比如在新增了一条数据后开辟了新的一页进行存储数据，在rollback后，新增的数据会被逻辑删除，但是新开辟的页并不会被删除
 *    MVCC（详情看第16章）
 *     undo的另一个作用是MVCC，即在InnoDB存储引擎中MVCC的实现是通过undo来完成。当用户读取一行记录时，若该记录已经被其他事务占用，当前事务可以通过undo读取之前的行版本信息，以此实现非锁定读取。
 *
 * 【redo log、undo log 工作流程概述】
-----------------------
1. start transaction;
2．记录A=1到undo log;                 # 记录原先的值
3. update A = 3;
4. 记录A=3 到redo log;
5．记录B=2到undo log;
6. update B = 4;
7．记录B =4到redo log;
8．将redo log刷新到磁盘               # 这里说的是系统默认每隔一段时间将redo log刷新到磁盘的情况
9. commit
若在1-8步骤的任意一步系统宕机，事务未提交，该事务就不会对磁盘上的数据做任何影响。
如果在8-9之间宕机，系统恢复之后，由于此时redo log已经持久化，可以选择回滚，也可以选择继续完成事务提交
若在9之后系统宕机，并且导致了内存中变更的数据还来不及刷回磁盘，那么系统恢复之后，可以根据redo log把数据刷回磁盘
-----------------------
 *
 * 【undo日志的底层原理】
 * 包括 undo的存储结构、 undo的类型、undo log的生命周期、undo log详细生成过程
 * 具体分析于172集
 *
 @author Alex
 @create 2023-05-23-15:22
 */
public class UT03 {
}
