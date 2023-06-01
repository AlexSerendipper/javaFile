package usenetprogram;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 【面向socket编程】基于TCP的网络编程
 *  注意：涉及到的异常应该使用try-catch处理，为简洁，这里均采用throws
 *   网络上具有唯一标识的IP地址和端口号组合在一起才能构成唯一能识别的标识符套接字(socket)
 *   Socket允许程序把网络连接当成一个流，数据在两个Socket间通过IO传输。
 *   一般主动发起通信的应用程序属客户端，等待通信请求的为服务端✔
 *     客户端：自定义、浏览器
 *     服务端：自定义、Tomcat服务器（可以通过浏览器访问本地服务器。这个在web阶段会细说）
 *   Socket分类✔：
 *     流套接字（stream socket）：使用TCP提供可依赖的字节流服务
 *     数据报套接字（datagram socket）：使用UDP提供“尽力而为”的数据报服务
 *
 * 【常用方法】
 *  Socket类的常用构造器：
 *    public Socket(InetAddress address,int port)        # 客户端创建一个流套接字并将其连接到指定 IP 地址的指定端口号。创建的同时会自动向服务器方发起连接
 *    public Socket(String host,int port)                # 创建一个流套接字并将其连接到指定主机上的指定端口号。
 *    public ServerSocket(int port) ：                   # 创建一个服务器端套接字，并绑定到指定端口上。
 *  Socket类的常用方法：
 *    public InputStream getInputStream()                # 返回此套接字的输入流。可以用于接收网络消息
 *    public OutputStream getOutputStream()              # 返回此套接字的输出流。可以用于发送网络消息
 *    public InetAddress getInetAddress()                # 此套接字连接到的远程 IP 地址；如果套接字是未连接的，则返回 null。
 *    public InetAddress getLocalAddress()               # 获取套接字绑定的本地地址。 即本端的IP地址
 *    public int getPort()                               # 此套接字连接到的远程端口号；如果尚未连接套接字，则返回 0。
 *    public int getLocalPort()                          # 返回此套接字绑定到的本地端口。 如果尚未绑定套接字，则返回 -1。即本端的端口号。
 *    public void close()                                # 关闭此套接字。套接字被关闭后，便不可在以后的网络连接中使用（即无法重新连接或重新绑定）。
 *                                                           需要创建新的套接字对象。关闭此套接字也将会关闭该套接字的 InputStream 和OutputStream。
 *    public void shutdownInput()                        # 如果在套接字上调用 shutdownInput() 后从套接字输入流读取内容，
 *                                                          则流将返回 EOF（文件结束符）。即不能在从此套接字的输入流中接收任何数据。
 *    public void shutdownOutput()                       # 禁用此套接字的输出流。对于 TCP 套接字，任何以前写入的数据都将被发送，并且后跟 TCP 的正常连接终止序列。
 *                                                           如果在套接字上调用 shutdownOutput() 后写入套接字输出流，则该流将抛出 IOException。即不能通过此套接字的输出流发送任何数据。
 *
 * 【具体流程】
 *  1）客户端流程：
 *     ① 创建 Socket
 *     ② 打开连接到 Socket 的输入/出流：使用 getInputStream()方法获得输入流，使用getOutputStream()方法获得输出流。进行数据传输
 *     ③ 按照一定的协议对 Socket 进行读/写操作
 *     ④ 关闭 Socket
 *  2）服务器端流程
 *     ① 创建 ServerSocket：调用 ServerSocket(int port) ：
 *     ② 调用 accept()：监听连接请求，如果客户端请求连接，则接受连接。返回通信套接字对象（socket）
 *     ③ 打开连接到 Socket 的输入/出流：getOutputStream() 和 getInputStream ()
 *     ④ 关闭ServerSocket和Socket对象
 *
 @author Alex
 @create 2022-12-19-21:07
 */

// 例子1：客户端发送信息给服务端，服务端将客户端发送的消息转换为大写 并返回“发送成功”给客户端
public class UseNetProgram02 {
    // 客户端
    @Test
    public void test() throws IOException {
        // 1. 创建socket，指明服务器的ip和端口号。
        InetAddress inet = InetAddress.getByName("localhost");
        Socket socket = new Socket(inet,8899);
        // 2. 用对应输出流发送数据
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello,i'm sexy lady".getBytes());
        // 客户端输出后，一定要关闭数据的输出，否则服务器端调用read方法永远不会返回-1，因为不知道什么时候接收完成，客户端服务器端将会无法终止
        socket.shutdownOutput();

        // 3.接收服务器返回数据并显示
        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  // 为了避免显示乱码
        int len;
        byte[] b = new byte[5];
        while((len=inputStream.read(b))!=-1){
            byteArrayOutputStream.write(b, 0, len);
        }
        System.out.println(byteArrayOutputStream.toString());

        // 4. 关闭
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    // 服务端
    @Test
    public void test1() throws IOException {
        // 1. 创建服务器端的socket，指明自己的端口号
        ServerSocket serverSocket = new ServerSocket(8899);
        // 2. 打开开关让其接收来自客户端的socket
        Socket socket = serverSocket.accept();
        // 3. 用对应输入流接收数据，因为有中文且要及时显示，故使用字节数组输出流
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        // 服务器段给客户端反馈
        outputStream.write("发送成功".getBytes());
        outputStream.write("你发送的消息如下：".getBytes());
        byte[] b = new byte[5];
        int len;
        while ((len = inputStream.read(b)) != -1) {
            String s = new String(b, 0, len).toUpperCase();
            System.out.printf(s);
            outputStream.write(s.getBytes(), 0, len);
        }
        System.out.println();
        System.out.println("收到了来自于:"+socket.getInetAddress().getHostAddress()+"的消息");

        // 4. 资源关闭
        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }

}