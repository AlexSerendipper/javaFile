package service.impl;

import bean.Book;
import bean.Page;
import dao.BookDao;
import dao.impl.BookDaoImpl;
import service.BookService;

import java.util.List;

/**
 @author Alex
 @create 2023-02-03-15:09
 */
public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }
    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }
    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }
    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }
    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page = new Page<Book>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        // 求总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);
        // 求总页码
        Integer pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal+=1;
        }
        // 设置总页码
        page.setPageTotal(pageTotal);
        // 设置当前页码（引入了数据边境检查，所以必须在设置总页码后设置当前页码）
        page.setPageNo(pageNo);
        // 求当前页数据的开始索引
        int begin = (page.getPageNo() - 1) * pageSize;
        // 求当前页数据
        List<Book> items = bookDao.queryForPageItems(begin,pageSize);
        // 设置当前页数据
        page.setItems(items);
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<Book>();
        // 1、总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount(min,max);
        page.setPageTotalCount(pageTotalCount);
        // 2、总页码
        Integer pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal+=1;
        }
        // 设置总页码
        page.setPageTotal(pageTotal);
        // 设置当前页码（引入了数据边境检查，所以必须在总页码后设置当前页码）
        page.setPageNo(pageNo);
        // 求当前页数据的开始索引
        int begin = (page.getPageNo() - 1) * pageSize;
        // 3、当前页数据
        List<Book> items = bookDao.queryForPageItems(begin,pageSize,min,max);
        // 设置当前页数据
        page.setItems(items);
        return page;
    }

}
