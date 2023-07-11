package servlet;

/**
 * 【ThreadLocal】工具类。实现在同一个线程下的操作都可以共享同一个数据，map实现见US13
 *  ThreadLocal 可以解决多线程的数据安全问题。即通过可以给当前线程关联一个数据（普通变量、对象、数组、集合等），避免了其他线程访问该数据。
 *               具有线程隔离的特性，即每个线程变量都是独立的，不会互相影响。
 *  ThreadLocal底层原理：实际上，可以在ThreadLocal底层的get、set源码中看到，它是创建了当前线程的一个map，然后向map中存值，从而实现了数据的线程隔离
 *    所以ThreadLocal关联的数据可以像Map一样存取，key为当前线程✔
 *    并且每个ThreadLocal对象，只能为当前线程关联一个数据，如果要为当前线程关联多个数据，就需要使用多个ThreadLocal对象实例✔
 *
 *  每个ThreadLocal对象实例定义的时候，一般都是static类型
 *  ThreadLocal中保存数据，在线程销毁后。会由 JVM 虚拟自动释放。
 *   ✔✔✔通常浏览器关闭，线程销毁，所以Threadlocal实际上代替了session域的功能（session域的存取对象也是线程隔离的，但是敏感数据不适合存储在session中）
 *
 * 【ThreadLocal使用流程】
 *  ThreadLocal<Object> threadLocal = new ThreadLocal<>();               # 创建threadlocal实现类, 与map不同的是,threadlocal默认传入了key为当前线程，所以只需要传入value的类型
 *  threadLocal.set(i);                                                  # 存数据时，也只需要传入值即可
 *  threadLocal.get();                                                   # 取数据时，不需要传入参数，因为默认当前线程名
 *
 * 【ThreadLocal最常用在数据库的事务管理】见JdbcUtils
 *  Connection conn = JdbcUtils.getConnection()                          # 从数据库连接池中获取连接，但是每次执行sql语句后都会将连接放回。
 *                                                                           但是我们想让一系列jbdc操作（如结算分为：保存订单、保存订单项、更新商品）使用同一个connect。通常的做法就是使用threadlocal
 *  threadLocal.set(connection);                                         # 在执行一系列jdbc操作时 使用threadLocal.get()中的连接对象（之前保存的）
 *                                                                          确保所有的操作都使用同一个连接对象（即确保所有操作都在同一个线程中完成）
 *                                                                          这样当我们回滚时，就会回滚到jdbc操作之初，即实现了要么成功、要么回滚
 *
 * 【使用filter统一给所有的Servlet方法都加上try-catch】project_book中TransactionFilter.java
 *  <url-pattern>/*</url-pattern>                                        # 为filter配置拦截路径
 *  配置filter重写do filter方法
 *      try {
 *          filterChain.doFilter(servletRequest,servletResponse);
 *          JdbcUtils.commitAndClose();// 提交事务
 *      } catch (Exception e) {
 *          JdbcUtils.rollbackAndClose();//回滚事务
 *          e.printStackTrace();
 *      }
 *  因为通常一个servlet程序就对应了一个功能，所以可以对所有资源都进行try catch一下
 *   而访问其他资源的时候，提交事务并不产生实际效果
 *   仅servlet程序中提交事务有效✔
 *
 * 【将所有异常都统一交给Tomcat展示】
--------------------------------------------------
 <!--error-page 标签配置，服务器出错之后，自动跳转的页面-->
 <error-page>
 <!--error-code 是错误类型-->
 <error-code>404</error-code>
 <!--location 标签表示。要跳转去的页面路径-->
 <location>/pages/error/error404.jsp</location>
 </error-page>
 --------------------------------------------------
 @author Alex
 @create 2023-02-17-14:26
 */

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * 用ThreadLocal存取数据，实现与map存取相同的功能，key为当前线程
 * 实现只要是同一个线程下的操作，都可以共享同一个数据
 */
public class US14 {
    //    public static Map<String,Object> data = new Hashtable<String,Object>();
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();
    private static Random random = new Random();

    static class Task implements Runnable {
        @Override
        public void run() {
            // 在 Run 方法中，随机生成一个变量（线程要关联的数据），然后以当前线程名为 key 保存到 map 中
            Integer i = random.nextInt(1000);
            // 获取当前线程名
            String name = Thread.currentThread().getName();
            System.out.println("线程[" + name + "]生成的随机数是：" + i);
//            data.put(name,i);;
            threadLocal.set(i);
            // 模拟操作，同一个线程中的操作，能否取出在当前线程中保存的数据？
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 进行其他操作，均属于统一线程
            new UserService().createOrder();

            // 在 Run 方法结束之前，以当前线程名获取出数据并打印。查看是否可以取出操作
//            Object o = data.get(name);
            Object o = threadLocal.get();
            System.out.println("在线程[" + name + "]快结束时取出关联的数据是：" + o);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Task()).start();
        }
    }
}


class UserService {
    public void createOrder() {
        String name = Thread.currentThread().getName();
        System.out.println("UserService 当前线程[" + name + "]中保存的数据是：" + US14.threadLocal.get());
        new UserDao().saveOrder();
    }
}

class UserDao {
    public void saveOrder() {
        String name = Thread.currentThread().getName();
        System.out.println("UserDao 当前线程[" + name + "]中保存的数据是：" +  US14.threadLocal.get());
    }
}

