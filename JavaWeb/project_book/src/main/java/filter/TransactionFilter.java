package filter;

import utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 当前web工程下所有资源被访问都会被捕获
 * 目标资源包括：html页面、jsp页面、txt文本、jpg图片、servlet程序等！ 即访问这些资源时都可以触发filterChain
 * 以OrderServletImp中出异常为例
 * 当cart.jsp中的点击去结账时，访问orderServlet?action=createOrder。被TransactionFilter捕获，运行filterChain.doFilter()，即直接让其访问目标资源，即去执行createOrder方法
 * （1）当执行到orderService.createOrder出现异常，一路抛出到TransactionFilter后直接回滚
 * （2）一路没有抛出异常，提交事务
 * （
 *   因为通常一个servlet程序就对应了一个功能，所以可以对所有资源都进行try catch一下
 *   而访问其他资源的时候，提交事务并不产生实际效果
 *   仅servlet程序中提交事务有效
 *   ）
 *
 *
 *
 *
 @author Alex
 @create 2023-02-17-16:30
 */
public class TransactionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
            JdbcUtils.commitAndClose();// 提交事务
        } catch (Exception e) {
            JdbcUtils.rollbackAndClose();//回滚事务
            e.printStackTrace();
            throw new RuntimeException(e);  // 把异常抛给tomcat统一展示友好的错误页面
        }
    }

    @Override
    public void destroy() {
    }
}
