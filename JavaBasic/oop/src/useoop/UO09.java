package useoop;

import org.junit.Test;

/**
 *【object类的使用】
 *  Object类是所有Java类的根父类
 *  如果在类的声明中未使用extends关键字指明其父类，则默认父类为java.lang.Object类
 *  对于数组，也可以看作是一个Object类的子类
 *
 *【object类中的常用方法】所有类都继承了Object，也都获得了其中定义的方法
 *   public Object() 构造器
 *   public boolean equals(Object obj) 方法 对象比较
 *   public int hashCode() 方法 取得Hash码
 *   public String toString() 方法 对象打印时调用
 *   getClass(), clone(), finalize(), wait(), notify(), notifyAll()等
 *
 *【==与equals方法】对象比较时调用。实际使用均自动生成
 *  == ：当比较基本数据类型时，比较是保存的数据。比较引用数据类型时，比较的是地址值。
 *  equals()：只适用于比较引用数据类型，内部封装的是==，所以比较的是地址值。
 *  可以通过重写类中的equals()方法。使其用于比较两个对象的“内容”，而不再比较地址值。
 *  ✔对类File、String、Date及包装类来说，其内部都重写了object类中的equals方法，重写后用于比较两个对象的是比较类型及内容是否相同，而不再比较地址值是否相同。
 *
 *【toString方法】对象打印时调用。实际使用均自动生成
 *  toString()方法在Object类中定义，其返回值是String类型，返回类名和它的引用地址。
 *  在进行String与其它类型数据的连接操作时，自动调用toString()方法
 *    如："now="+ new Date()相当于"now="+new Date().toString();
 *  可以通过重写类中的的toString()方法，使其在被打印时返回指定的字符串
 *  对类File、String、Date及包装类来说，其内部都重写了object类中的toString()方法,使得返回实体信息而不再返回地址值
 *
 @author Alex
 @create 2023-01-10-15:50
 */
public class UO09 {
    // 一道奇怪的问题
    @Test
    public void test3(){
        int[] i = {1,2,3};
        char[] c= {'a','b'};
        System.out.println(i);
        System.out.println(c);  // 理论上这里输出的是地址值的，但是这个println方法重载了，变成遍历数组了
    }


    // 体会重写equals的思路
    class Person{
        String name;
        int age;

        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }

            if(obj instanceof Person) {  // 避免传入的都不是子类，那就没法比了
                Person objj = (Person)obj;
                // 比较两个对象的属性是否完全相同
                // 注意这里name比较的是两个字符串是否相同，不是比较地址值是否相同，比较的是内容，要用string中重写的equals方法
                return this.age == objj.age && this.name.equals(objj.name);
            }

            return false;
        }
    }
}

