package usejava8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 【方法引用】
 * 1. 方法引用，本质上就是lambda表达式的更简化形式。故其本质也是作为函数式接口的实例
 * 2. 使用格式：类（或对象）：：调用方法名
 * 3. 使用情景：✔只能用于lambda表达式中只有一条执行语句，可以省略return和大括号的情况
 * 4. 具体分为如下的三种情况
 * （1）对象：：非静态方法（实例方法）✔要求接口中的抽象方法它的形参列表和返回值类型与方法引用的方法的形参列表和返回值类型完全相同
 * （2）类：：静态方法 ✔要求接口中的抽象方法它的形参列表和返回值类型与方法引用的方法的形参列表和返回值类型完全相同
 * （3）类：：非静态方法（实例方法）✔要求接口中抽象方法的形参，作为非静态方法的调用者出现
 * 5. 如果给函数式接口提供实例刚好满足方法引用使用的要求，则可以考虑使用方法引用
 *
 * 【构造器引用】
 * 1、使用条件：接口中的抽象方法的形参列表与构造器形参列表相同，其返回值类型就是构造器所属的类的类型
 * 2、使用格式为: 类名::new
 *
 * 【数组引用】
 * 1. 使用条件：接口中的抽象方法的形参列表刚好作为数组的长度，返回值类型就是数组的类型
 * 2. 使用格式： type[]::new
 @author Alex
 @create 2022-12-28-18:07
 */
public class UseJava802 {
    // 一：对象：：非静态方法
    // Consumer中的void accept(T t)
    // PrintStream中的void println(T t)
    @Test
    public void test() {
        // ✔注意：只能用于lambda表达式中只有一条执行语句，可以省略return和大括号的情况
        //    这里我们是刚好在consumer接口的抽象方法中【只】需要用println方法，而这个非静态方法的形参列表和返回值类型刚好和抽象方法相同
        Consumer<String> con = (s) -> System.out.println(s);
        con.accept("北京");
        System.out.println("*****************");
        Consumer<String> con1 = System.out::println;
        con1.accept("天津");
    }

    // supplier中T get()
    // Employee中String getName()
    @Test
    public void test1() {
        Employee employee = new Employee("zzj", 18);
        Supplier<String> sup = () -> employee.getName();
        System.out.println(sup.get());
        System.out.println("*****************");
        Supplier<String> sup2 = employee::getName;
        System.out.println(sup.get());
    }

    // 情况二：类：：静态方法
    // Comparator中的int compare(T t1,T t2)
    // integer中的int compare(T t1,T t2)
    @Test
    public void test2() {
        Comparator<Integer> com1 = (t1, t2) -> Integer.compare(t1, t2);
        System.out.println("com1.compare(11,22) = " + com1.compare(11, 22));
        System.out.println("*****************");
        Comparator<Integer> com2 = Integer::compare;
        System.out.println("com2.compare(22,11) = " + com2.compare(22, 11));
    }

    // function中的R apply(T t)
    // Math中的Long round(Double d)
    @Test
    public void test3() {
        Function<Double, Long> fun = d -> Math.round(d);
        System.out.println(fun.apply(12.3));
        System.out.println("*****************");
        Function<Double, Long> fun1 = Math::round;
        System.out.println(fun1.apply(12.6));
    }

    // 情况三：类：：非静态方法（实例方法）—————有难度
    // comparator中的int compare(T t1,T t2)
    // String中的 int t1.compareTo(t2)
    // 要求接口中抽象方法的形参，作为非静态方法的调用者出现
    @Test
    public void test4() {
        Comparator<String> com = (s1, s2) -> s1.compareTo(s2);
        System.out.println(com.compare("abc", "abd"));
        System.out.println("*****************");
        Comparator<String> com1 = String::compareTo;
        System.out.println(com1.compare("abc", "abd"));
    }

    // Function中的R apply(T t)
    // Employee中的String getName()
    // 抽象方法传入Employee对象，作为非静态方法getName的调用者
    @Test
    public void test5() {
        Employee hyq = new Employee("hyq", 66);
        Function<Employee, String> fun = s -> s.getName();
        System.out.println("fun.apply(hyq) = " + fun.apply(hyq));
        System.out.println("*****************");
        Function<Employee, String> fun2 = Employee::getName;
        System.out.println("fun2.apply(hyq) = " + fun2.apply(hyq));
    }

    // 一：构造器引用
    // Supplier中的T get()
    // Employee中的空参构造器T Employee(), 二者都是没有输入，返回了一个东西
    @Test
    public void test6() {
        Supplier<Employee> sub = () -> new Employee();
        sub.get();
        System.out.println("*****************");
        Supplier<Employee> sub1 = Employee::new;
        sub1.get();
    }

    // BiFunction中的R apply(T t,U u)
    // T Employee(String name, int age)
    @Test
    public void test7() {
        BiFunction<String, Integer, Employee> bifun = new BiFunction<String, Integer, Employee>() {
            @Override
            public Employee apply(String s, Integer integer) {
                return new Employee(s, integer);
            }
        };
        System.out.println("*****************");
        BiFunction<String, Integer, Employee> bifun1 = (s, i) -> new Employee(s, i);
        System.out.println(bifun.apply("zzk", 16));
        System.out.println("*****************");
        BiFunction<String, Integer, Employee> bifun2 = Employee::new;
        System.out.println(bifun2.apply("zzz", 11));
    }

    // 二：数组引用, 实际上把后边new的数组看作是构造器就行了
    // function中的R apply(T t)
    @Test
    public void test8() {
        Function<Integer, String[]> func1 = length -> new String[length];
        String[] arr = func1.apply(5);
        System.out.println(Arrays.toString(arr));
        System.out.println("*****************");
        Function<Integer, String[]> func2 = String[]::new;
        String[] arr2 = func1.apply(10);
        System.out.println(Arrays.toString(arr2));
    }
}

class Employee {
    String name;
    int age;

    public Employee() {
        System.out.println("我出生拉~~");
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (age != employee.age) return false;
        return Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}