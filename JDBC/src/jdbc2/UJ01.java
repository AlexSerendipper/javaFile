package jdbc2;

import jdbc1.UCD02;
import jdbc1.UCD03;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 【事务】transaction
 *  事务：指一组逻辑操作单元，使数据从一种状态变换到另一种状态。
 *     一组逻辑操作单元：即一个或多个DML操作(增删改)。如aa向bb转账，我们需要写两行sql语句修改aa和bb的账户，这两行语句构成一个事物
 *  事务处理原则：即原子性，要保证所有事务都作为一个工作单元来执行，当在一个事务中执行多个操作时， 要么所有的事务
 *   都被提交(commit)，要么数据库管理系统将放弃所作的所有修改，整个事务回滚(rollback)到最初状态。
 *   如aa向bb转账，当aa转账后出现网络故障，bb没有收到钱，事务应当回滚。
 *  为确保数据库中数据的一致性,数据的操纵应当是离散的成组的逻辑单元（即事务），并遵守事务的处理原则
 *
 * 【事务处理】
 *  当一个连接对象被创建时，默认情况下是自动提交事务：即每次执行一个SQL语句时，如果执行成功，就会向数据库自动提交，而不能回滚
 *    注意，DDL操作一旦执行，就自动提交且无法更改。DDM操作一旦执行就默认提交，但可以更改
 *    默认在关闭连接时，会将没有提交的数据自动提交
 *  为了让多个 SQL 语句作为一个事务执行：
 *   (1)  Connection.setAutoCommit(false); 获取链接后取消自动提交事务
 *  （2） Connection.commit(); 在所有的 SQL 语句都成功执行后，调用 commit()方法提交事务
 *  （3） Connection.rollback(); 在出现异常时，调用 rollback(); 方法回滚事务
 *  （4） 统一关闭Connection（建议先恢复其自动提交状态，主要针对数据库连接池时，我们从连接池中拉取的connection是要归还连接池的）
 *
 @author Alex
 @create 2023-01-30-20:31
 */
public class UJ01 {
    // 未考虑事务的转账操作
    @Test
    public void test1() throws Exception{
        // 调用上篇中的通用DDM操作，实现aa向bb转账100元
        String sql1 = "update user_table set balance = balance-100 where user = ?";
        String sql2 = "update user_table set balance = balance+100 where user = ?";
        int i1 = UCD03.DDMopertor(sql1,"AA");  // 每次执行都会关闭流，导致提交数据
        int i2 = UCD03.DDMopertor(sql2,"BB");
    }

    // ✔✔✔DML通用操作（考虑事务）
    // 关键在于传入连接，sdl执行后不关闭连接
    public static int DDMop(Connection connection,String sql,Object...args) throws SQLException {
        PreparedStatement ps = null;
        try {
            // 预编译
            // 填充占位符
            ps = connection.prepareStatement(sql);
            // sql中占位符的个数，应该与可变形参的长度相同
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            // 执行sql
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally {
            // 关闭资源
            UCD03.closeResourse(null,ps);
        }
    }

    // ✔✔✔通用查询操作（考虑事务），返回多个对象
    // 关键在于传入连接，sdl执行后不关闭连接
    public static <T> List<T> query(Connection connection, Class<T> clazz, String sql, Object...args) throws Exception{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        // 执行，并返回结果集
        ResultSet resultSet = ps.executeQuery();
        // 处理结果集
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 由于不知道取出的数据是几个，对应类的哪一个属性，所以需要把对象先造出来
        // 当查询结果集有多条记录时，把if改成while
        ArrayList<T> arrayList = new ArrayList<>();
        while(resultSet.next()){
            T t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = resultSet.getObject(i+1);
                String columnLabel = metaData.getColumnLabel(i + 1);
                // 通过反射，为customer对象中指定columnName属性赋值
                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(t,columnValue);
            }
            arrayList.add(t);
//            new UCD03().closeResourse(connection,ps,resultSet);
//            return arrayList;
        }
        UCD03.closeResourse(null,ps,resultSet);
        return arrayList;
    }

    // ✔✔✔通用查询操作（考虑事务），返回单个对象
    // 关键在于传入连接，sdl执行后不关闭连接
    public static <T> T getInstance(Connection connection,Class<T> clazz,String sql,Object...args) throws Exception{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        // 执行，并返回结果集,仅查一次
        ResultSet resultSet = ps.executeQuery();
        // 处理结果集（难点：获取结果集的列数）
        // 列数封装在结果集的元数据当中
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 由于不知道取出的数据是几个，对应类的哪一个属性，所以需要把对象先造出来
        if(resultSet.next()){
            T t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = resultSet.getObject(i+1);
                String columnLabel = metaData.getColumnLabel(i + 1);
                // 通过反射，为customer对象中指定columnName属性赋值
                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(t,columnValue);
            }
            UCD03.closeResourse(null,ps,resultSet);
            return t;
        }
        UCD03.closeResourse(null,ps,resultSet);
        return null;
    }

    // 考虑事务的转账操作
    // 取消自动提交事务
    @Test
    public void test2() throws SQLException {
        Connection connection = null;
        try {
            connection = UCD02.getConnection();
            // 取消数据的自动提交
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance-100 where user = ?";
            // 模仿转账过程中出现异常
            System.out.println(10/0);
            String sql2 = "update user_table set balance = balance+100 where user = ?";
            int i1 = DDMop(connection, sql1, "AA");
            int i2 = DDMop(connection, sql2, "BB");
            connection.commit();  // 要么提交
            System.out.println("转账成功");
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();  // 要么出现异常回滚
        } finally {
            // 建议先恢复其自动提交状态
            connection.setAutoCommit(true);
            // 统一关闭连接资源
            UCD03.closeResourse(connection,null);
        }

    }
}



