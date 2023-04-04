package servlet;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * 【map】
 *  某些时候我们需要实现，在同一个线程下的操作都可以共享同一个数据
 *  使用map实现如下，使用ThreadLocal见US14
 @author Alex
 @create 2023-02-17-12:08
 */


/**
 * 用map存取数据，key为当前线程名
 * 可以实现只要是同一个线程下的操作，都可以共享同一个数据
 */
public class US13 {
    public static Map<String,Object> data = new Hashtable<String,Object>();
    private static Random random = new Random();
    // 内部类
    static class Task implements Runnable {
        @Override
        public void run() {
            // 在 Run 方法中，随机生成一个变量（线程要关联的数据），然后以当前线程名为 key 保存到 map 中
            Integer i = random.nextInt(1000);
            // 获取当前线程名
            String name = Thread.currentThread().getName();
            System.out.println("线程["+name+"]生成的随机数是：" + i);
            data.put(name,i);;

            // 模拟操作，同一个线程中的操作，能否取出在当前线程中保存的数据？
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 进行其他操作，均属于统一线程
            new OrderService().createOrder();

            // 在 Run 方法结束之前，以当前线程名获取出数据并打印。查看是否可以取出操作
            Object o = data.get(name);
            System.out.println("在线程["+name+"]快结束时取出关联的数据是：" + o);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++){
            new Thread(new Task()).start();
        }
    }
}



class OrderService {
    public void createOrder(){
        String name = Thread.currentThread().getName();
        System.out.println("OrderService 当前线程[" + name + "]中保存的数据是：" + US13.data.get(name));
        new OrderDao().saveOrder();
    }
}
class OrderDao {
    public void saveOrder(){
        String name = Thread.currentThread().getName();
        System.out.println("OrderDao 当前线程[" + name + "]中保存的数据是：" + US13.data.get(name));
    }
}
