package computerNetWork;

/**
 * 【HTTP & HTTPS】
 *  HTTP协议：HTTP协议中，请求和响应的报文都是明文的，所有人都能看得到HTTP报文中的内容
 *   为了给HTTP协议增加安全性，所以应运而生了HTTPS
 *  HTTPS：HTTPS协议并不是一个单独的协议，只不过是在HTTP的基础之上，运用了TLS和SSL进行加密，这样通信就不容易受到拦截和攻击
 *
 * 【SSL & TLS】
 *  SSL 是 TLS的前身，他们都是加密安全协议，现在绝大部分浏览器都不支持SSL，而是支持 TLS
 *  （只不过SSL的名气大，所以很多人会把这两个名字混用）
 *  对称加密：发送方和接收方 使用一种同样的规则对数据进行加密和解密！以此来保证会话的安全性
 *       缺点：如果有第三方知道了加密的规则，这种方法将不再安全
 *  非对称加密（公钥加密）：发送方自己独有的密钥P1 和 接收方独有的密钥P2
 *                        将各自的私钥 通过公钥密G 进行加密！得到混合钥（公钥可能被黑客知道）
 *                        发送方将自己的混合钥Y1 与 接收方的混合钥Y2进行交换（混合钥也会可能被黑客窃取）
 *                        此时 P1+Y2 = P2+Y1， 以此作为二者的通信规则（这是黑客毫无办法的，因为黑客没有私钥）
 *                        （P1 + P2 + G = P2 + P1 + G）
 * ✔非对称加密应用于客户端和服务端时：通常服务端拥有成对的密钥和公钥。就不会向上述这么复杂~
 *                                 服务端将公钥公布，客户端的数据通过公钥加密后，只能通过服务端的私钥解密。
 *
 *  证书：如要使用HTTPS协议，服务端需要向CA（Certificate Authority）申请SSL证书（证书而言，SSL证书比TLS证书更出名）
 *         这个证书中，包含了：域名所属、日期、特定的公钥和密钥
 *         申请成功后，浏览器会把HTTP的默认端口从80改为HTTPS的默认端口443
 *  TLS握手过程：（1）客户端向服务端发送Client Hello，并且发送自己支持的TLS版本和加密套件，以及第一个随机数
 *               （2）服务端向客户端发送Server Hello，确认支持的TLS版本和加密套件，以及第二个随机数
 *                    接着，服务端还把自己的证书、公钥都发给了客户端，最后发送一个Server Hello Done，说明自己发送完了
 *               （3）客户端生成第三个随机数（预主密钥），并通过公钥加密后发送给服务端
 *                    服务端使用自己的私钥进行解密，得到原始的预主密钥（只有客户端和服务端知道这个预主密钥）
 *               （4）客户端和服务端，都使用 第一个随机数+第二个随机数+预主密钥 = 会话密钥
 *               （5）前四个步骤直到得到会话密钥都是属于非对称加密！最终得到了只有通信二者才知道的会话密钥
 *                    接下来客户端和服务端都使用会话密钥对数据进行加密传输（对称加密）
 *
 * 【浏览器输入URL返回页面过程】
 *  1.通过DNS解析URL中域名对应的服务器主机IP地址
 *  2.与服务器主机三次握手建立TCP连接
 *  3.发送HTTP请求（请求报文） ，获取服务器返回的数据（响应报文）
 *  4.浏览器解析HTML、CSS和JS等前端文件，渲染页面
 @author Alex
 @create 2023-06-16-14:25
 */
public class UC03 {
}
