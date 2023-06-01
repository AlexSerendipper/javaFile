package jdbc2;

import jdbc1.UCD02;
import jdbc1.UCD03;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 【事务的ACID(acid)属性】四大特性
 *  原子性（Atomicity）
 *   原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。
 *  一致性（Consistency）
 *   事务必须使数据库从一个一致性状态变换到另外一个一致性状态。
 *  隔离性（Isolation）：类似于线程安全问题，当多个用户同时操作表时，需要考虑隔离性事务的隔离性是指一个事务的执行不能被其他事务干扰，
 *   即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。
 *  持久性（Durability）
 *   持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来的其他操作和数据库故障不应该对其有任何影响
 *
 * 【隔离性】
 *  ✔数据库的并发问题
 *  （0）脏写：对于两个事务 T1, T2，如果事务T1修改了另一个 未提交事务T2修改过的数据，那就意味着发生了脏写.
 *  （1）脏读: 对于两个事务 T1, T2。 T1 读取了已经被 T2 更新但还没有被提交的字段。 T1读取的内容就是临时且无效的。（若T2回滚，数据库中数据其实并未改变，而T1读到的 却是改变的数据）
 *  （2）不可重复读(upgrade): 对于两个事务T1, T2。 T1 读取了一个字段, 然后 T2 更新了该字段（修改并提交）。之后, T1再次
 *       读取同一个字段（之前的流未关闭）, 但是值就不同了。
 *  （3）幻读(insert): 对于两个事务T1, T2, T1 从一个表中读取了一个字段, 然后 T2 在该表中插入了一些新的
 *       行。之后, 如果 T1 再次读取同一个表（之前的流未关闭）, 但是发现多出几行。我们把新插入的那些记录称之为 幻影记录 。
 *  注意：如果T2在该表中 不是插入新记录，而是删除一些记录，T1读取的记录变少了，这种现象不属于幻读，这相当于对每一条记录都发生了不可重复读的现象。幻读只是重点强调了读取到之前读取没有获取到的记录。
 *
 *【数据库的隔离等级】：不同的等级限制了不同的并发范围，实际上隔离等级越高，数据一致性就越好, 但并发性越弱。一般情况下，READ COMMITED就够了，不可重复读和幻读被认为是可以接受的
 *  所有的隔离级别都不允许脏写的情况发生！！！
 *    READ UNCOMMITED: 所有并发问题未解决
 *    READ COMMITED：只解决了脏读。oracle默认使用该隔离等级
 *    REPEATABLE READ：解决了脏读和不可重复读。MySQL默认使用该隔离等级
 *    SERIALIZABLE：解决了所有并发问题，一般不使用，影响性能。
 *
 * 【设置隔离等级】
 * （1）数据库层面
 *  每个数据库连接都有一个全局变量 @@tx_isolation, 表示当前的事务隔离级别
 *  SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;        # 设置数据库系统的全局的隔离级别:全局有效
 *   SHOW GLOBAL VARIABLES LIKE 'transaction_isolation';           # 查看当前的隔离级别:
 *  SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;       # 设置当前mySQL连接的隔离级别，当前会话有效
 *   SHOW VARIABLES LIKE 'transaction_isolation';                  # 查看当前的隔离级别:
 * （2）java层面
 *  Connection.getTransactionIsolation();                        # 1对应READ UNCOMMITED；2对应READ COMMITED；4对应REPEATABLE READ；
 *  Connection.setTransactionIsolation();                        # 设置当前连接的隔离等级。输入隔离等级对应的数字
 *
 * 【隔离的实现方式】事务的隔离性 最终是通过锁（乐观锁和悲观锁）实现，具体内容在mysql 以及 mybatisplus中叙述
 *
 @author Alex
 @create 2023-01-30-23:03
 */
public class UJ02 {
    // 数据库的并发问题演示，设置隔离级别分别为1
    // 取消默认提交且不关闭资源
    // 对表中CC进行查询后更新数据，然后再次查询，查询到的是更新后的数据，即没有解决不可重复读问题
    @Test
    public void test3() throws Exception{
        Connection connection = UCD02.getConnection();
        // 查看隔离级别
        connection.setTransactionIsolation(1);  // 可以看到这里设置1就出问题，设置2就不会有问题
        System.out.println("connection.getTransactionIsolation() = " + connection.getTransactionIsolation());
        // 取消默认提交
        connection.setAutoCommit(false);
        String sql = "select * from user_table where user = ?";
        User user = UJ01.getInstance(connection, User.class, sql, "CC");
        System.out.println(user);
    }

    // 对CC进行增删改
    @Test
    public void test4() throws Exception{
        Connection connection = UCD02.getConnection();
        // 取消默认提交
        connection.setAutoCommit(false);
        String sql = "update user_table set balance = ? where user = ?";
        UJ01.DDMop(connection, sql, 5000, "CC");
        Thread.sleep(15000);
        System.out.println("修改结束");
    }
}

class User {
    String user;
    String password;
    int balance;

    public User() {
    }

    public User(String user, String password, int balance) {
        this.user = user;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "user{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}