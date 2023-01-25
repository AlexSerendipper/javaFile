package usecommonclass;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 * @author Alex
 * @create 2022-11-21-20:23
 */
public class UseCommonClass06 {
    // 练习一：将字符串“2020-09-08”转换为java.sql.Date
    @Test
    public void test() throws ParseException {
        String str = "2020-09-08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 设定好对应格式的解析器
        Date date = sdf.parse(str);
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        System.out.println(date1);
    }

    // 练习2：从1990-01-01开始，"三天打鱼两天晒网"，问 xxxx-xx-xx是在打鱼还是在晒网
    // 思路: xxxx-xx-xx距离1990-01-01多少天，然后除5的余数为 1，2，3打鱼，4，0晒网
    @Test
    public void test2() throws ParseException {
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
