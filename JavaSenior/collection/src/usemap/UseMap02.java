package usemap;

import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 【treeMap】
 *  底层使用红黑树结构存储数据
 *  treeMap是对key进行排序（自然排序、定制排序），所以向treemap中添加key-value时，要保证key必须是同一个类创建的对象
 @author Alex
 @create 2022-12-11-14:52
 */
public class UseMap02 {
    // 自然排序举例
    @Test
    public void test() {
        // 因为需要传入键值，值就随便传了
        TreeMap treeMap = new TreeMap();
        treeMap.put(new Slave("hyq", 99), 98);
        treeMap.put(new Slave("zzj", 22), 89);
        treeMap.put(new Slave("zzj", 34), 76);
        treeMap.put(new Slave("hjy", 73), 100);
        for (Object o : treeMap.entrySet()) {
            System.out.println(o);
        }

    }

    // 定制排序举例
    @Test
    public void test1() {
        Comparator comparator = new Comparator() {
            // 年纪从大到小排序，名字从小到大，
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Slave && o2 instanceof Slave) {
                    Slave o11 = (Slave) o1;
                    Slave o22 = (Slave) o2;
                    int compare = -Integer.compare(o11.age, o22.age);
                    if (compare != 0) {
                        return compare;
                    } else {
                        return o11.name.compareTo(o22.name);
                    }
                } else {
                    throw new RuntimeException("传入参数异常");
                }
            }
        };
        TreeMap treeMap = new TreeMap(comparator);
        treeMap.put(new Slave("hyq", 99), 98);
        treeMap.put(new Slave("zzj", 22), 89);
        treeMap.put(new Slave("zzj", 34), 76);
        treeMap.put(new Slave("hjy", 73), 100);
        for (Object o : treeMap.entrySet()) {
            System.out.println(o);
        }
    }
}


class Slave implements Comparable {
    public String name;
    public int age;

    public Slave() {
    }

    public Slave(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Slave{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Slave slave = (Slave) o;

        if (age != slave.age) return false;
        return Objects.equals(name, slave.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    // 按照姓名从大到小排序，年龄从小到大排序
    @Override
    public int compareTo(Object o) {
        if (o instanceof Slave) {
            Slave o1 = (Slave) o;
            int compare = -this.name.compareTo(o1.name);
            if (compare != 0) {
                return compare;
            } else {
                return Integer.compare(this.age, o1.age);
            }
        } else {
            throw new RuntimeException("输入数据类型异常");
        }
    }
}