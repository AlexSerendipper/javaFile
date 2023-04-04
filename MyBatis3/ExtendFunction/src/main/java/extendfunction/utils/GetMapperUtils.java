package extendfunction.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 @author Alex
 @create 2023-03-11-10:20
 */
public class GetMapperUtils {
    // 实现输入核心配置文件的路径 以及 对应的mappers接口
    // 返回对应的 mappers 实现类
    public static <T> T getMapper1(Class<T> clazz) throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        T mapper = sqlSession.getMapper(clazz);
        return mapper;
    }

    // 实现输入核心配置文件的路径 以及 对应的mappers接口
    // 返回对应的 mappers 实现类
    public static SqlSession getMapper2() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession;
    }
}
