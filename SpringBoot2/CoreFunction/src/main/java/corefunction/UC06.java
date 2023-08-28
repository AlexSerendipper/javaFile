package corefunction;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * 【常用开发工具】SSM均可使用！
 * 【Lombok】能够简化JavaBean开发
 * （1）引入依赖
----------------
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
----------------
 * （2）使用方式
 *  ✔@NoArgsConstructor                # 重写类中的无参构造器
 *  ✔@AllArgsConstructor               # 重写类中的带参构造器（带全部参数）（如果需要其他的构造器自己配置一下就好啦）
 *  ✔@Data                             # 重写类中的get和set方法和equals和hashcode和toString方法
 *
 *  @ToString                         # 重写类中的toString方法
 *  @EqualsAndHashCode                # 重写类中的equals和hashcode
 *  @Slf4j                            # 可以在类中使用log.info("")输出日志信息
 *
 * 【dev-tools】
 *  项目代码修改以后，按Ctrl+F9 可以将springboot重新启动.....如果没有修改，则不会重新启动
 *  静态页面页面以后，按Ctrl+F9 直接进行热部署，不用重新启动
------------------
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
-------------------
 *  【Spring Initailizr】项目初始化向导
 *  创建模块：创建时选择我们需要的开发场景 和 开发工具
 *  （注意选择springboot版本,如果版本不对可以先创建一个，然后手动降低版本）
 *  （注意创建完项目看一下 <java.version>8</java.version>）
 *  使用Spring Initailizr创建模块，将自动完成 依赖引入、项目结构创建、编写好主配置类
 *
 * 【commons-lands】
 *  StringUtils.isBlank()              # 判断是否为空值（包括null,""等）
 *  CharUtils.isAsciiAlphanumeric(c)   # 判断字符是否为普通字符（普通字符返回true,特殊字符返回false）
 *
 *  HtmlUtils.htmlEscape(json1)        # 将json字符串中的特殊符号进行转义，就是原先title中不能有标签，如<p>，会对页面造成破坏，所以要进行转义！！！（org.springframework.web.util.HtmlUtils）
 *  HtmlUtils.htmlUnescape(json1)      # 将json字符串中的特殊符号进行反转义（org.springframework.web.util.HtmlUtils）
--------------------
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.9</version>
</dependency>
--------------------
 *
 * 【UUID】用于生成随机字符串
 *  UUID.randomUUID().toString().replaceAll("-","");      # java自带工具，默认生成的字符串有横线，可以全部替换为空字符串
 *
 * 【MD5】加密算法，只能加密不能解密，但是每次加密都是同样的结果
 *  DigestUtils.md5DigestAsHex(key.getBytes());
 *  MD5是计算机广泛使用的哈希算法之一，MD5的固定长度为128bit，也就是16字节。通常用16进制字面值来输出他，也就是一个32位的字符串
 *   特点一：长度固定，无论输入多少字节，输出总是16字节
 *   特点二：不可逆，从结果无法反推原始数据
 *   特点三：高度离散性，输出的16字节，没有任何规律
 *   特点四：抗碰撞性，想找到两个不同数据，他们的MD5加密后数值完全一致，是非常困难的
 *   用途一：密码保护，好处是只有用户自己知道明文密码，即便密码库被盗，也无法反推出明文密码
 *   用途二：文件完整性校验，当传输大文件，可能因为网络因素导致文件传输不完整，使用MD5可以校验接收端和发送端 数据一致
 *   用途三：数字签名，比如发布了一个程序的同时附上MD5，这样可以防止用户下载时的文件与发布的不同，有效防止木马被植入
 *   用途四：文件秒传，网盘的文件秒传功能并不是真的把文件上传了，只需要在自己的数据库中搜索一下待上传文件的MD5，如果存在，就无须上传，从而实现文件秒传
 *  基本原理：
 *  （1）补位：原始长度为N，使长度变为N*512+448 bit（即补到总长度除512=448），不论原始数据的长度，都需要进行补位（就算总长度刚好满足N*512+448，也需要再补512 bit）
 *  （2）记录原始长度：然后用64位来记录原始数据的长度。。此时数据总长度为N*512 + 448 + 64, 是512（64字节）的整数倍
 *  （3）标准幻数：准备4个标准幻数，每个幻数都是4个字节，共16个字节
 *               四个幻数的值实际上就是32个16进制的数依次排列，最终的MD5结果，实际上就是这四个标准幻数经过多轮哈希运算之后的值
 *  （4）运算：原始数据被我们处理为 M*512 bit的长度，我们每一次只计算其中一份64字节，每一份64字节又被分成了16小份（每一份4个字节）
 *            这里很复杂，简单说来就是定义函数（实际上有多个函数），对这四个幻数A B C D进行处理，如 ff(A,B,C,D,4字节)，每代入一个四字节数，函数都会改变四个幻数的值，最终四个幻数的值变得和原来完全不一样，此为一轮
 *            一轮后的四个幻数，作为下一轮的标准幻数进行下一轮的处理。
 *            最终的四个幻数，利用16进制显示出来，就是最终的MD5结果。
 *
 *
 * 【wkhtmltopdf】https://wkhtmltopdf.org/downloads.html 处下载。
 *  打印网页为pdf。但是该工具无法用于打印百度文库等有权限的网址。
 *  使用该工具，必须得 提前建好文件存放目录！
 *  pc端运行：使用bin目录下的命令，输入 wkhtmltoimage --quality 75 http://www.nowcoder.com d:/data/wk-pdf/2.png                      # 其中--quality 75表示把原图片按照75%压缩保存
 *  java端运行： String cmd = "d:/wkhtmltopdf/bin/wkhtmltoimage --quality 75 http://www.nowcoder.com d:/data/wk-pdf/1.png"          #
 *                Runtime.getRuntime().exec(cmd);
 *
 * 【POI】 Apache POI是用来实现存取 Microsoft Office文档的功能
------------------
 <!--poi_excel表引入-->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>3.15</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>3.15</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml-schemas</artifactId>
    <version>3.15</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-scratchpad</artifactId>
    <version>3.15</version>
</dependency>
<dependency>
    <groupId>org.apache.xmlbeans</groupId>
    <artifactId>xmlbeans</artifactId>
    <version>2.3.0</version>
</dependency>
------------------
 *  HSSF提供读写Microsoft Excel XLS格式档案的功能。
 *  XSSF提供读写Microsoft Excel OOXML XLSX格式档案的功能。
 *  HWPF提供读写Microsoft Word DOC格式档案的功能。
 *  HSLF提供读写Microsoft PowerPoint格式档案的功能。
 * （1）使用POI读取xlsx文件流程如下
 *
 @author Alex
 @create 2023-03-19-13:03
 */


public class UC06{
    public static void main(String[] args) throws Exception{
        String filePath="D:\\桌面存放\\test.xlsx";  // 文件路径
        File file = new File(filePath);

        // 以下流程需要使用try catch包围
        // 1、创建文件的输入流
        FileInputStream stream=new FileInputStream(file);
        // 2、创建工作簿
        XSSFWorkbook workbook=new XSSFWorkbook(stream);
        // 3、获取一个工作表！，下标从0开始
        XSSFSheet sheet=workbook.getSheetAt(0);
        // 4、循环读取数据
        System.out.println(sheet.getLastRowNum());
        for (int i=0;i<=sheet.getLastRowNum();i++){ //getLastRowNum 获取最后一行行标，首行为0
            // 获取行
            XSSFRow row=sheet.getRow(i);
            // 读取每一列的数据
            String cell1 = row.getCell(0).getStringCellValue();
            double cell2=row.getCell(1).getNumericCellValue();
            double cell3 = row.getCell(2).getNumericCellValue();
            System.out.println(cell1);
            System.out.println(cell2);
            System.out.println(cell3);
        }
        stream.close();
    }
}
