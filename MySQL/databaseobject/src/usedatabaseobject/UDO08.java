package usedatabaseobject;

import org.junit.Test;

import java.util.TreeSet;

/**
 * 【MySQL8新特性概述】详见pdf，大部分企业都还是用5.7，所以这部分知识主要了解即可
 *  (1)新的特性
 *  1、更简便的NoSQL支持
 *  2、更好的索引：新增了隐藏索引和降序索引
 *  3、更完善的JSON支持
 *  4、安全和账户管理：新增了caching_sha2_password的加密方式
 *  5、InnoDB：作为MySQL默认的存储引擎，作了大量改进和优化
 *  6、数据字典
 *  7、原子数据定义语句：要么成功、要么回滚
 *  8、资源管理：让整个资源分配更加合理
 *  9、字符集支持：默认字符集改为utf8mb4
 *  10、优化器增强：
 *  11、公用表表达式（可以用于替换子查询）
 *  12、窗口函数：单行函数、聚合函数、窗口函数三分天下
 *  13、正则表达式支持：
 *  14、内部临时表
 *  15、日志记录
 *  16、备份锁
 *  17、增强的MySQL复制
 *  (2)抛弃的旧特性
 *  1. 查询缓存 查询缓存已被移除
 *  2.加密相关
 *  3.空间函数相关 在MySQL 5.7版本中，多个空间函数已被标记为过时。
 *  4.\N和NULL 在SQL语句中，解析器不再将\N视为NULL，所以在SQL语句中应使用NULL代替\N。
 *  5. mysql_install_db 在MySQL分布中，已移除了mysql_install_db程序
 *  6.通用分区处理程序 通用分区处理程序已从MySQL服务中被移除。
 *  7.系统和状态变量信息 在INFORMATION_SCHEMA数据库中，对系统和状态变量信息不再进行维护。
 *  8.mysql_plugin工具 mysql_plugin工具用来配置MySQL服务器插件，现已被删除
 *
 *
 * 【窗口函数】
 *  窗口函数的作用类似于在查询中对数据进行分组，不同的是，分组操作会把分组的结果聚合成一条记录，如：GROUP BY department_id，相同的部门编号一行显示
 *  而窗口函数是将结果置于每一条数据记录中。
 *   而窗口函数介于单行函数和聚合函数之间。输出的结果数与输入数相同（类似于单行函数）。输出的结果按照指定要求分类显示，相同类别放在一块（类似于聚合函数）
 *   由于可以分组，并且可以在分组内排序，不会因为分组而减少原表中的行数。所以对我们在原表数据的基础上进行统计和排序非常有用。
 *  窗口函数可以分为 静态窗口函数 和 动态窗口函数 （了解即可）
 *    函数 OVER (PARTITION BY 字段名 ORDER BY 字段名 ASC|DESC) FROM...             # 窗口函数语法结构方式一，PARTITION就是按片显示拉~~
 *    函数 OVER w FROM...                                                         # 窗口函数语法结构方式二，就是OVER后的东西取了个别名罢了
 *      WINDOW w AS (PARTITION BY 字段名 ORDER BY 字段名 ASC|DESC)
 *    注意： PARTITION BY按照指定的字段名分组，如果不需要也可以不分组，直接ORDER BY
 * (1)序号函数，见示例
 *    ROW_NUMBER()：顺序排序
 *    RANK()：能够对序号进行并列排序，并且会跳过重复的序号，比如序号为1、1、3
 *    DENSE_RANK(): 能够对序号进行并列排序，并且不会跳过重复的序号，比如序号为1、1、2。
 * (2)分布函数
 *    PERCENT_RANK()：等级值百分比函数，有点像计算排名的占比。按照如下方式进行计算 (rank - 1) / (rows - 1)      # rank为rank排序的序号，rows为总行数
 *    CUME_DIST()：累积分不知。主要用于查询小于或等于某个值的比例。有点像计算课程标准分
 * (3)前后函数
 *    LAG(expr,n): 当前行的前n行的expr的值。
 *    LEAD(expr,n): 当前行的前n行的expr的值。
 * (4)首尾函数
 *    FIRST_VALUE(expr)：返回第一个expr的值。
 *    LAST_VALUE(expr)：返回最后一个expr的值。
 * (5)其他函数
 *    NTH_VALUE(expr,n)函数返回第n个expr的值
 *    NTILE(n)函数将分区中的有序数据分为n个桶，记录桶编号。
 *
 * 【公用表表达式】（或通用表表达式）简称为CTE（Common Table Expressions）
 *  CTE是一个命名的临时结果集✔。CTE可以理解成一个可以复用的子查询✔，当然跟子查询还是有点区别的，
 *   CTE可以引用其他CTE，但子查询不能引用其他子查询。所以，可以考虑使用CTE代替子查询。
 *  依据语法结构和执行方式的不同，公用表表达式分为 普通公用表表达式 和 递归公用表表达式 2 种。
 * （1）普通共用表表达式
 *  普通公用表表达式类似于子查询，不过，跟子查询不同的是，它可以被多次引用，而且可以被其他的普通公用表表达式所引用。
 *     WITH CTE名称
 *      AS
 *     (子查询语句)
 * (2) 递归共用表表达式
 *  递归公用表表达式也是一种公用表表达式，只不过，除了普通公用表表达式的特点以外，它还有可以调用自己。
 *     WITH RECURSIVE
 *      CTE名称
 *      AS
 *     (子查询语句)

 @author Alex
 @create 2023-01-22-21:07
 */
public class UDO08 {
    /*
     * 【举例用表】
     *      id  category_id  category             NAME               price   stock  upper_time
     * ------  -----------  -------------------  ---------------  -------  ------  ---------------------
     *      1            1  女装/女士精品          T恤               39.90    1000  2020-11-10 00:00:00
     *      2            1  女装/女士精品          连衣裙            79.90    2500  2020-11-10 00:00:00
     *      3            1  女装/女士精品          卫衣              89.90    1500  2020-11-10 00:00:00
     *      4            1  女装/女士精品          牛仔裤            89.90    3500  2020-11-10 00:00:00
     *      5            1  女装/女士精品          百褶裙            29.90    500   2020-11-10 00:00:00
     *      6            1  女装/女士精品          呢绒外套          399.90   1200  2020-11-10 00:00:00
     *      7            2  户外运动               自行车           399.90    1000  2020-11-10 00:00:00
     *      8            2  户外运动               山地自行车       1399.90   2500  2020-11-10 00:00:00
     *      9            2  户外运动               登山杖            59.90    1500  2020-11-10 00:00:00
     *     10            2  户外运动               骑行装备          399.90   3500  2020-11-10 00:00:00
     *     11            2  户外运动               运动外套          799.90    500  2020-11-10 00:00:00
     *     12            2  户外运动               滑板             499.90    1200  2020-11-10 00:00:00
     */

    // 序号函数举例
    @Test
    public void test1(){
    /*
    SELECT DENSE_RANK() OVER(PARTITION BY category_id ORDER BY price DESC) AS row_num,     # 按照category_id分块，而后按照price排序
                                         id, category_id, category, NAME, price, stock
    FROM goods;

    结果如下
    row_num   id     category_id  category             NAME               price     stock
    -------  ------  -----------  -------------------  ---------------  -------    --------
      1       6       1          女装/女士精品              呢绒外套        399.90      1200
      2       3       1          女装/女士精品              卫衣            89.90      1500
      2       4       1          女装/女士精品              牛仔裤          89.90      3500
      3       2       1           女装/女士精品             连衣裙          79.90      2500
      4       1       1           女装/女士精品             T恤             39.90      1000
      5       5       1           女装/女士精品            百褶裙            29.90       500
      1       8       2           户外运动                 山地自行车       1399.90      2500
      2      11       2           户外运动                 运动外套         799.90       500
      3      12       2           户外运动                 滑板             499.90      1200
      4       7       2           户外运动                 自行车            399.90      1000
      4      10       2           户外运动                 骑行装备          399.90      3500
      5       9       2           户外运动                 登山杖             59.90      1500
     */
    }

    // PERCENT_RANK举例
    @Test
    public void test2(){
        /*
        SELECT RANK() OVER (PARTITION BY category_id ORDER BY price DESC) AS r,
               PERCENT_RANK() OVER (PARTITION BY category_id ORDER BY price DESC) AS pr,
               id, category_id, category, NAME, price, stock
        FROM goods
        WHERE category_id = 1;

        结果如下
     r      pr      id  category_id  category             NAME           price   stock
------  ------  ------  -----------  -------------------  ------------  ------  --------
     1       0       6            1  女装/女士精品       呢绒外套          399.90      1200
     2     0.2       3            1  女装/女士精品       卫衣             89.90      1500
     2     0.2       4            1  女装/女士精品       牛仔裤            89.90      3500
     4     0.6       2            1  女装/女士精品       连衣裙            79.90      2500
     5     0.8       1            1  女装/女士精品        T恤             39.90      1000
     6       1       5            1  女装/女士精品       百褶裙            29.90       500
         */
    }

    // CUME_DIST举例
    @Test
    public void test3(){
        /*
        SELECT CUME_DIST() OVER(PARTITION BY category_id ORDER BY price ASC) AS cd,     # 计算价格占当前类最高价格的比例
               id, category, NAME, price
        FROM goods;

        结果如下
                     cd      id  category             NAME               price
    -------------------  ------  -------------------  ---------------  ---------
    0.16666666666666666       5  女装/女士精品       百褶裙                  29.90
     0.3333333333333333       1  女装/女士精品       T恤                   39.90
                    0.5       2  女装/女士精品       连衣裙                  79.90
     0.8333333333333334       3  女装/女士精品       卫衣                   89.90
     0.8333333333333334       4  女装/女士精品       牛仔裤                  89.90
                      1       6  女装/女士精品       呢绒外套                399.90
    0.16666666666666666       9  户外运动            登山杖                  59.90
                    0.5       7  户外运动            自行车                 399.90
                    0.5      10  户外运动            骑行装备                399.90
     0.6666666666666666      12  户外运动            滑板                  499.90
     0.8333333333333334      11  户外运动            运动外套                799.90
                      1       8  户外运动            山地自行车              1399.90
         */
    }

    // LAG(expr,n)举例，返回前两行的price
    @Test
    public void test4(){
        /*
        SELECT id, category, NAME, price,LAG(price,1) OVER (PARTITION BY category_id ORDER BY price) AS pre_price
        FROM goods

        结果如下
        id  category             NAME               price      pre_price
    ------  -------------------  ---------------  -------      -----------
         5  女装/女士精品         百褶裙                29.90       (NULL)
         1  女装/女士精品         T恤                 39.90        (NULL)
         2  女装/女士精品         连衣裙                79.90       29.90
         3  女装/女士精品         卫衣                 89.90        39.90
         4  女装/女士精品         牛仔裤                89.90       79.90
         6  女装/女士精品         呢绒外套              399.90      89.90
         9  户外运动              登山杖                59.90       (NULL)
         7  户外运动              自行车               399.90       (NULL)
        10  户外运动              骑行装备              399.90       59.90
        12  户外运动              滑板                499.90        399.90
        11  户外运动              运动外套              799.90      399.90
         8  户外运动              山地自行车            1399.90      499.90
         */
    }

    // FIRST_VALUE(expr举例)
    @Test
    public void test5(){
        /*
        SELECT id, category, NAME, price, stock,FIRST_VALUE(price) OVER w AS first_price
        FROM goods
        WINDOW w AS (PARTITION BY category_id ORDER BY price);

        结果如下
        id      category             NAME               price   stock      first_price
       ------  -------------------  ---------------  -------  ------     -------------
         5    女装/女士精品           百褶裙             29.90     500         29.90
         1    女装/女士精品           T恤               39.90     1000         29.90
         2    女装/女士精品           连衣裙             79.90     2500        29.90
         3    女装/女士精品           卫衣               89.90     1500        29.90
         4    女装/女士精品           牛仔裤             89.90     3500        29.90
         6    女装/女士精品           呢绒外套           399.90    1200        29.90
         9    户外运动                登山杖             59.90     1500        59.90
         7    户外运动                自行车            399.90     1000        59.90
        10    户外运动                骑行装备           399.90    3500        59.90
        12    户外运动                滑板              499.90    1200         59.90
        11    户外运动                运动外套           799.90     500        59.90
         8    户外运动                山地自行车         1399.90    2500        59.90
         */
    }

    // NTH_VALUE(expr,n)举例，查询goods数据表中排名第2和第3的价格信息
    @Test
    public void test6(){
        /*
        SELECT id, category, NAME, price,NTH_VALUE(price,2) OVER w AS second_price,
                                 NTH_VALUE(price,3) OVER w AS third_price
        FROM goods
        WINDOW w AS (PARTITION BY category_id ORDER BY price);

        结果如下
            id  category             NAME               price    second_price    third_price
        ------  -------------------  ---------------  -------   ------------     -------------
             5  女装/女士精品         百褶裙             29.90        (NULL)         (NULL)              # 按照顺序排序，由于第一条数据进来的时候，还没有第二第三的概念呢，所以值为null
             1  女装/女士精品         T恤                39.90         39.90         (NULL)
             2  女装/女士精品         连衣裙             79.90         39.90          79.90
             3  女装/女士精品         卫衣               89.90         39.90          79.90
             4  女装/女士精品         牛仔裤             89.90         39.90          79.90
             6  女装/女士精品         呢绒外套           399.90         39.90          79.90
             9  户外运动              登山杖            59.90        (NULL)         (NULL)
             7  户外运动              自行车            399.90        399.90         399.90
            10  户外运动              骑行装备          399.90        399.90         399.90
            12  户外运动              滑板              499.90        399.90         399.90
            11  户外运动              运动外套          799.90        399.90         399.90
             8  户外运动              山地自行车        1399.90        399.90         399.90
         */
    }

    // NTILE(n)函数举例：将goods表中的商品按照价格分为3组。
    @Test
    public void test7(){
        /*
        SELECT NTILE(3) OVER w AS nt,id, category, NAME, price               # 按照category_id分组，price排序，并按照price分成三桶
        FROM goods
        WINDOW w AS (PARTITION BY category_id ORDER BY price);

        结果如下
            nt      id  category             NAME               price
        ------  ------  -------------------  ---------------  ---------
             1       5  女装/女士精品         百褶裙             29.90
             1       1  女装/女士精品         T恤                39.90
             2       2  女装/女士精品         连衣裙             79.90
             2       3  女装/女士精品         卫衣               89.90
             3       4  女装/女士精品         牛仔裤             89.90
             3       6  女装/女士精品         呢绒外套           399.90
             1       9  户外运动             登山杖              59.90
             1       7  户外运动             自行车              399.90
             2      10  户外运动             骑行装备            399.90
             2      12  户外运动             滑板               499.90
             3      11  户外运动             运动外套           799.90
             3       8  户外运动             山地自行车         1399.90
         */
    }

    // 共用表表达式举例：查询员工所在的部门的详细信息。
    @Test
    public void test8(){
        /*
        (1) 使用子查询
        SELECT * FROM departments
        WHERE department_id IN (
                                SELECT DISTINCT department_id
                                FROM employees
                                );

        (2)使用共用表达式，将共用表达式视为一个临时结果集就好。相当于我们子查询放在FROM里的情况
        WITH emp_dept_id
        AS
        (SELECT DISTINCT department_id FROM employees)

        SELECT *
        FROM departments d JOIN emp_dept_id e
        ON d.department_id = e.department_id;
         */
    }

    // 递归共用表表达式举例，了解即可，我也看不懂
    @Test
    public void test9(){
        /*
        针对于我们常用的employees表，包含employee_id，last_name和manager_id三个字段。如果a是b
        的管理者，那么，我们可以把b叫做a的下属，如果同时b又是c的管理者，那么c就是b的下属，是a的下下属。

        如果用我们之前学过的知识来解决，会比较复杂，至少要进行 4 次查询才能搞定：
        第一步，先找出初代管理者，就是不以任何别人为管理者的人，把结果存入临时表；
        第二步，找出所有以初代管理者为管理者的人，得到一个下属集，把结果存入临时表；
        第三步，找出所有以下属为管理者的人，得到一个下下属集，把结果存入临时表。
        第四步，找出所有以下下属为管理者的人，得到一个结果集。

        如果用递归公用表表达式，就非常简单了。我介绍下具体的思路。

        用递归公用表表达式中的种子查询，找出初代管理者。字段 n 表示代次，初始值为 1，表示是第一代管理者。
        用递归公用表表达式中的递归查询，查出以这个递归公用表表达式中的人为管理者的人，并且代次的值加 1。
        直到没有人以这个递归公用表表达式中的人为管理者了，递归返回。
        在最后的查询中，选出所有代次大于等于 3 的人，他们肯定是第三代及以上代次的下属了，也就是下下属了。

        WITH RECURSIVE cte
        AS
        (
        SELECT employee_id,last_name,manager_id,1 AS n FROM employees WHERE employee_id = 100
        -- 种子查询，找到第一代领导
        UNION ALL
        SELECT a.employee_id,a.last_name,a.manager_id,n+1 FROM employees AS a JOIN cte
        ON (a.manager_id = cte.employee_id) -- 递归查询，找出以递归公用表表达式的人为领导的人
        )
        SELECT employee_id,last_name FROM cte WHERE n >= 3;
         */
    }
}

