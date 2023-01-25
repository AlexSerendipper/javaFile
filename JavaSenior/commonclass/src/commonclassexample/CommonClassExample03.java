package commonclassexample;

import org.junit.Test;

/**
 * 判断程序输出，啊这有点搞
 *
 * @author Alex
 * @create 2022-11-21-14:04
 */
public class CommonClassExample03 {
    @Test
    public void test() {
        String str = null;
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        System.out.println(sb.length());  // 1.  4✔debug看源码把
        System.out.println(sb);  // 2.   "null"
        StringBuffer sb1 = new StringBuffer(str);  // 这里就抛异常咯~
        System.out.println(sb1);  // 3.   wrong
    }
}
