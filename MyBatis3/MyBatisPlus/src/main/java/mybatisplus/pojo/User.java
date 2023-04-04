package mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mybatisplus.enums.SexEnum;

/**
 @author Alex
 @create 2023-03-25-12:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mybatisplus_user")
// @Slf4j
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    private SexEnum sex;

    public User(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public User(String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public User(String name, Integer age, String email, SexEnum sex) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.sex = sex;
    }

    @TableField
    @TableLogic
    private Integer isDelete;
}
