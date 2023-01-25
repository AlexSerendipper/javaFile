package oopexample;

/**
 * 【细节接口排错题，程序是否有问题，如何修改？】
 @author Alex
 @create 2023-01-11-16:06
 */

public class OE01 {
}
interface Playable {
    void play();
}

interface Bounceable {
    void play();
}

interface Rollable extends Playable, Bounceable {
    Ball ball = new Ball("PingPang");
}

class Ball implements Rollable {
    private String name;

    public String getName() {
        return name;
    }

    public Ball(String name) {
        this.name = name;
    }

    public void play() {  // 1.此处并没有错，这认为是对两个接口中的play方法同时进行重写！
        // ball = new Ball("Football");  // 2.此处错误的原因是，直接使用了接口中的对象属性ball，而接口中的
        // 对象属性是省略了public final static的，final无法被修改
        System.out.println(ball.getName());
    }
}