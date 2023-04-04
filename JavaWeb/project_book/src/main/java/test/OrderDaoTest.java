package test;

import bean.Order;
import bean.OrderItem;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderItemDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;


/**
 @author Alex
 @create 2023-02-16-14:45
 */
public class OrderDaoTest {
    @Test
    public void saveOrder() {
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.saveOrder(new Order("1234567890",new Date(),new BigDecimal(100),0, 1));

    }

    @Test
    public void saveOrderItem() {
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        orderItemDao.saveOrderItem(new OrderItem(null,"java 从入门到精通", 1,new BigDecimal(100),new BigDecimal(100),"1234567890"));
        orderItemDao.saveOrderItem(new OrderItem(null,"javaScript 从入门到精通", 2,new BigDecimal(100),new BigDecimal(200),"1234567890"));
        orderItemDao.saveOrderItem(new OrderItem(null,"Netty 入门", 1,new BigDecimal(100),new BigDecimal(100),"1234567890"));
}
}