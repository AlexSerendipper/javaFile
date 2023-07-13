package kafka;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 【kafka简介】
 *  kafka是一个消息队列框架（java大数据的内容），作为一个消息中间件，用于构建TB级的异步消息系统
 *
 * 【阻塞队列】
 *  阻塞队列作为传统的 消息系统的 解决方法，是java核心包中，自带的api
 *  blockingQueue是阻塞队列提供的用来解决线程通信问题的一个接口
 *  （实际上，线程通信更原始的解决方案是使用object.notify，object.wait的方式，见java下线程通信~）
 *  实现流程：blockingQueue 提供了两个方法，让线程1使用put方法 往阻塞队列中存放数据，让线程1使用take方法 往阻塞队列中取出数据。
 *   当线程1（生产者）往queue中存满数据，这个线程1就阻塞，或者线程2（消费者）把queue中的数据取光了，这个线程2就阻塞。由于阻塞是不会占用资源，
 *   所以这种生产者与消费者的模式，作为一种传统的 消息系统的 解决方法是可行的。
 *  blockingQueue实现类： - ArrayBlockingQueue（底层数组实现）
 *                        - LinkedBlockingQueue（底层是链表实现）
 *                        - PriorityBlockingQueue、SynchronousQueue、DelayQueue等
 *
 * 【阻塞队列的使用】
 *（1）创建生产者与消费者线程，并将阻塞队列传入线程
 *     queue.put(i);                                      # 生产者将产品i交由阻塞队列管理！当队列满了，线程阻塞
 *     queue.take();                                      # 消费者从阻塞队列中取出产品
 *（2）new ArrayBlockingQueue<Integer>(10);                # 创建阻塞队列，设置底层数组长度，让生产者和消费者共用同一个阻塞队列
 *
 @author Alex
 @create 2023-04-14-9:26
 */
public class UK01 {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
        // 创建线程并启动(由于一个生产者 三个消费者，很快就就可以看到线程0被阻塞了 停产了)
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}


class Producer implements Runnable{
    private BlockingQueue<Integer> queue;

    // 将阻塞队列传入线程
    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    // 作为生产者，不断的往阻塞队列中传入数据，每次生产中间都有间隔
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(20);
                queue.put(i);
                System.out.println(Thread.currentThread().getName() + "生成：" + queue.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{
    private BlockingQueue<Integer> queue;

    // 将阻塞队列传入线程
    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    // 作为消费者，不断的往阻塞队列中取出数据，每次取出中间都有间隔(这里间隔随机)
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                while (true){
                    Thread.sleep(new Random().nextInt(1000));
                    queue.take();
                    System.out.println(Thread.currentThread().getName() + "消费：" + queue.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
