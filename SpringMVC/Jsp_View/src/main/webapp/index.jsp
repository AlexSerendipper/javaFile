<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/3/4
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页·</title>
</head>
<body>
    <%--发起具体的请求地址--%>
    <a href="${pageContext.request.contextPath}/success">success.jsp</a><br>
    <%--手动补充完整上下文路径--%>
    <a href="/jsp/success">success.jsp</a><br>
</body>
</html>
