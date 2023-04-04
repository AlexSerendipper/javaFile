package datafunction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author Alex
 @create 2023-03-23-21:53
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
// @Slf4j
public class Emp {
    private Integer eid;
    private String empName;
    private Integer age;
    private char sex;
    private String email;
    private String did;
}
