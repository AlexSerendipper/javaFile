package project1;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Alex
 * @create 2022-12-05-16:11
 */
public class Test {
    public static void main(String[] args) {
        MyDate myDate1 = new MyDate(1997,9,20);
        MyDate myDate2 = new MyDate(1997,9,18);
        MyDate myDate3 = new MyDate(2001,5,6);
        MyDate myDate4 = new MyDate(2020,1,10);
        MyDate myDate5 = new MyDate(1990,7,7);

        Employee employee1 = new Employee("jack",12, myDate1);
        Employee employee2 = new Employee("rose",24, myDate2);
        Employee employee3 = new Employee("fuck",56, myDate3);
        Employee employee4 = new Employee("jack",12, myDate4);  // employ按名字排序，这个应该加不进去的
        Employee employee5 = new Employee("zack",33, myDate5);


        // 使用泛型的定制排序，在new的时候重写方法，再指定局部变量
        Comparator<Employee> compare = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                MyDate bir1 = o1.getBirthday();
                MyDate bir2 = o2.getBirthday();
                int comYY = Integer.compare(bir1.getYear(), bir2.getYear());
                int comMM = Integer.compare(bir1.getMonth(), bir2.getMonth());
                int comDD = Integer.compare(bir1.getDay(), bir2.getDay());

                if (comYY != 0) {
                    return comYY;
                } else if (comMM != 0) {
                    return comMM;
                } else {
                    return comDD;
                }
            }
        };


        // 因为这个定义了定制化比较器，优先按照这个定制化比较器执行的
        // 如果不传入定制化比较器，就是按照employee中的比较器
        // 指定了treeset的泛型，只能添加employee类的数据
        TreeSet<Employee> treeSet = new TreeSet<>(compare);
        treeSet.add(employee1);
        treeSet.add(employee2);
        treeSet.add(employee3);
        treeSet.add(employee4);
        treeSet.add(employee5);
        Iterator<Employee> iterator = treeSet.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
