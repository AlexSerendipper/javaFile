package usenetprogram;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 【网络编程的两个基本要素✔】
 * 如何定位另一台主机 ==> IP地址+端口号
 * 找到主机后如何可靠高效地进行数据传输 ==> 网络通信协议TCP/IP协议（应用层、传输层、）
 *
 * 【IP地址】
 * (1)作为internet上计算机的唯一标识
 * (2)在java中使用inetAddress类，来代表IP
 * (3)分类： ①IPV4（4个字节组成）和 IPV6（16个字节组成）
 *           ②公网地址(万维网使用www)和私有地址(局域网使用192.168.)。
 * (4)域名，相当于是ip地址的别名（上网的时候通过DNS把域名解析成IP地址）
 * (5)本地回环地址：127.0.0.1, 对应本地域名localhost
 * 【端口号】端口号标识正在计算机上运行的程序（进程）
 * (1)不同的进程有不同的端口号，范围从0~65535
 * (2)端口号和ip地址组成得出一个网络套接字：socket
 *
 * 【InetAddress】IP地址类
 *   构造器
 *   public static InetAddress getLocalHost()：静态方法，获取本地回环IP地址实例
 *   public static InetAddress getByName(String host)：静态方法，根据域名或IP地址获取IP地址实例
 *   方法
 *   public String getHostAddress()：返回 IP 地址字符串（以文本表现形式）。
 *   public String getHostName()：获取此 IP 地址的主机名
 *   public boolean isReachable(int timeout)：测试是否可以达到该地址
 *
 * 【网络通信协议概述】
 *  (1)计算机网络中实现通信必须有一些约定，即通信协议，对速率、传输代码、代码结构、传输控制步骤、出错控制等制定标准。
 *  (2)协议的分层思想，即同层间可以通信、上一层可以调用下一层，而与再下一层不发生关系。
 *  (3)传输层协议中有两个非常重要的协议：
 *     传输控制协议TCP(Transmission Control Protocol)
 *     用户数据报协议UDP(User Datagram Protocol)。
 *
 * 【TCP协议：可靠的数据传输】
 *   使用TCP协议前，须先建立TCP连接，形成传输数据通道
 *   传输前，采用“三次握手”方式，点对点通信，是可靠的
 *   TCP协议进行通信的两个应用进程：客户端、服务端。
 *   在连接中可进行大数据量的传输
 *   传输完毕，需释放已建立的连接，效率低（四次挥手）
 *
 * 【UDP协议：不可靠的数据传输。主要用于网络视频、广播之类的，就是允许丢帧，尽量保证速度】
 *   将数据、源、目的封装成数据包，不需要建立连接
 *   每个数据报的大小限制在64K内
 *   发送不管对方是否准备好，接收方收到也不确认，故是不可靠的
 *   可以广播发送
 *   发送数据结束时无需释放资源，开销小，速度快
 @author Alex
 @create 2022-12-19-20:05
 */
public class UseNetProgram01 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inet1 = InetAddress.getByName("192.168.10.14");  // 可以输入IP地址，或者域名
        System.out.println(inet1);
        System.out.println("*****************");
        InetAddress inet2 = InetAddress.getByName("www.baidu.com");
        System.out.println(inet2);  // 输出时经过dns域名的解析了
        System.out.println(inet2.getHostName());
        System.out.println(inet2.getHostAddress());
        System.out.println("*****************");
        InetAddress inet3 = InetAddress.getByName("localhost");
        System.out.println(inet3);
        System.out.println("*****************");
        InetAddress inet4 = InetAddress.getLocalHost();  // 直接获取本机的ip地址
        System.out.println(inet4);  // 这里会输出本机的局域网内ip地址，所以和上边不一样，但指向同一个对象
        System.out.println("*****************");
    }
}
