package IoC.service;

import IoC.dao.UserDao;
import IoC.dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 @author Alex
 @create 2023-02-21-13:29
 */

@Service
public class UserService {
    /**
     * 传统方式:
     * UserDaoImpl userDao = new UserDaoImpl();
     * userDao.update();
     */

    @Value(value = "使用注解的方式注入的普通属性")
    public String name;


    //创建 UserDao 类型属性
    // @Autowired  // 自动注入
    // @Qualifier(value = "userDaoImpl2")  // 根据名称注入
    @Resource(name="userDaoImpl2")

    private UserDao userDao;
    // 生成 set 方法，便可通过xml配置方式进行注入（使用注解方式无需进行此操作）
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add() {
        System.out.println("service add...............");
        userDao.update();
    }
}
