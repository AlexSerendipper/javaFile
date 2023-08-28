package useSSM.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import useSSM.Service.TimeService;
import useSSM.mapper.EmpMapper;
import useSSM.mapper.HolidayMapper;
import useSSM.pojo.Holiday;

import java.sql.Date;
import java.util.List;

/**
 @author Alex
 @create 2023-07-22-17:06
 */
@Service
public class TimeServiceImpl implements TimeService {
    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public List<Holiday> getHolidayByTime(Date begin, Date end) {
        return holidayMapper.getHolidayByTime(begin,end);
    }

    @Override
    public int insertHoliday(java.util.Date date) {

        return holidayMapper.insertHoliday(date);
    }
}
