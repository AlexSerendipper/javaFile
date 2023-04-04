package mybatisplus;

import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import mybatisplus.pojo.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【MyBatisPlus简介】 
 *  MyBatis-Plus（简称 MP）是一个 MyBatis的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
 *
 * 【MyBatisPlus使用流程】
 * （1）引入相关依赖 (也可以用spring initializr直接勾选，就不用配置了)
----------------
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.1</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.11</version>
<dependency>
-------------------
 *（2）在Spring Boot启动类中添加 @MapperScan注解，扫描mapper包
 *（3）配置日志功能（方便查看执行的sql语句）
-------------------
# 配置MyBatis日志
mybatis-plus.configuration.log-impl = org.apache.ibatis.logging.stdout.StdOutImpl
# 设置类型别名，的包扫描位置
mybatis-plus.type-aliases-package=mybatisplus.pojo
-------------------
 *（4）添加实体类（默认实习类类名需要和表名一致，字段名需要和属性名对应）
 *（5）添加mapper
 *    ✔BaseMapper类 是 MyBatis-Plus 提供的模板mapper，其中包含了基本的CRUD方法，泛型为操作的实体类型。
 *    ✔只需要在我们自己的mapper中继承BaseMapper, 就可以方法的使用其提供的方法
 *    ✔此时不再需要创建mapper接口的映射文件编写sql语句，就可以直接进行使用啦（自动装配mapper接口，即会在ioc容器中自动生成其实现类）
 *
 * 【CURD操作】见下方，wrapper相关的之后再讲
 *  大多方法中都有Wrapper类型的形参，此为条件构造器，可针对于SQL语句设置不同的条件
 *    若没有条件，则可以为该形参赋值null，即查询（删除/修改）所有数据
 @author Alex
 @create 2023-03-25-11:47
 */

public class UMP01 {
    @Autowired
    UserMapper userMapper;
    // 常用方法测试（需要在test目录下才能运行）
    // 插入数据（默认会通过雪花算法，生成一个唯一的主键id）
    @Test
    public void test1(){
        User user = new User(null, "张三", 23, "zhangsan@atguigu.com");
        int result = userMapper.insert(user);
        System.out.println("受影响行数："+result);
        // 1475754982694199298
        System.out.println("id自动获取："+user.getId());
        // 最终执行的结果，所获取的id为1475754982694199298（并非自增的id）这是因为MyBatis-Plus在实现插入数据时，会默认基于雪花算法的策略生成id
    }

    // 通过id删除记录
    @Test
    public void testDeleteById(){
        // 通过id删除用户信息
        // DELETE FROM user WHERE id=?
        int result = userMapper.deleteById(1475754982694199298L);
        System.out.println("受影响行数："+result);
    }

    // 通过id批量删除记录
    @Test
    public void testDeleteBatchIds(){
        // 通过多个id批量删除
        // DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("受影响行数："+result);
    }

    // 通过map条件删除记录
    @Test
    public void testDeleteByMap(){
        // 根据map集合中所设置的条件删除记录
        // DELETE FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("age", 23);
        map.put("name", "张三");
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响行数："+result);
    }
    // 修改
    @Test
    public void testUpdateById(){
        User user = new User(4L, "admin", 22, null);
        // UPDATE user SET name=?, age=? WHERE id=?
        int result = userMapper.updateById(user);
        System.out.println("受影响行数："+result);
    }

    // 根据id查询用户信息
    @Test
    public void testSelectById(){
        //根据id查询用户信息
        //SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(4L);
        System.out.println(user);
    }

    // 根据多个id查询多个用户信息
    @Test
    public void testSelectBatchIds(){
        // 根据多个id查询多个用户信息
        // SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
        List<Long> idList = Arrays.asList(4L, 5L);
        List<User> list = userMapper.selectBatchIds(idList);
        list.forEach(System.out::println);
    }

    // 通过map条件查询用户信息
    @Test
    public void testSelectByMap(){
        // 通过map条件查询用户信息
        // SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("age", 22);
        map.put("name", "admin");
        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    // 查询所有数据
    @Test
    public void testSelectList(){
        // 查询所有用户信息
        // SELECT id,name,age,email FROM user
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
