package usereflection;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Random;

/**
 * 【通过反射创建对应的 运行时类(Class) 的对象】
 *  Class.newInstance()
 *    实际上调用了类的空参构造器
 *    要求 (1) 必须提供空参构造器。没有空参构造器会报异常InstantiationException (2) 空参构造器的访问权限要够。空参构造器如果是私有的，权限不够会报异常IllegalAccessException
 *  Class.getDeclaredConstructor(Class … parameterTypes)                # 取得本类的指定形参类型的构造器
 *  Constructor.newInstance()                                           # 实现在没有空参构造器的情况下创建对象
 *
 * 【通过反射获取 运行时类(Class) 的完整结构】
 * 1） 获取属性Field及相关结构
 *     Class.getFields()                                                # 获取当前运行时类及其父类中声明为public访问权限的属性
 *     Class.getDeclaredFields()                                        # 获取当前运行时类当中的所有属性（不包含父类中声明的属性）
 *    属性含下述内容
 *     Field.getModifiers()                                             # 获取权限修饰符。默认返回int值,需要调用modifier类的toString静态方法进行转换
 *     Field.getType()                                                  # 获取数据类型
 *     Field.getName()                                                  # 获取变量名
 * 2） 获取方法及相关结构
 *     Class.getMethods()                                               # 获取当前运行时类及其父类中声明为public访问权限的方法
 *     Class.getDeclaredMethods()                                       # 获取当前运行时类当中的所有方法（不包含父类中声明的属性）
 *    方法含下述内容
 *     Method.getAnnotations()                                          # 只能获取RUNTIME类型的注解
 *     Method.getModifiers(),                                           # 获取权限修饰符
 *     Method.getReturnType()                                           # 获取返回值类型
 *     Method.getName()                                                 # 获取方法名
 *     Method.getParameterTypes()                                       # 获取形参列表
 *     Method.getExceptionTypes()                                       # 获取抛出的异常
 * 3） 获取运行时类的构造器及相关结构
 *     Class.getConstructors()                                         # 获取当前运行时类当中声明为public访问权限的方法（不包含父类中声明的属性）
 *     Class.getDeclaredMethods()                                      # 获取当前运行时类当中的所有构造器（不包含父类中声明的属性）
 *    构造器包含下述内容
 *     Constructor.getModifiers()                                      # 获取权限修饰符
 *     Constructor.getName()                                           # 获取方法名
 *     Constructor.getParameterTypes()                                 # 获取形参列表
 * 4） 获取运行时类的父类及相关结构
 *     Class.getSuperclass()                                           # 获取的不带泛型的父类
 *     Class superClass = Class.getGenericSuperclass()                 # 获取带泛型的父类、
 *     superClass.getActualTypeArguments()                             # 获取带泛型父类 的泛型
 * 5） 其他
 *     Class.getInterfaces();                                          # 获取运行时类实现的接口
 *     Class.getPackage();                                             # 获取运行时类所在的包
 *     Class.getAnnotations;                                           # 获取运行时类声明的注解
 *
 * 【修改 运行时类的 指定结构（属性、方法、构造器）】首先需要 获取运行时类(Class) 的完整结构
 *  ✔✔✔（非静态属性/方法 的调用者为运行时类(Class) 的对象，静态属性/方法 的调用者为当前运行时类）
 *  1）调用运行时类的指定属性
 *     Field.get(Object obj)                                  # 获取属性调用者obj的Field值。
 *     Field.set(Object obj,Object value)                     # 设置属性调用者obj的Field值。
 *     Field.setAccessible(true)：                            # 对于私有权限，必须要将访问权限扩大才能修改
 *  2）调用运行时类的指定方法
 *     Method.invoke(Object obj, Object[] args)。             # 触发方法，传入方法调用者obj，以及方法形参args
 *                                                               原方法形参列表为空，invoke也无需传入形参列表
 *                                                               invoke方法的返回值，即为调用的方法的返回值,没有返回值为返回null
 *     setAccessible(true)：                                  # 对于私有方法，必须要将访问权限扩大才能调用
 *  3）调用运行时类中指定的构造器
 *     newInstance(Object[] args):                            # 传入形参列表，创建指定构造器的对象
 *     setAccessible(true)：                                  # 对于私有构造器，必须要将访问权限扩大才能调用
 *
 @author Alex
 @create 2022-12-22-10:38
 */
public class UseReflection03 {
    // ✔✔举例体会反射的动态性，编译时无法确定生成的哪个对象，运行时才知道
    @Test
    public void tt() throws Exception {
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
            Object instance = getInstance(classPath);
            System.out.println(instance);
        }
    }

    // 此方法创建一个指定类的对象，classPath为指定类的全类名
    public static Object getInstance(String classPath) throws Exception {
        Class class1 = Class.forName(classPath);
        return class1.newInstance();
    }


    //  一、获取属性
    @Test
    public void test() {
        Class clazz = Person.class;
        Field[] fields = clazz.getFields();  // 获取所有Public属性
        for (Field f : fields) {
            System.out.println(f);
        }
        System.out.println("*****************");
        Field[] declaredFields = clazz.getDeclaredFields();  // 获取所有属性
        for (Field f : declaredFields) {
            System.out.println(f);
        }
        System.out.println("*****************");
        for (Field f : declaredFields) {
            // 获取权限修饰符,默认返回int值,需要调用modifier类的toString方法进行转换
            int modifiers = f.getModifiers();
            System.out.println("Modifier.toString(modifiers) = " + Modifier.toString(modifiers));
            // 获取数据类型,默认为保留java.lang.String,用以区分自定义的String类
            Class<?> type = f.getType();
            System.out.println("type = " + type.getName());
            // 获取变量名
            System.out.println("f.getName() = " + f.getName());
        }
    }

    // 二、✔获取运行时类的获取方法及相关结构
    @Test
    public void test1() {
        Class clazz = Person.class;
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            System.out.println(m);
        }
        System.out.println("*****************");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m);
        }
        System.out.println("*****************");
        for (Method m : declaredMethods) {
            // 1 获取注解
            Annotation[] annotations = m.getAnnotations();
            for (Annotation a : annotations) {
                System.out.println(a + " ");
            }
            // 2 获取权限修饰符
            int modifiers = m.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            // 3 返回值类型
            Class returnType = m.getReturnType();
            System.out.print(returnType.getName() + " ");
            // 4 返回方法名
            System.out.print(m.getName());
            // 5 形参列表
            System.out.print("(");
            Class[] parameterTypes = m.getParameterTypes();
            if (parameterTypes != null && parameterTypes.length != 0) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i == parameterTypes.length - 1) {
                        System.out.print(parameterTypes[i].getName());
                        break;
                    }
                    System.out.print(parameterTypes[i].getName() + ",");
                }
            }
            System.out.print(")" + " ");

            // 6 抛出的异常
            Class[] exceptionTypes = m.getExceptionTypes();
            if (exceptionTypes != null && exceptionTypes.length != 0) {
                System.out.print("throws ");
                for (int i = 0; i < exceptionTypes.length; i++) {
                    if (i == exceptionTypes.length - 1) {
                        System.out.print(exceptionTypes[i].getName());
                        break;
                    }
                    System.out.print(exceptionTypes[i].getName() + ",");
                }
            }
            System.out.println();
        }

    }

    // 三、获取运行时类的构造器结构
    @Test
    public void test2(){
        Class clazz = Person.class;
        Constructor[] constructors = clazz.getConstructors();
        for(Constructor c:constructors){
            System.out.println(c);
        }
        System.out.println("*****************");
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for(Constructor c:declaredConstructors){
            System.out.println(c);
        }
    }

    // 四、✔获取运行时类的父类
    // .getSuperclass()：获取的不带泛型的父类
    // .getGenericSuperclass():获取带泛型的父类、
    // 获取带泛型父类的泛型：调用带泛型的父类.getActualTypeArguments()方法
    @Test
    public void test3(){
        Class clazz = Person.class;
        // 获取父类
        Class superclass = clazz.getSuperclass();
        System.out.println(superclass);
        // 获取带泛型的父类
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);
        // 获取带泛型父类的泛型
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = paramType.getActualTypeArguments();
        System.out.println(actualTypeArguments[0].getTypeName());
        System.out.println(((Class)actualTypeArguments[0]).getName());
    }

    // 五、其他功能
    // .getInterfaces();      获取运行时类实现的接口
    // .getPackage();         获取运行时类所在的包
    // .getAnnotations;  获取运行时类声明的注解
    @Test
    public void test4(){
        Class clazz = Person.class;
        Class[] interfaces = clazz.getInterfaces();
        for(Class i:interfaces){
            System.out.println(i.getName());
        }
        System.out.println("*****************");
        Package aPackage = clazz.getPackage();
        System.out.println(aPackage);
        System.out.println("*****************");
        Annotation[] annotations = clazz.getAnnotations();
        for(Annotation a:annotations){
            System.out.println(a);
        }

    }


    // 一、调用运行时类的指定属性
    @Test
    public void test5() throws Exception {
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
    public void test6() throws Exception{
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
    public void test7() throws Exception{
        Class<Person> clazz = Person.class;
        // 获取指定的构造器,参数指明构造器的参数列表
        Constructor<Person> declaredConstructor = clazz.getDeclaredConstructor(String.class, int.class);
        declaredConstructor.setAccessible(true);
        // 调用构造器创建运行时对象
        Person hyq = declaredConstructor.newInstance("hyq", 66);
        System.out.println(hyq);
    }
}
