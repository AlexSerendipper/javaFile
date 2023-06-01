package usecollection;

import org.junit.Test;

import java.util.Arrays;

/**
 * 【加强for循环练习，判断输出】
 * @author Alex
 * @create 2022-12-04-14:44
 */
public class Q1 {
    @Test
    public void test(){
        String[] str = new String[5];
        String[] str1 = new String[5];
        // 1、普通for循环是可以完成赋值操作
        for (int i = 0; i < str.length; i++) {
            str[i] = "atguigu";
        }
        System.out.println(Arrays.toString(str));
        System.out.println("*****************");

        // 2、而加强for循环是把str中的每一个值取出来（后赋值不改变原先的数组）
        for (String myStr : str1) {
            myStr = "atguigu";
        }
        System.out.println(Arrays.toString(str1));
    }
}
