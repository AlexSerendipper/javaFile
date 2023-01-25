package usejava8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 【Stream的终止操作】
 *  1)终止操作一：匹配与查找
 *      allMatch(Predicate p)   检查是否匹配所有元素
 *      anyMatch(Predicate p)   检查是否至少匹配一个元素
 *      noneMatch(Predicate p)   检查是否没有匹配所有元素
 *      findFirst()   返回第一个元素
 *      findAny()   返回当前流中的任意(随机)元素
 *      count()   返回流中元素总数
 *      max(Comparator c)   返回流中最大值
 *      min(Comparator c)   返回流中最小值
 *      forEach(Consumer c)  内部迭代(集合中使用Iterator称为外部迭代。stream中forEach称为内部迭代)
 *                             内部迭代时最常用System.out::println，直接输出所有信息
 *  2)终止操作二：规约
 *     reduce(T iden, BinaryOperator b) ： iden为初始值，可以将流中元素反复结合起来，得到一个值。返回 T
 *     reduce(BinaryOperator b) ：可以将流中元素反复结合起来，得到一个值。返回 Optional<T>类
 *  3)终止操作三：收集
 *     collect(java.util.stream.Collector c) ：将流转换为其他形式。可以对流执行收集的操作(如收集到 List、Set、Map中)。
 *     Collectors实用类提供了很多静态方法，可以方便地创建常见收集器实例，如
 *     toList List<T>  ：把流中元素收集到List
 *     toSet Set<T>  ：把流中元素收集到Set
 *     toCollection Collection<T>  ：把流中元素收集到创建的集合
 @author Alex
 @create 2022-12-28-18:07
 */
public class UseJava804 {
    // 一、匹配与查找
    @Test
    public void test1(){
        // 是否所有员工的年龄都大于18岁
        List<Employee> employees = UseJava803.getEmployees();
        boolean b = employees.stream().allMatch(e -> e.getAge() > 18);
        System.out.println(b);
        System.out.println("*****************");
        // 返回第一个元素
        Optional<Employee> first = employees.stream().findFirst();
        System.out.println(first);
        System.out.println("*****************");
        // 返回流中元素总数
        long count = employees.stream().filter(e -> e.getAge() > 40).count();
        System.out.println(count);
        System.out.println("*****************");
        // 返回最高年龄的员工,由于employee本身没有实现comparator，我这里直接先取出所有的年龄
        Optional<Employee> max = employees.stream().max((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));
        System.out.println(max);
        System.out.println("*****************");
        // 返回最低的年龄
        Optional<Integer> min = employees.stream().map(e -> e.getAge()).min(Integer::compareTo);
        System.out.println(min);
        System.out.println("*****************");
        // 内部迭代
        employees.stream().forEach(System.out::println);
    }

    // 二、规约
    @Test
    public void test2(){
        // 练习1：计算1-10的自然数的和
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // 相当于是初始值为0，f(x,y) = x + y = z，等于是0 + 1的值作为输入继续传入函数 1 + 2
        Integer reduce = list.stream().reduce(0, (x1, x2) -> Integer.sum(x1, x2));
        System.out.println(reduce);
        System.out.println("*****************");
        // 练习2：计算公司所有员工的年龄总和
        List<Employee> employees = UseJava803.getEmployees();
        Optional<Integer> reduce1 = employees.stream().map(e -> e.getAge()).reduce((x1, x2) -> Integer.sum(x1, x2));
        System.out.println(reduce1);
    }

    // 三、 收集
    @Test
    public void test3(){
        // 练习：查找年龄大于30岁的人，结果返回一个list和set
        List<Employee> employees = UseJava803.getEmployees();
        List<Employee> list = employees.stream().filter(e -> e.getAge() > 30).collect(Collectors.toList());
        list.forEach(System.out::println);  // 注意，这是list中的foreach方法
        System.out.println("*****************");
        Set<Employee> collect = employees.stream().filter(e -> e.getAge() > 40).collect(Collectors.toSet());
        collect.forEach(System.out::println);  // 注意，这是list中的foreach方法
    }
}
