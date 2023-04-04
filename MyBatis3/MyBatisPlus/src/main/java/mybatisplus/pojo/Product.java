package mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author Alex
 @create 2023-03-28-11:31
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mybatisplus_product")
// @Slf4j
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version
    private Integer version;
}
