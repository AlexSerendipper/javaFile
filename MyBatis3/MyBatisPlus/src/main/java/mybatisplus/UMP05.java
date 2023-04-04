package mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 【条件构造器Wrapper】
 * -- Wrapper ： 条件构造抽象类，最顶端父类
 *    -- AbstractWrapper ：  查询条件封装，生成 sql 的 where 条件
 *       (1) QueryWrapper ： 条件封装 （仅用于设置条件✔，故删除操作 也是使用QueryWrapper，更新操作如果传入了对象 也是使用QueryWrapper）
 *       (2) UpdateWrapper ： Update条件封装 (仅用于更新操作✔)
 *       (3) AbstractLambdaWrapper ： 使用 Lambda 语法
 *          -- LambdaQueryWrapper ：使用SFunction函数式表达式，可以访问实体类的属性值作为字段名，大大避免了字段名写错的问题
 *          -- LambdaUpdateWrapper ：使用SFunction函数式表达式，可以访问实体类的属性值作为字段名，大大避免了字段名写错的问题
 *
 * 【QueryWrapper & UpdateWrapper】
 *  QueryWrapper.le(String column, Object val)              # column为输入的为字段名，而非属性名。 val为字段对应值
 *  链式调用时，每次调用的条件都是使用and连接， 若需要使用or连接，可以使用.or()方法。
 *  ✔链式调用时，优先级如下， lambda表达式 ==> and ==> or
 *  通常在进行修改操作时，需要传入修改的对象。如 userMapper.update(user, queryWrapper);
 *   但是在 UpdateWrapper 中，也可以设置修改的字段。若设置了修改的字段，则不需要传入对象。如 userMapper.update(null, queryWrapper);
 *
 *
 * 【condition】
 *  真正开发的过程中，必须先判断用户输入的数据是否合法（或是是否输入），若没有输入则不需要拼接到sql中，这种要求可以通过condition实现
 *  QueryWrapper.le(boolean condition, String column, Object val)                  # column为输入的为字段名，而非属性名。 val为字段对应值
 *
 * 【LambdaQueryWrapper & LambdaUpdateWrapper】
 *  LambdaQueryWrapper.le(boolean condition, SFunction<> column, Object val)       # 使用SFunction函数式表达式，可以访问实体类的属性值作为字段名，大大避免了字段名写错的问题~
 *
 @author Alex
 @create 2023-03-27-11:18
 */
public class UMP05 {
    @Autowired
    UserMapper userMapper;

    // 查：查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息
    @Test
    public void test01(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "a")
                    .between("age", 20, 30)
                    .isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    // 查：按年龄降序查询用户，如果年龄相同则按id升序排列
    @Test
    public void test02(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age")
                    .orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    // 删：删除email为空的用户
    @Test
    public void test03(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        // 条件构造器也可以构建删除语句的条件
        int result = userMapper.delete(queryWrapper);
        System.out.println("受影响的行数：" + result);
    }

    // ✔改：将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
    // 注意 and 的优先级比 or高，所以直接链式操作即可
    @Test
    public void test04() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("username", "a")
                    .gt("age", 20)
                    .or()
                    .isNull("email");
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("受影响的行数：" + result);
    }

    // ✔改：将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
    @Test
    public void test044() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 将用户名中包含有a 并且（年龄大于20或邮箱为null）的用户信息修改
        // lambda表达式内的逻辑优先运算
        queryWrapper.like("username", "a")
                   .and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("受影响的行数：" + result);
    }

    // 查询用户信息的name和age字段
    @Test
    public void test05() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "age");
        // selectMaps()返回Map集合列表，通常配合select()使用，避免User对象中没有被查询到的列值为null
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }


    // 查询id小于等于3的用户信息(当然这个功能可以有更简单的实现方式，这里仅作为例子演示)
    // select * from mybatisplus_user where id in(select id from mybatisplus_user where id<3)
    @Test
    public void test06() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // insql就是使用in关键字的子查询sql
        queryWrapper.inSql("id", "select id from mybatisplus_user where id <= 3");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    // ********************************************UpdateWrapper********************************************************
    // 将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
    @Test
    public void test07() {
        //
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

    // ********************************************condition********************************************************
    @Test
    public void test08UseCondition() {
        //定义查询条件，有可能为null（用户未输入或未选择）
        String name = null;
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // StringUtils.isNotBlank()判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成
        queryWrapper.like(StringUtils.isNotBlank(name), "name", "a")
                    .ge(ageBegin != null, "age", ageBegin)
                    .le(ageEnd != null, "age", ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    // ********************************************AbstractLambdaWrapper************************************************
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

    @Test
    public void test10() {
        // 组装set子句
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAge, 18)
                     .set(User::getEmail, "user@atguigu.com")
                     .like(User::getName, "a")
                     .and(i -> i.lt(User::getAge, 24).or().isNull(User::getEmail));
        User user = new User();
        int result = userMapper.update(user, updateWrapper);
        System.out.println("受影响的行数：" + result);
    }
}
