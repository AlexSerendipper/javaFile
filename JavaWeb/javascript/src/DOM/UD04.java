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
 *
 * 【必加】
 *  //
 *  /.../g                                    # /g为模式修饰符，表示全局匹配，并不会仅匹配一次
 *
 *
 * 【限定符】限定数量
 *  ?，表示？前的字符可有可无(0次或1次)         # used?，可以匹配use和used
 *  *，表示可出现0次或多次                     # ab*c  ，可以匹配abbc, ac
 *  +，表示匹配出现1次或多次                   # ab+c
 *  {}，匹配{}前的字符出现的次数               # ab{2, 6}c，这里限定为2到6次， 例如ab{2, }c表示字符出现两次以上
 *
 *
 * 【贪婪匹配与懒惰匹配】
 *                                   # 正则默认匹配尽可能匹配多的字符（贪婪匹配）， 如ab\d+?在用于匹配ab123时，结果为ab123
 * *?,+?,??,{}?                      # 限定符后加?，表示切换为懒惰匹配，懒惰匹配总是尽可能匹配少的内容。如ab\d+?在用于匹配ab123时，结果为ab1
 *
 *
 * 【组 and 逻辑符】
 *  ()                                          # (ab)+c, 匹配出现一次及以上ab。如ababc。
 *                                                 方式一：number\，1\可以表示引用第一组的内容 (从左到右第一组
 *                                                 方式二：还可以使用(?P<name1>)的方式为该组命名，从而替代 1\这种方式。。。(?P=name1)的方式调用
 *                                                         如 (?P<name1>ab|cd).+(?P=name1) ，即表示匹配的内容需要以ab或者cd来开头或结尾
 *                                                         这种取名的用法，常用于前后标签要匹配一致的情况
 *  |                                           # 分组常结合这个或运算符， 如(ab|cd), 即匹配ab或者cd
 *
 *
 * 【字符类】
 *  []，标识一组匹配字符，匹配的字符只能取自其中   # [a-zA-Z0-9], 匹配所有由大小写字母和数字构成的字符，数字只能匹配0-9
 *  [^]，在字符类中的^表示非，即取补集            # [^0-9]，匹配所有的非数字字符（包括空格和换行符）
 *
 *
 * 【✔元字符】匹配规则
 *  \d                              # 表示数字字符相当于[0-9]
 *  \D                              # 非数字字符
 *  \w                              # 表示任意字母和下划线，包括所有的英文+数字+上下划线，相当于[a-zA-Z0-9_]✔
 *  \W                              # 非英文字符
 *  \s                              # 表示空白字符，相当于[\t\n\r\f]，空格+tab键+换行符✔
 *  \S                              # 非空白字符
 *  .                               # 表示任意字符，但不包括换行符
 *  ^                               # 匹配行首，^a，匹配行首的a✔
 *  $                               # 匹配行尾，a$, 匹配行尾的a（如果行尾有换行符，则匹配\n前的那个字符✔
 *  \b                              # 匹配单词边界。每个单词前后那个看不见的就是\b！！！所以\b最长用于界定单词的边界✔✔✔
 *                                     例如，‌表达式\bhere\b可以匹配字符串中的单词"here"，‌但如果"here"不是作为一个完整的单词出现，‌而是其他单词的一部分，
 *                                     ‌如"adheread"中的"here"，‌则\bhere\b无法匹配，‌因为"adhere"中的"here"后面没有单词边界。
 *                                     ‌因此，‌\b确保你匹配的是整个单词，‌而不是单词的一部分。‌
 *                                     当然，你也可以使用here\b，这样adhere也能匹配到
 *  \B                              # 匹配非单词边界，即与\b相反
 *  \A                              # 表示从字符串的开始处匹配
 *  \Z                              # 表示从字符串的结束处匹配，如果存在换行，只匹配到换行前的结束字符串。
 *
 * 【转义】
 *  \\                              # 对正则中原本就有意义的字符需要添加\进行转义，如\n，原本在字符串中就是换行的含义，所以需要\\n
 *
 * 【实例】
 * ^[1-9][0-9]{8,}&                            # 匹配qq号，要求以1-9的数字开头，0-8的数字结尾(8个以上)
 *                                               实际上^ &一起使用，相当于必须输入整体必须满足该正则。。否则只要其中一部分满足就会匹配
 * \b((25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}((25[0-5]|2[0-4]\d|[01]?\d\d?))\b       # 匹配ip地址的正则表达式，0~255,前三位有. 最后一位没有.
 * s*([a-zA-Z]+)\s*                           # 表达式： 匹配所有字母，字母可以出现任意次，且前后可含空格。\s表示空白字符，\s*表示多个空白字符
 * [\s\S]*                                    # 表达式：匹配其内所有，\S*表示多个非空白字符
 * ^\/api/                                    # 表达式：匹配所有以api开头的路径
 *
 * <p>.*?<\/p>                                # 匹配所有p标签（包括里面的内容）
 * (?<=<p>).*?(?=<\/p>)                       # 匹配所有的p标签里的内容，这个属于高级内容啊，需要再深入学习
 * <([\w]+)>(.+)<([\w]+)>                     # 匹配h5中的标签，如<h1>xxx<h1>，匹配后使用.group(1)，即可取出标签中的 xxx
 *                                              问题是对于<html><h1>xxx<h1>这种非成对标签也能匹配，取出的内容即为 <h1>xxx
 *                                              因此作出如下改进，<([\w]+)>(.+)<\1>，
 *                                              其中 \1 表示引用第一组的内容，即要求了和第一组内容一致才可匹配
 * ([a-zA-Z]+)                                # 表达式：匹配所有字母，+表示字母可以出现任意次
 * exec()                                     # 正则表达式1.exec(字符串1) ，方法用于检索字符串中的正则表达式的匹配。索引为0项为匹配的内容，没有匹配的值返回 null
 *
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
