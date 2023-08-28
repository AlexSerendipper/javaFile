package usemap;

import org.junit.Test;

import java.util.*;

/**
 * 【一： map概述】
 * |----Map:存储双列数据，存储key-value对的数据     ----类似于高中的函数：y=f(x)
 *      |----HashMap: 作为Map的主要实现类，线程不安全，效率高; 可以存储null的key和value
 *           |----LinkedHashMap: 保证在遍历map元素时，可以按照添加的顺序实现遍历
 *                             : 原因，在原有的HashMap底层之上，添加了一对指针（引入了链式结构）
 *                             ：对于频繁的遍历操作，此类的执行效率高于HashMap
 *      |----Hashtable: 作为Map的古老实现类，线程安全，效率低; 不能存储null的key和value
 *           |----Properties：常用来处理配置文件，key和value都是string类型
 *      |----TreeMap:可以根据添加的key-value对进行排序，实现排序遍历，通常都是对key进行排序（自然排序，定制排序）
 *                  :底层使用红黑树
 *
 * 1) HashMap的底层结构：① jdk7及以前，使用数组＋链表
 *                      ② jdk8,使用了数组+链表+红黑树
 * 2) map中的key的无序的，不可重复的，使用set存储所有的key。key所对应的类须重写hashCode()和equals()方法
 * 3) value中的数据是无序的，可重复的，使用collection存储。value所在的类，需要重写equals方法
 * 4) 一个键值对，构成一个entry对象（作为其两个属性）。n个entry对象构成一个entry数组
 * 5) 由于key是无序的，不可重复的，entry对象也是无序的，不可重复的，使用set存储
 *
 * 【二、jdk7中的底层实现源码流程（饿汉式）(需要能够复述)✔✔✔】
 * (1) HashMap map = new HashMap(); 在实例化以后，底层创建了一个长度为16的一维数组Entry[] table;(看构造器，点this进去)
 * (2) map.put(key1,value1)(查看Put方法)
 *  ① 首先调用key1所在类的hashcode()方法，此hash值经过某种映射，得到在entry数组中的存放位置
 *  ② 如果此位置上为空，此时直接添加成功，key1-value1添加成功
 *  ③ 如果此位置上数据不为空（意味着此位置上存在一个或多个数据（以链表方式存储）），比较当前key1和已经存在数据的哈希值，
 *    如果哈希值不同，添加成功。如果哈希值与某一个数据相同(key2-value2)，继续比较key1所在类的equals()方法，如果equals返回false，添加成功
 *    如果equals()返回true,使用value1替换value2
 *  补充：对于原先就有数据的情况，key1-value1和key2-value2以链表的方式存储
 *  扩容：默认扩容为原来容量的2倍，并把原来的数据复制过来
 *
 * 【三、Jdk8中的底层实现源码流程（懒汉式）(需要能够复述)✔✔】
 * (1) HashMap map = new HashMap(); ✔在实例化以后，底层没有创建了一个长度为16的一维数组Node[] table; (改名了而且)
 * (2) 首次调用 map.put(key1,value1)方法时：
 *    ① 创建长度为16的一维数组Node[] table;
 *    ② jdk8,使用了数组+链表+红黑树
 *    ✔当数组的某一个索引位置上有多个元素（以链表方法存储）,元素个数>8 且 当前数组长度>64时，该索引位置上所有数据改用红黑树存储
 *    ② 首先调用key1所在类的hashcode()方法，此hash值经过某种映射，得到在entry数组中的存放位置
 *    ③ 如果此位置上为空，此时直接添加成功，key1-value1添加成功
 *    ④ 如果此位置上数据不为空（意味着此位置上存在一个或多个数据（以链表方式存储）），比较当前key1和已经存在数据的哈希值，
 *      如果哈希值不同，添加成功。如果哈希值与某一个数据相同(key2-value2)，继续比较key1所在类的equals()方法，如果equals返回false，添加成功
 *      如果equals()返回true,使用value1替换value2
 *    补充：对于原先就有数据的情况，key1-value1和key2-value2以链表的方式存储
 *           当形成链表时，七上八下（jdk7中新的元素指向旧元素，jdk8中旧元素指向新元素）
 *    扩容：默认扩容为原来容量的2倍，并把原来的数据复制过来
 *
 * 【四、LinkedHashMap的底层实现原理（了解）】
 *   底层使用的结构与hashmap基本相同，因为Linkedhashmap继承于hashmap, 只是重写了newNode方法，
 *   并且 新造了内部类Entry,该Entry对象继承自Node，实现了双向链表，所以LinkedHashMap就可以根据添加的顺序实现遍历
 *
 * 【五、hashset】
 *  在new HashSet的时候，实际上底层是new了一个HashMap
 *  当我们add元素的时候，实际上是往这个HashMap.put(e, object())
 *  ✔✔即我们存入的键值，value处就放一个空的object对象（常量），所有的key都指向它
 *
 * 【六. 源码中的一些常量解析，了解即可，主要看源码就行】
 *   DEFAULT_INITIAL_CAPACITY : HashMap的默认容量，16
 *   table：存储元素的数组，容量为2的n次幂
 *   size：HashMap中存储的键值对的数量
 *   threshold：扩容的临界值，=容量*填充因子
 *   loadFactor：填充因子
 *   DEFAULT_LOAD_FACTOR：HashMap的默认加载因子，就是存到百分之几该扩容拉
 *   TREEIFY_THRESHOLD：Bucket中链表长度大于该默认值，转化为红黑树
 *   MAXIMUM_CAPACITY ： HashMap的最大支持容量，2^30
 *   UNTREEIFY_THRESHOLD：Bucket中红黑树存储的Node小于该默认值，转化为链表
 *   MIN_TREEIFY_CAPACITY：桶中的Node被树化时最小的hash表容量。（当桶中Node的
 *   数量大到需要变红黑树时，若hash表容量小于MIN_TREEIFY_CAPACITY时，此时应执行
 *   resize扩容操作这个MIN_TREEIFY_CAPACITY的值至少是TREEIFY_THRESHOLD的4倍。）
 *   entrySet：存储具体元素的集
 *   modCount：HashMap扩容和结构改变的次数。
 *
 * 【map中的常用方法】以hashmap为例
 *  1) 添加、删除、修改操作：
 *      Object put(Object key,Object value)：将指定key-value添加到(或修改)当前map对象中
 *      void putAll(Map m):将m中的所有key-value对存放到当前map中
 *      Object remove(Object key)：移除指定key的key-value对，并返回value
 *      void clear()：清空当前map中的所有数据
 *  2) 元素查询的操作：
 *      Object get(Object key)：获取指定key对应的value
 *      boolean containsKey(Object key)：是否包含指定的key
 *      boolean containsValue(Object value)：是否包含指定的value
 *      int size()：返回map中key-value对的个数
 *      boolean isEmpty()：判断当前map是否为空
 *      boolean equals(Object obj)：判断当前map和参数对象obj是否相等
 *      int getOrDefault(Object key, V defaultValue)  意思就是当Map集合中有这个key时，就使用这个key对应的value值，如果没有就使用默认值defaultValue
 *  3) 元视图操作的方法：
 *      Set keySet()：返回所有key构成的Set集合
 *      Collection values()：返回所有value构成的Collection集合
 *      Set entrySet()：返回所有key-value对构成的Set集合
 * @author Alex
 * @create 2022-12-06-14:49
 */
public class UseMap01 {
    // 一、添加、删除、修改操作
    @Test
    public void test1() {
        HashMap hashMap = new HashMap();
        hashMap.put("aa", 123);
        // 添加
        hashMap.put("bb", 456);
        // 修改
        hashMap.put("bb", 78);
        System.out.println(hashMap);
        System.out.println("***********");
        // 2. putAll操作
        HashMap hashMap1 = new HashMap();
        hashMap1.putAll(hashMap);
        hashMap1.put("cc", 78);
        System.out.println(hashMap1);
        System.out.println("***********");
        // 3. remove操作
        Object aa = hashMap1.remove("aa");
        System.out.println(aa);
        System.out.println(hashMap1);
        System.out.println("***********");
        // 4. clear操作
        hashMap1.clear();
        System.out.println(hashMap1);
    }

    // 二、元素查询的操作
    @Test
    public void test2() {
        HashMap hashMap = new HashMap();
        hashMap.put("aa", 123);
        hashMap.put("bb", 456);
        hashMap.put("bb", 78);
        System.out.println(hashMap.get("aa"));
        System.out.println(hashMap.containsKey("cc"));
        System.out.println(hashMap.containsValue(123));
        System.out.println(hashMap.size());
        System.out.println(hashMap.isEmpty());
        HashMap hashMap1 = new HashMap();
        hashMap1.put("aa", 123);
        hashMap1.put("bb", 78);
        System.out.println(hashMap.equals(hashMap1));
    }

    // 三、元视图操作的方法：这里主要是对应着遍历的操作。
    @Test
    public void test3() {
        // 因为map中的key是存在set中，value存在collection中，entry对象也是存在set中。
        // 所以单独取出来就可以用iterator进行遍历拉。
        HashMap hashMap = new HashMap();
        hashMap.put("aa", 123);
        hashMap.put("bb", 456);
        hashMap.put("bb", 78);
        hashMap.put("cc", 9);
        // 遍历所有的key
        Set keySet = hashMap.keySet();
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("*****************");
        // 遍历所有的value
        Collection values = hashMap.values();
        for (Object obj : values) {
            System.out.println(obj);
        }
        System.out.println("*****************");
        // 遍历所有的entry1
        Set entrySet = hashMap.entrySet();
        for (Object obj : entrySet) {
            System.out.println(obj);
        }
        System.out.println("*****************");
        // 遍历所有的entry2
        Iterator iterator1 = entrySet.iterator();
        while (iterator1.hasNext()) {
            Object obj = iterator1.next();
            // 强转为entry对象
            Map.Entry entry = (Map.Entry) obj;
            // 调用entry对象的方法
            System.out.println(entry.getKey() + ">>>>" + entry.getValue());
        }
        System.out.println("*****************");
        // 遍历所有的entry3，利用key找到value，拼凑出所有entry
        Iterator iterator2 = keySet.iterator();
        while (iterator2.hasNext()) {
            Object currentKey = iterator2.next();
            System.out.println(currentKey + "==>" + hashMap.get(currentKey));
        }

    }

    //
}
