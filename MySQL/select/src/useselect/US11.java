package useselect;

import org.junit.Test;

/**
 * 【子查询】子查询指一个查询语句嵌套在另一个查询语句内部的查询。因为很多时候查询需要从结果集中获取数据
 *  SELECT...
 *    FROM...
 *    WHERE... (SELECT...
 *              FROM...
 *              WHERE...)
 *    GROUP BY...
 *    HAVING...(SELECT...
 *              FROM...
 *              WHERE...)
 *  子查询（内查询）在主查询之前一次执行完成。子查询的结果被主查询（外查询）使用 。
 *  子查询要包含在括号内
 *  将子查询放在比较条件的右侧
 *  ✔单行操作符对应单行子查询，多行操作符对应多行子查询
 *  ✔单行操作符对应单行子查询，多行操作符对应多行子查询
 *  可以在WHERE/HAVING/CASE/FROM/ORDER BY/SELECT中使用子查询
 *  ✔✔✔不论是单行还是多行，只要内查询结果是null值（或多行情况结果包含null值）。外查询结果一定为null值
 *
 * 【子查询的分类】
 *  我们按内查询的结果返回一条还是多条记录，将子查询分为 单行子查询 和 多行子查询
 *  按内查询是否被执行多次，将子查询划分为 相关(或关联)子查询 和 不相关(或非关联)子查询 。
 *    如：查询工资大于本部门平均工资的员工信息是相关子查询，因为每次内查询返回的结果不同。
 *        而查询工资大于本公司平均工资的员工信息是非相关子查询，因为每次内查询返回的结果相同。
 *
 * 【单行子查询】
 *  单行操作符：我们使用符号类比较运算符来操作单行子查询。> < = !=等
 *
 * 【多行子查询】
 *  IN 等于列表中的任意一个
 *  ANY 需要和单行比较操作符一起使用，和子查询返回的某一个值比较
 *  ALL 需要和单行比较操作符一起使用，和子查询返回的所有值比较
 *  SOME 实际上是ANY的别名，作用相同，一般常使用ANY
 *
 * 【相关子查询】
 *  SELECT...
 *    FROM table1 "outer"
 *    WHERE... (SELECT...
 *              FROM table2 "inner"
 *              WHERE expr1 = outer.expr2)
 *  相关子查询的关键就是在内查询中使用了外查询的表，建立了相关性
 *
 * 【EXIST与NOT EXIST】看例子，这个用的少，这个看起来更像双重for循环
 *  关联子查询通常也会和 EXISTS操作符一起来使用，用来检查在子查询中是否存在满足条件的行。
 *  EXIST，会在在子查询中查找满足条件的行，查找到一条时，就返回当前行
 *  NOT EXISTS，则相反，会在在子查询中查找不满足条件的行，到查找到一条时，就返回当前行
 *
 * 【✔相关子查询与EXIST的异同】
 *  同：二者都在内查询中使用了外查询的表
 *  异：相关子查询中的内查询返回所有符合条件的行。而exist中仅返回一条匹配到的行
 *
 * 【相关更新/删除】说明子查询在更新删除时也是有使用场景的
 *  使用相关子查询依据一个表中的数据更新另一个表的数据。
 *  使用相关子查询依据一个表中的数据删除另一个表的数据。
 *
 @author Alex
 @create 2023-01-15-17:07
 */
public class US11 {
    // WHERE中使用子查询
    // 需求：谁的工资比Abel高
    @Test
    public void test1() {
    /*
        方式一：自连接
        SELECT e2.last_name,e2.salary
        FROM employees e1,employees e2
        WHERE e1.last_name = 'Abel'
        AND e1.`salary` < e2.`salary`
        方式二：子查询
        SELECT last_name,salary
        FROM employees
        WHERE salary > (
            SELECT salary
            FROM employees
            WHERE last_name = 'Abel'
            );
     */
    }

    // 了解成对子查询:查询与141号或174号员工的manager_id和department_id相同的其他员工的employee_id，manager_id，department_id
    @Test
    public void test2() {
    /*
    (1)标准做法
    SELECT employee_id, manager_id, department_id
    FROM employees
    WHERE manager_id IN
        (SELECT manager_id
        FROM employees
        WHERE employee_id IN (174,141))
    AND department_id IN
        (SELECT department_id
        FROM employees
        WHERE employee_id IN (174,141))
    AND
    employee_id NOT IN(174,141);
    (2)成对做法
    SELECT employee_id, manager_id, department_id
    FROM employees
    WHERE (manager_id, department_id) IN
        (SELECT manager_id, department_id
        FROM employees
        WHERE employee_id IN (141,174))
    AND
    employee_id NOT IN (141,174);
     */
    }

    // CASE中的子查询: 显式员工的employee_id,last_name和location。其中，若员工department_id与location_id为1800
    //                      的department_id相同，则location为’Canada’，其余则为’USA’。
    @Test
    public void test3() {
    /*
    SELECT employee_id, last_name,
        (CASE department_id
        WHEN
            (SELECT department_id FROM departments WHERE location_id = 1800)
        THEN 'Canada'
        ELSE 'USA'
        END) "location"
    FROM employees;
     */
    }

    // HAVING中的子查询
    // FROM中的子查询：查询平均工资最低的部门id
    //  ✔实际上在FROM中使用子查询，就是把查询的结果当作一个表来使用了
    //  在FROM中使用自查询，查询的结果表必须取别名
    @Test
    public void test4() {
    /*
    SELECT department_id
    FROM employees
    GROUP BY department_id
    HAVING AVG(salary) = (
                            SELECT MIN(avg_sal)
                            FROM (
                            SELECT AVG(salary) avg_sal
                            FROM employees
                            GROUP BY department_id
                            ) dept_avg_sal
                        )

    方式二：
    SELECT department_id
    FROM employees
    GROUP BY department_id
    HAVING AVG(salary) <= ALL (
                                SELECT AVG(salary) avg_sal
                                FROM employees
                                GROUP BY department_id
                                )
     */
    }

    // 题目：查询员工中工资大于本部门平均工资的员工的last_name,salary和其department_id
    @Test
    public void test5() {
        //
        /* 方式一：相关子查询
        SELECT last_name,salary,department_id
        FROM employees e1
        WHERE salary > (
                        SELECT AVG(salary)
                        FROM employees e2
                        WHERE e1.department_id = e2.department_id
                        );

        方式二：在from中声明子查询，思路：建立一个各个部门及其对应的平均工资表。拿每个员工去比
                这个 e1.salary > e2.avg_salary ，只能理解为已经按照department_id对应好了，再比的salary
        SELECT last_name,salary,e1.department_id
        FROM employees e1 JOIN (SELECT department_id, AVG(salary) avg_salary
                                FROM employees
                                GROUP BY department_id) e2
        ON e1.department_id = e2.department_id
        WHERE e1.salary > e2.avg_salary;
         */
    }


    // 在ORDER BY中使用子查询：题目：查询员工的id,salary,按照department_name 排序
    @Test
    public void test6() {
        /* 方式一：使用相关子查询
        SELECT employee_id,salary
        FROM employees e
        ORDER BY (
                SELECT department_name
        FROM departments d
        WHERE e.`department_id` = d.`department_id`;
           方式二：使用正常方式
        SELECT employee_id,salary,department_name
        FROM employees e JOIN departments d
        ON e.department_id = d.department_id
        ORDER BY department_name;
         */
    }

    // 一道题：我现在想着有点儿吃力，下次看如果不会就可以删了
    // 若employees表中employee_id与job_history表中employee_id相同的数目不小于2，输出这些相同id的员工的employee_id,last_name和其job_id
    @Test
    public void test7() {
        // 实际上，内循环是一个非等值匹配，匹配结果就是肯定就是job_history中的employee_id，加上我们需要select的其他值
        /*
        SELECT employee_id,last_name,job_id
        FROM employees e1
        WHERE 2<=(SELECT COUNT(employee_id)
                  FROM job_history j
                  WHERE e1.employee_id = j.employee_id);
         */
    }

    // exist的使用：查询公司管理者的employee_id，last_name，job_id，department_id信息
    @Test
    public void test8() {
        /* 方法一：自连接
        SELECT DISTINCT m.employee_id,m.last_name,m.job_id,m.department_id
        FROM employees e JOIN employees m
        ON e.manager_id = m.employee_id;
           方法二：EXIST
        SELECT employee_id,last_name,job_id,department_id
        FROM employees e1
        WHERE EXISTS(
             SELECT *
             FROM employees e2
             WHERE e1.employee_id = e2.manager_id  // 实际上是外循环的一条记录送进来，然后遍历内循环和他匹配，返回与他匹配的行
                                                   // 所有select什么不重要，返回的都是e1中的当前行
        );
         */
    }

    // not exists的使用:查询departments表中，不存在于employees表中的部门的department_id和department_name
    @Test
    public void test9() {
        // 方式一：外连接
        /*
        SELECT department_id,department_name
        FROM departments d
        WHERE NOT EXISTS(SELECT *
                         FROM employees e
                         WHERE d.department_id = e.department_id);
         */
    }

    // 查询平均工资最低的部门信息和该部门的平均工资（相关子查询）
    // select中使用子查询
    @Test
    public void test10() {
        //SELECT department_id,department_name,(SELECT AVG(salary) FROM employees WHERE department_id = d.department_id) sal_
        //FROM employees e JOIN departments d
        //USING(department_id)
        //GROUP BY department_id
        //HAVING AVG(salary) <= ALL(SELECT AVG(salary)
        //                        FROM employees
        //                        GROUP BY department_id);
    }

}
