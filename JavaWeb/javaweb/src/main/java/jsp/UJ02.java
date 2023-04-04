package jsp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 【jsp的常用标签】见UJ02.jsp
 * 【jsp静态包含标签】
 *  <%@ include file="/include/footer.jsp"%>               # file 属性指定你要包含的jsp页面的路径（web路径）
 *  被包含的jsp页面不会被翻译java文件，仅仅是将其拷贝到包含的位置执行输出。
 *  当修改footer.jsp的代码，所有引用位置全部被修改
 *
 * 【jsp动态包含标签】
 *  <jsp:include page=""></jsp:include>                   # page属性指定你要包含的jsp页面的路径（web路径）
 *  动态包含会把包含的 jsp 页面也翻译成为 java 代码
 *  动态包含，还可以传递参数。如：
 *   <jsp:include page="/include/footer.jsp">
 *       <jsp:param name="username" value="bbj"/>
 *       <jsp:param name="password" value="root"/>
 *   </jsp:include>
 *   传递的参数可以在引用它的页面被获取
 *       <%=request.getParameter("username")%>
 *
 * 【jsp请求转发标签】
 *  <jsp:forward page="/scope2.jsp"></jsp:forward>       # page属性指定请求转发的路径
 @author Alex
 @create 2023-02-01-22:49
 */

// /student
public class UJ02 extends HttpServlet {
    // 请求转发的实用演示:体会动态生成HTML页面!
    // 要求：利用jsp输出一个表格，里面有10个学生信息。
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数
        // 发sql语句查询学生的信息（暂使用for循环直接生成，作为查询到的结果）
        List<Student> studentList = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            int t = i + 1;
            studentList.add(new Student(t,"name"+t, 18+t,"phone"+t));
        }
        // 保存查询到的结果（学生信息）到request域中（一次请求内有效）
        req.setAttribute("stuList", studentList);
        // 请求转发到 showStudent.jsp 页面
        req.getRequestDispatcher("/jsp/UJ02.jsp").forward(req,resp);
    }
}

