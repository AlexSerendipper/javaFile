package IoC.dao;

import org.springframework.stereotype.Repository;

/**
 @author Alex
 @create 2023-02-23-12:40
 */

@Repository(value = "userDaoImpl2")
public class UserDaoImpl2 implements UserDao{
    @Override
    public void update() {
        System.out.println("dao2 update...");
    }
}
