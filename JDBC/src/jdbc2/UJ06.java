package jdbc2;

import jdbc1.UCD03;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 【DBUtils工具包】
 *  用该工具包实现替换我们自己写的DML的通用操作（增删改查）
 *  ✔该工具类底层的源码与DML通用操作中的并无差别~~相当于是进行了简单的封装罢了。只不过健壮性更好罢了
 *
 * 【使用】
 *  QueryRunner runner = new QueryRunner();
 *  runner.update(con,sql,...args);            # ✔相当于我们先前写的DML增删改通用操作，并且该方法返回影响的行数
 *  runner.query(con,sql,rsh,...args)          # ✔相当于我们先前写的通用操作查询操作
 *    相比与我们的方法，多了一个参数ResultSetHandler。
 *    ResultSetHandler是一个接口，提供了各种实现类，用来处理返回结果多样的问题✔
 *      BeanHandler<>           # 用于返回表中的一条记录（单个对象的形式）
 *                                  注意：✔✔✔使用了javabean要求一定要有get、set方法，且类必须得是公共类
 *      BeanListHandler<>       # 用于返回表中的多条记录（对应对象数组的形式）
 *      ArrayHandler<>          # 用于返回表中的一条记录（数组形式）
 *      ArrayListHandler<>      # 用于返回表中的多条记录（数组形式）
 *      MapHandler              # 用于返回表中的一条记录（Map形式，将字段和值分别作为key和value）,不需要泛型
 *      MapListHandler          # 用于返回表中的多条记录（Map形式）
 *      ScalarHandler           # ✔相当于我们先前写的针对聚合函数查询（查询结果为一行一列的情况）
 *      自定义Handler            # ✔创建ResultSetHandler的实现类，实现其中的方法即可
 *  DbUtils.close(connection);             # 关闭资源，不能一次关闭多个（需要try-catch处理）
 *  DbUtils.closeQuietly(connection)       # 关闭资源，（无需try-catch处理）✔相当于我们先前写的关闭资源的操作
 *
 *
 *
 @author Alex
 @create 2023-01-31-16:49
 */
public class UJ06 {
    // 测试DML增删改
    @Test
    public void test1() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = UJ05.getdruid();
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        int insertCount = runner.update(connection, sql, "蔡徐坤", "cxk@126.com", "1998-11-11");
        UCD03.closeResourse(connection,null);
    }

    // 测试查询操作（返回单条记录）
    @Test
    public void test2() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = UJ05.getdruid();
        String sql = "select name,email,birth from customers where id=?";
        BeanHandler<Customer1> rsh = new BeanHandler<>(Customer1.class);
        Customer1 query = runner.query(connection, sql, rsh, 20);
        System.out.println(query);
        UCD03.closeResourse(connection,null);
    }

    // 测试查询操作（返回多条记录）
    @Test
    public void test3() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = UJ05.getdruid();
        String sql = "select name,email,birth from customers where id<?";
        BeanListHandler<Customer1> rsh = new BeanListHandler<>(Customer1.class);
        List<Customer1> query = runner.query(connection, sql, rsh, 20);
        for(Customer1 i:query){
            System.out.println(i);
        }
        UCD03.closeResourse(connection,null);
    }

    // 测试查询操作（返回多条记录）(MAP形式)
    @Test
    public void test4() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = UJ05.getdruid();
        String sql = "select name,email,birth from customers where id<?";
        MapListHandler rsh = new MapListHandler();
        List<Map<String, Object>> query = runner.query(connection, sql, rsh, 25);
        for(Map i:query){
            Set set = i.entrySet();
            for(Object j:set){
                System.out.println(j);
            }
            System.out.println("****************************");
        }
        UCD03.closeResourse(connection,null);
    }

    // 针对聚合函数查询（查询结果为一行一列的情况）
    @Test
    public void test5() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = UJ05.getdruid();
        String sql = "select count(*) from customers";
        ScalarHandler rsh = new ScalarHandler();
        Object query = runner.query(connection, sql, rsh);
        System.out.println(query);
        UCD03.closeResourse(connection,null);
    }

    // 自定义Handler
    @Test
    public void test6() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = UJ05.getdruid();
        String sql = "select name,email,birth from customers where id=?";
        // 仿BeanHandler功能,注意，实际上sql查询的结果已经放在其方法中了
        // Handler的返回值就是runner.query的返回值
        ResultSetHandler<Customer1> personalHanlder = new ResultSetHandler<Customer1>(){
            @Override
            public Customer1 handle(ResultSet resultSet) throws SQLException {  // 查询结果
                if(resultSet.next()){
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    Date birth = resultSet.getDate("birth");
                    return new Customer1(name,email,birth);
                }
                return null;
            }
        };

        Customer1 query = runner.query(connection, sql, personalHanlder, 10);
        System.out.println(query);
        DbUtils.closeQuietly(connection);
    }
}
