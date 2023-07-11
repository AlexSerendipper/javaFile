package usethread;

/**
 * 【创建多线程1：继承多线程】
 * 步骤：（1）创建一个继承于Thread类的子类
 *      （2）重写Thread类的run()，该线程方法体放在此处
 *      （3）创建子类对象，并调用start方法（此时会开辟一个新线程，然后调用当前线程的run方法）
 *           要想创建多个进程，需要创建多个对象
 *           可以利用Thread的匿名子类的方法创建进程
 *
 * 【thread类的构造器】
 *  Thread()：继承方式。创建新的Thread对象
 *  Thread(String threadname)：继承方式。创建线程并指定线程实例名
 *  Thread(Runnable target)：实现方式。指定创建线程的目标对象，它实现了Runnable接口中的run方法
 *  Thread(Runnable target, String name)：实现方式。创建新的Thread对象并指定线程名
 *
 *  【thread类的常用方法】
 *  void start()                        # 启动线程，并执行对象的run()方法
 *  run()                               # 线程在被调度时执行的操作
 *  String getName()                    # 返回线程的名称
 *  void setName(String name)           # 设置该线程名称
 *  static Thread currentThread()       # ✔返回当前线程。在Thread子类中就是this，通常用于主线程和Runnable实现类
 *  static void yield()                 # 线程让步, 暂停当前正在执行的线程，把执行机会让给优先级相同或更高的线程
 *                                          若队列中没有同优先级的线程，忽略此方法
 *  join()                              # 当某个程序执行流中 调用其他线程的join()方法 时，当前线程将被阻塞，直到join()方法加入的线程执行完，当前线程才会继续执行
 *                                          （底层使用的是wait，也会释放锁）
 *  static void sleep(long millis)：(指定时间:毫秒)            # 令当前活动线程在指定时间段内放弃对CPU控制, 使其他线程有机会被执行,时间到后重新排队。有点像计数器
 *                                                              # 抛出InterruptedException异常
 *  stop()                              # 强制线程生命期结束，不推荐使用
 *  boolean isAlive()                   # 返回boolean，判断线程是否还活着
 *
 * 【线程命名的两种方式】
 * (1) 构造器命名
 * (2) currentThread().setName          # 为当前进程命名
 *
 * 【线程优先级】
 * (1) 高优先级的线程会抢占低优先级线程的cpu资源（并非低优先级就是在高优先级线程之后被调用✔）
 * (2) 线程创建时继承父线程的优先级
 * (3) 线程优先级分为1-10，默认为5
 *    getPriority() ：返回线程优先值
 *    setPriority(int newPriority) ：改变线程的优先级
 *
 * @author Alex
 * @create 2023-1-2-10:51
 */


// 需求：使用三个线程，一个【输出100以内的偶数】，一个【输出100以内的奇数】，一个【输出100以内的质数】
public class UseThread02 {
    public static void main(String[] args) {
        NewThread thread0 = new NewThread("线程零：");  // 命名1：构造器命名
        thread0.start();

        System.out.println("*****************");
        // 匿名子类创建线程，输出100以内的奇数
        new Thread("线程一") {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {

                    try {
                        sleep(500);  // 知识点：sleep方法，有点像计数器，每隔多少毫秒执行
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (i % 2 != 0) {
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                    }
                }
            }
        }.start();


        System.out.println("*****************");
        // 当前线程，即主线程，输出100以内的质数
        Thread.currentThread().setName("主线程："); // 命名2：设置当前进程实例名
        Thread.currentThread().setPriority(10);  // 设置主线程优先级

        Boolean flag=true;
        for (int i = 2; i < 100; i++) {
            for (int j = 2; j < i; j++) {
                if(i%j==0){
                    flag = false;
                }
            }
            if(flag){
                System.out.println(Thread.currentThread().getName() + ":" + i + "  优先级为" + Thread.currentThread().getPriority());
            }
            flag = true;

            if (i == 54) {
                try {
                    thread0.join();  // join方法，执行完join进来的进程后再执行当前进程。。这里即为主线程必须在线程0执行完之后才会执行
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(thread0.isAlive());
    }
}


// 线程零输出100内的偶数
class NewThread extends Thread {
    @Override
    public void run() {
        Thread.currentThread().setName("线程零：");  // ① 设置线程名之当前进程中命名
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }

            if (i % 20 == 0) {
                yield();  // 释放当前线程的执行权（但可能下一刻又被cpu抢到执行权）
            }
        }

    }

    public NewThread() {
        super();
    }

    public NewThread(String name) {
        super(name);
    }
}

