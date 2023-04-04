<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/2/3
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
<%--由于不是form表单，没有隐藏域，所以用这个方式指明调用page方法--%>
<%--  分页：manager_menu.jsp ==> bookservlet ==> book_manager.jsp --%>
  <a href="manager/bookServlet?action=page">图书管理</a>
  <a href="order_manager.jsp">订单管理</a>
  <a href="index.jsp">返回商城</a>
</div>
