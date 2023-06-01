package usegeneric;

import org.junit.Test;

import java.util.*;

/**
 * 【为什么要有泛型？】
 *  集合容器类在设计阶段/声明阶段不能确定这个容器到底实际存的是什么类型的对象，所以在JDK1.5之前只能把元素类型设计为Object，
 *    JDK1.5之后使用泛型来解决。因此此时把元素的类型设计成一个参数，这个类型参数叫做泛型。
 *  使用泛型可以有效避免读取出来的对象需要强转的问题（避免了ClassCastException异常）
 *
 * 【泛型概述】
 *  声明：如interface List<T> 和 class GenTest<K,V>
 *   其中，T,K,V不代表值，而是表示类型。这里使用任意字母都可以。常用T表示，是Type的缩写。
 *  实例化：List<String> strList = new ArrayList<String>();
 *   jdk1.7之后，泛型的简化操作新特性之类型推断，new ArrayList<~>，~处的泛型可省略
 *
 * 【自定义泛型结构】类、接口、方法
 *   泛型类的空参构造器如下：public GenericClass(){}。
 *     public GenericClass<E>(){} 这是错误的，因为构造器不需要声明泛型
 *   指明具体泛型后，在集合类中凡是使用到类的泛型的位置（方法、属性、构造器），都只能使用指定的泛型
 *   ✔泛型不同的引用不能相互赋值（见继承性）
 *       尽管在编译时ArrayList<String>和ArrayList<Integer>是两种类型，但是，在运行时只有一个ArrayList被加载到JVM中。
 *   泛型如果不指定，对应的类型均按照Object处理。经验：泛型要么一路都用，要么一路都不要用。
 *   如果泛型结构是一个接口或抽象类，则不可创建泛型类的对象。
 *   泛型的指定中不能使用基本数据类型，需要使用包装类替换
 *   ✔在静态方法中不能使用类的泛型。(因为类的泛型是在实例化时指定的)
 *   异常类不能是泛型的
 *   ✔声明泛型数组时，不能使用new E[]。但是可以：E[] elements = (E[])new Object[capacity];
 *
 * 【父类有泛型，子类可以选择保留泛型也可以选择指定泛型类型】共8种情况，见下方
 *  结论：子类必须是“富二代”，子类除了指定或保留父类的泛型，还可以增加自己的泛型
 *
 @author Alex
 @create 2022-12-12-12:25
 */

public class UseGeneric01<T> {
    /**
     * 泛型不同的引用不能相互赋值
     */
    public static void main(String[] args) {
        // 普通的类是可以把引用赋值给相同的类
        Father f1 = new Father();
        Father f2 = new Father();
        f1 = f2;
        // 泛型不同的引用不能相互赋值
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        // arrayList = arrayList1;  // 报错
    }

    /**
     * 泛型的嵌套
     */
    @Test
    public void test1(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("tom",78);
        map.put("jerry",88);
        map.put("jack",88);
        // 使用泛型能够在编译时进行检查，保证数据的安全；
        // map.put(99,"ss");
        // ✔泛型的嵌套，entrySet返回set类型，set类型中的数据是entry类型，entry中的键值是string和integer类型
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

// 自定义泛型类
class Father<T1, T2> {
    String name;
    T2 id;
    // 属性使用类的泛型
    T1 age;
    // ✔声明数组时，不能使用new E[]。但是可以：E[] elements = (E[])new Object[capacity];
    T1[] ages = (T1[]) new Object[10];

    // 构造器使用类的泛型
    public Father() {
    }

    public Father(String name, T2 id, T1 age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public T1 getAge() {
        return age;
    }

    // 方法使用类的泛型
    public void setAge(T1 age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                '}';
    }
}

// 情况1：子类不保留父类的泛型, 情况特殊，相当于未指定了父类的泛型
// 相当于是class Son1 extends Father<Object,Object>，
class Son1 extends Father {
    public Son1(String name, Object id, Object age) {
        super(name, id, age);
    }
}

//情况2：非常常见✔，子类指明其父类的泛型，这样实例化子类对象就不用指明泛型了
class Son2 extends Father<Integer, Integer> {
    public Son2(String name, Integer id, Integer age) {
        super(name, id, age);
    }
}

//情况3：非常常见✔,子类全部保留父类的泛型，相当于创建子类时仍需指定父类的泛型（如果使用了相关结构）
class Son3<T1, T2> extends Father<T1, T2> {
    public Son3(String name, T2 id, T1 age) {
        super(name, id, age);
    }
}

//情况4：子类部分保留父类的泛型，创建对象时，仍需为未指定的父类泛型指明类型（如果使用了相关结构）
class Son4<T2> extends Father<Integer, T2> {
    public Son4(String name, T2 id, Integer age) {
        super(name, id, age);
    }
}

//情况5：子类需要用到自己的泛型的情况, 相当于未指定了父类的泛型
class Son5<T3, T4> extends Father {
    T3 gender;
    T4 score;

    public Son5(String name, Object id, Object age, T3 gender, T4 score) {
        super(name, id, age);
        this.gender = gender;
        this.score = score;
    }
}

//情况6：子类需要用到自己的泛型的情况, 并且指定了父类的泛型
class Son6<T3, T4> extends Father<Integer, Integer> {
    T3 gender;
    T4 score;

    public Son6(String name, Integer id, Integer age, T3 gender, T4 score) {
        super(name, id, age);
        this.gender = gender;
        this.score = score;
    }
}

//情况7：子类需要额外用到自己的泛型的情况, 子类全部保留父类的泛型
class Son7<T1, T2, T3, T4> extends Father<T1, T2> {
    T3 gender;
    T4 score;

    public Son7(String name, T2 id, T1 age, T3 gender, T4 score) {
        super(name, id, age);
        this.gender = gender;
        this.score = score;
    }
}

//情况8：子类需要用到自己的泛型的情况, 子类部分保留父类的泛型
class Son8<T2, T3, T4> extends Father<Integer, T2> {
    T3 gender;
    T4 score;

    public Son8(String name, T2 id, Integer age, T3 gender, T4 score) {
        super(name, id, age);
        this.gender = gender;
        this.score = score;
    }
}
