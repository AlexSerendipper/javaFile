package mutilThread;

/**
 * 【Volatile】两个作用
 * （1）保证线程 可见性/一致性
 *  指的是 volatile 类型的变量，其变量值一旦被修改，其他线程就能够立刻感知到
 *   如果对声明了 volatile 变量将进行写操作，JVM就会向处理器发送一条Lock前缀的指令，将这个变量所在的缓存行的数据立即写回到系统内存。
 *   并且cpu读取volatile标识的变量时 只能从系统内存中读取，不能从缓存中读取
 *   这样便保证了线程的可见性
 * （2）禁止指令重排序
 *  指的是被 volatile 修饰的变量，其执行顺序不能被重排序
 * 
 * 【happens-before原则】JVM中，规定了8种程序不可以乱序执行的情况！这八种情况具体无须背诵，只需要知道存在这么个东西即可✔✔！
 *  程序次序规则：同一个线程内，按照代码出现的顺序，前面的代码 先行于 后面的代码。
 *  管程锁定规则： unlock 操作先行发生于后面对同一个锁的 lock 操作。
 *  volatile规则：对 volatile 变量的 写操作 先行发生于 后面对这个变量的读操作。
 *  线程启动规则：线程的 start()方法 先行发生于该线程的每个操作。
 *  线程终止规则：线程中所有操作先行发生于对线程的终止检测。
 *  对象终结规则：对象的初始化完成 先行发生于 finalize()方法。
 *  传递性：如果操作 A 先行发生于操作 B，操作 B 先行发生于操作 C，那么操作 A 先行发生于操作C 。
 *
 * 【volatile的底层实现】即volatile底层如何实现 禁止指令重排序以及线程可见性
 * （0）java层面：对指定变量使用volatile标记
 *      字节码层面: 指定表面添加了ACC_VOLATILE标记
 * （1）✔JVM层面：JVM中使用了JSR内存屏障机制规范，屏障两端的指令不允许重排序
 *      JVM层面规定了必须要实现四种逻辑屏障————针对读读LoadLoad，读写LoadStore，写读StoreLoad，写写StoreStore的屏障。
 *      （具体针对不同的cpu，存在以下三种实现方式，具体实现代码层面是控制不了的）
----------------------
        %%StoreStore Barrier%%           // 该屏障即保证了，对屏障两端的 Volatile写 指令不可交换顺序
    对Volatile标记的变量进行写指令
        %%StoreLoad Barrier%%            // 该屏障即保证了，必须先完成 Volatile写 （其他线程）才能进行 Volatile读，即保证了线程之间的可见性

        %%LoadLoad Barrier%%             // 该屏障好像没啥意义
    对Volatile标记的变量进行读指令
        %%LoadStore Barrier%%            // 该屏障即保证了，必须先完成 Volatile读 （其他线程）才能进行 Volatile写，即保证了线程之间的可见性
----------------------
 * （2）hotSpot层面：调用了lock;addl（汇编语言）
 *                  这里是hotSpot中的lock指令，具体是调用 cpu锁定缓存行 或者 锁总线来实现的
 * （3）✔✔✔cpu层面：① 使用MESI来实现，也即缓存数据的一致性
 *                      ② 使用sfence/ifence/mfence（intel cpu实现屏障的指令）实现JVM规定的各种屏障（原语支持）
 *                      ③ 或使用lock指令，锁总线！
 *     这里特别绕！！，MESI协议是cpu本身就存在的，是cpu的一套固有机制，无论是否声明volatile，只要变量在CPU缓存中！就会通过这个协议保障缓存可见性。
 *     那既然有了MESI协议，为什么还要volatile？
 *     这是因为cpu中还引入了store buffer和invalidate queue等存储结构来优化性能。。若当数据仅存在于L1/L2 cache中时，MESI足可以保证volatile的内存语义（即线程可见性和禁止指令重排序）
 *     但如果数据存在于store buffer或者invalidate queue中时，MESI将失效。并且，并不是所有的cpu都支持MESI协议
 *     这时候，就需要通过 明确的fence指令 或者 锁总线的方式，来保证volatile想达到的目标。
 *
 * 【对象创建过程】半初始化问题，以汇编码示例
 * （1）new #2 <T>                      # 申请一段内存空间，此时对象的属性值都是默认值（如int默认为0）
 * （2）dup                             # 暂时不用管
 * （3）invokespecial #3 <T.<<init>>    # 调用构造方法，初始化对象，为对象中的各个属性赋值
 * （4）astore_1                        # 建立关联，将引用指向对象地址。
 * （5）return
 *  如果发生了指令重排序，在调用构造方法前，先建立了关联。。。此时该对象,引用指向的对象此时是默认值，别的线程此时读到的是对象的默认值而出现错误。
 *
 * 【系统底层如何实现数据有序性】并发编程的三大特性之有序性（可见性、有序性、原子性）
 *   Java层面，使用volatile即可禁止指令重排序
 *   JVM中使用了JSR内存屏障机制规范，屏障两端的指令不允许重排序
 *   cpu层面使用sfence/ifence/mfence实现JVM规定的各种屏障（原语支持），或者通过lock指令，锁总线！的方式来实现
 *
 * 【系统底层如何实现数据原子性】并发编程的三大特性之原子性（可见性、有序性、原子性）
 *   cpu层面提供了lock指令，通过锁总线的方式，在当前cpu访问时，不允许其他的cpu访问。。。从而保证了数据操作的原子性
 *
 * 【多线程下的单例模式DCL】见下方
 *  问：private static volatile UM04 INSTANCE; 是否一定要加volatile
 *    答：一定需要，因为new一个对象时，实际上分为 (1)为对象分配内存地址 (2)构造器赋值 (3) 建立引用，堆栈之间建立引用
 *        如果不加volatile，指令乱序执行，先执行了3，对象中属性均为初始值（如int=0）
 *        此时若第二个线程进来拿走了对象，由于构造器还未赋值，将造成不可估量的后果
 *
 @author Alex
 @create 2023-06-28-15:17
 */
public class UM04 {
    private static volatile UM04 INSTANCE;

    private UM04(){

    }

    // 多线程下的单例模式，其中因为该方法中可能还会有其他的业务逻辑，所以无法简单的为整个方法加synchronized，需要将线程安全的范围缩小
    // 其中双重检查：当第一个线程进入if1(第一个if语句)后，假如第二个线程此时抢到了锁，最后new了一个对象 然后归还锁
    //              此时第一个线程拿到了锁，假设没有if2(第二个if语句)，第一个线程还会继续new一个新对象，从而无法实现单实例
    public static UM04 getINSTANCE() throws Exception{
        // 业务代码省略

        if(INSTANCE == null){
            // 双重检查
            synchronized (UM04.class){
                if(INSTANCE==null){
                    Thread.sleep(1);
                    INSTANCE = new UM04();
                }
            }
        }
        return INSTANCE;
    }
}
