package jsp;

/**
 * 【EL表达式】EL表达式的全称是：Expression Language。是表达式语言。
 *  EL表达式主要是代替jsp页面中的表达式脚本在jsp页面中进行数据的输出。
 *   因为EL表达式在输出数据的时候，要比jsp的表达式脚本要简洁很多✔✔
 *   要时刻记得EL表达式的出现是为了让jsp的表达式更简洁
 *  EL表达式在输出null值的时候输出的是空串。jsp表达式脚本输出null值的时候输出的是null字符串。
 *  用EL表达式输出域对象中存储的数据时。若四个域中都有相同key的数据的时候，
 *   EL表达式会按照四个域的从小到大的顺序去进行搜索，找到就输出。
 *
 * 【EL表达式支持的运算符】见示例UJ03.jsp
 * (1)算数运算符（同Java）
 * (2)逻辑运算符（同Java）
 * (3)关系运算符（同Java）
 * (4)三元运算符（同Java）
 * (5)empty运算：empty 运算可以判断一个数据是否为空，如果为空，则输出 true,不为空输出 false。
 *     值为null值的时候为空
 *     值为空串的时候为空
 *     值是Object类型数组，长度为零的时候
 *     list集合元素个数为零
 *     map集合元素个数为零
 *
 * 【EL表达式中自定义的11个对象】可以直接在EL表达式中使用
 * 变量                                  类型                                      作用
 * pageContext                     PageContextImpl                     它可以获取 jsp 中的九大内置对象
 *
 * pageScope                       Map<String,Object>                  它可以获取 pageContext 域中的数据
 * requestScope                    Map<String,Object>                  它可以获取 Request 域中的数据
 * sessionScope                    Map<String,Object>                  它可以获取 Session 域中的数据
 * applicationScope                Map<String,Object>                  它可以获取 ServletContext 域中的数据
 *
 * param                           Map<String,String>                  它可以获取请求参数的值
 * paramValues                     Map<String,String[]>                它也可以获取请求参数的值，获取多个值的时候使用。
 * header                          Map<String,String>                  它可以获取请求头的信息
 * headerValues                    Map<String,String[]>                它可以获取请求头的信息，它可以获取多个值的情况
 * cookie                          Map<String,Cookie>                  它可以获取当前请求的 Cookie 信息
 * initParam                       Map<String,String>                  它可以获取在 web.xml 中配置的<context-param>上下文参数
 *
 * 【EL表达式式输出对象的属性和方法】见示例UJ03.jsp
 *  EL表达式可以输出javaBean对象的任意类型的属性。包括普通属性、数组属性、List集合属性、map集合属性等
 *  注意：必须是javabean对象。EL输出属性的时候，格式为： javabean.属性。实际上调用的是javabean中的get方法的返回值✔✔✔✔
 *                                            所以使用EL表达式输出对象的方法时，只需要将方法设置为get方法即可✔
 *                                            如Person.age。实际上输出的是getAge方法的返回值
 *
 * 【EL中pageContext的使用】实际上就是jsp九大内置对象的常用方法，然后jsp的九大对象又是封装了servlet中的对象
 * （1）协议： ${ pageContext.request.scheme }<br>                       # 实际上在jsp表达式中调用的都是get方法，EL中省略了get   <%=request.getScheme() %> <br>
 * （2）服务器 ip：${ pageContext.request.serverName }<br>
 * （3）服务器端口：${ pageContext.request.serverPort }<br>
 * （4）✔获取工程路径：${ pageContext.request.contextPath }<br>
 * （5）获取请求方法：${ pageContext.request.method }<br>
 * （6）获取客户端 ip 地址：${ pageContext.request.remoteHost }<br>
 * （7）获取会话的 id 编号：${ pageContext.session.id }<br>
 *
 * 【EL中其他标签的使用】例：使用请求地址http://localhost:8080/javaweb/jsp/UJ03.jsp?username=wzg168&password=666666&hobby=java&hobby=cpp
 * ${ param.username } <br>          输出请求参数 username 的值
 * ${ param.password } <br>          输出请求参数 password 的值
 * ${ paramValues.hobby[1] } <br>    输出请求参数 hobby 的值（多个参数时）
 * ${ header['User-Agent'] } <br>    输出请求头【User-Agent】的值
 * ${ header.Connection } <br>       输出请求头【Connection】的值
 * ${ headerValues['User-Agent'][0] } <br>    输出请求头【User-Agent】的值
 * ${ cookie.JSESSIONID.name } <br>           输出Cookie 的名称：
 * ${ cookie.JSESSIONID.value } <br>          输出Cookie 的值：
 * ${ initParam.password } <br>      输出web.xml中配置的上下文参数的值
 *
 @author Alex
 @create 2023-02-02-14:08
 */
public class UJ03 {
}
