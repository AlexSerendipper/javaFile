package corefunction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author Alex
 @create 2023-03-18-13:18
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
// @EqualsAndHashCode
// @Slf4j
public class User {
    private String name;
    private Integer age;
    private Pet pet;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
