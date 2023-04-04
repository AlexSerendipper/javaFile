package basicfunction;

import basicfunction.mappers.UserMapper;
import basicfunction.pojo.User;
import basicfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

/**
 * 【MyBatis获取参数值】两种方式
 *  MyBatis获取参数值的两种方式：${}和#{}
 *   ${}的本质就是字符串拼接, 有sql注入风险，并且若为字符串类型或日期类型的字段进行赋值时，需要手动加单引号
 *   #{}的本质是使用占位符赋值，自动添加单引号。故推荐使用#{}的方式获取参数值
 *   如：select * from mybatis_user where username = #{username}
 *       select * from mybatis_user where username = "${username}"
 *  理论上 ${}、#{} 中可以传入任意的字符串 (但是必须要有传入，idea中在调用方法时会提示我们传入的字符串)
 *   见名知意原则：我们约定 传入的值 与对应的 字段名/属性名 相同，便于见名知意
 *
 * 【获取参数的各种情况】
 * （1）mapper接口中的方法所需参数为单个
 *   ${}和#{}都可以实现，注意${}需要手动加单引号
 *
 * （2）mapper接口中的方法所需参数为多个（自动map）
 *   此时MyBatis会自动将这些参数放在一个map集合中，以arg0,arg1...为键，以参数为值；
 *                                           和 以param1,param2...为键，以参数为值；
 *    因此只需要通过${}和#{}访问 map集合的键 就可以获取相对应的值，如 '${arg0}'
 *
 *  (3) mapper接口中的方法所需参数为 map 类型：极少用！
 *   我们完全可以不使用MyBatis自动创建的map集合，自己手动创建map集合，将这些数据放在map中
 *    通过 ${}和#{} 访问map集合的键就获取相对应的值
 *
 *  (4) ✔✔mapper接口中的方法所需参数为实体类类型
 *   此时可以直接通过访问实体类对象中的 属性名 获取属性值，注意${}需要手动加单引号
 *
 *  (5) 当所需参数为array、list类型时：动态sql常用
 *   此时MyBatis会自动将这些参数放在一个map集合中，以 array 为键，以 数组/集合 为值；
 *                                           和 以 arg0 为键，以 数组/集合 为值；
 *     因此只需要通过${}和#{}访问 map集合的键 就可以获取相对应的 数组/集合，如 '${arg0}'
 *
 *  【@Param(value="") 标识参数】
 *   当MyBatis自动将多个参数放在一个map集合，使用该注解可以自定义键值
 *     ✔✔以@Param注解的value属性值 为键，以参数为值
 *   只需要通过${}和#{}访问map集合中我们的 自定义键 就可以获取相对应的值，注意${}需要手动加单引号
 *   ✔✔由于获取单个参数时，默认使用 与传入参数同名 的 变量就可以实现调用参数。故可以不用@Param
 *    但是强烈建议建议 方法二和方法五（即传入参数为多个，或传入数组集合的情况）都使用@Param注解。满足见名知意
 *
 *
 @author Alex
 @create 2023-03-11-10:05
 */
public class UBF03 {
    // 单个字面量类型的参数
    @Test
    public void test1() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        User user = userMapper.getUserByUsername("ybc");
        System.out.println(user);
    }

    // 多个字面量类型的参数
    @Test
    public void test2() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        User user = userMapper.checkLogin("ybc","123");
        System.out.println(user);
    }

    // 多个字面量类型的参数(传入map)
    @Test
    public void test3() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("username","ybc");
        map.put("password","123");
        User user = userMapper.checkLoginByMap(map);
        System.out.println(user);
    }

    // mapper接口中的方法所需参数为实体类类型(传入User)
    @Test
    public void test4() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        User user = new User(9, "zzj", "qqabcd", 17, "男", "642671525@qq.com");
        int i = userMapper.insert(user);
        System.out.println("被影响的行数为：" + i);
    }

    // 多个字面量类型的参数(传入@param)
    @Test
    public void test5() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        User user = userMapper.checkLoginByParam("zzj", "qqabcd");
        System.out.println(user);
    }


}
