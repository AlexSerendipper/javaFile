package mutilThread;

/**
 * 【threadLocal的底层原理】使用了弱引用
 *  javaWeb中，简单叙述了 threadLocal 的底层原理，即它是创建了当前线程的一个map（key为当前线程），然后向map中存值，从而实现了数据的线程隔离
 *   实际上，key不是简单的指向当前线程！
 *   threadLocal.set() 后，底层维护了一个 ThreadLocalMap，key为弱引用指向当前ThreadLocal（即指向当前线程）
 *   这里使用弱引用的原因是，当我们不想使用这个 ThreadLocal，垃圾回收可以顺利的清除掉ThreadLocal对象（弱引用关联的对象会被自动垃圾回收），从而不发生内存泄漏的问题
 *  问题1：线程复用会产生脏数据，由于线程池会复用 Thread 对象，因此与 Thread 绑定的 ThreadLocal 也会被重用。
 *          如果没有调用 remove 清理与线程相关的 ThreadLocal 信息，那么假如下一个线程没有调用 set 设置初始值就可能 get 到重用的线程信息。
 *  问题2：垃圾回收后，threadLocal对象被顺利回收，而ThreadLocalMap的key变为空值
 *          这条没有用的记录中，value值可能很大（即我们存放的内容），可能也会产生内存泄漏
 *          我们想让这条记录也在清除ThreadLocal后被删掉，因此需要及时调用 remove 方法进行清理操作。
 *
 * 【lock锁】
 *  java提供的两种最常见的锁，一个是synchronized锁，一个就是lock锁
 *   而lock锁中，最重要的就是 ReentrantLock锁 和ReadWriteLock
 *  ReentrantLock锁 和 synchronized 一样，是一个互斥锁，可以让多线程在执行期间，只有一个线程在执行指定的代码
 *      区别1： synchronized是非公平的，ReentrantLock在默认情况下是非公平的，可以通过构造方法指定公平锁。
 *             （一旦使用了公平锁，性能会急剧下降，影响吞吐量。。故推荐优先使用非公平锁）
 *             （公平锁指多个线程在等待同一个锁时，必须按照申请锁的顺序来依次获得锁，而非公平锁不保证这一点，在锁被释放时，任何线程都有机会获得锁）
 *      区别2：ReentrantLoc锁具有等待可中断的特性，持有锁的线程长期不释放锁时，正在等待的线程可以选择放弃等待而处理其他事情。。。而synchronized是不可中断的
 *      区别3：锁绑定多个条件，一个 ReentrantLock可以同时绑定多个 Condition（多次调用 newCondition 创建多个条件）
 *            synchronized 中锁对象的wait跟notify可以实现一个隐含条件，如果要和多个条件关联就不得不额外添加锁
 *
 * 【AQS】
 *  AQS介绍：AQS 就是 AbstractQueuedSynchronizer，多线程同步器
 *   lock锁中，sync()方法就是继承了AQS类。
 *   AQS 提供了两种锁机制，分别是排它锁，和共享锁。 排它锁，就是存在多线程竞争同一共享资源时，同一时刻只允许一个线程访问该 共享资源，也就是多个线程中只能有一个线程获得锁资源，比如 Lock 中的 ReentrantLock 重入锁实现就是用到了 AQS 中的排它锁功能。
 *   共享锁也称为读锁，就是在同一时刻允许多个线程同时获得锁资源，比如 CountDownLatch 和 Semaphore 都是用到了 AQS 中的共享锁功能。
 *  AQS类的属性
 *  (1) AQS内部有一个内部类Node(next,prev,thread属性)，由多个node组成双向链表
 *  (2) AQS类有三个属性，Node head, Node tail, int state
 *  锁的实现机制：如ReentrantLock锁想要获得锁资源，就需要基于CAS的方式，将AQS的state属性值加1（默认为0）
 *               初始化时，state = 0，当一个线程A，拿到了锁资源，state改为1
 *               此时其他线程B，想要获取锁资源时，无法获取锁，会将其信息存储在node双向链表中排队(线程信息存储在thread属性中)
 *               （如果线程 A又请求锁，是不需要排队的，否则就直接死锁了，作为可重入锁，state继续加1即可）
 *
 *               若为公平锁，只有线程A把此锁全部释放了，状态值减到0了。然后才会通知队列唤醒线程B，使B可以再次竞争锁。如果线程B后面还有线 C，线程C继续休眠，直到B执行完了，通知了线程C。
 *               若为非公平锁，唤醒线程B的过程中，来了一个线程 D，那么线程 D 是有可能获取到锁的，如果线程 D 获取到了锁，线程 B 就只能继续等待休眠了。
 *
 @author Alex
 @create 2023-06-30-14:27
 */
public class UM05 {
}
