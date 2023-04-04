package service;

import bean.Cart;

/**
 @author Alex
 @create 2023-02-16-14:55
 */

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
}
