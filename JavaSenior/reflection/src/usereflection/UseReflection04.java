package usereflection;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * 【通过反射获取运行时类的完整结构】该操作在实际操作中并不常用
 * 1） 获取属性Field及相关结构
 *  .getFields():获取当前运行时类及其父类中声明为public访问权限的属性
 *  .getDeclaredFields():获取当前运行时类当中的所有属性（不包含父类中声明的属性）
 *     属性包括下述内容
 *   .getModifiers(),获取权限修饰符。默认返回int值,需要调用modifier类的toString静态方法进行转换
 *   .getType(),获取数据类型
 *   .getName(),获取变量名
 * 2） 获取方法及相关结构
 *  .getMethods():获取当前运行时类及其父类中声明为public访问权限的方法
 *  .getDeclaredMethods():获取当前运行时类当中的所有方法（不包含父类中声明的属性）
 *    方法包含下述内容
 *  .getAnnotations(),只能获取RUNTIME类型的注解
 *  .getModifiers(),获取权限修饰符
 *  .getReturnType(),获取返回值类型
 *  .getName(),获取方法名
 *  .getParameterTypes(),获取形参列表
 *  .getExceptionTypes()，获取抛出的异常
 * 3） 获取运行时类的构造器及相关结构
 *  .getConstructors():获取当前运行时类当中声明为public访问权限的方法（不包含父类中声明的属性）
 *  .getDeclaredMethods():获取当前运行时类当中的所有构造器（不包含父类中声明的属性）
 *    构造器包含下述内容
 *  .getModifiers(),获取权限修饰符
 *  .getName(),获取方法名
 *  .getParameterTypes(),获取形参列表
 * 4） 获取运行时类的父类及相关结构
 *   .getSuperclass()：获取的不带泛型的父类
 *   .getGenericSuperclass():获取带泛型的父类、
 *   获取带泛型父类的泛型：调用带泛型的父类.getActualTypeArguments()方法
 * 5） 其他
 *   .getInterfaces();      获取运行时类实现的接口
 *   .getPackage();         获取运行时类所在的包
 *   .getAnnotations;  获取运行时类声明的注解
 *
 @author Alex
 @create 2022-12-22-10:38
 */
public class UseReflection04 {
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

}
