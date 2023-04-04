package webfunction.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import webfunction.UW05;
import webfunction.pojo.Pet;

/**
 @author Alex
 @create 2023-03-18-13:20
 */


@Configuration(proxyBeanMethods = true)
public class config implements WebMvcConfigurer {
    // 开启矩阵变量功能
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        // 设置不移除封号后的内容，即开启矩阵变量功能
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    // 自定义格式转换
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

    // 添加拦截器。指定拦截规则【如果是拦截所有，静态资源也会被拦截】
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UW05())
                .addPathPatterns("/success")  // 拦截路径，若拦截所有，静态资源也会被拦截
                .excludePathPatterns("/","/css/**","/js/**");  // 放行路径，不能拦截静态资源
    }
}
