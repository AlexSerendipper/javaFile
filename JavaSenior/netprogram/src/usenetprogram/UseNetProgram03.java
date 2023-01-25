package usenetprogram;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 【面向socket编程】基于TCP的网络编程
 @author Alex
 @create 2022-12-19-21:38
 */

// 例子2：客户端发送文件给服务端，服务端将文件保存在本地。并返回“发送成功”给 客户端。并关闭相应的连接。
public class UseNetProgram03 {
    // 客户端
    @Test
    public void test() throws IOException {
        InetAddress inet = InetAddress.getByName("localhost");
        Socket socket = new Socket(inet, 8090);
        OutputStream outputStream = socket.getOutputStream();

        // 先读取文件，再写出出去
        FileInputStream fileInputStream = new FileInputStream(new File("111.png"));
        int len;
        byte[] bytes = new byte[100];
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }

        // ✔关闭数据的输出。如果不关闭数据的输出，服务器端调用read方法永远不会返回-1，因为不知道什么时候接收完成，客户端服务器端会无法终止
        socket.shutdownOutput();

        // 接收来自服务器端的数据并显示在控制台上。接收从服务器端返回的数据，不需要socket.accept
        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  // 为了避免显示乱码
        byte[] b = new byte[5];
        int len1;
        while ((len1 = inputStream.read(b)) != -1) {
            byteArrayOutputStream.write(b, 0, len1);
        }
        System.out.println(byteArrayOutputStream.toString());

        // 流的关闭
        fileInputStream.close();
        outputStream.close();
        socket.close();
        byteArrayOutputStream.close();
    }

    // 服务器端
    @Test
    public void test1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8090);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        // 读取来自客户端的数据，在写到本地
        FileOutputStream fileOutputStream = new FileOutputStream(new File("222.png"));
        int len;
        byte[] bytes = new byte[100];
        while ((len = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
        }
        System.out.println("已收到来自：" + socket.getInetAddress().getHostAddress() + "的消息");
        // 服务器段给客户端反馈
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("发送成功".getBytes());

        // 资源的关闭
        fileOutputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
        outputStream.close();
    }
}
