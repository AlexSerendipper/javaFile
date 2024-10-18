package select;

/**
 * 【mysql的环境搭建，见附录】
 *
 * 【服务的启动】在使用mysql前需要保证mysql的服务处于开启的状态
 *   启动任务管理器（点击）→ 服务（点击）→ 找到MySQL80（点击鼠标右键）→ 启动或停止（点击）
 *   启动 MySQL 服务命令：net start MySQL服务名
 *   停止 MySQL 服务命令：net stop MySQL服务名
 *
 * 【用户登录】
 * （1）使用cmd命令框：
 *       mysql -u用户名 -p密码
 *       cmd命令完整版，可以登录不同版本的sql：mysql -u用户名 -p密码 -h指定IP地址 -P端口号
 *       默认使用本机IP地址localhost的3306端口号
 * （2）使用mysql 8.0 command line client（仅限root用户）：
 *       直接输入密码
 *       quit退出登录
 *
 * 【修改MySQL的编码设置】mysql5.7默认为拉丁码，需要修改为UTF-8。mysql8.0不用改
 *  1）查看数据库编码格式
 *     show variables like 'character_%';       # 查看数据库的字符集
 *     show variables like 'collation_%';       # 查看数据库的字符集对应的编码格式
 *
 *  2）修改MySQL的编码设置
 *   修改mysql的数据目录下的my.ini配置文件
 *   [client]在其下添加
 *   default-character-set=utf8mb4
 *   [mysql] #大概在63行左右，在其下添加
 *   default-character-set=utf8mb4
 *   [mysqld] # 大概在76行左右，在其下添加
 *   character-set-server=utf8mb4
 *   collation-server=utf8mb4_general_ci
 *
 * 【MySQL图形化管理工具】MySQL Workbench，Navicat，SQLyog，dbeaver
 *   图形化工具一般可以任选，这里推荐navicat和SQLyog。页面非常简洁，很舒适
 *       F9：执行选中的语句
 *       ctrl T：新建查询页
 *       alt L : 关闭当前查询页
 *       Ctrl+PgUp 切换到上一查询页
 *       Ctrl+PgDown 切换到下一查询页
 *   是旧版本的图形界面工具，在连接MySQL8时出现“Authentication plugin'caching_sha2_password' cannot be loaded”错误。
 *   出现这个原因是MySQL8之前的版本中加密规则是mysql_native_password，而在MySQL8之后，加密规则是caching_sha2_password。
 *    解决问题方法有两种，1）第一种是升级图形界面工具版本，
 *                      2）第二种是把MySQL8用户登录密码加密规则还原成mysql_native_password
 *                           USE mysql;        #cmd中使用mysql数据库
 *                           ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'qqabcd';  #修改'root'@'localhost'用户的密码规则和密码
 *                           FLUSH PRIVILEGES;  #刷新权限
 *   本机的主机名为localhost,端口3306,用户名root,密码qqabcd
 *   SHOW VARIABLES WHERE Variable_name = 'hostname';           # 查看主机名
 *   SHOW VARIABLES WHERE Variable_name = 'port';               # 查看端口
 *
 *
 * 【MySQL主要目录结构】
 *   bin目录 ：所有MySQL的可执行文件。如：mysql.exe
 *   MySQLInstanceConfig.exe ：数据库的配置向导，在安装时出现的内容
 *   data目录 ：系统数据库所在的目录，用户创建的数据库也在此处
 *   my.ini文件 ：MySQL的主要配置文件
 @author Alex
 @create 2023-01-12-15:14
 */
public class US02 {
}
