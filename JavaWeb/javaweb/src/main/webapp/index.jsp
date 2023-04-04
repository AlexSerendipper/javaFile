<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<h1><%= "没有登录成功哦~被转发到webapp下的index.jsp文件呢" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<%
    System.out.println("目标资源页面被执行");
    System.out.println("目标资源界面的线程是" + Thread.currentThread().getName());
    System.out.println("共享的request域中的数据为" + request.getAttribute("username"));
%>
</body>
</html>