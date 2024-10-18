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
 * 【mapper接口映射文件编写（传入参数值）】即如何将参数传递给mapper.xml（sql语句）
 *  MyBatis获取参数值的两种方式：${}和#{}
 *   ${}的本质就是字符串拼接, 有sql注入风险，并且若为字符串类型或日期类型的字段进行赋值时，需要手动加单引号
 *   #{}的本质是使用占位符赋值，自动添加单引号。故推荐使用#{}的方式获取参数值
 *   如：select * from mybatis_user where username = #{username}
 *      select * from mybatis_user where username = "${username}"
 *  理论上 ${}、#{} 中可以传入任意的字符串 (但是必须要有传入，idea中在调用方法时会提示我们传入的字符串)
 *   建议见名知意原则：我们约定 传入的值 与对应的 字段名/属性名 相同，便于见名知意
 *
 * 【传入参数的各种情况】userMapper.xxx(Int、String/Map/Pojo/Array、List)
 * （1）单个参数传入mapper接口
 *   sql语句中使用 ${}和#{}都可以实现获取该参数，注意${}需要手动加单引号
 *
 * （2）多个参数传入mapper接口（自动map）
 *   此时MyBatis会自动将这些参数放在一个map集合中，以arg0,arg1...为键，以参数为值；
 *                                             或 以param1,param2...为键，以参数为值；
 *    因此sql语句中只需要通过${}和#{}访问 map集合的键(arg1、param1...) 就可以获取相对应的值，如 '${arg0}'
 *
 *  (3) Map类型参数传入mapper接口方法：极少用！
 *   我们完全可以不使用MyBatis自动创建的map集合，自己手动创建map集合，将这些数据放在map中
 *    通过 ${}和#{} 访问 'map集合中设置的键' 就获取相对应的值
 *
 *  (4) ✔✔Pojo实体类传入mapper接口中的方法
 *   此时可以直接通过访问实体类对象中的 属性名 获取属性值，注意${}需要手动加单引号
 *
 *  (5) Array、List类型传入mapper接口中的方法：动态sql常用
 *   此时MyBatis会自动将这些参数放在一个map集合中，以 array 为键，以 数组/集合 为值；
 *                                            和 以 arg0 为键，以 数组/集合 为值；
 *     因此只需要通过${}和#{}访问 map集合的键 就可以获取相对应的 数组/集合，如 '${arg0}'
 *
 *  【@Param(value="") 标识参数】✔✔
 *   对于上述方式二，当MyBatis自动将多个参数放在一个map集合，使用 #{arg0} 这样的方式来获取参数值便不够见名知意了
 *     可以在编写usermapper时，使用该注解，设置创建传入参数值 与 mapper映射文件中获取参数值的映射关系
 *     如下：
--------------
userMapper.checkLoginByParam("zzj", "qqabcd");
User checkLoginByParam(@Param("uu") String username, @Param("pp") String password);    // 创建映射关系
select * from mybatis_user where username = "${uu}" and password = #{pp}               // 此时通过映射关系，不再需要通过#{arg0}这样的方式来访问，满足了见名知意原则
--------------
 *   ✔✔由于获取单个参数时，在映射文件中 默认使用 与传入参数同名 的 变量就可以获取参数。
 *          因此其本身就满足见名知意原则
 *          故因此强烈强烈建议建议 方法二和方法五（即传入参数为多个，或传入数组集合的情况）都使用@Param注解。以满足见名知意
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

    // 多个字面量类型的参数（推荐适用@param注解）
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
