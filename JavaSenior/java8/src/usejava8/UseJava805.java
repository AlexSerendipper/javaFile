package usejava8;

import org.junit.Test;

import java.util.Optional;

/**
 * 【Optional类】总体来说使用不多
 *  java.util.Optional是一个容器类，它可以保存类型T的值，代表这个值存在。或者仅仅保存null，表示这个值不存在。
 *  Optional提供了很多方法，让我们不用显式进行空值检测。可以说optional的出现，就是为了解决程序中总是会出现空指针异常的问题
 *
 * 【常用方法】
 * 1）创建Optional类对象的方法
 *     Optional.of(T t) : 创建一个 Optional 实例，t必须非空；
 *     Optional.empty() : 创建一个空的 Optional 实例
 *     Optional.ofNullable(T t)：t可以为null✔
 * 2）获取Optional容器的对象
 *     T get(): 如果调用对象包含值，返回该值，对象值为null则抛异常✔get方法与of方法搭配使用！！！
 *     T orElse(T other) ：如果有值则将其返回，否则返回指定的other对象✔orElse方法与ofnullable搭配使用！！！
 *     T orElseGet(Supplier<? extends T> other) ：如果有值则将其返回，否则返回由Supplier接口实现提供的对象。
 *     T orElseThrow(Supplier<? extends X> exceptionSupplier) ：如果有值则将其返回，否则抛出由Supplier接口实现提供的异常
 * 3）判断Optional容器中是否包含对象
 *     boolean isPresent() : 判断是否包含对象✔
 *      void ifPresent(Consumer<? super T> consumer) ：如果有值，就执行Consumer接口的实现代码，并且该值会作为参数传给它
 *
 @author Alex
 @create 2022-12-28-18:07
 */
public class UseJava805 {
    //  Optional类使用体验，常用方法测试
    @Test
    public void test1() {
        // get方法与of方法搭配使用，防止空指针异常
        Girl girl = new Girl();
        girl.setName("钟锤翰");
        Optional<Girl> g1 = Optional.of(girl);
        System.out.println("g1.get() = " + g1.get());
        System.out.println("*****************");
        // orElse方法与ofnullable搭配使用，防止空指针异常
        girl = null;
        Optional<Girl> g = Optional.ofNullable(girl);
        Girl g2 = g.orElse(new Girl("迪丽热巴"));
        System.out.println(g2);
        System.out.println("*****************");
        Optional<Object> op = Optional.empty();
        // 判断optional中是否包含数据
        if (!op.isPresent()) {
            System.out.println("数据为空");
        } else {
            System.out.println(op.get());
        }
    }


    // 体会为什么Optional类可以避免空指针的问题
    @Test
    public void test2(){
        // 1. 传统方法很容易出现空指针
        Boy boy = new Boy();
        boy = null;  // 设置Boy为空，且不向boy中传入girl
        // String grilName = getGrilName(boy);
        // System.out.println(grilName);
        System.out.println("*****************");
        // 2. 传统方法优化
        String grilName = getGrilName1(boy);
        System.out.println(grilName);
        System.out.println("*****************");
        // 3. 使用Optional进行优化
        String grilName2 = getGrilName2(boy);
        System.out.println(grilName2);
    }

    // 1. 传统方法
    public String getGrilName(Boy boy){
        return boy.getGirl().getName();
    }

    // 2. 传统方法优化,只能嵌套判断每一层都不是null
    public String getGrilName1(Boy boy){
        if(boy!=null){
            Girl girl = boy.getGirl();
            if(girl!=null){
                return girl.getName();
            }
        }
        return null;
    }

    // 3. 使用optional类进行优化
    public String getGrilName2(Boy boy){
        Optional<Boy> boy1 = Optional.ofNullable(boy);
        Boy boy2 = boy1.orElse(new Boy(new Girl("赵丽颖")));
        // 此时得到的Boy一定是非空，但是girl可能是空
        Girl girl = boy2.getGirl();
        Optional<Girl> girl1 = Optional.ofNullable(girl);
        Girl girl2 = girl1.orElse(new Girl("zzj"));
        return girl2.getName();
    }
}

class Boy {
    private Girl girl;

    public Boy() {
    }

    public Boy(Girl girl) {
        this.girl = girl;
    }

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "girl=" + girl +
                '}';
    }
}

class Girl {
    private String name;

    public Girl(String name) {
        this.name = name;
    }

    public Girl() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "name='" + name + '\'' +
                '}';
    }
}

