package jdbc1;

import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * 【DDM之增删改操作】包括DDL，都是没有返回值的！都可以使用如下流程
 * 【数据库连接】
 *  数据库连接被用于向数据库服务器发送命令和SQL语句。即在连接建立后，对数据库进行访问，执行sql语句
 *  在java.sql包中有3个接口分别定义了对数据库的连接的不同方式：
 *   Statement：被淘汰了
 *   PreparedStatement：Statement的子接口，SQL语句被预编译并存储在此对象中，可以使用此对象多次高效的执行该语句
 *   CallableStatement：用于执行SQL存储过程
 *
 * 【statement操作数据表的弊端】了解
 *  弊端：需要拼写sql语句，存在拼串麻烦的问题。并且存在注入问题
 *  SQL注入是利用某些系统没有对用户输入的数据进行充分的检查，而在用户输入数据中注入非法的SQL语句段或命令
 *   (如：SELECT user,password FROM user_table WHERE user='a' OR 1 = ' AND password =' OR '1' = '1') ，
 *   从而利用系统的SQL引擎完成恶意行为的做法
 *  对于Java而言，要防范SQL注入，只要用PreparedStatement(从Statement扩展而来) 取代 Statement就可以了
 *
 *
 * 【PreparedStatement】执行sql（增删改）流程如下
 *   ✔PreparedStatement解决了拼串、sql注入问题
 *    ✔并且使用PreparedStatement能够操作blog类型的数据，而statement不行
 *    ✔并且使用PreparedStatement能够实现更高效的批量操作，相比于statement
 * （1）获取连接
 * （2）获取PreparedStatement对象 ==> 填充占位符
 *  Connection.preparedStatement()           # 获取PreparedStatement对象
 *    PreparedStatement对象所代表的 SQL 语句中的参数用问号(?)占位符来表示，
 *  PreparedStatement.setXxx()               # 填充占位符。XXX取决于填充数据的类型，也可以使用通用的setObject()
 *                                               .setXxx()方法有两个参数，第一个参数是要设置的SQL语句中的参数的索引(从1开始,可以省略)，
 *                                               第二个是设置的 SQL语句中的参数的值。
 * （3）执行sql语句
 *  PreparedStatement.execute()              # 执行sql语句。注：该方法如果执行的是增删改操作，返回false，如果执行的是查询操作，返回值为true
 *  PreparedStatement.executeUpdate();       # 执行sql语句。注：该方法返回受sql语句影响的行数
 * （4）关闭资源
 *
 * 【PreparedStatement获取自动递增的主键】
 *  PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);      # 设置允许获取自动递增的主键
 *  ResultSet resultSet = ps.getGeneratedKeys();      # 获取带有主键的结果集
 *  遍历结果集即可获取自动递增的主键
 *
 @author Alex
 @create 2023-01-29-23:09
 */
public class UCD03 {
    // PreparedStatement执行sql（增删改）示范
    @Test
    public void test1() throws Exception{
        // 获取数据库连接
        Connection connection = new UCD02().getConnection();
        // 预编译
        String sql = "insert into customers(name,email,birth)" +
                     "values(?,?,?)";
        // 填充占位符
        PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,"蔡徐坤");
        ps.setString(2,"cxk@gmail.com");
        ResultSet resultSet = ps.getGeneratedKeys();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse("1998-9-20");
        ps.setDate(3,new Date(date.getTime()));
        // 执行sql
        ps.execute();
        // 关闭资源
        ps.close();
        connection.close();
    }

    // ✔实现通用的增删改操作(可针对不同的表)
    public static int DDMopertor(String sql,Object...args) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            // 获取数据库连接
            connection = UCD02.getConnection();
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
            ps.close();
            connection.close();
        }
    }

    // 通用DML操作测试
    @Test
    public void test2() throws Exception{
        String sql = "update customers set name='王俊凯' where id=?";
        int i = DDMopertor(sql, 21);
        if(i!=0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }

    }

    // ✔实现通用的关闭操作
    public static void closeResourse(Connection con,PreparedStatement ps){
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void closeResourse(Connection con, PreparedStatement ps, ResultSet resultSet){
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
