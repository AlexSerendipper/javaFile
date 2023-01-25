package usethread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 【使用线程池新增线程】
 * 目前说来实际中多用线程池，因为这部分很多时候都用框架实现了
 * 原理：提前创建好多个线程，放入线程池中，使用时直接获取，使用完后放回池中。可以避免频繁创建销毁、实现重复利用。
 *
 * 【线程池属性】
 * corePoolSize：核心池的大小
 * maximumPoolSize：最大线程数
 * keepAliveTime：线程没有任务时最多保持多长时间后会终止
 *
 * 【Executors】工具类、线程池的工厂类，用于创建并返回不同类型的线程池
 *  Executors.newCachedThreadPool()：创建一个可根据需要创建新线程的线程池
 *  Executors.newFixedThreadPool(n)： 创建一个可重用固定线程数的线程池
 *  Executors.newSingleThreadExecutor()：创建一个只有一个线程的线程池
 *  Executors.newScheduledThreadPool(n)：创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行
 *
 * 【使用流程】
 * （1）ExecutorService executorService = Executors.newFixedThreadPool(10); # 提供指定数量的线程池
 * （2）                                                            # 设置线程池属性
 * （2）executorService.execute()                                   # 执行指定的线程操作，需要传入实现runnable接口的实现类对象
 * （3）executorService.submit()                                    # 执行指定的线程操作，需要传入实现callable接口的实现类对象
 * （4）executorService.shutdown();                                 # 执行完后可以手动关闭线程池
 * @author Alex
 * @create 2022-11-19-13:58
 */
public class UseThread15 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);  // 返回了一个对象，赋值给接口，这里是多态
                                                                                       // 源码这个接口实现类是abs,abs被ThreadPoolExecutor继承

        // 设置线程池属性
        ThreadPoolExecutor service = (ThreadPoolExecutor)executorService;  // 强转
        service.setCorePoolSize(15);

        // 查看线程池的父类
        System.out.println(executorService.getClass());


        executorService.execute(new nThread());  // 适用于runnable
        executorService.execute(new nThread2());  // 适用于runnable
        executorService.submit(new nThread3());  // 适用于callable

        executorService.shutdown();  // 执行完后可以手动关闭线程池

    }
}


class nThread implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i < 50; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}

class nThread2 implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i < 50; i++) {
            if (i % 2 != 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}

class nThread3 implements Callable {
    @Override
    public Object call() throws Exception {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName() + ":" + "lighter and princess");
        }
        return null;
    }
}