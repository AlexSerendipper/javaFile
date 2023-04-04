package web;

import bean.Book;
import bean.Cart;
import bean.CartItem;
import com.google.gson.Gson;
import service.BookService;
import service.impl.BookServiceImpl;
import utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 @author Alex
 @create 2023-02-16-10:52
 */
public class CartServlet extends BaseServlet{
    private BookServiceImpl bookService = new BookServiceImpl();
    /**
     * 加入购物车方法1,传统servlet实现
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        // 调用 bookService.queryBookById(id):Book 得到图书的信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成为 CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        // 调用 Cart.addItem(CartItem);添加商品项
        // 不能每次添加商品都重新new一个购物车，故将cart放在session域中
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
//        System.out.println(cart);
//        System.out.println("请求头 Referer 的值：" + req.getHeader("Referer"));
        // 重定向回原来商品所在的地址页面（要保证原来是在第二页浏览，也要跳转回第二页）
        // HTTP协议中有一个请求头，叫Referer，它可以把请求发起时浏览器地址栏中的地址发送给服务器
        System.out.println("添加图书成功");
        System.out.println(book.getName());
        // 返回最后一个添加的图书的名称
        req.getSession().setAttribute("lastName",book.getName());
        resp.sendRedirect(req.getHeader("Referer"));
    }


    /**
     * 加入购物车方法2，ajax实现
     * @param req,
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        // 调用 bookService.queryBookById(id):Book 得到图书的信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成为 CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        // 调用 Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        System.out.println(cart);
        // 最后一个添加的商品名称
        req.getSession().setAttribute("lastName", cartItem.getName());
        // ajax请求，利用json，返回购物车总的商品数量和最后一个添加的商品名称
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("totalCount", cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());
        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);
        resp.getWriter().write(resultMapJsonString);
    }

    /**
     * 删除商品的方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        // 基于有删除这个选项，所以session域中是一定有cart的
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        cart.deleteItem(id);
        System.out.println("删除图书成功");
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 清空购物车方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 基于有清空购物车这个选项，所以session域中是一定有cart的
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        cart.clear();
        System.out.println("清空图书成功");
        resp.sendRedirect(req.getHeader("Referer"));
    }


    /**
     * 更新数量的方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数 商品编号 和 修改的数量
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        int count = WebUtils.parseInt(req.getParameter("count"),0);
        // 获取 Cart 购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        // 修改商品数量
         cart.updateCount(id,count);
        // 重定向回原来购物车展示页面
        resp.sendRedirect(req.getHeader("Referer"));
    }
}
