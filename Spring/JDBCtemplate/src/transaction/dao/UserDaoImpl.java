package transaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 @author Alex
 @create 2023-02-27-10:24
 */

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //lucy 转账 100 给 mary
    //少钱
    @Override
    public void reduceMoney() {
        String sql = "update transaction_account set money=money-? where username=?";
        jdbcTemplate.update(sql,100,"lucy");
    }

    //多钱
    @Override
    public void addMoney() {
        String sql = "update transaction_account set money=money+? where username=?";
        jdbcTemplate.update(sql,100,"mary");
    }
}
