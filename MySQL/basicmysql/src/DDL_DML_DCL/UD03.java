package DDL_DML_DCL;

/**
 * 【MySQL中的数据类型汇总】字段才涉及数据类型。数据类型有各种属性可以指定，声明在声明字段之后`
 * 1）数值型
 *   整数类型 TINYINT、SMALLINT、MEDIUMINT、✔INT(或INTEGER)、BIGINT
 *   浮点类型 FLOAT、✔DOUBLE
 *   定点数类型 DECIMAL
 *   位类型 BIT
 * 2）日期型
 * 3）字符串类型
 *   日期时间类型 YEAR、TIME、✔DATE、DATETIME、TIMESTAMP
 *   文本字符串类型 CHAR、✔VARCHAR、TINYTEXT、TEXT、MEDIUMTEXT、LONGTEXT
 *   枚举类型 ENUM
 *   集合类型 SET
 *   二进制字符串类型BINARY、VARBINARY、TINYBLOB、BLOB、MEDIUMBLOB、LONGBLOB
 *   JSON类型 JSON对象、JSON数组
 * 4）空间数据类型
 *   单值：GEOMETRY、POINT、LINESTRING、POLYGON；
 *   集合：MULTIPOINT、MULTILINESTRING、MULTIPOLYGON、GEOMETRYCOLLECTION
 *
 * 【整数类型】包含三个属性：数据宽度、ZEROFILL、UNSIGNED
 *   TINYINT、SMALLINT、MEDIUMINT、INT(或INTEGER)、BIGINT所能表示的范围越来越大
 *   整数类型可以指定数据的宽度，指定宽度并不影响其本身的容量范围。如TINYINT取值范围为-128~127，由于负号占了一个数字位，因此TINYINT默认的显示宽度为4。
 *   显示宽度通常和ZEROFILL配合使用。如INT(5) ZEROFILL，当输入123时，显示为00123,输入123456仍能正常储存
 *   当使用ZEROFILL时默认会添加UNSIGNED标识，表示无符号属性，无符号整数类型的最小取值为0。
 *   如 INT(5) UNSIGNED ZEROFILL。在MySQL8中，不推荐使用数据宽度属性
 *
 * 【浮点数、定点数、位】
 * （1）浮点数：包含三个属性：数据精度、UNSIGNED
 *   FLOAT 占用字节数少，取值范围小；DOUBLE 占用字节数多，取值范围也大。
 *   设置精度：FLOAT(M,D) 或 DOUBLE(M,D) 。这里，M称为精度，是整数位+小数位。D称为标度，为小数位
 *    如FLOAT(5,2)的一个列可以显示为-999.99-999.99。如果超过这个范围会报错。
 *    注意：FLOAT和DOUBLE类型在不指定(M,D)时，默认会按照实际的精度来显示。
 *    注意：如果存入的数据是999.994，小数部分超出了设置的精度范围，则会四舍五入，四舍五入后若符合条件，则可以存入。而整数部分不存在四舍五入
 *    注意：设置精度在MySQL8.0中已经不推荐使用了
 *   浮点类型，也可以加 UNSIGNED。例如：FLOAT(3,2)表示0-9.99的范围。
 *    注意：UNSIGNED在MySQL8.0中已经不推荐使用了
 * (2)定点数
 *   浮点数是不准确的。定点数是一种精准的数据类型
 *   用DECIMAL(M,D)的方式表示高精度小数。同样的 M被称为精度，D被称为标度
 *    注意：DECIMAL类型在不指定(M,D)时，默认为DECIMAL(10,0)
 *    注意：当数据的精度超出了定点数类型的精度范围时，则MySQL同样会进行四舍五入处理
 *   浮点数与定点数的选用：浮点数相对于定点数的优点是在长度一定的情况下，浮点类型取值范围大，但是不精准，适用于需要取值范围大，
 *                       又可以容忍微小误差的科学计算场景（比如计算化学、分子建模、流体动力学等）
 *                       定点数类型取值范围相对小，但是精准，没有误差，适合于对精度要求极高的场景 （比如涉及金额计算的场景）
 * (3)位类型
 *   BIT(M)。这里(M)是表示二进制的位数，位数最小值为1，最大值为64。
 *
 @author Alex
 @create 2023-01-17-13:14
 */
public class UD03 {
}
