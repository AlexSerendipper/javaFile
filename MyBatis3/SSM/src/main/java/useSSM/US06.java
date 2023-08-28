package useSSM;

/**
 * 【案例1】见控制器 EmpController
 * /emp-->get请求-->查询所有用户信息
 * /emp/page/1-->get请求-->查询所有用户信息，分页信息
 * /emp/1-->get请求-->根据id查询用户信息
 * /emp/add -->get请求 --> 跳转到添加页面
 * /emp-->post请求-->添加用户信息
 * /emp/1-->delete请求-->根据id删除用户信息
 * /emp-->put请求方式-->更新用户信息
 *
 * 【案例2】根据输入的时间查询表中的数据，见holidayController
 * （1）经过尝试，发现前端的<input type="date">表单，传过来的数据是'yyyy-MM-dd'的字符串类型的时间数据
 *      而本质上java.sql.date就是上述格式的字符串的数据，所以可以通过java.sql.date直接接收上述数据
 *      而查询结果为holiday，故将Holiday的time属性类型也设置为Java.sql.date即可
 *
 *      麻烦一些的做法就是通过@DateTimeFormat，将接收的字符串数据转换为java.util.Date类型的数据
 *      然后将date类型的数据转换为java.sql.date类型的数据()
 * （2）经过尝试，发现前端的<input type="datetime-local">表单, 传过来的数据是'yyyy-MM-ddTHH:mm'的字符串类型的时间数据
 *      这时候就需要使用SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm")，把字符串转换成date类型数据
 *      此date类型的数据可以直接赋值给数据库中的dateTime类型的数据
 *
 * 【访问 http://localhost:8080/ssm/】
 *
 @author Alex
 @create 2023-03-17-12:55
 */
public class US06 {

}
