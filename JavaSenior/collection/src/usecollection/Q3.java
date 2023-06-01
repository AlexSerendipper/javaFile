package usecollection;

import org.junit.Test;

import java.util.HashSet;
import java.util.Objects;

/**
 * 【判断输出】
 * @author Alex
 * @create 2022-12-06-13:07
 */
public class Q3 {
    @Test
    public void test(){
        HashSet set = new HashSet();
        Person1 p1 = new Person1(1001,"AA");
        Person1 p2 = new Person1(1002,"BB");
        set.add(p1);
        set.add(p2);
        p1.name = "CC";
        set.remove(p1);  // ✔✔删除元素时，先用hashcode判断删除位置，再用equals判断核实
                         // 由于p1的位置是根据1001，"AA"算出来的，所以remove失败
        System.out.println(set);
        System.out.println("*****************");
        set.add(new Person1(1001,"CC"));  // 添加成功
        System.out.println(set);
        System.out.println("*****************");
        set.add(new Person1(1001,"AA"));  // 添加成功，因为原来那个位置现在是CC，equals返回false所以能加
        System.out.println(set);
    }


    class Person1{
        public int age;
        public String name;

        public Person1(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person1 person1 = (Person1) o;

            if (age != person1.age) return false;
            return Objects.equals(name, person1.name);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + age;
            return result;
        }

        @Override
        public String toString() {
            return "Person1{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
