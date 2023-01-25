package useenum;

import org.junit.Test;

import java.util.Arrays;

/**
 * 【枚举类使用情景】类的对象只有有限个，确定个时。。尤其是当需要定义一组常量时，强烈建议使用枚举类
 *   若枚举只有一个对象, 则可以作为一种单例模式的实现方式
 *
 * 【如何自定义枚举类】
 * 1）JDK1.5之前需要自定义枚举类
 *  私有化类的构造器，保证不能在类的外部创建其对象，
 *  对象如果有实例变量(属性)，应该声明为private final，并在构造器中初始化
 *  在类的内部创建枚举类的实例。声明为：public static final
 * 2）✔JDK1.5新增的enum关键字用于定义枚举类
 *  必须在枚举类的首行声明枚举类对象，
 *  枚举类的所有实例必须在枚举类中显式列出(, 分隔 ; 结尾)。列出的实例系统会自动添加public static final修饰
 *  枚举类的构造器只能使用 private 权限修饰符，所以private可省略
 *  使用enum定义的枚举类默认继承了java.lang.Enum类，不能再继承其他类
 *  toString被重写过，默认输出常量名。不需要再重写
 *
 * 【Enum类的主要方法】
 *  values()   # 返回枚举类定义常量的数组。该方法可以很方便地遍历所有的枚举值。
 *  valueOf(String str)   # 返回指定常量名的枚举对象。若输入字符串不是枚举类对象的“名字”。会有运行时异常：IllegalArgumentException。
 *  toString()   # (默认方法)返回当前枚举类对象常量的名称
 *
 * 【实现接口的枚举类】
 *  若需要每个枚举值在调用实现的接口方法呈现相同的行为方式，则只要统一实现该方法即可。
 *  若需要每个枚举值在调用实现的接口方法呈现出不同的行为方式, 则可以让每个枚举值分别来实现该方法
 *
 * @author Alex
 * @create 2022-12-01-17:14
 */
public class UseEnum01 {
    // jdk5.0之前
    @Test
    public void test() {
        Season spring = Season.SPRING;
        System.out.println(spring);
    }

    // jdk5.0之后
    @Test
    public void test1() {
        Season1 spring = Season1.SPRING;
        System.out.println(spring);
        // 打印枚举类的父类
        System.out.println(spring.getClass().getSuperclass());
        // 返回枚举类定义常量的数组
        Season1[] seasons = Season1.values();
        System.out.println(Arrays.toString(seasons));
        // 返回指定常量名的枚举对象
        Season1 winter = Season1.valueOf("WINTER");
        System.out.println(winter);
        // 调用实现的接口
        winter.show();
    }
}


// 自定义枚举类（jdk5.0之前）
class Season {
    // 1. 声明属性，声明属性用private,final修饰
    private final String seasonName;
    private final String seasonDesc;

    // 2. 私有化类的构造器
    private Season(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    // 3. 提供当前枚举类的多个对象
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "冰天雪地");

    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesc='" + seasonDesc + '\'' +
                '}';
    }
}

// 自定义枚举类（jdk5.0之后）
enum Season1 implements Info{
    // 1. 声明枚举类对象
    SPRING("春天", "春暖花开"){
        public void show(){
            System.out.println("这是春天");
        }
    },
    SUMMER("夏天", "夏日炎炎") {
        @Override
        public void show() {
            System.out.println("这是夏天");
        }
    },
    AUTUMN("秋天", "秋高气爽") {
        @Override
        public void show() {
            System.out.println("这是秋天");
        }
    },
    WINTER("冬天", "冰天雪地") {
        @Override
        public void show() {
            System.out.println("这是冬天");
        }
    };

    // 2. 声明属性，声明属性用private,final修饰
    private final String seasonName;
    private final String seasonDesc;

    // 3. 私有化类的构造器
    Season1(String seasonName, String seasonDesc) {  // 可以不用private，默认帮你补上去拉
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }
//    @Override
//    public void show() {
//        System.out.println("这是一个季节");
//    }

}

interface Info{
    void show();
}