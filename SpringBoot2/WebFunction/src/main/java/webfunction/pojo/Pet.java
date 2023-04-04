package webfunction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author Alex
 @create 2023-03-20-19:54
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
// @Slf4j
public class Pet {
    private String name;
    private Integer age;
}
