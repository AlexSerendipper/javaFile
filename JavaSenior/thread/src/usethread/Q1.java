package usethread;


/**
 * 线程通信经典例题
 *  生产者(producer)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，店员一次只能持有固定数量的产品(比如:20），如果生产者试图
 *   生产更多的产品，店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品了再通知消费者来取走产品。
 *  这里可能出现两个问题：
 * (1) 生产者比消费者快时，消费者会漏掉一些数据没有取到。
 * (2) 消费者比生产者快时，消费者会取相同的数据。
 *
 * @author Alex
 * @create 2022-11-19-10:24
 */

public class Q1 {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer producer = new Producer(clerk);
        Customer customer = new Customer(clerk);

        producer.setName("生产者");
        customer.setName("消费者");
        producer.start();
        customer.start();
    }
}

class Producer extends Thread {
    private Clerk clerk;  // 生产者消费者都共用clerk，这是自己写的时候不容易想到的

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始生产...");
        while (true) {
            clerk.produceProduct();
        }

    }
}

class Clerk {  // 店员在这个面向对象中干了啥，主要就是传递数据，所以把所有共享数据都放在店员这里操作
    private int product = 0;

    public synchronized void produceProduct() {  // 这里指向的都是clerk，所以没啥问题
        if (product < 20) {
            System.out.println(Thread.currentThread().getName() + " 开始生产第" + ++product + "个产品");
            notify();  // 只要有生产，就可以唤起消费了
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 消费产品
    public synchronized void consumeProduct() {
        if (product > 0) {
            System.out.println(Thread.currentThread().getName() + " 开始消费第" + product-- + "个产品");

            notify();  // 只要有消费，就可以唤起继续生产了
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Customer extends Thread {
    private Clerk clerk;  // 生产者消费者都共用clerk

    public Customer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始消费...");
        while (true) {
            clerk.consumeProduct();
        }
    }

}