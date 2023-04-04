package usecss;

/**
 * 【css概述】
 *  CSS由选择器（给谁修改）及其 样式（修改什么）组成，声明中属性以键值对的形式出现(：)
 *   若要定义多个样式声明，需要用分号将每个声明分开。
 *  模块化开发：网页开发时，将重复的样式写在同一个common.css中，实现模块化开发（重复使用，修改方便）
 *  基本格式如： 选择器:{样式1：值;
 *                      样式2：值}
 *
 * 【CSS 和 HTML 的结合方式】
 * （1）行内样式表
 *  在元素内部style属性中设定CSS样式，只改变当前标签的样式
 *  注意html和CSS属性格式的区别：html标签的样式属性是直接用键值对的形式表示，而css属性是写在style中
 *     <div style="color: red; font-size: 12px">  哈哈哈 </div>
 *
 * （2）内部样式表：通过写入html的<style>标签中引入CSS，控制整个当前html页面
 *     <head>
 *         <style>                                     # CSS写在<head>中的<style>标签中，实现样式结构相分离
 *             h3 {
 *                  color: pink;                       # 推荐一行一个属性, 冒号后紧跟一个空格，选择器和大括号之间保留一个空格
 *                  font-size: 20px
 *                 }
 *         </style>
 *     </head>
 *
 * （3）外部样式表：适合于样式比较多的情况，将样式单独写到CSS文件中，再导入html页面中使用
 *     <head>
 *         <link rel="stylesheet" href="./文件名.css">
 *     </head>
 @author Alex
 @create 2023-01-27-10:17
 */
public class UC01 {
}
