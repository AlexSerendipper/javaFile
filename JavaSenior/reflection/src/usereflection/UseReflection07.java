package usereflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 【面向切面编程体验(了解)】AOP(aspect orient programming)
 *  其实就是如何像静态代理一样，让代理也能干一些除了被代理者的事之外的，自己来干的事
 *
 @author Alex
 @create 2022-12-28-17:14
 */
public class UseReflection07 {
    public static void main(String[] args) {
        // 动态代理+aop
        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        ClothFactory proxy = (ClothFactory) ProxyFactory1.getProxyInstance(nikeClothFactory);
        proxy.produceCloth();
    }
}

class HumanUtil{
    public void method1(){
        System.out.println("=======通用方法1=======");
    }
    public void method2(){
        System.out.println("=======通用方法2=======");
    }
}



class ProxyFactory1{
    public static Object getProxyInstance(Object obj){  // obj为被代理类的对象
        MyInvocationHandler1 handler = new MyInvocationHandler1(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),handler);
    }
}

class MyInvocationHandler1 implements InvocationHandler {
    private Object obj;
    public MyInvocationHandler1(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // ✔✔AOP的核心点就在这，加入那些通用的结构
        HumanUtil humanUtil = new HumanUtil();
        humanUtil.method1();
        Object value = method.invoke(obj, args);
        humanUtil.method2();
        return value;
    }
}