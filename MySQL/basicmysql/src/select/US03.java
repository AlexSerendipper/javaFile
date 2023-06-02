package select;

/**
 * 【SQL】
 *  ANSI制定了SQL语言的规范，但是不同的数据库厂商都有其特有的内容，即SQL规范就像是普通话，
 *    不同的数据库厂商如MySQL、Oracle有其特有方言
 *
 * 【SQL的分类】SQL语言在功能上主要分为如下3大类：
 * (1)DDL（Data Definition Languages、数据定义语言）：针对数据库和数据表的操作。
 * 这些语句定义了不同的数据库、表、视图、索引等数据库对象，还可以用来创建、删除、修改数据库和数据表的结构。
 *  主要的语句关键字包括 CREATE/DROP/ALTER/RENAME/TRUNCATE 等。
 * (2)DML（Data Manipulation Language、数据操作语言）：针对记录的操作，增删改查。
 * 用于添加、删除、更新和查询数据库记录，并检查数据完整性。
 *  主要的语句关键字包括 INSERT/DELETE/UPDATE/SELECT 等。✔SELECT是SQL语言的基础，最为重要。
 * (3)DCL（Data Control Language、数据控制语言）用于定义数据库、表、字段、用户的访问权限和安全级别。
 *  主要的语句关键字包括 GRANT/REVOKE/COMMIT/ROLLBACK/SAVEPOINT 等。
 *  有些人把查询语句SELECT单拎出来一类：DQL（数据查询语言）。
 *  还有单独将 COMMIT、ROLLBACK取出来作一类称为TCL（Transaction Control Language，事务控制语言）。
 *
 * 【SQL语言的规则与规范】
 *  1）基本规则
 *  SQL可以写在一行或者多行。为了提高可读性，各子句分行写，必要时使用缩进每条
 *  命令以 ; 结束(或 \g 或 \G)
 *  关键字不能被缩写也不能分行
 *  必须保证所有的()、单引号、双引号是成对结束的
 *  必须使用英文状态下的半角输入方式
 *  字符串型和日期时间类型的数据可以使用单引号（' '）表示
 *  列的别名，尽量使用双引号（" "），而且不建议省略as
 *  2）注释规则
 *  单行注释：#注释文字(MySQL特有的方式)
 *  单行注释：-- 注释文字(--后面必须包含一个空格。)
 *  多行注释：/* 注释文字 *
 *  3）命名规则
 *  数据库、表名不得超过30个字符，变量名限制为29个
 *  必须只能包含 A–Z, a–z, 0–9, _共63个字符
 *  数据库名、表名、字段名等对象名中间不要包含空格
 *  同一个MySQL软件中，数据库不能同名；同一个库中，表不能重名；同一个表中，字段不能重名
 *   必须保证你的字段没有和保留字、数据库系统或常用方法冲突。如果坚持使用，请在SQL语句中使用`（着重号）引起来
 *  保持字段名和类型的一致性，在命名字段并为其指定数据类型的时候一定要保证一致性。假如数据
 *   类型在一个表里是整数，那在另一个表里可就别变成字符型了
 *  4）大小写规范
 *  MySQL 在 Windows 环境下是大小写不敏感的
 *  MySQL 在 Linux 环境下是大小写敏感的
 *  推荐采用统一的书写规范：✔
 *          数据库名、表名、表别名、字段名、字段别名等都小写
 *          SQL关键字、函数名、绑定变量等都大写
 *
 *【导入现有的数据表】
 *  在cmd中使用：source + .sql文件的路径
 *  基于图形化界面工具导入数据：工具==>执行sql脚本
 *
 *【基本的查询语句】
 *  SELECT...FROM...WHERE...               # SELECT 字段1，字段2... FROM 表名 WHERE 过滤条件
 *  SELECT*FROM...                         # *表示表中的所有字段
 *  SELECT first_name AS "名"              # AS全程alias(别名)。别名使用双引号，以便在别名中包含空格或特殊的字符并区分大小写。
 *   FROM employees;                        # 如果别名中不包含空格或特殊的字符，可以省略双引号
 *  SELECT DISTINCT department_id FROM employees;                # 使用DISTINCT关键字可以去除重复行
 *  SELECT * FROM `order`;                # 我们需要保证表中的字段、表名等没有和保留字、数据库系统或常用方法冲突。如果真的相同，请在SQL语句中使用一对``（着重号）引起来。
 *  SELECT '尚硅谷', last_name FROM employees;                 # 第一列作为查询常数出现，个数匹配后面的列。如last_name有20条数据，第一列就有20个'尚硅谷'
 *  SELECT '尚硅谷','牛逼' FROM DUAL       # DUAL表示伪表，因为查询常数不需要从哪个表中就能获取
 *  DESC employees;                       # 使用DESCRIBE 或 DESC 命令，表示表结构,即表中字段的详细信息。
 *
 @author Alex
 @create 2023-01-12-18:33
 */
public class US03 {
}