<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <%--静态包含base标签、css、jquery文件--%>
    <%@ include file="/pages/common/head.jsp" %>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif">
    <span class="wel_word">图书管理系统</span>
    <%--静态包含图书管理信息--%>
    <%@ include file="/pages/common/manager_menu.jsp" %>
</div>

<div id="main">
    <table>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td colspan="2">操作</td>
        </tr>

        <c:forEach items="${requestScope.page.items}" var="book">
        <tr>
            <td>${book.name}</td>
            <td>${book.price}</td>
            <td>${book.author}</td>
            <td>${book.sales}</td>
            <td>${book.stock}</td>
            <%--修改图书 ==> getbook方法 ==> book_edit.jsp ==> update方法--%>
            <td><a href="manager/bookServlet?action=getBook&id=${book.id}&pageNo=${requestScope.page.pageNo}">修改</a></td>
            <td><a href="manager/bookServlet?action=delete&id=${book.id}&pageNo=${requestScope.page.pageTotal}" class="deleteClass">删除</a></td>
        </tr>
        </c:forEach>

        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <%--这个pageNo表示当前页，指向添加图书后，页面要停留在哪一页，添加后肯定是停留在最后一页，故pageNo = pageTotal--%>
            <td><a href="pages/manager/book_edit.jsp?pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
        </tr>
    </table>

    <%--分页条的开始--%>
    <%--  manager_menu.jsp ==> bookservlet ==> book_manager.jsp --%>
    <%@include file="/pages/common/page_nav.jsp"%>
    <%--分页条的结束--%>
</div>

<%--静态包含页脚--%>
<%@ include file="/pages/common/footer.jsp" %>
</body>
<script type="text/javascript">
    $(function () {
        // 给删除的 a 标签绑定单击事件，用于删除的确认提示操作
        $("a.deleteClass").click(function () {
        // 在事件的 function 函数中，有一个 this 对象。这个 this 对象，是当前正在响应事件的 dom 对象。
            /**
             * confirm 是确认提示框函数
             * 参数是它的提示内容
             * 它有两个按钮，一个确认，一个是取消。
             * 返回 true 表示点击了，确认，返回 false 表示点击取消。
             */
            return confirm("你确定要删除【" + $(this).parent().parent().find("td:first").text() + "】?");
        // return false// 阻止元素的默认行为===不提交请求
        });
    });
</script>
</html>