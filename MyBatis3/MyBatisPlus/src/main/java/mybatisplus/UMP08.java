package mybatisplus;

import mybatisplus.enums.SexEnum;
import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 【通用枚举】
 *  表中的有些字段值是固定的，例如性别（男或女），此时我们可以使用MyBatis-Plus的 通用枚举 功能来实现
 * (1) 在数据库中创建枚举类字段
 * (2) 创建字段对应的枚举类，并添加@Getter，并在 需要插入数据库的字段上 添加@EnumValue注解✔
 * (3) 在对应实体类中设置对应的枚举类属性 。如 private SexEnum sex;
 *     在进行数据库操作时，会将@EnumValue注解所标识的值 存储到数据库中
 *（4）在配置文件中配置 扫描通用枚举
---------------------
# 扫描枚举类所在包（mybatisplus从3.5.2版本后 就无需配置扫描通用枚举了）
mybatis-plus.type-enums-package=mybatisplus.enums
---------------------

 @author Alex
 @create 2023-03-28-12:53
 */
public class UMP08 {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testSexEnum(){
        // 设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
        User user = new User("Enum", 11, "11@qq.com", SexEnum.FAMALE);
        userMapper.insert(user);
    }
}
