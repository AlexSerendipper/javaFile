package usereflection;

import org.junit.Test;

import java.util.Random;

/**
 * 【通过反射创建对应的运行时类的对象1】
 *  调用Class对象的newInstance()
 *   1）实际上调用了类的空参构造器
 *   2）要求 ① 必须提供空参构造器。没有空参构造器会报异常InstantiationException
 *           ② 空参构造器的访问权限要够。空参构造器如果是私有的，权限不够会报异常IllegalAccessException
 *
 * 【通过反射创建对应的运行时类的对象2】
 *  通过Class类的getDeclaredConstructor(Class … parameterTypes)取得本类的指定形参类型的构造器
 *  通过Constructor对象的newInstance()，实现在没有空参构造器的情况下创建对象
 *
 *
 @author Alex
 @create 2022-12-22-10:38
 */


public class UseReflection03 {
    // 通过反射创建对应的运行时类的对象1
    @Test
    public void test() throws InstantiationException, IllegalAccessException {
        Class<Person> class1 = Person.class;
        // newInstance(): 获取对应的运行时类的对象，该方法内部调用了运行时类的空参的构造器
        Person o = class1.newInstance();
        System.out.println(o);
        //
    }

    // ✔✔举例体会反射的动态性，编译时无法确定生成的哪个对象，运行时才知道
    @Test
    public void test1() throws Exception {
        for (int i = 0; i < 50; i++) {
            int num = new Random().nextInt(3);  // 输出0，1，2
            String classPath = "";
            switch (num) {
                case 0:
                    classPath = "java.util.Date";
                    break;
                case 1:
                    classPath = "java.lang.Object";
                    break;
                case 2:
                    classPath =  "usereflection.Person";  // 右键文件copy reference可以复制全路径
                    break;
            }
            Object instance = UseReflection03.getInstance(classPath);
            System.out.println(instance);
        }
    }

    // 此方法创建一个指定类的对象，classPath为指定类的全类名
    public static Object getInstance(String classPath) throws Exception {
        Class class1 = Class.forName(classPath);
        return class1.newInstance();
    }
}
