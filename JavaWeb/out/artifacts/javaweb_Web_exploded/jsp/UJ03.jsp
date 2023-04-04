<%@ page import="jsp.Person" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/2/2
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        //往四个域中都保存了相同的 key 的数据。
        request.setAttribute("key", "request");
        session.setAttribute("key", "session");
        application.setAttribute("key", "application");
        pageContext.setAttribute("key", "pageContext");
    %>

    ${key}<br>
<%-- ---------------------EL读取对象属性-------------------------------------   --%>
    <%
        Person person = new Person();
        person.setName("国哥好帅！");
        person.setPhones(new String[]{"18610541354","18688886666","18699998888"});
        List<String> cities = new ArrayList<String>();
        cities.add("北京");
        cities.add("上海");
        cities.add("深圳");
        person.setCities(cities);
        Map<String,Object> map = new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        person.setMap(map);
        pageContext.setAttribute("p", person);
    %>
    输出 Person：${ p }<br/>
    输出 Person 的 name 属性：${p.name} <br>
    输出 Person 的 pnones 数组属性值：${p.phones[2]} <br>
    输出 Person 的 cities 集合中的元素值：${p.cities} <br>
    输出 Person 的 List 集合中个别元素值：${p.cities[2]} <br>
    输出 Person 的 Map 集合: ${p.map} <br>
    输出 Person 的 Map 集合中某个 key 的值: ${p.map.key3} <br>
<%-- ---------------------EL运算符-------------------------------------   --%>
    <%
        // 1、值为 null 值的时候，为空
        request.setAttribute("emptyNull", null);
        // 2、值为空串的时候，为空
        request.setAttribute("emptyStr", "");
        // 3、值是 Object 类型数组，长度为零的时候
        request.setAttribute("emptyArr", new Object[]{});
        // 4、list 集合，元素个数为零
        List<String> list = new ArrayList<>();
        // list.add("abc");
        request.setAttribute("emptyList", list);
        // 5、map 集合，元素个数为零
        Map<String,Object> mmap = new HashMap<String, Object>();
        // map.put("key1", "value1");
        request.setAttribute("emptyMap", mmap);
    %>
    ${ empty emptyNull } <br/>
    ${ empty emptyStr } <br/>
    ${ empty emptyArr } <br/>
    ${ empty emptyList } <br/>
    ${ empty emptyMap } <br/>
<%-- ---------------------11个对象的使用1-------------------------------------   --%>
    <%
        pageContext.setAttribute("key1", "pageContext1");
        pageContext.setAttribute("key2", "pageContext2");
        request.setAttribute("key2", "request");
        session.setAttribute("key2", "session");
        application.setAttribute("key2", "application");
    %>
    ${ applicationScope.key2 }<br>
<%-- ---------------------11个对象的使用2-------------------------------------   --%>
    输出请求参数 username 的值${ param.username } <br>
    输出请求参数 password 的值${ param.password } <br>
    输出请求参数 hobby 的值 ${ paramValues.hobby[1] } <br>
    输出请求头【User-Agent】的值 ${ header['User-Agent'] } <br>
    输出请求头【Connection】的值 ${ header.Connection } <br>
    输出请求头【User-Agent】的值 ${ headerValues['User-Agent'][0] } <br>
    输出Cookie 的名称： ${ cookie.JSESSIONID.name } <br>
    输出Cookie 的值： ${ cookie.JSESSIONID.value } <br>
    输出web.xml中配置的上下文参数的值 ${ initParam.password } <br>

</body>
</html>
