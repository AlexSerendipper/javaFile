<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/2/2
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="jsp.Student" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <style>
    table{
      border: 1px blue solid;
      width: 600px;
      border-collapse: collapse;
    }
    td,th{
      border: 1px blue solid;
    }
  </style>
</head>
<body>
<%@ include file="/jsp/include.jsp"%>
<p>----------------------------------------------------------------------------<p>
<%--jsp 输出一个表格，里面有 10 个学生信息。--%>
<%
List<Student> studentList = (List<Student>) request.getAttribute("stuList");
%>
<table>
  <tr>
    <td>编号</td>
    <td>姓名</td>
    <td>年龄</td>
    <td>电话</td>
    <td>操作</td>
  </tr>
  <% for (Student student : studentList) { %>
  <tr>
    <td><%=student.getId()%></td>
    <td><%=student.getName()%></td>
    <td><%=student.getAge()%></td>
    <td><%=student.getPhone()%></td>
    <td>删除、修改</td>
  </tr>
  <% } %>
</table>
</body>
</html>
