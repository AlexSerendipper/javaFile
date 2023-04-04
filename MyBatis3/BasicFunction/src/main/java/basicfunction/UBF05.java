package basicfunction;

import basicfunction.mappers.UserMapper;
import basicfunction.pojo.User;
import basicfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * 【特殊SQL的执行】在某些特殊SQL中 推荐使用${}
 *
 * 【模糊查询】like关键字
 *   无法使用#{}，因为模糊查询的条件在单引号中，若使用#{}，实际上会被解析成问号（自带单引号），而实际上不需要再有单引号 ，故出错
 *     如 select * from mybatis_user where username like '% #{blur_name} %'  被解析为
 *        select * from mybatis_user where username like '% ？ %'
 *   解决方案1：使用 ${}
 *   解决方案2：使用 concat('%',#{blur_name},'%')
 *   ✔解决方案3：使用 "%"#{blur_name}"%"
 *
 * 【批量删除】in关键字
 *  无法使用#{}，因为in中若使用#{}，实际上会被解析成问号（自带单引号），而实际上不需要再有单引号 ，故出错
 *   如 delete from mybatis_user where id in (#{ids})  被解析为
 *      delete from mybatis_user where id in (？)
 *  解决方案：使用 ${}
 *
 * 【动态设置表名】从而实现查询不同的表
 *  无法使用#{}，因为查询中若使用#{}，实际上会被解析成问号（自带单引号），而实际上在查询中，表名是不能加单引号的
 *  解决方案：使用 ${}
 *
 * 【获取添加信息时 自增的主键（若设置了auto_increment）】
 *  需要在<insert>标签中设置两个属性
 *   useGeneratedKeys="true"               # 设置允许获得自增的主键
 *   keyProperty="id"                      # 因为增删改有统一的返回值是受影响的行数，因此只能将获取的 自增的主键 放在 传入参数的实体类对象的某个属性中
 *                                            当执行完添加操作后，传入参数的实体类对象的某个属性被赋值为 自增的主键
 *
 @author Alex
 @create 2023-03-12-11:18
 */
public class UBF05 {
    // 根据名字包含的内容模糊查询
    @Test
    public void test1() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        List<User> user = userMapper.getUserByLike("张");
        System.out.println(user);
    }

    // 测试批量删除
    @Test
    public void test2() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        int result = userMapper.deleteMore("1,2,4");
        System.out.println(result);
    }

    // 查询指定表中的数据
    @Test
    public void test3() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        List<User> allUser = userMapper.getAllUser("mybatis_user");
        List<User> allUser2 = userMapper.getAllUser("mybatis_user2");
        System.out.println(allUser);
        System.out.println(allUser2);
    }

    // 获取添加信息时 自增的主键（若设置了auto_increment）
    @Test
    public void test4() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        User user = new User("hjy", "aaa", 17, "男", "642671525@qq.com");
        int result = userMapper.insertUserGetKey(user);
        System.out.println(result);
        System.out.println(user);
    }

}
