package usejava8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 【lambda表达式】
 *  举例： (o1,o2) -> Integer.compare(o1,o2)
 *    ->称为lambda操作符 或 箭头操作符
 *    箭头左侧为原抽象方法的形参列表
 *    箭头右侧为重写的抽象方法体
 *  使用（见下共六种情况）
 *    箭头左侧，lambda表达式形参列表的参数类型可以省略（类型推断），如果只有一个参数，小括号也可以省略✔
 *    箭头右侧，lambda体应该用一对大括号包裹，如果lambda中只有一条执行语句，可以省略return和大括号✔
 *  本质：快速创造了一个实现接口的实例（要求此接口只能有一个抽象方法，即函数式接口）✔
 *  使用场景
 *    当需要对一个函数式接口进行实例化时，可以使用函数式接口
 *    所以以前用匿名实现类表示的现在都可以用Lambda表达式来写。
 *
 * 【函数式接口】
 *  只包含一个抽象方法的接口，称为函数式接口。
 *  可以在一个接口上使用 @FunctionalInterface注解，检查它是否是一个函数式接口(编译器检查)
 *  Java内置四大核心函数式接口。其他接口见xmind图
 *    Consumer<T>消费型接口。参数类型T，返回类型void。包含方法：void accept(T t)
 *    Supplier<T>供给型接口。参数类型无，返回类型T。包含方法：T get()
 *    Function<T , R>函数型接口。参数类型T，返回类型R。包含方法：R apply(T t)
 *    Predicate<T>断定型接口。参数类型T，返回类型boolean。包含方法：boolean test(T t)
 *  何时使用函数式接口：如果开发中需要定义一个函数式接口，首先看看jdk已提供的函数式接口是否能满足需求，如果能，则直接调用即可
 @author Alex
 @create 2022-12-28-18:07
 */
public class UseJava801 {
    // 语法格式一：无参无返回值
    @Test
    public void test1() {
//        Runnable r0 = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("快速创造了一个实现runnable接口的实例");
//            }
//        };
        System.out.println("****************************");
        Runnable r1 = () -> System.out.println("快速创造了一个实现runnable接口的实例");
        r1.run();
    }

    // 语法格式二：需要参数但无返回值
    @Test
    public void test2() {
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        };
//        consumer.accept("hahaha");

        System.out.println("*****************");
        Consumer<String> con = (String s) -> {
            System.out.println(s);
        };
        con.accept("666");
    }

    // 语法格式三：数据类型可以省略，因为可由编译器推断得出（类型推断）
    @Test
    public void test3() {
        // 因为指定了泛型，所以s的数据类型可以省略
        Consumer<String> con = (s) -> {
            System.out.println(s);
        };
        con.accept("666");
        System.out.println("*****************");
        // 其他类型推断举例
        ArrayList<Integer> arrayList = new ArrayList<>();  // 之前见过的类型推断1
        int[] a = new int[]{1, 2, 3};
        int[] b = {4, 5, 6};   // 之前见过的类型推断2
    }

    // 语法格式四：lambda只有一个参数，参数的小括号可以省略
    @Test
    public void test4() {
        // lambda只需要一个参数，参数的小括号可以省略
        Consumer<String> con = s -> {
            System.out.println(s);
        };
        con.accept("666");
    }

    // 语法格式五：Lambda需要两个或以上的参数，多条执行语句，并且可以有返回值
    @Test
    public void test5() {
//        Comparator<Integer> comparator = new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                System.out.println("有多条执行语句1");
//                System.out.println("有多条执行语句2");
//                return Integer.compare(o1, o2);
//            }
//        };
//        int compare1 = comparator.compare(11, 22);
//        System.out.println(compare1);
        System.out.println("*****************");
        // lambda表达式, 大大滴偷懒
        Comparator<Integer> comparator2 = (o1, o2) -> {  // 类型推断
            System.out.println("有多条执行语句1");
            System.out.println("有多条执行语句2");
            return Integer.compare(o1, o2);
        };
        int compare2 = comparator2.compare(22, 11);
        System.out.println(compare2);
    }


    // 语法格式六：当Lambda体只有一条语句时，return与大括号若有，都可以省略
    @Test
    public void test6() {
        Comparator<Integer> comparator2 = (o1, o2) -> Integer.compare(o1, o2);
        int compare2 = comparator2.compare(22, 11);
        System.out.println(compare2);
    }

    // 核心函数式接口应用举例1
    @Test
    public void test7() {
//        happyTime(500, new Consumer<Double>() {
//            @Override
//            public void accept(Double aDouble) {
//                System.out.println("我花了" + aDouble);
//            }
//        });
        System.out.println("*****************");
        // ✔因为方法定义的是consumer接口，这里直接就默认Lambda表达式传入的是consumer接口，类型为double
        happyTime(1000, m -> System.out.println("我花了" + m));
    }

    public void happyTime(double money, Consumer<Double> con) {
        con.accept(money);
    }

    // 核心函数式接口应用举例2
    @Test
    public void test8() {
        List<String> list = Arrays.asList("北京", "南京", "天津", "东京", "精精");
        List<String> list1 = filterString(list, (s) -> s.contains("京"));
        System.out.println(list1);
    }

    // 根据给定的规则，过滤集合中的字符串，此规则由predicate抽象方法决定
    public List<String> filterString(List<String> list, Predicate<String> pre) {
        ArrayList<String> list1 = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)) {
                list1.add(s);
            }
        }
        return list1;
    }
}
