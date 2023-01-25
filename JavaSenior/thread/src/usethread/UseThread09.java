package usethread;

/**
 * 【使用同步机制将单例模式中的懒汉式改为线程安全的】
 * @author Alex
 * @create 2022-11-17-9:35
 */

public class UseThread09 {
    public static void main(String[] args) {
        Bank bank1 = Bank.getInstance();
        Bank bank2 = Bank.getInstance();
        System.out.println(bank1==bank2);
    }
}


class Bank {
    private Bank() {
    }

    private static Bank instance = null;

    public static Bank getInstance() {
        // 方式1：效率稍差一点点，因为其他的进程无需进入if判断了
//        synchronized (Bank.class) {
//            if (instance == null) {
//                instance = new Bank();
//            }
//            return instance;
//        }
//    }

        // 方式2:提高效率，这样的话可能前两个线程得等一下，再后来的线程就无需进入If语句了
        if (instance == null) {
            synchronized (Bank.class) {
                instance = new Bank();
            }
        }
        return instance;
    }

}