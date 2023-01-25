package useoop;

import org.junit.Test;

/**
 * 【内部类】使用频率比代码块还低
 *  当一个事物的内部，还有一个部分需要一个完整的结构进行描述，而这个内部的完整的结构又只为此事物提供服务，
 * 那么整个内部的完整结构最好使用内部类。如：（Person类中可能原来有一个brain对象属性，这个brain仅用于Person，不想再专门写一个类，可以将brain定义为内部类）
 *  在Java中，允许一个类的定义位于另一个类的内部，前者称为内部类，后者称为外部类
 *  定义格式为：Inner class1
 *     在外部引用它时必须给出完整的名称。
 *     内部类的名字不能与包含它的外部类类名相同
 *
 * 【内部类的分类】与变量相同
 *  1）在类内部声明的类称为成员内部类
 *      static成员内部类
 *      非static成员内部类
 *  2）在方法内、代码块内声明的类称为局部内部类
 *
 * 【成员内部类】成员内部类作为类的成员的角色：
 *  和外部类不同，Inner class还可以声明为private或protected；
 *  可以声明为abstract类 ，因此可以被其它的内部类继承
 *  可以声明为static的，但此时就不能再使用外层类的非static的成员变量
 *     非static的成员内部类中的成员不能声明为static的，只有在外部类或static的成员部类中才可声明static成员。
 *  可以声明为final类。
 *  可以在内部定义属性、方法、构造器等结构
 *  可以调用外部类的结构
 *     成员内部类可以直接使用外部类的所有成员，包括私有的数据
 *     外部类.this.属性：来调用外部类的结构。再内部类中直接用this调用的是内部类的属性
 *  可以访问内部类的结构
 *     外部类访问成员内部类的成员，需要先创建内部类对象，再通过“内部类.成员”或“内部类对象.成员”的方式访问
 *     当想要在外部类的静态成员（如静态方法中）使用内部类时，可以考虑内部类声明为静态的
 *  编译以后生成OuterClass$InnerClass.class字节码文件（也适用于局部内部类）
 *
 * 【局部内部类】在方法内、代码块内、构造器内声明的类称为局部内部类
 *  只能在声明它的方法或代码块中使用，而且是先声明后使用。除此之外的任何地方都不能使用该类
 *  但是它的对象可以通过外部方法的返回值返回使用，返回值类型只能是局部内部类的父类或父接口类型
 *  局部内部类可以使用外部类的成员，包括私有的
 *  ✔局部内部类可以使用外部方法的局部变量，但是必须是final的。这是由局部内部类和局部变量的声明周期不同所致。
 *  ✔局部内部类和局部变量地位类似，不能使用public,protected,缺省,private等权限修饰符修饰
 *  局部内部类不能使用static修饰，因此也不能包含静态成员
 *
 * 【匿名内部类】
 *  匿名内部类不能定义任何静态成员、方法和类，只能创建匿名内部类的一个实例。
 *   一个匿名内部类一定是在new的后面，用其隐含实现一个接口或实现一个类。
 *  格式：
 *      new 父类构造器（实参列表）|实现接口(){
 *              //匿名内部类的类体部分
 *      }
 *  匿名内部类必须继承父类或实现接口
 *  匿名内部类只能有一个对象
 *  匿名内部类对象只能使用多态形式引用
 *
 @author Alex
 @create 2023-01-10-19:49
 */
public class UO16 {
    @Test
    public void test1(){
        // 访问内部静态类时，需要创建内部类对象
        Human.Hand hand = new Human.Hand();
        hand.show();
        System.out.println("*****************");
        // 访问内部非静态类，需要先创建外部类对象，再创建内部类对象
        Human human = new Human();
        Human.Brain brain = human.new Brain();
        brain.test();
        System.out.println("*****************");
        brain.display(10);
        System.out.println("*****************");
        // 当外部类的属性与方法和内部类重名时
        human.eat();
        brain.eat();
    }
}

class Human {
    int age = 99;
    // 想要在外部类的静态成员部分使用内部类时，可以考虑内部类声明为静态的
    Hand h = new Hand();

    public void eat() {
        System.out.println("人在狠狠吃");
    }

    // 非静态成员内部类
    class Brain {
        int age;

        // 成员内部类作为类的内部成员，还可以调用外部类的结构。此处省略了Human.this.eat();
        public void test() {
            eat();
        }

        // ✔当外部类的属性/方法和内部类重名时
        public void display(int age) {
            System.out.println("the age you key in is:" + age);  // 1 调用形参
            System.out.println("your brain's age is:" + this.age);  // 2 调用内部类的属性
            System.out.println("your age is:" + Human.this.age);  // 3 调用外部类的属性
            Human.this.eat();  // 4 调用外部类的方法
        }

        public void eat() {
            System.out.println("吃掉你的脑子");
        }
    }

    // 静态成员内部类
    static class Hand {
        static int length;

        public void show() {
            System.out.println("给你一巴掌");
        }
    }

    // 局部内部类:实现返回实现了Comparable接口的对象
    // 局部内部类使用在代码块和构造器中的情况非常罕见
    public Comparable getMyComparable(){
        class MyComparable implements Comparable<Integer>{
            @Override
            public int compareTo(Integer o) {
                return 0;
            }
        }
        return new MyComparable();
    }

}

