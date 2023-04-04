package mybatisplus.boot;

import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UMP01 {
    // 不影响运行
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        // 通过条件构造器查询一个list集合，如果没有条件，传入null即可
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

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
