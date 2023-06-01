package transaction;

/**
 * 【数据库事务】
 *  事务是数据库区别于文件系统的重要特性之一，当我们有了事务就会让数据库始终保持 一致性✔✔✔，同时我们还能通过事务的机制 恢复到某个时间点，这样可以保证已提交到数据库的修改不会因为系统崩溃而丢失。
 *
 * 【事务相关内容见JDBC处】
 *  事务的含义
 *  事务的四大特性：ACID
 *  事务的隔离性 与 隔离级别，以及如何设置
 *
 * 【事务的状态】
 *  活动的（active）：事务对应的数据库操作正在执行过程中时，我们就说该事务处在活动的状态。
 *  部分提交的（partially committed）：当事务中的最后一个操作执行完成，但由于操作都在内存中执行，所造成的影响并没有刷新到磁盘时，我们就说该事务处在部分提交的状态。
 *  失败的（failed）：当事务处在活动的或者部分提交的状态时，可能遇到了某些错误（数据库自身的错误、操作系统错误或者直接断电等）而无法继续执行，或者人为的停止当前事务的执行，我们就说该事务处在失败的状态。
 *  中止的（aborted）：如果事务执行了一部分而变为失败的状态，那么就需要把已经修改的事务中的操作还原到事务执行前的状态。换句话说，就是要撤销失败事务对当前数据库造成的影响。我们把这个撤销的过程称之为回滚。当回滚操作执行完毕时，也就是数据库恢复到了执行事务之前的状态，我们就说该事务处在了中止的状态。
 *  提交的（committed）：当一个处在部分提交的状态的事务将修改过的数据都同步到磁盘上之后，我们就可以说该事务处在了提交的状态。
 *
 * 【事务的使用】
 *  ✔set autocommit = true;                      # autocommit 默认开启状态，在此状态下每条DML语句都是一个独立的事务
 *  ✔注意1：即使在autocommit为true的情况下，使用start transaction或begin开启事务，那么DML操作也不会自动提交数据
 *  注意2：即使关闭 autocommit 时，① 使用DDL操作
 *                                  ② 使用ALTER USER、CREATE USER、DROP USER、GRANT、RENAME USER、REVOKE、SET PASSWORD 等语句时
 *                                  ③ 手动把autocommit的值调为ON时
 *                                  ④ 使用LOCK TABLES、UNLOCK TABLES等关于锁定的语句
 *                                  ⑤ 使用 LOAD DATA 语句来批量往数据库中导入数据
 *                                  ⑥ 关于MySQL复制的一些语句，如：START SLAVE、STOP SLAVE、RESET SLAVE、CHANGE MASTER TO 等语句时
 *                                  ⑦ 其它的一些语句。如 ANALYZE TABLE、CACHE INDEX、CHECK TABLE、FLUSH、LOAD INDEX INTO CACHE、OPTIAIZE TABLE、REPAIR TABLE、RESET 等语句
 *  都也会隐式的提交前边语句所属于的事务。
---------------------------------
（1）START TRANSACTION或者BEGIN，显式开启一个事务。
    START TRANSACTION语句相较于BEGIN特别之处在于，后边能跟随几个修饰符：
    ① READ ONLY：标识当前事务是一个只读事务，也就是属于该事务的数据库操作只能读取数据，而不能修改数据。
    ② READ WRITE(默认)：标识当前事务是一个读写事务，也就是属于该事务的数据库操作既可以读取数据，也可以修改数据。
    ③ WITH CONSISTENT SNAPSHOT：启动一致性读。
（2）系列事务中的操作（主要是DML，不含DDL）
     SAVEPOINT s1;                               # 可以在DML操作中设置保存点，用于回滚
（3）提交事务(commit) 或 rollback（即中止，回滚事务）
     ROLLBACK TO [SAVEPOINT]                     # 可以在事务中创建保存点，方便后续针对保存点进行回滚。一个事物中可以存在多个保存点。
----------------------------------
 *  在事务开始之前设置completion.type 、
 *  SET @@completion.type = 0，这是 默认情况。当我们执行COMMIT的时候会提交事务，在执行下一个事务时，还需要使用 START TRANSACTION 或者 BEGIN 来开启。
 *  SET @@completion.type = 1，这种情况下，当我们提交事务后，相当于执行了COMMIT AND CHAIN，也就是开启一个链式事务，即当我们提交事务之后会开启一个相同隔离级别的事务（不需要再写一个begin）。
 *  SET @@completion.type = 2，这种情况下 CONMIT=COMMIT AND RELEASE，也就是当我们提交后，会自动与服务器断开连接。
 *
 *
 * 【事务的常见分类】
 *   扁平事务（Flat Transactions）
 *    由BEGIN WORK开始，由COMMIT WORK或ROLLBACK WORK结束的 最普通的事务
 *   带有保存点的扁平事务（Flat Transactions with Savepoints）
 *    扁平事务中使用了保存点savepoint
 *   链事务（Chained Transactions）
 *    链事务 是指一个事务由多个子事务链式组成，它可以被视为保存点模式的一个变种。
 *    带有保存点的扁平事务能回滚到任意正确的保存点，而链事务中的回滚仅限于当前事务，即只能恢复到最近的一个保存点。
 *   嵌套事务（Nested Transactions）
 *    是一个层次结构框架，由一个顶层事务(Top-Level Transaction）控制着各个层次的事务，顶层事务之下嵌套的事务被称为子事务(Subtransaction)，其控制着每一个局部的变换，
 *    子事务本身也可以是嵌套事务。因此，嵌套事务的层次结构可以看成是一棵树。
 *   分布式事务（Distributed Transactions）
 *    通常是在一个分布式环境下运行的扁平事务
 @author Alex
 @create 2023-05-22-13:10
 */
public class UT01 {
}
