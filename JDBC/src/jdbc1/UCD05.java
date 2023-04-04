package jdbc1;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * 【blog】
 *  二进制字符串类型包括：BINARY、VARBINARY、TINYBLOB、BLOB、MEDIUMBLOB、LONGBLOB
 *  BLOB是一个二进制大对象，可以容纳可变数量的数据。包括TINYBLOB(255Bit)、BLOB(65K)、MEDIUMBLOB(16M)和LONGBLOB(4G) 4种类型
 *  在实际工作中，往往不会在MySQL数据库中使用BLOB类型存储大对象数据，通常会将图片、音频和视频文件存储到服务器的磁盘上 ，并将图片、音频和视频的访问路径存储到MySQL中。
 *  如果在设置了blob类型后，仍然报 xxx too large，那么在mysql的安装目录下找到my.ini文件，添加配置参数max_allowed_packet=16M，即可
 *
 * 【使用PreparedStatement操作blog类型数据】
 *  PreparedStatement.setBlob(index,FileInputStream)            # 使用PreparedStatement能够操作blob类型的数据
 *  Blob bb = resultSet.getBlob(index);                         # 使用PreparedStatement读取blob类型的数据
 *  bb.getBinaryStream();                                       # 可以获得读出的blob文件的输入流
 @author Alex
 @create 2023-01-30-17:03
 */
public class UCD05 {
    // 插入blob数据
    @Test
    public void test1() throws Exception{
        String sql = "INSERT INTO customers(name, email, birth, photo) VALUES(?, ?, ?, ?)";
        Connection connection = UCD02.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "LDH");
        ps.setString(2, "LDH@163.com");
        ps.setDate(3, new Date(new java.util.Date().getTime()));
        //填充 Blob 类型的数据
        ps.setBlob(4, new FileInputStream(new File("./src/useconnnectdatabase/aaa.jpg")));  // 根目录在当前module
        ps.executeUpdate();
        UCD03.closeResourse(connection,ps);
    }

    // 查询数据表中的blob数据
    @Test
    public void test2() throws Exception{
        String sql = "select id,name, email, birth, photo from customers where id=?";
        Connection connection = UCD02.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,21);
        ResultSet resultSet = ps.executeQuery();
        // 前四个字段封装在类中，最后一个字段用流的方式读取
        customer cust = null;
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            java.util.Date birth = resultSet.getDate(4);
            cust = new customer(id, name, email, birth);
        }
        System.out.println(cust);
        Blob photo = resultSet.getBlob(5);
        InputStream is = photo.getBinaryStream();
        FileOutputStream fos = new FileOutputStream(new File("./src/useconnnectdatabase/bbb.jpg"));
        byte[] bytes = new byte[1024];
        int len;
        while((len = is.read(bytes)) != -1){
            fos.write(bytes,0,len);
        }
        UCD03.closeResourse(connection,ps,resultSet);
    }

}
