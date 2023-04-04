package mybatisplus.boot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mybatisplus.mapper.ProductMapper;
import mybatisplus.mapper.UserMapper;
import mybatisplus.pojo.Product;
import mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 @author Alex
 @create 2023-03-27-11:34
 */
@SpringBootTest
public class UMP07 {
    @Autowired
    ProductMapper productMapper;

    @Test
    public void testConcurrentUpdate() {
        //1、小李取出的物品
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());
        //2、小王取出的物品
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + result2);
        // 最后的结果
        Product p3 = productMapper.selectById(1L);
        // 价格覆盖，最后的结果：70
        System.out.println("最后的结果（价格覆盖）：" + p3.getPrice());
    }

    // 乐观锁，小王操作时由于版本号不对，操作失败
    // 操作失败后重新获取商品，再次操作~故最终结果为120
    @Test
    public void testConcurrentUpdate2() {
        //1、小李取出的物品
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());
        //2、小王取出的物品
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + result2);
        if(result2 == 0){
            // 操作失败后的重新尝试
            Product newProduct = productMapper.selectById(1L);
            newProduct.setPrice(newProduct.getPrice()-30);
            productMapper.updateById(newProduct);
        }
        // 最后的结果
        Product p3 = productMapper.selectById(1L);
        // 价格覆盖，最后的结果：
        System.out.println("最后的结果：" + p3.getPrice());
    }
}
