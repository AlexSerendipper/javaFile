package jdbc1;

/**
 * 【软件架构的两种方式】
 *  B/S：browser server，就是通过浏览器与服务器端通讯的方式架构，即通过网页的形式。该架构最大的好处就是实时更新
 *  C/S：client server，就是通过客户端与服务器端通讯的方式架构，即通过app的形式。该架构最大的弊端是需要更新app，最大的好处是可以增加用户体验
 *
 * 【javaweb技术概览】详见xmind
 *  浏览器端发送的数据，首先就被服务器端（tomcat）的servlet组件接收。servlet负责获取请求、处理请求、响应请求
 *  浏览器端和服务器端需要遵守相应的协议，这个协议就是http协议
 *  jsp: java server page, servlet并不适合响应请求，通过jsp可以实现动态响应
 *   为了更好的写jsp，引入了EL和JSTL
 *  本质上当服务器端接收到两个请求，服务器端是无法分辨这两个请求是否来自同一个浏览器端。为了解决这个问题，引入了session和cookie
 *  jdbc主要负责的是服务器端与数据库进行交互
 *  servlet、filter、listener构成了tomcat的三大核心组件。filter用于过滤数据，listener监听用户操作
 *  ajax实现了浏览器端和服务器端的异步传输数据（通过json和xml格式）
 *
 * 【JDBC概述】
 *  在Java中，数据库存取技术可分为如下几类：
 *    JDBC直接访问数据库
 *    JDO技术
 *    第三方O/R工具，如Hibernate, mybatis等
 *  ✔JDBC是java访问数据库的基石，JDO, Hibernate，mybatis等只是更好的封装了JDBC。
 *  JDBC(Java Database Connectivity)是一个独立于特定数据库管理系统、通用的SQL数据库存取和操作的公共接口（一组API）。
 *   接口即是规范，jdbc实际上定义了如何用java访问数据库的一组规范，为访问不同的数据库提供了一种统一的途径
 *   但是对于不同的操作，例如Mysql中添加某种数据用add,Oracle中使用insert，java程序员要操作不同数据库是很困难的
 *   所以不同的数据库厂商，需要针对这套接口，提供不同实现。不同的实现的集合，即为不同数据库的驱动✔
 *   这样java程序员只需要面向jdbc这套接口编程即可。
 *
 * 【JDBC程序编写流程】
 *  导入相应厂商的驱动 ==> 创建connection对象（获取数据库连接） ==> 创建statement对象（使用sql进行增删改查）
 *                                                           ==> 针对查，会产生结果集，对应resultset对象，操作后关闭resultset对象
 *                                                           ==> 针对增删改，不会产生结果集，直接关闭statement对象
 * 【java类型与sql类型对应关系】
 *                  java类型                                    SQL类型
 *                  boolean                                     BIT
 *                  byte                                        TINYINT
 *                  short                                       SMALLINT
 *                  int                                         INTEGER
 *                  long                                        BIGINT
 *                  String                                      CHAR,VARCHAR,LONGVARCHAR
 *                  byte array                                  BINARY , VAR BINARY
 *                  java.sql.Date                               DATE
 *                  java.sql.Time                               TIME
 *                  java.sql.Timestamp                          TIMESTAMP
 *
 *
 @author Alex
 @create 2023-01-29-21:06
 */
public class UCD01 {
}
