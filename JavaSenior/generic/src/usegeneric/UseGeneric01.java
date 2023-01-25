package usegeneric;

import org.junit.Test;

import java.util.*;

/**
 * 【一、 为什么要有泛型？】
 *  集合容器类在设计阶段/声明阶段不能确定这个容器到底实际存的是什么类型的
 * 对象，所以在JDK1.5之前只能把元素类型设计为Object，JDK1.5之后使用泛型来
 * 解决。因此此时把元素的类型设计成一个参数，这个类型参数叫做泛型。
 *  使用泛型可以有效避免读取出来的对象需要强转的问题（避免了ClassCastException异常）
 *
 * 【二、泛型概述】
 *  声明：如interface List<T> 和 class GenTest<K,V>
 * 其中，T,K,V不代表值，而是表示类型。这里使用任意字母都可以。常用T表示，是Type的缩写。
 *  实例化：List<String> strList = new ArrayList<String>();
 * jdk1.7，泛型的简化操作新特性之类型推断，new ArrayList<~>，~处的泛型可省略
 *
 @author Alex
 @create 2022-12-12-11:49
 */
public class UseGeneric01 {
    // 不使用泛型会出现的问题
    @Test
    public void test() {
        ArrayList arrayList = new ArrayList();
        // 需求：存放学生的成绩
        arrayList.add(78);
        arrayList.add(76);
        arrayList.add(71);
        arrayList.add(77);
        arrayList.add(90);
        // 问题：可以存入其他类型的数据。类型不安全
        arrayList.add("tom");
        for (Object score : arrayList) {
            // 强转时，出现异常
            int stuScore = (int) score;  // 自动拆箱
            System.out.println(stuScore);
        }
    }

    // 集合中使用泛型举例1
    @Test
    public void test1 () {
        // 类型推断，new ArrayList<~>，~处的泛型可省略
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(67);
        arrayList.add(17);
        arrayList.add(77);
        // 使用泛型能够在编译时进行检查，保证数据的安全；
        // arrayList.add("ss");
        for (Integer score : arrayList) {
            System.out.println(score);  // 避免了强转操作~
        }

        Iterator<Integer> iterator = arrayList.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    // 集合中使用泛型举例2
    @Test
    public void test2(){
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
