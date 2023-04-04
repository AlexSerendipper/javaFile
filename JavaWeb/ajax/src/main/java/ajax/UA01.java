package ajax;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【JSON】见json.html
 *  JSON (JavaScript Object Notation) 是一种轻量级的数据交换格式。
 *   轻量级指的是跟xml做比较（目前主要使用json来做数据交换了，xml主要用于配置文件）
 *   数据交换指的是客户端和服务器之间业务数据的传递格式
 *
 * 【客户端使用json】在javascript中的使用✔✔✔。见json.html
 *   json的两种存在形式
 *    （1）json对象:
 *      json对象由大括号包围。是由键值对组成，并且每个键由 双引号 引起来，键和值之间使用冒号进行分隔，多组键值对之间进行逗号进行分隔。
 *         在前端中 以json对象 的形式存在。如 {"firstName": "Bill", "lastName": "Gates"}
 *           json对象可以直接通过 json对象.key 访问值
 *         在后端中，以json字符串的的形式存在，如 "{ "firstName": "Bill", "lastName": "Gates" }"
 *           通常在客户端和服务器之间进行数据交换的时用json字符串格式。
 *     (2) json数组：json对象为元素构成的数组
 *      json数组，可以直接通过for循环 取出json数组中的数据
 *   前端中操作json的两个常用方法
 *   JSON.stringify()            # 把json对象转换成为json字符串，类似于java的toString
 *   JSON.parse()                # 把json字符串转换成为json对象
 *
 * 【服务器端使用json】在java中的使用✔✔✔，需要导入gson包~
 *  javaBean和json对象的互转
 *    new Gson();                  # 创建 Gson 对象实例
 *    gson.toJson(stu);            # toJson方法可以把javabean对象转换成为json字符串✔
 *    gson.fromJson(str, Student.class);                 # fromJson把json字符串转换回Java对象。第一个参数是json字符串。第二个参数是转换回去的Java对象类型
 *  List和json的互转
 *    new Gson();                  # 创建 Gson 对象实例
 *    gson.toJson(stu);            # toJson方法可以把javabean对象List转换成为json数组✔
 *    StudentListType extends TypeToken<ArrayList<Student>>{}        # 创建一个类，继承gson包中定义的TypeToken，传入我们最终需要转换为的类型
 *    gson.fromJson(str, new StudentListType().getType());           # fromJson把json字符串转换回Java对象
 *                                                                      第一个参数是json字符串。第二个参数是转换回去的Java对象类型
 *                                                                      这里使用的是创建类的形式
 *  map和json的互转
 *    new Gson();                  # 创建 Gson 对象实例
 *    gson.toJson(stu);            # toJson方法可以把javabean对象Map转换成为json字符串✔
 *    gson.fromJson(personMapStr, new TypeToken<HashMap<Integer,Student>>(){}.getType());      # fromJson把json字符串转换回Java中的map对象
 *                                                                                                第一个参数是json字符串。第二个参数是转换回去的Java对象类型
 *                                                                                                这里使用的是匿名实现类的形式
 @author Alex
 @create 2023-02-18-13:51
 */
public class UA01 {
    // javaBean和json的互转
    @Test
    public void test(){
        Student student = new Student(1,"zzh",18,"13155340920");
        // 创建 Gson 对象实例
        Gson gson = new Gson();
        // toJson 方法可以把 java 对象转换成为 json 字符串
        String stuStr = gson.toJson(student);
        System.out.println(stuStr);
        // fromJson 把 json 字符串转换回 Java 对象
        // 第一个参数是 json 字符串
        // 第二个参数是转换回去的 Java 对象类型
        Student stu = gson.fromJson(stuStr, Student.class);
        System.out.println(stu);
    }

    // List和json的互转
    @Test
    public void test2(){
        List<Student> personList = new ArrayList<>();
        personList.add(new Student(1,"zzh",18,"13155340920"));
        personList.add(new Student(2,"hyq",20,"12345670920"));
        Gson gson = new Gson();
        // 把 List 转换为 json 字符串
        String stuListStr = gson.toJson(personList);
        System.out.println(stuListStr);
        List<Student> list = gson.fromJson(stuListStr, new StudentListType().getType());
        System.out.println(list);
    }

    // map和json的互转
    @Test
    public void test3() {
        Map<Integer, Student> personMap = new HashMap<>();
        personMap.put(1, new Student(1, "zzh", 18, "13155340920"));
        personMap.put(2, new Student(2, "hyq", 20, "12345670920"));
        Gson gson = new Gson();
        // 把map集合转换成为json字符串
        String personMapStr = gson.toJson(personMap);
        System.out.println(personMapStr);

        // 方式一：创建类 Map<Integer,Student> personMap = gson.fromJson(personMapStr, new StudentMapType().getType());
        // 方式二：匿名实现类
        gson.fromJson(personMapStr, new TypeToken<HashMap<Integer, Student>>() {
        }.getType());
        System.out.println(personMap);
    }
}
