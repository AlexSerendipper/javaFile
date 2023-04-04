package usereflection;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 【修改/调用 运行时类的指定结构（属性、方法、构造器）】
 *  1）调用运行时类的指定属性（首先通过.getFields()获取其属性对象）
 *  public Object get(Object obj) 取得指定对象obj在此Field的属性值。静态属性的调用者为当前类
 *  public void set(Object obj,Object value) 设置指定对象obj在此Field的属性内容。静态属性的调用者为当前类
 *  setAccessible(true)：对于私有权限，必须要将访问权限扩大才能修改
 *  2）调用运行时类的指定方法
 *  Object invoke(Object obj, Object[] args)。① 向方法中传递要设置的obj对象的参数信息。
 *                                             ② invoke方法的返回值，即为调用的方法的返回值,没有返回值为返回null
 *                                             ③ 静态方法的调用者为当前类（或填入null）
 *                                             ④ 原方法形参列表为空，invoke也无需传入形参列表
 *  setAccessible(true)：对于私有方法，必须要将访问权限扩大才能调用
 *  3）调用运行时类中指定的构造器
 *  setAccessible(true)：对于私有构造器，必须要将访问权限扩大才能调用
 *  newInstance(Object[] args):传入形参列表，创建指定构造器的对象
 @author Alex
 @create 2022-12-22-10:38
 */
public class UseReflection05 {
     // 一、调用运行时类的指定属性
    @Test
    public void test() throws Exception {
        Class<Person> clazz = Person.class;
        // 创建运行时类对象
        Person person = clazz.newInstance();
        // 获取指定属性
        Field age = clazz.getField("age");
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);  // 对于私有的权限，必须要将访问权限扩大才能修改
        // 设置属性值
        age.set(clazz, 11);
        name.set(person,"zzj");
        // 获取属性值
        int a = (int) age.get(clazz);  // 静态属性，调用者为当前类
        String n = (String) name.get(person);
        System.out.println(a);
        System.out.println(n);
    }

    // 二、调用运行时类的指定方法
    @Test
    public void test1() throws Exception{
        Class<Person> clazz = Person.class;
        // 创建运行时类对象
        Person person = clazz.newInstance();
        // 获取指定方法:参数1为方法名，参数2为形参列表
        Method showNation = clazz.getDeclaredMethod("showNation", String.class, int.class);
        showNation.setAccessible(true);
        Method getAge = clazz.getMethod("getAge");
        // 调用方法,参数1为调用者，参数2为形参
        // invoke方法的返回值，即为调用的方法的返回值,没有返回值为返回null
        Object o1 = getAge.invoke(person);
        int age = (int) o1;
        Object o = showNation.invoke(person,"中国", 11);
        String nation = (String) o;
        System.out.println(age);
        System.out.println(nation);
        // 如何调用静态方法，调用者为当前类
        System.out.println("*****************");
        Method showDesc = clazz.getDeclaredMethod("showDesc");
        showDesc.setAccessible(true);
        Object o2 = showDesc.invoke(clazz);  // 静态方法的调用者为当前类
        Object o3 = showDesc.invoke(null);  // 因为我们这个方法就是通过clazz.getDeclaredMethod来的，所以不写也罢
        System.out.println(o3);
    }

    // 三、调用运行时类中指定的构造器
    @Test
    public void test2() throws Exception{
        Class<Person> clazz = Person.class;
        // 获取指定的构造器,参数指明构造器的参数列表
        Constructor<Person> declaredConstructor = clazz.getDeclaredConstructor(String.class, int.class);
        declaredConstructor.setAccessible(true);
        // 调用构造器创建运行时对象
        Person hyq = declaredConstructor.newInstance("hyq", 66);
        System.out.println(hyq);
    }
}
