package usethread;

/**
 * 【创建多线程2： 实现runnable类】
 * (1) 定义子类，实现Runnable接口。
 * (2) 子类中重写Runnable接口中的run方法。
 * (3) 将实现Runnable接口的子类对象作为实际参数传递给Thread类的构造器中。
 * (4) 调用Thread类的start方法：开启线程，调用Runnable子类接口的run方法
 *      要想创建多个进程，可以创建多个实现类对象
 *
 * 【继承方式和实现方式的比较】
 * (1)继承方式将线程代码存放在继承Thread的子类的run方法中。
 *    实现方式将线程代码存放在实现runnable接口的接口类的run方法中
 * (2)实现方式的好处:
 *  避免了单继承的局限性
 *  ✔✔多个线程可以共享同一个接口实现类的对象，实现的方式更适合用来处理多个线程间有共享数据的情况
 *  开发中优先选择实现runnable接口的方式
 *
 * 【线程的分类】
 *  JAVA中线程分为两种，守护线程和用户线程，二者的关系是兔死狗烹，鸟尽弓藏。
 *   如main进程和垃圾回收进程，当main进程结束后，垃圾回收进程也会被关闭
 *
 *
 * @author Alex
 * @create 2022-11-13-14:23
 */

public class UseThread03 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }

        }
    }

    public static void main(String[] args) {
        UseThread03 i = new UseThread03();
        Thread t1 = new Thread(i);
        t1.start();
    }
}