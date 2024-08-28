package basic;

/**
 *
 * 【引用数据类型】
 *    引用数据类型，变量中存储的仅仅是在数据在 堆空间 中的地址，引用数据类型包括所有 对象（系统对象、自定义对象），如 Object、Array、Date
 *
 * 【数组】一组数据的集合，存储在单个变量中（创建数组仅有两个方式！New关键字和字面量✔）
 * var 数组名 = [ ]                    # 使用数组字面量创建数组，可以存放任意数据，用逗号隔开
 * var 变量名 = new Array( );          # 使用new关键字创建数组，注意new Array(2)是创建一个长度为2的空数组，new Array(2, 3)是创建一个内容为2，3的数组，
 * 数组名[索引值]                      # 访问数组中的某个元素用索引值，索引值从0开始
 * 数组名[索引值] = ''                 # 修改索引下的值，可以实现增改数组元素
 *
 * 【对象】创建对象有三种方式（1）字面量{} （2）new关键字 （3）利用构造函数创建对象
 * （1）× 字面量创建，不推荐使用
 *       var object1 = {
 *           name: '', age='',                                   # 对象的属性采用键值对的形式
 *           method1: function() { }                             # 对象的方法采用类似匿名函数的形式
 *       }
 *       object1.name                                            # 调用对象的属性1
 *       object1['name']                                         # 调用对象的属性2，两种方法效果相同，但如果name是字符串型的（带有单引号），只能用第二种方法
 *       object1.method1( )                                      # 记得添加小括号
 * (2) × new关键字创建对象，别用
 *       var object1 = new Object( );                            # 用new关键字创建一个空对象
 *       object1.name = ''；                                     # 添加属性
 *       object1.method1 = function() { } ；                     # 添加方法
 * (3) ✔✔✔构造函数创建对象，都给我用这个，和JAVA一样
 *  构造函数约定首字母大写。
 *      function Person(name, age, sex) {                         # 创建类
 *           this.name = name;
 *           this.age = age;
 *           this.sex = sex;
 *           this.sayHi = function() { }
 *      }
 *      var bigbai = new Person('大白', 100, '男');                # 使用new调用构造函数（实例化）
 *
 * 【Object.defineProperty】
 * Object.defineProperty(obj, prop)                               # 用于在对象obj上定义新的属性或修改现有属性prop 
 @author Alex
 @create 2023-01-27-10:44
 */
public class UB03 {
}
