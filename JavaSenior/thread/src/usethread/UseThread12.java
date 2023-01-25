package usethread;

/**
 * 需求：银行有一个账户。
 * 有两个储户分别向同一个账户存3000元，每次存1000，存3次。每次存完打印账户余额。
 * 问题：该程序是否有安全问题，如果有，如何解决？
 * 【提示】
 * 1，明确哪些代码是多线程运行代码，须写入run()方法
 * 2，明确什么是共享数据。
 * 3，明确多线程运行代码中哪些语句是操作共享数据的。
 * 拓展问题：可否实现两个储户交替存钱的操作
 *
 * @author Alex
 * @create 2022-11-17-10:44
 */
public class UseThread12 {

    public static void main(String[] args) {
        Account acc = new Account();
        customer p1 = new customer(acc);
        customer p2 = new customer(acc);

        p1.setName("甲");
        p2.setName("乙");

        p1.start();
        p2.start();

    }
}


class Account {
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}


class customer extends Thread{
    public Account account;

    public customer(Account account) {  // 知识点：共用同一个账户最巧妙的方式
        this.account = account;
    }

    public synchronized void deposit (int amt){  // 所以存钱处存在线程安全问题，当两个人存完钱后休息了。然后直接输出了2000
        try {  // 这里一定要加静态，因为这里的this指代customer，所以并不是同一把锁
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        account.setBalance(account.getBalance() + amt);
        System.out.println(currentThread().getName()+"存钱成功，当前余额为：" + account.getBalance());
    }

    @Override  // 存钱操作
    public void run() {
        for (int i=0;i<3;i++) {
//            System.out.println("当前余额为：" + account.getBalance());
            deposit(1000);
        }
    }
}

