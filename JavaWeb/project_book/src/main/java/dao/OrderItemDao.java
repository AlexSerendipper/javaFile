package dao;

import bean.Order;
import bean.OrderItem;

/** 本项目中只演示保存订单和保存订单项的dao~~~其余功能自行研究
 @author Alex
 @create 2023-02-16-14:20
 */

public interface OrderItemDao {
    public int saveOrderItem(OrderItem orderItem);
}
