package basicfunction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 【@RequestMapping】请求映射：通过该注解，SpringMVC接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求
 *  必须保证@RequestMapping映射的上下文路径的唯一性，否则报错
 *    @RequestMapping标识一个类：设置映射请求的请求路径的初始信息✔
 *    @RequestMapping标识一个方法：设置映射请求请求路径的具体信息✔
 *
 * 【value属性】@RequestMapping的value属性
 *  表示该请求映射能够匹配请求地址所对应的请求
 *  value属性是一个字符串类型的数组，表示可以匹配多个地址✔
 *  value属性必须设置
 *  value属性支持模糊匹配✔
 *    ？：表示任意的单个字符
 *    *：表示任意的0个或多个字符
 *    **：表示任意的0层或多层目录
 *   注意：在使用**时，只能使用 /** 的格式，不能使用这样的格式 /a**a
 *  value属性支持路径占位符✔✔✔✔✔
 *    SpringMVC路径中的占位符常用于RESTful风格中，即把请求参数均通过路径的方式传输到服务器中
 *    在Value属性中通过占位符{xxx}表示传输的数据，如 /get/{name}/{id}
 *     在通过 @PathVariable("占位符")注解，将占位符所表示的数据赋值给控制器方法的形参, 如 public String getName(@PathVariable("name") username)
 *     还可以 将占位符中所有的数据 全部自动放在map中，如public String getName(@PathVariable Map<String,String> pv)
 *    若使用了路径占位符，则在请求路径中 必须为占位符处赋值
 *
 * 【method属性】@RequestMapping的method属性
 *  method属性通过请求的请求方式（get或post）匹配请求映射, 默认可以同时匹配get和post✔
 *  method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配多种请求方式的请求
 *  若当前请求的请求地址满足请求映射的value属性，但是请求方式不满足method属性，则浏览器报错405
 *  SpringMVC中提供了@RequestMapping的派生注解。用来处理常用的请求方式：get，post，put，delete。用这些注解，就无需设置method属性了
 *    @GetMapping      处理get请求的映射
 *    @PostMapping     处理post请求的映射
 *    @PutMapping      处理put请求的映射
 *    @DeleteMapping   处理delete请求的映射
 *   但，目前浏览器只支持get和post请求，若在form表单提交时，为method设置了其他请求方式的字符串（put或delete），则按照默认的请求方式get处理
 *   若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter，在RESTful部分会讲到
 *
 * 【params属性】了解。@RequestMapping的params属性
 *  params属性表示通过 请求的请求参数 匹配请求映射
 *  params属性是一个字符串类型的数组，可以通过四种表达式 组合设置 请求参数和请求映射的匹配关系
 *    "param"          要求请求映射所匹配的请求必须携带名为 param 的请求参数
 *    "!param"         要求请求映射所匹配的请求必须不能携带名为 param 的请求参数
 *    "param=value"    要求请求映射所匹配的请求必须携带名为 param 的请求参数 且 值为 value
 *    "param!=value"   要求请求映射所匹配的请求必须携带名为 param 的请求参数 但 值不为 alue
 *
 * 【header属性】了解。@RequestMapping的header属性
 *  headers属性通过请求的请求头信息匹配请求映射
 *  headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系
 *    "header"         要求请求映射所匹配的请求必须携带名为 header 的请求头信息
 *    "!header"        要求请求映射所匹配的请求必须不能携带名为 header 的请求头信息
 *    "header=value"   要求请求映射所匹配的请求必须携带名为 header 的请求头信息且 值value
 *    "header!=value"  要求请求映射所匹配的请求必须携带名为 header 请求头信息且 值不为value
 *
 @author Alex
 @create 2023-03-02-11:27
 */

@Controller
@RequestMapping("/**/test")  // 任意的多层目录
public class UBF03 {
    // 此时请求映射所映射的请求的请求路径为：/test/UBF03?username=zzj
    @RequestMapping(value = {"/UBF03","/test"},method = {RequestMethod.POST},
                    params = {"username=zzj"},
                    headers = {"Host=localhost:8080"})
    public String testRequestMapping(){
        return "UBF03";
    }


    // value属性支持路径占位符
    // 使用@PathVariable指定占位符中的username，赋值给形参name
    @RequestMapping(value = {"/UBF03/{username}/{password}"})
    public String testRequestMapping2(@PathVariable("username") String name,@PathVariable("password") String password){
        System.out.println("将占位符中的参数自动赋值给方法的形参:" + name + ":" + password);
        return "UBF03";
    }
}
