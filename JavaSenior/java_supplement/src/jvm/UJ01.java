package jvm;

/**
 * 【JVM的位置】
 *  整个软件系统从下至上为：
 *    硬件体系（intel）==>操作系统（windows,linux）==>虚拟机（jre，jre中有jvm）==>软件（xxx.java）
 *  整个软件系统从上至下为：见xmind
 *    xxx.java==>被编译为Class File==>类加载器（Class Loader）==>jvm
 *    在jvm中的运行时数据区，通常我们发生运行时异常都是在这个区域
 *     本地方法栈通过本地方法接口，调用本地方法库
 *     栈里面是没有垃圾的（用完就弹出），本地方法栈和程序计数器中也没有垃圾。。。垃圾主要都在堆和方法区中，所谓的jvm调优，主要都是调堆和方法区
 *
 * 【类加载器】详见java反射章中的ClassLoader类加载器
 *
 * 【沙箱安全机制】了解即可
 *  Java安全模型的核心就是Java沙箱(sandbox) ,
 *    沙箱是一个限制程序运行的环境。沙箱机制就是将Java代码限定在虚拟机(JVM)特定的运行范围中，
 *    并且严格限制代码对本地系统资源访问（CPU、内存、文件系统、网络。不同级别的沙箱对这些资源访问的限制也可以不一样），
 *    通过这样的措施来保证对代码的有效隔离，防止对本地系统造成破坏。
 *  JDK1.0安全模型：本地代码默认视为可信任的，而远程代码则被看作是不受信的。对于授信的本地代码,可以访问一切本地资源。
 *   JDK1.1安全模型：针对安全机制做了改进，增加了安全策略，允许用户指定代码对本地资源的访问权限（也就可以允许远程代码访问本地系统的文件）
 *   Java1.2版本中：再次改进了安全机制，增加了代码签名。不论本地代码或是远程代码，都会按照用户的安全策略设定，由类加载器加载到虚拟机中权限不同的运行空间，来实现差异化的代码执行权限控制。
 *   Java1.6版本中（最新的安全模型）：引入了域(Domain)的概念。虚拟机会把所有代码加载到不同的系统域和应用域, 系统域部分专门负责与关键资源进行交互，而各个应用域部分则通过系统域的部分代理来对各种需要的资源进行访问。
 *  组成沙箱的基本组件
 *   （1）字节码校验器(bytecode verifier) :确保Java类文件遵循Java语言规范。
 *   （2）类裝载器(class loader) :其中类装载器在3个方面对Java沙箱起作用
 *        它防止恶意代码去干涉善意的代码;                     # 双亲委派机制
 *        它守护了被信任的类库边界;                          # 双亲委派机制
 *        它将代码归入保护域,确定了代码可以进行哪些操作。      # 沙箱安全机制
 *
 @author Alex
 @create 2023-06-04-13:13
 */


public class UJ01 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println();
            }
        }).start();
    }
}
