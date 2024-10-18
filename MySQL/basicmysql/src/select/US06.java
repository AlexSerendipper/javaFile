package select;

/**
 *
 *                   【employees】          【departments】          【locations】
 *                    employee_id           department_id            location_id
 *                    first_name            department_name          street_address
 *                    last_name             manager_id               postal_code
 *                    email                 location_id              city
 *                    phone_number                                   state_province
 *                    hire_date                                      country_id
 *                    job_id
 *                    salary
 *                    commission_pct
 *                    manager_id
 *                    department_id
 *
 *【多表查询】非常重要
 *  多表查询，也称为关联查询，指两个或更多个表一起完成查询操作。
 *  前提条件：这些一起查询的表之间是有关系的（一对一、一对多），它们之间一定是有关联字段，
 *   这个关联字段可能建立了外键，也可能没有建立外键。比如：员工表和部门表，这两个表依靠“部门编号”进行关联
 *  SELECT employee_id,department_name
 *   FROM employees,departments;
 *    错误：每个员工与每个部门都匹配了一遍。。。标准的笛卡尔积（或称为交叉连接）错误
 *    笛卡尔乘积是一个数学运算。假设我有两个集合 X 和 Y，那么 X 和 Y 的笛卡尔积就是 X组合
 *    错误的原因是因为缺少了多表的连接条件
 *  SELECT employee_id,department_name,e.department_id
 *   FROM employees e,departments d
 *   WHERE e.department_id = d.department_id
 *    目前数据中employee_id中有107条数据，有一个员工没有department_id。最后匹配结果为106条数据
 *    需要加入有效的连接条件避免笛卡尔积错误。✔如果有n个表实现多表的查询，则需要至少需要n-1个连接条件
 *    如果查询语句中出现了多个表中都存在的字段，则必须指明此字段所在的表
 *    从sql优化的角度，建议多表查询时，每个字段前都指明其所在的表
 *    建议给表起别名，增强可读性。✔注意：起了别名后必须使用别名
 *
 * 【等值连接和非等值连接】多表查询分类1，连接条件使用 = 的连接为等值连接
 *  SELECT e.last_name,e.salary,j.grade_level
 *   FROM employees e,job_grades j
 *   WHERE e.salary BETWEEN j.lowest_sal AND j.highest_sal;
 *  非等值连接实际上就是运用了笛卡尔积，由于两个表中并没有建立连接，所以根据笛卡尔积匹配出所有员工工资的对应等级
 *
 * 【自连接和非自连接】多表查询分类2
 *  以上部分均为自连接操作
 *  SELECT emp.employee_id,emp.last_name,e.employee_id,e.last_name
 *   FROM employees as emp, employees as e
 *   WHERE emp.manager_id = e.employee_id
 *    查询员工id及姓名，及其管理者的id和姓名
 *    自连接：当table1和table2本质上是同一张表，只是用取别名的方式虚拟成两张表以代表不同的意义。表中数据自我引用，称为自连接
 *    实际上可以把emp看作是员工表，e看作是管理者表
 *
 * 【内连接】多表查询分类3，这里涉及到SQL92语法和SQL99语法的迭代，SQL99语法更复杂，但是可读性更强
 *  ✔多表查询结果不包含不匹配的结果称为内连接
 *  SQL92语法实现内连接就是我们常用的写法...
 *  SELECT employee_id,department_name,e.department_id
 *   FROM employees e,departments d
 *   WHERE e.department_id = d.department_id
 *  SQL99语法实现内连接需要使用JONI ON的方式，JOIN一张表ON一下连接条件
 *  SELECT employee_id,department_name,e.department_id,city
 *   FROM employees e INNER JOIN departments d
 *   ON e.department_id = d.department_id
 *   JOIN location l
 *   ON d.location_id = l.location_id
 *    目前数据中employee_id中有107条数据，有一个员工(老板)的department_id为null。最后匹配结果为106条数据
 *    INNER可以省略✔
 *
 * 【外连接】
*   多表查询结果包含不匹配的结果称为外连接。返回左（右）表中不满足条件的结果称为左（右）外连接。返回表中所有满足和不满足条件的结果称为满外连接
 *  左外连接物理意义：左表匹配右表，一个员工(老板)的department_id为null的数据没有被显示。故加上内连接的106条数据，最后匹配结果为107条数据(显示所有员工)
 *  右外连接物理意义：右表匹配左表，departments中16个部门并没有员工。故加上内连接的106条数据，最后匹配结果为122条数据（显示所有部门）
 *（1）SQL92语法实现外连接，实现查询所有员工的相关信息。我们需要左表中不满足条件是数据，所以左外连接，是左表数据少了，所以在右表中使用+即可
 *  SELECT employee_id,department_name，e.department_id
 *   FROM employees e,departments d
 *   WHERE e.department_id = d.department_id(+)
 *    注意：MySQL中不支持SQL92的外连接写法✔
 *（2）SQL99语法实现外连接，实现查询所有员工的相关信息。
 *  SELECT employee_id,department_name,e.department_id
 *   FROM employees e LEFT/RIGHT OUTER JOIN departments d
 *   ON e.department_id = d.department_id
 *（3）SQL99语法实现满外连接
 *  SELECT employee_id,department_name,e.department_id
 *   FROM employees e FULL OUTER JOIN departments d
 *   ON e.department_id = d.department_id
 *    OUTER可以省略✔
 *    注意：MySQL中不支持SQL99的满外连接写法✔
 *
 *  【UNION】
 *   UNION操作符: 返回两个查询的结果集的并集，去除重复记录。如上例中，左外连接返回结果集106+1，右外连接返回106+16。UNION结果为1 + 106 + 16
 *   UNION ALL操作符：返回两个查询的结果集的并集。对于两个结果集的重复部分，不去重。UNION ALL结果为1 + 106 + 106 + 16
 *   执行UNION ALL语句时所需要的资源比UNION语句少。如果明确知道合 并数据后的结果数据不存在重复数据，或者不需要去除重复的数据，则尽量使用UNION ALL语句，以提高数据查询的效率
 *
 * 【其中JOIN的实现】图见Xmind
 *  中图：内连接见上
 *  左上图：左外连接见上
 *  右上图：右外连接见上
 *  左中图：由左外连接去除重复部分得到,理应得到一条数据
 *    SELECT employee_id,department_name,e.department_id
 *     FROM employees e LEFT OUTER JOIN departments d
 *     ON e.department_id = d.department_id
 *     WHERE d.department_id IS NULL;                          # 拿老板匹配。d.department_id一定为null
 *  右中图：由右外连接去除重复部分得到,理应得到16条数据
 *    SELECT employee_id,department_name,e.department_id
 *     FROM employees e RIGHT OUTER JOIN departments d
 *     ON e.department_id = d.department_id
 *     WHERE e.department_id IS NULL;                          # 拿16个部门并没有员工匹配。e.department_id一定为null
 *  左下图：满外连接，拿左上+右中图。或拿左中＋右上
 *  SELECT employee_id,department_name,e.department_id
 *   FROM employees e LEFT OUTER JOIN departments d
 *   ON e.department_id = d.department_id
 *   UNION ALL
 *   SELECT employee_id,department_name,e.department_id
 *   FROM employees e LEFT OUTER JOIN departments d
 *   ON e.department_id = d.department_id
 *   WHERE d.department_id IS NULL;
 *  右下图：左中+右中图
 *
 *
 * 【SQL99语法新特性】上述知识已经能够完全满足用途，这里相当于知识拓展，看到别人这样写要能看得懂
 * （1）自然连接： NATURAL JOIN
 *    它会帮你自动查询两张连接表中 所有相同的字段 ，然后进行等值连接。即下列两种方式等价
 *   SELECT employee_id,department_name,e.department_id
 *    FROM employees e NATURE JOIN departments d
 *
 *   SELECT employee_id,department_name,e.department_id
 *    FROM employees e JOIN departments d
 *    ON e.department_id = d.department_id
 *    AND e.manager_id = d.manager_id;
 *
 * （2）USING的使用：用 USING 指定数据表里的同名字段进行等值连接。只能配合JOIN一起使用
 *   SELECT employee_id,department_name,e.department_id
 *    FROM employees e JOIN departments d
 *    ON e.department_id = d.department_id;
 *
 *   SELECT employee_id,department_name,e.department_id
 *    FROM employees e JOIN departments d
 *    USING(department_id);
 @author Alex
 @create 2023-01-13-14:41
 */
public class US06 {
}
