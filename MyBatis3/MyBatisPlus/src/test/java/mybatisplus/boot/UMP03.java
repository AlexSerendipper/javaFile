package mybatisplus.boot;

import mybatisplus.pojo.User;
import mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 @author Alex
 @create 2023-03-26-13:27
 */

@SpringBootTest
public class UMP03 {
    @Autowired
    private UserService userService;

    // 测试通用service输出总记录数
    @Test
    public void test1(){
        long count = userService.count();
        System.out.println(count);
    }

    // 测试通用service进行批量添加操作
    // 这个在basicMapper中就没有批量添加的方法
    @Test
    public void test2(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(6L,"zzj",18,"642671525@qq.com"));
        users.add(new User(7L,"lzy",18,"642671525@qq.com"));
        users.add(new User(8L,"hjy",18,"642671525@qq.com"));
        users.add(new User(9L,"hyq",18,"642671525@qq.com"));
        boolean result = userService.saveBatch(users);
        System.out.println(result);
    }
}
