<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/2/13
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="http://localhost:8080/javaweb/loginServlet" method="get">
        <%--用户名和密码首先从cookie中取值--%>
        用户名：<input type="text" name="username" value="${cookie.username.value}"> <br>
        密码：<input type="password" name="password" value="${cookie.password.value}"> <br>
        <input type="submit" value="登录">
    </form>
</body>
</html>
