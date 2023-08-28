package usehtml;

/**
 * 【表单】
 *  表单就是 html 页面中,用来收集用户信息的所有元素集合.然后把这些信息发送给服务器
 *
 * 【表单标签骨架】表单域（包含表单元素的区域），提示信息（性别），表单元素/控件（男）
 * <form action="url地址" method="GET/POST" name="表单域名字">           # action属性设置提交的服务器地址。method属性设置提交的方式GET(默认值)或POST
 *       提示信息：各种表单元素控件                                       # GET请求后浏览器地址栏中的地址是：action 属性[+?+请求参数]
 *                                                                      # POST请求后，浏览器地址栏中只有 action 属性值
 * </form>
 *
 * 【表单元素】input输入表单元素，select下拉表单元素，textarea文本域表单元素
 * <input type="text" name="" value="" maxlength="int" />    # 单行输入。value指定默认文字。maxlength指定最大输入长度（默认为20）
 * <input type="radio" name="" checked="checked" id=""/>     # ✔单选框，多个单选框必须使用相同的name才能实现单选，checked属性可以默认选中该单选框，id属性搭配label使用
 * <input type="checkbox">                                   # 复选框
 * <input type="submit" value="提交">                        # 提交按钮，提交会将表单域中的所有值提交给后台服务器
 * <input type="button" value="获取验证码">                   # 普通按钮，后期通过搭配javescript交互使用
 * <input type="file">                                       # 上传文件标签。
 * <input type="hidden">                                     # 定义隐藏的输入字段。当我们要发送某些信息，而这些信息，不需要用户参与，就可以使用隐藏域
 * <input type="image">                                      # 定义图像形式的提交按钮
 * <input type="password">                                   # 定义密码字段，该字段的字符被掩码。
 * <input type="rest">                                       # 重置按钮。重置按钮会清除表单中所有的数据
 * <input type="date">                                       # 日期按钮，date选取日、月、年、month-选取月、年
 *                                                                      week-选取周和年、time-选取时间（小时和分钟）、datetime-选取时间、日、月、年（本地时间）
 *                                                                      
 * <label for=""> </label>                                   # 绑定一个表单元素，当点击label中的文本时，浏览器自动跳转到对应的表单元素上，
 *                                                             用以增加用户体验（扩大了点击范围），for的值必须和id一样才可以✔
 * <select>                                                  # 下拉表单，"selected"可以默认选择该选项
 *         <option selected="selected">请选择国籍</option>
 *         <option>中国</option>
 *         <option>美国</option>
 *         <option>日本</option>
 * </select>
 * <textarea rows="" cols=""> 默认显示的文字 </textarea>      # 当用户需要输入内容较多，可以使用文本域表单元素，其属性通常在CSS定义
 *
 * 【表单格式化】
 *  我们通常会将表单元素放置在列表中，从而实现对齐的效果
 *
 * 【表单提交的时候，数据没有发送给服务器的三种情况】
 *  ✔表单项没有 name 属性值
 *  单选、复选（或是下拉列表中的 option 标签）都需要添加 value 属性（即有输入数据），以便发送给服务器
 *  表单项不在提交的 form 标签中
 *
 @author Alex
 @create 2023-01-27-10:14
 */
public class UH03 {
}
