package useSSM.mapper;

import org.apache.ibatis.annotations.Param;
import useSSM.pojo.Holiday;

import java.sql.Date;
import java.util.List;

/**
 @author Alex
 @create 2023-07-22-17:01
 */

public interface HolidayMapper {
    /**
     * 查询所有节假日信息（sql中的date类型，实际上就是对应了java的java.sql.date类型）
     */
    List<Holiday> getHolidayByTime(@Param(value = "begin") Date begin, @Param(value = "end") Date end);

    /**
     * 新增节假日（sql中的datetime类型，对应了java中的）
     */
    int insertHoliday(@Param(value = "dateTime") java.util.Date dateTime);
}
