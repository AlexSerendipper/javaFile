package jdbc2;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import jdbc1.UCD02;
import jdbc1.UCD03;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.Properties;

/**
 * 【数据库连接池的必要性】了解
 *  普通的JDBC数据库连接使用 DriverManager 来获取，每次向数据库建立连接的时候都要将Connection 加载到内存中，
 *   再验证用户名和密码(得花费0.05s～1s的时间)。需要数据库连接的时候，就向数据库要求一个，执行完成后再断开连接。
 *   这样的方式将会消耗大量的资源和时间。数据库的连接资源并没有得到很好的重复利用.若同时有几百人甚至几千人在线，
 *   频繁的进行数据库连接操作将占用很多的系统资源，严重的甚至会造成服务器的崩溃。
 *  对于每一次数据库连接，使用完后都得断开。否则，如果程序出现异常而未能关闭，将会导致数
 *   据库系统中的内存泄漏（即对象无法被回收），最终将导致重启数据库。
 *  这种开发不能控制被创建的连接对象数，系统资源会被毫无顾及的分配出去，如连接过多，也可
 *   能导致内存泄漏，服务器崩溃
 *
 * 【数据库连接池技术】connection poll，了解
 *  数据库连接池的基本思想就是为数据库连接建立一个“缓冲池”。预先在缓冲池中放入一定数量的连接，
 *   当需要建立数据库连接时，只需从“缓冲池”中取出一个，使用完毕之后再放回去。
 *  数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是重新建立一个。
 *  数据库连接池在初始化时将创建一定数量的数据库连接放到连接池中，这些数据库连接的数量是由最小数据库连接数来设定的。
 *   无论这些数据库连接是否被使用，连接池都将一直保证至少拥有这么多的连接数量。连接池的最大数据库连接数量限定了这个连接池能占有的最大连接数，
 *   当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到等待队列中。
 *
 * 【数据库连接池的使用】✔使用该技术可以实现替换jbdc上的数据库连接技术
 *  JDBC 的数据库连接池使用 javax.sql.DataSource 来表示，DataSource只是一个接口，
 *   该接口通常由服务器(Weblogic, WebSphere, Tomcat)提供实现，也有一些开源组织提供实现：
 *    DBCP       # Apache提供的数据库连接池,tomcat自带DBCP数据库连接池，速度相对比C3P0快，但自身存在BUG
 *    C3P0       # 某开源组织提供的数据库连接池，速度相对较慢，但稳定性高，hibernate推荐使用
 *    Druid      # 德鲁伊数据库连接池，阿里提供的数据库连接池。目前开发中主流使用
 *  DataSource通常被称为数据源，它包含连接池和连接池管理两个部分，习惯上也经常把 DataSource 称为连接池
 *  DataSource用来取代DriverManager来获取Connection，获取速度快，同时可以大幅度提高数据库访问速度
 *
 * 【c3p0】了解，先导入c3p0的jar包
 *  配置文件必须放置在src目录下，且文件名必须为c3p0-config.xml
 *  sql8.0的需要把配置文件中的URL连接中的的&改成 &amp;
 *  new ComboPooledDataSource("helloc3p0"); 注意要把造的池子放在外边，否则每次调用都造一个池子就傻逼了
 *
 * 【DBCP】了解，导入dbcp和pooljar包
 *  创建配置文件于src目录下
 *  BasicDataSourceFactory.createDataSource(properties); 通过该方法创建数据库连接池
 *
 * 【Druid】✔必须掌握，开发中主要使用该方式！导入
 *
 @author Alex
 @create 2023-01-31-14:13
 */
public class UJ05 {
    // 测试CustomerDAOimp
    // 右键CustomerDAOimp ==> go to ==> test，可以快速创建一个测试该类所有功能的junit测试
    @Test
    public void test1() throws Exception{
        CustomerDAOimp2 dao = new CustomerDAOimp2();
        Connection connection = UCD02.getConnection();
        // 测试插入
        customer hyq = new customer(1, "hyq", "hyq@162.com", new Date(31415926982L));
//        dao.insert(connection,hyq);
        // 测试删除
//        dao.deleteById(connection,22);
        // 测试修改
//        dao.update(connection,hyq);
        // 测试根据ID查询
        customer cust = dao.getCustomerById(connection, 3);
        System.out.println(cust);
        // 测试查找所有表中记录
        // 测试查询表中有多少数据
        // 测试数据表中最大生日
        Date maxBirth = dao.getMaxBirth(connection);
        System.out.println(maxBirth);
        // 关闭资源
        UCD03.closeResourse(connection,null);
    }



// ****************************************************************************************
    // C3P0测试，硬编码，不推荐，直接将信息写在程序里
    @Test
    public void test2() throws Exception{
        // datasourse的具体实现类
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        // loads the jdbc driver。配置我们自己的connection
        cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" );
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true" );
        cpds.setUser("root");
        cpds.setPassword("qqabcd");
        // 数据库连接池的相关设置，进行数据库连接池的管理
        cpds.setInitialPoolSize(10);  // 初始池连接数
        // 获取数据库连接
        Connection connection = cpds.getConnection();
        System.out.println(connection);

        // 销毁c3p0连接池
        DataSources.destroy(cpds);
    }

    // c3p0测试，软编码，通过读取配置文件(自动读取)
    @Test
    public void test3() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }

    // 将c3p0封装，只创建一个池子
    public static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
    public static Connection getc3p0() throws Exception{
        Connection connection = cpds.getConnection();
        return connection;
    }



// ****************************************************************************************
    // dbcp测试，硬编码，直接将信息写在程序里
    @Test
    public void test4() throws Exception{
        // datasourse的具体实现类
        BasicDataSource source = new BasicDataSource();
        // 配置我们自己的connection
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true");
        source.setUsername("root");
        source.setPassword("qqabcd");
        // 数据库连接池的相关设置，进行数据库连接池的管理
        source.setInitialSize(10);  // 初始池连接数
        source.setMaxActive(10);
        // 获取连接
        Connection connection = source.getConnection();
        System.out.println(connection);
    }

    // dbcp测试，软编码，通过读取配置文件(自动读取)
    @Test
    public void test5() throws Exception{
        // 读取配置文件的信息
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("./src/dbcp.properties");  // 自定义配置文件，main方法中默认在workspace下
        properties.load(fis);  // 加载对应的流文件
        // 创建一个dbcp数据库连接池
        DataSource source = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = source.getConnection();
        System.out.println(connection);
    }

    // 将dbcp封装，只创建一个池子
    // 使用静态代码块，其中的代码随着类的加载而加载，对静态属性DataSource进行初始化，并只执行一次
    private static DataSource source;
    static{
        try {
            // 读取配置文件的信息
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("./src/dbcp.properties");  // 自定义配置文件，main方法中默认在workspace下
            properties.load(fis);  // 加载对应的流文件
            // 创建一个dbcp数据库连接池
            source = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection getdbcp() throws Exception{
        Connection connection = source.getConnection();
        return connection;
    }



// ****************************************************************************************
    // druid和dbcp调用几乎是一样的
    // druid测试，硬编码，直接将信息写在程序里
    @Test
    public void test6() throws Exception{
        // datasourse的具体实现类
        DruidDataSource source = new DruidDataSource();
        // 配置我们自己的connection
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true");
        source.setUsername("root");
        source.setPassword("qqabcd");
        // 数据库连接池的相关设置，进行数据库连接池的管理
        source.setInitialSize(10);  // 初始池连接数
        source.setMaxActive(10);
        // 获取连接
        Connection connection = UJ05.source.getConnection();
        System.out.println(connection);
    }

    // dbcp测试，软编码，通过读取配置文件(自动读取)
    @Test
    public void test7() throws Exception{
        // 读取配置文件的信息
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("./src/druid.properties");  // 自定义配置文件，main方法中默认在workspace下
        properties.load(fis);  // 加载对应的流文件
        // 创建一个dbcp数据库连接池
        DataSource source = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = source.getConnection();
        System.out.println(connection);
    }

    // 将druid封装，只创建一个池子
    // 使用静态代码块，其中的代码随着类的加载而加载，对静态属性DataSource进行初始化，并只执行一次
    private static DataSource source1;
    static{
        try {
            // 读取配置文件的信息
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("./src/druid.properties");  // 自定义配置文件，main方法中默认在workspace下
            properties.load(fis);  // 加载对应的流文件
            // 创建一个druid数据库连接池
            source1 = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection getdruid() throws Exception{
        Connection connection = source1.getConnection();
        return connection;
    }
}
