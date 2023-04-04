package mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mybatisplus.mapper.ProductMapper;
import mybatisplus.pojo.Product;
import mybatisplus.service.ProductService;
import org.springframework.stereotype.Service;

/**
 @author Alex
 @create 2023-03-28-15:17
 */

@DS("slave_1")
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
