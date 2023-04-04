package dao;

import bean.Book;

import java.util.List;

/**
 @author Alex
 @create 2023-02-03-14:57
 */
public interface BookDao {
    public int addBook(Book book);
    public int deleteBookById(Integer id);
    public int updateBook(Book book);
    public Book queryBookById(Integer id);
    public List<Book> queryBooks();
    Integer queryForPageTotalCount();
    List<Book> queryForPageItems(int begin, int pageSize);

    Integer queryForPageTotalCount(int min,int max);

    List<Book> queryForPageItems(int begin, int pageSize, int min,int max);

}
