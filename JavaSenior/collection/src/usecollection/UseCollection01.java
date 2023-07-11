package usecollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * 【一、集合、接口的概述】
 * 1. 集合、数组都是对多个数据进行存储（内存层面的存储）操作的结构，简称java容器
 * 2. 数组在存储数据方面的弊端：
 *     数组初始化以后，长度就不可变了，不便于扩展
 *     数组中提供的属性和方法少，不便于进行添加、删除、插入等操作，且效率不高。同时无法直接获取存储元素的个数
 *     数组存储的数据是有序的、可以重复的。对于无序、不可重复的需求，数组没法实现---->存储数据的特点单一
 * 3. 集合类可以用于存储数量不等的多个对象，还可用于保存具有映射关系的关联数组
 *
 * 【二、collection接口】单列数据，定义了存取一组对象的方法的集合
 * (1) Collection 接口是 List、Set 和 Queue 接口的父接口，该接口里定义的方法既可用于操作 Set 集合，也可用于操作 List 和 Queue 集合。
 * (2) JDK不提供此接口的任何直接实现，而是提供更具体的子接口(如：Set和List)
 * (3) 在 Java5 之前，Java 集合会丢失容器中所有对象的数据类型，把所有对象都
 *     当成 Object 类型处理；从 JDK 5.0 增加了泛型以后，Java 集合可以记住容器中对象的数据类型
 *
 * 【三、collection的14个方法】
 *  add()                              # 将元素添加到集合中
 *  size()                             # 获取集合中元素的个数
 *  addAll()                           # 将另一集合中的元素添加到该集合中
 *  isEmpty()                          # 判断集合中是否有元素
 *  clear()                            # 清空集合的元素
 *  contains(obj)                      # ✔判断当前集合中是否包含obj，判断时会调用obj对象所在类的equals方法，所以需要对类重写equals方法
 *  containsAll(Collection coll1)      # 判断coll1中的所有元素是否都包含于当前集合中
 *  remove(obj)                        # ✔移除指定的元素（利用equals判断，需要对类重写equals方法）
 *  removeAll(Collection coll1)        # 从当前集合中移除coll1中所有的元素
 *  retainAll(Collection coll1)        # 从当前集合中找出与coll1共有的元素,并赋值给当前集合
 *  equals(Collection coll1)           # 判断coll1与当前集合是否完全相等(注意此时为有序，需完全相等才true)
 *  hashcode()                         # 返回当前对象的哈希值
 *  toArray()                          # 将当前集合转换为数组，由于添加的是obj类型的元素，所以返回的是obj类型的数组（Arrays.asList(T...t)，将数组类型数据转换为集合）
 *  iterator()                         # 返回iterator接口的示例，用于遍历集合的元素（也推荐使用foreach增强for循环）
 *
 * 【总结：】
 * 增：add/删: remove/改: set/查：get/插：add(index,item)/长度:size
 * 遍历: ①iterator迭代器 ②foreach增强for循环 ③对于有序集合list，也可以用普通for循环哦
 *
 * 【四、数组与集合之间的转换】
 *  Collection coll = Arrays.asList(array1)          # 将数组 ==> 集合
 *   ✔ Arrays.asList返回的ArrayList实际上是Arrays的内部类，只是通过数组简单包装成的arraylist，并没有add方法
 *   ✔ 注意：对于int型数组，转换为集合会识别为一个元素（数组元素为一个对象），建议使用integer类型的数组（每个元素都是一个对象）
 *  Array arr = coll.toArray()                       # 集合 ==> 数组
 *   ✔ toArray()的返回值是Object[]，而toArray(T[])的返回值为T[]。
 *      形参数组T[]的大小可以随意指定，且不需要有值，只要是个实例对象即可
 *
 * @author Alex
 * @create 2022-12-03-13:54
 */
public class UseCollection01 {
    // collection的14个方法使用
    @Test
    public void test() {
        Collection coll = new ArrayList();
        // 方法1：add(obj)，将obj类型的元素添加到集合中
        coll.add("aa");
        coll.add("bb");
        coll.add(123);  // 自动装箱
        coll.add(new Date());

        // 方法2：size()获取集合中元素的个数
        System.out.println("coll.size() = " + coll.size());

        // 方法3：addAll(),将另一集合中的元素添加到该集合中
        Collection coll1 = new ArrayList();
        coll1.addAll(coll);
        System.out.println("coll1.size() = " + coll1.size());

        // 方法4：isEmpty()判断集合中是否有元素
        System.out.println("coll.isEmpty() = " + coll.isEmpty());

        // 方法5：clear()，清空集合的元素
        coll1.clear();
        System.out.println("coll1.isEmpty() = " + coll1.isEmpty());

        // 方法6：contains(obj)，判断当前集合中是否包含obj，判断时会调用obj对象所在类的equals方法，所以需要对类重写equals方法✔✔✔
        System.out.println("coll.contains(123) = " + coll.contains(123));
        System.out.println("coll.contains(new Date()) = " + coll.contains(new Date()));  // 重点，这里调用的是equals方法，date的equals方法重写过，比较的是两个对象的内容

        // 方法7：containsAll(Collection coll1),判断coll1中的所有元素是否都包含于当前集合中
        System.out.println("coll.containsAll(coll1) = " + coll.containsAll(coll1));

        // 方法8：remove(obj),移除指定的元素（需要对类重写equals方法）
        System.out.println("coll = " + coll);
        coll.remove(123);
        System.out.println("coll = " + coll);


        // 方法9：removeAll(Collection coll1), 从当前集合中移除coll1中所有的元素
        Collection coll2 = Arrays.asList("aa");  // 返回list接口，多态，声明collection
        coll.removeAll(coll2);
        System.out.println("coll = " + coll);

        // 方法10：retainAll(Collection coll1), 从当前集合中找出与coll1共有的元素,并赋值给当前集合
        coll1.add("bb");
        coll1.add("cc");
        coll1.add(123);
        coll.retainAll(coll1);
        System.out.println("coll = " + coll);

        // 方法11：equals(Collection coll1), 判断coll1与当前集合是否完全相等(注意此时为有序，需完全相等才true)✔
        System.out.println("coll.equals(coll2) = " + coll.equals(coll2));

        // 方法12：hashcode(),返回当前对象的哈希值
        System.out.println(coll.hashCode());

        // 方法13：toArray(), 将当前集合转换为数组，由于添加的是obj类型的元素，所以返回的是obj类型的数组
        Object[] arr = coll.toArray();
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
        // 拓展：数组转换为集合(数组类似于list，都属于有序列表，所以转换为集合中的list)
        // ✔Arrays.asList返回的ArrayList 实际上是Arrays的内部类 属于java.util.Arrays$ArrayList
        // ✔只是通过数组简单包装成的arraylist，并没有add方法
        Collection coll3 = Arrays.asList(new String[]{"aa", "bb", "cc"});
        System.out.println(coll3);
        // ✔细节点：对于int型数组，转换为集合会识别为一个元素（数组元素为一个对象），建议使用integer类型的数组（每个元素都是一个对象）
        Collection coll4 = Arrays.asList(new int[]{123, 456});
        Collection coll5 = Arrays.asList(new Integer[]{123, 456});
        System.out.println(coll4);
        System.out.println(coll5);

        // 方法14：iterator(),返回iterator接口的示例，用于遍历集合的元素
    }
}
