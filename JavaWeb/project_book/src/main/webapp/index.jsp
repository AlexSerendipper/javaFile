<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--只负责请求转发--%>
<%--  分页：index.jsp ==> ClientBookServlet(得到page对象，以及url，以及转发到client目录下的index)  --%>
<jsp:forward page="/client/bookServlet?action=page"></jsp:forward>