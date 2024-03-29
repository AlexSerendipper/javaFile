package DOM;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * 【正则表达式】
 *   我们很需要对表达提交的密码进行校验，所以需要使用正则表达式
 *   在js中使用test方法可以对表单进行验证，如
 *     $("#sub_btn").click(function () {
 *       //1 获取用户输入的用户名
 *       var usernameText = $("#username").val();
 *       //2 创建正则表达式对象
 *       var usernamePatt = /^\w{5,12}$/;
 *       //3 使用 test 方法验证
 *      if (!usernamePatt.test(usernameText)) {
 *           console.log("用户名不合法")
 *      }
 *  }
 *
 * 【必加】
 *  //
 *  /.../g                                    # /g为模式修饰符，表示全局匹配，并不会仅匹配一次
 *
 * 【限定符】
 *  ?，表示？前的字符可有可无                   # used?，可以匹配use和used
 *  *，表示可出现0次或无数次                    # ab*c  ，可以匹配abbc, ac
 *  +，表示匹配出现一次及以上的字符             # ab+c
 *  {}，匹配出现的次数                         # ab{2, 6}c，这里限定为2到6次， 例如ab{2, }c表示字符出现两次以上
 *
 * 【组 and 逻辑符】
 *  ()                                          # (ab)+c, 匹配出现一次及以上ab。如ababc
 *  |                                           # 或运算符， (ab|cd), 匹配ab或者cd

 * 【字符类】
 *  []，代表要求匹配的字符只能取自括号中            # [a-zA-Z0-9], 匹配所有由大小写字母和数字构成的字符，数字只能匹配0-9
 *  [^]，在字符类中的^表示非                      # [^0-9]，匹配所有的非数字字符（包括空格和换行符）
 *
 * 【✔元字符】java中前面要加转义字符\。如\\d
 *  \d                              # 表示数字字符相当于[0-9]
 *  \D                              # 非数字字符
 *  \w                              # 表示英文字符，包括所有的英文+数字+上下划线✔
 *  \W                              # 非英文字符
 *  \s                              # 表示空白字符，即空格+tab键+换行符✔
 *  \S                              # 非空白字符
 *  .                               # 表示任意字符，但不包括换行符
 *  ^                               # 匹配行首，^a，匹配行首的a
 *  $,                              # 匹配行尾，a$, 匹配行尾的a
 *  \b                              # 每个单词前后那个看不见的就是\b！！！所以\b最长用于界定字符的边界✔✔✔
 *
 * 【贪婪匹配与懒惰匹配】
 * <.+>                               # 正则默认匹配尽可能匹配多的字符（贪婪匹配）， 对于一个<span><b>test</b></span> ，贪婪匹配会全部选中
 * <.+?>                              # 切换为懒惰匹配， 对于一个<span><b>test</b></span> ，懒惰匹配会只选出标签
 *
 * 【转义】
 * \                                                                          # 对正则中原本就有意义的字符需要添加\进行转义
 *
 * 【实例】
 * \^a{3,5}&\                                 # \^ &\。要求从头到尾都要匹配！！！！
 * \b((25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}((25[0-5]|2[0-4]\d|[01]?\d\d?))\b       # 匹配ip地址的正则表达式
 * \s*([a-zA-Z]+)\s*                          # 表达式： 匹配所有字母，字母可以出现任意次，且前后可含空格。\s表示空白字符，\s*表示多个空白字符
 * [\s\S]*                                    # 表达式：匹配其内所有，\S*表示多个非空白字符
 * ^\/api/                                    # 表达式：匹配所有以api开头的路径
 * <p>.*?<\/p>                                # 匹配所有p标签（包括里面的内容）
 * (?<=<p>).*?(?=<\/p>)                       # 匹配所有的p标签里的内容，这个属于高级内容啊，需要再深入学习
 * ([a-zA-Z]+)                                # 表达式：匹配所有字母，+表示字母可以出现任意次
 * exec()                                     # 正则表达式1.exec(字符串1) ，方法用于检索字符串中的正则表达式的匹配。索引为0项为匹配的内容，没有匹配的值返回 null
 @author Alex
 @create 2023-01-27-14:55
 */
public class UD04 {
    public static void main(String[] args) {
        String s = "a";
        System.out.println(s.matches("[abc]"));
        // 匹配0-9
        String ss = "7";
        System.out.println(ss.matches("\\d"));
        // 匹配两位数
        String sss = "17";
        System.out.println(sss.matches("[1-9]{1,2}"));
        // 用Pattern做了一个正则表达式p，然后用p去匹配str
        // matches方法的匹配机制是针对整个字符串的，而find方法则不同，它属于搜索匹配。只要有一个或多个 '子字符串' 符合正则表达式，则返回true。
        Pattern p1 = Pattern.compile("[A-Z]");
        if(p1.matcher("AXC000asko").find()){
            System.out.println("OK");
        }
    }

    // 匹配ip地址（也就是说这里匹配的4个数字全为0-255）
    // 2[0-4]\d代表:第一个字符是2,第二个字符是0到4,第三个字符是任意一位数字.表示200-249.
    // 25[0-5]代表:第一个字符是2,第二个字符是5,第三个字符是0到5.表示250-255.
    // [01]?\d\d?代表:第一个字符是0,或者1,或者可以没有这个字符,第二个字符是任意一位数字,第三个字符是任意一位数字,可以没有这个字符.表示0-199,可以有前导零✔.
    @Test
    public void test1(){
        String s = "256.255.0.1";
        System.out.println(s.matches("\\b((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?))\\b"));
    }


}
