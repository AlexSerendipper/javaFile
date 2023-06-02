package index;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * 【常见索引分类】
 *  索引按照物理实现方式，索引可以分为 2 种：聚簇（聚集）和非聚簇（非聚集）索引。 我们也把非聚集索引称为二级索引或者辅助索引
 * （1）聚簇（聚集）索引：以主键列构建的索引
 *  概念：聚簇索引并不是一种单独的索引类型，而是一种数据存储方式（所有的用户记录都存储在了叶子节点），✔也就是所谓的索引即数据，数据即索引。
 *          上述推导的B+树即为聚簇索引✔, 正如innodb引擎中，使用的idb文件，索引即数据。而MyISAM引擎中，索引和数据分开存储
 *          MySQL中，InnoDB存储引擎会自动的为我们创建聚簇索引✔。
 *  特点：
 *      页内（所有层）的记录是按照 主键的大小顺序 排成一个单向链表。
 *      各个存放用户记录的页（第0层） 也是根据页中用户记录的主键大小顺序 排成一个双向链表。
 *      存放目录项记录的页（除第0层外的所有层）分为不同的层次，在同一层次中的页也是根据页中 目录项记录的主键大小顺序 排成一个双向链表。
 *      B+树的叶子节点存储的是完整的用户记录（所有列的值，包括隐藏列）
 *  优点：
 *      数据访问更快，因为聚簇索引将索引和数据保存在同一个B+树中，因此从聚簇索引中获取数据比非聚簇所以更快
 *      聚簇索引对主键的排序查找和范围查找速度非常快
 *      按照聚簇索引排列顺序，查找显示一定范围数据的时候，由于数据都是紧密相连，数据库不用从多个数据块中提取数据，所以节省了大量的IO操作
 *  缺点：
 *      插入速度严重依赖于插入顺序 ，按照主键的顺序插入是最快的方式，否则将会出现页分裂，严重影响性能。因此，对于InnoDB表，我们一般都会定义一个自增的ID列为主键
 *      更新主键的代价很高 ，因为将会导致被更新的行移动。因此，对于InnoDB表，我们一般定义主键为不可更新
 *      二级索引访问需要两次索引查找 ，第一次找到主键值，第二次根据主键值找到行数据
 *  限制：
 *      对于MySQL数据库目前只有InnoDB数据引擎支持聚簇索引，而MyISAM并不支持聚簇索引。
 *      由于数据物理存储排序方式只能有一种，所以每个MySQL的表只能有一个聚簇索引。一般情况下就是该表的主键
 *      如果没有定义主键，InnoDB会选择非空的唯一索引代替，如果没有这样的索引。InnoDB会隐式的定义一个主键来作为聚簇索引。
 *      为了充分利用聚簇索引和聚簇的特性，主键列尽量选用有序的顺序id，而不建议用无序的id（比如UUID、MD5等）
 * （2）非聚簇（非聚集）索引：以其他列构建的索引
 *  特点：
 *      页的记录是按照（所有层） c2列的大小顺序 排成一个单向链表。
 *      各个存放用户记录的页（第0层）也是根据页中记录的 c2列大小顺序 排成一个双向链表。
 *      存放目录项记录的页（除第0层外的所有层）分为不同的层次，在同一层次中的页也是根据页中目录项记录的c2列大小顺序排成一个双向链表。
 *      ✔B+树的叶子节点存储的并不是完整的用户记录，而只是c2列 和 主键（c1列）的值
 *      ✔目录项记录页 中不再是主键+页号的搭配，而变成了c2列+页号的搭配。
 *  回表：
 *      概念：我们根据这个以c2列大小排序的B+树只能确定我们要查找记录的主键值，所以如果我们想根据c2列的值查找到完整的用户记录的话，
 *       仍然要到聚簇索引中再查一遍，这个过程称为回表。也就是根据c2列的值查询一条完整的用户记录需要使用2棵B+树！
 *      问题：为什么我们还需要一次回表操作呢？直接把完整的用户记录放到叶子节点不OK吗？
 *        如果把完整的用户记录放到叶子节点是可以不用回表。但是太占地方了，相当于每建立一棵B+树都需要把所有的用户记录再拷贝一遍，这就有点太浪费存储空间了。
 *        因为这种按照非主键列建立的B+树需要一次回表操作才可以定位到完整的用户记录，所以这种B+树也称为二级索引（英文名secondary index），或者辅助索引。
 *        由于我们使用的是c2列的大小作为B+树的排序规则，所以我们也称这个B+树是为c2列创建的索引。非聚簇索引的存在不影响数据在聚簇索引中的值，所以一张表可以有多个非聚簇索引✔。
 *  二者区别：
 *      聚簇索引的叶子节点存储的就是我们的数据记录，非聚簇索引的叶子节点存储的是数据位置。非聚簇索引不会影响数据表的物理存储顺序。
 *      一个表只能有一个聚簇索引，因为只能有一种排序存储的方式，但可以有多个非聚簇索引，也就是多个索引目录提供数据检索。
 *      使用聚簇索引的时候，数据的查询效率高，但如果对数据进行插入、删除、更新等操作，效率会比非聚簇索引低。
 *
 * （3）联合索引
 *  联合索引本质还是非聚簇索引，只不过是同时以多个非主键列构建的索引
 *  特点：
 *      每条目录项记录都由c2、c3、页号这三个部分组成，各条记录先按照c2列进行排除，如果记录的c2列相同，则按照c3列的值进行排序。
 *      B+树叶子节点处的用户记录由c2、c3和主键c1列组成。
 *  组合索引可起几个索引的作用，但是使用时并不是随便查询哪个字段都可以使用索引，而是遵从“最左前缀"。例如，
 *   索引可以搜索的字段组合为: (id, name, age). (id, name)或者id。而(age)或者(name,age)组合不能使用索引查询。
 *
 *
 * 【innodb中索引注意事项】
 *   不建议使用过长的字段作为主键, 因为所有二级索引都引用主键索引，过长的主键索引会令二级索引变得过大。
 *   用非单调的字段作为主键在InnoDB中不是个好主意，因为InnnoDB数据文件本身是一棵B+Tree，非单调的主键会造成在插入新记录时，数据文件为了维持B+Tree的特性而频繁的分裂调整，十分抵消，而使用自增字段作为主键是一个很好的选择
 *
 * 【B+树的注意事项】innodb与myisam 均使用b+tree, 这里概述了其特点
 * （1）根界面位置万年不动
 *    实际上B+树的行程过程是这样的：
 *     每当为某个表创建一个B+树索引（聚簇索引不是认为创建的，默认就有）的时候，都会为这个索引创建一个根节点页面，最开始表中没有数据的时候，每个B+树索引对应的根节点中既没有用户记录，也没有目录项记录。
 *     随后向表中插入用户记录时，先把用户记录存储到这个根节点中。
 *     当根节点中的可用空间用完时继续插入记录，此时会将根节点中的所有记录复制到一个新分配的页，比如页a中，然后对这个新页进行页分裂的操作，得到另一个新页，比如页b。此时根节点（便升级为存储目录项记录的页）。
 *       这个过程特别注意的是：一个B+树索引的根节点自诞生之日起，便不会再移动，这样只要我们对某个表建立一个索引，那么它的根节点的页号便会被记录到某个地方，然后凡是InnoDB存储引擎需要用到这个索引的时候，都会从那个固定的地方取出根节点的页号，从而来访问这个索引。
 * （2）内节点中目录项记录的唯一性（针对非聚簇索引）
 *     假设数据为 c1（主键）：1 3 5 7，对应	c2：1 1 1 1，图见xmind。 如果我们想新插入一行记录，其中c1、c2的值分别是：9、1。那我们这条新插入的记录到底应该放到页4中，还是应该放到页5中就很难得知了
 *      为了让新插入记录能找到自己在哪个页中，所以 存放目录项记录的页 实际上是有三个部分构成的：索引列的值、主键值、页号
 *      如果c2列的值相同的话，可以接着比较主键值，最后肯定能定位唯一的一条目录项记录，在本例中最后确定新记录应该被插入页5中。
 * （3）一个数据页最少存储2条记录
 *     一个B+树只需要很少的层级就可以轻松存储数亿条记录，查询速度相当不错！这是因为B+树本质上就是一个大的多层级目录，每经过一个目录时都会过滤调许多无效的子目录，直到最后访问到存储真实数据的目录。
 *
 @author Alex
 @create 2023-05-14-21:43
 */
public class UI02 {

}

