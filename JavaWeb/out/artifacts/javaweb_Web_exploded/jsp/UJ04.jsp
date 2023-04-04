<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="jsp.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/2/2
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- JSTL中一些常用标签使用示范--%>
<%-- -----------------------SET标签-------------------------------------- --%>
    保存之前：${ sessionScope.abc } <br>
    <c:set scope="session" var="abc" value="abcValue"/>
    保存之后：${ sessionScope.abc } <br>
<%-- -----------------------IF标签-------------------------------------- --%>
    <c:if test="${ 12 == 12 }">
              <h1>12 等于 12</h1>
    </c:if>
<%-- ---------------<c:choose> <c:when> <c:otherwise>标签----------------- --%>
<%
    request.setAttribute("height", 169);
%>
<c:choose>
    <%-- 这是 html 注释 --%>
    <c:when test="${ requestScope.height > 190 }">
        <h2>小巨人</h2>
    </c:when>
    <c:when test="${ requestScope.height > 180 }">
        <h2>很高</h2>
    </c:when>
    <c:when test="${ requestScope.height > 170 }">
        <h2>还可以</h2>
    </c:when>
    <c:otherwise>
        <h2>残疾人</h2>
    </c:otherwise>
</c:choose>
<%-- ------------------------<c:forEach>标签1：遍历数字------------------------- --%>
<table border="1">
    <c:forEach begin="1" end="10" step = "2" var="i">
        <tr>
            <td>第${i}行</td>
        </tr>
    </c:forEach>
</table>
<%-- -----------------------<c:forEach>标签2：遍历对象------------------------- --%>
<%
    request.setAttribute("arr", new String[]{"18610541354","18688886666","18699998888"});
%>
<c:forEach items="${ requestScope.arr }" var="item">
    ${ item } <br>
</c:forEach>
<%-- -----------------------<c:forEach>标签3：遍历map------------------------- --%>
<%
    Map<String,Object> map = new HashMap<String, Object>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
// for ( Map.Entry<String,Object> entry : map.entrySet()) {
// }
    request.setAttribute("map", map);
%>
<c:forEach items="${ requestScope.map }" var="entry">
    <h1>${entry.key} = ${entry.value}</h1>
</c:forEach>
<%-- -----------------------<c:forEach>标签4：List[]<Student>------------------------- --%>
<%
    List<Student> studentList = new ArrayList<Student>();
    for (int i = 1; i <= 10; i++) {
        studentList.add(new Student(i,"username"+i ,18+i,"phone"+i));
    }
    request.setAttribute("stus", studentList);
%>
<table>
    <tr>
        <th>编号</th>
        <th>用户名</th>
        <th>年龄</th>
        <th>电话</th>
        <th>操作</th>
    </tr>
    <c:forEach begin="2" end="7" step="2" varStatus="status" items="${requestScope.stus}" var="stu">
        <tr>
            <td>${stu.id}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.phone}</td>
            <td>${status.step}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
