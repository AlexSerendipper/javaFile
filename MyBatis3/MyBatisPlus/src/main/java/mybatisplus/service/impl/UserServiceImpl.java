package mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.User;
import mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 @author Alex
 @create 2023-03-25-14:05
 */

@DS("master")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
