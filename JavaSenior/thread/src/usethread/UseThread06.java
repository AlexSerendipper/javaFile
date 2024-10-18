package usethread;

/**
 * 【同步问题引入】
 *  要求概述：使用继承Thread的方式创建三个窗口卖票。总票数为100张
 *   1）多个线程之间无法处理同一份资源，除非将其属性设置为静态！✔✔
 *   2）
 *
 * 【同步代码块】
 *  Java对于多线程的安全问题提供了专业的解决方式：同步机制
 *    同步：多个线程进入，一个线程工作，其余线程等待
 *    异步：多个线程进入，各个线程各自工作互不影响
 *  同步问题最常见的两种解决方式：同步代码块 和 同步方法
 *
 * 【同步代码块】
 *  格式：synchronized(同步监视器){需要被同步的代码}
 *  (1) 需要被同步的代码为操作共享数据的代码
 *  (2) 同步监视器，俗称锁，任何一个类的对象都可以充当锁，但要求多个线程必须共用同一把锁✔
 *  (3) 继承Thread方式中常用【当前类.class】作为锁（或在类中定义一个static对象属性作为锁）✔
 *  (4) 实现runnable方式中常用【this】作为锁（或在类中定义一个对象属性作为锁）✔
 *
 *
 * @author Alex
 * @create 2022-1-3
 */

public class UseThread06 {
    // 利用同步代码块解决线程安全问题
    public static void main(String[] args) {
        window2 w1 = new window2();
        window2 w2 = new window2();
        window2 w3 = new window2();
        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");
        w1.start();
        w2.start();
        w3.start();
    }
}


/**
 * 利用同步代码块解决线程安全问题
 */
class window2 extends Thread{
    private static int ticket = 100;  // 只有将该属性设置为静态，多个线程才能共享同一份数据
    private static Object obj = new Object();  // 在继承的方式中用同步代码块解决线程安全问题，这里必须static

    @Override
    public void run() {
        while (true){
            synchronized (window2.class){  // 用Obj或者类名.class作为锁，才能指向同一把锁
                if(ticket>=0){
                    try {
                        Thread.currentThread().sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("当前窗口为： " + getName() + "  当前剩余票数为：" + ticket--);  // 核心代码
                }else{
                    break;
                }
            }
        }
    }

}




