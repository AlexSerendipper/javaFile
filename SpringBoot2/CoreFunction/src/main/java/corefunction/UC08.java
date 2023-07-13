package corefunction;

import corefunction.boot.ThreadT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 【Spring多线程】类比于jdk提供的线程池
 * （1）spring.properties配置，配置后，spring将自动初始化好线程池，并将其放入容器中进行管理
*   从其配置上不难看出，spring提供的线程池，比jdk提供的线程池要更加灵活
-----------------
# TaskExecutionProperties普通线程
# 默认调用五个线程
spring.task.execution.pool.core-size=5
# 当线程不够用时，自动扩容，最多扩容到15
spring.task.execution.pool.max-size=15
# 当扩容到最大线程数量时，线程仍然不够用时，会把任务先放到队列中
spring.task.execution.pool.queue-capacity=100

# TaskSchedulingProperties定时任务线程
spring.task.scheduling.pool.size=5
-----------------
 * （2）创建配置类，让spring定时任务线程生效
-----------------
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {}
-----------------
 * （3） 自动装配后，用户与jdk线程池几乎一致
 *   threadPoolTaskExecutor.execute()                                   # 执行指定的线程操作，需要传入实现runnable接口的实现类对象
 *   threadPoolTaskExecutor.submit()                                    # 执行指定的线程操作，需要传入实现callable接口的实现类对象
 *   threadPoolTaskExecutor.shutdown();                                 # 执行完后可以手动关闭线程池
 *   scheduledExecutorService.scheduleAtFixedRate(task,new Date(System.currentTimeMillis() + 10000),period(ms));             # 以固定的 延迟（需要传入Date类型数据）和频率 执行~ 执行多次（直到执行完线程任务）
-----------------
@Autowired
private ThreadPoolTaskExecutor threadPoolTaskExecutor;
// spring定时任务线程池
@Autowired
private ThreadPoolTaskScheduler threadPoolTaskScheduler;
-----------------
 *
 * 【spring 线程池简化使用方式】
 *   @Async                 # 普通线程池的快速调用：在某方法上使用@Async注解，当调用该方法时，会由多个线程异步执行
 *   @Scheduled(initialDelay = 10000,fixedRate = 1000)         # 定时任务线程池的快速调用，使用该注解，指定延迟时间和频率，当该方法的组件被装配时，该方法自动被调用！！
 *
 * 【分布式多线程】
 *  当服务器采用分布式部署时，每个服务器上都有（controller 以及 scheduler），当服务器启动，若采用传统的多线程的方式scheduler自动运行，将可能产生冲突。
 *  使用Quartz代替scheduler可以解决上述问题，jdk以及spring的定时任务组件是基于内存的，因为各个服务器 内存之间数据不共享（配置scheduler多久运行一次，配置参数也是存储在内存中），
 *   所以无法解决分布式部署时存在的问题。而Quartz定时任务的配置参数是存储在数据库中，各个服务器共享同一个数据库，由此解决了上述问题
 *  quartz底层的四大接口
 *    Scheduler             # quartz核心调度接口，所有由quartz调度的接口都是由该接口去调度的
 *    Job                   # 定义任务
 *    JobDetail             # 配置任务（对应qrtz_job_details表）
 *    trigger               # 触发器，配置job什么时候运行，以什么频率运行（对应qrtz_triggers表）
 *   以上这些配置信息在首次启动后存储在数据库中~
 *                            # qrtz_scheduler_status表，存储了定时器的状态信息
 *                            # qrtz_locks表，锁名，当多个quartz访问数据表，以设定的锁名对数据表进行加锁
 *
 * 【quartz的使用】
 * （1）引入依赖
-----------
<!--quartz核心包-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
-----------
 * (2) 定义任务
 *  org.quartz.Job          # 创建类实现该接口，重写其execute方法为其指定任务
 *
 * (3) 配置任务(根据配置的内容将配置信息存储在数据表中，然后quartz底层的调度器scheduler就会根据配置信息进行调度)（同样的，当组件被装配时，定时任务线程池自动被调用！！）
--------------
@Configuration
public class QuartzConfig {
    // 通常BeanFactory是整个IOC容器的顶层接口
    // 而FactoryBean作用是可简化bean的实例化过程：
    // 1. FactoryBean 中封装了某些bean的实例化过程
    // 2. 将 FactoryBean 装配到spring容器中后， 将 FactoryBean 注入给其他的bean，则该bean得到的是FactoryBean所管理的对象实例


    // 配置 JobDetail
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean JobDetail = new JobDetailFactoryBean();
        JobDetail.setJobClass(Job.class);     // 管理的job
        JobDetail.setName("jobTest");         // 为job取名
        JobDetail.setGroup("jobTest");        // 为job设定一个组（多个Job可以在同一个组中）
        JobDetail.setDurability(true);        // job是否永久保存（job被废弃后，相关信息仍保存）
        JobDetail.setRequestsRecovery(true);  // job出现异常，是否可恢复
        return JobDetail;
    }


    // 配置 Trigger
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetail jobDetailFactoryBean){  // 如此处，注入的并不是JobDetailFactoryBean对象，而是其所管理的JobDetail对象
        SimpleTriggerFactoryBean Trigger = new SimpleTriggerFactoryBean();
        Trigger.setJobDetail(jobDetailFactoryBean);   // 指定为哪个job设定的触发器
        Trigger.setName("jobTestTrigger");            // 为当前trigger取名
        Trigger.setGroup("jobTestGroup");             // 为当前trigger设定一个组
        Trigger.setRepeatInterval(3000);              // 触发器执行的时间，多长时间触发一次
        Trigger.setJobDataMap(new JobDataMap());      // 使用JobDataMap存储触发器的运行状态
        return Trigger;
    }
}
--------------
* (4) application.properties配置（匹配后quartz才会将信息存储到数据库中）
--------------
# QuartzProperties
# 任务底层的存储方式
spring.quartz.job-store-type=jdbc
# 任务调度器scheduler的名字
spring.quartz.scheduler-name=communityScheduler
# 任务调度器scheduler的ID（auto自动生成）
spring.quartz.properties.org.quartz.scheduler.instanceId=AUTO
# 任务配置信息存储到数据表中时，使用哪个类作为存储介质
spring.quartz.properties.org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
# 任务配置信息存储到数据表中时，使用的驱动
spring.quartz.properties.org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
# 任务配置信息存储到数据表中时，是否使用集群的方式
spring.quartz.properties.org.quartz.jobStore.isClustered=true
# 底层使用哪种线程池
spring.quartz.properties.org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
# 底层使用几个线程池
spring.quartz.properties.org.quartz.threadPool.threadCount=5
--------------
 * (5) 如何删除表中job : 当数据表中存在数据后，quartz将根据表中配置运行，可以根据scheduler删除表中job以及trigger的相关数据
 *   boolean b = scheduler.deleteJob(new JobKey("jobTest", "jobTest"));    # 自动装配scheduler后，根据要删除job的Name以及GroupName确定唯一的Job(以JobKey的形式)，返回删除结果
 *
 @author Alex
 @create 2023-04-25-10:55
 */
public class UC08 {
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




