package usenetprogram;

import org.junit.Test;

import java.io.IOException;
import java.net.*;

/**
 * 【基于UDP协议的网络编程,作为了解】
 *  类DatagramSocket和DatagramPacket实现了基于UDP协议网络程序。
 *  UDP数据报通过DatagramSocket发送和接收，系统不保证UDP数据报一定能够安全送到目的地，也不能确定什么时候可以抵达✔
 *  DatagramPacket对象封装了UDP数据报✔，在数据报中包含了发送端的IP地址和端口号以及接收端的IP地址和端口号。
 *  UDP协议中每个数据报都给出了完整的地址信息，因此无须建立发送方和接收方的连接。如同发快递包裹一样。
 *  可以发现，UDP不打开接收端，直接打开发送端也不会报错，因为它只管发送，不管接收，这是和TCP最大的区别
 *
 * 【DatagramPacket类的常用方法】
 *  ✔public DatagramPacket(byte[] buf,int offset,int length,InetAddress address,int port)
 *    构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号。length参数必须小于等于 buf.length。
 *  public InetAddress getAddress()返回某台机器的 IP 地址，此数据报将要发往该机器或者是从该机器接收到的。
 *  public int getPort()返回某台远程主机的端口号，此数据报将要发往该主机或者是从该主机接收到的。
 *  public byte[] getData()返回数据缓冲区。接收到的或将要发送的数据从缓冲区中的偏移量 offset 处开始，持续 length 长度。
 *  public int getLength()返回将要发送或接收到的数据的长度。
 *
 * 【DatagramSocket类的常用方法】
 *  public DatagramSocket()创建数据报套接字
 *  public void close()关闭此数据报套接字。
 *  ✔public void send(DatagramPacket p)从此套接字发送数据报包。DatagramPacket包含的信息指示：将要发送的数据、其长度、远程主机的 IP 地址和远程主机的端口号。
 *  public void receive(DatagramPacket p)从此套接字接收数据报包。当此方法返回时，DatagramPacket
 *    的缓冲区填充了接收的数据。数据报包也包含发送方的 IP 地址和发送方机器上的端口号。 此方法
 *    在接收到数据报前一直阻塞。数据报包对象的 length 字段包含所接收信息的长度。如果信息比包的
 *    长度长，该信息将被截短。
 *  public InetAddress getLocalAddress()获取套接字绑定的本地地址。
 *  public int getLocalPort()返回此套接字绑定的本地主机上的端口号。
 *  public InetAddress getInetAddress()返回此套接字连接的地址。如果套接字未连接，则返回 null。
 *  public int getPort()返回此套接字的端口。如果套接字未连接，则返回 -1。
 *
 * 【流程】：
 * DatagramSocket与DatagramPacket
 * 建立数据包
 * 调用Socket的发送、接收方法
 * 关闭Socket
 *
 @author Alex
 @create 2022-12-21-14:48
 */
public class UseNetProgram04 {
    // 发送端
    @Test
    public void test() throws IOException {
        // UDP就不在socket里指明ip和端口号了
        DatagramSocket socket = new DatagramSocket();
        String s = new String("我是UDP方式传递的数据");
        byte[] data = s.getBytes();
        InetAddress localhost = InetAddress.getByName("localhost");
        // 把数据，ip和端口号都放在Packet里
        DatagramPacket datagramPacket = new DatagramPacket(data,0,data.length,localhost,8090);
        // 发送数据
        socket.send(datagramPacket);
        // 关闭流
        socket.close();
    }


    // 接收端
    @Test
    public void test1() throws IOException {
        // 接收端要指定一下端口号
        DatagramSocket socket = new DatagramSocket(8090);

        byte[] b = new byte[100];
        // 这里就先体会一下，所以接收的数据特别长
        DatagramPacket packet = new DatagramPacket(b,0,b.length);
        // 接收数据
        socket.receive(packet);
        System.out.println(new String(packet.getData(),0,packet.getLength()));
        // 关闭流
        socket.close();

    }
}
