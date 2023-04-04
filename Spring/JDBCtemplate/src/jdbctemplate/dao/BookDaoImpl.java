package jdbctemplate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jdbctemplate.bean.Book;

import java.util.Arrays;
import java.util.List;


/**
 @author Alex
 @create 2023-02-25-13:24
 */

@Repository
public class BookDaoImpl implements BookDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //添加的方法
    @Override
    public void add(Book book){
        //1 创建 sql 语句
        String sql = "insert into jdbctemplate_book values(?,?,?)";
        //2 调用方法实现
        Object[] args = {book.getUserId(), book.getUsername(),book.getUstatus()};
        int update = jdbcTemplate.update(sql,args);
        //3 返回影响的行数
        System.out.println("影响的行数为:" + update);
    }

    // 修改
    @Override
    public void updateBook(Book book) {
        String sql = "update jdbctemplate_book set username=?,ustatus=? where user_id=?";
        Object[] args = {book.getUsername(), book.getUstatus(),book.getUserId()};
        int update = jdbcTemplate.update(sql, args);
        System.out.println("影响的行数为:" + update);
    }

    // 删除
    @Override
    public void delete(String id) {
        String sql = "delete from jdbctemplate_book where user_id=?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println("影响的行数为:" + update);
    }

    //查询表记录数（一条记录）
    @Override
    public int selectCount() {
        String sql = "select count(*) from jdbctemplate_book";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    // 查询表中的一个对象（一条记录）
    // 根据id查找书籍
    @Override
    public Book findBookInfo(String id) {
        String sql = "select * from jdbctemplate_book where user_id=?";
        //调用方法
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }

    // 查询表中的多条记录
    // 查询表中所有书籍
    @Override
    public List<Book> findAllBook() {
        String sql = "select * from jdbctemplate_book";
        //调用方法
        List<Book> bookList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        return bookList;
    }

    // 批量添加操作
    @Override
    public void batchAddBook(List<Object[]> batchArgs) {
        String sql = "insert into jdbctemplate_book values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println("影响的行数是" + Arrays.toString(ints));
    }

    // 批量修改
    @Override
    public void batchUpdateBook(List<Object[]> batchArgs) {
        String sql = "update jdbctemplate_book set username=?,ustatus=? where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println("影响的行数是" + Arrays.toString(ints));
    }

    // 批量删除
    @Override
    public void batchDeleteBook(List<Object[]> batchArgs) {
        String sql = "delete from jdbctemplate_book where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println("影响的行数是" + Arrays.toString(ints));
    }
}
