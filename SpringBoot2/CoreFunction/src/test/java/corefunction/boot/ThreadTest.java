package corefunction.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 @author Alex
 @create 2023-04-25-11:09
 */
@SpringBootTest
public class ThreadTest {
    // spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    // spring定时任务线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    ThreadT threadT;
    // 测试普通线程池
    @Test
    public void test1(){
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
        threadPoolTaskExecutor.execute(task1);  // 适用于runnable，000
        threadPoolTaskExecutor.execute(task2);  // 适用于runnable，111
        threadPoolTaskExecutor.submit(task3);  // 适用于callable，222
        sleep(10000);
    }

    // 测试spring定时任务线程池
    @Test
    public void test2(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "hello~");
            }
        };
        Date startTime = new Date(System.currentTimeMillis() + 10000);  // 开始时间，10s后

        threadPoolTaskScheduler.scheduleAtFixedRate(task,startTime,1000);  // 十秒以后开始执行任务，每秒执行一个输出，默认以ms为单位
        // junit测试 不同与 main方法中的线程测试，当junit检测到当前没有线程输出时，会自动停止方法，所以要让当前线程sleep
        sleep(30000);
    }

    // 测试普通线程池的快速调用
    @Test
    public void test3(){
        for (int i = 0; i < 10; i++) {
            // 该方法必须写在另一个类中，写在当前类中均使用的是main方法
            threadT.exe();;
        }
    }

    // 测试定时任务线程池的快速调用
    @Test
    public void test4(){
        sleep(30000);
    }

//    @Async
//    public void exe(){
//        System.out.println(Thread.currentThread().getName());
//        System.out.print("普通线程池的快速调用-");
//
//    }

    @Scheduled(initialDelay = 10000,fixedRate = 1000)
    public void exe2(){
        System.out.println("定时线程池的快速调用");
    }
    public void sleep(long m){
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
