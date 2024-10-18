package tomcat;

/**
 * 【JavaWeb】java技术概览见xmind(同jdbc)
 *  web 资源按实现的技术和呈现的效果的不同，又分为静态资源和动态资源两种。
 *     静态资源： html、css、js、txt、mp4 视频 , jpg 图片
 *     动态资源： jsp页面、Servlet程序（动态资源只能通过服务器访问）
 *  浏览器端发送的数据，首先就被服务器端（tomcat）的servlet组件接收。servlet负责获取请求、处理请求、响应请求
 *  浏览器端和服务器端需要遵守相应的协议，这个协议就是http协议
 *  jsp: java server page, servlet并不适合响应请求，通过jsp可以实现动态响应
 *   为了更好的写jsp，引入了EL和JSTL
 *  本质上当服务器端接收到两个请求，服务器端是无法分辨这两个请求是否来自同一个浏览器端。为了解决这个问题，引入了session和cookie
 *  jdbc主要负责的是服务器端与数据库进行交互
 *  servlet、filter、listener构成了tomcat的三大核心组件。filter用于过滤数据，listener监听用户操作
 *  ajax实现了浏览器端和服务器端的异步传输数据（通过json和xml格式）
 *（我们写的html页面，在浏览器输入http://ip:port/工程名/xxx.html，实际上就是向tomcat服务器发起get请求，然后服务器通过响应体的形式将我们的页面响应回来，显示在客户端，也就是浏览器上）
 *
 * 【tomcat概述】
 *  Tomcat由 Apache 组织提供的一种Web服务器，提供对jsp和Servlet的支持。它是一种轻量级的javaWeb容器（服务器），也是当前应用最广的JavaWeb 服务器（免费）。
 *  Mysql默认的端口号是：3306。Tomcat 默认的端口号是：8080
 *   修改tomcat默认端口号：Tomcat目录下的conf ==> server.xml ==> 找到connector标签，修改port属性为想要的端口号(范围是1-65535)
 *  ✔✔为什么要用tomcat？因为我们直接打开html文件，使用的file://协议，即告诉浏览器读取本地文件即可
 *   使用tomcat输入入http://ip:port/工程目录/项目名，使用的是http://协议，相当于客户端发起了请求，二者原理完全不同
 *  tomcat的默认访问路径✔✔
 *    当我们在浏览器地址栏中http://ip:port/ （没有输入工程名）的时候，默认访问的是 webapps/ROOT工程。
 *    当我们在浏览器地址栏中http://ip:port/工程名/  默认映射到webapp这个目录（即当前工程在web中的路径），但无法方法WEB-INF目录
 *    当我们在浏览器地址栏中http://ip:port/工程名/ （没有输入资源名）的时候，默认访问 webapp/index.html 页面
 *    在绝大多数javaweb程序中，'/'通常映射为'http://ip:port/工程名'
 *     ✔(特殊情况1：在html/jsp中，斜杠通常被解析为'http://ip:port')
 *     ✔(特殊情况2：在javaweb的重定向中使用response.sendRediect("/"); 把斜杠发送给浏览器解析。得到的是http://ip:port/)
 *
 * 【Tomcat部暑web工程】本地中
 * （1）方式一：了解
 *  将项目拷贝到tomcat目录的webapps目录下，然后重启tomcat服务器
 *  在浏览器中输入http://ip:port/工程目录/资源名✔✔✔
 *              如http://localhost:8080/book/index.html（实际上http://ip:port/就定位到webapps目录）
 * （2）方式二：了解
 *  找到Tomcat下的conf目录\Catalina\localhost\ 下,创建配置文件abc.xml
 *  在文件中输入：<Context path="/abc" docBase="E:\book"/>
 *                 Context 表示一个工程上下文
 *                 path 表示工程的访问路径:/abc，即在浏览器输入该路径访问到docBase目录
 *                 docBase 表示你的工程目录在哪里
 *                 貌似指定docBase下webapps下还不行~
 *
 * 【IDEA中集成Tomcat】idea中，重点
 *（1）配置tomcat
 *  File ==> Settings ==> Build, Execution, Deployment ==> Application Servers ==> 添加，将Tomcat Home和base directory设置成文件按安装 目录 ！
 *   可以添加多个Tomcat服务器
 * (2) 创建javaweb模块
 *  new module ==> jakarta EE(template:web application) ==> version:java ee8
 * (3) 添加jar包方式一：较方便✔目录添加
 *  src ==> main ==> webapp ==> web-inf ==> 创建一个lib目录 (web-inf是一个服务器无法直接访问到的文件夹)
 *  将需要使用的jar放在lib目录中，右键add as library即可
 *  javaweb默认根目录是src/main/java，java只能在根目录下建package，可以右键其他目录标记为根目录mark directory as sources root
 * (4) 添加jar包方式二：较通用✔库添加
 *  File ==> project structure ==> libraries ==> new project libraray ==> java ==> 选择相关的java包创建类库 ==> 选择创建的类库给哪个模块使用
 *  File ==> project structure  ==> modules(选择需要查看的模块) ==> dependencies可以查看当前依赖的jar包,并添加所需要的包(库)
 * (5) 添加artifact伪影地址/war包地址 (因为javaweb部署时，会将webapp文件夹，打包成war包映射到伪影地址中✔✔)
 *  File ==> project structure ==> facets ==>选择指定web项目，点击右下角的fix添加artifacts（自动添加伪影）
 *  File ==> project structure ==> artifacts ==> output directory 修改伪影地址，通常默认在target目录下（手动修改,一般不改）
 *  File ==> project structure ==> artifacts ==> available elements  当为web项目配置library类库后，一定要为artifacts也添加相应的类库,具体做法就是双击available elements中的相应类库
 *                                                                    如使用maven管理，则其中Lifecycle-package指令，也是实现相同的功能~
 * (6)具体tomcat配置(导航栏 ==> tomcat edit configuration)
 *  导航栏 ==> tomcat edit configuration ==> server ==> name         # 为了让每个工程对应一个tomcat服务器，建议修改
 *  server ==> URL                                                   # tomcat启动时默认打开的访问地址
 *          ==> HTTP port                                             # 修改端口号
 *          ==> on frame deactivation ==> updateclassesandresources   # ✔配置资源热部署，修改页面后可以在立即显示（修改html页面可以直接显示无需redeploy）
 *          ==> on update action                                      # ✔修改重启tomcat按钮的默认选项，当我们配置完资源热部署之后，可以修改默认选项为redeploy（修改了文件参数文件或是修改了servlet需要redeploy）
 *  deployment                                    # javaweb部署时，会将webapp文件夹以war包的形式复制到artifact设定的路径中。
 *                                                    deployment中需要配置这个地址。通常在添加artifact后，为 模块名:war exploded格式
 *                                                    如果希望该tomcat服务器跑多个web工程，可以在此处添加（但通常一个tomcat跑一个工程）
 *  deployment ==> application context            # ✔appLication context指向当前工程在web中的路径（即浏览器输入的地址 或 称为当前工程的上下文路径）（建议配合上方URL修改，建议改成和项目名一致）
 *
 @author Alex
 @create 2023-01-27-19:45
 */
public class UT02 {
}
