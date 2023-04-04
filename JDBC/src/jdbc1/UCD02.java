package jdbc1;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 【获取数据库连接】
 *  java.sql.Driver 接口是所有 JDBC 驱动程序需要实现的接口。这个接口是提供给数据库厂商使用的，不同数据库厂商提供不同的实现
 *    Oracle的驱动：oracle.jdbc.driver.OracleDriver
 *    mySql的驱动： com.mysql.jdbc.Driver      (适用于mysql5.7驱动)(该驱动就是mySql公司针对自己软件的驱动)
 *                   com.mysql.cj.jdbc.Driver   (适用于mysql8.0驱动)
 *  实际开发中通常使用反射，动态获取mysql驱动
 *  实际开发中通常使用DriverManager获取连接，并且通常不用显式调用DriverManager类的registerDriver()方法来注册驱动程序类的实例，
 *   因为 Driver 接口的驱动程序类都包含了静态代码块，在这个静态代码块中，会调用DriverManager.registerDriver()方法来注册自身的一个实例
 * (1) 方式一：最基础的做法
 *（2）方式二：对方式一的升级，使用反射，动态获取mysql驱动，使程序有更好的可移植性
 *（3）方式三：实际中开发都是使用DriverManager替换Driver。DriverManager类是驱动程序管理器类，负责管理驱动程序
 * (4) 方式四：对方法三的注册驱动的优化，只需要加载驱动，不需要显示的注册驱动
 * (5) 方式五：最终版，把基本信息写入配置文件，读取配置文件进行数据库的连接
 *      最终版中我们只需要修改一下配置文件，就可以实现连接上不同的数据库，实现了数据与代码的分离（解耦）
 *      ??useUnicode=true&characterEncoding=UTF-8         # Mysql中默认字符集需要改，需把该语句写在配置文件的url后面
 *
 @author Alex
 @create 2023-01-29-21:48
 */
public class UCD02 {
    // 方式一：最基础的做法
    @Test
    public void test1() throws SQLException {
        // 获取mysql驱动
        Driver driver = new com.mysql.jdbc.Driver();
        // 提供url，即连接到哪个数据库。其中jdbc:mysql:是协议
        String url = "jdbc:mysql://localhost:3306/test";
        // 将用户名和密码写入properties中
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","qqabcd");
        // 通过该方法获取数据库的连接对象
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }
    // 方式二：对方式一的升级，使用反射，动态获取mysql驱动，使程序有更好的可移植性
    @Test
    public void test2() throws Exception{
        // 获取mysql驱动,反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        // 提供url，即连接到哪个数据库。其中jdbc:mysql:是协议
        String url = "jdbc:mysql://localhost:3306/test";
        // 将用户名和密码写入properties中
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","qqabcd");
        // 通过该方法获取数据库的连接对象
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }
    // 方式三：实际中开发都是使用DriverManager替换Driver。DriverManager类是驱动程序管理器类，负责管理驱动程序
    @Test
    public void test3() throws Exception{
        // 反射获取mysql驱动。
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        // 注册驱动
        DriverManager.registerDriver(driver);
        // 获取连接,实际上可以看到使用.getConnection有个重载方式，使用Properties的形式也可以
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "qqabcd";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    // 方式四：对方法三的注册驱动的优化，只需要加载驱动，不需要显示的注册驱动
    @Test
    public void test4() throws Exception{
        // 反射获取mysql驱动。。。实际上在类的加载过程中，完成了驱动的加载，自动在静态代码块中调用了DriverManager.registerDriver()方法
        Class.forName("com.mysql.jdbc.Driver");
//        Driver driver = (Driver) clazz.newInstance();
        // 不再需要注册驱动
//        DriverManager.registerDriver(driver);
        // 获取连接,实际上可以看到使用.getConnection有个重载方式，使用Properties的形式也可以
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "qqabcd";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    // 方式五✔✔✔：最终版，把基本信息写入配置文件，读取配置文件进行数据库的连接
    public static Connection getConnection() throws Exception{
        // 读取配置文件的信息
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("jdbc.properties");  // 自定义配置文件，main方法中默认在workspace下
        properties.load(fis);  // 加载对应的流文件
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String drive = properties.getProperty("drive");
        // 反射获取mysql驱动。。。实际上在类的加载过程中，完成了驱动的加载，自动在静态代码块中调用了DriverManager.registerDriver()方法
        Class.forName(drive);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
