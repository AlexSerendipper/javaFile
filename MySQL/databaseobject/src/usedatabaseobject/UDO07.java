package usedatabaseobject;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;

/**
 * 【触发器】
 *  触发器是由事件来触发某个操作，这些事件包括 INSERT 、 UPDATE 、 DELETE事件。当事件被触发，会自动激发触发器执行相应的操作
 *   CREATE TRIGGER 触发器名称
 *    {BEFORE|AFTER} {INSERT|UPDATE|DELETE} ON 表名
 *    FOR EACH ROW
 *    触发器执行的语句块;
 *  表名 ：表示触发器监控的对象。
 *   BEFORE|AFTER ：表示触发的时间。BEFORE 表示在事件之前触发；AFTER 表示在事件之后触发。
 *   INSERT 表示插入记录时触发；
 *   UPDATE 表示更新记录时触发；
 *   DELETE 表示删除记录时触发。
 *   触发器执行的语句块 ：可以是单条SQL语句，也可以是由BEGIN…END结构组成的复合语句块。
 *  添加的数据的信息，可以用NEW.字段名来表示。如示例
 *   删除的数据信息，可以用OLD.字段名来表示
 * 【触发器的查看与删除】
 *  (1)查看
 *  SHOW TRIGGERS\G                                # 查看当前数据库的所有触发器的定义、状态和语法信息等。
 *  SHOW CREATE TRIGGER 触发器名                    # 查看当前数据库中某个触发器的信息
 *  SELECT * FROM information_schema.TRIGGERS;     # 从系统库information_schema的TRIGGERS表中查询“salary_check_trigger”触发器的信息
 *  (2)删除
 *  DROP TRIGGER IF EXISTS 触发器名称               # 删除所有数据库对象都是一样的
 *
 * 【触发器的优缺点】
 * (1)优点
 *  触发器可以确保数据的完整性。 每次对一个表进行操作时，对另一个表的相关数据可以进行修改
 *  触发器可以帮助我们记录操作日志。
 *  触发器还可以用在操作数据前，对数据进行合法性检查。
 * (2)缺点
 *  触发器最大的一个问题就是可读性差。
 *  相关数据的变更，可能会导致触发器出错
 * (3)注意点
 * 注意，如果在子表中定义了外键约束，并且外键指定了ON UPDATE/DELETE CASCADE/SET NULL子句，此
 * 时修改父表被引用的键值或删除父表被引用的记录行时，也会引起子表的修改和删除操作，但是此时基于子
 * 表的UPDATE和DELETE语句定义的触发器并不会被激活
 *
 @author Alex
 @create 2023-01-22-19:46
 */
public class UDO07 {
    // NEW.的使用
    // 定义触发器“salary_check_trigger”，基于员工表“employees”的INSERT事件，在INSERT之前检查
    // 将要添加的新员工薪资是否大于他领导的薪资，如果大于领导薪资，则报sqlstate_value为'HY000'的错误，从而使得添加失败
    @Test
    public void test1(){
        /*
        DELIMITER //
        CREATE TRIGGER salary_check_trigger
        BEFORE INSERT ON employees FOR EACH ROW
                BEGIN
        DECLARE mgrsalary DOUBLE;
        SELECT salary INTO mgrsalary FROM employees WHERE employee_id = NEW.manager_id;                 # 添加的数据的信息，可以用NEW.字段名来表示
        IF NEW.salary > mgrsalary THEN
        SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = '薪资高于领导薪资错误';
        END IF;
        END //
        DELIMITER ;
         */
    }

    // OLD.的使用
    // 创建触发器emps_del_trigger，每当向emps表中删除一条记录时，同步将删除的这条记录添加到/emps_back1表中
    @Test
    public void test2(){
        /*
        DELIMITER //
        CREATE TRIGGER emps_del_trigger
        BEFORE DELETE ON emps
        FOR EACH ROW
        BEGIN
        INSERT INTO emps_back1(employee_id,last_name,salary)
        VALUES(OLD.employee_id,OLD.last_name,OLD.salary);                      # OLD代表已经被删除的数据
        END //
        DELIMITER ;
         */
    }
}
