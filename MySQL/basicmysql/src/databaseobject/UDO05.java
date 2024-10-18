package databaseobject;

import org.junit.Test;

/**
 * 【流程控制】
 *  针对于MySQL 的流程控制语句主要有3类。注意：流程控制只能用于存储程序（存储过程和函数）。
 *  此流程控制有区别与于单行函数中的流程控制函数
 * 条件判断语句 ：IF 语句和 CASE 语句
 * 循环语句 ：LOOP、WHILE 和 REPEAT 语句
 * 跳转语句 ：ITERATE 和 LEAVE 语句
 *
 * 【条件判断语句】
 * （1）IF
 *   IF 表达式1 THEN 操作1                         # 表达式可以加括号
 *        [ELSEIF 表达式2 THEN 操作2];……
 *        [ELSE 操作N];                             # ELSE不用加THEN
 *     END IF;
 *  (2)CASE
 *   CASE 表达式
 *        WHEN 值1 THEN 结果1或语句1(如果是语句，需要加分号)
 *        WHEN 值2 THEN 结果2或语句2(如果是语句，需要加分号)
 *        ...
 *        ELSE 结果n或语句n(如果是语句，需要加分号)
 *    END [case]（如果是放在begin end中需要加上case，如果放在select后面不需要）✔
 *  (3)CASE
 *   CASE
 *        WHEN 条件1 THEN 结果1或语句1(如果是语句，需要加分号)
 *        WHEN 条件2 THEN 结果2或语句2(如果是语句，需要加分号)
 *        ...
 *        ELSE 结果n或语句n(如果是语句，需要加分号)
 *    END [case]（如果是放在begin end中需要加上case，如果放在select后面不需要）✔
 *
 * 【循环语句】凡是循环。一定具备 初始化条件、循环条件、循环体、迭代条件
 * (1)LOOP
 *  [loop_label:] LOOP
 *       循环执行的语句;                                  # 循环体中需要配合使用LEAVE的方式退出循环
 *   END LOOP [loop_label];                          # 结束循环处都需要封号
 * (2)WHILE：相当于Java的for。WHILE在执行语句执行时，先对指定的表达式进行判断，如果为真，就执行循环内的语句，否则退出循环
 *  [while_label:] WHILE 循环条件 DO                # while true创建一个无限循环
 *       循环体
 *   END WHILE [while_label];
 * (3)REPEAT：相当于Java的do while。REPEAT循环首先会执行一次循环，然后再进入循环，如果满足退出条件就退出循环
 *  [repeat_label:] REPEAT
 *       循环体的语句;
 *       UNTIL 结束循环的条件表达式                       # 注意，这里不用加封号
 *   END REPEAT [repeat_label];
 * (4)三种循环对比：
 *     这三种循环都可以省略名称，但如果循环中添加了循环控制语句（LEAVE或ITERATE）则必须添加名称✔✔✔
 *     LOOP：一般用于实现简单的"死"循环
 *      WHILE：先判断后执行✔
 *      REPEAT：先执行后判断，无条件至少执行一次
 *
 * 【LEAVE】相当于Java中的break。LEAVE用在循环语句内 或 以BEGIN和END包裹起来的程序体内✔，表示跳出循环或者跳出程序体的操作。
 *
 * 【ITERATE】相当于Java中的continue。只能用在循环语句中，表示退出当次循环，进行下一次循环
 *
 @author Alex
 @create 2023-01-21-19:39
 */
public class UDO05 {
    // IF举例:：声明存储过程“update_salary_by_eid2”，定义IN参数emp_id，输入员工编号。判断该员工
    // 薪资如果低于8000元并且入职时间超过5年，就涨薪500元；否则就涨薪100元。
    @Test
    public void test1(){
        /*
        DELIMITER //
        CREATE PROCEDURE update_salary_by_eid2(IN emp_id INT)
        BEGIN
            DECLARE emp_salary DOUBLE;
            DECLARE hire_year DOUBLE;
            SELECT salary INTO emp_salary FROM employees WHERE employee_id = emp_id;
            SELECT DATEDIFF(CURDATE(),hire_date)/365 INTO hire_year
            FROM employees WHERE employee_id = emp_id;
            IF emp_salary < 8000 AND hire_year > 5
                THEN UPDATE employees SET salary = salary + 500 WHERE employee_id =
                emp_id;
            ELSE
                UPDATE employees SET salary = salary + 100 WHERE employee_id = emp_id;
            END IF;
        END //
        DELIMITER ;
         */
    }

    // case举例:声明存储过程“update_salary_by_eid4”，定义IN参数emp_id，输入员工编号。判断该员工
    // 薪资如果低于9000元，就更新薪资为9000元；薪资大于等于9000元且低于10000的，但是奖金比例
    // 为NULL的，就更新奖金比例为0.01；其他的涨薪100元。
    @Test
    public void test2(){
        /*
        DELIMITER //
        CREATE PROCEDURE update_salary_by_eid4(IN emp_id INT)
        BEGIN
             DECLARE emp_sal DOUBLE;
             DECLARE bonus DECIMAL(3,2);
             SELECT salary INTO emp_sal FROM test_emp WHERE employee_id = emp_id;
             SELECT commission_pct INTO bonus FROM test_emp WHERE employee_id = emp_id;

             CASE WHEN emp_sal<9000 THEN UPDATE test_emp SET salary = 9000 WHERE employee_id = emp_id;
                  WHEN emp_sal<10000 && bonus IS NULL THEN UPDATE test_emp SET commission_pct = 0.01 WHERE employee_id = emp_id;
                  ELSE UPDATE test_emp SET salary = salary+100 WHERE employee_id = emp_id;
             END CASE;
        END //
        DELIMITER ;
         */
    }

    // 退出程序体示例
    @Test
    public void test3(){
        /*
        DELIMITER //
        CREATE PROCEDURE leave_begin(IN num INT)
        begin_label: BEGIN                                 # 给程序体取名
        IF num<=0
        THEN LEAVE begin_label;                            # 退出程序体
        ELSEIF num=1
        THEN SELECT AVG(salary) FROM employees;
        ELSEIF num=2
        THEN SELECT MIN(salary) FROM employees;
        ELSE
        SELECT MAX(salary) FROM employees;
        END IF;
        SELECT COUNT(*) FROM employees;
        END //
        DELIMITER ;
         */
    }


}
