package usecommonclass;

import org.junit.Test;

/**
 * 【String类的常用方法】
 *  int length()：                                                         # 返回字符串的长度
 *  char charAt(int index)：                                               # 返回某索引处的字符
 *  boolean isEmpty()：                                                    # 判断是否是空字符串
 *  String toLowerCase()：                                                 # 使用默认语言环境，将 String中的所有字符转换为小写
 *  String toUpperCase()：                                                 # 使用默认语言环境，将 String中的所有字符转换为大写
 *  String trim()：                                                        # 返回字符串的副本，忽略前导空白和尾部空白
 *  boolean equals(Object obj)：                                           # 比较字符串的内容是否相同
 *  boolean equalsIgnoreCase(String anotherString)：                       # 与equals方法类似，忽略大小写
 *  String concat(String str)：                                            # 将指定字符串连接到此字符串的结尾。等价于用“+”
 *  int compareTo(String anotherString)：                                  # 比较两个字符串的大小
 *  String substring(int beginIndex)：                                     # 回一个新的字符串，它是此字符串的从beginIndex开始截取到最后的一个子字符串。
 *  String substring(int beginIndex, int endIndex) ：                      #　返回一个新字符串，它是此字符串从beginIndex开始截取到endIndex(不包含)的一个子字符串（左闭右开）
 *  byte[] getBytes()                                                      # 方法获取字符串的ASCII码值数组
 *   `(int) ch`                                                              # 此外，使用强制类型转换，可以将单个字符 'ch' 转换为 int 类型，并打印结果为ASCII码值
 *   ch1 - ch2                                                               # 两个字符串相减，结果也为ascii码值
 *
 * 【查找】
 *  boolean contains(CharSequence s)：　　　　　　　　　　　　　　　　　  # 当且仅当此字符串包含指定的 char 值序列时，返回 true
 *  int indexOf(String str)：　　　　　　　　　　　　　　　　　　　　　　　# 返回指定子字符串在此字符串中第一次出现处的索引
 *  int indexOf(String str, int fromIndex)：　　　　　　　　　　　　　　　# 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
 *  int lastIndexOf(String str)：　　　　　　　　　　　　　　　　　　　　　# 返回指定子字符串在此字符串中最右边出现处的索引
 *  int lastIndexOf(String str, int fromIndex)：　　　　　　　　　　　　  # 返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索
 *    注：indexOf 和 lastIndexOf方法如果未找到都是返回-1
 *  boolean endsWith(String suffix)：　　　　　　　　　　　　　　　　　　　# 测试此字符串是否以指定的后缀结束
 *  boolean startsWith(String prefix)：　　　　　　　　　　　　　　　　　　# 测试此字符串是否以指定的前缀开始
 *  boolean startsWith(String prefix, int toffset)：　　　　　　　　　　  # 测试此字符串从指定索引开始的子字符串是否以指定前缀开始
 *
 * 【替换】
 *  String replace(char oldChar, char newChar)：                          # 返回一个新的字符串，它是通过用 newChar替换此字符串中出现的所有 oldChar得到的。
 *  String replace(CharSequence target, CharSequence replacement)：       # 使用指定的字面值(字符串)替换序列替换此字符串所有匹配字面值目标序列的子字符串。
 *  String replaceAll(String regex, String replacement) ：                # 使用给定的replacement替换此字符串所有匹配给定的正则表达式的子字符串。
 *  String replaceFirst(String regex, String replacement) ：              # 使用给定的replacement替换此字符串匹配给定的正则表达式的第一个子字符串。
 *  boolean matches(String regex)：                                       # 告知此字符串是否匹配给定的正则表达式。（正则表达式见javaweb）
 *  String format(String formate, object);                                # 可以使用带有占位符%d的字符串作为formate，动态输入object进行占位符的替换
 *
 * 【切片】
 *  String[] split(String regex)：                                       # 根据给定正则表达式的匹配拆分此字符串。
 *  String[] split(String regex, int limit)：                            # 根据匹配给定的正则表达式来拆分此字符串，最多不超过limit个，如果超过了，剩下的全部都放到最后一个元素中。
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


}
