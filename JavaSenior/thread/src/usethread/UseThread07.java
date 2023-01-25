package usethread;

/**
 * 【要求】
 * 利用实现类的方式创建三个窗口卖票。总票数为100张
 * 使用同步方法，解决线程安全问题
 *
 * 【同步方法】
 *  将synchronized声明在方法中，表示整个方法为同步方法
 *
 *  【同步方法默认锁】
 *  对于非静态的同步方法，默认锁为this，即适用于用实现的方式多线程
 *  对于静态的同步方法，默认锁为当前类.class，即适用于用继承的方式多线程
 *
 * 【使用情景】
 * 当操作共享数据的代码完整的声明在一个方法中，不妨设该方法为同步方法
 * @author Alex
 * @create 2022-11-13-14:05
 */

public class UseThread07 {
    public static void main(String[] args) {
        window3 w = new window3();
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


class window3 implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {  // 若直接给run加上synchronized，则会出现一个窗口卖票的情况，相当于一个进程走进去然后循环完了把入场凭证给下一进程
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



