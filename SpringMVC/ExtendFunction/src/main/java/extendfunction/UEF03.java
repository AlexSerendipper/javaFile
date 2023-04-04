package extendfunction;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 【文件的下载】ResponseEntity
 * （1）获取响应体
 * （2）设置响应头
 *     MultiValueMap<String, String> headers = new HttpHeaders();
 *     headers.add("Content-Disposition", "attachment;filename=1.jpg");
 * （3）设置响应报文
 *      HttpStatus statusCode = HttpStatus.OK;
 * （4）设置返回值类型ResponseEntity
 *     ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers,statusCode);
 *
 *
 * 【文件的上传】MultipartFile
 *  文件上传要求form表单的请求方式必须为post，并且添加属性enctype="multipart/form-data"
 *  SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息
 * （1）添加依赖
----------------------------------
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.1</version>
</dependency>
------------------------------------
 * （2）在SpringMVC的配置文件中添加配置
------------------------------------
<!--配置文件上传解析器，才能将文件转换为MultipartFile对象-->
<!--必须设置id，该bean是通过id完成自动注入的-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"> </bean>
------------------------------------
 * （3）控制器方法
 *     控制器方法形参处，用MultipartFile类型，接收上传的数据file1
 *     file1.transferTo(new File("./src"));                            # 进行文件上传,上传到artifact路径
 *     file1.getOriginalFilename()                                     # 获取上传的文件的文件名
 *     file1.getName()                                                 # 获取上传的表单元素的name属性

 @author Alex
 @create 2023-03-07-16:07
 */
@Controller
public class UEF03 {

    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        // 获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        // 获取文件在web中的真实路径（即artifact路径）
        String realPath = servletContext.getRealPath("/static/img/pp.png");
        // 创建输入流
        InputStream is = new FileInputStream(realPath);
        // 创建字节数组
        // 其中is.available()对应了输入流长度，如当前图片对应长度为100，则is.available()=100...文件很大不建议这么用
        byte[] bytes = new byte[is.available()];
        // 将流读到字节数组中
        is.read(bytes);
        // 创建HttpHeaders对象设置响应头信息（map集合形式）
        MultiValueMap<String, String> headers = new HttpHeaders();
        // 设置要下载方式以及下载文件的名字
        // 格式固定，键为 Content-Disposition，值为 attachment;filename=xxx
        // attachment表示以附件的形式下载文件，filename为默认的下载名
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        // 设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        // 创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        // 关闭输入流
        is.close();
        return responseEntity;
    }


    @RequestMapping("/upload")
    public String testUpload(MultipartFile photo, HttpSession session) throws IOException {
        // 获取上传的表单元素的name属性
        String name = photo.getName();
        // 获取上传的文件的文件名
        String fileName = photo.getOriginalFilename();
        // 处理上传文件重名的问题（通过lastindexof获取上传文件的后缀，UUID为32为随机字符）
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + hzName;
        // 获取 服务器中 存放图片的路径（实际上，上传的文件是存放在target目录中）
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("./static/img");
        // 如果目录不存在，创建目录
        if (!new File(photoPath).exists()) {
            new File(photoPath).mkdir();
        }
        // 实现上传功能
        photo.transferTo(new File(photoPath + File.separator + fileName));
        return "success";
    }
}