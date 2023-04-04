<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP - Hello World</title>
</head>
<body>

<%--声明脚本演示--%>
<%--1、声明类属性--%>
<%!
    private Integer id;
    private String name;
    private static Map<String,Object> map;
%>
<%--2、声明 static 静态代码块--%>
<%!
    static {
        map = new HashMap<String,Object>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
    }
%>

<%--表达式脚本演示--%>
<%=12 %> <br>
<%=12.12 %> <br>
<%="我是字符串" %> <br>
<%=map%> <br>
<%=request.getParameter("username")%>


<%--代码脚本演示--%>
<%--1.代码脚本if语句，需要拼接，非常繁琐，建议使用EL--%>
<%
    int i = 13 ;
    if (i == 12) {
%>
<h1>代码脚本可以由多个代码脚本块组合完成一个完整的java语句</h1>
<%
} else {
%>
<h1>其实就是根据代码脚本可以由多个代码脚本块组合完成一个完整的java语句，将需要显示在页面上的内容露出来罢了！</h1>
<%
    }
%>
<br>
<%--2.代码脚本----for 循环语句--%>
<table border="1" cellspacing="0">
    <%
        for (int j = 0; j < 10; j++) {
    %>
    <tr>
        <td>第 <%=j + 1%> 行</td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>