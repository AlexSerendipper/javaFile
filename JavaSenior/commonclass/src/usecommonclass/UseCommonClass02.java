package usecommonclass;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 【String类的常用方法】
 * int length()：返回字符串的长度
 * char charAt(int index)：返回某索引处的字符
 * boolean isEmpty()：判断是否是空字符串
 * String toLowerCase()：使用默认语言环境，将 String中的所有字符转换为小写
 * String toUpperCase()：使用默认语言环境，将 String中的所有字符转换为大写
 * String trim()：返回字符串的副本，忽略前导空白和尾部空白
 * boolean equals(Object obj)：比较字符串的内容是否相同
 * boolean equalsIgnoreCase(String anotherString)：与equals方法类似，忽略大小写
 * String concat(String str)：将指定字符串连接到此字符串的结尾。等价于用“+”
 * int compareTo(String anotherString)：比较两个字符串的大小
 * String substring(int beginIndex)：返回一个新的字符串，它是此字符串的从beginIndex开始截取到最后的一个子字符串。
 * String substring(int beginIndex, int endIndex) ：返回一个新字符串，它是此字符串从beginIndex开始截取到endIndex(不包含)的一个子字符串（左闭右开）
 * 【查找】
 * boolean contains(CharSequence s)：当且仅当此字符串包含指定的 char 值序列时，返回 true
 * int indexOf(String str)：返回指定子字符串在此字符串中第一次出现处的索引
 * int indexOf(String str, int fromIndex)：返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
 * int lastIndexOf(String str)：返回指定子字符串在此字符串中最右边出现处的索引
 * int lastIndexOf(String str, int fromIndex)：返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索
 * 注：indexOf和lastIndexOf方法如果未找到都是返回-1
 * boolean endsWith(String suffix)：测试此字符串是否以指定的后缀结束
 * boolean startsWith(String prefix)：测试此字符串是否以指定的前缀开始
 * boolean startsWith(String prefix, int toffset)：测试此字符串从指定索引开始的子字符串是否以指定前缀开始
 * 【替换】
 * String replace(char oldChar, char newChar)：返回一个新的字符串，它是通过用 newChar替换此字符串中出现的所有 oldChar得到的。
 * String replace(CharSequence target, CharSequence replacement)：使用指定的字面值(字符串)替换序列替换此字符串所有匹配字面值目标序列的子字符串。
 * String replaceAll(String regex, String replacement) ：使用给定的replacement替换此字符串所有匹配给定的正则表达式的子字符串。
 * String replaceFirst(String regex, String replacement) ：使用给定的replacement替换此字符串匹配给定的正则表达式的第一个子字符串。
 * boolean matches(String regex)：告知此字符串是否匹配给定的正则表达式。
 * 【切片】
 * String[] split(String regex)：根据给定正则表达式的匹配拆分此字符串。
 * String[] split(String regex, int limit)：根据匹配给定的正则表达式来拆分此字符串，最多不超过limit个，如果超过了，剩下的全部都放到最后一个元素中。
 *
 *
 * @author Alex
 * @create 2022-11-20-14:07
 */
public class UseCommonClass02 {
   // 字符串常用方法测试
   @Test
   public void test(){
       String str = "hello|world|java";
       String[] strs = str.split("\\|");  // 这个转义为啥要用两个 我晕
       for (int i = 0; i < strs.length; i++) {
           System.out.println(strs[i]);
       }
   }

   // 【String与byte[] 转换】
   @Test
   public void test1() throws UnsupportedEncodingException {
       String str1 = "abc123中国";
       byte[] bytes = str1.getBytes();  // 编码：使用默认的字符集进行转换，三位表示一个汉字
       System.out.println(Arrays.toString(bytes));

       byte[] gbks = str1.getBytes("gbk");  // 使用指定的字符集进行转换，两位表示一个汉字
       System.out.println(Arrays.toString(gbks));

       String str2 = new String(bytes);
       System.out.println(str2);

       String str3 = new String(gbks, "gbk");
       System.out.println(str3);
   }
}
