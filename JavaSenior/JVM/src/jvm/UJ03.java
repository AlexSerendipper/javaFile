package jvm;

/**
 * 【JVM组成2】
 *
 * 【三种JVM】我们学习都是: Hotspot
 *  Sun公司HotSpot。Java Hotspot™ 64-Bit Server VM (build 25.181-b13,mixed mode)
 *  BEA JRockit。
 *  IBM J9VM。
 *
 * 【堆】
 *  一个JVM只有一个堆内存，并且堆内存的大小是可以调节的。
 *  存放内容：
 *     类的实例化对象，包括实例对象的方法、常量、变量等
 *  堆内存中还要细分为三个区域:
 *     新生区(伊甸园区) Young/New
 *     养老区 old
 *     永久区 Perm
 *  永久存储区里存放的都是Java自带的类，例如lang包中的类 如果不存在这些，Java就跑不起来了
 *    ✔在JDK8以后，永久存储区改了个名字(元空间)
 *  GC垃圾回收
 *     主要是在伊甸园区和养老区~~ 幸存区是新生区和养老区的过度，一般不会被回收
 *     针对新生区的垃圾回收称为轻GC。。。如果养老区的内存都满了，会自动进行重GC（FULL GC），会清楚很多东西
 *
 @author Alex
 @create 2023-06-04-17:01
 */
public class UJ03 {
}
