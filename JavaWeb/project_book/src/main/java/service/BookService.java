package service;

import bean.Book;
import bean.Page;

import java.util.List;

/**
 @author Alex
 @create 2023-02-03-15:08
 */
public interface BookService {
    public void addBook(Book book);
    public void deleteBookById(Integer id);
    public void updateBook(Book book);
    public Book queryBookById(Integer id);
    public List<Book> queryBooks();
    Page<Book> page(int pageNo, int pageSize);
    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);
}
