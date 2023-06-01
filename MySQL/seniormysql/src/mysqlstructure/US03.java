package mysqlstructure;

/**
 * 【用户与权限管理】
 *  MySQL用户可以分为普通用户和root用户，root用户作为超级管理员，拥有包括创建用户、删除用户和修改用户密码等所有权限
 *   而普通用户只拥有被授予的指定权限
 *（1）登录MySQL服务器（完整结构）
 *  mysql –h hostname|hostIP –P port –u username –p DatabaseName –e "SQL语句"             # 启动MySQL服务后，可以通过mysql命令来登录MySQL服务器，命令如下：
 *     -h参数 后面接主机名或者主机IP，hostname为主机，hostIP为主机IP。
 *     -P参数 后面接MySQL服务的端口，通过该参数连接到指定的端口。MySQL服务的默认端口是3306，不使用该参数时自动连接到3306端口，port为连接的端口号。
 *     -u参数 后面接用户名，username为用户名。
 *     -p参数 会提示输入密码。
 *     DatabaseName参数 指明登录到哪一个数据库中。如果没有该参数，就会直接登录到MySQL数据库中，然后可以使用USE命令来选择数据库。
 *     -e参数 后面可以直接加SQL语句。登录MySQL服务器以后即可执行这个SQL语句，然后退出MySQL服务器。
 *（2）创建用户
 *  CREATE USER 用户名 [IDENTIFIED BY '密码'][,用户名 [IDENTIFIED BY '密码']]              # root用户才具有创建其他用户的权限，可以一次创建多个用户
 *     “[ ]”表示可选，也就是说，可以指定用户登录时需要密码验证，也可以不指定密码验证，这样用户可以直接登录。不过，不指定密码的方式不安全，不推荐使用。
 *     如果指定密码值，需要使用IDENTIFIED BY指定明文密码值。
 *     CREATE USER语句可以同时创建多个用户
 *  CREATE USER 'kangshifu'@'localhost' IDENTIFIED BY '123456';                          # 举例，可以指定创建的用户可以被哪个ip访问。✔实际上'kangshifu'@'localhost'才对应了用户的唯一标识
 *（3）修改用户：用户信息实际上存储在user表中，修改用户即修改表中的字段
 *  UPDATE mysql.user SET USER='li4' WHERE USER='wang5';
 *  FLUSH PRIVILEGES;                                                    # 刷新权限，仔修改用户后需要更新一下权限（对表数据进行增删改后都需要刷新权限）
 *（4）删除用户：
 *  DROP USER li4 [@'localhost'] ;                                       # 方式一：删除用户，可选择是否指定访问地址，默认为%（所有ip可访问）
 *  DELETE FROM mysql.user WHERE Host=’hostname’ AND User=’username’;    # 方式二：通过user表中的字段删除指定用户数据
 *   FLUSH PRIVILEGES;                                                      方式二删除需要刷新权限，但是不推荐通过方式二进行删除，系统会有残留信息保留
 * (5) 修改用户密码
 *  ALTER USER USER() IDENTIFIED BY 'new_password';                      # 修改当前登录用户密码：方式一
 *  SET PASSWORD='new_password';                                         # 修改当前登录用户密码：方式二
 *  ALTER USER 'username'@'hostname' [IDENTIFIED BY '新密码']            # root用户修改其他用户密码：方式一
 *  SET PASSWORD FOR 'username'@'hostname'='new_password';               # root用户修改其他用户密码：方式二
 *（6） MySQL8用户密码管理
 *  在MySQL8中，数据库管理员可以 手动设置 账号密码过期，也可以建立一个 自动 密码过期策略。过期策略可以是 全局的 ，也可以为 每个账号 设置单独的过期策略。
 *  ALTER USER 'kangshifu'@'localhost' PASSWORD EXPIRE;                  # 使当前用户密码立刻过期。。过期用户仍然可以登录数据库，但是无法进行查询，除非重新设置密码
 *  ALTER USER 'kangshifu'@'localhost' PASSWORD EXPIRE INTERVAL 90 DAY;  # 设置kangshifu账号密码每90天过期：
 *  ALTER USER 'kangshifu'@'localhost' PASSWORD EXPIRE NEVER;            # 设置密码永不过期
 *  ALTER USER 'kangshifu'@'localhost' PASSWORD EXPIRE DEFAULT;          # 根据我们设置的全局策略，进行密码过期时间的设置
 *     SET PERSIST default_password_lifetime = 180;                         # 建立全局策略，设置密码每隔180天过期
      -------------
      [mysqld]
      default_password_lifetime=180          # 配置文件中建立全局策略，设置密码每隔180天过期。默认为0，即永不过期
      -------------
 *（7） 用户密码重用策略
 *  SET PERSIST password_history = 6;                                      # 全局设置（所有用户生效），设置不能选择最近使用过的6个密码
 *  SET PERSIST password_reuse_interval = 365;                             # 全局设置，设置不能选择最近一年内的密码
 *  ALTER USER 'kangshifu'@'localhost' PASSWORD HISTORY 6;                 # 局部设置（指定用户生效），，设置不能选择最近使用过的6个密码
 *  ALTER USER 'kangshifu'@'localhost' PASSWORD REUSE INTERVAL 365 DAY;    # 局部设置，设置不能选择最近一年内的密码
    ---------
    [mysqld]
    password_history=6                             # 配置文件全局设置
    password_reuse_interval=365                    # 配置文件全局设置
    ---------
 *
 * 【权限管理】
 *  MySQL允许你做你权力以内的事情，不可以越界，比如只允许你做select操作，那么你就不可以做update操作
 * （1）查看用户权限
 *  show privileges;                             # MySQL具有的所有权限查看
 *  show grants                                  # 查看当前登录用户具有的权限
 *  SHOW GRANTS FOR 'user'@'主机地址' ;           # 查看指定用户具有的权限
 *      CREATE和DROP权限：可以创建新的数据库和表，或删除（移掉）已有的数据库和表。如果将MySQL数据库中的DROP权限授予某用户，用户就可以删除MySQL访问权限保存的数据库。
 *      SELECT、INSERT、UPDATE和DELETE权限 ：允许在一个数据库现有的表上实施操作。
 *      SELECT权限：只有在它们真正从一个表中检索行时才被用到。
 *      INDEX权限：允许创建或删除索引，INDEX适用于已有的表。如果具有某个表的CREATE权限，就可以在CREATE TABLE语句中包括索引定义。
 *      ALTER权限：可以使用ALTER TABLE来更改表的结构和重新命名表。
 *      CREATE ROUTINE权限 用来创建保存的程序（函数和程序），ALTER ROUTINE权限用来更改和删除保存的程序
 *        EXECUTE权限：用来执行保存的程序。
 *      GRANT权限：允许授权给其他用户，可用于数据库、表和保存的程序。
 *      FILE权限：使用户可以使用LOAD DATA INFILE和SELECT ... INTO OUTFILE语句读或写服务器上的文件，任何被授予FILE权限的用户都能读或写MySQL服务器上的任何文件
 *       （说明用户可以读任何数据库目录下的文件，因为服务器可以访问这些文件）
 * （2）权限控制授予原则：
 *      只授予能 满足需要的最小权限 ，防止用户干坏事。比如用户只是需要查询，那就只给select权限就可以了，不要给用户赋予update、insert或者delete权限。
 *      创建用户的时候 限制用户的登录主机 ，一般是限制成指定IP或者内网IP段。
 *      为每个用户 设置满足密码复杂度的密码 。
 *      定期清理不需要的用户 ，回收权限或者删除用户。
 * （3）授予权限
 *  GRANT 权限1,权限2,…权限n ON 数据库名称.表名称 TO 用户名@用户地址 [IDENTIFIED BY ‘密码口令’];     # 若授权时没有发现该用户，则会直接新建一个用户。
 *    GRANT SELECT,INSERT,DELETE,UPDATE ON atguigudb.* TO li4@localhost ;                         # 给li4用户授予atguigudb这个库下的所有表的插删改查的权限
 *  GRANT ALL PRIVILEGES ON *.* TO ；li4@'%';                                                   # 给li4用户授予所有数据库下所有表的 所有权限。。。此时li4用户和root用户的唯一区别就是不具备grant的权限
 *                                                                                              如果需要赋予包裹grant的权限，添加参数"WITH GRANT OPTION" 即可
 * （4）回收权限（用户重新登录后才能生效）
 *  REVOKE ALL PRIVILEGES ON *.* FROM li4@'%';                               # 收回全库全表的所有权限
 *  REVOKE SELECT,INSERT,UPDATE,DELETE ON mysql.* FROM joe@localhost;        # 收回mysql库下的所有表的插删改查权限
 *
 * 【权限表】
 *  MySQL服务器通过权限表来控制用户对数据库的访问，权限表存放在 mysql 这个数据库中，在mysql服务启动时，服务器将这些数据库表中的权限信息读入内存
 *  这些权限表中，最重要的是（权限表权限越来越小）：
 * （1）user 表
 *  select host,user,authentication_string from mysql.user                   # 从user表中查看当前用户的可登录的ip地址，用户名 以及 密码
 *（2）db 表
 *  DESCRIBE mysql.db;                                                       # 查看db表结构可以发现，其中存储的是用户（Host、User字段）对某数据库（db字段）是否有指定的权限（其他字段）
 *（3）tables_priv表 和 columns_priv表
 *  DESCRIBE mysql.tables_priv;                                              # 查看表结构发现，tables_priv表中存储的是是用户（Host、User字段）对某数据库（db字段）下某张表（Table_name）是否有指定的权限（其他字段）
 *  DESCRIBE mysql.columns_priv;                                             # 查看表结构发现，columns_priv表中存储的是是用户（Host、User字段）对某数据库（db字段）下某张表（Table_name）的某字段（Column_name） 是否有指定的权限（其他字段）
 *（4）procs_priv表：                                                          # procs_priv表可以对存储过程和存储函数设置操作权限
 *
 * 【访问控制】
 *  连接核实阶段：MySQL服务器接收到用户请求后，会使用user表中的host、user和authentication_string这3个字段匹配客户端提供信息。服务器只有在user表记录的Host和User字段匹配客户端主机名和用户名，并且提供正确的密码时才接受连接。
 *  请求核实阶段：一旦建立了连接，服务器就进入了请求核实阶段。对此连接上进来的每个请求，服务器检查该请求要执行什么操作、是否有足够的权限来执行它，这正是需要授权表中的权限列发挥作用的地方。这些权限可以来自user、db、table_priv和column_priv表。
 *
 * 【角色】
 *  MySQL8.0中引入的角色的概念，角色是权限的集合，可以为角色添加或移除权限，用户可以被赋予角色（同时被授予角色包含的权限）
 * （1）创建角色
 *  CREATE ROLE 'manager'@'localhost';                                      # 创建一个经理的角色
 *  GRANT SELECT ON atguigudb.db TO 'manager'@'localhost'                   # 为经理角色赋予权限
 *  SHOW GRANTS FOR 'manager'@'localhost';                                  # 查看角色权限
 *  REVOKE SELECT ON atguigudb.db FROM 'manager'@'localhost';               # 回收角色的权限
 *  DROP ROLE 'manager'@'localhost';                                        # 删除角色
 *（2）赋予角色
 *  GRANT 'manager' TO 'kangshifu'@'localhost';                             # 将经理角色赋给康师傅
 *  SET DEFAULT 'manager' ALL TO 'kangshifu'@'localhost';                   # 方式一：在mysql中被赋予角色都是默认没有被激活的，需要登录kangshifu账号后进行激活（需要重新登陆）
 *  SET GLOBAL activate_all_roles_on_login=ON;                              # 方式二：设置全局变量，对被赋予的所有角色永久激活
 *  REVOKE 'manager' FROM 'kangshifu'@'localhost';                          # 撤销kangshifu用户的经理角色
 *（3）设置强制角色：为每个创建的账户一个默认角色，强制角色无法被REVOKE 或 DROP
--------------------
[mysqld]
mandatory_roles='role1,role2@localhost,r3@%.atguigu.com'                                    # 方式1：服务启动前设置
--------------------
 *  SET PERSIST mandatory_roles = 'role1,role2@localhost,r3@%.example.com';                # 方式2：系统重启后仍然有效
 *  SET GLOBAL mandatory_roles = 'role1,role2@localhost,r3@%.example.com';                 # 方式3：系统重启后失效
 *
 *
 *
 @author Alex
 @create 2023-05-10-14:40
 */
public class US03 {
}
