package threadexample;

/**
 *
 *
 * 一. synchronized and lock的异同
 * 同：二者都能够解决线程安全问题
 * 异：synchronized机制是再执行完相应的代码后自动的释放同步监视器，lock需要手动的启动同步，手动的结束同步
 *
 * 二. 解决线程安全的方式有几种？4种
 *
 * 三. sleep和wait的异同
 * 同：一旦执行方法，都可以让当前线程进入阻塞状态
 * 异：1）声明位置不同：thread类中声明sleep(),object类中声明wait()
 *     2)调用要求不同：sleep()可以在任何需要的场景下调用，wait()必须要同步代码块和同步方法中调用
 *     3）如果两个方法都在同步代码块和同步方法中,sleep不会释放同步锁，wait会释放锁
 *
 * 四. 如何理解实现callable接口的方式比实现runnable接口的方式更强大?
 *     1) 可以有返回值
 *     2）可以抛出异常，被外面的操作捕获
 *     3）callable支持泛型
 *
 *
 * @author Alex
 * @create 2022-11-17-10:34
 */





public class ThreadExample01 {
}
