package useabnormal;

/**
 * 【判断输出】
 @author Alex
 @create 2023-01-11-17:28
 */
public class Q1 {
    public static void main(String[] args) {
        try {
            methodA();  // 知识点：通常都是将throw等同于return，在返回前先输出finally
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        methodB();
    }

    static void methodA() {
        try {
            System.out.println("进入方法A");
            throw new RuntimeException("异常A");
        } finally {
            System.out.println("A方法的finally");
        }
    }

    static void methodB() {
        try {
            System.out.println("进入方法B");  // 没有异常也可以用try catch, 保证try中如果出现了运行时异常，也能够执行finally
            return;
        } finally {
            System.out.println("调用B方法的finally");
        }
    }
}
