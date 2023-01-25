package usethread;

/**
 * 例题：使用继承Thread的方式创建三个窗口卖票。总票数为100张
 * 要求：利用同步代码块解决线程安全问题
 *
 * @author Alex
 * @create 2022-11-13-14:05
 */

public class UseThread06 {
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



class window2 extends Thread{
    private static int ticket = 100;
    private static Object obj = new Object();   // 在继承的方式中用同步代码块解决线程安全问题，这里必须static

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


