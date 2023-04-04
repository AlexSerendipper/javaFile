package DOM;

/**
 * 【jquery概述】
 *  jQuery，是 JavaScript 和查询（Query），它就是辅助 JavaScript 开发的 js 类库。
 *  jQuery的核心思想是 write less,do more(写得更少,做得更多)，所以它实现了很多浏览器的兼容问题
 *
 * 【】感觉这东西要被淘汰了，不想学啦！！
 *
 *
 * 【jquery的常用方法DOM】见UD05.html
 * (1) 获取元素
 *  $(function (){})                                             # 页面加载完成后触发
 *  $("button#button1")                                          # 快速获取id值为button1的button元素
 *  $("button#button1").click(function (){alert("按钮1")})       # 普通DOM操作
 *  $("ul#uu").on('click','button',function (){})                # 为动态添加的元素统一绑定事件
 *                                                                   ul为冒泡的父元素，button为动态添加的子元素，统一绑定click事件
 *                                                                   （用原生的dom操作，只能动态生成元素时就直接绑定事件）
 * (2) 操作元素
 *  confirm()                                                    # 确认选项，输入提示的信息
 *  var a = $('<a index=""> x </a>');                            # 创建元素，可以在其中直接设置自定义属性，非常方便
 *  $("").append("<li></li>")                                    # 方法在被选元素的结尾插入内容。可以直接插入元素，不用创建，非常方便
 *  $("").prepend()                                              # 方法在被选元素的开头插入内容。
 *  $("").after()                                                # 方法在被选元素之后插入内容。
 *  $("").before()                                               # 方法在被选元素之前插入内容
 *  $("").attr("")                                               # 获取元素属性
 *
 *（3）特殊事件
 *  onkeyup=function (e){ }                                      # 没有对应的keyup方法，用该方式操作
 *
 * 【jquery的常用方法BOM】见UD05.html
 *  location.reload()                                            # 重载当前页面
 *  location.href                                                # 获取当前页面的url地址，可读可写
 *
 @author Alex
 @create 2023-01-27-16:02
 */
public class UD05 {
}
