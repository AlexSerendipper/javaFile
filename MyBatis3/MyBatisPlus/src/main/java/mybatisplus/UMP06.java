package mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 【分页插件】
 *  MyBatis Plus自带分页插件，只要简单的配置即可实现分页功能
---------------------
// 创建配置类
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 创建分页插件 并 指定数据库
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
---------------------
 *  分页插件的使用
 * （1）new Page<>(int pageNum, int pageSize)             # 创建Page对象，当前页页码pageNum, 以及每页显示的条数pageSize、
 * （2）执行查询后，分页后的 相关的数据会封装在page对象中
 * （3）当前页数据使用 page.getRecord() 方法查看✔
 *
 *  自定义sql语句：使用mybatis提供的分页插件
 * （1）在Mapper中自定义方法，注意：传入的第一个对象为page<>对象
 * （2）方法的返回值为page<>对象, 并在泛型中指定对应的实体类
 *
 @author Alex
 @create 2023-03-27-20:20
 */
public class UMP06 {
    @Autowired
    UserMapper userMapper;

    // page功能测试
    @Test
    public void testPage(){
        // 设置分页参数
        Page<User> page = new Page<>(1, 5);
        userMapper.selectPage(page, null);
        // 获取分页数据
        List<User> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页："+page.getCurrent());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());
    }

    // 自定义sql使用分页功能
    @Test
    public void testSelectPageVo(){
        // 设置分页参数
        Page<User> page = new Page<>(2, 3);
        userMapper.selectPageVo(page, 20);
        // 获取分页数据
        List<User> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页："+page.getCurrent());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());
    }
}
