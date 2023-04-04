package mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 @author Alex
 @create 2023-03-28-13:05
 */
@Getter
public enum SexEnum {
    MALE(1,"男"),
    FAMALE(2,"女");
    @EnumValue
    private Integer sex;
    private String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
