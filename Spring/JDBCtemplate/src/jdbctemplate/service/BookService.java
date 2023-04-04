package jdbctemplate.service;

import jdbctemplate.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jdbctemplate.bean.Book;

import java.util.List;

/**
 @author Alex
 @create 2023-02-25-13:24
 */

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    // 添加图书
    public void addBook(Book book) {
        bookDao.add(book);
    }

    // 修改图书
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    // 删除图书
    public void delteBook(String  id) {
        bookDao.delete(id);
    }

    // 查找表中的一条记录
    // 查询表中的记录总数
    public int findCount(){
        return bookDao.selectCount();
    }

    // 查找表中的一条记录
    // 根据id查找书籍
    public Book findOne(String id) {
        return bookDao.findBookInfo(id);
    }

    // 查找表中的多条记录
    // 查找表中所有书籍
    public List<Book> findAll() {
        return bookDao.findAllBook();
    }

    // 批量添加操作
    public void batchAdd(List<Object[]> batchArgs){
        bookDao.batchAddBook(batchArgs);
    }

    // 批量修改操作
    public void batchUpdate(List<Object[]> batchArgs){
        bookDao.batchUpdateBook(batchArgs);
    }

    // 批量删除操作
    public void batchDelete(List<Object[]> batchArgs){
        bookDao.batchDeleteBook(batchArgs);
    }
}
