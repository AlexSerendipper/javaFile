package test;

import bean.Cart;
import bean.CartItem;
import org.junit.Test;
import service.OrderService;
import service.impl.OrderServiceImpl;

import java.math.BigDecimal;

/**
 @author Alex
 @create 2023-02-16-15:07
 */
public class OrderServiceTest {
    @Test
    public void createOrder() {
    Cart cart = new Cart();
    cart.addItem(new CartItem(1, "java从入门到精通", 1, new BigDecimal(1000),new BigDecimal(1000)));
    cart.addItem(new CartItem(1, "java从入门到精通", 1, new BigDecimal(1000),new BigDecimal(1000)));
    cart.addItem(new CartItem(2, "数据结构与算法", 1, new BigDecimal(100),new BigDecimal(100)));
    OrderService orderService = new OrderServiceImpl();
    System.out.println( "订单号是：" + orderService.createOrder(cart, 1) );
    }
}