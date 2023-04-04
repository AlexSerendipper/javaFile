<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%--2023/2/20复习了一下，希望下次看的时候，把每个功能的跳转路线整理一下，就是从哪个jsp页面跳到哪个servlet程序--%>
    <meta charset="UTF-8">
    <title>书城首页</title>
    <%--静态包含base标签、css、jquery文件--%>
    <%@ include file="/pages/common/head.jsp" %>
    <script type="text/javascript">
        $(function () {
            // 加入购物车请求的传统实现（servlet程序过去后再重定向回来）
            // $("button.addToCart").click(function () {
            //     /**
            //      * 在事件响应的 function 函数 中，有一个 this 对象，这个 this 对象，是当前正在响应事件的 dom 对象
            //      * @type {jQuery}
            //      */
            //     var bookId = $(this).attr("bookId");
            //     location.href = "http://localhost:8080/project_book/cartServlet?action=addItem&id=" + bookId;
            // });

            // 加入购物车请求的ajax实现
            $("button.addToCart").click(function () {
                var bookId = $(this).attr("bookId");
                // location.href = "http://localhost:8080/book/cartServlet?action=addItem&id=" + bookId;
                // 发 ajax 请求，添加商品到购物车
                // 因为是直接修改内部的文字，所以两种购物车是否有数据的两种情况都要加Id
                $.getJSON("http://localhost:8080/project_book/cartServlet",
                         "action=ajaxAddItem&id=" + bookId,
                         function (data) {$("#cartTotalCount").text("您的购物车中有 " + data.totalCount + " 件商品");
                                          $("#cartLastName").text(data.lastName);
                })
            })
        })
    </script>
</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">网上书城</span>
    <div>
        <%--根据用户是否登录显示不同内容--%>
        <c:if test="${empty sessionScope.user}">
            <a href="pages/user/login.jsp">登录</a> |
            <a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
            <a href="pages/cart/cart.jsp">购物车</a>
            <a href="pages/manager/manager.jsp">后台管理</a>
        </c:if>

        <c:if test="${not empty sessionScope.user}">
            <span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
            <a href="pages/order/order.jsp">我的订单</a>
            <a href="userServlet?action=logout">注销</a>&nbsp;&nbsp;
            <%-- index ==> cart.jsp ==> --%>
            <a href="pages/cart/cart.jsp">购物车</a>
            <a href="pages/manager/manager.jsp">后台管理</a>
        </c:if>

    </div>
</div>
<div id="main">
    <div id="book">
        <div class="book_cond">
            <%--  分页：index.jsp ==> ClientBookServlet?pageByPrice  --%>
            <form action="client/bookServlet" method="post">
                <input type="hidden" name="action" value="pageByPrice">
                价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
                <input id="max" type="text" name="max" value="${param.max}"> 元
                <input type="submit" value="查询"/>
            </form>
        </div>
        <div style="text-align: center">
            <%--购物车为空的输出--%>
            <c:if test="${empty sessionScope.cart.items}">
                <span id="cartTotalCount"> </span>
                <div>
                    <span style="color: red" id="cartLastName">当前购物车为空</span>
                </div>
            </c:if>
            <%--购物车非空的输出--%>
            <c:if test="${not empty sessionScope.cart.items}">
                <span id="cartTotalCount">您的购物车中有 ${sessionScope.cart.totalCount} 件商品</span>
                <div>
                    您刚刚将<span style="color: red" id="cartLastName">${sessionScope.lastName}</span>加入到了购物车中
                </div>
            </c:if>
        </div>
        <%--首页需要遍历的数据--%>
        <c:forEach items="${requestScope.page.items}" var="book">
        <div class="b_list">
            <div class="img_div">
                <img class="book_img" alt="" src="${book.imgPath}"/>
            </div>
            <div class="book_info">
                <div class="book_name">
                    <span class="sp1">书名:</span>
                    <span class="sp2">${book.name}</span>
                </div>
                <div class="book_author">
                    <span class="sp1">作者:</span>
                    <span class="sp2">${book.author}</span>
                </div>
                <div class="book_price">
                    <span class="sp1">价格:</span>
                    <span class="sp2">￥${book.price}</span>
                </div>
                <div class="book_sales">
                    <span class="sp1">销量:</span>
                    <span class="sp2">${book.sales}</span>
                </div>
                <div class="book_amount">
                    <span class="sp1">库存:</span>
                    <span class="sp2">${book.stock}</span>
                </div>
                <div class="book_add">
                    <%--添加一个自定义属性bookId--%>
                    <%--index.jsp ==> cartservlet?action=addToCart--%>
                    <button bookId="${book.id}" class="addToCart">加入购物车</button>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>

    <%--分页条的开始--%>
    <%@include file="/pages/common/page_nav.jsp"%>
    <%--分页条的结束--%>
</div>

<%--静态包含页脚--%>
<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>