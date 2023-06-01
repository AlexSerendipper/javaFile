package springsecurity;

/**
 * 【SpringSecurity防止CSRF攻击】同步请求
 *  CSRF：当用户登陆过网站1后，在客户端存储了网站1的cookie，若用户在网站一填写表格(交易，转账) 后未提交
 *          此时用户浏览了某病毒网页，病毒获取了当前用户在网站1的cookie，操纵用户提交表格。此为CSRF
 *  Security 防止CSRF攻击：当启用了 security 后，security 默认开启防止CSRF攻击，做法为：当用户
 *          像服务器请求带有表格的页面时，服务器同时向用户返回了一个token（该token会在表格中以隐藏域的方式存在！）
 *          若存在病毒，病毒只能获取用户的cookie，并不能获取token，无法实现CSRF攻击
 *
 * 【SpringSecurity防止CSRF攻击】异步请求
 *  默认Security 对异步或同步请求，都需要进行进行防止CSRF攻击处理，即当服务器端在用户发送异步请求时 如果没有收到token，将禁止访问
 * （1）在异步请求的页面的header处，手动生成CSRF令牌的key和value。当用户访问该页面时，可以自动生成
--------------
<!--用户访问该页面，在此处生成CSRF令牌的key-->
<meta name="_csrf_header" th:content="${_csrf.headerName}">
<!--用户访问该页面，在此处生成CSRF令牌的value-->
<meta name="_csrf" th:content="${_csrf.token}">
--------------
 * （2）在异步请求发送 前！！，同时也将手动生成的token，以请求头的形式发送出去
--------------
// 在发送AJAX请求之前，将CSRF令牌设置到请求的消息头中
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function (e,xhr,options){
    xhr.setRequestHeader(header,token);
})
--------------
 * （3）若无需处理CSRF攻击，只需要在security config类中的 授权相关配置处进行配置即可。
 *  .csrf().disable()
 *
 @author Alex
 @create 2023-04-22-15:21
 */
public class US02 {
}
