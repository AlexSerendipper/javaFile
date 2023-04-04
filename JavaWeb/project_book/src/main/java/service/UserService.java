package service;

import bean.User;

/**
 * 一个业务就是一个方法，当前接口规范了用户相关业务
 @author Alex
 @create 2023-02-01-13:48
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);


    /**
     * 登录，实际上是根据传入user的用户名和密码判断数据库中是否有相同的user
     * @param user
     * @return 如果返回 null，说明登录失败，返回有值（值为查询到的user），是登录成功
     */
    public User login(User user);


    /**
     * 检查 用户名是否可用
     * @param username
     * @return 返回 true 表示用户名已存在，返回 false 表示用户名可用
     */
    public boolean existsUsername(String username);
}
