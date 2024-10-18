package useoop;

import org.junit.Test;

/**
 *
 *【static】可用static修饰属性、方法、代码块、内部类，
 *  static修饰的成员有以下特点：
 *    1）随着类的加载而加载。优先于对象存在
 *    2）修饰的成员，被所有对象所共享
 *    3）访问权限允许时，可不创建对象，直接被类调用
      4）都存在于方法区的静态域中
 *  ✔用static修饰属性称为类变量。如果想让一个类的所有实例共享数据，就用类变量！
 *  ✔用static修饰的方法称为类方法，由于不需要创建对象就可以调用类方法，可以用类名.方法名()直接调用，从而简化了方法的调用。
 *  ✔在静态方法内部只能访问静态属性和方法，不能访问非静态属性和方法（省略了类名.属性）
 *      非静态方法既可以调用静态属性和方法，也可以调用非静态属性和方法（省略了this.属性）
 *  static方法内部不能有this和super。 因为静态方法存在时，当前实例还未存在
 *  static修饰的方法不能被重写
 *  常见的静态结构，1）不同对象共享的属性 2)常量，通常都设置为静态属性；
 *                  3）操作静态属性的方法 4）工具类的方法，通常都设置为静态方法
 *
 * 【单例设计模式】
 *  设计模式：设计模式是在大量的实践中总结和理论化之后优选的代码结构、编程风格、以及解决问题的思考方式。
 * 设计模免去我们自己再思考和摸索。就像是经典的棋谱，不同的棋局，我们用不同的棋谱。”套路”
 *  所谓类的单例设计模式，就是采取一定的方法保证在整个的软件系统中，对某个类只能存在一个对象实例，
 * 并且该类只提供一个取得其对象实例的方法。
 *  单例模式的优点：由于单例模式只生成一个实例，减少了系统性能开销，当一个对象的产生需要比较多的资源时，
 * 如读取配置、产生其他依赖对象时，则可以通过在应用启动时直接产生一个单例对象，然后永久驻留内存的方式来解决。
 *
 * 【main方法】main作为程序的入口
 *  由于Java虚拟机需要调用类的main()方法，所以该方法的访问权限必须是public，
 * 又因为Java虚拟机在执行main()方法时不必创建对象，所以该方法必须是static的，
 * 该方法接收一个String类型的数组参数，该数组中保存执行Java命令时传递给所运行的类的参数
 *  又因为main()方法其本身就是一个静态方法，可以被调用，也可以调用本类中的其他方法。但我们不能直接访问该类中的非静态成员，
 * 必须创建该类的一个实例对象后，才能通过这个对象去访问类中的非静态成员，
 *  main方法也可以和控制台交互，
 * // (先run一次生成字节码文件) - 右键more run- modify run configurations - program argument(此处输入的都传入main中的 args)
 *
 * 【单元测试方法】
 * 1）右键选择当前工程，build path - configure build path - add libraries - Junit 4
 * 2）使用单元测试要求类是公共的，且提供公共的无参构构造器
 * 3）✔声明一个公共单元测试方法（不要返回值），此方法上需要声明注解@test，并导入包（实际开发中直接写test，根据提示可以自动完成前两步）
 * 4）此时写好测试代码后，右键单元测试方法名，run as Junit
 *
 @author Alex
 @create 2023-01-10-18:14
 */
public class UO11 {

    public static void main(String[] args) {
        // main方法作为静态方法，可以调用本类其他静态方法
        show();
        System.out.println("*****************");
        // main方法也可以和控制台交互
        // (先run一次生成字节码文件) - more run- modify run configurations - program argument(此处输入的都传入main中的 args)
        for(int i=0;i<args.length;i++) {
            System.out.println(args[i]);
        }
    }

    public static void show(){
        System.out.println("一个静态方法");
    }
}


/**
 * 单例设计模式之饿汉式——直接造好对象
 */
class Bank1{
    int balance = 0;

    // 1.私有化构造器
    private Bank1() {
    }
    // 2.内部提供一个当前类的实例 且实例也必须静态化
    private static Bank1 single = new Bank1();  // 对象属性~  关联
    // 3.提供公共的静态的方法，返回当前类的对象
    public static Bank1 getInstance() {
        return single;
    }

}

/**
 *  2. 单例设计模式之懒汉式 —— 啥时候用啥时候造
 */
class Bank2{
    int balance = 0;

    // 1.私有化构造器
    private Bank2() {
    }

    // 2.声明当前类对象，暂不初始化 且 对象也必须静态化（否则静态方法无法调用）
    private static Bank2 single;

    // 3.提供公共的静态的方法，返回当前类的对象
    public static Bank2 getInstance() {
        if(single == null) {
            single = new Bank2();
        }
        return single;
    }
}

// 3. 简陋版单例
class Bank3{
    int balance = 0;

    // 1.私有化构造器
    private Bank3() {
    }

    // 2. 公开静态属性
    public final static Bank3 single = new Bank3();  // 添加final，防止用户恶意修改
}

