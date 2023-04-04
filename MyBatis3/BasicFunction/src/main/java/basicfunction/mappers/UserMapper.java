package basicfunction.mappers;

import basicfunction.pojo.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 @author Alex
 @create 2023-03-10-14:29
 */
public interface UserMapper {
// ---------------------------------------------增---------------------------------------------------------
    /**
     * 添加用户
     */
    int insertUser();

    /**
     * 添加用户（传入User对象）
     */
    int insert(User user);

    /**
     * 添加用户（获取添加的主键）
     */
    int insertUserGetKey(User user);

// ----------------------------------------------改--------------------------------------------------------
    /**
     * 修改用户
     */
    int updateUser(String username, String password, Integer age, String sex, String email, Integer id);
// ----------------------------------------------删--------------------------------------------------------
    /**
     * 删除用户
     */
    int deleteUser(String id);

    /**
     * 批量删除
     */
    int deleteMore(String ids);
// ----------------------------------------------查-------------------------------------------------------
    /**
     * 根据id查询一条用户数据
     */
    User getUserById(Integer id);

    /**
     * 根据name查询一条用户数据
     */
    User getUserByUsername(String ybc);

    /**
     * 查询所有用户数据
     */
    List<User> getUserList();

    /**
     * 根据用户id查询用户信息为map集合
     */
    Map<String, Object> getUserToMap(int id);

    /**
     * 查询所有用户信息为map集合
     *
     */
    @MapKey("id")
    Map<String, Object> getAllUserToMap();

    /**
     * 查询所有用户信息为由map组合的list中
     *
     */
    List<Map<String, Object>> getAllUserToMapList();
// ------------------------------------------------------------------------------------------------------
    /**
     * 验证登录功能
     */
    User checkLogin(String username, String password);

    /**
     * 验证登录功能（传入map）
     */
    User checkLoginByMap(Map<String, Object> map);

    /**
     * 验证登录（使用@param）
     */
    User checkLoginByParam(@Param("uu") String username, @Param("pp") String password);

// ------------------------------------------特殊查询------------------------------------------------------------
    /**
     * 查询用户记录总数
     */
    int getCount();

    /**
     * 根据用户名模糊查询用户信息
     */
    List<User> getUserByLike(String blur_name);

    /**
     * 动态设置表名，查询所有的用户信息
     */
    List<User> getAllUser(String tableName);

}
