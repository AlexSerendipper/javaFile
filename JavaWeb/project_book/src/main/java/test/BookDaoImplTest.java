package test;

import bean.Book;
import dao.BookDao;
import dao.impl.BookDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 @author Alex
 @create 2023-02-03-15:05
 */
public class BookDaoImplTest {
    private BookDao bookDao = new BookDaoImpl();
    @Test
    public void addBook() {
        bookDao.addBook(new Book(null,"小钟钟为什么这么帅！", "191125", new
                BigDecimal(9999),1100000,0,null
        ));
    }
    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(21);
    }
    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(21,"大家都可以这么帅！", "国哥", new
                BigDecimal(9999),1100000,0,null
        ));
    }
    @Test
    public void queryBookById() {
        System.out.println( bookDao.queryBookById(21) );
    }
    @Test
    public void queryBooks() {
        for (Book queryBook : bookDao.queryBooks()) {
            System.out.println(queryBook);
        }
    }
}