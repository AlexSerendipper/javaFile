package index;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * 【分析查询语句】查看执行计划，EXPLAIN
 *  定位了查询慢的SQL之后，我们就可以使用EXPLAIN 或 DESCRIBE 工具做针对性的分析查询语句。DESCRIBE语句的使用方法与EXPLAIN语句是一样的，并且分析结果也是一样的。
 *   经过分析后，为客户端请求 推荐的执行计划是他认为最优的数据检索方式，但不见得是DBA最优的（不一定是实际环境中最优）
 *  能够分析出：
 *    ① 表的读取顺序
 *    ② 数据读取操作的操作类型
 *    ③ 哪些索引可以使用
 *    ④ 哪些索引被实际使用✔
 *    ⑤ 表之间的引用
 *    ⑥ 每张表有多少行被优化器查询✔
 *  基本语法：EXPLAIN SELECT select * from tables;
--------------------
id	                              # 在一个大的查询语句中 每个SELECT关键字都对应一个唯一的id
select_type	                      # SELECT关键字对应的那个查询的类型
table	                          # 表名
partitions	                      # 匹配的分区信息
type	                          # ✔针对单表的访问方法
possible_keys	                  # 可能用到的索引
key	                              # 实际上使用的索引
key_len	                          # ✔实际使用到的索引长度
ref	                              # 当使用索引列等值查询时，与索引列进行等值匹配的对象信息
rows	                          # ✔预估的需要读取的记录条数
filtered	                      # 某个表经过搜索条件过滤后剩余记录条数的百分比
Extra	                          # ✔一些额外的信息
--------------------
 *
 * 【各字段详解】 137集，有需要在学把，太多了
 * (1) table: 表名
 *     查询的每一条记录都对应着一个单表，table为表名
 *     如：explain select * from s1 inner join s2; 存在两条记录
 * (2) id: 在一个大的查询语句中 每个SELECT关键字都对应一个唯一的id
 *     如：EXPLAIN SELECT * FROM s1 WHERE key1 IN (SELECT key1 FROM s2) OR key3 = 'a'; 存在两个不同的id
 *     注意：查询优化器可能对涉及子查询的查询语句进行重写, 转变为多表查询的操作，所以可能会看到理应有两个不同的id，最后却只有一个id
 *     注意：可能会出现id值为null，extra为using temporary的临时表数据
 *  id如果相同，可以认为是一组，从上往下顺序执行
 *  在所有组中，id值越大，优先级越高，越先执行
 *  关注点：id号每个号码，表示一趟独立的查询,一个sql的查询趟数越少越好
 * (3) select_type: MySQL为每一个SELECT关键字代表的小查询都定义了一个称之为 select_type 的属性，意思是我们只要知道了某个小查询的 select_type属性，就知道了这个 小查询在整个大查询中扮演了一个什么角色
 *  SIMPLE                       # 不包含UNION或者子查询的查询都算作是SIMPLE类型
 *  PRIMARY                      # 包含子查询的外层查询语句，称为PRIMARY
 *  UNION
 *  UNION RESULT                 # 临时表来完成UNION的去重工作，该临时表类型为UNION RESULT
 *  SUBQUERY
 *  DEPENDENT SUBQUERY
 * ....
 * (4) partitions（了解）
 *     代表分区表中的命中情况，非分区表，该项为 NULL。一般情况下我们的查询语句的执行计划的 partitions 列的值都是 NULL。
 * (5) type（重点）
 *  system                       # 当统计表中的记录数，并且该表使用的存储引擎的 统计数据是精确的(如MyISAM、Memory 记录了表中的记录数)
 *  const                        # 当我们根据主键或者唯一二级索引列 与 常数进行等值匹配
 *  eq_ref                       # 当我们根据主键或者唯一二级索引列 与 常数进行等值匹配，并且主键或者唯一二级索引列是联合索引
 *  ref                          # 普通的二级索引列与常量进行等值匹配
 *  ref or null                  # 普通的二级索引列与常量进行等值匹配，并且该索引列的值可能是null值。
 *  index_merge                  # 索引合并的情况。如 EXPLAIN SELECT * FROM s1 WHERE key1 = 'a' OR key3 = 'a';
 *  unique_subquery
 *  index_subquery
 *  range                        # 使用索引获取某些范围区间的记录
 *  index
 *  ALL                          # 全表扫描
 * 结果值从最好到最坏依次是：system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery >range > index > ALL
 * SQL性能优化的目标：至少要达到 range级别，要求是ref级别，最好是consts级别。（阿里巴巴开发手册要求）
 * (6) possible_keys和key ：单表查询时 可能用到的索引 以及 实际用到的索引
 * (7) key_len（重点）：实际使用到的索引的长度（字节数），帮你检查是否充分利用上了索引，值越大越好，主要针对联合索引有一定的参加意义
 * (8) ref: 当使用索引列进行等值查询时，与索引列进行等值匹配的对象信息~
 * (9) rows（重点）：预估的需要读取的记录条数，"值越小越好"
 * (10) filtered：某个表经过搜索条件过滤后剩余记录条数的百分比
 * (11) Extra（重要）：用来说明一些额外信息
 *  No tables used               # 当查询语句的没有 FROM 子句时将会提示该额外信息
 *  Impossible WHERE             # 查询语句的WHERE子句永远为FALSE时将会提示该额外信息
 *  Using where                  # 当我们使用全表扫描来执行对某个表的查询，并且该语句的WHERE子句中有针对该表的搜索条件时，在Extra列中会提示上述额外信息。
 *  No matching min/max row      # 当查询列表处有MIN 或 MAX聚合函数，但是并没有符合条件的记录时
 *  Using index                  # 当我们的查询列表 以及 搜索条件中只包含属于某个索引的列，也就是在可以使用索引覆盖的情况下 (不需要回表操作)，在Extra列将会提示该额外信息。
 *  Using index condition        # 索引条件下推
 *  Using join buffer (Block Nested Loop)           # 在连接查询执行过程中，当被驱动表不能有效的利用索引加快访问速度，MySQL一般会为其分配一块名叫join buffer的内存块来加快查询速度，也就是我们所讲的基于块的嵌套循环算法
 *  Not exists                                      # 当我们使用左(外）连接时，如果 WHERE 子句中包含要求被驱动表的某个列等于 NULL 值的搜索条件，而且那个列又是不允许存储NULL值的，那么在该表的执行计划的Extra列就会提示 Not exists 额外信息
 *  Using union()                # 表明MySQL即将使用索引合并的方式执行查询。
 *  Zero limit                   # 当我们的 LIMIT 子句的参数为 0 时，表示压根儿不打算从表中读出任何记录，将会提示该额外信息
 *  Using filesort               # 对没有建立索引的列进行排序时，会提示该额外信息
 *  Using temporary              # 如果查询中使用到了内部的临时表，在执行计划的Extra列将会显示Using temporary提示
 *
 *
 @author Alex
 @create 2023-05-20-14:04
 */
public class UI11 {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();//表示数据组数int
            String[] res = new String[T];
            for(int i = 0; i < T; i++){
                int n = sc.nextInt();//表示观察到的火车数量
                int[] pushed = new int[n];//火车入栈数组
                int[] popped = new int[n];//火车出栈数组
                for(int j = 0; j < n; j++){
                    pushed[j] = sc.nextInt();
                }
                for(int k = 0; k < n; k++){
                    popped[k] = sc.nextInt();
                }
                res[i] = judge(pushed, popped);
            }
            for(String s: res){
                System.out.println(s);
            }
        }

        public static String judge(int[] pushed,int[] popped){
            Deque<Integer> stack = new ArrayDeque<>();
            int n = pushed.length;
            for(int i = 0,j = 0;i < n;i++){
                stack.push(pushed[i]);
                while(!stack.isEmpty() && stack.peek() == popped[j]){
                    stack.pop();
                    j++;
                }
            }
            return stack.isEmpty() ? "Yes" : "No";
        }
}
