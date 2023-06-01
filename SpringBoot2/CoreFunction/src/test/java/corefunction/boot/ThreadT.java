package corefunction.boot;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 @author Alex
 @create 2023-04-25-14:09
 */
@Component
public class ThreadT {
    @Async
    public void exe(){
        System.out.println(Thread.currentThread().getName());
        System.out.print("普通线程池的快速调用-");

    }
}
