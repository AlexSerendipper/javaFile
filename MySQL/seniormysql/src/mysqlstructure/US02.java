package mysqlstructure;

/**
 * 【MySQL的相关文件目录】
 *  C:\ProgramData\MySQL\MySQL Server 8.0\Data         　  # 在windows中　存放数据库文件的路径
 *  C:\ProgramData\MySQL\MySQL Server 8.0\my.ini           # 在windows中 存放mySQL配置文件
 *  D:\MySQL\bin                                           # 在windows中 存放mysql可执行文件（命令）
 *
 *  find / -name mysql                   # linux系统下查看Mysql数据目录
 *  /var/lib/mysql/                      # linux系统中 存放数据库文件的路径
 *  /usr/bin                             # linux系统中 存放mysql可执行文件（命令）
 *   /usr/sbin
 *  /usr/share/mysql-8.0（命令及配置文件） #  linux系统中 存放mySQL配置文件
 *   /etc/mysql（如my.cnf）                #  linux系统中 存放mySQL配置文件, 对应windows下的my.ini文件
 *
 * 【文件系统】
 *  InnoDB、MyISAM这样的存储引擎都是把表存储在磁盘上的，操作系统中负责管理和存储文件信息的软件结构
 *  InnoDB、MyISAM实际上都是把表存储在文件系统上
 *  有4个数据库是属于MySQL自带的系统数据库（了解）。
 *     mysql：MySQL 系统自带的核心数据库，它存储了MySQL的用户账户和权限信息，一些存储过程、事件的定
 *                   义信息，一些运行过程中产生的日志信息，一些帮助信息以及时区信息等。
 *     information_schema：MySQL 系统自带的数据库，这个数据库保存着MySQL服务器 维护的所有其他数据库的信息 ，比如有哪些表、哪些视图、哪些触发器、哪些列、哪些索引。
 *                                 这些信息并不是真实的用户数据，而是一些描述性信息，有时候也称之为 元数据 。在系统数据库 information_schema 中提供了一些以innodb_sys 开头的表，用于表示内部系统表。
 *     performance_schema：MySQL 系统自带的数据库，这个数据库里主要保存MySQL服务器运行过程中的一些状态信息，可以用来 监控 MySQL 服务的各类性能指标 。
 *     sys：MySQL 系统自带的数据库，这个数据库主要是通过 视图 的形式把 information_schema 和 performance_schema 结合起来，帮助系统管理员和开发人员监控 MySQL 的技术性能
 *
 * 【数据库/表 在文件系统中的表示】
 * （1） InnoDB 5.7 存储引擎模式
 *     db.opt              # 数据库相关配置，文件用于保存数据库的相关配置。比如：字符集、比较规则。
 *     ibdata1             # 系统表空间：InnoDB会在数据库文件路径 C:\ProgramData\MySQL\MySQL Server 8.0\Data 下， 创建一个名为 ibdata1、大小为12M的文件，
 *                            这个文件就是对应的 系统表空间 在文件系统上的表示。在MySQL5.6.6之前，所以的表数据都默认存储在 系统表空间中
 *     表名.frm            # 表结构：InnoDB会在数据表路径 C:\ProgramData\MySQL\MySQL Server 8.0\Data\test 下，使用 表名.frm 专门来存储表结构
 *     表名.ibd            # 独立表空间：在MySQL5.6.6以及之后的版本中，InnoDB会在数据表路径下，使用 表名.ibd 的格式，为每一个表建立一个独立表空间
 *  可以使用如下 配置文件 设置，指定使用 系统表空间 还是 独立表空间 来存储数据
--------------------
[server]
innodb_file_per_table=0 # 0：代表使用系统表空间； 1：代表使用独立表空间
--------------------
 * （2） InnoDB 8.0 存储引擎模式
 *  MySQL8.0不再提供db.opt文件。
 *  MySQL8.0中不再单独提供b.frm，而是合并在b.ibd文件中。
 * （3） MyISAM 5.7 存储引擎模式：在 MyISAM 中的索引全部都是 二级索引 ，该存储引擎的 数据和索引是分开存放 的
 *     ibdata1             # 系统表空间：InnoDB会在数据库文件路径 C:\ProgramData\MySQL\MySQL Server 8.0\Data 下， 创建一个名为 ibdata1、大小为12M的文件，
 *                            这个文件就是对应的 系统表空间 在文件系统上的表示。在MySQL5.6.6之前，所以的表数据都默认存储在 系统表空间中
 *     表名.frm            # 表结构：MyISAM 会在数据表路径 C:\ProgramData\MySQL\MySQL Server 8.0\Data\test 下，使用 表名.frm 专门来存储表结构
 *     表名.MYD            # 存储数据 (MYData)
 *     表名.MYI            # 存储索引 (MYIndex)
 * （4）MyISAM 8.0 存储引擎模式
 *     ibdata1             # 系统表空间：InnoDB会在数据库文件路径 C:\ProgramData\MySQL\MySQL Server 8.0\Data 下， 创建一个名为 ibdata1、大小为12M的文件，
 *                            这个文件就是对应的 系统表空间 在文件系统上的表示。在MySQL5.6.6之前，所以的表数据都默认存储在 系统表空间中
 *     b.xxx.sdi           # MySQL8.0中 使用sdi文件格式来代替5.7中的 frm文件，来描述表结构文件，
 *     表名.MYD            # 存储数据 (MYData)
 *     表名.MYI            # 存储索引 (MYIndex)
 *  对于视图文件，我们会发现他只有frm格式的文件 并没有idb格式的文件，这也进一步印证了它只是存储结构，并没有数据
 *
 @author Alex
 @create 2023-05-10-13:49
 */
public class US02 {
}
