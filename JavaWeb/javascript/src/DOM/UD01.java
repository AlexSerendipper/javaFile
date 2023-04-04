package DOM;

/**
 * 【DOM】
 *  Web_API是一套操作浏览器功能（BOM）和页面元素（DOM）的API（例如alert等）
 *  DOM 全称是 Document Object Model 文档对象模型
 *   就是把文档中的标签，属性，文本，转换成为对象来管理（由于DOM返回的都是对象，所以叫文档对象模型）
 *   通过DOM接口可以改变网页的内容、结构和样式
 *  常见元素
 *   文档：一个页面就是一个文档，DOM 中使用 document 表示
 *   元素：页面中的所有标签都是元素，DOM 中使用 element 表示
 *   节点：网页中的 所有内容 都是节点（标签、属性、文本、注释等），DOM 中使用 node 表示，一般来说，节点至少有Nodetype(节点类型)、nodename(节点名称)、nodevalue(节点值)
 *  Document的理解
 *    Document 它管理了所有的 HTML 文档内容。
 *    document 它是一种树结构的文档。有层级关系。
 *    它让我们把所有的标签都对象化。我们可以通过 document 访问所有的标签对象。
 *
 * 【DOM获取元素】
 * （1）内置方法
 *  document.getElementById('id')                           # 根据ID属性查找
 *  document.getElementByName('xixixi')                     # 根据Name属性查找，全文档查找
 *  var element1 = document.getElementByTagName( 'ol' )     # 标签名查找，全文档查找，返回带有指定标签名的对象的集合，用伪数组的方式储存✔
 *   element1[0].getElementsByTagName('li');                   ✔常使用这种方式，先获取父标签集合，再得到子标签对象的集合。如获取ol下的li的集合
 *                                                             ✔集合中元素顺序 是他们在 html 页面中从上到下的顺序
 *  document.body                                           # 获取body标签，当然也可以用取id的形式取出
 *  document.documentElement                                # 获取html标签，当然也可以用取id的形式取出
 * （2）H5新增。非常好用
 *  document.getElementsByClassName('类名')                   # 类名查找
 *  document.querySelector('选择器');                         # ✔返回指定选择器的第一个元素对象，这个选择器（需要加符号），包括  .类名，#id名，标签名等✔
 *  document.querySelectorAll('选择器');                      # ✔返回指定选择器的所有元素对象，选择器（需要加符号）
 *  (3)根据层级关系获取元素，element1.nodeType，可返回节点的类型
 *  element1.childNodes;                                     # 获取所有的子节点，包含元素、文本节点等，所以不推荐使用
 *  element1.firstChild;                                     # 返回第一个子节点，包含元素、文本节点等，所以不推荐使用
 *  element1.nextSibling;                                    # 返回下一个兄弟节点，包含元素、文本节点等，所以不推荐使用
 *  element1.previousSibling;                                # 返回上一个兄弟节点，包含元素、文本节点等，所以不推荐使用
 *
 *  element1.parentNode;                                     # 获取离元素最近的父元素✔不包含文本，推荐使用
 *  element1.children;                                       # 获取所有子元素节点✔
 *  element1.firstElementChild;                              # 返回第一个子元素节点
 *  element1.lastElementChild;                               # 返回最后一个子元素节点
 *  element1.nextElementSibling;                             # 返回下一个兄弟元素节点
 *  element1.previousElementSibling;                         # 返回上一个兄弟元素节点
 *
 *【特殊元素】
 *  javaScript 语言中提供了一个location地址栏对象，它有一个属性叫href（可读，可写）。可以直接修改浏览器地址栏中的地址
 *  location.href = "www.baidu.com"
 *
 @author Alex
 @create 2023-01-27-12:51
 */
public class UD01 {
}
