package backups;

/**
 * 【数据表的导入与导出】整体上了解即可
 * 【表数据导出】
 *  mysql默认对导出的目录有权限限制，也就是说使用命令行进行导出的时候，需要指定目录进行操作。
 *     SHOW GLOBAL VARIABLES LIKE 'secure_file_priv'                        # 查看secure_file_priv值
 *     如果设置参数为empty，表示不限制文件生成的位置，这是不安全的设置;
 *     如果设置参数为 路径字符串，就要求生成的文件只能放在这个指定的目录，或者它的子目录;
 *     如果设置参数为NULL，就表示禁止在这个MySQL实例上执行select ... into outfile 操作。
 * （1）使用SELECT…INTO OUTFILE导出文本文件
 *     SELECT * FROM account INTO OUTFILE "D:\MysqlData\Uploads\";               # 方式一：可以将查询的结果赋值给txt文件（仅表数据）
 *     SELECT * FROM atguigu.account INTO OUTFILE '/var/lib/mysql-files/account_1.txt' FIELDS TERMINATED BY ',' ENCLOSED BY '\"';            # 数据导出时可以使用 FIELDS选项和LINES选项，实现字段之间使用逗号"，"间隔，所有字段值用双引号括起来：
 *
 * （2）使用mysqldump命令导出文本文件（需要终端执行指令）
 *     mysqldump -uroot -p password -T "/var/lib/mysql-files/" atguigu account   # 方式二：将atguigu库中的account表进行导出
 *                                                                                  mysqldump命令执行完毕后，在指定的目录/var/lib/mysql-files/下生成了account.sql和account.txt文件。
 *                                                                                  account.sql文件包含了创建account表的CREATE语句。account.txt文件只包含account表中的数据。
 *    # 方式二的拓展用法，使用mysqldump将atguigu数据库中的account表导出到文本文件，使用FIELDS选项，实现字段之间使用逗号“，”间隔，并且所有字符类型字段值用双引号括起来：
 *     mysqldump -uroot -p -T "/var/lib/mysql-files/" atguigu account --fields-terminated- by=',' --fields-optionally-enclosed-by='\"'
 *
 * （3）使用mysql命令导出文本文件
 *     mysql -uroot -p password --execute="SELECT * FROM account;" atguigu> "/var/lib/mysql-files/account.txt"          # 方式三：使用mysql语句导出atguigu数据中account表中的记录到指定的文本文件：
 *     mysql -uroot -p --vertical --execute="SELECT * FROM account;" atguigu >"/var/lib/mysql-files/account_1.txt"      # 使用--veritcal参数将该条件记录分为多行显示：
 *     mysql -uroot -p --xml --execute="SELECT * FROM account;" atguigu>"/var/lib/mysql-files/account_3.xml"            # 将表中记录导出到xml文件中（还可以使用--html，将记录导出到html文件中）
 *
 * 【表数据导入】
 * （1）使用LOAD DATA INFILE方式导入文本文件：需要使用 SELECT…INTO OUTFILE 导出的文本文件
 *     LOAD DATA INFILE '/var/lib/mysql-files/account_0.txt' INTO TABLE atguigu.account;                                              # 方式一：从文本文件account.txt中恢复数据
 *     LOAD DATA INFILE '/var/lib/mysql-files/account_1.txt' INTO TABLE atguigu.account FIELDS TERMINATED BY ',' ENCLOSED BY '\"';    # 从特殊文本文件中恢复数据
 * （2）使用mysqlimport方式导入文本文件
 *     mysqlimport -uroot -p password atguigu '/var/lib/mysql-files/account.txt' --fields-terminated- by=',' --fields-optionally-enclosed-by='\"'         # 方式二：从文本文件account.txt中恢复数据
-------------------------------
除了前面介绍的几个选项之外，mysqlimport支持需要选项，常见的选项有:
--columns=column_list,-c column_list：该选项采用逗号分隔的列名作为其值。列名的顺序只是如何匹配数据文件列和表列。
--compress，-C：压缩在客户端和服务器之间发送的所有信息（如果二者均支持压缩)。
-d，--delete：导入文本文件前请空表。
--force，-f：忽视错误。例如，如果某个文本文件的表不存在，就继续处理其他文件。不使用--force，若表不存在，则mysqlimport退出。
--host=host_name，-h host host_name：将数据导入给定主机上的MySQL服务器，默认主机是localhost,。
--ignore，-i：参见--replace选项的描述。
--ignore-lines=n:忽视数据文件的前n行。
--local，-L：从本地客户端读入输入文件。
--lock-tables，-l：处理文本文件前锁定所有表，以便写入。这样可以确保所有表在服务器上保持同步。
--password[=password],-p[password]：当连接服务器时使用的密码。如果使用短选项形式(-p)，选项和密码之间不能有空格。如果在命令行中--password或-p选项后面没有密码值，就提示输入一个密码。
--port=port_num, -P port_num：用户连接的TCP/IP端口号。
--protocol={TCP|SOCKET|PIPE|MEMORY:使用的连接协议。
--replace，-r --replace和-ignore选项控制复制唯一键值已有记录的输入记录的处理。如果指定--replace，新行替换有相同唯一键值的已有行；如果指定--ignore，复制已有唯一键值的输入行被跳过;如果不指定这两个选项，当发现一个复制键值时会出现一个错误，并且忽视文本文件的剩余部分。
--silent, -s：沉默模式。只有出现错误时才输出信息。
--user=username，-u user_name：当连接服务器时My5QL使用的用户名。
--verbose, -v：冗长模式。打印出程序操作的详细信息。
--version,-v：显示版本信息并退出。
-------------------------------
 @author Alex
 @create 2023-05-29-15:31
 */
public class UB05 {
}
