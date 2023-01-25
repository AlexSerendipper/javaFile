package usenetprogram;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *【URI、URL和URN的区别】
 * URI，是uniform resource identifier，统一资源标识符，用来唯一的标识一个资源。
 * 而URL是uniform resource locator，统一资源定位符，它是一种具体的URI，即URL可以用来标识一个资源，而且还指明了如何locate这个资源。
 * 而URN，uniform resource name，统一资源命名，是通过名字来标识资源，比如mailto:java-net@java.sun.com。
 * 也就是说，URI是以一种抽象的，高层次概念定义统一资源标识，而URL和URN则是具体的资源标识的方式。
 * URL和URN都是一种URI。在Java的URI中，一个URI实例可以代表绝对的，也可以是相对的，只要它符
 * 合URI的语法规则。而URL类则不仅符合语义，还包含了定位该资源的信息，因此它不能是相对的。
 *
 * 【URL编程概述】
 *  URL(Uniform Resource Locator)：统一资源定位符，它表示 Internet上某一资源的地址。
 *  URL就是“种子”，通过它定位网络上某处的资源
 *  通过 URL 我们可以访问 Internet 上的各种网络资源，比如最常见的 www，ftp站点。浏览器通过解析给定的 URL 可以在网络上查找相应的文件或其他资源。
 *  URL的基本结构由5部分组成：
 *     <传输协议>://<主机名>:<端口号>/<文件名>#片段名?参数列表
 *  例如:http://192.168.1.100:8080/helloworld/index.jsp#a?username=shkstart&password=123
 *  #片段名：即锚点，例如看小说，直接定位到章节
 *  参数列表格式：参数名=参数值&参数名=参数值....
 *
 * 【URL编程常用方法】
 *  public URL (String spec)：通过一个表示URL地址的字符串可以构造一个URL对象。例如：URL url = new URL("http://www. atguigu.com/");
 *  public String getProtocol(  )     获取该URL的协议名
 *  public String getHost(  )        获取该URL的主机名
 *  public String getPort(  )        获取该URL的端口号
 *  public String getPath(  )        获取该URL的文件路径
 *  public String getFile(  )         获取该URL的文件名
 *  public String getQuery(   )      获取该URL的查询名
 *
 * 【URLConnection】若希望从URL端接收/发送信息，需要与URL建立连接，此时需要使用URLConnection
 *  openConnection()：生成对应的URLConnection对象
 *  public InputStream getInputStream( )throws IOException
 *  public OutputSteram getOutputStream( )throws IOException
 *  public Object getContent( ) throws IOException
 *  public int getContentLength( )
 *  public String getContentType( )
 *  public long getDate( )
 *  public long getLastModified( )
 *
 @author Alex
 @create 2022-12-21-15:06
 */
public class UseNetProgram05 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://192.168.1.100:8080/helloworld/index.jsp?username=shkstart&password=123");
        System.out.println(url.getProtocol());
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getPath());
        System.out.println(url.getFile());
        System.out.println(url.getQuery());
    }

    @Test
    public void test() throws IOException {
        URL url = new URL("https://img2.woyaogexing.com/2021/08/29/e08b324dbe554519a69e79f44bc44d80.jpeg");
        // 得到连接，实际上获取的是http的连接
        URLConnection urlConnection = url.openConnection();
        // 与URL建立连接
        urlConnection.connect();
        // 得到输入输出流
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(new File("111.png"));
        byte[] b = new byte[20];
        int len;
        while((len= inputStream.read(b))!=-1){
            fileOutputStream.write(b,0,len);
        }
        // 关闭流
        fileOutputStream.close();
        inputStream.close();
    }
}
