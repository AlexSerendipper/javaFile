package usecommonclass;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 *  由于java的对象正常情况下只能进行==比较其地址值，无法比较出大小。所以需要排序操作
 * 【自然排序】
 *   ✔✔让被排序的类实现 java.lang.Comparable 接口，并重写其中的 compareTo(obj)方法，指明如何进行排序
 *   实现Comparable接口的对象（列表list1和数组array1），✔可以直接通过 Collections.sort(list1) 或 Arrays.sort(array1)进行自动排序
 *   重写规则：(默认按照从小到大继续排序)
 *               如果当前对象this大于形参对象obj，则返回正整数
 *               如果当前对象this小于形参对象obj，则返回负整数，
 *               如果当前对象this等于形参对象obj，则返回零
 *   注意：String、包装类等已经默认实现了comparable接口 (看源码string比的是第一个不同的字母的进行排序)
 *
 * 【定制排序】
 *   定制排序常用于 当元素的类型没有实现java.lang.Comparable接口而又不方便修改代码，
 *     或者实现了java.lang.Comparable接口的排序规则不适合当前的操作，那么可以考虑实现 Comparator 接口来实现排序
 *   ✔创建 java.util.Comparator 定制化排序器，并重写其中的compare(Object o1,Object o2)方法，比较o1和o2的大小
 *   可以传入定制化排序器后，✔通过 Collections.sort(list1,comparator1) 或 Arrays.sort(array1,comparator1)进行排序
 *    ✔✔✔注意，传入sort方法的数组或集合，其泛型一定要是包装类，如Integer[]。。。若使用基本类型数组会报错
 *   重写规则如下：
 *     1)如果方法返回正整数，则表示o1大于o2；
 *     2)返回负整数，表示o1小于o2
 *     3)如果返回0，表示相等；
 *     4)✔常使用包装类.compare(x,y)                 # 如果(x==y)则返回零，如果(x<y)则返回小于零，如果(x>y)，则返回大于零的值。
 *
 *
 * @author Alex
 * @create 2022-12-01-13:28
 */

public class UseCommonClass07 {
    // string已经重写过comparable方法了
    @Test
    public void test1() {
        String[] arr = new String[]{"aa", "cc", "bb"};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // comparable接口的使用
    @Test
    public void test2() {
        Good[] arr = new Good[5];
        arr[0] = new Good("lianxiang", 34);
        arr[1] = new Good("dell", 43);
        arr[2] = new Good("xiaomi", 12);
        arr[3] = new Good("huawei", 65);
        arr[4] = new Good("zhongxin", 12);
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // comparator接口的使用
    @Test
    public void test4(){
        Good[] arr = new Good[6];
        arr[0] = new Good("lianxiang", 34);
        arr[1] = new Good("dell", 43);
        arr[2] = new Good("xiaomi", 12);
        arr[3] = new Good("huawei", 65);
        arr[4] = new Good("zhongxin", 12);
        arr[5] = new Good("lianxiang", 22);
        Arrays.sort(arr, new Comparator<Good>() {  // 定制化排序, 这里指定按照名字从低到高排序,二级排序为价格从高往低排
            @Override
            public int compare(Good o1, Good o2) {
                if(o1.getName().equals(o2.getName())){
                    return -Double.compare(o1.getPrice(),o2.getPrice());
                }else{
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        System.out.println(Arrays.toString(arr));
    }



    // 商品类
    class Good implements Comparable {
        private String name;
        private double price;

        public Good() {
        }

        public Good(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Good{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }

        // 指明按照何种方式进行排序
        // 这里指定按照价格从低到高排序，二级排序按照名字进行排序
        @Override
        public int compareTo(Object o) {
            if (o instanceof Good) {
                Good good = (Good) o;
                if (this.price > good.price) {
                    return 1;
                } else if (this.price < good.price) {
                    return -1;
                } else {
                    // return 0;
                    return this.name.compareTo(good.name);  // 二级按照名字进行排序
                }
            }
            throw new RuntimeException("传入的数据类型不一致");
        }
    }
}



