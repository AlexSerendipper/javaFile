package usethread;

/**
 * 【要求】
 * 使用继承Thread的方式创建三个窗口卖票。总票数为100张
 * 利用同步方法解决线程安全问题
 *
 * @author Alex
 * @create 2022-11-13-14:05
 */

public class UseThread08 {
    public static void main(String[] args) {
        window4 w1 = new window4();
        window4 w2 = new window4();
        window4 w3 = new window4();
        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");
        w1.start();
        w2.start();
        w3.start();
    }
}


class window4 extends Thread {
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
        }
    }


    public static synchronized void show() {  // 默认为当前类.class
        if (ticket >= 0) {
            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("当前窗口为： " + Thread.currentThread().getName() + "  当前剩余票数为：" + ticket--);
        } else {
            return;
        }
    }
}


