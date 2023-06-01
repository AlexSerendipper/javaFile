package usecommonclass;

import org.junit.Test;
import java.util.Arrays;

/**
 * 一些常见的算法题
 *
 * @author Alex
 * @create 2022-11-20-13:24
 */

public class Q2 {
    // 题目1：模拟一个trim方法，去除字符串两端的空格
    // 思路1：采用正则替换
    @Test
    public void test1() {
        String str1 = " asdb    ";
        String str2 = str1.replaceAll("^\\s*", "").replaceAll("\\s*$", "");
        System.out.println(str2);
    }

    // 题目1：标准答案
    @Test
    public void test1_1() {
        String str = " asdb    ";
        int start = 0;// 用于记录从前往后首次索引位置不是空格的位置的索引
        int end = str.length() - 1;// 用于记录从后往前首次索引位置不是空格的位置的索引
        while (start < end && str.charAt(start) == ' ') {
            start++;
        }
        while (start < end && str.charAt(end) == ' ') {
            end--;
        }
        System.out.println("str.substring(start, end + 1) = " + str.substring(start, end + 1));
    }

    // 题目2：将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反转为"abfedcg"
    // 思路1：取出为char[]后进行翻转，然后再转换为string
    @Test
    public void test2() {
        String str1 = "abcdefg";
        String str2 = str1.substring(2, 6);
        System.out.println(str2);
        char[] chars = str2.toCharArray();  // 转换为char[]
        // System.out.println(chars.length);
        char[] chars2 = new char[chars.length];

        int j = chars.length - 1;
        for (int i = 0; i < chars.length; i++) {   // 反转操作
            chars2[i] = chars[j];
            j--;
        }

        String str3 = new String(chars2);
        String str4 = str1.replace(str2, str3);
        System.out.println(str4);
    }


    // 题目2：标准答案1
    @Test
    public void test2_1() {
        String str1 = "abcdefg";
        char[] chars = str1.toCharArray();
        for (int x = 2, y = 5; x < y; x++, y--) {  // x和y对应开始和结束的索引
            char temp = chars[x];
            chars[x] = chars[y];
            chars[y] = temp;
        }
        System.out.println(new String(chars));
    }

    // 题目2：标准答案2（高效）
    @Test
    public void test2_2() {
        String str = "abcdefg";
        StringBuilder s = new StringBuilder(str.length());
        // 1. 先存两个不翻转的字母
        s.append(str.substring(0, 2));  // ab

        // 2. 把需要翻转的部分翻转后储存进stringbuider
        for (int i = 5; i >= 2; i--) {
            s.append(str.charAt(i));
        }

        // 3. 存最后一个不翻转的字母
        s.append(str.substring(6));
        System.out.println("s.toString() = " + s.toString());
    }

    // 题目3：获取一个字符串在另一个字符串中出现的次数。比如：获取“ ab”在 “abkkcadkabkebfkabkskab” 中出现的次数
    @Test
    public void test3() {
        String str1 = "abkkcadkabkebfkabkskab";
        int inx = 0;
        int sum = 0;
        for (; ; ) {
            inx = str1.indexOf("ab", inx);
            if (inx != -1) {
                sum++;
                inx++;
            } else {
                break;
            }
        }
        System.out.println("ab出现的次数为" + sum);
    }

    // 题目4：获取两个字符串中最大相同子串。比如：str1 = "abcwerthelloyuiodef“; str2 = "cvhellobnm"
    // 提示：将短的那个串进行长度依次递减的子串与较长的串比较。
    @Test
    public void test4() {
        String str1 = "abcwerthelloyuiodef";
        String str2 = "cvhellobnm";
        String str3 = "";
        // System.out.println("str2.contains(\"cv\") = " + str2.contains("cv"));
        int argmax = 0;
        for (int i = 0; i < str2.length(); i++) {  // i控制起始位置(startindex)，j控制其后的个数(endindex)
            for (int j = str2.length(); j >= i + 1; j--) {
                if (str1.contains(str2.substring(i, j))) {
                    if (str2.substring(i, j).length() > argmax) {
                        argmax = str2.substring(i, j).length();  // 变量赋值，相当于改变其对堆空间的指向
                        str3 = str2.substring(i, j);
                    }
                }
            }
        }
        System.out.println(str3);
    }

    // 题目4改进：当有两个相同长度的最小子串时
    @Test
    public void test4_1() {
        String str1 = "abvncwerthelloyuiodef";
        String str2 = "cvnhellobnmiodef";
        StringBuffer str3 = new StringBuffer();
        // System.out.println("str2.contains(\"cv\") = " + str2.contains("cv"));
        int argmax = 0;
        for (int i = 0; i < str2.length(); i++) {  // i控制起始位置(startindex)，j控制其后的个数(endindex)
            for (int j = str2.length(); j >= i + 1; j--) {
                if (str1.contains(str2.substring(i, j))) {
                    if (str2.substring(i, j).length() >= argmax) {  // 这里是>=
                        argmax = str2.substring(i, j).length();  // 变量赋值，相当于改变其对堆空间的指向
                        str3.append(str2.substring(i, j) + ",");
                    }
                }
            }
        }

        // 接下来就是把这个stringbuffer转换为数组形式（split），然后取出其中最长的几个字符串
        System.out.println(str3);
        String str4 = new String(str3);
        String[] str5 = str4.split(",");
        argmax = 0;  // 得到最大长度
        for(String s:str5){
            if(s.length()>argmax){
                argmax = s.length();
            }
        }
        for(String s:str5){
            if(s.length() == argmax){
                System.out.println(s);
            }
        }
    }

    // 题目4：标准答案，就是第一轮找0-9，第二轮0-8，1-9，第三轮0-7，1-8，2-9
    // 这样子遍历最大的好处就是不用全跑完，因为是从最大长度开始找的，找到就停
    @Test
    public void test4_2() {
        String str1 = "abvncwerthelloyuiodef";
        String str2 = "cvnhellobnmiodef";
        StringBuffer str3 = new StringBuffer();
//      System.out.println("str2.contains(\"cv\") = " + str2.contains("cv"));
        int length = str2.length();
        for (int i = 0; i < length; i++) {  // i控制轮数
            for (int x = 0, y = length-i; y <= length; x++, y++) {  // x控制起始位置，y控制最终位置,这里左闭右开，非常关键，所以是length-i
                if (str1.contains(str2.substring(x, y))) {
                    str3.append(str2.substring(x, y) + ",");
                }
            }
            if (str3.length() != 0) {
                break;
            }
        }
        System.out.println(str3);
        // 接下来就是把这个stringbuffer转换为数组形式（split），然后取出其中最长的几个字符串，和上面一样
    }


    // 题目5：对字符串中字符进行自然顺序排序。
    // 1）字符串变成字符数组。str1 = "abcwerthelloyuiodef"
    // 2）对数组排序，选择，冒泡，Arrays.sort();
    // 3）将排序后的数组变成字符串
    @Test
    public void test5() {
        String str1 = "abcwerthelloyuiodef";
        char[] chars = str1.toCharArray();
        Arrays.sort(chars);
        String str2 = new String(chars);
        System.out.println(str2);
    }

}
