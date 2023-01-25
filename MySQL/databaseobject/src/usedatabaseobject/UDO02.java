package usedatabaseobject;

import org.junit.Test;

/**
 * 【存储过程】
 *  MySQL从5.0版本开始支持存储过程和函数。存储过程和函数能够将复杂的SQL逻辑封装在一起，
 *   应用程序无须关注存储过程和函数内部复杂的SQL逻辑，而只需要简单地调用存储过程和函数即可。
 *  存储过程的英文是 Stored Procedure 。它的思想很简单，就是一组经过 预先编译的 SQL语句的封装。
 *   一旦存储过程被创建出来，使用它直接通过调用存储过程名即可。
 *  相较于存储函数，存储过程是没有返回值的(return)。
 *  CREATE PROCEDURE 存储过程名(IN|OUT|INOUT 参数名 参数类型,...)
 *   [characteristics ...]
 *   BEGIN
 *      存储过程体
 *   END
 *
 * 【存储过程的形参列表】形参类型可以是 MySQL数据库中的任意类型。
 *  IN ：表示当前参数的输入参数，存储过程只是读取这个参数的值。✔如果没有定义参数种类，默认就是IN
 *  OUT ：当前参数为输出参数，执行完成之后，调用这个存储过程的客户端或者应用程序就可以读取这个参数返回的值
 *  INOUT ：当前参数既可以为输入参数，也可以为输出参数。
 *  characteristics 表示创建存储过程时指定的对存储过程的约束条件，其取值信息如下：
 *   * LANGUAGE SQL ：说明存储过程执行体是由SQL语句组成的，当前系统支持的语言为SQL。
 *   * [NOT] DETERMINISTIC ：指明存储过程执行的结果是否确定。
 *   * { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA } ：指明子程序使用SQL语句的限制。
 *   * SQL SECURITY { DEFINER | INVOKER } ：执行当前存储过程的权限，即指明哪些用户能够执行当前存储过程。
 *   * COMMENT 'string' ：注释信息，可以用来描述存储过程
 *  禁止使用characteristics
 *  存储过程体中可以有多条 SQL 语句，如果仅仅一条SQL 语句，则可以省略 BEGIN 和 END
 *  因为MySQL默认的语句结束符号为分号‘;’。为了避免与存储过程中SQL语句结束符相冲突，需要使用DELIMITER改变存储过程的结束符。
 *   // 显示某员工工资举例
 *    DELIMITER $                          # 指明用$表示执行结束
 *     CREATE PROCEDURE show_someone_salary(IN empname VARCHAR(20),OUT empsalary DOUBLE)              # 有几个参数输入几个IN
 *     BEGIN
 *          SELECT salary INTO empsalary    # 使用INTO返回OUT参数，使用逗号分隔，不用加括号
 *          FROM employees
 *          WHERE last_name = empname;
 *     END //
 *     DELIMITER ;                          # 重新指明用;表示执行结束
 *  利用CALL关键字进行存储过程的调用
 *   // 显示某员工工资举例
 *    SET @empname = 'Abel'               # 设置变量并显式赋值
 *    CALL show_someone_salary(@empname,@empsalary)         # 传入IN变量和OUT变量，如果只有OUT变量也要传入OUT变量
 *    SELECT @empsalary                   # 显示输出的OUT变量，如果输出变量未赋值，将显示NULL值
 *
 * 【存储函数】ySQL支持自定义函数，定义好之后，调用方式与调用MySQL预定义的系统函数一样
 *  CREATE FUNCTION 函数名(参数名 参数类型,...)
 *   RETURNS 返回值类型
 *   [characteristics ...]
 *   BEGIN
 *       函数体                             # 函数体中肯定有 RETURN 语句
 *   END
 *  FUNCTION的形参列表中只有IN参数。
 *  RETURNS子句只能对FUNCTION做指定，对函数而言这是 强制 的。且函数体必须包含一个RETURN value语句。
 *  characteristic 创建函数时指定的对函数的约束。取值与创建存储过程时相同
 *  函数体也用BEGIN…END来表示SQL代码的开始和结束。如果函数体只有一条语句，也可以省略BEGIN…END
 *  SELECT 函数名(实参列表)                # 存储函数的调用。其使用方法与MySQL内部函数的使用方法是一样的
 *
 * 【对比存储函数和存储过程】
 *               关键字                调用语法               返回值                     应用场景
 * 存储过程       PROCEDURE            CALL存储过程()         理解为有0个或多个           一般用于更新
 * 存储函数       FUNCTION             SELECT函数()          只能是一个                  一般用于查询结果为一个值并返回时
 *  此外，存储函数可以放在查询语句中使用，存储过程不行✔。
 *   反之，存储过程的功能更加强大，包括能够执行对表的操作（比如创建表，删除表等）和事务操作，这些功能是存储函数不具备的。
 *
 * 【存储过程和函数的查看、修改、删除】
 * (1)查看
 *     SHOW CREATE PROCEDURE/FUNCTION 存储过程名或函数名               # 方式一：查看存储过程和函数的创建信息
 *     SHOW PROCEDURE/FUNCTION STATUS [LIKE '存储过程名或函数名']      # 方式二：查看存储过程和函数的状态信息，如果不写LIKE模糊查询，将显示所有的存储过程或函数
 *     SELECT * FROM information_schema.Routines                     # 方式三：MySQL中存储过程和函数的信息存储在information_schema数据库下的Routines表中。可以通过查询该表的记录来查询存储过程和函数的信息。
 *      WHERE ROUTINE_NAME='存储过程或函数的名' [AND ROUTINE_TYPE = 'PROCEDURE/FUNCTION'];                    # 如果在MySQL数据库中存在存储过程和函数名称相同的情况，最好指定ROUTINE_TYPE查询条件来指明查询的是存储过程还是函数。
 * (2)修改：修改存储过程或函数，不影响存储过程或函数功能（不能修改函数体），只是修改相关特性（characteristic）
 *     ALTER PROCEDURE/FUNCTION 存储过程或函数的名 [characteristic ...]
 * (3)删除
 *     DROP PROCEDURE/FUNCTION [IF EXISTS] 存储过程或函数的名
 *
 * 【关于存储过程使用的争议】
 *  优点：存储过程可以一次编译多次使用。
 *       可以减少开发工作量、可以减少开发工作量
 *       存储过程的安全性强
 *  缺点：可移植性差
 *       调试困难
 *       它不适合高并发的场景
 *
 @author Alex
 @create 2023-01-19-11:23
 */
public class UDO02 {
    // # 创建存储过程show_mgr_name()，查询某个员工领导的姓名，并用INOUT参数“empname”输入员工姓名，输出领导的姓名。
    @Test
    public void test(){
        /*
        DELIMITER %
        CREATE PROCEDURE show_mgr_name()(INOUT empname VARCHAR(20))
        BEGIN
        SELECT last_name INTO empname
        FROM employees e1
        WHERE employee_id = (SELECT manager_id
                             FROM employees e2
                             WHERE last_name = empname)
        END %
        DELIMITER ;
         */

    }

    // # 创建存储函数count_by_id()，参数传入dept_id，该函数查询dept_id部门的员工人数，并返回，数据类型为整型。
    @Test
    public void test2(){
        /*
        DELIMITER //
        CREATE FUNCTION count_by_id(dept_id INT)
        BEGIN
            RETURN (SELECT COUNT(*) FROM employees WHERE department_id = dept_id);
        END //
        DELIMITER ;

        // 函数调用
        SET @dept_id = 50;
        SELECT count_by_id(@dept_id);
         */
    }
}
