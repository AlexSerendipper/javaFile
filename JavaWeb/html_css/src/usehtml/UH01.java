package usehtml;

/** HTML中，使用ctrl + / 快速注释
 *
 * 【基于BS编程】B:browser S:server
 *  网页：网页通常是HTML格式的文件，通过浏览器来阅读，网页的集合构成网站
 *  浏览器：显示html文件的工具
 *  HTML（Hyper Text Markup Language）：超文本标记语言，是用来描述网页的一种语言（非编程语言），标记语言由各种标签组成（图片标签、链接标签...）
 *  web标准：让不同浏览器解析同一个html文件时显示同一内容，标准包括结构(HTML)、表现(CSS)、行为(Javascript)，标准建议三者分离
 *  CSS3（cascading style sheets）：层叠样式表，用于美化网页、布局页面——css3就是CSS的第三次重大更新
 *  H5：广义的HTML5包括 html5本身+css3+javascript——html5就是html的第五次重大更新
 *
 *
 * 【网页的SEO优化】
 *  SEO优化：search engine optimization，搜索引擎优化，提高在搜索引擎上的排名（注册页面不需要SEO优化）
 *  TDK：页面必须有三个标签来进行SEO优化，ttile（建议网站名-网站介绍）
 *                                      description  如，<meta name="description" content="..." />
 *                                      keyword， 如，<meta name="keywords" content="网上购物,网上商城,手机.." />，建议6~8个关键词，之间用英文逗号隔开
 *  具体SEO优化时对logo的操作：（1）会在放logo的盒子中放一个h1标签（提权，告诉搜索引擎这个很重要），再在h1中放一个链接便于返回首页，再把logo设置为链接的背景图片
 *                             （2）为满足SEO优化，我们在链接中放文字（网站名称），但文字不要显示出来 ①text-indent: 99999px; overflow:hidden ②font-size: 0
 *                             （3）给链接一个title属性，这样鼠标放到logo上可以看到提示文字
 *
 * 【html基本骨架】
 *     <!DOCTYPE html>                          # 文档类型声明标签，告诉浏览器采用html5解码
 *     <html lang="zh-CN">                      # 当前文档的显示语言(填写en显示英语)，一般都直接显示，某些浏览器自动翻译
 *         <head>                               # 网页头部，一般有三部分：标题、css、js
 *             meta charset="UTF-8"             # 定义使用UTF-8字符集保存文字，否则可能出现乱码！
 *             <title></title>                  # 显示网页名
 *         </head>
 *         <body>                               # 网页主体部分
 *         </body>
 *     </html>
 *
 * 【HTML标签简述】属性
 *  标签的格式：<标签名>数据</标签名>
 *  标签拥有自己的属性：（1）如基本属性：<a herf="">。
 *                            样式属性：<body bgcolor ="green">。
 *                            事件属性：<body onclick="alert('你好！')">
 *                            用户自定义属性（可以直接定义，但通常通过JS定义）
 *                      （2）属性必须有值，属性值必须加引号
 *                      （3）✔✔✔通常基本属性需要自己填写，样式属性和事件属性基本上都用css和js作统一修改了，一般不会在标签内修改
 *                                                         因为css一定包含标签的样式属性，并且标签还会有自己css属性✔
 *  标签分为单标签和双标签，基本上标签都是成对出现，单标签很少
 *
 * 【HTML常用标签】不常用标签请查阅W3Cschool
 *  <h1><h2> ... <h6>           # 标题标签，六个等级的网页标题，标题文字变大变粗且独占一行
 *  <p>                         # 段落标签，段落根据浏览器大小自动换行，自带内外边距（建议去除）
 *  <br />，                    # 换行标签，单标签~~。遇到该标签强制换行
 *  <hr/>                       # 水平线标签，单标签~~
 *  <strong>                    # 加粗
 *  <em>                        # 倾斜
 *  <del>                       # 删除线
 *  <ins>                       # 下划线
 *  <div>                       # 对话框，独占一行
 *  <span>                      # 小对话框，多个小对话框放在一行显示，其长度为封装数据的长度
 *  <img src="./文件路径/文件名", alt="图像显示不出时用文字替换", title="鼠标放图像上显示的文字"/>
 *     （1）图像标签：单标签~~。
 *     （2）图像建议与html放置于同一文件夹下，通常仅修改宽高中的一个，另一个等比例缩放
 *     （3）也可以使用网络绝对路径，src=" https://www.itcast.cn/2018czgw/images/logo2.png "
 *     （4）通常会用盒子装着图片，通过控制盒子来控制图片
 *  <a href="url地址", target="弹出方式">  [文本或图像]  </a>
 *     （1）超链接标签：外部链接("url地址")，其中弹出方式默认_self, 其中_blank表示用新窗口弹出
 *     （2）href="./文件路径/xxx.html"          内部链接：跳转到另一个页面
 *         href="./文件路径/xxx.exe/.tar"       下载链接
 *         href="./文件路径/xxx.exe/#"          空链接1
 *         href="javescript:;"                 空链接2：推荐使用这种方式
 *         href="#标记1"                       标记链接，跳转到标记处的标签。如需要跳转至h3标签<h3 id="标记1">
 *  <iframe src="" name="">
 *      (1)iframe标签，可以在一个 html页面上,打开一个小窗口, 去加载一个单独的页面
 *      (2)可以设置src加载指定的页面
 *      (3)通常我们会不设置src的值,而设置其name属性名。然后设置a标签的弹出方式为name。实现点击标签后在iframe区域显示内容。见下方示例✔
 *
 * 【html特殊字符】
 *             特殊字符                    描述                       字符代码
 *                                        空格符                      &nbsp
 *             >                          小于号                      &lt
 *             <                          大于号                      &gt
 *             &                          与                         &amp
 *             ￥                          人名币                     &yen
 *             °                          摄氏度                      &deg
 *             ±                          正负号                      &plusmn
 *             ×                          乘号                        &times
 *             ÷                          除号                        &divide
 *             ²                          平方2（上标2）               &sup2
 *             ³                          立方3（上标3）               &sup3
 @author Alex
 @create 2023-01-27-10:14
 */
public class UH01 {
}
