package useoop;

import org.junit.Test;

/**
 * 【this】
 *  它在方法内部使用，即这个方法所属对象的引用；
 *  它在构造器内部使用，表示该构造器正在初始化的对象。
 *  this 可以调用类的属性、方法和构造器
 *  当在方法内需要用到调用该方法的对象时，就用this。
 *   ✔具体的：我们可以用this来区分属性和局部变量。比如：this.name（类中的属性） = name（构造器中的局部变量）
 *   ✔许多时候，属性和局部变量没有重名，我们会省略this
 *
 * 【this(形参列表)】
 *  在类的构造器中使用"this(形参列表)"的方式，调用本类中重载的其他的构造器！
 *  ✔构造器中不能通过"this(形参列表)"的方式调用自身构造器
 *  如果一个类中声明了n个构造器，则最多有n-1个构造器中使用了"this(形参列表)"
 *  "this(形参列表)"必须声明在类的构造器的首行！
 *  在类的一个构造器中，最多只能声明一个"this(形参列表)"
 *
 * 【package】
 *  package语句作为Java源文件的第一条语句，指明该文件中定义的类所在的包。(若缺省该语句，则指定为无名包)。
 *  格式为：package 顶层包名.子包名;
 *   如pack1\pack2\PackageTest.java  声明为package pack1.pack2;
 *  包对应于文件系统的目录，package语句中，用 “.” 来指明包(目录)的层次；
 *  包通常用小写单词标识。通常使用所在公司域名的倒置：com.atguigu.xxx
 *  包帮助管理大型软件系统：将功能相近的类划分到同一个包中。比如：MVC的设计模式
 *
 * 【MVC设计模式】
 *  MVC是常用的设计模式之一，将整个程序分为三个层次：视图模型层，控制器层，与数据模型层。
 *   这种将程序输入输出、数据处理，以及数据的展示分离开来的设计模式使程序结构变的灵活而且清晰，
 *   同时也描述了程序各个对象间的通信方式，降低了程序的耦合性。
 *  模型层 model 主要处理数据
 *  控制层 controller 处理业务逻辑
 *  视图层 view 显示数据
 *
 * 【JDK中主要的包介绍】
 * 1. java.lang----包含一些Java语言的核心类，如String、Math、Integer、 System和Thread，提供常用功能
 * 2. java.net----包含执行与网络相关的操作的类和接口。
 * 3. java.io ----包含能提供多种输入/输出功能的类。
 * 4. java.util----包含一些实用工具类，如定义系统特性、接口的集合框架类、使用与日期日历相关的函数。
 * 5. java.text----包含了一些java格式化相关的类
 * 6. java.sql----包含了java进行JDBC数据库编程的相关类/接口
 * 7. java.awt----包含了构成抽象窗口工具集（abstract window toolkits）的多个类，这些类被用来构建和管理应用程序的图形用户界面(GUI)。（awt是用来写客户端的包，现在已经不用了）
 *
 * 【import】告诉编译器到哪里去寻找类
 *  为使用定义在不同包中的Java类，需用import语句来引入指定包层次下所需要的类或全部类(.*)。
 *  语法格式：import 包名. 类名;
 *  import声明在包的声明和类的声明之间。
 *  如果需要导入多个类或接口，那么就并列显式多个import语句即可
 *  可以使用.*的方式，一次性导入包下所有的类或接口
 *  ✔如果导入的类或接口是java.lang包下的，或者是当前包下的，则可以省略此import语句。
 *  ✔如果在代码中使用不同包下的同名的类。那么就需要使用类的全类名的方式指明调用的是哪个类
 *  ✔如果已经导入java.a包下的类。那么如果需要使用a包的子包下的类的话，仍然需要导入。
 *  import static组合的使用：调用指定类或接口下的静态的属性或方法
 *
 * 【super】
 *  super和this的用法相像，this代表本类对象的引用，super代表父类的内存间的标识
 *  super可用于调用父类的属性和方法
 *   ✔常用来区分子类中与父类重名的属性或重写的方法
 *   ✔未出现重名时可以省略
 *
 * 【super(形参列表)】可用于在子类构造器中调用父类的构造器
 *  ✔子类中所有的构造器默认都会访问父类中空参数的构造器。即构造器首行没有声明调用this/super构造器，默认调用super()。
 *  ✔当父类中没有空参数的构造器时，子类的构造器必须通过this(参数列表)或者super(参数列表)语句指定调用本类或者父类中相应的
 *    构造器。同时，只能”二选一” ，且必须放在构造器的首行。。。否则编译报错
 *  即子类构造器一定间接或直接调用了父类的构造器（所以通常会在父类中声明一个空参构造器防止报错）
 *
 * 【this与super在继承方面的区别】
 * 区别点                this                                                         super
 * 1 访问属性         访问本类中的属性，如果本类没有此属性则从父类中继续查找           直接访问父类中的属性
 * 2 调用方法         访问本类中的方法，如果本类没有此方法则从父类中继续查找           直接访问父类中的方法
 * 3 调用构造器       调用本类构造器，必须放在构造器的首行                            调用父类构造器，必须放在子类构造器的首行
 *
 * 【子类对象的实例化过程】
 *  由大及小，父类先行
 *
 @author Alex
 @create 2023-01-10-13:19
 */
public class UO06 {
    // 子类对象的实例化过程
    @Test
    public void test1(){
        Wolf wolf = new Wolf();
        // 注意，这里wolf重写了sleep方法，所以this指向子类重写的方法✔✔✔
        // ✔✔✔.getClass方法也应当如此理解，子类也同样重写了.getClass方法， 故该this指向的是子类wolf，而不是animal
    }


    class Creature {
        public Creature() {
            System.out.println("Creature无参数的构造器");
        }
    }

    class Animal extends Creature {
        public Animal(String name) {
            System.out.println("Animal带一个参数的构造器，该动物的name为" + name);
        }

        public Animal(String name, int age) {
            this(name);  // 调用本类中重载的其他构造器
            System.out.println("Animal带两个参数的构造器，其age为" + age);
            this.sleep();
            System.out.println(this.getClass().getName());
        }
        public void sleep(){
            System.out.println("animal sleep now~");
        }
    }

    public class Wolf extends Animal {
        public Wolf() {
            super("灰太狼", 3);
            System.out.println("Wolf无参数的构造器");
        }

        public void sleep(){
            System.out.println("wolf sleep now~");
        }
    }
}
