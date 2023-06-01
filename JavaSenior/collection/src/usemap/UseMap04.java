package usemap;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 【collections工具类】
 *  Collections是一个操作 Set、List 和 Map 等集合的工具类
 *  Collections中提供了一系列静态的方法对集合元素进行排序、查询和修改等操作，还提供了对集合对象设置不可变、对集合对象实现同步控制等方法
 *
 * 【排序操作：（均为static方法）】
 *  reverse(List)：反转 List 中元素的顺序
 *  shuffle(List)：对 List集合元素进行随机排序
 *  sort(List)：根据元素的自然顺序对指定 List 集合元素按升序排序
 *  sort(List，Comparator)：根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
 *  swap(List，int， int)：将指定 list 集合中的 i处元素和 j 处元素进行交换
 *
 * 【查找、替换操作】
 *  Object max(Collection)：根据元素的自然顺序，返回给定集合中的最大元素
 *  Object max(Collection，Comparator)：根据 Comparator 指定的顺序，返回给定集合中的最大元素
 *  Object min(Collection)
 *  Object min(Collection，Comparator)
 *  int frequency(Collection，Object)：返回指定集合中指定元素的出现次数
 *  void copy(List dest,List src)：✔将src中的内容复制到dest中
 *  boolean replaceAll(List list，Object oldVal，Object newVal)：使用新值替换List 对象的所有旧值
 *
 * 【同步操作】Collections类中提供了多个 synchronizedXxx()方法，可使将指定集合包装成线程同步的集合
 *  Collections.synchronizedCollection()
 *  Collections.synchronizedList()
 *  Collections.synchronizedMap()
 *  Collections.synchronizedSet()
 *
 * 【面试题：collection和collections的区别：】
 *
 @author Alex
 @create 2022-12-11-20:52
 */
public class UseMap04 {
    // 【排序操作：（均为static方法）】
    @Test
    public void test() {
        ArrayList arrayList = getList();
        System.out.println(arrayList);
        Collections.reverse(arrayList);
        System.out.println(arrayList);
        Collections.shuffle(arrayList);
        System.out.println(arrayList);
        Collections.sort(arrayList);
        System.out.println(arrayList);
        Collections.swap(arrayList, 0, 4);
        System.out.println(arrayList);
    }

    // 【查找、替换操作】
    @Test
    public void test1() {
        ArrayList arrayList = getList();
        System.out.println(Collections.max(arrayList));;
        System.out.println(Collections.frequency(arrayList,113));
        // 赋值操作，void copy(List dest,List src)。很容易出错,看源码很容易发现，如果src容量比dest大，就会报错！
//        ArrayList arrayList1 = new ArrayList();
//        Collections.copy(arrayList1,arrayList);
//        System.out.println(arrayList1);
        // ✔赋值操作的常规做法
        List dest = Arrays.asList(new Object[arrayList.size()]);
        Collections.copy(dest,arrayList);
        System.out.println(dest);
        Collections.replaceAll(arrayList,113,666);
        System.out.println(arrayList);
    }

    // 【同步操作】
    @Test
    public void test2(){
        ArrayList arrayList = getList();
        // 返回线程安全的集合
        List list = Collections.synchronizedList(arrayList);
    }


    public static ArrayList getList(){
        ArrayList arrayList = new ArrayList();
        arrayList.add(123);
        arrayList.add(34);
        arrayList.add(43);
        arrayList.add(113);
        arrayList.add(113);
        arrayList.add(0);
        return arrayList;
    }
}
