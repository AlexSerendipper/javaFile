package useio;

import org.junit.Test;

import java.io.*;

/**
 * 【对象流】ObjectInputStream和OjbectOutputSteam
 *  用于存储和读取基本数据类型数据或对象的处理流。它的强大之处就是可以把Java中的对象写入到数据源中，也能把对象从数据源中还原回来。
 *  序列化：用ObjectOutputStream类保存基本类型数据或对象的机制
 *  ✔对象序列化机制：允许把内存中的Java对象转换成平台无关的二进制流，从
 *   而允许把这种二进制流持久地保存在磁盘上，或通过网络将这种二进制流传输到另一个网络节点。
 *  反序列化：用ObjectInputStream类读取基本类型数据或对象的机制
 *  ✔对象反序列化机制：当其它程序获取了这种二进制流，就可以恢复成原来的Java对象
 *  对象流不能序列化static和transient修饰的成员变量

 *  【自定义类可序列化的条件】
 * （1）实现接口Serializable
 * （2）提供一个全局常量：序列版本号private static final long serialVersionUID;
 * （3）✔如果类没有显示定义这个静态常量，系统会自动生成。
 *      ✔若类的实例变量做了修改，serialVersionUID 可能发生变化。故建议显式声明
 * （4）必须保证其内部的所有属性都可序列化（默认情况下，基本数据类型可以序列化）
 * （5）ObjectOutputStream和ObjectInputStream不能序列化static和transient修饰的成员变量
 *
 * 【常用方法】
 *   ObjectOutputStream.writeObject(对象)            # 输出可序列化对象。✔注意写出一次，操作flush()一次
 *   readObject()                                    # 读取流中的对象
 *
 * 【谈谈你对java.io.Serializable接口的理解，我们知道它用于序列化，是空方法接口，还有其它认识吗？】
 *  实现了Serializable接口的对象，可将它们转换成一系列字节，并可在以后完全恢复回原来的样子。
 *   这一过程亦可通过网络进行。这意味着序列化机制能自动补偿操作系统间的差异。
 *   换句话说，可以先在Windows机器上创建一个对象，对其序列化，然后通过网络发给一台Unix机器，然后在那里
 *   准确无误地重新“装配”。不必关心数据在不同机器上如何表示，也不必关心字节的顺序或者其他任何细节。
 *  由于大部分作为参数的类如String、Integer等都实现了java.io.Serializable的接口，也可以利用多态的性质，作为参数使接口更灵活。
 @author Alex
 @create 2022-12-19-10:50
 */

public class UseIO05 {
    // 序列化自定义类, 注意写出一次，操作flush()一次
    // 正常开发时，一般只会写入同一种类型的数据
    @Test
    public void test2() throws IOException {
        // 1. 创建对象流
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("object.dat")));

        // 2. 操作
        objectOutputStream.writeObject(new Person("zzj",17));  // String已经重写过serialize方法，可以直接用
        objectOutputStream.flush();  // 刷新操作
        objectOutputStream.writeObject(new String("zzj"));
        objectOutputStream.flush();  // 刷新操作

        // 3. 关闭资源
        objectOutputStream.close();
    }

    // ✔自定义类的反序列化,按照序列化写入的顺序进行反序列化
    @Test
    public void test3() throws IOException, ClassNotFoundException {
        // 1. 创建对象流
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("object.dat")));

        // 2. 操作
        Object o = objectInputStream.readObject();
        System.out.println(o);
        Object o1 = objectInputStream.readObject();
        System.out.println(o1);

        // 3. 关闭资源
        objectInputStream.close();
    }
}


// Serializable没有抽象方法，这种接口一般称为标识接口
class Person implements Serializable{
    public static final long serialVersionUID = 1234125134L;  // 如果不定义该变量，系统自动生成，当你改变属性时，可能反序列化会出错
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
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
        return "person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}