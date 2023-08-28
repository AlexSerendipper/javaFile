package basicfunction;

import basicfunction.mappers.UserMapper;
import basicfunction.pojo.User;
import basicfunction.utils.GetMapperUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 【MyBatis简介】
 *  在之前学习的工具中，如Dbutils，jdbc template只能算作是工具，功能较为单一
 *   并且在传统的工具中，SQL夹在Java代码块里，耦合度高导致硬编码内伤，维护不易。
 *  MyBatis作为一个轻量级的半自动化的持久化层框架，提供了一个整体的解决方法。
 *   实现了将Java代码与sql语句分离，方便开发控制与后期维护
 *
 * 【MyBatis特点】
 *  MyBatis 是 支持定制化SQL、存储过程以及高级映射的优秀的 持久层框架
 *  MyBatis 避免了几乎所有的 JDBC代码 和 手动设置参数 以及 获取结果集
 *  MyBatis 可以使用简单的XML或注解用于配置和原始映射，将接口和Java的POJO（普通的Java对象）映射成数据库中的记录
 *  MyBatis 是一个 半自动的ORM（Object Relation Mapping对象关系映射）框架，即数据库对象 与 实体类对象的映射关系
 *                                                                      （即 类 ==> 表）
 *                                                                      （即 对象 ==> 记录/行）
 *                                                                      （即 属性 ==> 字段/列）
 *
 * 【MyBatis下载】
 *  MyBatis下载地址：https://github.com/mybatis/mybatis-3
 *
 * 【搭建MyBatis所需结构】
 * （1）引入依赖
 ----------------------------
 <!--设置打包方式为jar包-->
 <packaging>jar</packaging>
 <dependencies>
     <!-- Mybatis核心 -->
     <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis</artifactId>
         <version>3.5.7</version>
     </dependency>
     <!-- junit测试 -->
     <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>4.12</version>
         <scope>test</scope>
     </dependency>
     <!-- MySQL驱动，注意要导入8.0的驱动 -->
     <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>8.0.11</version>
     </dependency>
     <dependency>
         <groupId>org.testng</groupId>
         <artifactId>testng</artifactId>
         <version>6.14.3</version>
         <scope>test</scope>
     </dependency>
     <!-- log4j日志 -->
     <dependency>
         <groupId>log4j</groupId>
         <artifactId>log4j</artifactId>
         <version>1.2.17</version>
     </dependency>
     <dependency>
         <groupId>org.junit.jupiter</groupId>
         <artifactId>junit-jupiter</artifactId>
         <version>RELEASE</version>
         <scope>compile</scope>
     </dependency>
 </dependencies>
 ----------------------------
 * （2）创建MyBatis的核心配置文件
 *  核心配置文件习惯上命名为 mybatis-config.xml，主要用于配置连接数据库的环境以及MyBatis的全局配置信息
 ----------------------------
 <configuration>
     <!--设置连接数据库的环境-->
     <environments default="development">
         <environment id="development">
         <transactionManager type="JDBC"/>
         <dataSource type="POOLED">
             <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
             <property name="url" value="jdbc:mysql://localhost:3306/javastudy?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true"/>
             <property name="username" value="root"/>
             <property name="password" value="qqabcd"/>
         </dataSource>
         </environment>
     </environments>
     <!--引入映射文件-->
     <mappers>
         <mapper resource="mappers/UserMapper.xml"/>
     </mappers>
 </configuration>
 ----------------------------
 * (3) 创建mapper接口
 *  MyBatis中的mapper接口相当于以前的dao。但是区别在于，mapper仅仅是接口，不需要提供实现类。
 *   因为MyBatis内置面向接口编程，当调用接口中的方法，自动会匹配sql语句并执行
 * (4) 创建mapper接口 映射文件
 *  映射文件用于编写SQL，访问以及操作表中的数据
 *  映射文件的命名规则：通常以 表所对应的实体类类名 + Mapper.xml 命名， 即与mapper接口的名字保持一致。因此一个映射文件对应一个实体类，对应一张表的操作
 *  映射文件存放的位置是src/main/resources/mappers目录下
 *  ✔✔✔默认存在两个一致：① 映射文件的命名空间（namespace） 需要与 mapper接口的全类名 保持一致
 *                           ② 映射文件中编写SQL的标签的id属性 需要与 接口中方法的方法名 保持一致
 *  ✔实际上最终执行时，就是通过 namespace + id，找到对应的唯一的sql语句
-------------------------------
<!--映射文件的命名空间（namespace） 需要与 mapper接口的全类名 保持一致-->
<mapper namespace="basicfunction.mapper.UserMapper">
<!--映射文件中编写SQL的标签的id属性 需要与 接口中方法的方法名 保持一致-->
<insert id="insertUser">
    insert into mybatis_user values(null,'张三','123',23,'女')
</insert>
</mapper>
-------------------------------

 * 【功能使用】获取sqlsession对象，见下方junit测试
 *  SqlSession：代表 Java程序 和 数据库 之间的会话对象
 *  由于获取sqlsession的过程几乎固定，故强烈建议将其封装为SqlSessionUtils函数
 *
 * 【基本查询功能实现】详见UBF04
 *  查询的标签select必须设置属性resultType或resultMap，输入查询数据结果的类的全类名。用于设置实体类和数据库表的映射关系
 *     resultType：设置返回值为具体类，即将对应字段的查询结果赋值给具体类的属性值（自动映射）
 *                   ✔此处若不在核心配置文件中设置，需要传入类的全类名！！
 *                   即适用于 '属性名和表中字段名' 一致的情况
 *     resultMap：自定义映射关系，即用于 '一对多或多对一' 或 '字段名和属性名不一致' 的情况
 *  当查询的数据只有一条，可以使用 实体类 或 集合 作为返回值✔
 *     查询的数据为多条时，只能使用集合接收✔
 *
 @author Alex
 @create 2023-03-10-13:38
 */
public class UBF01 {
    // 测试添加功能
    @Test
    public void test() throws IOException {
        // （1）读取MyBatis的核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        // （2）创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // （3）创建SqlSession对象，通过核心配置文件所对应的字节输入流创建工厂类SqlSessionFactory，
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // （4）创建SqlSession对象，不传入true, 则此时通过SqlSession对象所操作的sql都会自动提交 SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //   ✔创建SqlSession对象，此时通过SqlSession对象所操作的sql都必须手动提交或回滚事务
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // （5）通过代理模式创建UserMapper接口的代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // （6）调用UserMapper接口中的方法，就可以根据UserMapper的全类名匹配映射文件，通过调用的方法名匹配映射文件中的SQL标签，并执行标签中的SQL语句
        int result = userMapper.insertUser();
        sqlSession.commit();
        System.out.println("结果："+result);
    }


    // 测试查询功能
    // 测试获取mapper工具类
    @Test
    public void test3() throws IOException {
        UserMapper userMapper = GetMapperUtils.getMapper1(UserMapper.class);
        User user = userMapper.getUserById(4);
        System.out.println("结果3："+ user);
        // 测试查询功能, 所有user
        List<User> userList = userMapper.getUserList();
        System.out.println("结果4："+ userList);
    }

}
