package useselect;

/**
 * 【流程控制函数】流程处理函数可以根据不同的条件，执行不同的处理流程，可以在SQL语句中实现不同的条件选择。
 *   IF(value,value1,value2) 如果value的值为TRUE，返回value1，否则返回value2     ✔相当于Java的三元表达式
 *   IFNULL(value1, value2)  如果value1不为NULL，返回value1，否则返回value2
 *   CASE WHEN 条件1 THEN 结果1 WHEN 条件2 THEN 结果2.... [ELSE 结果n] END：    ✔相当于Java的if...else if...else...
 *   CASE expr WHEN 常量值1 THEN 值1 WHEN 常量值1 THEN值1 .... [ELSE 值n] END： ✔相当于Java的switch...case...
 *   为什么SQL没有循环控制，因为其实SQL自带循环了....我们查找的结果就是一条条循环输出的
 *
 * 【*加密与解密函数】加密与解密函数主要用于对数据库中的数据进行加密和解密处理，以防止数据被他人窃取。
 *   实际操作中，加密操作往往是在客户端就完成了的，没有必要到数据库阶段再加密，所以使用情景较少
 *    PASSWORD(str)返回字符串str的加密版本，41位长的字符串。加密结果不可逆 ，常用于用户的密码加密
 *    MD5(str)返回字符串str的md5加密后的值，也是一种加密方式。若参数为NULL，则会返回NULL
 *    SHA(str)从原明文密码str计算并返回加密后的密码字符串，当参数为NULL时，返回NULL。 SHA加密算法比MD5更加安全 。
 *    ENCODE(value,password_seed) 返回使用password_seed作为加密密码加密value
 *    DECODE(value,password_seed) 返回使用password_seed作为加密密码解密value
 *   ✔PASSWORD()/ENCODE()/DECODE()在MySQL8.0被弃用
 *
 * 【*MySQL信息函数】MySQL中内置了一些可以查询MySQL信息的函数，这些函数主要用于帮助数据库开发或运维人员更好地对数据库进行维护工作。
 *   VERSION() 返回当前MySQL的版本号
 *   CONNECTION_ID() 返回当前MySQL服务器的连接数
 *   DATABASE()，SCHEMA() 返回MySQL命令行当前所在的数据库
 *   USER()，CURRENT_USER()、SYSTEM_USER()，SESSION_USER()返回当前连接MySQL的用户名，返回结果格式为“主机名@用户名”
 *   CHARSET(value) 返回字符串value自变量的字符集
 *   COLLATION(value) 返回字符串value的比较规则
 *   FORMAT(value,n)返回对数字value进行格式化后的结果数据。n表示四舍五入后保留到小数点后n位。如果n<=0，则只保留整数部分
 *   CONV(value,from,to) 将value的值进行不同进制之间的转换
 *   INET_ATON(ipvalue) 将以点分隔的IP地址转化为一个数字（类似与加密）
 *   INET_NTOA(value) 将数字形式的IP地址转化为以点分隔的IP地址（类似与解密）
 *   BENCHMARK(n,expr)将表达式expr重复执行n次。用于测试MySQL处理expr表达式所耗费的时间
 *   CONVERT(value USING char_code)将value所使用的字符编码修改为char_code
 @author Alex
 @create 2023-01-15-12:25
 */
public class US09 {
}
