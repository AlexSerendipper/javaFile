package usecommonclass;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 【JDK8之前日期和时间API】了解即可，实际上在引入了calendar后，java.util.date类基本被抛弃了
 * 【一. java.lang.System类】
 *   public static long currentTimeMillis()
 *
 * 【二. java.util.Date类】
 *      ----【三. java.sql.Date类】是 java.util.Date类的子类，对应数据库中的时间，二者构造器与方法相同。。。二者的相互转换见下方示例
 *  要知道。本质上java.sql.Date 就是'yyyy-MM-dd'格式类型的数据，
 *   所以通过特殊的sdf, 即 new SimpleDateFormat("yyyy-MM-dd") 可以实现java.util.date 和 java.sql.Date 二者的相互转换
 *   当然，通过构造器方法，传入时间戳（使用.getTime()方法获得时间戳）,同样能够二者格式的相互转换
 *   Date():                   # 无参构造器创建的对象可以获取本地当前时间。
 *   Date(long date)
 *   getTime():                # 返回时间戳
 *   toString():               # 把此 Date对象转换为时间形式的String
 *
 * 【四. SimpleDateFormat】主要用于对Date类的格式化和解析
 *   SimpleDateFormat(pattern) :            # 可以将date对象转换为指定的pattern格式的文本String (如 "yyyy-MM-dd hh:mm:ss")
 *   SimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));    # 指定一个时区为GMT+8即可输出当前正确的日期时间。否则在使用SimpleDateFormat 对日期进行格式化的时候，输出结果比预期结果晚了8个小时。
 *   public String format(Date date)：      # 格式化-时间对象date成文本
 *   public Date parse(String source)：     # 解析-将给定字符串文本解析成一个日期对象
 *
 * 【五. Calendar类】
 *  Calendar.getInstance()：                        # 获取Calendar实例
 *  public void set(int field,int value)：          # field为传入的常量，可以直接更改Calendar类中的属性
 *  public void add(int field,int amount)：         # field为传入的常量，可以让Calendar的属性加上一个值
 *  public final Date getTime() ：                  # 可以将Calendar类 ==> Date类
 *  public final void setTime(Date date) ：         # 可以将Date类 ==> Calendar类
 *
 * 【Calendar类中有许多常量可以调用】
 *  Calendar.YEAR ： // 返回当前年份
 *  Calendar.MONTH： // 返回当前月份，注意一月是0
 *  Calendar.DATE:   // 表示当前时间为多少号
 *  DAY_OF_WEEK:     // 返回当前是一周中的第几天，注意周一是2
 *  HOUR_OF_DAY:
 *  MINUTE:
 *  SECOND
 * @author Alex
 * @create 2022-11-21-14:01
 */


public class UseCommonClass04 {
    // 一. java.lang.System类
    // public static long currentTimeMillis()用来返回当前时间与1970年1月1日0时0分0秒之间以毫秒为单位的时间差
    @Test
    public void test() {
        System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
    }

    // 二. java.util.Date类
    // |---三. java.sql.Date类  # 这个是上面的子类，对应数据库中的时间，暂时不用
    @Test
    public void test1(){
        Date date1 = new Date();
        System.out.println("date1.toString() = " + date1.toString());
        System.out.println("date1.getTime() = " + date1.getTime());
        System.out.println("**************************");
        // sql.date的实例化
        java.sql.Date date2 = new java.sql.Date(1231231231L);
        System.out.println(date2);
        System.out.println("*****************");
        // 将sql.date与util.date转换
           // 情况1: sql.date → util.date
        Date date3 = new java.sql.Date(1669012674083L);
        Date date4 = new Date(date3.getTime());
        System.out.println("date4.toString() = " + date4.toString());
           // 情况2：util.date → sql.date
        Date date5 = new Date();
        java.sql.Date date6 = new java.sql.Date(date5.getTime());
        System.out.println("date6.toString() = " + date6.toString());
    }

    // 四. SimpleDateFormat，主要用于对Date类的格式化和解析
       // （1）格式化：日期 ==> 字符串
       //  (2) 解析：字符串==>日期
    @Test
    public void test2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date();
        System.out.println(date);
        //（1）格式化：日期 ==> 字符串
        String formate = sdf.format(date);
        System.out.println(formate);
        // (2) 解析：字符串==>日期
        String str = "2019-08-09 上午8:01";
        Date date1 = sdf.parse(str);
        System.out.println(date1);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf2.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String str2 = "2019-08-09 16:01";
        Date date2 = sdf2.parse(str2);
        System.out.println(date2);

        System.out.println("********************************");
        // （3）查看源码，构造器中可以调用不同的转换格式（如果要转换为特定的格式的字符串，就先看看标准格式date转换成字符串是什么样子就好了）
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date3 = new Date();
        String formate2 = sdf1.format(date3);
        System.out.println(formate2);
    }

    // 五. 日历类（抽象类）的使用
    @Test
    public void test5(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getClass());  // 其实这个方法，也是返回子类的对象

        // calendar.get
        System.out.println("calendar.get(Calendar.DAY_OF_YEAR) = " + calendar.get(Calendar.DAY_OF_YEAR));

        // calendar.set
        calendar.set(Calendar.DAY_OF_YEAR,22);  // 直接更改了它的静态属性
        System.out.println("calendar.get(Calendar.DAY_OF_YEAR) = " + calendar.get(Calendar.DAY_OF_YEAR));

        // calendar.add();  // 让Calendar的属性加上一个值
        calendar.add(Calendar.DAY_OF_YEAR,3);
        System.out.println("calendar.get(Calendar.DAY_OF_YEAR) = " + calendar.get(Calendar.DAY_OF_YEAR));

        // calendar.getTime();
        Date time = calendar.getTime(); // Calendar类 ==> Date类

        // calendar.setTime();
        Date date = new Date();  // Date类 ==> Calendar类
        calendar.setTime(date);
        System.out.println("calendar.get(Calendar.DAY_OF_YEAR) = " + calendar.get(Calendar.DAY_OF_YEAR));
    }


    // 练习一：将字符串“2020-09-08”转换为java.sql.Date
    @Test
    public void test6() throws ParseException {
        String str = "2020-09-08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 设定好对应格式的解析器
        Date date = sdf.parse(str);
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        System.out.println(date1);
    }

    // 练习2：从1990-01-01开始，"三天打鱼两天晒网"，问 xxxx-xx-xx是在打鱼还是在晒网
    // 思路: xxxx-xx-xx距离1990-01-01多少天，然后除5的余数为 1，2，3打鱼，4，0晒网
    @Test
    public void test7() throws ParseException {
        // 我就算今天是打鱼还是晒网了
        String str = "1990-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 设定好对应格式的解析器
        Date date = sdf.parse(str);
        long time = new Date().getTime() - date.getTime();
        long day = time / (1000*60*60*24) + 1;  // 求总天数，大概率除不尽，所以加1
        System.out.println(day);
        if(day%5 == 4 || day%5 == 0){
            System.out.println("我在打渔");
        }else{
            System.out.println("我在晒网");
        }
    }
}
