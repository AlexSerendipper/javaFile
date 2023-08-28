package useSSM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import useSSM.Service.TimeService;
import useSSM.pojo.Emp;
import useSSM.pojo.Holiday;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 @author Alex
 @create 2023-07-22-23:43
 */
@Controller
public class HolidayController {
    @Autowired
    TimeService timeService;

    // 经过尝试，发现前端传过来的是yyyy-MM-dd的字符串类型的时间数据，只能通过@DateTimeFormat转换为java.util.Date类型的数据
    // 无法直接转换为java.sql.date类型的数据()
    @RequestMapping(value = "/time",method = RequestMethod.POST)
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String getHoliday(java.sql.Date begin,  java.sql.Date end){
//        java.sql.Date beg = new java.sql.Date(begin.getTime());
//        java.sql.Date en = new java.sql.Date(end.getTime());

        List<Holiday> holidayByTime = timeService.getHolidayByTime(begin, end);

        for(Holiday hol:holidayByTime){
            Date time = hol.getTime();
            System.out.println(time);
            System.out.println(hol);
        }
        return "emp_list";
    }

//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @RequestMapping(value="/dateTime",method = RequestMethod.POST)
    public String addDateTime(String dateTime) throws Exception{
        String[] times = dateTime.split("T");
        dateTime = times[0] + " " + times[1];
        System.out.println(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = sdf.parse(dateTime);
        System.out.println(date);
        // 前面的时间都没有问题，最终插入数据库时间差了8个小时，可能是数据库配置的问题
        timeService.insertHoliday(date);
        return "emp_list";
    }
}
