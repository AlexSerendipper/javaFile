package databaseobject;

import org.junit.Test;

/**
 * 【游标】
 *  游标实际上充当了指针的作用 ，我们可以通过操作游标来对数据行进行操作。
 *  MySQL中游标可以在存储过程和函数中使用
 *
 * 【游标的使用】
 *  游标必须在声明处理程序之前被声明，并且变量和条件还必须在声明游标或处理程序之前被声明。
 *  DECLARE 游标名 CURSOR FOR SELECT employee_id,department_id FROM employees;               # 第一步：创建游标
 *  OPEN 游标名                                                                              # 第二步：打开游标
 *  FETCH 游标名 INTO emp_id,dep_id                                                          # 第三步：使用游标
 *  CLOSE 游标名                                                                             # 第四步：关闭游标
 *  FETCH的作用是使用 cursor_name 这个游标来读取当前行，并且将数据保存到变量中，然后游标指针指到下一行✔
 *  变量必须在声明游标之前就定义好。
 *  游标SELECT查询的结果集中的字段数，必须跟FETCH INTO后面的变量数一致
 *
 *
 @author Alex
 @create 2023-01-22-13:59
 */
public class UDO06 {
    // 游标使用举例：创建存储过程“get_count_by_limit_total_salary()”，声明IN参数 limit_total_salary，DOUBLE类型；声明
    // OUT参数total_count，INT类型。函数的功能可以实现累加薪资最高的几个员工的薪资值，直到薪资总和
    // 达到limit_total_salary参数的值，返回累加的人数给total_count。
    @Test
    public void test1(){
        /*
        DELIMITER //
        CREATE PROCEDURE get_count_by_limit_total_salary(IN limit_total_salary DOUBLE,OUT total_count INT)
        BEGIN
            DECLARE sal DOUBLE DEFAULT 0;
            DECLARE sal_total DOUBLE DEFAULT 0;  # 记录工资总和
            DECLARE num_total INT DEFAULT 0;  # 记录累加人数
            DECLARE cc CURSOR FOR SELECT salary FROM test_emp ORDER BY salary DESC;
            OPEN cc;
            WHILE sal_total < limit_total_salary DO
                FETCH cc INTO sal;
                SET sal_total = sal_total + sal;
                SET num_total = num_total + 1;
            END WHILE;
            SET total_count = num_total;
            CLOSE cc;  # 别忘了关闭游标
        END //
        DELIMITER ;
         */
    }

}
