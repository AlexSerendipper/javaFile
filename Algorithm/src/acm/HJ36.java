package acm;

import java.util.HashSet;
import java.util.Scanner;

/**
 * HJ36 字符串加密：https://www.nowcoder.com/practice/e4af1fe682b54459b2a211df91a91cf3?tpId=37&tqId=21259&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 描述
 * 有一种技巧可以对数据进行加密，它使用一个单词作为它的密匙。下面是它的工作原理：首先，选择一个单词作为密匙，如TRAILBLAZERS。如果单词中包含有重复的字母，只保留第1个，将所得结果作为新字母表开头，并将新建立的字母表中未出现的字母按照正常字母表顺序加入新字母表。如下所示：
 * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
 * T R A I L B Z E S C D F G H J K M N O P Q U V W X Y (实际需建立小写字母的字母表，此字母表仅为方便演示）
 * 上面其他用字母表中剩余的字母填充完整。在对信息进行加密时，信息中的每个字母被固定于顶上那行，并用下面那行的对应字母一一取代原文的字母
 * (字母字符的大小写状态应该保留)。因此，使用这个密匙， Attack AT DAWN (黎明时攻击)就会被加密为Tpptad TP ITVH。
 * 请实现下述接口，通过指定的密匙和明文得到密文。
 *
 * 输入描述：
 * 先输入key和要加密的字符串
 *
 * 输出描述：
 * 返回加密后的字符串
 @author Alex
 @create 2023-08-21-11:03
 */
// 注意，本题后台示例均为小写，无需涉及大小写转换
public class HJ36 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String key = in.nextLine().toLowerCase();  // 小写字母的表
        String strs = in.nextLine();

        // 建立字母表
        int k = 0;
        HashSet<Character> set1 = new HashSet<>();
        StringBuilder sb1 = new StringBuilder();
        while(set1.size()<26){
            char c = (char) ('a' + k);
            set1.add(c);
            sb1.append(c);
            k++;
        }

        // 根据key建立密码表
        HashSet<Character> set2 = new HashSet<>();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            if(!set2.contains(key.charAt(i))){
                set2.add(key.charAt(i));
                sb2.append(key.charAt(i));
            }
        }

        while(sb2.length()<26){
            for(char c:set1){
                if(!set2.contains(c)){
                    set2.add(c);
                    sb2.append(c);
                }
            };
        }

        // 根据字符表和密码表，对字符串进行加密
        StringBuilder sb3 = new StringBuilder();
        for (int i = 0; i < strs.length(); i++) {
            int idx = sb1.indexOf(strs.charAt(i) + "");
            sb3.append(sb2.toString().charAt(idx));
        }
        System.out.println(sb3.toString());
    }
}
