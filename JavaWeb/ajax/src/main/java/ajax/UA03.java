package ajax;

/**
 * 【jQuery中的AJAX 请求】见Jquery_Ajax_request.html
 *   url 表示请求的地址
 *   type 表示请求的类型（GET 或 POST 请求）
 *   data 表示发送给服务器的数据，以下两种方式最终都是以（键=值的方式 附在上下文路径 http://localhost:8080/? 后面）
 *     name=value&name=value
 *     {key:value}                                        # 其实更推荐这种方式✔，因为value值可能是一个变量
 *  success 请求成功，响应的回调函数                      # 即原生xml中请求成功后执行的函数
 *  dataType 响应的数据类型
 *     text表示纯文本
 *     xml表示xml数据
 *     json表示json对象
 *
 * 【四个方法】见Jquery_Ajax_request.html
 *  ajax请求：可以发起get和post请求。需要：url、data、type、success、dataType五个参数
 *  get请求：封装了ajax请求。只需要url、data、callback 、type四个参数，顺序传入（不用再写键值了）
 *  post请求：封装了ajax请求。只需要url、data、callback、type 四个参数，顺序传入（不用再写键值了）
 *  getJSON请求：封装了get请求，只需要url、data、callback三个参数，顺序传入（不用再写键值了）
 *  .serialize()：可以把表单中所有表单项的内容都获取到，并以 name=value&name=value的形式进行拼接然后提交给服务器
 *                 我们原先是整个页面提交，现在用ajax提交，不会舍弃原来的页面
---------------------------------------------------------------------
                        // 普通传递字符串过去
                         $("#ajaxBtn").click(function(){
                            $.ajax({
                                    url:"http://localhost:8080/ajax/ajaxServlet",
                                    data:"action=jQueryAjax",
                                    type:"GET",
                                    // 服务器返回的数据就是d，不用像原生做法那也去做转换了
                                    success:function (d) {
                                        $("#msg").html("编号：" + d.id + " , 姓名：" + d.name);
                                    },
                                    // 设置了返回数据类型，根据返回数据类型自动转换为js对象！！就不用像原生做法一样去转换了（json.parse()）
                                    dataType : "json"
                                });
                         });

                        // ajax上传文件
                         $('inputBtn').click(function(){
                             // 使用formData来传递参数，获取form表单，并取出表单数据
                             // 这里xxx为表单，[0]表示表单中的第一个多段传输的元素控件值，所以此处一定为0
                             // 建议将input:file的name属性，controller接收值，全都设置为formData，保证一致性
                             var formData=new FormData($('#xxx')[0]);
                             $.ajax({
                                 url:'',
                                 data:formData,
                                 async:false,
                                 type:'post',
                                 // （固定格式）ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
                                 contentType:false,
                                 // （固定格式）
                                 processData:false,
                                     success：function(d){
                                     console.log(d);
                                 }
                             })
                         })

 ----------------------------------------------------------------
 * 【开发遇到的问题】使用ajax碰到进入不了success回调函数的原因分析
 * （1）后端返回的格式，与dataType设置的格式类型不一致！！
 * （2）ajax默认使用异步模式（async:false）
 *      发送ajax异步后，在等待server端返回数据的过程中，前端程序会继续执行ajax块之后的内容，直到server返回正确的结果。如果后面的程序有刷新页面的操作！就会错过success函数中数据的回显！
 *      而使用同步方式时（async:true），发送ajax请求后，程序会一直等待server端，直到server端返回数据。
 @author Alex
 @create 2023-02-18-17:23
 */
public class UA03 {
}
