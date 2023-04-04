package usehtml;

/**
 * 【列表骨架】无序列表域<ul>, 有序列表域<ol>，有序列表就是前面多了序号而已
 *                                                        # ul的type属性能够关闭li前小圆点样式
 *     <ul type="none">                                   # <ul>标签内只能放<li>标签，<li>标签内能放任何标签，ul中的type属性，可以修改列表项前的符号
 *         <li>文字、图片</li>                             # li是块元素，默认宽度与ul相同✔ul是块元素，默认宽度于父元素相同✔
 *         <li>文字、图片</li>
 *     </ul>
 *
 *     <dl>                                                # 自定义列表域，少见，用于对术语或名词进行解释或描述<dl>内只能放<dt>和<dd>
 *         <dt>名词</dt>
 *         <dd>解释</dd>
 *         <dd>解释</dd>
 *     </dl>
 *
 *
 * 【表格骨架】
 *     <table cellspacing="">                           # cellspacing属性可以设置单元格间距
 *         <thead>                                      # thead和tbody将表格分为表头区域和主体区域，让表格结构更加清晰（可省略）
 *             <tr>                                     # table row，定义表格中的行
 *                 <th> 表头中的文字</th>                # table head，定义表头中的单元格，加粗且居中显示
 *             </tr>
 *         </thead>
 *         <tbody>
 *             <tr>                                      # table row，定义表格中的行
 *                 <td> 单元格中的文字</td>               # table data定义表格中的单元格
 *             </tr>
 *         </tbody>
 *     </table>
 *
 * 【✔✔合并单元格】见下方示例
 * (1) 跨行，以最上侧单元格为目标单元格。跨列，以最左侧单元格为目标单元格
 * (2) 找到目标单元格后写合并方式<td colspan="2"></td>
 *                             <td rowspan="2"></td>
 * (3) 删除多余的单元格！
 @author Alex
 @create 2023-01-27-10:14
 */
public class UH02 {
}
