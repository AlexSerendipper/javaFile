package mybatisplus.boot;

import mybatisplus.enums.SexEnum;
import mybatisplus.mapper.ProductMapper;
import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.Product;
import mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 @author Alex
 @create 2023-03-27-11:34
 */
@SpringBootTest
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
