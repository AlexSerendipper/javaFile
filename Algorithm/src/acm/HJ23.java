package acm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 描述：实现删除字符串中出现次数最少的字符，若出现次数最少的字符有多个，则把出现次数最少的字符都删除。输出删除这些单词后的字符串，字符串中其它字符保持原来的顺序。
 *
 * 数据范围：保证输入的字符串中仅出现小写字母
 * 输入描述：字符串只包含小写英文字母, 不考虑非法输入，输入的字符串长度小于等于20个字节。
 *
 * 输出描述：删除字符串中出现次数最少的字符后的字符串。
 *
 @author Alex
 @create 2023-08-17-8:15
 */
public class HJ23 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.next();
        if(s.equals("")){
            System.out.println(s);
            return;
        }else {
            // 计算每个字符出现的次数
            HashMap<Character, Integer> map = new HashMap<>();
            int minCount = Integer.MAX_VALUE;
            for (int i = 0; i < s.length(); i++) {
                int count = map.get(s.charAt(i))==null?0:map.get(s.charAt(i));
                count++;
                if(count<minCount){
                    minCount = count;
                }
                map.put(s.charAt(i),count);
            }




            // 遍历输出String中所有出现次数并非为最小的值
            String ss="";
            for (int i = 0; i < s.length(); i++) {
                if(map.get(s.charAt(i))!=minCount){
                    ss += s.charAt(i);
                }
            }

            // 如果ss和s一致，说明是因为出现了最小字符为双的情况，如assssa：遍历到最后一个a时，count=2,minCount=1

            if(ss.equals(s)){
                ss="";
                minCount++;
                for (int i = 0; i < s.length(); i++) {
                    if(map.get(s.charAt(i))!=minCount){
                        ss += s.charAt(i);
                    }
                }
            }
            System.out.println(ss);
        }
    }
}
