package usethread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 【创建多线程3：实现callable接口】了解即可
 *  相比run()方法，可以有返回值
 *  方法可以抛出异常
 *  支持泛型的返回值
 *  需要借助FutureTask类，比如获取返回结果
 *
 * 【使用流程】
 *（1）创建一个实现了Callable接口的实现类
 *（2）实现类实现Callable中的抽象方法
 *（3）创建实现类的对象
 *（4）创建futuretask类，传入实现了Callable接口的实现类对象
 *（4）将futuretask类作为参数传递到Thread类的构造器中，并调用start方法
 *（5）调用futureTask.get()，获取callable中的返回值
 *
 * @author Alex
 * @create 2022-11-19-11:03
 */
public class UseThread04 implements Callable {
    @Override
    public Object call() throws Exception {  // 可以抛异常，可以有返回值
        // 遍历100以内偶数，并返回偶数的和
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
                sum = sum + i;
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        UseThread04 u = new UseThread04();

        FutureTask futureTask = new FutureTask(u);

        new Thread(futureTask).start();

        try {
            Object sum = futureTask.get();  // get方法的返回值为futuretask构造器参数callable实现类重写的call方法的返回值
            System.out.println("总和为" + sum);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
