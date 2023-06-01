package select;

/**
 * 【单行函数】
 *  函数：函数在DBMS之间的差异性很大，远大于同一个语言不同版本之间的差异。实际上，只有很少的函数是被DBMS同时支持的。比如，大多数DBMS使
 *   用（||）或者（+）来做拼接符，而在MySQL中的字符串拼接函数为concat()。大部分DBMS会有自己特定的函数，这就意味着采用SQL函数的代码可移植性是很差的
 *  MySQL提供的内置函数从实现的功能角度 可以分为数值函数、字符串函数、日期和时间函数、流程控制函数、加密与解密函数、
 *   获取MySQL信息函数、聚合函数等。✔这里，我将这些丰富的内置函数再分为两类： 单行函数 、 聚合函数（或分组函数）
 *  单行函数：即接收单行数据，返回单行结果。可以嵌套
 *  多行函数：即接收多行数据，返回单行结果
 *
 * 【数值函数1】基本函数
 *  ABS(x) 返回x的绝对值
 *  SIGN(X) 返回X的符号。正数返回1，负数返回-1，0返回0
 *  PI() 返回圆周率的值
 *  CEIL(x)，CEILING(x) 返回大于或等于某个值的最小整数
 *  FLOOR(x) 返回小于或等于某个值的最大整数
 *  LEAST(e1,e2,e3…) 返回列表中的最小值
 *  GREATEST(e1,e2,e3…) 返回列表中的最大值
 *  MOD(x,y) 返回X除以Y后的余数
 *  RAND() 返回0~1的随机值
 *  RAND(x)返回0~1的随机值，其中x的值用作种子值，相同的X值会产生相同的随机数
 *  ROUND(x) 返回一个对x的值进行四舍五入后，最接近于X的整数
 *  ROUND(x,y) 返回一个对x的值进行四舍五入后最接近X的值，并保留到小数点后面Y位
 *  TRUNCATE(x,y) 返回数字x截断为y位小数的结果
 *  SQRT(x) 返回x的平方根。当X的值为负数时，返回NULL
 *
 * 【数值函数2】角度与弧度互换函数
 *  RADIANS(x) 将角度转化为弧度，其中，参数x为角度值
 *  DEGREES(x) 将弧度转化为角度，其中，参数x为弧度值
 *
 * 【数值函数3】三角函数
 *  SIN(x) 返回x的正弦值，其中，参数x为弧度值
 *  ASIN(x) 返回x的反正弦值，即获取正弦为x的值。如果x的值不在-1到1之间，则返回NULL
 *  COS(x) 返回x的余弦值，其中，参数x为弧度值
 *  ACOS(x) 返回x的反余弦值，即获取余弦为x的值。如果x的值不在-1到1之间，则返回NULL
 *  TAN(x) 返回x的正切值，其中，参数x为弧度值
 *  ATAN(x) 返回x的反正切值，即返回正切值为x的值
 *  ATAN2(m,n) 返回两个参数的反正切值。可以利用空间中的两个点计算出反正切的值
 *  COT(x) 返回x的余切值，其中，X为弧度值
 *
 * 【数值函数4】指数与对数函数
 *  POW(x,y)，POWER(X,Y) 返回x的y次方
 *  EXP(X) 返回e的X次方，其中e是一个常数，2.718281828459045
 *  LN(X)，LOG(X) 返回以e为底的X的对数，当X <= 0 时，返回的结果为NULL
 *  LOG10(X) 返回以10为底的X的对数，当X <= 0 时，返回的结果为NULL
 *  LOG2(X) 返回以2为底的X的对数，当X <= 0 时，返回NULL
 *
 * 【数值函数5】进制转换函数
 *  BIN(x) 返回x的二进制编码
 *  HEX(x) 返回x的十六进制编码
 *  OCT(x) 返回x的八进制编码
 *  CONV(x,f1,f2) 返回f1进制数变成f2进制数
 *
 * 【字符串函数】important
 *  ASCII(S) 返回字符串S中的第一个字符的ASCII码值
 *  CHAR_LENGTH(s) 返回字符串s的字符数✔。作用与CHARACTER_LENGTH(s)相同
 *  LENGTH(s) 返回字符串s的字节数，和字符集有关✔
 *  CONCAT(s1,s2,......,sn) 连接s1,s2,......,sn为一个字符串
 *  CONCAT_WS(x,s1,s2,......,sn)  同CONCAT(s1,s2,...)函数，但是每个字符串之间用x连接✔
 *  INSERT(str, idx, len,replacestr)  ✔字符串的索引是从1开始的。将字符串str从第idx位置开始，len个字符长的子串替换为字符串replacestr
 *  REPLACE(str, a, b) 用字符串b替换字符串str中所有出现的字符串a
 *  UPPER(s) 或 UCASE(s) 将字符串s的所有字母转成大写字母
 *  LOWER(s) 或LCASE(s) 将字符串s的所有字母转成小写字母
 *  LEFT(str,n) 返回字符串str最左边的n个字符
 *  RIGHT(str,n) 返回字符串str最右边的n个字符
 *  LPAD(str, len, pad) 用字符串pad对str最左边进行填充，直到str的长度为len个字符。能够实现右对齐的效果
 *  RPAD(str ,len, pad) 用字符串pad对str最右边进行填充，直到str的长度为len个字符。能够实现左对齐的效果
 *  LTRIM(s) 去掉字符串s左侧的空格
 *  RTRIM(s) 去掉字符串s右侧的空格
 *  TRIM(s) 去掉字符串s开始与结尾的空格
 *  TRIM(s1 FROM s) 去掉字符串s开始与结尾的s1
 *  TRIM(LEADING s1 FROM s)去掉字符串s开始处的s1
 *  TRIM(TRAILING s1 FROM s)去掉字符串s结尾处的s1
 *  REPEAT(str, n) 返回str重复n次的结果
 *  SPACE(n) 返回n个空格
 *  STRCMP(s1,s2) 比较字符串s1,s2的ASCII码值的大小
 *  SUBSTR(s,index,len)返回从字符串s的index位置其len个字符，作用与SUBSTRING(s,n,len)、MID(s,n,len)相同
 *  LOCATE(substr,str)返回字符串substr在字符串str中首次出现的位置，作用于POSITION(substrIN str)、INSTR(str,substr)相同。未找到，返回0
 *  ELT(m,s1,s2,…,sn)返回指定位置的字符串，如果m=1，则返回s1，如果m=2，则返回s2，如果m=n，则返回sn
 *  FIELD(s,s1,s2,…,sn) 返回字符串s在字符串列表中第一次出现的位置
 *  FIND_IN_SET(s1,s2) 返回字符串s1在字符串s2中出现的位置。其中，字符串s2是一个以逗号分隔的字符串（相当于字符串列表了）
 *  REVERSE(s) 返回s反转后的字符串
 *  NULLIF(value1,value2) 比较两个字符串，如果value1与value2相等，则返回NULL，否则返回value1
 @author Alex
 @create 2023-01-14-14:53
 */
public class US07 {
}
