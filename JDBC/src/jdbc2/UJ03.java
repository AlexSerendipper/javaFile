package jdbc2;

import jdbc1.UCD03;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 【DAO】初级
 *  DAO类：作为抽象基类，即把我们之前学习的通用操作进行封装
 *  实际上后续若使用JDBC，都已经被框架集成，但框架底层还是这些逻辑！仍需要掌握
 *  使用流程：（1）通常会有一个接口，规定了针对具体某个表的规范
 *            （2）创建某个表的实现类，让其继承DAO类，实现设定的规范
 *
 @author Alex
 @create 2023-01-31-10:17
 */

// DAO类
public abstract class UJ03{
    // (1) DML(增删改)+DDL通用操作（考虑事务）
    public static int DDMop(Connection connection, String sql, Object...args) throws Exception {
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

    // (2) 通用查询操作（考虑事务），返回多个对象
    public <T> List<T> query(Connection connection, Class<T> clazz, String sql, Object...args) throws Exception{
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

    // (3) 通用查询操作（考虑事务），返回单个对象
    public <T> T getInstance(Connection connection,Class<T> clazz,String sql,Object...args) throws Exception{
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

    // (4) 针对聚合函数查询（查询结果为一行一列的情况），没法封装到类中！所以之前的方法不适用
    // 其实应该写返回Object类型的，但是要强转。
    // ✔写泛型更方便，直接return就行（因为此处返回类型为方法调用者的类型，即return指明类型）
    public <E> E getValue(Connection connection,String sql,Object...args) throws Exception{
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            // 执行，并返回结果集,仅查一次
            resultSet = ps.executeQuery();
            // 处理结果集
            if(resultSet.next()){
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UCD03.closeResourse(null,ps,resultSet);
        }
        return null;
    }
}

// 此接口用于规范customers表的常用操作
interface CustomerDAO{
    // 将customer对象添加到数据库中
    void insert(Connection connection, customer cust) throws Exception;

    // 根据指定的ID删除表中的记录
    void deleteById(Connection connection,int id) throws Exception;

    // 根据cust对象的ID，修改数据表中指定的记录
    void update(Connection connection,customer cust) throws Exception;

    // 根据指定的ID查询表中的数据
    customer getCustomerById(Connection connection,int id) throws Exception;

    // 查询表中的所有数据
    List<customer> getAll(Connection connection) throws Exception;

    // 查询表中有多少条记录
    long getCount(Connection connection) throws Exception;

    // 返回数据表中最大的生日
    Date getMaxBirth(Connection connection) throws Exception;

}

// 针对于customers表的具体实现类
class CustomerDAOimp extends UJ03 implements CustomerDAO{
    @Override
    public void insert(Connection conn, customer cust) throws Exception {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        DDMop(conn,sql,cust.name,cust.email,cust.birth);
    }

    @Override
    public void deleteById(Connection conn, int id) throws Exception{
        String sql = "delete from customers where id=?";
        DDMop(conn,sql,id);
//        UCD03.closeResourse(conn,null);
    }

    @Override
    public void update(Connection conn, customer cust) throws Exception{
        String sql = "update customers set name=?,email=?,birth=? where id=?";
        DDMop(conn,sql,cust.name,cust.email,cust.birth,cust.id);
    }

    @Override
    public customer getCustomerById(Connection conn, int id) throws Exception {
        String sql = "select name,email,birth from customers where id=?";
        customer cust = getInstance(conn, customer.class, sql, id);
        return cust;
    }

    @Override
    public List<customer> getAll(Connection conn) throws Exception {
        String sql = "select name,email,birth from customers";
        List<customer> custs = query(conn, customer.class, sql);
        return custs;
    }

    @Override
    public long getCount(Connection conn) throws Exception {
        String sql = "select count(*) from customers";
        long value = (long)getValue(conn, sql);  // 返回的是object类型的对象，实际上直接return，由于使用了泛型，自动帮你完成强转
        return value;
    }

    @Override
    public Date getMaxBirth(Connection conn) throws Exception {
        String sql = "select max(birth) from customers";
        return getValue(conn, sql);
    }
}

class customer{
    int id;
    String name;
    String email;
    Date birth;

    public customer(int id, String name, String email, java.util.Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }


    public customer() {
    }

    public customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}