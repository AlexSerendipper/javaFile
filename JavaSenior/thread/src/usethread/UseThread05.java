package usethread;

import org.junit.Test;


import java.util.concurrent.*;

/**
 * 【创建多线程4：使用线程池新增线程】
 *  目前说来实际中 大多都用线程池技术，因为这部分很多时候都用框架实现了
 *  原理：提前创建好多个线程，放入线程池中，使用时直接获取，使用完后放回池中。可以避免频繁创建销毁、实现重复利用。
 *  ✔✔目前使用最多的两种线程技术为（1）普通线程池 ExecutorService （2）定时任务线程池 ScheduledExecutorService
 *  ✔✔注意：junit测试 不同与 main方法中的线程测试，当junit检测到当前没有线程输出时，会自动停止方法，所以要让当前线程sleep
 *
 * 【线程池属性】
 *  corePoolSize：核心池的大小
 *  maximumPoolSize：最大线程数
 *  keepAliveTime：线程没有任务时最多保持多长时间后会终止
 *
 * 【Executors】工具类、线程池的工厂类，用于创建并返回不同类型的线程池
 *  Executors.newCachedThreadPool()：创建一个可根据需要创建新线程的线程池
 *  Executors.newFixedThreadPool(n)： 创建一个可重用固定线程数的线程池
 *  Executors.newSingleThreadExecutor()：创建一个只有一个线程的线程池
 *  Executors.newScheduledThreadPool(n)：创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行
 *
 * 【普通线程池使用流程】ExecutorService
 * （1）ExecutorService executorService = Executors.newFixedThreadPool(10); # 提供指定数量的线程池
 * （2）                                                            # 设置线程池属性
 * （2）executorService.execute()                                   # 执行指定的线程操作，需要传入实现runnable接口的实现类对象
 * （3）executorService.submit()                                    # 执行指定的线程操作，需要传入实现callable接口的实现类对象
 * （4）executorService.shutdown();                                 # 执行完后可以手动关闭线程池
 *
 * 【定时任务线程池使用流程】ScheduledExecutorService
 * （1）ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);                   # 提供指定数量的线程池
 * （2）scheduledExecutorService.scheduleAtFixedRate(task,delay(ms),period(ms),TimeUnit.MILLISECONDS);             # 以固定的 延迟和频率 执行~ 执行多次（直到执行完线程任务）
 *
 * @author Alex
 * @create 2022-11-19-13:58
 */
public class UseThread05 {
    /**
     * 测试jdk普通线程池
     */
    public static void main(String[] args) {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println(Thread.currentThread().getName() + "-one");
                }
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println(Thread.currentThread().getName() + "-two");
                }
            }
        };
        Callable task3 = new Callable() {
            @Override
            public Object call() throws Exception {
                for (int i = 0; i < 50; i++) {
                    System.out.println(Thread.currentThread().getName() + "-three");
                }
                return null;
            }
        };

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);  // 返回了一个对象，赋值给接口，这里是多态
                                                                                       // 源码这个接口实现类是abs,abs被ThreadPoolExecutor继承
        // 设置线程池属性
        ThreadPoolExecutor service = (ThreadPoolExecutor)executorService;  // 强转
        service.setCorePoolSize(15);

        // 查看线程池的父类
        System.out.println(executorService.getClass());

        executorService.execute(task1);  // 适用于runnable，000
        executorService.execute(task2);  // 适用于runnable，111
        executorService.submit(task3);  // 适用于callable，222

        executorService.shutdown();  // 执行完后可以手动关闭线程池
    }


    /**
     * 测试定时任务线程池
     */
    @Test
    public void test(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "hello~");
            }
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(task,10000,1000,TimeUnit.MILLISECONDS);  // 十秒以后开始执行任务，每秒执行一个输出
        // junit测试 不同与 main方法中的线程测试，当junit检测到当前没有线程输出时，会自动停止方法，所以要让当前线程sleep
        sleep(30000);
    }

    public void sleep(long m){
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
