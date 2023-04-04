<%@ page import="javax.naming.Context" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/2/3
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%--动态获取base标签的值，避免所有ip访问都是用Localhost--%>
<%
        String basePath = request.getScheme()
                + "://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath()
                + "/";
        pageContext.setAttribute("basePath",basePath);
%>

<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
