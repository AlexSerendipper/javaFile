package DOM;

/**
 * 【修改元素】选出元素后就可以修改元素
 *
 * 【修改元素内容】
 *  element1.innerText  = ''                           # 可读写✔，不识别HTML标签！并且去除空格和换行
 *  element1.innerHTML = ''                            # 可读写✔，并且识别HTML标签✔！
 *
 * 【元素基本属性与自定义属性】
 * （1）传统方式
 *  element1.属性 = ''                                 # 可以直接修改基本属性。如img标签的src等
 *  element1.setAttribute('属性名', '值')              # 设置自定义属性
 *  element1.getAttribute('属性名1')                   # 获取元素的基本属性/自定义属性
 *  element1.removeAttribute('属性名')                 # 移除自定义属性
 *  div1.className = '类名2'                           # ✔特别的，类名作为基本属性，并不是通过element1.class修改~~
 *       类名操作，相当于给元素添加类名（会覆盖原有类名）
 *        div1.className = '原类名1 类名2' 。。。 如果不想覆盖原有类名，可以利用多类名赋值✔
 *       当需要修改样式很多时，我们会将需要更改的样式直接写在css中，然后为指定标签添加类名
 * （2）H5新增修改类名方式
 *  element1.classList                          # 返回元素的类名，仅支持ie10以上
 *  element1.classList.add('')                  # 添加类名（追加类名，不会覆盖），类名无需加.
 *  element1.classList.remove('')               # 删除指定类名
 *  element1.classList.toggle('')               # 切换类，如果有该类则删除该类，如果没有该类则添加该类
 *（3）H5新增：H5中规定，所有的自定义属性都以‘data- ’开头。
 *  element1.dataset                                                  # 获得含所有以data开头的属性
 *  element1.dataset.属性名1  或  element1.dataset[ '属性名1' ]        # 获得具体自定义属性
 *
 *     若设置自定义属性名为 data-list-name，引用时应采用驼峰命名法，如element1.dataset.listName来引用
 * 【元素的样式属性】
 * （1）传统方式
 *  div1.style.backgroundColor = 'url(./文件名)'
 *    div1.style.width = '50px'
 *       对于大部分样式属性，都可以直接通过.style.属性名的方式进行修改，
 *       ✔在修改时，属性名需要变更为小驼峰命名的方式（定义时全小写）
 *       元素的样式属性修改后，会在标签内生成行内样式，权重高于内部样式
 *
 * 【克隆/创建(添加)/删除元素】
 *  element1.cloneNode(true);                              # 克隆一个元素节点，括号内为true则为深拷贝（复制里面的内容），括号内为空则为浅拷贝（不复制里面的内容）
 *  document.creatElement('li');                           # 创建元素一：（常用）
 *    element1.innerHTML += '<a href="javascript:;"></a>'     创建元素二：利用InnerHTMLf赋值的形式创建标签(效率高)
 *    document.write('li');                                   创建元素三：创建一个元素节点（不好用，页面执行完毕后调用会导致页面重绘）
 *  element2.appendChild(element1);                        # 将element1添加到element2的子元素列表末
 *  element2.insertBefore(element1, element3);             # 将element1添加到element2子元素列表中的element3前
 *  element2.removeChild('element1');                      # 将元素1从element2的子元素列表中删除
 *
 @author Alex
 @create 2023-01-27-13:33
 */
public class UD02 {
}
