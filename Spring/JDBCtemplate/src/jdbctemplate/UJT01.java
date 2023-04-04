package jdbctemplate;

import jdbctemplate.bean.Book;
import jdbctemplate.dao.BookDao;
import jdbctemplate.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 【JDBCtemplate】建议使用mybatis
 *  JDBCtemplate是Spring对JDBC是封装，简化了jdbc的操作。更方便的实现对数据库实现增删改查
 *  实际上 学jdbc的时候我们也进行了封装，二者几乎一致
 *
 * 【JDBCtemplate准备工作】
 * （1）首先需要引入相关jar包
 *    spring-jdbc-5.2.6.RELEASE.jar          # 核心包
 *    spring-tx-5.2.6.RELEASE.jar            # 数据库事务相关操作
 *    spring-orm-5.2.6.RELEASE.jar           # 用于与其他模块集成，如使用mybatis操作数据库..
 * （2）配置数据库连接池 DataSource
 * （3）配置 JdbcTemplate 对象，注入 DataSource
 * （4）创建 service 类，创建 dao 类，在 dao 注入 jdbcTemplate 对象（注解方式）
------------------------
 <!-- JdbcTemplate 对象 -->
 <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
     <!--注入数据库连接池dataSource-->
     <property name="dataSource" ref="dataSource"></property>
 </bean>
------------------------
 * 【JDBCtemplate的增删改操作】类似于DBUtils,在druid.properties中不要用username✔，是敏感词汇，可能会报错。
 *  JdbcTemplate.update(String sql, Object...args)                          # 增删改操作
 *
 * 【JDBCtemplate的查询操作】
 *   类似于DBUtils, RowMapper类似于QueryRunner。
 *   RowMapper是接口，使用jdbctemplate提供的实现类BeanPropertyRowMapper           # 可以实现接收单个数据 或 数据集合
 *   new BeanPropertyRowMapper<Book>(Book.class)                               # <输入返回对象>(输入返回对象类)
 * （1）查询返回某个值（聚合函数）
 *  JdbcTemplate.queryForObject(String sql, Class<T> requiredType)                          # 返回表中的一条记录（聚合函数），class输入返回值类型
 * （2）查询返回记录的单个对象
 *  JdbcTemplate.queryForObject(String sql, RowMapper<T> rowMapper, object...args)          # 返回表中的一条记录（单个对象）
 * （3）查询返回记录的集合（List）
 *  JdbcTemplate.query(String sql, RowMapper<T> rowMapper, object...args)                   # 返回表中的多条记录
 *
 * 【DBUtil和 JDBCtemplate的区别】
 *  DBUtil中，查询操作都是用query方法
 *   而JDBCtemplate查询表中集合用的是query方法（而查询返回某个值 和 查询返回单个对象用的是queryForObject方法）
 *  JDBCtemplate实现了对获取连接到增删改查的全面封装，而DBUtil只封装了增删改查操作
 *
 * 【JdbcTemplate实现批量操作】批量的增删改操作
 *  JdbcTemplate.batchUpdate(String sql, List<Object[]>)                # List数组表示要操作的多条记录(底层实际上就是遍历数组，对每一条数据进行相关操作)
 *                                                                       # 该方法返回数据影响的行数
 *                                                                       # ✔注意：List<Object[]>中传入的Object[]为sql语句中需要的参数，是顺序传入，注意参数顺序！！
 @author Alex
 @create 2023-02-25-13:03
 */
public class UJT01 {
    // 测试使用jdbctemplate实现增删改操作
    @Test
    public void testJdbcTemplate() {
        // 添加
        ApplicationContext context = new ClassPathXmlApplicationContext("UJT01.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Book book = new Book("1","java","a");
        bookService.addBook(book);
        System.out.println("****************************");
        // 修改
        Book book1 = new Book("2", "python", "b");
        bookService.updateBook(book1);
        System.out.println("****************************");
        // 删除
        bookService.delteBook("3");
    }

    // 测试使用jdbctemplate实现查询操作
    @Test
    public void test2(){
        // 查询表中记录数
        ApplicationContext context = new ClassPathXmlApplicationContext("UJT01.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println("表中的记录数为" + bookService.findCount());
        System.out.println("****************************");
        // 根据id查询书籍
        Book book = bookService.findOne("1");
        System.out.println(book);
        System.out.println("****************************");
        // 查询返回集合（查询图书列表）
        List<Book> bookList = bookService.findAll();
        for(Book b:bookList){
            System.out.println(b);
        }
    }


    // 批量添加操作
    @Test
    public void test3(){
        ApplicationContext context = new ClassPathXmlApplicationContext("UJT01.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] book1 = {"6","aa","aa"};
        Object[] book2 = {"7","bb","bb"};
        Object[] book3 = {"8","cc","cc"};
        batchArgs.add(book1);
        batchArgs.add(book2);
        batchArgs.add(book3);
        bookService.batchAdd(batchArgs);
    }

    // 批量修改操作（注意传入参数的顺序）
    // 影响的行数有点问题，以后看看怎么回事
    @Test
    public void test4(){
        ApplicationContext context = new ClassPathXmlApplicationContext("UJT01.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] book1 = {"aa1","aa1","3"};
        Object[] book2 = {"bb1","bb1","4"};
        Object[] book3 = {"cc1","cc1","5"};
        batchArgs.add(book1);
        batchArgs.add(book2);
        batchArgs.add(book3);
        bookService.batchUpdate(batchArgs);
    }

    // 批量删除操作（注意传入的参数）
    @Test
    public void test5(){
        ApplicationContext context = new ClassPathXmlApplicationContext("UJT01.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] book1 = {"1"};
        Object[] book2 = {"6"};
        batchArgs.add(book1);
        batchArgs.add(book2);
        bookService.batchDelete(batchArgs);
    }
}
