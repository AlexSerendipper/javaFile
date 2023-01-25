package usethread;

/**
 * 【要求概述】
 * 例题：利用实现类的方式创建三个窗口卖票。总票数为100张
 * 体会：多个线程可以共享同一个接口实现类的对象（不在需要设置属性为static）
 * 需求：使用同步代码块的方式，解决线程安全问题（重票 错票）
 *
 * 【同步问题引入】
 *  Java对于多线程的安全问题提供了专业的解决方式：同步机制
 *  同步：多个线程进入，一个线程工作，其余线程等待
 *  异步：多个线程进入，各个线程各自工作互不影响
 *
 * 【同步代码块】
 *  格式：synchronized(同步监视器){需要被同步的代码}
 *  (1)需要被同步的代码为操作共享数据的代码
 *  (2)同步监视器，俗称锁，任何一个类的对象都可以充当锁，但要求多个线程必须共用同一把锁✔✔
 *  (3)继承方式中常用【当前类.class】作为锁（或在类中定义一个static对象属性作为锁）
 *  (4)实现方式中常用【this】作为锁（或在类中定义一个对象属性作为锁）
 *
 * 【不会释放锁的操作】
 * 1)当前线程的同步方法、同步代码块执行结束
 * 2)当前线程在同步代码块、同步方法中遇到break、return
 * 3)当前线程在同步代码块、同步方法中出现了未处理的Error或Exception
 * 4)当前线程在同步代码块、同步方法中执行了线程对象的wait()方法，当前线程暂停，并释放锁✔
 *
 * 【释放锁的操作】
 * 1)线程执行同步代码块或同步方法时，程序调用Thread.sleep()、Thread.yield()方法暂停当前线程的执行
 * 2)线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程挂起
 *   (实际工作中尽量避免使用suspend()和resume()来控制线程)
 * @author Alex
 * @create 2023-1-3
 */

public class UseThread05 {
    public static void main(String[] args) {
        window3 w = new window3();
        Thread w1 = new Thread(w);   // 因为传入线程构造器的都是同一个window，所以他们就是共享同一个属性（不需要加static）
        Thread w2 = new Thread(w);
        Thread w3 = new Thread(w);

        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");

        w1.start();
        w2.start();
        w3.start();
    }
}


class windows implements Runnable {
    private int ticket = 100;  // 不用static
    Object obj = new Object();  // 同步监视器，俗称锁，任何一个类的对象都可以充当锁，但要求多个线程必须共用同一把锁✔

    @Override
    public void run() {
        // Object obj = new Object();  // 不同的进程必须共用同一把锁，所以不能在此处new obj
        while (true) {
            synchronized (obj) {  // 这里用this最为方便✔因为指向的都是同一个window对象
                if (ticket >= 0) {
                    try {
                        Thread.currentThread().sleep(50);  // 使用sleep后更容易出现线程安全问题，出现重票错票
                        // 因为其让线程阻塞，然后让另一个进程进来操作共享数据，所以出错了
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("当前窗口为： " + Thread.currentThread().getName() + "  当前剩余票数为：" + ticket--);  // 核心代码
                } else {
                    break;
                }
            }
        }

    }
}


