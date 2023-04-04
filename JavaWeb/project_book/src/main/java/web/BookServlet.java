package web;

import bean.Book;
import bean.Page;
import service.BookService;
import service.impl.BookServiceImpl;
import utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 @author Alex
 @create 2023-02-03-15:13
 */
public class BookServlet extends BaseServlet{
    private BookService bookService = new BookServiceImpl();

    /**
     * 在图书管理页面，实现根据数据库动态展示图书的功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        //1 通过 BookService 查询全部图书
        List<Book> books = bookService.queryBooks();
        //2 把全部图书保存到 Request 域中
        req.setAttribute("books", books);
        //3、请求转发到/pages/manager/book_manager.jsp 页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    /**
     * 添加图书功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        // 1、封装成对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        // 2、添加图书
        bookService.addBook(book);
        // 3、请求重定向
        // 当用户提交完请求，浏览器会记录下最后一次请求的全部信息。当用户按下功能键 F5，就会发起浏览器记录的最后一次请求。
        // 所以不能用请求转发，否则每次刷新都上传了一次图书表单
        // 重定向到 ++pageNo，避免了新增到新一页的数据而不是最后一页
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),0);
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + ++pageNo);
    }

    /**
     * 删除图书功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        // 1、获取请求的参数 id，图书编程
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        // 2、调用 bookService.deleteBookById();删除图书
        bookService.deleteBookById(id);
        // 3、请求重定向
        // 当用户提交完请求，浏览器会记录下最后一次请求的全部信息。当用户按下功能键 F5，就会发起浏览器记录的最后一次请求。
        // 所以不能用请求转发，否则每次刷新都上传了一次图书表单
        // 重定向到 ++pageNo，避免了新增到新一页的数据而不是最后一页
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),0);
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + ++pageNo);
    }


    /**
     * 修改图书时，查询图书显示在修改页面上
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        // 1、获取请求的参数 id，图书编程
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        // 2、查询图书
        Book book = bookService.queryBookById(id);
        // 3、保存图书到request域中
        req.setAttribute("book",book);
        // 请求转发(请求转发，那些请求参数都是带着的，所以在update中可以直接用)
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }


    /**
     * 具体的更新图书的功能。请求流程为。
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        // 1、获取请求的参数==封装成为 Book 对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());
        // 2、调用 BookService.updateBook( book );修改图书
        // 该方法是需要id的，而默认表中是发不过来id的，所以需要在book_edit页面中添加隐藏域
        bookService.updateBook(book);
        // 3、重定向回图书列表管理页面
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),0);
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

    /**
     * 实现分页功能，实际上有了分页功能以后就不需要list动态显示数据库中的图书了
     * manager_menu.jsp ==> bookservlet ==> book_manager.jsp
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2 调用 BookService.page(pageNo，pageSize)：Page 对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        // 抽取所有请求地址，方便一次性修改
        page.setUrl("manager/bookServlet?action=page");
        //3 保存 Page 对象到 Request 域中
        req.setAttribute("page",page);
        //4 请求转发到 pages/manager/book_manager.jsp 页面作动态显示
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

}
