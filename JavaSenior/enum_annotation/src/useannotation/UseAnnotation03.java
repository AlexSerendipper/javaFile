package useannotation;

import java.lang.annotation.*;

/**
 * 【jdk8之前，要重复注解，只能如下通过数组的方式】
 * @author Alex
 * @create 2022-12-03-13:14
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.PARAMETER,ElementType.CONSTRUCTOR,ElementType.LOCAL_VARIABLE})
public @interface UseAnnotation03 {
    UseAnnotation02[] value();
}
