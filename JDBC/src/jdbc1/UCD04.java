package jdbc1;

import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * 【DDM之查询操作】
 *  针对DDM的查询操作，会产生结果集，对应resultset对象，操作后需要关闭resultset对象
 *  ORM思想：一个数据表对应一个java类、一条记录对应一个java对象、一个字段对应一个属性
 *
 * 【PreparedStatement】执行sql（查询）流程如下
 * （1）获取连接
 * （2）获取PreparedStatement对象 ==> 填充占位符
 * （3）执行sql查询语句，并处理结果集
 *  ResultSet resultSet = PreparedStatement.executeQuery()       # 执行sql查询语句，并返回结果集
 *  ResultSet.next()                                             # 该方法法检测下一行是否有效。若有效该方法返回true, 取出该行，且指针下移。返回false，指针不动。
 *    ResultSet 返回的实际上就是一张数据表. 有一个指针指向数据表的第一条记录的前面。
 *    对比Iterator对象。.hasNext()只判断下一行是否有数据，.next()进行指针下移和输出数据
 *  ResultSet.getXxx(int index) 或 getXxx(String columnName)      # 获取该行数据各个列的值,index与查询语句中字段的顺序一一对应。通用的做法是getObject
 *  ResultSetMetaData类的相关操作
 *（4）关闭资源
 *
 * 【ResultSetMetaData类】
 *  ResultSetMetaData metaData = resultSet.getMetaData();        # 获取结果集的元数据
 *  metaData.getColumnCount()                                    # 获取结果集的列数
 *  metaData.getColumnName()                                     # 获取结果集指定列的列名(真实名)(不推荐使用)
 *  metaData.getColumnLabel()                                    # 获取列的别名（没有起别名则返回真实列名）（推荐使用）
 @author Alex
 @create 2023-01-30-10:08
 */
public class UCD04 {
    // PreparedStatement执行sql（查询）流程如下
    @Test
    public void test1() throws Exception{
        Connection connection = new UCD02().getConnection();
        String sql = "select * " +
                     "from customers " +
                     "where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,5);
        // 执行，并返回结果集
        ResultSet resultSet = ps.executeQuery();
        // 处理结果集
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString("name");
            String email = resultSet.getString(3);
            // 方式一：直接显示,不建议
            // 方式二：封装到数组中,不推荐
            Object[] data = new Object[]{id,name,email};
            // 方式三：封装到类中
            customer customer = new customer(id,name,email);
            System.out.println(customer);
        }

        // 关闭资源
        new UCD03().closeResourse(connection,ps,resultSet);;
    }

    // 针对order表的通用查询操作（能够处理不同字段）：实现获取列的别名
    public order queryOrder(String sql,Object...args) throws Exception{  // 设置可变形参，也可以不输入形参
        Connection connection = UCD02.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        // 执行，并返回结果集,仅查一次
        ResultSet resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 由于不知道取出的数据是几个，对应类的哪一个属性，所以需要把对象先造出来
        if(resultSet.next()){
            order or = new order();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = resultSet.getObject(i+1);
                String columnLabel = metaData.getColumnLabel(i + 1);
                // 通过反射，为customer对象中指定columnName属性赋值
                Field field = order.class.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(or,columnValue);
            }
            UCD03.closeResourse(connection,ps,resultSet);
            return or;
        }
        UCD03.closeResourse(connection,ps,resultSet);
        return null;
    }

    // quertOrder测试。获取列的别名
    // 当类中的属性和取出的字段名并不完全对应时，sql语句要起别名
    @Test
    public void test2() throws Exception{
        // 注意：当类中的属性和取出的字段名并不完全对应时，sql语句要起别名
        String sql = "select order_id orderId,order_name orderName from `order` where order_id=?";
        order order = queryOrder(sql, 1);
        System.out.println(order);
    }

    // 针对于不同表的查询操作, 仅查询一条数据，返回一条数据✔✔✔
    // 本可以返回Object类，但是就需要强转操作
    // 使用泛型方法的好处是在传入clazz时就指定了类型，从而实现适用于任何表
    public <T> T getInstance(Class<T> clazz,String sql,Object...args) throws Exception{
        Connection connection = UCD02.getConnection();
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
            UCD03.closeResourse(connection,ps,resultSet);
            return t;
        }
        UCD03.closeResourse(connection,ps,resultSet);
        return null;
    }


    // 针对于不同表的查询操作，查询多条数据，返回多个对象✔✔✔
    public <T> List<T> query(Class<T> clazz, String sql, Object...args) throws Exception{
        Connection connection = UCD02.getConnection();
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
        }
        UCD03.closeResourse(connection,ps,resultSet);
        return arrayList;
    }
}


class customer{
    int id;
    String name;
    String email;

    public customer(int id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    Date birth;
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
class order{
    int orderId;
    String orderName;
    Date orderDate;

    public order() {
    }

    @Override
    public String toString() {
        return "order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}