package DOM;

/**
 * 【静态事件】
 *  通过直接修改标签的事件属性，接赋于事件响应后的代码，这种方式我们叫静态注册。
 *
 * 【动态事件】由三要素组成，事件源、事件类型、事件处理程序，网页中的每个元素都可以添加事件，即触发JS的机制
 * 【一、事件源】 即事件对象，如 var btn = document.getElementById('btn')
 * 【二、事件类型】 即注册事件（点击/鼠标经过...）
*   （1）传统的注册事件： 一个元素只能设置一个处理函数
*   .onclick                  # 点击事件
*   .onmouseover              # 鼠标经过，冒泡
*   .onmouseenter             # 鼠标经过触发，不冒泡
*   .onmouseout               # 鼠标离开，冒泡
*   .onmouseleave             # 鼠标离开，不冒泡
*   .onmousemove              # 鼠标移动
*   .onmouseup                # 鼠标谈起
*   .onmousedown              # 鼠标按下
 *  ---------------------------------------
*   .onfocus                  # 表单事件，获得焦点
*   .onblur                   # 表单事件，失去焦点
*   .onchange                 # 表单事件，内容发生改变事件，常用于下拉列表和输入框内容发生改变后操作
*   .onsubmit                 # 表单事件，表单提交事件： 常用于表单提交前，验证所有表单项是否合法。
 *   ---------------------------------------
*   .onkeyup                  # 按键松开时触发
*   .onkeydown                # 按键按下时触发
*   .onkeypress               # 按键按下时触发（不识别功能键吗，如ctrl/shift/箭头等。区分大小写✔）
*   .onscroll                 # 拖动滚动条触发事件
*   .onclick = null           # 传统解绑事件（删除事件）

*   （2）方法监听注册事件：同一个元素可以注册多个监听器，这个方法更加常用✔
*    element1.addEventListener('click', function() {} , true)         # 不带on的字符串形式，第三个参数设置为true为事件流捕获阶段，设置为false或不写为事件冒泡阶段✔
*    element1.addEventListener('click', function 变量名1() {} )        # 如果涉及到移除监听器，需要给函数命名，建议在外部命名函数（如果命名在.addEventListener内部，就只能在该函数内部移除函数）✔
*    element1.removeEventListener('click',  变量名1)                   # 移除监听器
 * 【三、事件处理程序】
 *  btn.onclick = function(){ this.disabled = 'true' }                 # this指向事件函数的调用者
 *  btn.click()                                                        # 事件处理程序调用
 *
 * 【事件流】即多个事件的触发顺序
 *  当div1中包含div2，如果点击了div2
 *   如果div1设置了事件而div2未设置事件，则会触发div1事件，这个过程称为冒泡
 *   某些事件如onblur/onfocus/onmouseenter等是没有冒泡的
 *  事件冒泡，即为从div ==> body ==> html ==> document的顺序触发事件
 *  事件委托/代理：核心原理即为：为父节点设置事件监听器，利用冒泡原理影响每个子节点。这样就无需为每个子节点添加监听器
 *
 * 【事件对象】
 *  event就是一个事件对象，在注册事件触发后生成，是我们事件的一系列相关数据的集合（例如鼠标触发事件的话，会得到鼠标的相关信息，如鼠标位置）
 *  div1.onclick = function(event) {}                    # 事件对象可以自己命名，常用event, e, evt等
 *  e.target                                             # 返回触发事件的元素（区别于this，this返回注册事件的对象）
 *  e.type                                               # 返回触发事件的类型
 *  e.preventDefault()                                   # 阻止默认行为，如不让链接跳转，亦或阻止表单的默认提交行为✔
 *  e.stopPropagation()                                  # 阻止冒泡✔
 *  e.clientX                                            # 返回鼠标相对于浏览器可视区的X坐标
 *  e.pageY                                              # 返回鼠标相对于浏览器整个文档页面的X坐标，不带单位✔✔✔
 *  e.screenX                                            # 返回鼠标相对于电脑屏幕的X坐标（不常用）
 *  e.keyCode                                            # 返回按键相应的ASCII码值
 *
 @author Alex
 @create 2023-01-27-14:10
 */
public class UD03 {
}
