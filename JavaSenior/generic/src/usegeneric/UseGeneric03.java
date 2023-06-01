package usegeneric;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 【泛型在继承方面的体现】
 *   泛型不同的引用不能相互赋值。虽然类A是类B的父类，G<A>与G<B>二者不具备子父类关系
 *   由于不存在多态性，泛型在方法的通用性角度来说是比较差
 *
 * 【通配符】为解决泛型中通用性差的问题，引入了通配符
 *   List<?>是List<String>、List<Object>等各种泛型List的父类
 *   读取List<?>正常。因为不管list的真实类型是什么，它包含的都是Object。
 *   写入List<?>错误。因为我们不知道c的元素类型，我们不能向其中添加对象。唯一的例外是null，它是所有类型的成员。
 *   注意点1：编译错误：通配符不能用在泛型方法声明上
 *   注意点2：编译错误：通配符不能用在泛型类的声明上
 *   注意点3：编译错误：通配符不能用在创建对象上
 *
 * 【有限制条件的通配符的使用】
 *    <? extends Human>，即<=，作为（-∞，Human]的父类
 *    <? super Human>，即>=，作为[Human,+∞）的父类
 *    <? extends Comparable>。只允许泛型为实现Comparable接口的实现类的引用调用
 @author Alex
 @create 2022-12-12-19:19
 */
public class UseGeneric03 {
    // 泛型在继承方面的体现
    @Test
    public void test1() {
        // 具备子父类关系的类存在多态性（子类的对象赋给父类的引用）
        Object[] arr1 = null;
        String[] arr2 = null;
        arr1 = arr2;

        // 泛型不同的引用不能相互赋值，故不存在多态性
        List<Object> list = null;
        List<String> list1 = null;
        // List = List1;  // 编译不通过
        // 由于不存在多态性，泛型在方法的通用性角度来说是比较差的
        // show(list1);  // 不存在多态性，编译不通过

        // 知识点2：类A是类B的父类，A<G>和B<G>仍然存在着子父类关系，存在多态性！！！！！！！！！
        List<Object> list2 = new ArrayList<>();  // 存在多态性，编译通过
        show1(list2);
    }

    public static void show1(List<Object> list) {
    }

    // 通配符的使用
    @Test
    public void test2() {
        List<Object> list1 = null;
        List<String> list2 = new ArrayList<>();
        // 一. 由于G(A)和G(B)不存在子父类关系，所以使用G(?)作为一个通用父类[-∞，+∞]
        List<?> list3 = null;
        list3 = list1;
        list3 = list2;
        // 二. 通配符解决了，泛型在方法的通用性较差的问题，但是这和不使用泛型有什么区别
        list2.add("aa");
        list2.add("bb");
        list2.add("cc");
        show2(list2);
        // 三.无法向G(?)中添加任何数据，除了添加null;
        // 可以读G(?)中的数据，读取的数据类型为object
        list3 = list2;
        // list3.add("aa");
        list3.add(null);
        System.out.println("list1.get(1) = " + list3.get(3));
    }

    public static void show2(List<?> list) {
        Iterator<?> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // 有限制条件的通配符的使用
    @Test
    public void test3() {
        List<? extends Human> list1 = null;
        List<? super Human> list2 = null;
        List<Object> list3 = null;
        List<Human> list4 = new ArrayList<>();
        List<Teacher> list5 = new ArrayList<>();
        // extends看成小于等于。即list1为（-∞，Human]的父类
        // list1 = list3;
        list1 = list4;
        list1 = list5;
        // super看成大于等于。作为[Human,+∞）的父类
        list2 = list3;
        list2 = list4;
        // list2 = list5;

        // 读取数据时,extends限制，这时候就不一定用Object了，因为extends范围不会超过Human
        // 读取数据时,super限制仍然只能用Object接收
        Human human = list1.get(0);
        Object object = list2.get(0);
        // 写入数据, extends限制，无法添加数据，比如先添加了Teacher类型(?此时代表Teacher类型)，在添加其父类Human就不合理了
        // list1.add(new Teacher());
        // list1.add(new Human())
        // 写入数据，list2可以添加Human类型，也可以添加Student，因为肯定都是Human的子类[Human,+∞）
        list2.add(new Human());
        list2.add(new Teacher());

    }
}


class Human {

}

class Teacher extends Human {
}