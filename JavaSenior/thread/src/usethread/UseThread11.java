package usethread;


import org.junit.jupiter.api.Test;
/**
 * 【线程通信】例题见Q2
 * (1) 常用方法
 *     wait()：让当前线程等待，同时释放锁(这是和sleep的最大区别)，使别的线程可访问并修改共享资源！
 *     notify()：唤醒正在排队等待同步资源的线程中优先级最高者结束等待
 *     notifyAll ()：唤醒正在排队等待资源的所有线程结束等待
 * (2) 这三个方法只有在synchronized方法或synchronized代码块中才能使用，否则会报java.lang.IllegalMonitorStateException异常。
 * (3) 上述方法的调用者，是同步监视器✔✔✔由于任何对象都可作为同步监视器，所以上述方法定义在object类中
 *
 * @author Alex
 * @create 2022-11-18-16:01
 */
public class UseThread11 {
    /**
     * 使用两个线程，交替打印1-100
     */
    @Test
    public void test1(){
        Number n = new Number();
        Thread t1 = new Thread(n);
        Thread t2 = new Thread(n);
        t1.start();
        t2.start();
    }

    @Test
    public void test2(){
        
    }
}

class Number implements Runnable {
    private int num = 1;
    private Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (obj) {
                obj.notify();  // 必须写在synchronized里面,且调用者为同步监视器
                if (num <= 100) {
                    System.out.println(Thread.currentThread().getName() + ":" + num++);
                    try {
                        obj.wait();  // 让当前线程等待，同时释放锁！
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    break;
                }
            }
        }
    }
}
