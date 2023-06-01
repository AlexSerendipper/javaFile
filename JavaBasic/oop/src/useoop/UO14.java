package useoop;

import org.junit.Test;

/**
 * 【为什么要有接口】interface
 *  一方面，有时必须从几个类中派生出一个子类，继承它们所有的属性和方法。但是，Java不支持多重继承。
 *  另一方面，有时必须从几个类中抽取出一些共同的行为特征，而它们之间又没有is-a的关系，仅仅是具有相同的行为特征而已。
 *   例如：鼠标、键盘、打印机、扫描仪、摄像头、充电器、MP3机、手机、数码相机、移动硬盘等都支持USB连接
 *  所以说，接口实际上是指定了一组契约，标准，规范。接口实现则是 "能不能"的关系。而继承主要体现的是"是不是"的关系，
 *
 * 【interface】
 *  接口(interface)是抽象方法和常量值定义的集合。
 *  用interface来标记，先写extends，后写implements。如class SubClass extends SuperClass implements InterfaceA{ }
 *  接口中的所有成员变量都默认是由 public static final 修饰的。
 *  接口中的所有抽象方法都默认是由 public abstract 修饰的。
 *  接口中没有构造器。
 *  ✔接口采用多继承机制。一个类可以实现多个接口，接口也可以继承其它接口。
 *  实现接口的类中必须提供接口中所有方法的具体实现内容，方可实例化。否则，仍需定义为抽象类
 *  与继承关系类似，接口与实现类之间存在多态性
 *  如果抽象类和接口都可以使用的话，优先使用接口，因为避免单继承的局限
 *  在声明接口实现类时直接对其方法重写，称为接口的匿名实现类
 *
 * 【Java 8中关于接口的改进】随着JDK的发展，接口越来越像类
 *  可以为接口添加静态方法。使用 static 关键字修饰。可以通过接口直接调用静态方法，并执行其方法体。
 *  可以为接口添加默认方法。使用 default 关键字修饰。可以通过实现类对象来调用。也可以被实现类重写。
 *
 * 【代理设计模式】代理设计模式就是为对象提供代理，通过代理以控制对这个对象的访问。
 *                通常代理类和被代理类需要实现同一接口
 *
 * 【工厂设计模式】
 *
 *
 @author Alex
 @create 2023-01-10-19:17
 */
public class UO14 {
    // 一、体会接口的多态性
    @Test
    public void test1() {
        transferData(new Flash());
    }

    public void transferData(USB u) {  // 电脑通过USB传输数据，但是USB接口不能造对象，所以传入实现类，体现多态性
        u.start();
        System.out.println("开始传输数据....");
        u.end();
    }

    // 定义了USB的规范~~~长宽高等
    interface USB {
        void start();

        void end();
    }

    class Flash implements USB {
        @Override
        public void start() {
            System.out.println("flash开始工作");
        }

        @Override
        public void end() {
            System.out.println("flash结束工作");
        }
    }









    // 二、静态代理举例
    @Test
    public void test2(){
        NikeClothFactory nike = new NikeClothFactory();
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(nike);
        proxyClothFactory.produceCloth();
    }

    /**
     * 代理类与被代理类实现同一套接口
     */
    interface ClothFactory {
        void produceCloth();
    }

    // 代理工厂
    class ProxyClothFactory implements ClothFactory {
        private ClothFactory factory;

        public ProxyClothFactory(ClothFactory factory) {  // 用被代理对象进行实例化
            this.factory = factory;
        }

        @Override
        public void produceCloth() {
            System.out.println("代理工厂工作..");
            factory.produceCloth();
            System.out.println("代理工厂后续工作...");
        }
    }

    // 被代理工厂
    class NikeClothFactory implements ClothFactory {
        @Override
        public void produceCloth() {
            System.out.println("nike工厂生产一批运动服");
        }
    }

}



