package useselect;

/**
 * 【排序】
 *  SELECT...FROM...WHERE...ORDER BY salary ASC/DESC;
 *  如果没有使用排序，查询返回的数据是按照添加数据的顺序返回
 *  ASC（ascend）升序,DESC（descend）降序，ORDER BY子句需要放在SELECT语句中WHERE的后边
 *  如果没有指明排序的方式，则默认按照升序排列
 *  ✔列的别名只能在ORDER BY中使用，不能在WHERE中使用
 *    因为SELECT语句执行过程是从FROM ==> WHERE ==> SELECT ==> ORDER BY。别名是在SELECT中声明的，所以WHERE中不能用
 *  SELECT...FROM...ORDER BY salary ASC, last_name DESC。二级排序按照姓名降序排序
 *
 * 【分页】所谓分页显示，就是将查询结果，按照设定的条件一页一页显示
 *  SELECT...FROM...WHERE...ORDER...LIMIT 位置偏移量,行数;
 *  LIMIT需要放在SELECT语句的最后
 *  第一个“位置偏移量”参数指示MySQL从哪一行开始显示，是一个可选参数，如果不指定“位置偏移量”，将会从表中的第一条记录开始
 * （第一条记录的位置偏移量是0，第二条记录的位置偏移量是1，以此类推）；第二个参数“行数”指示返回的记录条数
 *  分页显式公式：LIMIT（当前页数-1）*每页条数，每页条数
 *
 * 【MySQL8.0分页新特性】就是行数和位置偏移量颠倒了一下，引入了OFFSET关键字
 *  SELECT...FROM...WHERE...ORDER...LIMIT 行数 OFFSET 位置偏移量,;
 * 
 @author Alex
 @create 2023-01-13-14:01
 */
public class US05 {
}
