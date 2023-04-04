package servlet;

/**
 * 【http协议】
 *  客户端和服务器之间通信时，发送的数据，需要遵守的规则，叫HTTP协议。HTTP协议中的数据又叫报文
 *
 * 【常见的GET、POST请求】
 *  由于GET和POST请求都会触发service方法，需要分别进行处理
 *    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;     # 类型转换，只有HttpServletRequest有 getMethod()方法
 *    String method = httpServletRequest.getMethod();                                  # 获取请求的方式
 *  常见的GET、POST请求
 *（1）GET请求：
 *    0、在浏览器地址栏中输入地址后敲回车✔
 *    1、form 标签 method=get
 *    2、a标签
 *    3、link标签引入 css
 *    4、Script标签引入 js 文件
 *    5、img标签引入图片
 *    6、iframe引入 html 页面
 *（2）POST请求：
 *    8、form 标签 method=post
 *    9、Ajax发送post请求
 *
 * 【HTTP协议的请求格式】图见xmind。
 *  get请求
 *    (1)请求行
 *       请求的方式 GET
 *       请求的资源路径[+?+请求参数]
 *       请求的协议的版本号 HTTP/1.1
 *    (2)请求头
 *       key : value 组成 不同的键值对，表示不同的含义
 *  post请求
 *  （1）请求行
 *       请求的方式 POST
 *       请求的资源路径[+?+请求参数]
 *       请求的协议的版本号 HTTP/1.1
 *  （2）请求头
 *       key : value 不同的请求头，有不同的含义
 *  （3）空行
 *  （4）请求体：即发送给服务器的数据
 *
 * 【HTTP协议的响应格式】见xmind
 * (1)响应行
 *     响应的协议和版本号
 *     响应状态码
 *     响应状态描述符
 * (2)响应头
 *     key : value 不同的响应头，有其不同含义
 * (3)空行
 * (4)响应体:即回传给客户端的数据
 *
 * 【get请求和post请求的区别】
 *  get请求没有请求体，将内容以键值对的形式拼接在url上传递给服务器
 *   post请求有请求体，内容以键值对的形式写在请求体中传递给服务器
 *  get请求的请求长度一般是有限制的
 *   post请求的请求长度一般没有限制的
 *  故（1）post请求相对于get请求更安全
 *      (2)上传文件只能用post请求（不可能把文件拼接在url上）
 *
 * 【✔常用的请求头】
 *  HTTP协议中有一个请求头，叫Referer，它可以把请求发起时浏览器地址栏中的地址发送给服务器
 *
 * 【常用的响应码】
 *  200 表示请求成功
 *  302 表示请求重定向（明天讲）
 *  404 表示请求服务器已经收到了，但是你要的数据不存在（请求地址错误）
 *  500 表示服务器已经收到请求，但是服务器内部错误（代码错误）
 *
 * 【MIME】了解即可
 *  MIME是HTTP协议中数据类型。有时候我们需要准确的告知浏览器我们传入的数据类型，放置浏览器曲解
 *  MIME 类型的格式是“大类型/小类型”
 *  常见MIME类型：
 *         文件                                              MIME类型
 *         超文本标记语言文本 .html , .htm                    text/html
 *         普通文本 .txt                                     text/plain
 *         RTF 文本 .rtf                                     application/rtf
 *         GIF 图形 .gif                                     image/gif
 *         JPEG 图形 .jpeg,.jpg                              image/jpeg
 *         au 声音文件 .au                                   audio/basic
 *         MIDI 音乐文件 mid,.midi                           audio/midi,audio/x-midi
 *         RealAudio 音乐文件 .ra, .ram                      audio/x-pn-realaudio
 *         MPEG 文件 .mpg,.mpeg                              video/mpeg
 *         AVI 文件 .avi                                     video/x-msvideo
 *         GZIP 文件 .gz                                     application/x-gzip
 *         TAR 文件 .tar                                     application/x-tar
 *
 * 【谷歌浏览器查看HTTP协议】
 *  F12 ==> network ==> ALL ==> 可以看见浏览器显示时把请求头和请求体分开拉~
 @author Alex
 @create 2023-01-29-10:08
 */
public class US03 {
}
