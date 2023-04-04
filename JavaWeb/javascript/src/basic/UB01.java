package basic;

/**
 * 【javascript概述】基础部分 即 ECMAScript核心语法
 *  javascript是一种运行在客户端（自己的电脑）的脚本（script）语言。JS也属于高级编程语言，编写的程序必须通过翻译器（二进制化）转换为机器语言。但脚本语言不需要编译，由JS解释器逐行进行解释执行
 *  JS是弱类型，Java是强类型。弱类型就是类型可变，强类型是当定义变量时，类型确定，且不可变
 *  浏览器有两个引擎 (1)渲染引擎，用来解析CSS和HTML也就是内核 (2)JS引擎，也就是JS解释器
 *  JS由 ① ECMAScript（javascript语法） ② DOM（页面文档对象模型） ③BOM组成（浏览器对象模型）✔
 *  css中用crtl+/，表示单行注释以及多行注释
 *    js中使用(1)ctrl+/ 表示单行注释 (2)ctrl+shift+/表示多行注释
 *
 * 【JS与HTML的结合方式】在css中推荐使用双引号，js中推荐使用单引号
 * （1）head标签末,如下
 *      使用<script type="text/javascript">。当页面打开后自动执行其中的代码
 * （2）body标签中
 *      通常在写在</body>标签前，使用一对script标签来书写
 * （3）head标签末，在（1）之前
 *      适合于样式比较多的情况，将样式单独写到.js文件中，再导入html页面中使用
 *        适合于引入Jquery文件
 *      注意，在引入的时候在两个script标签中，不允许写代码
 *      <script src="文件名.js"></script>
 *
 @author Alex
 @create 2023-01-27-10:19
 */
public class UB01 {
}
