package usethread;

/**
 * 【同步问题引入】
 *  要求概述：使用实现Thread的方式创建三个窗口卖票。总票数为100张
 *   1）多个线程可以共享同一个接口实现类的对象（不需要设置属性为static）
 *   2）当多个线程操作共享数据时，一个线程还没有执行完，另一个线程参与进来执行。导致共享数据的错误，即为同步问题。
 *
 * 【同步方法】
 *  将synchronized声明在方法中，表示整个方法为同步方法
 *  ✔对于非静态的同步方法，默认锁为this，即适用于用实现Thread的方式实现多线程
 *  ✔对于静态的同步方法，默认锁为当前类.class，即适用于用继承Thread的方式实现多线程
 *  使用情景：当操作共享数据的代码完整的声明在一个方法中，不妨设该方法为同步方法
 *
 * 【释放锁的操作】
 * 1) 当前线程的同步方法、同步代码块执行结束
 * 2) 当前线程在同步代码块、同步方法中遇到break、return
 * 3) 当前线程在同步代码块、同步方法中出现了未处理的Error或Exception
 * 4) 当前线程在同步代码块、同步方法中执行了线程对象的wait()方法，当前线程暂停，并释放锁✔
 *
 * 【不会释放锁的操作】
 * 1) 线程执行同步代码块或同步方法时，程序调用Thread.sleep()、Thread.yield()方法暂停当前线程的执行
 * 2) 线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程挂起
 *    (实际工作中尽量避免使用suspend()和resume()来控制线程)
 *
 * 【sleep和wait的异同】
 * 同：一旦执行方法，都可以让当前线程进入阻塞状态
 * 异：1）声明位置不同：thread类中声明sleep(),object类中声明wait()
 *     2)调用要求不同：sleep()可以在任何需要的场景下调用，wait()必须要同步代码块和同步方法中调用
 *     3）如果两个方法都在同步代码块和同步方法中,sleep不会释放同步锁，wait会释放锁
 *
 *
 * @author Alex
 * @create 2023-1-3
 */

public class UseThread07 {
    public static void main(String[] args) {
        windows w = new windows();
        Thread w1 = new Thread(w);  // 因为传入线程构造器的都是同一个window，所以他们就是共享同一个属性（不需要加static）
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
    public void run() {  // 若直接给run加上synchronized，则会出现一个窗口卖票的情况，相当于一个进程走进去然后循环完了把入场凭证给下一进程
        // Object obj = new Object();  // 不同的进程必须共用同一把锁，所以不能在此处new obj
        while (true) {
            show();
        }
    }

    public synchronized void show() {  // 默认锁为this
        if (ticket >= 0) {
            try {
                Thread.currentThread().sleep(50);  // 使用sleep后会容易出现线程安全问题
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("当前窗口为： " + Thread.currentThread().getName() + "  当前剩余票数为：" + ticket--);
        }else{
            return;
        }
    }
}


