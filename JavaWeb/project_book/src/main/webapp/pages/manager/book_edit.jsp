<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑图书</title>
    <%--静态包含base标签、css、jquery文件--%>
    <%@ include file="/pages/common/head.jsp" %>
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }

        input {
            text-align: center;
        }
    </style>
</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif">
    <span class="wel_word">编辑图书</span>
    <%--静态包含图书管理信息--%>
    <%@ include file="/pages/common/manager_menu.jsp" %>
</div>

<div id="main">
    <%--注意之前使用了base标签，manager前不要加/--%>
    <%--或者使用?action=add--%>
    <form action="manager/bookServlet" method="post">
        <%--由于添加图书是不需要传入图书的id属  --%>
        <%--所以当前页面请求时，没有传入id, 就实现添加操作，传入id时，就实现修改图书操作--%>
        <%--所以当前页面book_edit.jsp即可以实现添加操作，又可以修改图书操作--%>
        <input type="hidden" name="pageNo" value="${param.pageNo}">
        <input type="hidden" name="action" value="${empty param.id ? "add" : "update"}">
        <input type="hidden" name="id" value="${param.id}">
        <table>
            <tr>
                <td>名称</td>
                <td>价格</td>
                <td>作者</td>
                <td>销量</td>
                <td>库存</td>
                <td colspan="2">操作</td>
            </tr>
            <tr>
                <td><input name="name" type="text" value="${requestScope.book.name}"/></td>
                <td><input name="price" type="text" value="${requestScope.book.price}"/></td>
                <td><input name="author" type="text" value="${requestScope.book.author}"/></td>
                <td><input name="sales" type="text" value="${requestScope.book.sales}"/></td>
                <td><input name="stock" type="text" value="${requestScope.book.stock}"/></td>
                <td><input type="submit" value="提交"/></td>
            </tr>
        </table>
    </form>


</div>

<%--静态包含页脚--%>
<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>