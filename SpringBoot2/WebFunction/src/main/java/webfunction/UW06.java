package webfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import webfunction.pojo.Person;

/**
 * 【文件上传功能】与springMVC并无区别
 *  web-starter中已经配置好了文件上传解析器等相关配置
 *    控制器方法形参处，用MultipartFile类型，接收上传的数据file1
 *    file1.transferTo(new File("./src"));                            # 进行文件上传,上传到artifact路径
 *    file1.getOriginalFilename()                                     # 获取上传的文件的文件名
 *    file1.getName()                                                 #
 *  文件上传源码分析, 需要的时候再学把p50
 *
 * 【文件下载功能】与springMVC并无区别
 *
 @author Alex
 @create 2023-03-23-11:03
 */

@Controller
public class UW06 {
    @GetMapping("/")
    public String hello(){
        return "index";
    }

    // 多文件上传要写成数组形式MultipartFile[]
    @PostMapping("/upload")
    public String upload(Person person,MultipartFile avatar,MultipartFile[] photos){
        System.out.println(person);
        System.out.println("上传的头像的大小" + avatar.getSize());
        System.out.println("上传了几个文件" + photos.length);
        return "success";
    }

}
