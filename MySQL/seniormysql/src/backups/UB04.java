package backups;

/**
 * 【数据库的备份与恢复】
 *  为了有效防止数据丢失，并将损失降到最低，应定期 对MySQL数据库服务器做 备份。
 *    如果数据库中的数据丢失或者出现错误，可以使用备份的数据 进行恢复 。主从服务器之间的数据同步问题可以通过复制功能实现。
 *  物理备份与逻辑备份
 *   物理备份：备份数据文件，转储数据库物理文件到某一目录。物理备份恢复速度比较快，但占用空间比较大，MySQL中可以用xtrabackup工具来进行物理备份。
 *   逻辑备份：对数据库对象利用工具进行导出工作，汇总入备份文件内。逻辑备份恢复速度慢，但占用空间小，更灵活。MySQL 中常用的逻辑备份工具为mysqldump。逻辑备份就是备份sql语句，在恢复的时候执行备份的sql语句实现数据库数据的重现。
 *
 * 【逻辑备份与恢复】
 * （1）逻辑备份：使用mysqldump实现逻辑备份
 *      mysqldump –u user –h host –p password 待备份的数据库名称>备份文件名称.sql                                  # 备份一个数据库到指定文件。注意，不包含创建数据库的语句，与加了--databases参数做区分
 *       如：mysqldump -uroot -p password atguigudb1>/var/lib/mysql/atguigu.sql
 *      mysqldump -uroot -p password --all-databases > all_database.sql                                           # 备份所有数据库到指定文件。可以使用 --all-databases 或 -A 参数
 *       mysqldump -uroot -p password -A > all_database.sql                                                        # 备份所有数据库到指定文件。
 *      mysqldump –u 用户名称 –h 主机名称 –p 密码 待备份的数据库名称[tbname, [tbname...]]> 备份文件名称.sql
 *      mysqldump –u user –h host –p password --databases [数据库的名称1 [数据库的名称2...]] > 备份文件名称.sql     # 备份指定的几个数据库到指定文件。可以使用使用 --databases 或 -B 参数。注意：包含了创建数据库的语句
 *       mysqldump –u user –h host –p password -B [数据库的名称1 [数据库的名称2...]] > 备份文件名称.sql
 *      mysqldump –u user –h host –p password 数据库的名称 [表名1 [表名2...]] > 备份文件名称.sql                   # 备份数据库的指定数据表到指定文件
 *      mysqldump -uroot -p password atguigu student --where="id<10" > student_part_id10_low_bak.sql             # 备份单表的部分数据。备份student表中id小于10的数据
 *                                                                                                                  有些时候一张表的数据量很大，我们只需要部分数据。这时就可以使用 --where 选项了。where后面附带需要满足的条件。
 *      mysqldump -uroot -p password atguigu --ignore-table=atguigu.student > no_stu_bak.sql                     # 备份某个数据库，但是不要某个数据表
 *      mysqldump -uroot -p password atguigu --no-data > atguigu_no_data_bak.sql                                 # 只备份数据库的结构，可以使用 --no-data 或简写为 -d
 *      mysqldump -uroot -p password atguigu --no-create-info > atguigu_no_create_info_bak.sql                   # 只备份数据库的数据，可以使用 --no-create-info 或简写为 -t 选项。
 *      mysqldump -uroot -p password -R -E --databases atguigu > fun_atguigu_bak.sql                             # 备份数据库的同时备份存储过程，自定义函数及事件
 *                                                                                                                  mysqldump备份默认是不包含存储过程，自定义函数及事件的。可以使用--routines 或 -R 选项来备份存储过程及函数，使用 --events 或 -E 参数来备份事件。
 *  运行帮助命令 mysqldump --help ，可以获得特定版本的完整选项列表。
 *
 * （2）逻辑恢复：使用mysql命令恢复数据
 *      mysql -uroot -p password < atguigu.sql                                                                   # 恢复一个数据库。备份文件中包含了创建数据库的语句
 *      mysql -uroot -p password atguigu4< atguigu.sql                                                           # 恢复一个数据库，备份文件中不包含了创建数据库的语句，手动指定了数据库名（该数据库需要自己手动提前创建好）
 *      mysql -uroot -p password < all_database.sql                                                              # 恢复所有数据库
 *      sed -n '/^-- Current Database: `atguigu3`/,/^-- Current Database: `/p' all_database.sql > atguigu1111.sql    # 从全量备份中分离出单个库的备份文件。这里即从all_database.sql的全量备份中恢复atguigu3这个数据库这个数据库的备份文件（包含创建库的语句）
 *                                                                                                                       然后再根据 备份文件，恢复单个数据库即可
 *      cat atguigu1111.sql | sed -e '/./{H;$!d;}' -e 'x;/CREATE TABLE `student`/!d;q' > student_structure.sql       # 从单库备份中分离出单个表的结构备份文件，这里即从 atguigu1111.sql 的单库备份中 恢复出student这个单表的结构备份文件
 *                                                                                                                       source class_structure.sql; 通过该指令创建对应的表结构（注意只能在atguigu1111.sql中相同名的库中）
 *       cat atguigu1111.sql | grep --ignore-case 'insert into `class`' > student_data.sql                             # 从单库备份中分离出单个表的数据备份文件，这里即从 atguigu1111.sql 的单库备份中 恢复出student这个单表的数据备份文件
 *                                                                                                                       source class_data.sql; 通过该指令创建对应的表数据（注意只能在atguigu1111.sql中相同名的库中）
 *
 * 【物理备份与恢复】仅对myisam类型数据表有效
 * （1）物理备份
 *  直接将MySQL中的数据库文件复制出来。这种方法最简单，速度也最快。MySQL的数据库目录位置不一定相同：
 *    在Windows平台下，MySQL 8.0存放数据库的目录通常默认为 “ C:\ProgramData\MySQL\MySQL Server 8.0\Data”或者其他用户自定义目录；
 *    在Linux平台下，数据库目录位置通常为/var/lib/mysql/；
 *    在MAC OSX平台下，数据库目录位置通常为“/usr/local/mysql/data”
 *  为了保证备份的一致性。需要保证：
 *    方式1：备份前，将服务器停止。
 *    方式2：备份前，对相关表执行 FLUSH TABLES WITH READ LOCK 操作。这样当复制数据库目录中的文件时，允许其他客户继续查询表。同时，FLUSH TABLES语句来确保开始备份前将所有激活的索引页写入硬盘。
 * 注意，物理备份完毕后，执行 UNLOCK TABLES 来结算其他客户对表的修改行为。
 * 这种方式方便、快速，但不是最好的备份方法，因为实际情况可能 不允许停止MySQL服务器 或者 锁住表 ，而且这种方法 对InnoDB存储引擎 的表不适用。
 *  此外，还可以考虑使用相关工具实现备份。比如， MySQLhotcopy 工具。MySQLhotcopy是一个Perl脚本，它使用LOCK TABLES、FLUSH TABLES和cp或scp来快速备份数据库。它是备份数据库或单个表最快的途径，但它只能运行在数据库目录所在的机器上，并且只能备份MyISAM类型的表。多用于mysql5.5之前。
 *
 * （2）物理恢复
 *  将备份的数据库数据拷贝到数据目录下，并重启MySQL服务器。
 *  使用下面的 chown 操作（增大权限后才能访问copy过来的数据库）
 * 要求：
 *    必须确保备份数据的数据库和待恢复的数据库服务器的主版本号相同。
 *      因为只有MySQL数据库主版本号相同时，才能保证这两个MySQL数据库文件类型是相同的。
 *    这种方式 对 MyISAM类型的表比较有效 ，对于InnoDB类型的表则不可用。
 *      因为InnoDB表的表空间不能直接复制。
 *    在Linux操作系统下，复制到数据库目录后，一定要将数据库的用户和组变成mysql，命令如下：
 *      chown -R mysql.mysql /var/lib/mysql/dbname
 *      其中，两个mysql分别表示组和用户；“-R”参数可以改变文件夹下的所有子文件的用户和组；“dbname”参数表示数据库目录（即我们复制过来的数据库目录）。
 *      提示 Linux操作系统下的权限设置非常严格。通常情况下，MySQL数据库只有root用户和mysql用户组下的mysql用户才可以访问，
 *      因此将数据库目录复制到指定文件夹后，一定要使用chown命令将文件夹的用户组变为mysql，将用户变为mysql。
 *
 * 【】
 @author Alex
 @create 2023-05-29-13:04
 */
public class UB04 {
}
