package transaction;

/**
 * 【锁概述】
 *  事务的隔离性是由锁来实现的
 *  锁是计算机协调多个进程或线程 并发访问某一资源 的机制。在程序开发中会存在多线程同步的问题，当多个线程并发访问某个敏感数据的时候，我们可能需要保证这个数据在任何时刻 最多只有一个线程在访问，
 *   保证数据的 完整性 和 一致性，需要对并发操作进行控制，因此产生了锁。同时锁机制也为实现MySQL的各个隔离级别提供了保证。
 *   锁冲突 也是影响数据库并发访问性能的一个重要因素。所以锁对数据库而言显得尤其重要，也更加复杂。
 *  并发事务访问相同记录大致可以划分为3种：
 *（1）读-读情况：并发事务相继读取相同的记录。读取操作本身不会对记录有任何影响，并不会引起什么问题，所以允许这种情况的发生。
 *（2）写-写情况：这种情况下会发生脏写的问题，任何一种隔离级别都不允许这种问题发生。所以在多个未提交事务相继对一条记录做改动时，需要让它们排队执行，这个排队的过程其实是通过锁来实现的。这个所谓的锁其实是一个 内存中的结构。
 *              当一个事务想对这条记录做改动时，首先会看看内存中有没有与这条记录关联的 锁结构 ，当没有的时候就会在内存中生成一个 锁结构 与之关联
 *              锁结构中，比较重要的属性有（trx信息：代表这个锁结构是哪个事务生成的。is_waiting ：代表当前事务是否在等待）、
 *              ① 当事务T1改动了这条记录后，就生成了一个锁结构与该记录关联，因为之前没有别的事务为这条记录加锁，所以 is_waiting 属性就是 false ，我们把这个场景就称之为 获取锁成功，或者 加锁成功，然后就可以继续执行操作了。
 *              ② 在事务 T1 提交之前，另一个事务 T2 也想对该记录做改动，那么先看看有没有锁结构与这条记录关联，发现有一个锁结构与之关联后，然后也生成了一个锁结构与这条记录关联，不过锁结构的 is_waiting 属性值为 true ，表示当前事务需要等待，我们把这个场景就称之为 获取锁失败，或者 加锁失败，图示：
 *              ③ 在事务T1提交之后，就会把该事务生成的 锁结构释放掉，然后看看还有没有别的事务在等待获取锁，发现了事务T2还在等待获取锁，所以把事务T2对应的锁结构的is_waiting属性设置为false，然后把该事务对应的线程唤醒，让它继续执行，此时事务T2就算获取到锁了。
 *（3）读-写或写-读情况： 即一个事务进行读取操作，另一个进行改动操作。这种情况下可能发生脏读、不可重复读、幻读的问题。
 *                        各个数据库厂商对 SQL标准 的支持都可能不一样。比如MySQL在 REPEATABLE READ 隔离级别上就已经解决了 幻读 问题。
 *
 * 【并发问题的解决方案】主要就是针对一个是读一个是写的情况，因为脏写是无论如何都不被允许的，
 *  方案一：读操作利用多版本并发控制（MVCC，下章讲解），写操作进行加锁。
 *   所谓的MVCC，就是生成一个ReadView，通过ReadView找到符合条件的记录版本（历史版本由undo日志构建）。
 *   查询语句只能读到在生成ReadView之前已提交事务所做的更改，在生成ReadView之前未提交的事务或者之后才开启的事务所做的更改是看不到的。
 *   而写操作肯定针对的是最新版本的记录，所以使用加锁的方式
 *  在READ COMMITTED隔离级别下，一个事务在执行过程中每次执行SELECT操作时都会生成一个ReadView，ReadView的存在本身就保证了事务不可以读取到未提交的事务所做的更改，也就是避免了脏读现象；
 *  在REPEATABLE READ隔离级别下，一个事务在执行过程中只有第一次执行SELECT操作才会生成一个ReadView，之后的SELECT操作都复用这ReadView，这样也就避免了不可重复读和幻读的问题。
 *
 *  方案二：读、写操作都采用加锁的方式。
 *   如果我们的一些业务场景不允许读取记录的旧版本，而是每次都必须去 读取记录的最新版本（如：读取银行存款，直到本次存款事务执行完成，其他事务才可以访问账户的余额）。
 *   这样在读取记录的时候就需要对其进行 加锁 操作，这样也就意味着读操作和写操作也像 写-写 操作那样 排队 执行
 *   读写都加锁在解决脏读和幻读上是毋庸置疑的，在解决幻读上还存在着问题，就是对于新增的记录（幻影记录），在当前事务第一次读取时并不存在，所以读取的时候没法加锁~   这个问题通过后边的间隙锁来解决
 *  采用MVCC方式的话，读-写操作彼此并不冲突，性能更高。
 *  采用加锁方式的话，读-写操作彼此需要排队执行，影响性能。
 *
 * 【锁分类1：根据数据的操作类型】
 * （1）共享锁(Shared Lock，S Lock✔)， 也称为读锁(readlock)， 但是实际上读操作也可以加排他锁
 *  共享锁针对同一份数据，多个事务的读操作可以同时进行而不会互相影响，相互不阻塞的。
 *  为什么也称为读锁，是因为加了共享锁后，只能读取，不能写
 *   但是对于读操作而言，实际上可以加共享锁，也可以加排他锁
 * （2）排他锁（Exclusive Lock，X Lock✔)， 也称为写锁(write lock)。
 *  排他锁，在当前写操作没有完成前，会阻断其他写锁和读锁。这样就能确保在给定的时间里，只有一个事务能执行写入，并防止其他用户读取正在写入的同一资源。
 *   ✔对于写操作（DELETE、UPDATE、INSERT），innodb会自动将涉及的数据集添加排他锁（具体来看INSERT操作使用的隐式锁）
 *   ✔对于读操作（SELECT）,就需要指明使用S锁还是X锁
 *    SELECT ... LOCK IN SHARE MODE;             # 对读取的记录加S锁
 *      SELECT ... FOR SHARE;                     # 对读取的记录加S锁, mysql 8.0新增语法
 *    SELECT ... FOR UPDATE;                     # 对读取的记录加X锁：
 *    SELECT ... FOR UPDATE nowait;              # 对读取的记录加X锁，如果读取的数据已经加锁，则立刻报错返回
 *     SELECT ... FOR UPDATE skip locked          # 对读取的记录加X锁，只返回结果中没被加锁的行
 *
 @author Alex
 @create 2023-05-24-10:49
 */
public class UT04 {

}
