package webfunction.webcomponent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 @author Alex
 @create 2023-03-23-13:10
 */
@WebServlet(urlPatterns = "/servlet")
public class UW08_Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("访问到UW08_Servlet啦");
    }
}
