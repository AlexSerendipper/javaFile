package webfunction;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webfunction.pojo.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【获取请求参数 & 共享域对象】
 *  springboot中，获取请求参数 & 共享域对象的方式与 springMVC 完全一致
 *
 * 【获取特殊请求参数补充】springMVC UBF04补充。见下方示例
 *（5）@MatrixVariable: 获取矩阵变量
 *  矩阵变量，即绑定于具体请求路径后的参数，格式为 key=value;key=value; 并且使用矩阵变量需要使用路径占位符
 *   如：原本的请求路径为 /cars/buy/sell
 *       绑定矩阵变量后 实际请求路径为（从客户端发起的请求地址） /cars/buy;low=34;brand=byd,audi/sell;low=60;logo=durex,bwm
 *       绑定矩阵变量后 获取路径为（在服务器端接收地址） /cars/{buy}/{sell}
 *  若请求路径中有相同名称的矩阵变量，可以通过pathVar属性进行区分。 如：@MatrixVariable(value = "low",pathVar = "sell")
 *  SpringBoot默认关闭了矩阵变量的功能，需要手动开启
--------------------------------------------------
// 对于请求路径的处理，依赖于WebMvcAutoConfiguration类 中的内部配置类 public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer() 中的
                                                                   public void configurePathMatch() 方法中的 UrlPathHelper类
// UrlPathHelper类中的 removeSemicolonContent属性（默认为true） 用于控制是否开启矩阵变量功能。
// UrlPathHelper类，并不是配置类，并没有与配置文件绑定。故需要采用自定义组件的方式进行开启！✔

// 开启方式一：
public class config implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        // 设置不移除封号后的内容，即开启矩阵变量功能
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
--------------------------------------------------
 * 【✔✔✔自定义组件✔✔✔】
 *  当某些组件 并没有与配置文件绑定(大部分场景修改 配置文件就够了)。需要采用自定义组件的方式进行改变其某些值，则需要自定义组件！✔
 *  方式一：springboot推荐使用 @configuration + WebMvcConfigurer 来自定义组件规则 （底层许多自动配置类会继承 WebMvcConfigurer，方便进行配置的修改）
 *          即让 配置类 去继承WebMvcConfigurer接口，通过实现其中默认方法，来改变规则
 *  方式二：方式一的补充，使用@bean创建一个WebMvcConfigurer实例（面向接口编程，提供默认实现类），重写其内部的默认方法
 *  方式三：（1）普通对象
 *                在配置类中@bean方法中，创建对象来自定义组件，因为底层许多组件是根据@ConditionalOnMissingBean()来进行判断，根据就近原则会使用我们自己配置的bean
 *           （2）servlet对象
 *                在配置类中@bean的 public ServletRegistrationBean servletRegistrationBean(){} 方法中创建servlet, 并放置在ServletRegistrationBean中返回
 *           （3）filter对象
 *               在配置类中@bean的  public FilterRegistrationBean WebStatFilter(){} 方法中创建filter, 并放置在 FilterRegistrationBean 中返回
------------------------------------------------
// 开启方式二：
@Bean
public WebMvcConfigurer webMvcConfigurer(){
    return new WebMvcConfigurer() {
        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
            UrlPathHelper urlPathHelper = new UrlPathHelper();
            // 设置不移除封号后的内容，即开启矩阵变量功能
            urlPathHelper.setRemoveSemicolonContent(false);
            configurer.setUrlPathHelper(urlPathHelper);
        }
    };
}
------------------------------------------------
 *（6）通过POJO获取请求参数（若pojo中有一个属性是类）
 *  通过级联的方式，为其赋值
 *  自定义组件converter, 自定义规则
-------------------------------------
@Override
public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new Converter<String , Pet>() {
    @Override
    public Pet convert(String s) {
        // s为传递过来的字符串
        // 当传递过来的格式为 lyj,18 的格式，如何进行解析
        Pet pet = new Pet();
        String[] split = s.split(",");
        pet.setName(split[0]);
        pet.setAge(Integer.parseInt(split[1]));
        return pet;
        }
    });
}
-------------------------------------
 *
 @author Alex
 @create 2023-03-20-13:53
 */


// /cars/buy;low=34;brand=byd,audi/sell;low=60;logo=durex,bwm
@RestController
public class UW03 {
    @GetMapping(value = "/cars/{buy}/{sell}")
    public String carSell(@MatrixVariable(value = "low",pathVar = "buy") Integer low1,
                       @MatrixVariable(value = "low",pathVar = "sell") Integer low2,
                       @MatrixVariable(value = "brand") List<String> brand,
                       @PathVariable("buy") String path){
        System.out.println("low1 = " + low1);
        System.out.println("low2 = " + low2);
        System.out.println("brand = " + brand);
        // 访问路径还是buy，封号后为矩阵变量，注意区分
        System.out.println("path = " + path);
        return "success";
    }

    // 在springboot中，不用th语法发送请求，也能找到对应的控制器，牛逼
    @PostMapping(value = "/saveperson1")
    public Person savePerson1(Person person){
        return person;
    }

    @PostMapping(value = "/saveperson2")
    public Person savePerson2(Person person){
        return person;
    }
}
