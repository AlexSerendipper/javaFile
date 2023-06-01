package DDL_DML_DCL;

import org.junit.Test;

/**
 * DML之增删改
 *【增】添加/插入数据
 * (1)一条条的添加数据
 *  INSERT INTO 表名 VALUES (value1,value2,....)                   # 没有声明字段，故输入各个字段的值，一一对应，将数据插入到表中
 *  INSERT INTO 表名(column1，column2) VALUES (value1,value2)      # 为指定的的字段添加数据
 *  INSERT INTO table_name(column1, column2) VALUES               # INSERT语句可以同时向数据表中插入多条记录，每个值列表之间用逗号分隔
 *    (value1 [,value2, …, valuen]),
 *    (value1 [,value2, …, valuen]),
 *             ……
 *    (value1 [,value2, …, valuen]);
 * (2)将查询结果插入到表中
 *  INSERT INTO 表名1(column1，column2)
 *   SELECT column1，column2
 *   FROM 表名2
 *   WHERE...
 *  注意：表1中要添加数据的字段长度，不能低于表2中查询字段的长度，否则可能会添加失败（精度损失）
 *
 *【删】
 * DELETE FROM 表名
 * WHERE condition;
 *  注意: 如果不加WHERE筛选条件，是可以实现批量删除的
 *  多表删除：可以使用类似多表查询的操作同时删除多表的数据，见下方例题
 *
 *【改】
 *  UPDATE 表名
 *   SET column1=value1, column2=value2, … , column=valuen
 *   WHERE condition;
 *  注意: 如果不加WHERE筛选条件，是可以实现批量修改的
 *
 *【MySQL8新特性：计算列】✔即某一列的值是通过别的列计算得来的，这个好用啊。
 * (1)CREATE TABLE支持增加计算列。
 *  CREATE TABLE tb1(
 *    a INT,
 *    b INT,
 *    c INT GENERATED ALWAYS AS (a + b) VIRTUAL                # C字段即为计算列
 *    );
 * (2) ALTER TABLE支持增加计算列
 *
 *
 *
 *
 *
 *
 * 【DDL和DML的说明】
 *  DDL的操作一旦执行，就不可以回滚
 *  DML的操作，默认情况下一旦执行后会自动提交，故不能回滚，可以通过在执行DML之前，设置了SET autocommit = FALSE。则DML可以回滚
 *
 * 【对比TRUNCATE TABLE 和 DELETE FROM】
 *  相同点: 都可以实现对表中数据的删除，同时保留表的结构
 *  不同点：TRUNCATE TABLE属于DDL，一旦执行此操作，数据不可以回滚
 *           DELETE FROM属于DELETE FROM，一旦执行此操作，表数据可以全部清除（不带WHERE），并且可以实现回滚
 *
 * 【DCL】commit & rollback
 *  COMMIT：提交数据，一旦执行了COMMIT操作，数据就永久保存在了数据库当中，意味着数据不可以回滚
 *  ROLLBACK: 回滚数据到最近的一次COMMIT之后
 *
 * 【MySQL8新特性—DDL的原子化】✔
 *  该特性体现了事务的完整性，即DDL操作要么成功要么回滚
 *   如：CREATE TABLE book1
 *       DROP TABLE book1,book2     由于book2不存在。此时book1由于该新特性不会被删除。而在MySQL5.7中,book1会被删除
 *
 @author Alex
 @create 2023-01-17-10:30
 */
public class UD02 {
    // 多表删除：删除employees和departments中department_id为100的数据删除
    @Test
    public void test1(){
        /*
        DELETE e,d
        FROM employees e JOIN departments
        USING(department_id)
        WHERE department_id = 100;
         */
    }
}
