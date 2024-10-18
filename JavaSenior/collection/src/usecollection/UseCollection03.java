package usecollection;

import org.junit.Test;

import java.util.*;

/**
 * 【List接口概述】
 * |----collection
 *      |----list
 *           |----ArrayList：作为list接口的主要实现类，线程不安全，效率高；底层使用object[] elementData存储
 *           |----LinkedList：作为list接口的次要实现类，对于频繁的插入、删除操作，使用此类效率比ArrayList高；底层使用双向链表存储
 *           |----Vector：作为list接口的古老实现类，线程安全，效率低；底层使用object[] elementData存储
 *  List集合类中元素有序、且可重复，集合中的每个元素都有其对应的顺序索引
 *  鉴于Java中数组用来存储数据的局限性，我们通常使用List替代数组
 *
 * 【List接口方法】除了从Collection集合继承的方法外，List集合里添加了一些根据索引来操作集合元素的方法
 *  void add(int index, Object ele):在index位置插入ele元素
 *  boolean addAll(int index, Collection eles):从index位置开始将eles中的所有元素添加进来
 *  Object get(int index):获取指定index位置的元素
 *  int indexOf(Object obj):返回obj在集合中首次出现的位置
 *  int lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置
 *  Object remove(int index):移除指定index位置的元素，并返回此元素
 *  Object set(int index, Object ele):设置指定index位置的元素为ele
 *  List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex位置的子集合
 *
 * 【面试题：arraylist,linkedlist,vector三者的异同】
 *  同：三个类都实现了list接口，存储数据的特点相同：储存有序的，可重复的数据。
 *  异（ArrayList和LinkedList）:
 *    1）二者都线程不安全，相对线程安全的Vector，执行效率高。
 *    2）ArrayList是实现了基于动态数组(自动扩展大小)的数据结构，LinkedList基于链表的数据结构。对于随机访问get和set，ArrayList效率优于LinkedList，
 *      因为LinkedList要移动指针。对于新增和删除操作add(特指插入)和remove，LinkedList比较占优势，因为增加和删除操作ArrayList要整体移动数据。
 *  异（ArrayList和Vector）：
 *    1）Vector和ArrayList几乎是完全相同的,唯一的区别在于Vector是同步类(synchronized)，属于强同步类。因此开销就比ArrayList要大，访问要慢。
 *       正常情况下,大多数的Java程序员使用 ArrayList而不是Vector, 因为同步完全可以由程序员自己来控制。Vector每次扩容请求其大小的2倍空间，而ArrayList是1.5倍。Vector还有一个子类Stack
 *
 * 【jdk7中ArrayList源码分析：详见ArrayList类文件】
 * 1. 先看构造器,创建了一个初始长度为10的底层object[]类型数组elementData
 * 2. 看添加操作add,当添加导致底层的elementData数组容量不够，则扩容，默认情况下扩容为原来容量的1.5倍
 * 3. 同时将原有数组中的数据赋值到新的数组中
 * 结论：建议开发中使用带参的构造器 new ArrayList(int initialCapacity),避免在中间环节扩容，提高效率
 *
 * 【jdk8ArrayList源码分析：详见ArrayList类文件】
 * 1. 先看构造器,创建了一个初始长度为{}的数组
 * 2. 在第一次调用add方法时，如list.add(123),才创建了底层长度为10的数组,并将123添加到数组中
 * 3. 后续的添加和扩容操作与jdk7无异
 * 小结：jdk7中的arraylist类似于单例的饿汉式，而jdk8中的arraylist创建类似于单例的懒汉式，延迟了数组的创建，节省了内存
 *
 * 【LinkedList源码分析：】
 * 1. 采用双向链式存储node = (prev,item,next)
 * 2. 查看add操作，首次添加时，last为null，造的第一个元素为（null,item1,null）,并将第一个元素作为 last 和 l，以及first
 * 3. 第二次添加时，造的第二个元素为（指向第一个元素item1,item2,null），并将第二个元素作为last
 * 4. 然后将l（此时为第一个元素）即（null,item1,null）的next改为第二个元素（null,item1,指向第二个元素item2）
 *
 * 【vector源码分析：基本不用了,稍微看一眼，与arraylist基本一致，默认扩容为原来的二倍】
 *
 * 【队列queue】
 *  通用操作
 *     .element（）                                           # 返回查看队首元素的值，不会删除队首元素。。。在队列元素为空时，该方法会抛出异常
 *     .peek()                                                # 返回查看队首元素的值，不会删除队首元素。。。在队列元素为空时，该方法会返回null
 *     .poll()                                                # 返回队首元素的同时删除队首元素，队列为空时返回NULL
 *     .remove（）                                            # 返回队首元素的同时删除队首元素，队列为空时抛出空指针异常
 *  Deque<Integer> deque = new LinkedList<>();                # 常用的queue实现类，这是一个双向队列！可以很方便进行队尾操作
 *     .getLast()                                             # 返回查看队尾元素的值
 *     .removeLast();                                         # 返回队尾元素的同时删除队尾元素
 *     .add()                                                 # 从队尾处添加元素
 *  Deque<Integer> deque = new PriorityQueue<>();             # 优先级队列，重写其compare方法可以实现对入队列的元素进行排序
 *
 * 【栈】
 *  通用操作
 *     push(int x)       # 将元素 x 压入栈顶。
 *     pop()             # 移除并返回栈顶元素。
 *     top()             # 返回栈顶元素，相当于peek。
 *     empty()           # 如果栈是空的，返回 true ；否则，返回 false
 *
 * @author Alex
 * @create 2022-12-04-15:01
 */


public class UseCollection03 {
    // List除了从Collection集合继承的方法外，List 集合里添加了一些根据索引来操作集合元素的方法。
    @Test
    public void test() {
        ArrayList arrayList = new UseCollection02().getCollection();

        System.out.println("arrayList = " + arrayList);
        // 1. void add(int index, Object ele):在index位置插入ele元素(在index位置元素前)
        arrayList.add(0, "bb");
        System.out.println("arrayList = " + arrayList);

        // 2. boolean addAll(int index, Collection eles):从index位置开始将eles中的所有元素添加进来
        Collection coll = Arrays.asList(1, 2, 3);
        arrayList.addAll(0,coll);
        System.out.println("arrayList = " + arrayList);

        // 3.Object get(int index):获取指定index位置的元素, 左闭右开
        Object o = arrayList.get(3);
        System.out.println("o = " + o);

        // 4.int indexOf(Object obj):返回obj在集合中首次出现的位置,如果没有找到，返回-1
        int i = arrayList.indexOf(456);
        System.out.println("i = " + i);

        // 5.int lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置
        int j = arrayList.lastIndexOf(456);
        System.out.println("j = " + j);

        // 6.Object remove(int index):移除指定index位置的元素，并返回此元素
        // 此方法是对父类方法remove的重载，所以remove方法可以根据指定对象删除，也可以指定索引删除，注意区分
        Object remove = arrayList.remove(7);
        System.out.println("remove = " + remove);
        System.out.println("arrayList = " + arrayList);

        // 7.Object set(int index, Object ele):设置指定index位置的元素为ele
        arrayList.set(0,6);
        System.out.println("arrayList = " + arrayList);

        // 8.List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex位置的子集合
        List list = arrayList.subList(1, arrayList.size() - 1);
        System.out.println("list = " + list);
    }
}
