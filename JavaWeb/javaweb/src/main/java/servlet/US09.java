package servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 【文件的上传步骤】
 *（1）要有一个 form 标签，method=post 请求
 *（2）✔✔form 标签的 encType 属性值必须为 multipart/form-data 值（表示提交的所有表单项 以多段的形式进行拼接，然后以二进制流的形式发送给服务器）
 *（3）在 form 标签中使用 input type=file 添加上传的文件
 *（4）编写服务器代码（Servlet程序）接收，处理上传的数据。
 *
 * 【解析上传的内容】
 *  上传的内容 都是 以二进制 流 的形式发送给服务器✔，我们自己处理起来比较麻烦。故采用第三方包来进行解析
 *  需要导入commons-fileupload-1.2.1.jar 和 commons-io-1.4.jar 两个包
 *
 * 【ServletFileUpload】
 *  主要用到包中的ServletFileUpload类来解析数据。
 *  FileItem类表示每一个表单项。
 *  boolean ServletFileUpload.isMultipartContent(HttpServletRequest request);          # ✔判断当前上传的数据格式是否是多段的格式。
 *  public List<FileItem> parseRequest(HttpServletRequest request)                     # ✔解析上传的数据
 *  boolean FileItem.isFormField()                     # ✔判断当前这个表单项，是否是普通的表单项。还是上传的文件类型。true表示普通类型的表单项。false 表示上传的文件类型
 *  String FileItem.getFieldName()                     # 获取表单项的 name 属性值
 *  String FileItem.getString("UTF-8")                 # 获取普通表单项的值（输入UTF-8表示使用该字符集解析，避免乱码）
 *  String FileItem.getName();                         # 上传的文件名
 *  void FileItem.write( file );                       # 上传的文件写到参数file所指向抽硬盘位置 。
 *
 @author Alex
 @create 2023-02-02-16:41
 */

// /upload
public class US09 extends HttpServlet {
    /**
     * 用来处理上传过来的数据，如果不用第三方包，那些普通的表单项会乱码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("文件上传过来拉~");
//        ServletInputStream is = req.getInputStream();
//        byte[] buffer = new byte[200];
//        int len = is.read(buffer);
//        System.out.println("不要第三方包，直接读取数据如下所示");
//        System.out.println(new String(buffer,0,len));
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 0、发现文件名乱码啦。设置一下字符集
        req.setCharacterEncoding("UTF-8");
        // 1、先判断上传的数据是否多段数据（只有是多段的数据，才能知道这个表单中，有表单项是上传的文件）
        if (ServletFileUpload.isMultipartContent(req)) {
            // 2、创建 FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 3、创建用于解析上传数据的工具类 ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
//            servletFileUpload.setHeaderEncoding("UTF-8");
            try {
                // 4、解析上传的数据，得到每一个表单项 FileItem
                List<FileItem> list = servletFileUpload.parseRequest(req);
                // 5、循环判断，每一个表单项，是普通类型，还是上传的文件
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        // 普通表单项
                        System.out.println("表单项的 name 属性值：" + fileItem.getFieldName());
                        // 获取当前表单项的值（输入UTF-8表示使用该字符集解析，避免乱码）
                        System.out.println("表单项的 value 属性值：" + fileItem.getString("UTF-8"));
                    } else {
                        // 上传的文件
                        System.out.println("表单项的 name 属性值：" + fileItem.getFieldName());
                        System.out.println("上传的文件名：" + fileItem.getName());
                        // 6、也只能将上传的表单项写出
                        fileItem.write(new File("D:\\IdeaWorkspace\\JavaWeb\\javaweb\\src\\main\\java\\servlet\\" + fileItem.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
