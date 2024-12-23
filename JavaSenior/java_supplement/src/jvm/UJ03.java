package jvm;

/**
 * 【JVM组成2】
 * 【三种JVM】我们学习都是: Hotspot
 *  Sun公司HotSpot。Java Hotspot™ 64-Bit Server VM (build 25.181-b13,mixed mode)
 *  BEA JRockit。
 *  IBM J9VM。
 *
 * 【堆】
 *  一个JVM只有一个堆内存，并且堆内存的大小是可以调节的。
 *   ✔✔是JVM中所有线程共享的部分
 *  存放内容：
 *     类的实例化对象（包括实例对象的方法、变量等）、字符型常量池
 *  堆内存中还要细分为三个区域: 见xmind
 *     新生区/代(eden gen) Young/New： 包括伊甸园（Eden Space）、幸存者0区（from）、幸存者1区(to)
 *     养老（老年）区/代（old gen）
 *     永久（持久）区/代（Perm gen）： 实际上永久区在方法区中实现
 *  永久存储区里存放的都是Java自带的类，例如lang包中的类 如果不存在这些，Java就跑不起来了
 *    ✔在JDK8以后，永久存储区改了个名字(元空间)
 *  GC垃圾回收
 *     主要是在伊甸园区和养老区~~ 幸存区是新生区和养老区的过度，一般不会被回收
 *     ✔伊甸园满了就触发 轻GC，经过轻GC存活下来的就到了幸存者区，每熬过一次轻GC，年龄会加1，当年龄增加到15岁(默认)，就会晋升到老年代。。。
 *         养老区满了则触发 重GC。。。。如果重GC后内存仍不足，则触发OOM异常
 *     实际上99%的对象都是临时对象!
 *
 * 【新生区】
 *  所有的类 诞生和成长（甚至死亡）的地方
 *   所有的对象都是在伊甸园new出来的
 *
 * 【常量池详解】图见xmind
 *  静态常量池：每一个Class字节码文件，都会有一个对应的Class常量池（即静态常量池），其中包括了符号引用 和 字面量（数值型和字符串型）。。符号引用仅仅是引用了包和类名，加载到内存中后会变为具体引用
 *  运行时常量池：当Class字节码文件被classLoader加载到运行时内存中，内存会将静态常量池中的数据分成两个区域，一个是 运行时常量池 和 字符串常量池！！
 *   运行时常量池存储在方法区中✔，每一个Class对象（Class字节码文件）对应一个独立的运行时常量池，即其中的数据不共享
 *   运行时常量池中包括了直接引用（通过符号引用变换而来，引用具体的地址），以及 静态常量池中的数值型字面量
 *  字符串常量池：静态常量池中的字符型字面量单独存放在字符串常量池中，原因是帮助提高我们对重复字符串的利用！！
 *   字符型常量池存储在堆中（jdk1.7及之后），所有的String对象，都共享字符串常量池中的数据！✔
 *
 * 【持久（永久）区】
 *  这个区域常驻内存的。用来存放JDK自身携带的Class对象、Interface元数据等。。。存储的是Java运行时的一些环境~ 这个区域不存在垃圾回收，关闭虚拟机（jre）就会释放这个区域是内存
 *  如果一个启动类加载了大量第三方Jar包，或者tomcat部署了太多的应用。。是有可能在此处出现OOM异常的
 * （1）jdk1.6及之前
 *     方法区存储的是：静态变量(static)、类信息(.Class运行时类) 、运行时常量池（final常量） 以及 字符串常量池！！
 *     方法区的实现是通过永久代，使用的是jvm内存
 * （2）jdk1.7：见xmind
 *     将原来方法区的 静态变量和字符串常量池 存储在堆中✔，方法区中存储 类信息(.Class运行时类) 、运行时常量池（直接引用、字面量）
 *     方法区的实现方式是永久代，使用的是jvm内存
 * （3）jdk1.8：见xmind✔
 *     静态变量和字符串常量池 仍然存储在堆中，方法区中存储 类信息(.Class运行时类) 、运行时常量池（直接引用、字面量）
 *     方法区的实现方式是元空间，使用的是直接内存（本地内存），元空间是可以无限制使用本地内存（参数可调，默认分配电脑内存的1/4供其使用）
 *
 * 【为什么要使用元空间来替换永久代？】
 * （1）永久代的内存是存在上限的，虽然能够通过参数来设置，但是JVM加载的class总数大小是很难确定的，所以很容易出现OOM
 *      但是元空间存储在本地内存中，内存的空间大，可以很好的规避这个问题
 * （2）永久代的对象是通过fullGC进行垃圾回收的，也就是和老年代同时进行垃圾回收的。替换为元空间后，简化了fullGC的过程，提升了GC的性能
 * （3）oracle可能会将hotspot与Jrockit，而jrockit中是没有永久代的概念的
 *
 * 【JVM调优】
 * （1）堆内存设置
 *     edit configuration => program arguments => -Xms128M Xmx512m -XX:+PrintGCDetails                       # 设置最大最小堆内存，并打印GC垃圾回收的相关信息
 *      -Xms128M: 最小堆内存。。即jvm启动时的内存
 *      -Xmx512m: 最大堆内存。。即jvm运行过程中允许的最大内存，超出了这个设置值，就会抛出OutOfMemory异常。
 *      这两个数值可以设置相同
 * （2）OOM错误调优
 *     edit configuration => program arguments => -Xms128M Xmx512m -XX:+HeapDumpOnOutOfMemoryError           # 使用该命令可以在发生OOM异常时，✔Dump出 堆转储快照，堆转储快照可以使用分析工具JProfile对其进行分析（可以找到哪行代码出问题，内存中占用最多的变量是什么等信息）
 *
 *
 *
 @author Alex
 @create 2023-06-04-17:01
 */
public class UJ03 {
    public static void main(String[] args) {

    }
}
