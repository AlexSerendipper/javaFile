package usereflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 【静态代理复习】
 *   什么是代理模式：使用一个代理将对象包装起来, 然后用该代理对象取代原始对象。任何对原
 *    始对象的调用都要通过代理。代理对象决定是否以及何时将方法调用转到原始对象上。
 *   静态代理的问题：（1）静态代理中，代理类和目标对象的类都是在编译期间确定下来，不利于程序的扩展。
 *                   （2）同时，每一个代理类只能为一个接口服务，这样一来程序开发中必然产生过多的代理
 *   静态代理举例（静态代理中，代理类和被代理类通常实现同一套接口）：
 *      class MyThread implements Runable{}  // 相当于被代理类
 *      class Thread implements Runable{}  // 相当于代理类
 *      main{
 *          MyThread t = new MyThread();
 *          Thread thread = new Thread(t);
 *          thread.start()  // 启动线程，实际上其中调用了MyThread中的run方法
 *      }
 *
 * 【反射的应用：动态代理】
 *   动态代理的动态性就体现在编译期间不需要固定的代理类，根据被代理对象生成代理类
 *   动态代理使用场合:
 *     调试
 *     远程方法调用
 *
 * 【要想实现动态代理，需要解决的问题】
 * （1）如何根据加载到内存中的代理类，动态的创建一个代理类及其对象
 * （2）当通过代理类的对象调用方法时，如何动态的去调用被代理类中的同名方法
 *
 * 【动态代理的实现】该部分仅作了解，Spring框架中已高度集成
 *  1）✔创建一个实现接口InvocationHandler的类：实现传入 被代理类，方法以及形参列表
 *        实现当代理类被调用时，动态的去调用被代理类中的同名方法（解决问题2）
 *     public Object invoke(Object theProxy, Method method, Object[] params)
 *          Object value = method.invoke(targetObj, params);
 *          return value
 *       }
 *  2）通过Proxy的静态方法：实现传入的参数为被代理的类加载器，被代理实现的接口，InvocationHandler实例，返回一个动态代理对象
 *     这个动态代理对象能实现与传入的 被代理类完全相同功能（解决问题1）
 *     Proxy.newProxyInstance(ClassLoader loader, Class[] interfaces, InvocationHandler h)        #
 *
 @author Alex
 @create 2022-12-22-10:38
 */
public class UseReflection04 {
    public static void main(String[] args) {
        // 静态代理举例，在编译期间就确定好了代理工厂
        NikeClothFactory nike = new NikeClothFactory();
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(nike);
        proxyClothFactory.produceCloth();
        System.out.println("*****************");
        // 动态性就体现在编译期间不需要固定的代理类，根据被代理对象生成代理类
        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        ClothFactory proxyInstance1 = (ClothFactory) ProxyFactory.getProxyInstance(nikeClothFactory);  // 都实现了相同的接口，所以肯定可以强转为该接口的实现类
        proxyInstance1.produceCloth();
    }
}

// 动态代理工厂，生成动态代理类
// 如何根据加载到内存中的代理类，动态的创建一个代理类及其对象。调用Proxy.newProxyInstance
// 传入的参数为被代理的类加载器，被代理实现的接口，InvocationHandler实例
class ProxyFactory {
    public static Object getProxyInstance(Object obj) {  // obj为被代理类的对象
        MyInvocationHandler handler = new MyInvocationHandler(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}

// 如何通过代理类的对象调用方法时，如何动态的去调用被代理类中的同名方法
// 通过InvocationHandler实例，当我们通过代理类的对象，调用方法a时，就会自动的调用如下的invoke方法
class MyInvocationHandler implements InvocationHandler {
    // 所以，需要传入被代理类，让被代理类在invoke中执行同名方法
    private Object obj;

    public MyInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 执行被代理类中的方法
        // 注意被代理类方法可能会有返回值
        Object value = method.invoke(obj, args);
        return value;
    }
}

interface ClothFactory{
    void produceCloth();
}

// 代理工厂
class ProxyClothFactory implements ClothFactory{
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
class NikeClothFactory implements ClothFactory{
    @Override
    public void produceCloth() {
        System.out.println("nike工厂生产一批运动服");
    }
}