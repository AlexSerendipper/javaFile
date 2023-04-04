package webfunction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 @author Alex
 @create 2023-03-20-19:52
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
// @Slf4j
public class Person {
    private String username;
    private Integer age;
    private Date birth;
    private Pet pet;
}
