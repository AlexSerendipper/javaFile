package transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import transaction.dao.UserDao;

/**
 @author Alex
 @create 2023-02-27-10:24
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class UserService {
    @Autowired
    private UserDao userDao;

    // 进行转账的方法
    public void accountMoney(){
        // lucy少钱
        userDao.reduceMoney();
        // 转账出现异常
//        int i = 10/0;
        // mary多钱
        userDao.addMoney();
    }
}
