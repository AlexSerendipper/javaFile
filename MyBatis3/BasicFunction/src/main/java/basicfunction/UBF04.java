package basicfunction;

import basicfunction.mappers.UserMapper;
import basicfunction.pojo.User;
import basicfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 【MyBatis的各种查询功能】
 *
 * 【查询结果为实体类对象】即 resultType="user"
 *
 * 【查询结果为单个数据】即 resultType="int"
 *  当查询结果为整型、集合类型时，我们本应该输入类型全类名，如 java.lang.Integer
 *   但实际上在MyBatis中，对Java中常用的类型都设置了类型别名 (基本数据类型都为其简写)
 *   如：java.lang.Integer  -->  int/integer/_int/_integer（不区分大小写）
 *       java.util.Map  -->  map
 *       java.util.List  -->  list
 *
 * 【查询结果为list集合】mapper接口返回值为list, resultType="user"
 *  当查询的数据只有一条，可以使用 实体类 或 集合 作为返回值✔
 *   当查询的数据为多条时，只能使用集合(list或map)接收✔
 *
 * 【查询结果为一个map集合】mapper接口返回值为map, resultType="user"
 *  当查询结果为 实体类时，实际上就是根据 与属性名相同的字段名（查询结果中） 为其属性赋值
 *   故当查询结果为 map集合时，实际上就是以 字段名=值 的形式为map集合填充值
 *
 * 【查询结果为多个map集合】mapper接口返回值为 List<Map<String, Object>>, resultType="user"
 *  当查询多条数据，因为有多条数据，就会产生多个map集合
 *  若最终希望以List的形式返回，可以设置返回值类型为 List<Map<String, Object>>，把返回的多个map集合存储到List中
 *  若最终希望以一个map的方式返回数据。可以通过@MapKey注解 设置map集合的键（通常以某个字段作为键），每条数据所对应的map集合作为值
 *
 @author Alex
 @create 2023-03-12-10:05
 */
public class UBF04 {
    // 查询一个实体类对象
    @Test
    public void test1() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        User user = userMapper.getUserByUsername("zzj");
        System.out.println(user);
    }

    // 查询一个list集合
    @Test
    public void test2() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        List<User> userList = userMapper.getUserList();
        for (User u : userList) {
            System.out.println(u);
        }

    }

    // 查询单个数据
    @Test
    public void test3() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        int count = userMapper.getCount();
        System.out.println(count);

    }

    // 查询一条数据为map集合
    @Test
    public void test4() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        Map<String, Object> userToMap = userMapper.getUserToMap(7);
        System.out.println(userToMap);
    }

    // 查询多条数据为map集合
    @Test
    public void test5() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        List<Map<String, Object>> userToMapList = userMapper.getAllUserToMapList();
        Map<String, Object> allUserToMap = userMapper.getAllUserToMap();
        System.out.println(userToMapList);
        System.out.println(allUserToMap);
    }

}
