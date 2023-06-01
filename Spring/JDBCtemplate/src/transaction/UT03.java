package transaction;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import transaction.SpringConfig;
import transaction.service.UserService;

/**
 * 【编程式事务】
 * (1) 自动注入spring提供的 TransactionTemplate
 * (2) 使用 TransactionTemplate进行操作，其中的操作会被事务管理
 @author Alex
 @create 2023-04-08-14:34
 */
public class UT03 {
    @Autowired
    TransactionTemplate transactionTemplate;

    @Test
    public void test1() {
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
                UserService userService = context.getBean("userService", UserService.class);
                userService.accountMoney();
                return "ok";
            }
        });
    }
}
