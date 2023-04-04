package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 @author Alex
 @create 2023-02-01-12:40
 */
public class JdbcUtils {
    /**
     * 获取数据库连接池中的连接，如果返回Null说明获取连接失败
     * @return
     */
    private static DataSource source1;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {
        try {
            // 读取配置文件的信息
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("D:\\IdeaWorkspace\\JavaWeb\\src\\druid.properties");  // 自定义配置文件，main方法中默认在workspace下
            properties.load(fis);  // 加载对应的流文件

            // 创建一个dbcp数据库连接池
            source1 = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection() {
        Connection connection = threadLocal.get();
//        try {
//            connection = source1.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
        try {
            if (connection == null) {
                connection = source1.getConnection();
                // 将连接保存到threadlocal中，供后边的jdbc操作使用
                threadLocal.set(connection);
                // 设置手动提交
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
        return connection;
    }


    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose() {
        // 基本不会有连接为空的情况吧，不懂，蛮写
        Connection connection = threadLocal.get();
        if (connection != null) { // 如果不等于null，说明之前使用过连接，操作过数据库
            try {
                connection.commit(); // 提交 事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行remove 操作，否则就会出错。（因为 Tomcat 服务器底层使用了线程池技术）
        threadLocal.remove();
    }


    /**
     * 回滚事务，并关闭释放连接
     */
    public static void rollbackAndClose() {
        // 基本不会有连接为空的情况吧，不懂，蛮写
        Connection connection = threadLocal.get();
        if (connection != null) {  // 如果不等于 null，说明 之前使用过连接，操作过数据库
            try {
                connection.rollback();  //回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();  // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行 remove 操作，否则就会出错。（因为 Tomcat 服务器底层使用了线程池技术）
        threadLocal.remove();
    }


    /**
     * 关闭连接，放回数据库连接池
     * @param conn
     */
//    public static void close(Connection conn) {
//        if(conn!=null){
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
