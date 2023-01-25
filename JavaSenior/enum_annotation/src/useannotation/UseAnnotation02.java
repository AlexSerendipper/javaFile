package useannotation;

import java.lang.annotation.*;

/**
 * 【如何自定义注解：参照@suppressWarnings】
 * 1）注解声明为@interface
 * 2）Annotation的成员变量（属性）在 Annotation定义中以无参数方法的形式来声明。我们称为配置参数。类型只能是八种基本数据类型。
 * 3）可以使用default关键字在定义 Annotation的成员变量时为其指定初始值
 * 4）如果只有一个参数成员，建议使用参数名为value
 * 5）如果定义的注解含有配置参数，那么使用时必须指定参数值（除非它有默认值），✔
 * 格式是“参数名 =参数值”，如果只有一个参数成员，且名称为value，可以省略“value=”
 * 6）没有成员变量的Annotation称为标记; 包含成员变量的注解称为元数据注解✔
 * 7）注意：自定义注解必须配上注解的信息处理流程才有意义（反射）
 *
 * 【元注解】注解是对现有注解进行修饰的注解
 *  1）理解：String name = "zzj",这里string和name是元数据，是对现有数据zzj进行修饰的一个数据
 *     同理：元注解是对现有注解进行修饰的注解
 *  2）JDK5.0提供了4个标准的meta-annotation类型，分别是：
 *      @Retention 用于指定该Annotation的生命周期
 *          RetentionPolicy.SOURCE:在源文件中有效（即源文件保留），编译器直接丢弃这种策略的注释
 *          RetentionPolicy.CLASS:在class文件中有效（即class保留），当运行 Java程序时, JVM不会保留注解。这是默认值
 *          RetentionPolicy.RUNTIME:在运行时有效（即运行时保留），当运行Java程序时, JVM会保留注释。程序可以通过反射获取该注释。
 *      @Target
 *          ElementType.FIELD,  用于描述域
 *          ElementType.PARAMETER,  用于描述参数
 *          ElementType.CONSTRUCTOR,  用于描述构造器
 *          ElementType.LOCAL_VARIABLE，  用于描述局部变量
 *          ElementType.TYPE,  用于描述类、接口或enum声明
 *          ElementType.METHOD  用于描述方法
 *          ElementType.PACKAGE  用于描述包

 *      @Documented
 *          用于指定被该元 Annotation 修饰的 Annotation 类将被javadoc工具提取成文档。默认情况下，javadoc是不包括注解的。
 *          定义为Documented的注解必须设置Retention值为RUNTIME
 *      @Inherited
 *          被它修饰的 Annotation 将具有继承性。
 *          如果某个类使用了被@Inherited修饰的Annotation, 则其子类将自动具有该注解
 *
 *  【jdk8新增注解】可重复的注解 及 可用于类型的注解
 *   @Target
 *       ElementType.TYPE_USE,  表示该注解能写在使用类型的任何语句中。
 *       ElementType.TYPE_PARAMETER,  表示该注解能写在类型变量的声明语句中（如：泛型声明）。
 *   @Repeatable(当前注解类.class)  表示该注解能被重复使用
 *
 * @author Alex
 * @create 2022-12-03-11:01
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_PARAMETER,ElementType.TYPE_USE,ElementType.TYPE,ElementType.FIELD,ElementType.PARAMETER,ElementType.CONSTRUCTOR,ElementType.LOCAL_VARIABLE})
@Repeatable(UseAnnotation03.class)
public @interface UseAnnotation02 {
    String VV() default "hi";  // 指定一个默认值。这是用方法体的一个属性
}
