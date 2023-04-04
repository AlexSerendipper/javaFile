package mybatisplus.boot;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import mybatisplus.service.ProductService;
import mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 @author Alex
 @create 2023-03-27-11:34
 */
@SpringBootTest
public class UMP10 {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Test
    public void testDynamicDataSource(){
        System.out.println(userService.getById(7L));
        System.out.println(productService.getById(1L));
    }
}

