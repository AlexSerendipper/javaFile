package ajax;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 【AJAX】Asynchronous Javascript And XML
 *   AJAX，即，异步的JavaScript和XML，指一种创建交互式网页应用的网页开发技术。（因为在以前，数据的传输格式的xml，所以..这里有xml）
 *   ajax是一种浏览器通过 js 异步发起请求，局部更新页面的技术。
 *     ✔Ajax请求的局部更新，浏览器地址栏不会发生变化
 *     ✔✔局部更新不会舍弃原来页面的内容
 *     可以体会一下，原先的servlet程序，即非ajax请求，是通过 a标签 或者 表单域 来发情get和post请求，然后进行一系列操作。
 *      ✔虽然地址栏可能不会发生变化，但是原先页面的内容都会被抛弃！ 而ajax实现了为页面的任何元素都绑定事件，发生事件时可以使页面局部发生变化
 *      ✔即ajax是一种发起请求的方式，作为一种让页面局部更新的技术
 *
 *  【ajax客户端的使用】原生做法，见ajax.html
 * （1）我们首先要创建 XMLHttpRequest
 *       new XMLHttpRequest();
 * （2）调用open方法设置请求参数。第三个参数设置是否为异步，默认都是true
 *       xmlhttprequest.open("GET","url地址",true)
 * （3）在send方法前绑定onreadystatechange事件，处理请求完成后的操作
 *    其中readystate有5个参数，0为请求未初始化、1未服务器连接已建立、2请求已接收、3请求处理中、4请求已完成，且相应已就绪
 *    status有两个参数，200为请求成功，404为未找到页面
 * 		 xmlhttprequest.onreadystatechange = function(){}                                 # 当readystate发生改变时触发事件
 * 		 if (xmlhttprequest.readyState == 4 && xmlhttprequest.status == 200) {}           # 请求成功时判断
 * 		 xmlhttprequest.responseText);                        # 获取服务器端相应的参数
 *
 * 【ajax服务器端的使用】
 *   把数据转换为json字符串格式后发送回客户端
 *
 * 【异步与同步的理解】见ajax.html
 *   同步：在执行某个请求的时候，若该请求需要等待一段时间才能返回信息，那么这个进程将会一直等待下去(死心眼)，
 *          直到收到返回信息才继续执行下去。当响应时间过长时，就会十分影响用户体验。
 *    异步：先响应用户请求，将用户请求放入消息队列排队执行，用户就可以进行其他操作啦，无需等待。
 *
---------------------------------------------------------------------------------------
 var xhr = new XMLHttpRequest()                                      # 原生get请求                                                                                            1.  创建xhr对象
 xhr.open('GET', 'url地址？id=1&name=红楼梦')
 xhr.send()                                                                                                                                                         3. 调用send函数
 xhr.onreadystatechange = function () {                                                                                                           4. 监听onreadystatechange事件
     if (xhr.readyState === 4 && xhr.status === 200) {               # 判断是否成功获取数据
        var jsonObj = JSON.parse(xmlhttprequest.responseText);       # 把回传的数据(JSON字符串）转换为js对象，
        }
 }
 --------------------------------------------------------------------------------------
 var fd = new FormData(form)                                                     # 原生post请求
 var xhr = new XMLHttpRequest()
 xhr.open('POST', 'url地址')
 xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')       # 固定步骤
 xhr.send(data)                                                                                                                                        # 区别是在此处传入输入的数据
 xhr.onreadystatechange = function () {
     if (xhr.readyState === 4 && xhr.status === 200) {
        var jsonObj = JSON.parse(xmlhttprequest.responseText);                   # 把回传的数据(JSON字符串）转换为js对象，
        }
 }
 --------------------------------------------------------------------------------------
 *
 *
 @author Alex
 @create 2023-02-18-16:16
 */

// /ajaxServlet
public class UA02 extends BaseServlet{
    protected void javaScriptAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InterruptedException {
        resp.setContentType("text/html; Charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        System.out.println("ajax请求过来了，睡眠一会，看看异步效果");
        Thread.sleep(3000);
        // 把student对象转换为json字符串
        Student student = new Student(1, "zzh", 18, "13155340920");
        Gson gson = new Gson();
        String stuStr = gson.toJson(student);
        // 把student对象发送回客户端
        resp.getWriter().write(stuStr);
    }


    protected void jqueryAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InterruptedException {
        resp.setContentType("text/html; Charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        System.out.println("jqueryAjax请求过来了");
        // 把student对象转换为json字符串
        Student student = new Student(1, "zzh", 18, "13155340920");
        Gson gson = new Gson();
        String stuStr = gson.toJson(student);
        // 把student对象发送回客户端
        resp.getWriter().write(stuStr);
    }

    protected void serializeAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InterruptedException {
        resp.setContentType("text/html; Charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        System.out.println("serialize请求过来了");
        System.out.println("用户名：" + req.getParameter("username"));
        System.out.println("密码：" + req.getParameter("password"));
        // 把student对象转换为json字符串
        Student student = new Student(1, "zzh", 18, "13155340920");
        Gson gson = new Gson();
        String stuStr = gson.toJson(student);
        // 把student对象发送回客户端
        resp.getWriter().write(stuStr);
    }

}

