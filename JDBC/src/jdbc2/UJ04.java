package jdbc2;

import jdbc1.UCD03;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 【DAO类】高级
 *  DAO类的查询操作，我们仍然需要传入customer类，但实际上我们提供的是针对于customer类的实现类，故不希望传入此类参数
 *  通过采用泛型 配合 this实现
 *    由于使用了类泛型，将查询的泛型方法改为使用泛型的普通方法。并在CustomerDAOimp2中指明泛型参数
 *    CustomerDAOimp2获取带泛型参数的父类的泛型（反射），该部分声明在DAO的构造器中（多态+this,this指向子类重写的getClass()方法）
 *
 @author Alex
 @create 2023-01-31-11:37
 */
// DAO类
public abstract class UJ04<T>{
    // 关键点在这，获取父类的泛型，this指向子类重写的getClass(方法)
    private Class<T> clazz = null;
    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = paramType.getActualTypeArguments();
        clazz = (Class<T>) actualTypeArguments[0];
    }

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
    public List<T> query(Connection connection, String sql, Object...args) throws Exception{
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
    public T getInstance(Connection connection,String sql,Object...args) throws Exception{
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
    // 其实应该写返回Object类型的，但是要强转。写泛型更方便，直接return就行（因为此处返回类型为方法调用者的类型，即return指明类型）
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


// 针对于customers表的具体实现类
class CustomerDAOimp2 extends UJ04<customer> implements CustomerDAO{
    @Override
    public void insert(Connection conn, customer cust) throws Exception {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        DDMop(conn,sql,cust.name,cust.email,cust.birth);
//        UCD03.closeResourse(conn,null);
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
//        UCD03.closeResourse(conn,null);
    }

    @Override
    public customer getCustomerById(Connection conn, int id) throws Exception {
        String sql = "select name,email,birth from customers where id=?";
        customer cust = getInstance(conn, sql, id);
        return cust;
    }

    @Override
    public List<customer> getAll(Connection conn) throws Exception {
        String sql = "select name,email,birth from customers";
        List<customer> custs = query(conn, sql);
        return custs;
    }

    @Override
    public long getCount(Connection conn) throws Exception {
        String sql = "select count(*) from customers";
        long value = (long)getValue(conn, sql);  // 返回的是object类型的对象
        return value;
    }

    @Override
    public Date getMaxBirth(Connection conn) throws Exception {
        String sql = "select max(birth) from customers";
        java.sql.Date date = getValue(conn, sql);
        return date;
    }
}
