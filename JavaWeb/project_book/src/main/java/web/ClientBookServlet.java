package web;

import bean.Book;
import bean.Page;
import service.impl.BookServiceImpl;
import utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 @author Alex
 @create 2023-02-04-14:46
 */
public class ClientBookServlet extends BaseServlet{
    private BookServiceImpl bookService = new BookServiceImpl();
    /**
     * 实现分页功能，和后台的一模一样
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        //1 获取请求的参数 pageNo 和 pageSize
        System.out.println("webapp下的index转发到client下的首页啦");
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2 调用 BookService.page(pageNo，pageSize)：Page 对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        // 抽取所有请求地址，方便一次性修改
        // 该请求地址即该servlet
        page.setUrl("client/bookServlet?action=page");
        //3 保存 Page 对象到 Request 域中
        req.setAttribute("page",page);
        //4 请求转发到 pages/manager/book_manager.jsp 页面作动态显示
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }


    /**
     * 处理输入价格区间后的分页
     */
    public void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        // 1、 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"),0);
        int max = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);
        // 2、调用方法返回Page对象
        Page<Book> page = bookService.pageByPrice(pageNo, pageSize, min, max);
        // 抽取所有请求地址，方便一次性修改(这里需要让所有的分页符都要有请求区间)
        // 如果有最小价格参数，就追加到分页条的地址栏中。但其实这个Min和max都有默认值，我不懂为啥要这样弄
        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        if(req.getParameter("min")!=null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        if(req.getParameter("max")!=null){
            sb.append("&max=").append(req.getParameter("max"));
        }
        page.setUrl(sb.toString());
        // 3、保存分页对象到request域中
        req.setAttribute("page",page);
        // 4、请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

}
