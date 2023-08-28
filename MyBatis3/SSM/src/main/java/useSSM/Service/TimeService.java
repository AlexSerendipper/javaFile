package useSSM.Service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import useSSM.pojo.Emp;
import useSSM.pojo.Holiday;

import java.sql.Date;
import java.util.List;

/**
 @author Alex
 @create 2023-07-22-16:57
 */
@Service
public interface TimeService {
    /**
     * 根据时间范围查询数据
     */
    List<Holiday> getHolidayByTime(Date begin, Date end);

    /**
     * 插入dateTime类型的数据
     */
    int insertHoliday(java.util.Date date);

}
