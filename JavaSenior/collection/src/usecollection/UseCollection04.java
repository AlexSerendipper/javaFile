package usecollection;

import org.junit.Test;

import java.util.*;

/**
 * 【一. set接口概述】
 * |----collection接口
 *      |----set接口：无序的、不可重复的数据
 *           |----HashSet：作为list接口的主要实现类，线程不安全，可以存储null值
 *                |----LinkedHashSet：作为hashset的子类；遍历其内部数据时，可以按照添加的顺序遍历
 *           |----TreeSet：可以按照添加对象的指定属性进行排序
 *  1） set作为Collection的子接口没有定义新的方法，使用的都是collection中的方法
 *  2） ✔set接口具有无序性和不可重复性两大特性
 *      无序性：无序性不等于随机性（并不是按照添加的顺序输出就是有序）
 *              这里无序指的是存储的数据在底层数组中并非按照按照数组索引的顺序进行添加，而是根据哈希值进行添加
 *      不可重复性：不可重复性：会调用对象的equals方法进行比较！！即相同元素无法添加成功
 *
 * 【二、hashset源码分析✔✔✔】分析set中具体添加元素的过程(jdk7),具体源码分析见map
 *  (1)向hashset中添加元素a，调用a所在类的hashCode()方法，计算该元素的哈希值
 *  (2)根据某种函数计算出其在底层数组中的存放位置，如果该位置没有其他元素，则添加成功
 *  (3)如果该位置有其他元素 b：
 *     ① 若计算出存放在同一位置的两个数hash值不同，则以链表的方式7上8下存放)
 *     ② 若计算出存放在同一位置的两个数hash值相同，调用equals方法判断，返回为true时添加失败，false添加成功，存放在后一个位置上
 *   说明：七上八下，jdk7中，元素a放到数组中，指向原来的元素b（新添加的元素在上面）
 *                 jdk8中，元素b仍放在数组中，指向元素a
 *   要求：向set中添加的数据，其所在的类一定要重写hashCode()和equals()方法（相等的对象必须具有相等的散列码）
 *   理解：相等的对象必须具有相等的散列码，具体就是指两个相同的对象（equals返回true），计算出来的哈希值要是相同的（两个哈希值相同的对象可以并非一定是同一个对象）
 *   注意：object类计算哈希值方法是随机计算，所以自定义类一定要重写hashCode()
 *
 * 【三、linkedhashset分析】linkedhashset是hashset的子类，根据元素的 hashCode值来决定元素的存储位置，但它同时使用双向链表维护元素的次序，
 *   使得元素看起来是以插入顺序保存的。
 *   优点：LinkedHashSet插入性能略低于 HashSet，但在频繁的迭代访问（遍历） Set 里的全部元素时有很好的性能
 *
 * 【四、TreeSet】因为只有相同类的两个实例才会比较大小，所以向TreeSet 中添加的应该是同一个类的对象
 *   自然排序：如果试图把一个对象添加到TreeSet时，则该对象的类必须实现 Comparable接口
 *    TreeSet会调用集合元素的compareTo(Object obj)方法来比较元素之间的大小关系，然后将集合元素按升序(默认情况)排列
 *   定制排序：将实现Comparator接口的实例作为形参传递给TreeSet的构造器。
 *    TreeSet会调用集合元素的compare(Object o1,Object o2)方法来比较元素之间的大小关系
 *
 * @author Alex
 * @create 2022-12-05-9:36
 */
public class UseCollection04 {
    // hashSet使用举例,体会无序性和不可重复性
    @Test
    public void test1() {
        HashSet hashSet = getSet();
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println("iterator.next() = " + iterator.next());
        }
    }

    // treeSet使用举例:自然排序,按照年龄排序
    @Test
    public void test2(){
        TreeSet<Person> set = getSet2(new TreeSet<Person>());
        Iterator<Person> iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    // treeSet使用举例:定制排序，按照姓名排序
    @Test
    public void test3(){
        TreeSet<Person> set = new TreeSet<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        TreeSet<Person> set2 = getSet2(set);
        for(Person p:set2){
            System.out.println(p);
        }
    }

    public HashSet getSet() {
        HashSet hashSet = new HashSet();
        hashSet.add(123);
        hashSet.add(456);
        hashSet.add("hahaha");
        hashSet.add(new Person("tom", 17));
        hashSet.add(123);
        hashSet.add(new Person("tom", 17));
        return hashSet;
    }

    public TreeSet<Person> getSet2(TreeSet<Person> tree){
        tree.add(new Person("tom", 17));
        tree.add(new Person("jack", 1));
        tree.add(new Person("tony", 25));
        tree.add(new Person("ab", 87));
        tree.add(new Person("fuck", 16));
        tree.add(new Person("zack", 52));
        return tree;
    }


}

class Person implements Comparable<Person> {
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        return Objects.equals(name, person.name);
    }

    // 我们自己写一个low版的 return name.hashCode() + age
    // 这样有点小问题，可能算出来 20+20  与  18 + 22, 两个存放在同一个位置，而实际上我们希望存放时出现的指针越少越好，所以比较low
    // 为什么用31呢？ ①31只占用5bits,相乘造成数据溢出的概率较小
    //               ②31可以由i*31== (i<<5)-1来表示,现在很多虚拟机里面都有做相关优化。（提高算法效率）
    //               ③31是一个素数，素数作用就是如果我用一个数字来乘以这个素数，那么最终出来的结果只能被素数本身和被乘数还有1来整除(减少冲突)
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age,o.age);
    }
}