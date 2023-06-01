package databaseobject;

import org.junit.Test;

/**
 * 【变量、流程控制与游标】
 *
 * 【变量】
 *  承接存储过程与函数，该章节内容均可使用在存储过程与函数中
 *  MySQL 数据库中，变量分为 系统变量 以及 用户自定义变量
 *
 * 【系统变量】
 *  系统变量定义了当前MySQL服务实例的属性、特征。这些系统变量的值要么是 编译MySQL时参数 的默认值，要么是配置文件（例如my.ini等）中的参数值。
 *  ✔系统变量分为全局系统变量（需要添加global关键字，针对所有会话生效）
 *    以及会话系统变量（需要添加session关键字，仅针对当前会话生效）。如果不写，系统变量默认会话级别。
 *  在MySQL中有些系统变量只能是全局的，有些系统变量作用域既可以是全局又可以是会话
 * (1)全局系统变量
 *  ✔全局系统变量针对于所有会话（连接）有效，但 不能跨重启（即重启后系统变量全部重置✔）
 *  SHOW GLOBAL VARIABLES [LIKE ''];         # 查看全局系统变量，不加LIKE就是查看所有
 *  SELECT @@global.变量名;                  # 查看指定的全局系统变量的值
-------
[server]
default-storage-engine=MyISAM                 # 可以通过配置文件设置系统变量的值，这样设置后每次重启mysql服务 系统变量也会生效
-------
 * (2)会话系统变量
 *  MySQL客户机成功连接MySQL服务器后，都会产生与之对应的会话
 *  SHOW SESSION VARIABLES [LIKE ''];        # 查看会话变量
 *  SELECT @@session.变量名;                 # 查看指定的会话变量的值
 *  SELECT @@变量名;                         # 首先在会话系统变量中查询，如果不存在，则在全局系统变量中查询
 * (3) 有些时候，数据库管理员需要修改系统变量的默认值，以便修改当前会话或者MySQL服务实例的属性、特征。
 * 方式1：修改MySQL 配置文件 ，继而修改MySQL系统变量的值（该方法需要重启MySQL服务）————一劳永逸✔
 * 方式2：在MySQL服务运行期间，使用“set”命令重新设置系统变量的值————重启后系统变量全部重置✔
 *  SET GLOBAL 变量名=变量值;                # 关键字式，修改全局系统变量
 *  SET @@global.变量名=变量值;              # 操作符式，修改全局系统变量
 *  SET @@session.变量名=变量值;             # 关键字式，修改会话变量
 *  SET SESSION 变量名=变量值                # 操作符式，修改会话变量
 *
 * 【用户变量】
 *  用户变量根据作为范围不同，分为会话用户变量（全局变量） 和 局部变量
 * （1）会话用户变量
 *  会话用户变量的作用域和会话变量一样，只对 当前连接 会话有效。✔会话用户变量以 一个“@” 开头
 *      SET @用户变量 = 值;                              # 方式1 直接赋值：“=”✔或“:=”
 *      SET @用户变量 := 值;
 *      SELECT @用户变量 := 表达式 [FROM 等子句]; *
 *      SELECT 表达式 INTO @用户变量 [FROM 等子句];      # 方式2 SELECT语句赋值：“:=” 或 INTO关键字✔
 * （2）局部变量
 *  局部变量：只在 BEGIN 和 END 语句块中有效。局部变量只能在 存储过程和函数 中使用。
 *  使用 DECLARE 语句定义一个局部变量，只能放在 BEGIN ... END 中，而且只能放在第一句
 *  BEGIN
 *       DECLARE 变量名1 变量数据类型 [DEFAULT 变量默认值];                  # 声明局部变量，若没有DEFAULT语句，默认值为NULL
 *       DECLARE 变量名2,变量名3,... 变量数据类型 [DEFAULT 变量默认值];      # 合并声明局部变量，多个局部变量为相同的变量数据类型
 *       SET 变量名1 = 值;                                                 # 为局部变量赋值1（或者使用：=, 没有区别）
 *       SELECT 值 INTO 变量名2 [FROM 子句];                               # 为局部变量赋值2（将查询结果赋值给局部变量）
 *       SELECT 变量1,变量2,变量3;                                         # 查看局部变量的值
 *   END
 * (3)会话用户变量与局部变量对比
 *                        作用域                   定义位置                           语法
 * 会话用户变量           当前会话                 会话的任何地方                     加@符号，不用指定类型
 * 局部变量               定义它的BEGIN END中      BEGIN END的第一句话                不用加@,需要指定类型
 *
 * 【MySQL 8.0的新特性—全局变量的持久化】
 *  使用SET GLOBAL语句设置的全局变量只会临时生效。数据库重启后失效。 MySQL 8.0版本新增了全局变量的持久化功能
 *  SET PERSIST GLOBAL max_connections = 1000;                   # 如:设置服务器的最大连接数为1000.永久有效
 *
 @author Alex
 @create 2023-01-19-16:49
 */
public class UDO03 {
    // 异常处理示例
    @Test
    public void test1(){
        /*
        DELIMITER //
        CREATE PROCEDURE UpdateDataNoCondition()
        BEGIN
            #定义处理程序
            DECLARE CONTINUE HANDLER FOR 1048 SET @proc_value = -1;
            SET @x = 1;
            UPDATE employees SET email = NULL WHERE last_name = 'Abel';          # 设置遇到异常继续，实际上是上一个封号到下一个封号之间的内容都不再执行
            SET @x = 2;
            UPDATE employees SET email = 'aabbel' WHERE last_name = 'Abel';
            SET @x = 3;
        END //
        DELIMITER ;
         */
    }

}
