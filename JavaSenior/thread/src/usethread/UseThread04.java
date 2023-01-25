package usethread;

/**
 * 【要求概述】
 * 例题：使用继承Thread的方式创建三个窗口卖票。总票数为100张
 * 体会：1）多个线程之间无法处理同一份资源，除非将其属性设置为静态！
 *      2）当多个线程操作共享数据时，一个线程还没有执行完，另一个线程参与进来执行。导致共享数据的错误。
 *
 * 【同步问题引入】
 * Java对于多线程的安全问题提供了专业的解决方式：同步机制
 * 同步：多个线程进入，一个线程工作，其余线程等待
 * 异步：多个线程进入，各个线程各自工作互不影响
 *
 * @author Alex
 * @create 2022-1-3
 */

public class UseThread04 {
    public static void main(String[] args) {
        window w1 = new window();
        window w2 = new window();
        window w3 = new window();
        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");
        w1.start();
        w2.start();
        w3.start();
    }
}

class window extends Thread{
    private static int ticket = 100;

    @Override
    public void run() {
        while (true){
            if(ticket>=0){
                System.out.println("当前窗口为： " + getName() + "  当前剩余票数为：" + ticket--);
            }else{
                break;
            }
        }
    }
}


