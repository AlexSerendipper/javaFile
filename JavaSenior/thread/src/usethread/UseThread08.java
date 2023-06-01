package usethread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 【Lock锁解决线程安全问题】
 * 【使用格式】
------------------------------------
  class A{
      private final ReentrantLock lock = new ReenTrantLock();  ① 实例化
      public void m(){
         lock.lock();  ② 开锁
  try{
      //保证线程安全的代码;
  }
  finally{
      lock.unlock();  ③ 关锁
  }}}
-------------------------------------
 *
 * 【synchronized 与 Lock 的对比】
 *  (1) Lock是显式锁（手动开启和关闭锁）。synchronized是隐式锁，出了作用域自动释放
 *  (2) Lock只有代码块锁，synchronized有代码块锁和方法锁
 *  (3) 使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。
 *
 *  【推荐使用优先顺序】
 *  Lock  同步代码块（已经进入了方法体，分配了相应资源）同步方法（在方法体之外）
 *
 * @author Alex
 * @create 2022-11-17-10:16
 */
public class UseThread08 {
    public static void main(String[] args) {
        WW w = new WW();
        Thread w1 = new Thread(w);
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


class WW implements Runnable {
    private int ticket = 100;

    // 1. 实例化reentranlock
    private ReentrantLock lock = new ReentrantLock(true);  // true为公平锁，默认false，公平锁为线程1执行后按照其后等待的线程进入，不会出现线程一连续抢到锁的情况

    @Override
    public void run() {
        while (true) {
            try {
                // 2. 调用lock方法，锁定
                lock.lock();
                if (ticket > 0) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + ":售票票号为：" + ticket--);
                } else {
                    break;
                }
            } finally {
                // 3. 调用解锁方法
                lock.unlock();
            }
        }
    }
}