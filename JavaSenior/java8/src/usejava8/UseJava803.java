package usejava8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *【StringAPI(java.util.stream)】Java8中有两大最为重要的改变。第一个是 Lambda 表达式；另外一个则是 Stream API。
 *
 *【Stream和Collection集合的区别】Collection是一种静态的内存数据结构，存储数据，和内存打交道！而Stream是有关计算的，和cpu打交道！
 *   1） Stream 自己不会存储元素。
 *   2） Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
 *   3) Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执
 *
 *【Stream执行流程】、
 *   1) Stream的实例化：通过数据源（如：集合、数组）获取Stream
 *   2) 一系列的中间操作（过滤、映射....）: 对数据源进行处理
 *   3) 终止操作（延迟操作）：一旦执行终止操作，就执行中间操作链，并产生结果。之后中间操作和Stream都不会再被使用，该流被关闭
 *
 *【创建Stream的四种方式】
 *   1）通过集合，调用Collection的如下方法
 *      default Stream<E> stream() : 返回一个顺序流
 *      default Stream<E> parallelStream() : 返回一个并行流
 *   2）通过数组，调用Arrays的如下方法
 *      static <T> Stream<T> stream(T[] array): 返回一个流
 *   3）通过Stream类的静态方法 of(), 通过显示值创建一个流。它可以接收任意数量的参数
 *      public static<T> Stream<T> of(T... values) : 返回一个流
 *   4）通过Stream类的静态方法 Stream.iterate() 和 Stream.generate()创建无限流
 *     public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) 迭代
 *     public static<T> Stream<T> generate(Supplier<T> s)生成
 *
 * 【Stream的中间操作】
 *  1)中间操作一：筛选与切片
 *     filter(Predicate p):接收Lambda，从流中排除某些元素
 *     distinct():筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
 *     limit(long maxSize):截断流，使其元素不超过给定数量
 *     skip(long n):跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n)互补
 *  2)中间操作二：映射
 *     map(Function f) 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
 *     flatMap(Function f) 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
 *  3)中间操作三：排序
 *      sorted() 产生一个新流，其中按自然顺序排序。注意，若使用sorted()，要求被排序的对象要实现comparable接口
 *      sorted(Comparator com) 产生一个新流，其中按比较器顺序排序
 @author Alex
 @create 2022-12-28-18:07
 */


public class UseJava803 {
    // Stream的创建
    @Test
    public void test1() {
        // 创建Stream方式一：通过集合
        List<Employee> list = getEmployees();
        Stream<Employee> stream1 = list.stream();  // 返回一个顺序流
        Stream<Employee> employeeStream = list.parallelStream();  // 返回一个并行流
        System.out.println("*****************");
        // 创建Stream方式二：通过数组
        int[] arr = {1, 2, 3, 4, 5, 6};
        IntStream stream2 = Arrays.stream(arr);
        System.out.println("*****************");
        // 创建Stream方式三：通过Stream的of()：可以调用Stream类静态方法of, 直接放入数据
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
        System.out.println("*****************");
        // 创建Stream方式四（了解）：创建无线流。主要作用是帮助我们造数据
        // 遍历前10个偶数
        Stream.iterate(0, t -> t + 2).limit(10).forEach(System.out::println);
        // 输出10个随机数
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    // Stream的中间操作1:筛选与切片
    @Test
    public void test2() {
        List<Employee> employees = UseJava803.getEmployees();
        Stream<Employee> stream = employees.stream();
        // filter,查询员工表中年龄大于40的员工信息
        stream.filter(e -> e.getAge() > 40).forEach(System.out::println);
        System.out.println("*****************");
        // limit,限制输出个数
        // 注意，已经执行过终止操作，需要重新生成流
        employees.stream().limit(3).forEach(System.out::println);
        System.out.println("*****************");
        // skip,跳过指定元素
        employees.stream().skip(3).forEach(System.out::println);
        System.out.println("*****************");
        // distinct，去除重复元素(记得重写hashcode)
        employees.stream().distinct().forEach(System.out::println);
    }

    // 中间操作二：映射
    @Test
    public void test3() {
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);
        System.out.println("*****************");
        // 用map会得到嵌套的stream（这里好难，678集可以再看一下）
        Stream<Stream<Character>> streamStream = list.stream().map(str -> fromStringToStream(str));
        // 用flatMap得到的是直接拆出来的stream
        Stream<Character> characterStream = list.stream().flatMap(str -> fromStringToStream(str));
        characterStream.forEach(System.out::println);
    }

    // 该方法将字符串中的多个字符转换为对应的list的stream实例
    public static Stream<Character> fromStringToStream(String str) {
        ArrayList<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    // 中间操作三：排序
    @Test
    public void test4() {
        List<Integer> list = Arrays.asList(12, 34, 56, 42, 0, -12, 42);
        list.stream().sorted().forEach(System.out::println);
        System.out.println("*****************");
        List<Employee> employees = getEmployees();
        employees.stream().sorted((e1, e2) -> {
            return Integer.compare(e1.getAge(), e2.getAge());
        }).forEach(System.out::println);
    }

    public static List<Employee> getEmployees() {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("马化腾", 34));
        list.add(new Employee("马云", 14));
        list.add(new Employee("刘强东", 65));
        list.add(new Employee("雷军", 42));
        list.add(new Employee("李彦宏", 26));
        list.add(new Employee("任正非", 35));
        list.add(new Employee("扎克伯格", 1));
        list.add(new Employee("扎克伯格", 1));
        return list;
    }
}
