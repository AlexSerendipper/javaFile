package usecommonclass;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;

/**
 * 【JDK8之前的时间API面临的问题】
 * (1) 可变性：像日期和时间这样的类应该是不可变的。(calendar是可变的，调用set方法)
 * (2) 偏移性：Date中的年份是从1900开始的，而月份都从0开始。
 * (3) 格式化：格式化只对Date有用，Calendar则不行。
 * (4) 此外，它们也不是线程安全的；不能处理闰秒等。
 *
 * 【jdk8.0中引入的时间API基本解决了上述的所有问题】实际上在引入了新API后，旧的API都被抛弃了，当然新版和旧版是可以互相转换
 * (1) LocalDate代表IOS格式（yyyy-MM-dd）的日期
 *     LocalTime表示一个时间
 *     LocalDateTime 表示日期和时间,这是最常用的类之一
 * (2) 常用方法(创建)
 *      LocalDateTime ldt = LocalDateTime.now() ：# ✔创建当前的日期的本地日期实例
 *      LocalDateTime ldt = LocalDateTime.of() ： # 创建指定日期的实例化对象
 * (3)常用方法(获取)
 *      getDayOfMonth()/getDayOfYear() ：         # 获得月份天数(1-31) /获得年份天数(1-366)
 *      getDayOfWeek() ：                         # 获得星期几(返回一个DayOfWeek枚举值)
 *      getMonth() ：                             # 获得月份,返回一个 Month枚举值
 *      getMonthValue() / getYear() ：            # 获得月份(1-12) /获得年份
 *      getHour()/getMinute()/getSecond() ：      # 获得当前对象对应的小时、分钟、秒
 * (4)常用方法(修改)
 *      withDayOfMonth()/withDayOfYear()/withMonth()/withYear()             # 将月份天数、年份天数、月份、年份修改为指定的值并返回新的对象
 *      plusDays()/plusWeeks()/plusMonths()/plusYears()/plusHours()         # 向当前对象添加几天、几周、几个月、几年、几小时
 *      minusMonths()/minusWeeks()/minusDays()/minusYears()/minusHours()    # 从当前对象减去几月、几周、几天、几年、几小时
 *      LocalDateTime.toInstant(ZoneOffset.of("+8"))                        # 将LocalDateTime转换为 补偿了时区的偏移量8h 的instant对象（方便计算时间戳）
 *
 * 【instant类】用于计算时间戳。instant类精度可以达到纳秒级
 *  now()                                 # 静态方法，返回当前时间的 以默认UTC时区为标准的 Instant类的对象
 *  ofEpochMilli(long epochMilli)         # 静态方法，返回在1970-01-01 00:00:00基础上加上指定毫秒数之后的Instant类的对象
 *  atOffset(ZoneOffset offset)           # 结合即时的偏移来创建一个 OffsetDateTime。如中国时区和默认时区差8小时
 *  toEpochMilli()                        # 返回1970-01-01 00:00:00到当前时间的毫秒数，即为时间戳！
 *
 * 【java.time.format.DateTimeFormatter类】
 *  提供了三种格式化的方法
 *   方式1：预定义的标准格式：.ISO_LOCAL_DATE_TIME;ISO_LOCAL_DATE;ISO_LOCAL_TIME
 *   方式2：本地化相关的格式：.ofLocalizedDateTime(FormatStyle.LONG)
 *   方式3：自定义的格式：.ofPattern(“yyyy-MM-dd hh:mm:ss”)
 *   format(TemporalAccessor t) 格式化LocalDateTime为时间字符文本
 *   parse(CharSequence text) 将指定格式的时间字符文本解析为一个日期、时间
 *
 * @author Alex
 * @create 2022-11-29-15:31
 */

public class UseCommonClass05 {
    // java.time包下几个常用类的使用LocalDate、LocalTime、LocalDateTime
    // 以LocalDateTime为例
    @Test
    public void test() {
        // 创建当前的日期的实例化对象
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
        System.out.println("********************");
        // 创建指定的日期的实例化对象
        LocalDateTime ldt = LocalDateTime.of(1997, 9, 20, 17, 17, 17);
        System.out.println(ldt);
        System.out.println("********************");
        // get相关操作，获取当前年月日时间
        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonth());
        System.out.println(ldt.getDayOfMonth());
        System.out.println("*****************");
        // 修改操作1
        LocalDateTime ldt1 = ldt.withYear(2000);
        System.out.println(ldt1);
        System.out.println("*****************");
        // 修改操作2
        LocalDateTime ldt2 = ldt1.plusYears(1);
        System.out.println(ldt2);
    }


    // java.time包下几个常用类之instant瞬时类，获取时间戳
    @Test
    public void test1() {
        // 返回默认UTC时区对应时间的瞬时点实例
        Instant ins = Instant.now();
        System.out.println(ins);
        // 补偿时区的偏移量8h(中国和默认时区差8h)
        OffsetDateTime ins1 = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(ins1);
        // 计算当前实例的时间戳
        System.out.println(ins.toEpochMilli());
        // 根据毫秒数。返回瞬时点实例
        Instant ins2 = Instant.ofEpochMilli(1669808725779L);
        System.out.println(ins2);
    }

    // java.time.format.DateTimeFormatter 类，，用来格式化，解析时间和日期
    @Test
    public void test2() {
        LocalDateTime localDateTime = LocalDateTime.now();  // 处理对象
        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // 方式1：
        String str1 = formatter1.format(localDateTime);  // 格式化
        System.out.println(str1);
        TemporalAccessor parse = formatter1.parse("2022-11-30T19:58:03.24");  // 解析
        System.out.println(parse);
        System.out.println("*****************");

        // 方式2：
        DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        String str2 = formatter2.format(localDateTime);
        System.out.println(str2);
        TemporalAccessor parse1 = formatter2.parse("2022年12月1日 下午01时00分54秒");
        System.out.println(parse1);
        System.out.println("*****************");

        // 方式3：自定义格式
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String str3 = formatter3.format(localDateTime);
        System.out.println(str3);
        TemporalAccessor parse3 = formatter3.parse("2022-12-01 01:04:00");
        System.out.println(parse3 );
    }
}

