package dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 增删改查的抽象基类
 * 注意，这里遇到异常一定要往外抛，才能实现数据库的事务性（要么成功，要么回滚）
 @author Alex
 @create 2023-02-01-13:09
 */
public abstract class BaseDao {
    // 增删改，返回-1表示执行失败
    public int update(String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            return runner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 查询返回一条数据
    public <T> T queryForOne(String sql, Class<T> type, Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BeanHandler<T> rsh = new BeanHandler<T>(type);
            return runner.query(connection,sql,rsh,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 查询返回多条对象
    public <T> List<T> queryForList(String sql, Class<T> type, Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BeanListHandler<T> rsh = new BeanListHandler<>(type);
            return runner.query(connection,sql,rsh,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 针对聚合函数查询
    public Object queryForSingleValue(String sql, Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            ScalarHandler rsh = new ScalarHandler();
            return runner.query(connection,sql,rsh,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
