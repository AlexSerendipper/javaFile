package corefunction.boot;

import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

/**
 @author Alex
 @create 2023-04-25-15:51
 */
@SpringBootTest
public class QuartzTest {
    @Autowired
    private Scheduler scheduler;

    @Test
    public void test1() throws Exception{
        boolean b = scheduler.deleteJob(new JobKey("jobTest", "jobTest"));
        System.out.println(b);
    }
}
