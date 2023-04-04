package mybatisplus.boot;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 @author Alex
 @create 2023-03-27-11:34
 */
@SpringBootTest
public class UMP05 {
    @Autowired
    UserMapper userMapper;
    @Test
    public void test01(){
        // 查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "a")
                .between("age", 20, 30)
                .isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    // 将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
    @Test
    public void test07() {
        // 组装set子句以及修改条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // lambda表达式内的逻辑优先运算
        updateWrapper.set("age", 18)
                .set("email", "user@atguigu.com")
                .like("name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        int result = userMapper.update(null, updateWrapper);
        System.out.println(result);
    }


    @Test
    public void test09() {
        // 定义查询条件，有可能为null（用户未输入）
        String name = "a";
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // 避免使用字符串表示字段，防止运行时错误
        queryWrapper.like(StringUtils.isNotBlank(name), User::getName, name)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
