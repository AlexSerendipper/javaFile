package jsp;

/**
 * 【JSTL标签库】JSP Standard Tag Library
 *  JSTL是指JSP标准标签库。是一个不断完善的开放源代码的JSP标签库。
 *  ✔✔✔EL表达式主要是为了替换jsp中的表达式脚本，而标签库则是为了替换代码脚本。这样使得整个jsp页面变得更佳简洁。
 *  JSTL 由五个不同功能的标签库组成。
 *      功能范围                          URI                                 前缀
 *      核心标签库（重点）         http://java.sun.com/jsp/jstl/core            c
 *      格式化                    http://java.sun.com/jsp/jstl/fmt             fmt
 *      函数                      http://java.sun.com/jsp/jstl/functions       fn
 *      数据库(不使用)            http://java.sun.com/jsp/jstl/sql             sql
 *      XML(不使用)               http://java.sun.com/jsp/jstl/xml             x
 *  其中数据库标签库如果在web层引入，就破坏了javaweb的三层设计格式，已经被淘汰了
 *    并且现在都用JSON格式传输数据，所以XML标签库也被淘汰了
 *
 * 【JSTL标签库的使用步骤】
 * （1）先导入 jstl 标签库的两个 jar 包。
 *      taglibs-standard-impl-1.2.1.jar
 *      taglibs-standard-spec-1.2.1.jar
 * （2）第二步，使用 taglib 指令引入标签库。
 *      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>             ✔CORE标签库
 *      <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>            ✔FMT标签库
 *      <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>       ✔FUNCTIONS 标签库
 *      <%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>            SQL 标签库
 *      <%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>              XML标签库
 *
 * 【一些常用标签介绍】
 * （1）set标签（使用很少）
 *     set标签用于往域中保存数据
 *     scope属性设置保存到哪个域
 *       <c:set scope="" var="" value=""/>
 *     page表示 PageContext域（默认值）。request表示Request域。session表示Session域。application表示ServletContext域
 *     var属性设置key是多少。value属性设置值
 * （2）if标签: test 属性表示判断的条件（使用EL表达式）
 *        <c:if test="${ 12 == 12 }">
 *            <h1>12 等于 12</h1>
 *         </c:if>
 * (3)choose标签: 多路判断。跟switch...case....default功能一摸一样
 *  <c:choose>
 *      <c:when test="${ requestScope.height > 190 }">
 *          <h2>小巨人</h2>
 *      </c:when>
 *      <c:when test="${ requestScope.height > 180 }">
 *          <h2>很高</h2>
 *      </c:when>
 *     <c:otherwise>
 *         <h2>残疾人</h2>
 *     </c:otherwise>
 *  </c:choose>
 * (4)foreach标签
 *     <c:forEach begin="1" end="10" step="2" varStatus="status" var="i"></c:forEach>    # 用于循环数字，相当于for(int i=1, i<=10, i=i+2)
 *        varStatus属性表示当前遍历到的数据的状态，非常少用，可以输出 status.begin/end/step等
 *     <c:forEach items="" var="">                                    # 用于遍历对象，相当于for(Object var:items)
 *
 @author Alex
 @create 2023-02-02-15:28
 */
public class UJ04 {
}
