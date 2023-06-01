package mysqlstructure;

/**
 * 【字符集以及比较规则】
 * （1）字符集介绍
 *  utf8mb3 ：阉割过的 utf8 字符集，只使用1～3个字节表示字符。
 *  utf8mb4 ：正宗的 utf8 字符集，使用1～4个字节表示字符。
 *  在mysql钟utf8是utf8mb3的别名~ 在使用中，大部分的存储都可以使用utf8mb3完成，如果需要存储一些emoji表情，就需要用uft8mb4
 *
 * （2）修改字符集
 *  show variables like 'character_%';       # 查看数据库字符集
------------------------
✔character_set_server：                      # 服务器级别的字符集
✔character_set_database：                    # 当前数据库的字符集
character_set_client：                      # 服务器解码请求时使用的字符集
character_set_connection：                  # 服务器处理请求时会把请求字符串从character_set_client转为character_set_connection
character_set_results：                     # 服务器向客户端返回数据时使用的字符集
------------------------
 *  如果 创建或修改列 时没有显式的指定字符集和比较规则，则该列 默认用表的 字符集和比较规则
 *  如果 创建表时 没有显式的指定字符集和比较规则，则该表 默认用数据库的 字符集和比较规则
 *  如果 创建数据库时 没有显式的指定字符集和比较规则，则该数据库 默认用服务器的 字符集和比较规则
 * ✔实际上，我们可以在创建数据库以及数据表时指定不同的字符集，即分别指定服务器、数据库级别的字符集，但是通常将其都设置为uft8
 *
 *（3）比较规则
 *  show variables like 'collation_%';       # 查看数据库的字符集对应的编码格式
 *  MySQL版本一共支持41种字符集，其中的 Default collation列表示这种字符集中一种默认
 *   的比较规则，里面包含着该比较规则主要作用于哪种语言，比如 utf8_polish_ci 表示以波兰语的规则
 *   比较， utf8_spanish_ci 是以西班牙语的规则比较， utf8_general_ci 是一种通用的比较规则
 *
 * 【请求到响应过程中字符集的变化】为方便理解，分析字符 '我' 在这个过程中字符集的转换。
 * ✔实际上，通常把character_set_client、character_set_connection、character_set_results均设置为utf8，减少很多无意义的字符集转换
------------------------
character_set_server：                      # 服务器级别的字符集
character_set_database：                    # 当前数据库的字符集
✔character_set_client：                      # 服务器解码请求时使用的字符集
✔character_set_connection：                  # 服务器处理请求时会把请求字符串从character_set_client转为character_set_connection
✔character_set_results：                     # 服务器向客户端返回数据时使用的字符集
------------------------
 * 1、客户端发送请求所使用的字符集（如java程序写的查询语句）
 *  一般情况下客户端所使用的字符集和当前操作系统一致（不同操作系统使用的字符集可能不一样），当客户端使用的是 utf8 字符集，
 *    字符 '我' 在发送给服务器的请求中的字节形式就是：0xE68891
 * 2、服务器接收使用的字符集
 *  服务器接收客户端发送来的请求其实是一串二进制的字节，它会认为这串字节采用的字符集是character_set_client ，
 *   然后把这串字节转换为 character_set_connection 字符集编码的字符。
 *   （即本机中 character_set_client 的值是 utf8 ，首先会按照 utf8 字符集对字节串 0xE68891 进行解码，得到的字符串就是 '我' ，
 *   然后按照 character_set_connection 代表的字符集utf8进行编码，得到的结果就是字节串 0xE68891）
 * 3、查表所用的字符集
 *  因为表 t 的列 col 采用的也是 utf8 字符集，与 character_set_connection 一致，所以直接到列中找字节值为 0xE68891 的记录，最后找到了一条记录。
 * 4、查找到数据发送回客户端所用的字符集
 *  上一步中找到的记录其实是一个字节串 0xE68891， 由于 col 列是采用 utf8 进行编码的，所
 *   以首先会将这个字节串使用 utf8 进行解码，得到字符串 '我' ，
 *   然后再把这个字符串使用 character_set_results 代表的字符集，也就是 utf8 进行编码，得到了新的字节串：0xE68891 ，然后发送给客户端。
 * 5. 客户端显示使用的字符集
 *  由于客户端是用的字符集是 utf8 ，所以可以顺利的将 0xE68891 解释成字符 我 ，从而显示到我们的显示器上，所以我们人类也读懂了返回的结果。
 *
 *
 * 【SQL大小写规范】
 *  不论是在windows还是linux平台，关键字和函数名是不用区分字母大小写的，比如 SELECT、WHERE、ORDER、GROUP BY 等关键字，以及 ABS、MOD、ROUND、MAX 等函数名。
 *  对于以下情况，windows系统默认大小写不敏感 ，linux 系统是大小写敏感的
 *    linux中，数据库名、表名、表的别名、变量名是严格区分大小写的；
 *    linux中，关键字、函数名称在 SQL 中不区分大小写；
 *    linux中，列名（或字段名）与列的别名（或字段别名）在所有的情况下均是忽略大小写的；
 *  当想设置为大小写不敏感时，要在 my.cnf 这个配置文件 [mysqld] 中加入 lower_case_table_names=1 ，然后重启服务器（通常不建议修改）
 *
 *
 * 【sql_mode 设置】
 *  宽松模式：
 * 如果设置的是宽松模式，那么我们在插入数据的时候，即便是给了一个错误的数据，也可能会被接受，并且不报错。
 * 举例 ：我在创建一个表时，该表中有一个字段为name，给name设置的字段类型时 char(10) ，如果我在插入数据的时候，其中name这个字段对应的有一条数据的 长度超过了10 ，
 * 例如'1234567890abc'，超过了设定的字段长度10，那么不会报错，并且取前10个字符存上，也就是说你这个数据被存为了'1234567890'，而'abc'就没有了。
 * 但是，我们给的这条数据是错误的，因为超过了字段长度，但是并没有报错，并且mysql自行处理并接受了，这就是宽松模式的效果。
 * 应用场景 ：通过设置sql mode为宽松模式，来保证大多数sql符合标准的sql语法，这样应用在不同数据库之间进行 迁移 时，则不需要对业务sql 进行较大的修改
 *  严格模式：绝大多数都应该使用严格模式
 * 出现上面宽松模式的错误，应该报错才对，所以MySQL5.7版本就将sql_mode默认值改为了严格模式。所以在 生产等环境 中，我们必须采用的是严格模式，
 * 进而 开发、测试环境 的数据库也必须要设置，这样在开发测试阶段就可以发现问题。并且我们即便是用的MySQL5.6，也应该自行将其改为严格模式
 *  SET GLOBAL sql_mode = 'modes...';                                  # 临时设置sql_mode（全局）
 *  SET SESSION sql_mode = 'modes...';                                 # 临时设置sql_mode（当前会话）
 *  在my.cnf文件(windows系统是my.ini文件)，新增如下内容后，重启MySQL：     # 永久设置sql_mode
-----------------
[mysqld]
sql_mode=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR
_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
-----------------
 *
 @author Alex
 @create 2023-05-10-12:55
 */
public class US01 {
}
