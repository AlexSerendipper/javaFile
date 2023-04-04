package datafunction;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.time.Duration;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 【springboot整合junit】
 *  Spring Boot2 引入 JUnit5 作为单元测试默认库
 *  JUnit5作为最新版本的JUnit框架 与 之前版本的Junit框架有很大的不同。
 *   JUnit5由三个不同子项目的几个不同模块组成。
 *   JUnit Platform: Junit Platform是在JVM上启动测试框架的基础，不仅支持Junit自制的测试引擎，其他测试引擎也都可以接入。
 *   JUnit Jupiter: JUnit Jupiter提供了JUnit5的新的编程模型，是JUnit5新特性的核心。内部 包含了一个测试引擎，用于在Junit Platform上运行。
 *   JUnit Vintage: 由于JUint已经发展多年，为了照顾老的项目，JUnit Vintage提供了兼容JUnit4.x,Junit3.x的测试引擎。
 *
 * 【单元测试的使用】
 * （1）引入依赖(推荐自动版本依赖)
---------------
<!--以springboot启动集成junit5测试类-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<!--普通类中要用junit5的测试功能及其注解，就要引入如下三个依赖，建议使用自动版本仲裁-->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
</dependency>
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>RELEASE</version>
    <scope>compile</scope>
</dependency>
<!--普通类要使用参数化测试需要导入此包-->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
</dependency>
---------------
 * （2）只需要在test目录下，创建与主启动类相同路径下的 测试类。如 /src/test/java/datafunction/boot/WebFunctionApplicationTests
 *      然后在测试类上标注@SpringBootTest注解，
 *      在测试方法上标注@Test注解（注意：import org.junit.jupiter.api.Test）。即可实现单元测试功能
 *
 * 【Junit5常用注解】了解
 *  @ParameterizedTest :表示方法是参数化测试，下方会有详细介绍
 *  @RepeatedTest() :表示方法可重复执行，可以输入重复的次数，执行时会被重复执行
 *  @DisplayName :为测试类或者测试方法设置 名称
 *  @BeforeEach :表示该测试方法 在 每一个单元测试方法前 都会被执行
 *  @AfterEach :表示该测试方法 在 每一个单元测试方法后 都会被执行
 *  @BeforeAll :表示该测试方法 在 所有单元测试方法执行前被执行（运行测试类会运行该测试类内的所有测试方法）（通过将该注解标记的测试方法设置为静态）
 *  @AfterAll :表示该测试方法 在 所有单元测试方法执行后被执行（运行测试类会运行该测试类内的所有测试方法）（通过将该注解标记的测试方法设置为静态）
 *  @Tag :表示单元测试类别，类似于JUnit4中的@Categories
 *  @Disabled :表示测试类或测试方法不执行，类似于JUnit4中的@Ignore
 *  @Timeout :表示测试方法运行如果超过了指定时间将会返回错误
 *  @ExtendWith :为测试类或测试方法提供扩展类引用（如@SpringBootTest注解中，就是包含了@ExtendWith(SpringExtension.class),表示使用springboot测试引擎，能够拿到容器中的组件）
 *
 * 【断言机制】
 *  断言（assertions）用于检查业务逻辑返回的数据是否合理。通常使用方式是运行整个测试类，当所有的测试运行结束以后，会有一个详细的测试报告；
 *  当一个测试方法中有多个断言，一个断言失败后，后面的代码都不会执行
 *  断言静态方法都在断言类中，需要导入其所有静态方法import static org.junit.jupiter.api.Assertions.*;
 *   assertEquals(expected,actual,message)               # 判断两个对象或两个原始类型是否相等（输入预想值、实际值、断言失败的错误信息，断言失败会有详细的测试报告）
 *   assertNotEquals(expected,actual)                    # 判断两个对象或两个原始类型是否不相等
 *   assertSame                                          # 判断两个对象引用是否指向同一个对象
 *   assertNotSame                                       # 判断两个对象引用是否指向不同的对象
 *   assertTrue                                          # 判断给定的布尔值是否为 true
 *   assertFalse                                         # 判断给定的布尔值是否为 false
 *   assertNull                                          # 判断给定的对象引用是否为 null
 *   assertNotNull                                       # 判断给定的对象引用是否不为 null
 *   assertArrayEquals(arrays1,arrays2)                  # 数组断言：判断两个数组是否相等
 *   assertAll(heading,lambda1,lambda2)                  # 组合断言：assertAll方法接受多个函数式接口的实例作为要验证的断言，所有断言都会被判断！！
 *   assertThrows(Error.class,lambda,message);           # 异常断言：断言业务（lambda中）会出现异常，如果没有出现异常则抛出message
 *   assertTimeout(Duration.ofMillis(1000), lambda);     # 超时异常：断言业务（lambda中）一定在设置时间内，如果超出设置的时间则抛出message
 *   fail(message)                                       # 相当于抛出异常
 *
 * 【前置条件】
 *  前置条件（assumptions）类似于断言，不同之处在于 不满足的断言会终止测试方法执行并抛出错误提示（红）！
 *   而不满足的前置条件只会使测试方法执行终止，然后给出提示信息（白），不会抛出异常
 *  前置条件静态方法都在前置条件类中，需要导入其所有静态方法，我这里就不导入了
 *  Assumptions.assumeTrue(assumption,message)           # 判断业务是否返回true，如果返回false则抛出message
 *
 *【嵌套测试】
 *  使用@Nested标记一个内部类为内部测试类，外部类不能触发内部测试类的@BeforeEach等测试方法
 *   但是若内部测试类中还有内部测试类，即内部测试类是可以触发外部测试类的@BeforeEach等测试方法的
 *
 *【参数化测试】这个牛逼
 *  能够实现 使用不同的参数多次运行测试。而不需要每新增一个参数就新增一个单元测试，省去了很多冗余代码。
 *  @ValueSource: 为参数化测试指定入参来源，支持八大基础类以及String类型,Class类型
 *  @NullSource: 表示为参数化测试提供一个null的入参
 *  @EnumSource: 表示为参数化测试提供一个枚举入参
 *  @CsvFileSource：表示读取指定CSV文件内容作为参数化测试入参
 *  @MethodSource：表示读取指定方法的返回值作为参数化测试入参(注意方法返回需要是一个流)
 *
 @author Alex
 @create 2023-04-02-15:58
 */


// 普通测试
public class UD04 {
    public static Integer add(Integer i,Integer j){
        return i+j;
    }

    @Test
    public void test(){
        assertEquals(4,add(1,1));
    }


    @Test
    @DisplayName("array assertion")
    public void array() {
        assertArrayEquals(new int[]{1, 2}, new int[] {1, 2});
    }

    @Test
    @DisplayName("assertAll")
    void groupedAssertions() {
        assertAll("test",
                () -> assertEquals(3, 1 + 1,"?"),
                () -> assertTrue(1 > 0)
        );
    }


    @Test
    @DisplayName("异常测试")
    public void exceptionTest() {
        ArithmeticException exception = assertThrows(
                //扔出断言异常
                ArithmeticException.class, () -> System.out.println(1 % 2),"业务居然正常运行？");

    }

    @Test
    @DisplayName("超时测试")
    public void timeoutTest() {
        //如果测试方法时间超过1s将会异常
        assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(1500),"超时了");
    }


    @Test
    @DisplayName("fail")
    public void shouldFail() {
        if(1 == 1){
            fail("This should fail");
        }
    }

    @Test
    public void AssumptionTest(){
            Assumptions.assumeTrue(1==2,"前置条件不满足，跳过");
        System.out.println("666666666666666666");
    }
}

// 嵌套测试举例
class NestedTest{
    // 创建一个栈
    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        // 外层的测试不会触发内层 @BeforeEach 等
        new Stack<>();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }


        @Test
        @DisplayName("is empty")
        void isEmpty() {
            // 这里是判断栈中是否有元素，所以返回确实是true
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            // 弹出栈中的一个元素，由于栈中没有元素，异常断言成功
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            // 查看栈中的第一个元素，由于栈中没有元素，异常断言成功
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {
            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                // 内部测试类是可以触发外部测试类的@BeforeEach等测试方法，所有这个栈是存在的，测试通过
                stack.push(anElement);
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                // 元素弹出后栈为空，正确
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                // 注意每次执行单元测试，都是重新触发内部测试类和外部测试类的@BeforeEach方法哦
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }
}

// 参数化测试
class ParameterTese{
    @ParameterizedTest
    @ValueSource(strings = {"one", "two", "three"})
    @DisplayName("参数化测试1")
    public void parameterizedTest1(String string) {
        System.out.println(string);
        Assertions.assertTrue(StringUtils.isNotBlank(string));
    }


    @ParameterizedTest
    @MethodSource("method")    //指定方法名
    @DisplayName("方法来源参数")
    public void testWithExplicitLocalMethodSource(String name) {
        System.out.println(name);
        Assertions.assertNotNull(name);
    }

    static Stream<String> method() {
        return Stream.of("apple", "banana");
    }
}