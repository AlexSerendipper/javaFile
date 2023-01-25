package usecommonclass;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 【JDK8之前日期和时间API】了解即可，实际上在引入了calendar后，java.util.date类基本被抛弃了
 * 【一. java.lang.System类】
 *   public static long currentTimeMillis()
 *
 * 【二. java.util.Date类】
 *      ----【三. java.sql.Date类】是 java.util.Date类的子类，对应数据库中的时间，二者构造器与方法相同
 *   Date():无参构造器创建的对象可以获取本地当前时间。
 *   Date(long date)
 *   getTime():返回时间戳
 *   toString():把此 Date对象转换为时间形式的String
 *
 * 【四. SimpleDateFormat】主要用于对Date类的格式化和解析
 *   SimpleDateFormat(pattern) : 可以将date对象转换为指定的pattern格式的文本String
 *   public String format(Date date)：格式化-时间对象date成文本
 *   public Date parse(String source)：解析-将给定字符串文本解析成一个日期对象
 *
 * 【五. Calendar类】
 *  Calendar.getInstance()：获取Calendar实例
 *  public void set(int field,int value)：field为传入的常量，可以直接更改Calendar类中的属性
 *  public void add(int field,int amount)：field为传入的常量，可以让Calendar的属性加上一个值
 *  public final Date getTime() ：可以将Calendar类 ==> Date类
 *  public final void setTime(Date date) ：可以将Date类 ==> Calendar类
 *
 * 【Calendat类中有许多常量可以调用】
 * Calendar.YEAR ：返回当前年份
 * Calendar.MONTH： 返回当前月份，注意一月是0
 * DAY_OF_WEEK: 返回当前是一周中的第几天，注意周一是2
 * HOUR_OF_DAY:
 * MINUTE:
 * SECOND
 *
 * @author Alex
 * @create 2022-11-21-14:01
 */


public class UseCommonClass05 {
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
        Date date = new Date();
        System.out.println(date);
        //（1）格式化：日期 ==> 字符串
        String formate = sdf.format(date);
        System.out.println(formate);
        // (2) 解析：字符串==>日期
        String str = "2019-08-09 上午8:01";
        Date date1 = sdf.parse(str);
        System.out.println(date1);
        System.out.println("********************************");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  // （3）查看源码，构造器中可以调用不同的转换格式
        Date date2 = new Date();
        String formate2 = sdf1.format(date2);
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
}
