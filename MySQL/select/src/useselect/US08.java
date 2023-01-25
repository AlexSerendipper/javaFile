package useselect;

/**
 * 【日期和时间函数】
 *  CURDATE() / CURRENT_DATE() 返回当前日期，只包含年、月、日
 *  CURTIME() / CURRENT_TIME() 返回当前时间，只包含时、分、秒
 *  NOW() / SYSDATE() / CURRENT_TIMESTAMP() / LOCALTIME() /LOCALTIMESTAMP() 返回当前系统日期和时间
 *  UTC_DATE()返回UTC（世界标准时间）日期
 *  UTC_TIME()返回UTC（世界标准时间）时间
 *
 * 【日期与时间戳的转换】
 *  UNIX_TIMESTAMP() 以UNIX时间戳的形式返回当前时间。
 *  UNIX_TIMESTAMP(date) 将时间date以UNIX时间戳的形式返回。
 *  FROM_UNIXTIME(timestamp) 将UNIX时间戳的时间转换为普通格式的时间
 *
 * 【获取月份、星期、星期数、天数等函数】✔✔✔MySQL中日期格式为'2021-10-26 23:23:23'，如果输出的字符串数据满足其默认格式，MySQL可以自动将其转换为时间数据（隐式转换）
 *  YEAR(date) / MONTH(date) / DAY(date) 返回具体的日期值(数值)
 *  HOUR(time) / MINUTE(time) /
 *  SECOND(time)返回具体的时间值
 *  MONTHNAME(date) 返回月份：January，...
 *  DAYNAME(date) 返回星期几：MONDAY，TUESDAY.....SUNDAY
 *  WEEKDAY(date) 返回周几，注意，周1是0，周2是1，。。。周日是6
 *  QUARTER(date) 返回日期对应的季度，范围为1～4
 *  WEEK(date) ， WEEKOFYEAR(date) 返回一年中的第几周
 *  DAYOFYEAR(date) 返回日期是一年中的第几天
 *  DAYOFMONTH(date) 返回日期位于所在月份的第几天
 *  DAYOFWEEK(date)返回周几，注意：周日是1，周一是2，。。。周六是7
 *  EXTRACT(type FROM date) 返回指定日期中特定的部分，type指定返回的值
 *   type取值： YEAR/QUARTER/MONTH/WEEK/DAY
 *              HOUR/MINUTE/SECOND/MICROSECOND
 *              YEAR_MONTH
 *              DAY_HOUR
 *              DAY_MINUTE
 *              DAY_SECOND
 *              HOUR_MINUTE
 *              HOUR_SECOND
 *              MINUTE_SECOND
 *
 *              SECOND_MICROSECOND : 返回秒和毫秒值
 *              MINUTE_MICROSECOND
 *              HOUR_MICROSECOND
 *              DAY_MICROSECOND
 *
 * 【时间和秒钟转换的函数】
 *  TIME_TO_SEC(time)：将 time 转化为秒并返回结果值。转化的公式为：小时*3600+分钟*60+秒
 *  SEC_TO_TIME(seconds) ：将 seconds 描述转化为包含小时、分钟和秒的时间
 *
 * 【计算日期和时间的函数】✔较常用
 *  DATE_ADD(datetime, INTERVAL expr type) / ADDDATE(date,INTERVAL expr type)：返回与给定日期时间相差INTERVAL时间段的日期时间,expr为数值
 *  DATE_SUB(date,INTERVAL expr type) / SUBDATE(date,INTERVAL expr type): 返回与date相差INTERVAL时间间隔的日期,expr为数值
 *   type取值：即为EXTRACT函数取值中不包含MICROSECOND部分
 *              YEAR/QUARTER/MONTH/WEEK/DAY
 *              HOUR/MINUTE/SECOND/MICROSECOND
 *              YEAR_MONTH
 *              DAY_HOUR
 *              DAY_MINUTE
 *              DAY_SECOND
 *              HOUR_MINUTE
 *              HOUR_SECOND
 *              MINUTE_SECOND
 *  ADDTIME(time1,time2)返回time1加上time2的时间。当time2为一个数字时，代表的是秒 ，可以为负数
 *    可以不为一个数字，如SELECT ADDTIME(NOW(),'1-1-1')，即为在当前时间上加上一时一分一秒
 *  SUBTIME(time1,time2)返回time1减去time2后的时间。当time2为一个数字时，代表的是秒，可以为负数
 *  DATEDIFF(date1,date2) 返回date1 - date2的日期间隔天数
 *  TIMEDIFF(time1, time2) 返回time1 - time2的时间间隔
 *  FROM_DAYS(N) 返回从0000年1月1日起，N天以后的日期
 *  TO_DAYS(date) 返回日期date距离0000年1月1日的天数
 *  LAST_DAY(date) 返回date所在月份的最后一天的日期
 *  MAKEDATE(year,n) 针对给定年份与所在年份中的天数返回一个日期。
 *    如SELECT MAKEDATE(YEAR(NOW()),12)，即返回当前年份第12天的日期，'2023-1-12'
 *  MAKETIME(hour,minute,second) 将给定的小时、分钟和秒组合成时间并返回
 *  PERIOD_ADD(time,n) 返回time加上n秒后的时间
 *    PERIOD_ADD(time,n),此处time的格式为20230114010101
 *    这个格式是将时间类型转换为数值类型，如NOW()为'2023-01-14 01:01:01'。 NOW()+0 为20230114010101
 *
 * 【日期的格式化与解析】此处是显示的格式化与解析。
 *  DATE_FORMAT(date,fmt) 按照字符串fmt格式化日期date值
 *  TIME_FORMAT(time,fmt) 按照字符串fmt格式化时间time值
 *  STR_TO_DATE(str, fmt) 按照字符串fmt对str进行解析，解析为一个日期。解析的格式要与格式化的格式相同
 *    fmt格式如下：
 *   %Y 4位数字表示年份                                           %y 表示两位数字表示年份
 *   %M 月名表示月份（January,....）                              %m两位数字表示月份（01,02,03。。。）
 *   %b 缩写的月名（Jan.，Feb.，....）                            %c 数字表示月份（1,2,3,...）
 *   %D 英文后缀表示月中的天数（1st,2nd,3rd,...）                  %d 两位数字表示月中的天数(01,02...)
 *   %e 数字形式表示月中的天数（1,2,3,4,5.....）
 *   %H 两位数字表示小数，24小时制（01,02..）                      %h和%I 两位数字表示小时，12小时制（01,02..）
 *   %k 数字形式的小时，24小时制(1,2,3)                            %l数字形式表示小时，12小时制（1,2,3,4....）
 *   %i 两位数字表示分钟（00,01,02）                               %S和%s两位数字表示秒(00,01,02...)
 *   %W 一周中的星期名称（Sunday...）                              %a一周中的星期缩写（Sun.，Mon.,Tues.，..）
 *   %w以数字表示周中的天数(0=Sunday,1=Monday....)
 *   %j 以3位数字表示年中的天数(001,002...)
 *   %U以数字表示年中的第几周，（1,2,3。。）其中Sunday为周中第一天   %u 以数字表示年中的第几周，（1,2,3。。）其中Monday为周中第一天
 *   %T 24小时制时间                                              %r 12小时制的时间，带有AM/PM
 *   %p AM或PM                                                   %% 表示%
 *  GET_FORMAT(date_type,format_type) 返回日期字符串的显示格式。便捷为DATE_FORMAT提供参数罢了
 *    date_type                 format_type
 *    日期类型                  格式化类型                  返回的格式化字符串
 *    DATE                      USA                        %m.%d.%Y
 *    DATE                      JIS                        %Y-%m-%d
 *    DATE                      ISO                        %Y-%m-%d
 *    DATE                      EUR                        %d.¾m.%Y
 *    DATE                      INTERNAL                   %Y%m%d
 *    TIME                      USA                        %h:%i:%s %p
 *    TIME                      JIS                        %H:%i:%s
 *    TIME                      ISO                        %H:%i:%s
 *    TIME                      EUR                        %H:%i:%S
 *    TIME                      INTERNAL                   %H%i%s
 *    DATETIME                  USA                        %Y-%m-%d %H.%i:%s
 *    DATETIME                  JIS                        %Y-%m-%d %H:%1:%s
 *    DATETIME                  ISO                        %Y-%m-9d %H:%1:%s
 *    DATETIME                  EUR                        %Y-%m-%d %H:%i:%s
 *    DATETIME                  INTERNAL                   %Y%m%d%H%i%s
 *    举例应用：可以SELECT DATE_FORMATE(CURDATE(),GET_FORMAT(DATE,USA))
 @author Alex
 @create 2023-01-14-15:46
 */
public class US08 {
}
