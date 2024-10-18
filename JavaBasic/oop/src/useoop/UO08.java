package useoop;

import org.junit.Test;

import java.util.Random;

/**
 * 【多态性】
 *  父类的引用指向子类的对象（或说子类的对象赋给父类的引用）
 *  若编译时类型和运行时类型不一致，就出现了对象的多态性(Polymorphism)
 *    编译时“看左边”：看的是父类的引用（父类中不具备子类特有的方法）
 *    运行时“看右边”：看的是子类的对象（实际运行的是子类重写父类的方法✔）
 *  向上转型：如Person e = new Student();
 *   ✔属性和方法是在编译时确定的，编译时e为Person类型，所有只能调用Person的属性和方法，否则报错
 *
 * 【虚拟方法调用】多态的核心，也称为动态绑定
 *  多态情况下，当子类中定义了与父类同名同参数的方法，将此时父类的方法称为虚拟方法。
 *  虚拟方法调用：父类会根据赋给它的不同子类对象，动态调用属于子类的该方法。这样的方法调用在编译期是无法确定的，所以也称为动态绑定
 *   ✔编译时e为Person类型，而方法的调用是在运行时确定的，所以调用的是Student类中的同名同参方法
 *  多态的前提：
 *   1）需要存在继承或者实现关系
 *   2）有方法的重写
 *
 * 【强制类型转换Casting】基本数据类型的Casting分为自动类型转换和强制类型转换。对象也一样
 * 1）问题引入
 *   属性不具备多态性，只看引用变量所声明的类，即只能调用Person的属性
 *   student类中特有的方法也不具备多态性，无法被调用
 *   但实际上，new Student()后，内存空间里就有Student的属性和特有的方法，所以只能通过对象类型转换 (Casting)调用Student类中的属性和特有方法
 * 2）自动类型转换：即向上转型，可以自动进行
 * 3）强制类型转换
 *   Java对象的强制类型转换称为造型
 *   a instanceof b  ：为了避免造型时出现异常，通常使用instanceof进行判断。判断a在内存空间中是否包括了b的属性和方法（子类是对父类的扩展，所以包括了父类的属性和方法）
 *                     ：更通俗的理解是a是否为b的子类
 *   使用格式为：Student s  = (Student)e
 *
 *
 @author Alex
 @create 2023-01-10-14:04
 */
public class UO08 {
    @Test
    public void test1() {
        Sub s = new Sub();
        Base b = s;  // 多态，此处赋值赋的就是地址值。故下方地址值相同。。。。或者写成 Base b = new Sub()
        System.out.println(b == s);
        System.out.println(b.count);  // 多态性不适用于属性
        b.display();  // 难点!由于虚拟方法调用，执行子类的方法，通过方法显示了子类中特有的属性
        System.out.println("*****************");
        b.add(1, 2, 3);
        s.add(1,2,3);
        System.out.println("*****************");
        Sub c = (Sub) b;
        System.out.println(c.count); // 强转显示特有的属性
    }

    class Base {
        int count = 10;
        public void display() {
            System.out.println(this.count);
        }
        public void add(int a, int... arr) {
            System.out.println("base");
        }
    }

    class Sub extends Base {
        int count = 20;
        public void display() {
            System.out.println(this.count);
        }

        // 注意：该方法构成了重载
        public void add(int a, int[] arr) { // 考察1：这算不算重写！！！！！！——当然算
            System.out.println("sub_1");
        }

        // 该方法为新方法（并非重写）
        public void add(int a, int b, int c) { // 考察2：打开这个之后，输出为？——理论上优先执行固定参数的 //
            System.out.println("sub_2");
        }
    }

    // 多态是编译时行为还是运行时行为？ 如何证明？
    // 提示：多态是运行时行为，体现在编译的时候看不出来，只能运行才能看出来
    @Test
    public void test2(){
        for (int i = 0; i < 10; i++) {
            int key = new Random().nextInt(2);  //返回int随机数，每个值在[0,2)之间。
            Base b = getInstance(key);//调用getInstance()方法返回一个Animal类的对象
            b.display();
        }
    }

    public Base getInstance(int key){
        switch (key){
            case 0 :
                return new Base();
            case 1 :
                return new Sub();
            default:
                return null;
        }
    }




    @Test
    public void test666(){
        Animal o = new Duck();
        o.fly();
    }
    class Animal{
        public void fly(){
            System.out.println("起飞撸");
        }
    }
    class Duck extends Animal{
        public void fly(){
            System.out.println("起飞");
        }
    }
}
