package index;


/**
 * 【索引的分类】
 *  MySQL的索引包括普通索引、唯一性索引、全文索引、单列索引、多列索引和空间索引等。
 *  从 功能逻辑 上说，索引主要有 4 种，分别是普通索引、唯一索引、主键索引、全文索引。
 *  按照 物理实现方式 ，索引可以分为 2 种：聚簇索引和非聚簇索引。
 *  按照 作用字段个数 进行划分，分成单列索引和联合索引。
 *（1）普通索引
 * 在创建普通索引时，不附加任何限制条件，只是用于提高查询效率。这类索引可以创建在 任何数据类型 中，其值是否唯一和非空，要由字段本身的完整性约束条件决定。
 * 建立索引以后，可以通过索引进行查询。
 *（2）唯一性索引
 * 使用 UNIQUE参数 可以设置索引为唯一性索引，在创建唯一性索引时，限制该索引的值必须是唯一的，但允许有空值。在一张数据表里 可以有多个唯一索引。
 *（3）主键索引
 * 主键索引就是一种 特殊的唯一性索引，在唯一索引的基础上增加了不为空的约束，也就是 NOT NULL+UNIQUE，一张表里最多只有一个主键索引。
 * why?这是由主键索引的物理实现方式决定的，因为数据存储在文件中只能按照一种顺序进行存储，就是按照主键来存储数据的
 *（4）单列索引
 * 在表中的单个字段上创建索引。单列索引只根据该字段进行索引。单列索引可以是普通索引，也可以是唯一性索引，还可以是全文索引。只要保证该索引只对应一个字段即可。一个表可以 有多个 单列索引。
 *（5）多列(组合、联合)索引
 * 多列索引是在表的 多个字段组合 上创建一个索引。该索引指向创建时对应的多个字段，可以通过这几个字段进行查询，但是只有查询条件中使用了这些字段中的第一个字段时才会被使用。
 * 例如，在表中的字段id、name和gender上建立一个多列索引idx_id_name_gender，只有在查询条件中使用了字段id时该索引才会被使用。使用组合索引时遵循 最左前缀集合✔ 。
 *（6）全文索引
 * 全文索引（也称全文检索）是目前 搜索引擎 使用的一种关键技术。它能够利用 分词技术 等多种算法智能分析出文本文字中关键词的频率和重要性，然后按照一定的算法规则智能地筛选出我们想要的搜索结果。全文索引非常适合大型数据集，对于小的数据集，它的用处比较小。
 * 使用参数 FULL TEXT 可以设置索引为全文索引。在定义索引的列上支持值的全文查找，允许在这些索引列中插入重复值和空值。
 * 全文索引只能创建在 CHAR、VARCHAR 或 TEXT 类型及其系列类型的字段上，查询数据量较大的字符串类型的字段时，使用全文索引可以提高查询速度。
 * 全文索引典型的有两种类型: 自然语言的全文索引 和 布尔全文索引。
 *
 * 自然语言搜索引擎将计算每一个文档对象和查询的相关度。这里，相关度是基于匹配的关键词的个数，以及关键词在文档中出现的次数。在整个索引中出现次数越少的词语，匹配时的相关度就越高。相反，非常常见的单词将不会被搜索，如果一个词语的在超过50%的记录中都出现了，那么自然语言的搜索将不会搜索这类词语。
 * MySQL数据库从3.23.23版开始支持全文索引，但MySQL5.6.4以前只有Myisam支持，5.6.4版本以后innodb才支持，但是官方版本不支持中文分词，需要第三方分词插件。在5.7.6版本，MySQL内置了 ngram全文解析器，用来支持亚洲语种的分词。测试或使用全文索引时，要先看一下自己的MySQL版本、存储引擎和数据类型是否支持全文索引。
 * 随着大数据时代的到来，关系型数据库应对全文索引的需求已力不从心，逐渐被solr、ElasticSearch等专门的搜索引擎所替代。
 *（7）空间索引
 * 使用 参数SPATIAL 可以设置索引为空间索引。空间索引只能建立在空间数据类型上，这样可以提高系统获取空间数据的效率。MySQL中的空间数据类型包括 GEOMETRY、POINT、LINESTRING 和POLYGON 等。
 * 目前只有MyISAM存储引擎支持空间检索，而且索引的字段不能为空值。对于初学者来说，这类索引很少会用到。
 *
 *  小结：不同的存储引擎支持的索引类型也不一样
 * InnoDB ：支持 B-tree、Full-text 等索引，不支持 Hash索引；
 * MyISAM ： 支持 B-tree、Full-text 等索引，不支持 Hash 索引；
 * Memory ：支持 B-tree、Hash 等索引，不支持 Full-text 索引；
 * NDB ：支持 Hash 索引，不支持 B-tree、Full-text 等索引；
 * Archive ：不支持 B-tree、Hash、Full-text 等索引；
 *
 * 【创建索引】
 *（1）创建表的时候创建索引
 * # 显式的方式创建索引：
 *  CREATE TABLE table_name [col_name data_type]
 *   [UNIQUE | FULLTEXT | SPATIAL] [INDEX | KEY] [index_name] (col_name [length]) [ASC | DESC]
 *    UNIQUE、FULLTEXT和SPATIAL为可选参数，分别表示唯一索引、全文索引和空间索引；若创建普通索引无需声明该项
 *    INDEX与KEY为同义词，两者的作用相同，用来指定创建索引；
 *    index_name指定索引的名称，为可选参数，如果不指定，那么MySQL默认col_name为索引名✔；
 *    col_name为需要创建索引的字段列，该列必须从数据表中定义的多个列中选择；
 *    length为可选参数，表示索引的长度，只有字符串类型的字段才能指定索引长度；
 *    ASC或DESC指定升序或者降序的索引值存储。
 * # 通过命令查看索引
 *  SHOW CREATE TABLE book;    # 方式1
 *  SHOW INDEX FROM book;      # 方式2
---------------------------
# 隐式的方式创建索引：在声明有主键约束、唯一性约束、外键约束的字段上，会自动的添加相关的索引
CREATE TABLE emp(
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(20) UNIQUE,
    dept_id INT,
    CONSTRAINT emp_dept_id_fk FOREIGN KEY(dept_id) REFERENCES dept(dept_id)
);

# 创建唯一性索引 (也相当于创建了唯一性约束)
CREATE TABLE test1(
    id INT NOT NULL,
    name varchar(30) NOT NULL,
    UNIQUE INDEX uk_idx_id(id)
);

# 通过定义主键约束的方式定义主键索引
CREATE TABLE student (
    id INT(10) UNSIGNED AUTO_INCREMENT,
    student_no VARCHAR(200),
    student_name VARCHAR(200),
    PRIMARY KEY(id)
);

# 删除主键索引
ALTER TABLE student drop PRIMARY KEY

# 创建组合索引
CREATE TABLE test3(
    id INT(11) NOT NULL,
    name CHAR(30) NOT NULL,
    age INT(11) NOT NULL,
    info VARCHAR(255),
    INDEX multi_idx(id,name,age)
);
✔✔✔组合索引可起几个索引的作用，但是使用时并不是随便查询哪个字段都可以使用索引，而是遵从“最左前缀"。例如，
索引可以搜索的字段组合为: (id, name, age). (id, name)或者id。而(age)或者(name,age)组合不能使用索引查询。

# 创建全文检索索引
CREATE TABLE `papers` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(200) DEFAULT NULL,
    `content` text,
    PRIMARY KEY (`id`),
    FULLTEXT KEY `title` (`title`,`content`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

 SELECT * FROM papers WHERE MATCH(title,content) AGAINST ('查询字符串');         # 全文索引用match+against方式查询。全文索引比 like + % 快 N 倍，但是可能存在精度问题；

# 创建空间索引
CREATE TABLE test5(
    geo GEOMETRY NOT NULL,
    SPATIAL INDEX spa_idx_geo(geo)
) ENGINE=MyISAM;
---------------------------
 *
 * （2）在已存在的表上创建索引
 *  ALTER TABLE table_name
 *   ADD [UNIQUE | FULLTEXT | SPATIAL] [INDEX | KEY] [index_name] (col_name[length],...) [ASC | DESC]
 *  CREATE [UNIQUE | FULLTEXT | SPATIAL] INDEX index_name
 *   ON table_name (col_name[length],...) [ASC | DESC]
---------------------------
# 创建唯一索引
ALTER TABLE book5 ADD UNIQUE uk_idx_bname(book_name)；
# 创建组合索引
CREATE INDEX mul_bid_bname_info ON book6(book_id,book_name,info);
---------------------------
 *
 * 【删除索引】
 *  ALTER TABLE table_name DROP INDEX index_name;                       # 删除索引1
 *  DROP INDEX index_name ON table_name;                                # 删除索引2
 *  注意1：添加AUTO_INCREMENT约束字段的唯一索引不能被删除。
 *  注意2：删除表中的列时，如果要删除的列为索引的组成部分，则该列也会从索引中删除。如果组成索引的所有列都被删除，则整个索引将被删除。
 *
 * 【MySQL8.0索引新特性】
 *（1）支持降序索引
 *  8.0版本之前创建的仍然是升序索引，如果在使用时进行反向扫描（降序），会大大降低了数据库的效率 。在某些场景下，降序索引意义重大。
 *   例如，如果一个查询，需要对多个列进行排序，且顺序要求不一致，那么使用降序索引将会避免数据库使用额外的文件排序操作，从而提高性能。
 *  CREATE TABLE ts1(a int,b int,index idx_a_b(a,b desc));              # 创建B+树时，二级索引b降序排列
 *（2）隐藏索引
 *  在MySQL 5.7版本及之前，只能通过显式的方式删除索引。此时，如果发现删除索引后出现错误，又只能通过显式创建索引的方式将删除的索引创建回来。如果数据表中的数据量非常大，或者数据表本身比较大，
 *    这种操作就会消耗系统过多的资源，操作成本非常高。从MySQL 8.x开始支持隐藏索引（invisible indexes），只需要将待删除的索引设置为隐藏索引，使查询优化器不再使用这个索引（即使使用force index（强制使用索引），优化器也不会使用该索引），
 *    确认将索引设置为隐藏索引后系统不受任何响应，就可以彻底删除索引。这种通过先将索引设置为隐藏索引，再删除索引的方式就是软删除。此外，如果你想验证某个索引删除之后的查询性能影响，就可以暂时先隐藏该索引。
 *  注意1:主键不能被设置为隐藏索引。当表中没有显式主键时，表中第一个唯一非空索引会成为隐式主键，也不能设置为隐藏索引。
 *   索引默认是可见的，在使用 CREATE TABLE，CREATE INDEX 或者 ALTERT TABLE 等语句时可以通过 VISIBLE 或者 INVISIBLE 关键词设置索引的可见性。
 *  注意2:当索引被隐藏时，它的内容仍然是和正常索引一样实时更新的。如果一个索引需要长期被隐藏，那么可以将其删除，因为索引的存在会影响插入、更新和删除的性能。
---------------------
# 创建表时直接创建，使用关键字INVISIBLE
   CREATE TABLE tablename(
        ……
        INDEX [indexname](propname1 [(length)]) INVISIBLE
    );
# 在已经存在的表上创建
   CREATE INDEX indexname ON tablename(propname[(length)]) INVISIBLE;
# 通过ALTER TABLE语句创建下
   ALTER TABLE tablename ADD INDEX indexname (propname [(length)]) INVISIBLE;
# 修改索引可见状态
   ALTER TABLE tablename ALTER INDEX index_name INVISIBLE;
   ALTER TABLE tablename ALTER INDEX index_name VISIBLE;

 # 在MySQL 8.x版本中，为索引提供了一种新的测试方式，可以通过查询优化器的一个开关（use_invisible_indexes）来打开某个设置，使隐藏索引对查询优化器可见。
   如果 use_invisible_indexes 设置为off(默认)，优化器会忽略隐藏索引。如果设置为on，即使 隐藏索引不可见，优化器在生成执行计划时仍会考虑使用隐藏索引。
  set session optimizer_switch="use_invisible_indexes=on"
---------------------
 @author Alex
 @create 2023-05-19-9:52
 */
public class UI08 {
}
