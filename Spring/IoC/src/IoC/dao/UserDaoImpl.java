package IoC.dao;


import org.springframework.stereotype.Repository;

@Repository(value = "UserDaoImpl1")
/**
 @author Alex
 @create 2023-02-21-13:27
 */
public class UserDaoImpl implements UserDao{
    @Override
    public void update() {
        System.out.println("dao1 update...");
    }
}
