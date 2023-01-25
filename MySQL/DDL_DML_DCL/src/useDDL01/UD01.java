package useDDL01;

/**
 * 【命名规范：数据库、表、字段名】
 *  数据库名、表名不得超过30个字符，变量名限制为29个
 *  必须只能包含 A–Z, a–z, 0–9, _共63个字符
 *  数据库名、表名、字段名等对象名中间不要包含空格
 *  同一个MySQL软件中，数据库不能同名；同一个库中，表不能重名；同一个表中，字段不能重名
 *  必须保证你的字段没有和保留字、数据库系统或常用方法冲突。如果坚持使用，请在SQL语句中使用`（着重号）引起来
 *  保持字段名和类型的一致性：在命名字段并为其指定数据类型的时候一定要保证一致性，假如数据类型在一个表里是整数，那在另一个表里就别变成字符型
 *
 * 【数据库操作】
 *  1）创建数据库
 *  CREATE DATABASE 数据库名;                                      # 创建数据库,使用默认字符集
 *  CREATE DATABASE 数据库名 CHARACTER SET 字符集;                 # 创建数据库并指定字符集
 *  CREATE DATABASE IF NOT EXISTS 数据库名;                       # 判断数据库是否已经存在，不存在则创建数据库（推荐）
 *  2）使用数据库
 *  SHOW DATABASES;                                               # 查看当前所有的数据库。有一个S，代表多个数据库
 *  SELECT DATABASE();                                            # 查看当前正在使用的数据库。使用的一个 mysql 中的全局函数
 *  SHOW TABLES FROM 数据库名;                                    # 查看指定库下所有的表
 *  SHOW CREATE DATABASE 数据库名;
 *   SHOW CREATE DATABASE 数据库名\G                                # 查看数据库的创建信息
 *  USE 数据库名;                                                 # 使用/切换数据库
 *  3）修改数据库，不建议使用
 *  ALTER DATABASE 数据库名 CHARACTER SET 字符集;                # 更改数据库字符集
 *  DATABASE不能改名。一些可视化工具可以改名，它是建新库，把所有表复制到新库，再删旧库完成的。
 *  4)删除数据库
 *  DROP DATABASE 数据库名;                                         # 方式1：删除指定的数据库
 *  DROP DATABASE IF EXISTS 数据库名;                              # 方式2：删除指定的数据库（ 推荐 ）
 *
 * 【数据表操作】
 * 1）创建数据表：
 *   CREATE TABLE [IF NOT EXISTS] 表名(           # 直接创建表
 *       字段1 数据类型 [约束条件] [默认值],
 *       字段2 数据类型 [约束条件] [默认值],
 *       [约束条件]
 *       );
 *       可以在生命表的最后使用CHARACTER SET 字符集。指定表的字符集
 *       也可以在声明每个字段的最后使用CHARACTER SET 字符集。指定字段的字符集
 *       ✔如果创建表时没有指定字符集，则使用数据库字符集。如果创建字段时没有指定字符集，则表字符集。如果创建数据库时没有指定字符集，使用默认的my_init中的字符集
 *   CREATE TABLE 表名                            # ✔基于现有的表创建表，同时导入数据，数据类型与现有的表相同
 *    AS
 *    SELECT employee_id,last_name,salary
 *    FROM employees
 *   CREATE TEMPORAL TABLE 表名                   # 创建一个仅当前会话有效的临时表
 *    ...
 * 2) 查看表
 *   SHOW CREATE TABLE [表名\G]:                 # 使用SHOW CREATE TABLE语句不仅可以查看表创建时的详细语句，还可以查看存储引擎和字符编码。
 * 3) 修改表
 *   ALTER TABLE 表名 ADD 字段名 字段类型 [FIRST|AFTER 字段名];                                    # 向已有的表中添加列，默认添加到表中的最后一个字段。
 *                                                                                               # AFTER 字段名，可以将列添加到指定字段名的后面。FIRST设置添加到表中第一个字段
 *   ALTER TABLE 表名 MODIFY 字段名1 字段类型 [约束条件] [DEFAULT 默认值] [FIRST|AFTER 字段名2];    # 修改现有表中的列（数据类型、长度、默认值..）。✔利用alter可以实现字段的移动操作
 *                                                                                               # 通常也不会改类型拉，修改长度比较多。
 *   ALTER TABLE 表名 DROP 字段名                                                                # 删除现有表中的列
 *   ALTER TABLE 表名 CHANGE 字段名2 新列名 新数据类型;                                            # 重命名现有表中的列
 * 4）重命名表
 *   RENAME TABLE 表名1 TO 表名2;             # 二者效果一样，记第一个把
 *   ALTER table 表名1 RENAME [TO] 表名2;
 * 5）删除表：数据和结构都被删除、所有正在运行的相关事务被提交、所有相关索引被删除。
 *   DROP TABLE [IF EXISTS] 数据表1 [, 数据表2, …, 数据表n];
 *   在MySQL中，当一张数据表 没有与其他任何数据表形成关联关系时，可以将当前数据表直接删除。
 * 6）清空表：删除表中所有的数据、表的结构保留。释放表的存储空间
 *   TRUNCATE TABLE 表名1;
 *   TRUNCATE语句不能回滚，而使用DELETE语句删除数据，可以回滚
 *
 @author Alex
 @create 2023-01-16-16:01
 */
public class UD01 {
}
