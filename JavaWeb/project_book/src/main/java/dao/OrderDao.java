package dao;

import bean.Order;

/**
 @author Alex
 @create 2023-02-16-14:20
 */
public interface OrderDao {
    public int saveOrder(Order order);
}
