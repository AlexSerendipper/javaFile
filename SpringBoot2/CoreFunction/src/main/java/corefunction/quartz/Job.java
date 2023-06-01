package corefunction.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 @author Alex
 @create 2023-04-25-15:11
 */
public class Job implements org.quartz.Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + "-Job");
    }
}
