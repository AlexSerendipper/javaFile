package jdbctemplate.dao;

import jdbctemplate.bean.Book;

import java.util.List;

/**
 @author Alex
 @create 2023-02-25-13:24
 */
public interface BookDao {
    //添加的方法
    void add(Book book);

    // 修改
    void updateBook(Book book);

    // 删除
    void delete(String id);

    //查询表记录数
    int selectCount();

    // 查询表中的一个对象（一条记录）
    // 根据id查找书籍
    Book findBookInfo(String id);

    //查询返回集合
    // 查询所有书籍
    List<Book> findAllBook();

    // 批量添加操作
    void batchAddBook(List<Object[]> batchArgs);

    // 批量修改
    void batchUpdateBook(List<Object[]> batchArgs);

    // 批量删除
    void batchDeleteBook(List<Object[]> batchArgs);
}
