package mybatisplus;

import mybatisplus.mapper.ProductMapper;
import mybatisplus.pojo.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 【乐观锁 与 悲观锁插件场景引入】
 *  一件商品，成本价是80元，售价是100元。老板先是通知小李，说你去把商品价格增加50元。
 *    因为一些原因，又通知小王，你把商品价格降低30元。
 *    此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格100元；
 *    小王也在操作，取出的商品价格也是100元。小李将价格加了50元，并将100+50=150元存入了数据库；
 *    小王将商品减了30元，并将100-30=70元存入了数据库。是的，如果没有锁，小李的操作就完全被小王的覆盖了。
 *    现在商品价格是70元，比成本价低10元......
 *  如果是乐观锁，小王保存价格前，会检查下价格是否被人修改过了。如果被修改过，则重新取出的被修改后的价格，150元，这样他会将120元存入数据库。
 *   如果是悲观锁，小李取出数据后，小王只能等小李操作完之后，才能对价格进行操作，也会保证最终的价格是120元
 *
 * 【乐观锁实现流程】
 *   传统做法：数据库中添加version字段。取出记录时，获取当前version。
 *                更新时，version + 1，如果where语句中的version版本不对，则更新失败。
 *   SELECT id,`name`,price,`version` FROM product WHERE id=1
 *   UPDATE product SET price=price+50, `version`=`version` + 1 WHERE id=1 AND `version`=1
 *
 *   mybatisplus做法：
 *  （1）在pojo中使用@Version注解标记乐观锁的版本号字段
 *  （2）在配置类中添加乐观锁插件
---------------------
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 创建分页插件 并 指定数据库
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
---------------------
 * (3) 此时，使用MybatisPlus进行添加操作时，会自动判断版本号是否正确
 *
 * 【悲观锁】没讲
 *
 @author Alex
 @create 2023-03-28-11:24
 */
public class UMP07 {
    @Autowired
    ProductMapper productMapper;

    // 乐观锁，小王操作时由于版本号不对，操作失败，故最终结果为150
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
        // 价格覆盖，最后的结果：
        System.out.println("最后的结果：" + p3.getPrice());
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
