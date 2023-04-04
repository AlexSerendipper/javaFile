package test;

import bean.User;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import org.junit.Test;

/**
 @author Alex
 @create 2023-02-01-13:42
 */
public class UserDaoImplTest {
    UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {
        if (userDao.queryUserByUsername("admin1234") == null) {
            System.out.println("用户名可用！");
        } else {
            System.out.println("用户名已存在！");
        }
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        if (userDao.queryUserByUsernameAndPassword("admin", "admin1234") == null) {
            System.out.println("用户名或密码错误，登录失败");
        } else {
            System.out.println("查询成功");
        }
    }

    @Test
    public void saveUser() {
        int wzg168 = userDao.saveUser(new User(null, "wzg168", "123456", "wzg168@qq.com"));
        System.out.println(wzg168);
    }
}
