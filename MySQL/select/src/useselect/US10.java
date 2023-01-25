package useselect;

/**
 * 【常见的聚合函数】可以对数值型数据使用AVG和SUM函数
 *  聚合函数：聚合（或聚集、分组）函数，它是对一组数据进行汇总的函数，输入的是一组数据的集合，输出的是单个值
 * 1）AVG()和SUM()的函数：只能对数值型数据使用AVG和SUM函数（自动过滤null值）
 * 2）MIN()和MAX()函数：可以对任意数据类型的数据使用 MIN 和 MAX 函数（自动过滤null值）
 * 3）COUNT()：返回表中记录总数，适用于任意数据类型
 *     COUNT(查询常数):相当于遍历了每个组中行的个数
 *     COUNT(*):同样相当于遍历了每个组中行的个数
 *     COUNT(具体字段):✔返回每个组中具体字段expr不为空的记录总数
 *     AVG(expr) = SUM(expr) / COUNT(expr)✔：注意，很多时候我们计算总数时需要包括null值的个数。可以这样计算AVG(IFNULL(expr,0))
 *  注意：在MySQL中，聚合函数是无法嵌套的
 *
 * 【GROUP BY】使用GROUP BY子句将表中的数据分成若干组
 *  SELECT...FROM...WHERE...GROUP BY...ORDER BY...LIMIT...
 *    GROUP BY中可以添加多个字段，相当于是二级分组，如GROUP BY department_id,job_id。一级按照部门分类，二级按照工种分类
 *    ✔实际上一级按照部门分类，二级按照工种分类 与 一级按照工种分类，二级按照部门分类的效果是一样的。
 *    ✔SELECT中的字段一定要出现在GROUP BY中，反之则不一定
 *        如：SELECT department_id,job_id FROM employees GROUP BY department_id。是非常错误的，因为按照部门来分类的话每个部门无法对应一个工种了
 *  GROUP BY department_id WITH ROLLUP。
 *    可以在GROUP BY中使用WITH ROLLUP。这样查询出来的结果会多一条记录，表示对分组后的数据求平均值
 *    因为多了一条数据。所以当使用ROLLUP时，不能同时使用ORDER BY子句进行结果排序
 *
 * 【HAVING】用于过滤数据
 *  SELECT...FROM...WHERE...GROUP BY...HAVING...ORDER BY...LIMIT...
 *    HAVING 不能单独使用，必须要跟GROUP BY一起使用，必须声明在GROUP BY的后面
 *    如果在过滤条件中需要使用聚合函数，则必须使用HAVING，不能使用WHERE
 *    ✔HAVING中也可以声明普通的过滤条件，但是推荐把普通的过滤条件放在WHERE中，提高执行效率
 *
 * 【SQL的底层执行原理】SELECT语句的执行过程
 * （1）SELECT语句的完整结构
 *   SQL92中：SELECT...FROM...WHERE...GROUP BY...ORDER BY...HAVING...LIMIT...
 *      92中，WHERE中声明多表的连接条件以及不包含聚合函数的过滤条件
 *   SQL99中：SELECT...
 *             FROM...JOIN...ON...
 *             WHERE...
 *             GROUP BY...
 *             HAVING...
 *             ORDER BY...
 *             LIMIT...
 *      on中放多表的连接条件
 *      99中，WHERE中只声明不包含聚合函数的过滤条件
 * （2）✔SELECT语句的执行过程。实际上每个步骤都会产生虚拟表，基于上一步骤的虚拟表进行不断的迭代
 *     ① FROM ...,...       查询到多个表（实际上进行了笛卡尔积的擦欧总）
 *       on...              根据多表的连接条件筛选掉一大部分数据
 *       LEFT/RIGHT JOIN    根据左/右外连接过滤数据
 *       WHERE
 *       GROUP BY...HAVING
 *     ② SELECT             过滤字段，实际上步骤一就是对行进行过滤。
 *       DISTINCT           实际上去重操作就在SELECT之后
 *     ③ ORDER BY...LIMIT
 *   实际上根据这个执行过程就能解释了为什么要把普通的过滤条件放在WHERE中。因为WHERE执行先于HAVING，先对数据进行一波筛选，当然效率高
 *    也解释了为什么只有HAVING中能声明包含聚合函数的过滤条件，因为聚合函数的使用肯定是要基于多组数据的，即再GROUP BY之后
 *    也解释了为什么在SELECT语句中取的别名，只能在ORDER BY中使用
 *
 @author Alex
 @create 2023-01-15-13:31
 */
public class US10 {
    /*
     * 例：查询所有部门的名字，及其员工数量
     * SELECT department_name,location_id,COUNT(*)
     * FROM departments d LEFT JOIN employees e
     * ON d.department_id = e.department_id
     * GROUP BY department_name,location_id
     *  这个时候用COUNT(*)就错了，因为这时候是将department_name和location_id都相同的分为一个组，这时候那些没有员工的部门也作为一个组，COUNT结果为1
     *  所以应该要统计每个组中员工数量，即COUNT(employee_id)
     *
     * 例：查询每个工种、每个部门的部门名、工种名和最低工资
     * SELECT department_name,job_id,MIN(salary)
     * FROM departments d LEFT JOIN employees e
     * ON e.`department_id` = d.`department_id`
     * GROUP BY department_name,job_id
     *
     */
}
