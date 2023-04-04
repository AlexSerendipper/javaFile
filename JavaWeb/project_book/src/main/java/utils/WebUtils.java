package utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * 把 Map 中的值注入到对应的 JavaBean 属性中
 @author Alex
 @create 2023-02-03-13:02
 */
public class WebUtils {
    public static <T> T copyParamToBean(Map value , T bean){
        try {
             // 把所有请求的参数都注入到 user 对象中
            BeanUtils.populate(bean, value);
//            System.out.println("注入之后：" + bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    // 实现将字符串的数字传进来，输出数字出去
    public static int parseInt(String id,int default_value) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            System.out.println("没有拿到值，输出默认值");
        }
        // 类型转化成功则输出，转换失败输出默认值
        return default_value;
    }
}
