package usethread;
/**
 * 【演示线程的死锁问题】
 * @author Alex
 * @create 2022-11-17-9:55
 */
public class UseThread10 {
    public static void main(String[] args) {
        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();
        new Thread(){ // 匿名实现类1,继承
            @Override
            public void run() {
                synchronized (s1){  // 线程一进来先拿着s1这个锁
                    s1.append("a");
                    s2.append("1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (s2){  // 线程一之后需要拿s2这个锁
                        s1.append("b");
                        s2.append("2");
                        System.out.println(s1);
                        System.out.println(s2);
                    }
                }
            }
        }.start();

        new Thread(new Runnable() {  // 匿名实现类2，实现式
            @Override
            public void run() {
                synchronized (s2){  // 线程二进来先拿s2这个锁
                    s1.append("c");
                    s2.append("3");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }                   // 这时上面的线程拿着s1的锁，这里的线程拿着s2的锁，出现了死锁的情况
                                        // 死锁程序不会异常，就尬在这里了

                    synchronized (s1){  // 线程二之后需要拿s1这个锁
                        s1.append("d");
                        s2.append("4");
                        System.out.println(s1);
                        System.out.println(s2);
                    }
                }
            }
        }).start();
    }
}


