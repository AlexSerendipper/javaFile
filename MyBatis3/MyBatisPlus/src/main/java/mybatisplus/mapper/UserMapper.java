package mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mybatisplus.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 @author Alex
 @create 2023-03-25-12:08
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据id查询用户信息为 map 集合
     * @param id
     * @return
     */
    Map<String,Object> selectMapById(long id);

    /**
     * 根据年龄查询用户列表，分页显示
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位
     * @param age 年龄
     * @return
     */
    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);


}
