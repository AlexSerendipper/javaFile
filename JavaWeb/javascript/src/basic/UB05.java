package basic;

import org.junit.Test;

import java.util.TreeSet;

/**
 * 【函数】封装了一段可以重复使用的代码块
 *  如果传入的实参个数多于形参的个数，只会取形参个数的实参。
 *   如果实参个数少于形参的个数，会把未接收到值的形参看作是undefined
 *  函数内部的变量为局部变量，仅在局部域生效，函数的形参也是局部变量
 *   在函数外部的变量为全局变量，全局生效。（注意：在for循环if分支中声明的仍是全局变量）
 *   在函数内部赋值一个没有声明的变量，也属于全局变量✔
 *  当函数嵌套时，采用链式查找。即内部函数的所需的变量先从自己内部找，再从外部函数中找，再从全局变量在中找
 *  JavaScript 解析器在运行 JavaScript 代码的时候分为预解析和代码执行两步✔
 *    （1）预解析会把所有的var放到当前域（全局域或局部域）的最前面声明，赋值放到当前域的最后面（所以无法输出未定义的变量，包括匿名函数。function也无法调用外部全局变量）
 *    （2）预解析会把所有的function放到当前域的最前面，但不会调用（所以可以先调用函数再定义）
 *    （3）代码执行就是从上到下执行JS语句
 *  在 Java 中函数允许重载。但是在 JS 中函数的重载会直接覆盖掉上一次的定义，见示例
 *
 * 【函数的声明】
 * （1）函数声明一：function
 * function 函数名(形参1，形参2) {                        # 利用函数关键字声明函数
 *     //函数体代码
 *     return 需要返回的结果                              # return后函数会立刻停止，具有终止函数的功能，return只能返回一个值（或者返回一个数组）  # 如果没有return，则返回Undefined
 * }
 *
 * 函数名(实参1，实参2);                                  # 函数调用，常用一个变量接收即可
 *
 * （2）函数声明二：匿名函数。匿名函数最大的作用还是不设置变量名
 * [var 变量名 = ]function(形参1，形参2) {//函数体代码} 	  # 匿名函数，JS中，可以把匿名函数赋值给一个变量，通过变量名调用
 * 变量名(实参1，实参2) 	                                  # 匿名函数调用
 *
 * （3）函数声明三：ES6中新增箭头函数，相当于匿名函数，和JAVA8的新特性有点点像
 * [变量名] => (形参1，形参2){ //函数体代码}                 # 与上匿名函数完全相同

 *（4）函数声明五：立即执行函数。无需调用，立刻执行。最大的作用就是创建了一个独立作用域，其中的变量都为局部变量。。。。这个我是没用过，要用的时候再说把
 * (function(形参1，形参2){}) (实参1，实参2)  	                        # 第一种写法（匿名函数），第二个小括号相当于是调用函数，可以传递实参
 * (function 函数名(形参1，形参2){}) (实参1，实参2)  	                    # 第一种写法（函数），也可以给函数命名
 * (function(形参1，形参2){}(实参1，实参2))  	                            # 第二种写法
 *
 *
 * 【可变形参函数】✔利用该属性也实现了JAVA中类似重载的功能
 *  每个function函数中，都有一个隐形的arguments参数。不需要定义
 *  不论声明的形参是什么，所有传入的实参都会被储存在arguments数组中，都可以通过arguments被操作
 *  ✔arguments展示形式是一个伪数组，具有以下特点：（1）具有 length 属性
 *                                                （2）按索引方式储存数据
 *                                                （3）不具有数组的 push , pop 等方法
 @author Alex
 @create 2023-01-27-11:04
 */
public class UB05 {
    // 可变形参函数：要求 编写 一个函数。用于计算所有参数相加的和并返回
    @Test
    public void test1(){
        /*
        function sum(num1,num2) {
        var result = 0;
        for (var i = 0; i < arguments.length; i++) {
                if (typeof(arguments[i]) == "number") {
                    result += arguments[i];
                }
            }
        return result;
        }
        alert( sum(1,2,3,4,"abc",5,6,7,8,9) );
         */
    }

    // 在 Java 中函数允许重载。但是在 JS 中函数的重载会直接覆盖掉上一次的定义
    @Test
    public void test2(){
    /*
        function fun() {
            alert("无参函数 fun()");
        }

        function fun(a, b) {
            alert("有参函数 fun(a,b)");
        }
        fun();
     */
    }

    // JavaScript 解析器在运行 JavaScript 代码的时候分为预解析和代码执行两步
    @Test
    public void test3(){
        /*
        定义为：
        var a = 18;
        f1();
        function  f1(){
            var b = 9;
            console.log(a);
            console.log(b);
            var a = '123';
        }
        // 实际执行为：
        var a;
        function f1(){
            var b;
            var a;
            console.log(a);
            console.log(b);
            a = '123'
        }
        a = 18
         */
    }
}
