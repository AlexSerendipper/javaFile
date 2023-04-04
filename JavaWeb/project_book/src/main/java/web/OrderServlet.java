package web;

import bean.Cart;
import bean.User;
import service.OrderService;
import service.impl.OrderServiceImpl;
import utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 @author Alex
 @create 2023-02-16-15:10
 */
public class OrderServlet extends BaseServlet{
    private OrderServiceImpl orderService = new OrderServiceImpl();

    /**
     * 生成订单（点击购物车中的结账后生成订单）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 先获取 Cart 购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        // 获取 Userid(User对象于登录后保存在session域中)
        // 用户不存在，请先登录
        User loginUser = (User) req.getSession().getAttribute("user");
        if (loginUser == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        // 用户存在，保存订单。调用 orderService.createOrder(Cart,Userid);生成订单
        Integer userId = loginUser.getId();
        String orderId = null;
//        // 这样需要给每一个servlet中每一个方法调用service.xxx()都加上try-catch，比较笨拙
//        try {
//            orderId = orderService.createOrder(cart, userId);
//            // 没有异常
//            JdbcUtils.commitAndClose();
//        } catch (Exception e) {
//            JdbcUtils.rollbackAndClose();
//            throw new RuntimeException(e);
//        }
        // 所以利用filter统一try catch
        orderId = orderService.createOrder(cart, userId);
        // 为了防止用户重复提交，不建议使用请求转发，请求重定向到/pages/cart/checkout.jsp
        req.getSession().setAttribute("orderId",orderId);
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }
}
