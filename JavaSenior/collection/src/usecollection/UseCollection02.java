package usecollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * 【iterator迭代器接口】Iterator对象称为迭代器(设计模式的一种)，主要用于遍历 Collection 集合中的元素
 *  Collection接口继承了java.lang.Iterable接口，所有实现了Collection接口的集合类都有一个iterator()方法
 *  集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合的第一个元素之前。
 *
 * 【iterator迭代器常用方法】
 *  hasNext()                # 判断迭代器接口中，游标的下一个位置是否有对象，有则返回true
 *  next()                   # 输出迭代器接口的下一个元素，默认游标都在集合的第一个元素之前
 *  remove()                 # 删除当前游标位置的元素, 在指针（游标）指向的位置为空的地方，调用remove，会报错
 *                              如在第一次调用next()之前调用remove，或是连续两次调用remove()✔✔;
 *
 * 【foreach循环遍历】foreach能够用来遍历 数组或集合元素
 *  foreach循环遍历，底层仍调用Iterator完成操作。此遍历操作无需获取Collection或数组的长度，无需使用索引
 *  使用格式为：for(集合中元素类型 局部变量:集合对象)
 *
 * @author Alex
 * @create 2022-12-04-13:35
 */
public class UseCollection02 {

    @Test
    public void test() {
        // 迭代器的标准迭代操作
        ArrayList coll = getCollection();
        Iterator iterator = coll.iterator();
        while (iterator.hasNext()) {  // 判断是否还有下一个元素
            System.out.println(iterator.next());  // ① 指针下移 ② 将下移后集合位置上的元素返回
        }
        System.out.println("*****************");
        // remove方法的调用
        Iterator iterator1 = coll.iterator();
        while (iterator1.hasNext()) {
            Object obj = iterator1.next();
            if ("bb".equals(obj)) {
                iterator1.remove();
            }
        }
        // foreach循环遍历集合元素
        for (Object obj:coll){
            System.out.println(obj);
        }
        System.out.println("*****************");
        // jdk8中新增了集合中的方法，了解即可
        coll.forEach(System.out::println);  // 方法引用
        System.out.println("*****************");
        // 使用普通for循环也可以遍历元素
        for (int i = 0; i < coll.size(); i++) {
            System.out.println(coll.get(i));
        }
    }

    // 2. 迭代器的常见错误使用
    @Test
    public void test1() {
        Collection coll = getCollection();
        Iterator iterator = coll.iterator();
         // 错误方式一：指针下移两次。iterator.next()调用后指针就下移了，该错误就是调用了两次
//         while ((iterator.next()) != null) {
//             System.out.println(iterator.next()); // 输出bb → date
//         }
         System.out.println("*****************");
        // 错误方式二：匿名迭代器（集合对象每次调用iterator()方法都得到一个全新的迭代器对象）
        while (coll.iterator().hasNext()) {
            System.out.println(coll.iterator().next());  // 输出aa aa aa，每次都是新的iterator，新的指针
        }
    }

    public ArrayList getCollection() {
        ArrayList coll = new ArrayList();
        coll.add("aa");
        coll.add("bb");
        coll.add(123);  // 自动装箱
        coll.add(new Date());
        return coll;
    }
}
