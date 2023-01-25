package useDDL01;

/**
 * 【字符串类型】
 * 【CHAR和VARCHAR和TEXT】
 * （1）CHAR类型为固定长度类型
 *  CHAR(M) 类型一般需要预先定义字符串长度。如果不指定(M)，则表示长度默认是1个字符。
 *  如果保存时，数据的实际长度比CHAR类型声明的长度小，则会在右侧填充空格以达到指定的长度。当MySQL检索CHAR类型的数据时，CHAR类型的字段会去除尾部的空格。
 *  CHAR类型字段底层空间即为声明的字段长度。如:CHAR(3) 当我们存储'ab'时，底层还是占用三个字节
 * （2）VARCHAR类型为可变长度类型
 *  VARCHAR(M)定义时，必须指定字符长度M，否则报错。
 *  VARCHAR(M)类型字段底层空间为字符串实际长度。如:VARCHAR(3) 当我们存储'ab'时，底层占用两个字节（实际上三个字节，VARCHAR会拿出一个字节来记录储存长度）
 *  (3) CHAR和VARCHAR对比
 *  CHAR(M)  固定长度 浪费存储空间 效率高
 *   VARCHAR(M) 可变长度 节省存储空间 效率低
 *  (4)TEXTl类型
 *  TEXT用来保存文本类型的字符串，总共包含4种类型，分别为TINYTEXT、TEXT、MEDIUMTEXT 和LONGTEXT类型。储存容量依次递增
 *  向TEXT类型的字段保存和查询数据时，系统自动按照实际长度存储，不需要预先定义长度
 *  开发中如果不是特别大的内容，建议使用CHAR，VARCHAR来代替
 *
 * 【ENUM类型】ENUM类型只允许从成员中选取单个值，不能一次选取多个值。如
 *  CREATE TABLE test_enum(
 *     season ENUM('春','夏','秋','冬','unknow')
 *   );
 *  INSERT INTO test_enum       # 添加数据时忽略大小写
 *   VALUES('UNKNOW');
 *  INSERT INTO test_enum       # 允许按照角标的方式获取指定索引位置的枚举值
 *    VALUES('1'),(3);
 *  INSERT INTO test_enum#      # 当 ENUM类型的字段没有声明为NOT NULL时，插入NULL也是有效的
 *    VALUES(NULL);
 *
 * 【SET类型】SET表示一个字符串对象，可以包含0个或多个成员，但成员个数的上限为 64。设置字段值时，可以取取值范围内的0个或多个值。
 *  CREATE TABLE test_set(
 *     s SET ('A', 'B', 'C')
 *   );
 *  INSERT INTO test_set (s) VALUES ('A,B,C,A');      # 插入重复的SET类型成员时，MySQL会自动删除重复的成员
 *
 * 【二进制字符串类型】二进制字符串类型主要存储一些二进制数据，比如可以存储图片、音频和视频等二进制数据。
 * (1)BINARY和VARBINARY类似于CHAR和VARCHAR
 *  BINARY(M)为固定长度的二进制字符串，M表示最多能存储的字节数，取值范围是0~255个字符。如果未指定(M)，表示只能存储 1个字节
 *  VARBINARY (M)为可变长度的二进制字符串，VARBINARY类型 必须指定(M)，M表示最多能存储的字节数，总字节数不能超过行的字节长度限制65535
 *  BINARY和VARBINARY类似于CHAR和VARCHAR，只是它们存储的是二进制字符串。
 * (2)BLOG类型类似于TEXT类型
 *  BLOB是一个二进制大对象，可以容纳可变数量的数据。包括TINYBLOB、BLOB、MEDIUMBLOB和LONGBLOB 4种类型
 *  在实际工作中，往往不会在MySQL数据库中使用BLOB类型存储大对象数据，通常会将图片、音频和视频文件存储到服务器的磁盘上 ，并将图片、音频和视频的访问路径存储到MySQL中。
 *  ✔建议把BLOB或TEXT列 分离到单独的表中。避免出现碎片化等问题
 *
 * 【JSON类型】JSON（JavaScript Object Notation）是一种轻量级的数据交换格式
 *  INSERT INTO test_json (js)              # 插入JSON数据举例
 *   VALUES ('{"name":"songhk", "age":18, "address":{"province":"beijing","city":"beijing"}}');
 *  SELECT js -> '$.name' AS NAME, js -> '$.age' AS age, js -> '$.address.province' AS province, js -> '$.address.city' AS city
 *   FROM test_json;                         # 当需要检索JSON类型的字段中数据的某个具体值时，可以使用->举例
 *
 * 【空间数据类型】了解即可，以后用到再说吧
 *
 * 【总结以及使用建议】
 * ✔使用建议：在定义数据类型时，如果确定是整数，就用INT；如果是小数，一定用定点数类型DECIMAL(M,D)；如果是日期与时间，就用DATETIME。
 * ✔任何字段如果为非负数，必须声明UNSIGNED
 * ✔如果存储的字符串长度几乎相等，使用CHAR定长字符串类型。
 * ✔VARCHAR是可变长字符串，不预先分配存储空间，长度不要超过5000。如果存储长度大于此值，定义字段类型为 TEXT，且将其独立出来一张表，用主键来对应，避免影响其它字段索引效率。
 @author Alex
 @create 2023-01-17-16:11
 */
public class US05 {
}
