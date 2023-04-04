package servlet;

import com.sun.deploy.net.URLEncoder;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 【文件下载流程】
 * （1）读取要下载的文件内容
 *   ServletContext.getResourceAsStream(./web工程路径/文件名);
 * （2）通过响应头告知客户端返回的数据类型
 *   String mimeType = servletContext.getMimeType(./web工程路径/文件名);           # 获取文件类型
 *   resp.setContentType(mimeType);
 * （3）通过响应头告知客户端收到的数据是用于下载使用(如果不设置，客户端会直接显示在浏览器上)
 *   resp.setHeader("Content-Disposition", "attachment; fileName=中文会乱码.png");        # fileNmae指定下载的文件名，可以和原来的文件名不同
 * （4）把下载的内容回传给客户端
 *   ServletOutputStream outputStream = resp.getOutputStream();
 *
 * 【下载文件名乱码解决方案】
 *  如果我们要下载的文件是中文名的话，下载时无法正确显示！原因是在响应头中，不能包含有中文字符，只能包含ASCII码。
 *（1）URL编码解决IE和谷歌浏览器的附件中文名乱码问题。
 *  我们只需要使用URLEncoder类先对中文名进行UTF-8编码在传给客户端即可。
 *   因为IE和谷歌浏览器收到含有编码后的字符串后会自动以UTF-8字符集进行解码显示。
 *  resp.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode("中文附件名", "UTF-8"));
 *（2）BASE64 编解码解决火狐浏览器的附件中文名乱码问题（因为不用火狐，以后用到再说把）
 *  因为火狐浏览器收到含有编码后的字符串后会自动以BASE64进行解码显示。
 *  response.setHeader("Content-Disposition",  "attachment; fileName=" + "=?utf-8?B?" + new BASE64Encoder().encode("中文附件名".getBytes("utf-8")));
 *（3）我们只需要通过判断请求头中的User-Agent参数即可判断出是什么浏览器
 *  String fire = request.getHeader("User-Agent");
 *  if (fire.contains("Firefox"))                                                # true即为火狐浏览器，否则使用utf-8编码
 @author Alex
 @create 2023-02-02-18:41
 */
public class US10 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、获取要下载的文件名
        String downloadFileName = "中文会乱码.png";
        // 2、读取要下载的文件内容
        ServletContext servletContext = getServletContext();
        InputStream resourceAsStream = servletContext.getResourceAsStream("/servlet/" + downloadFileName);  // web编程中，/ 始终对应web-app目录
        // 3、通过响应头告知客户端返回的数据类型
        String mimeType = servletContext.getMimeType("/servlet/" + downloadFileName);
        resp.setContentType(mimeType);
        // 4、通过响应头告知客户端收到的数据是用于下载使用(如果不设置，客户端会直接显示在浏览器上)
        resp.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(downloadFileName, "UTF-8"));
        // 5、把下载的内容回传给客户端
        ServletOutputStream outputStream = resp.getOutputStream();
        IOUtils.copy(resourceAsStream,outputStream);  // 这里实际上就是使用流把inputstream中的数据读取到outputsteam中，只不过使用了别人写的jar包，我们自己也能做
    }
}
