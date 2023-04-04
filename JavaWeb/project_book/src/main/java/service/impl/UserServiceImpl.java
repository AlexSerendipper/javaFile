package service.impl;

import bean.User;
import dao.impl.UserDaoImpl;
import service.UserService;

/**
 * 业务逻辑是那些，但是具体操作是交给DAO来做
 * 右键--go to --test，可以快速测试所有方法
 @author Alex
 @create 2023-02-01-13:58
 */
public class UserServiceImpl implements UserService {
    private UserDaoImpl dao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
        dao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return dao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        if(dao.queryUserByUsername(username)==null){
            return false;
        }else {
            return true;
        }
    }
}
