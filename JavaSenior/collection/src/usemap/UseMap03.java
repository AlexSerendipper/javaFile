package usemap;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 【Properties】
 *  Properties类是 Hashtable的子类，该对象用于处理属性文件
 *  由于属性文件里的 key、value 都是字符串类型，所以Properties里的 key和value都是字符串类型
 *  load(InputStream is)                       # 读取配置文件
 *  setProperty(String key,String value)       # 取数据
 *  getProperty(String key)                    # 存数据
 *
 @author Alex
 @create 2022-12-11-15:21
 */
public class UseMap03 {
    public static void main(String[] args) throws Exception{
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("jdbc.properties");  // 自定义配置文件，main方法中默认在workspace下
        properties.load(fis);  // 加载对应的流文件
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");
        System.out.println("name = " + name + ",password = " + password);
    }
}
