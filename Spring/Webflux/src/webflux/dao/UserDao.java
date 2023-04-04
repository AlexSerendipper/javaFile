package webflux.dao;

/**
 @author Alex
 @create 2023-02-27-10:24
 */
public interface UserDao {
    //lucy 转账 100 给 mary
    //少钱
    void reduceMoney();

    //多钱
    void addMoney();
}
