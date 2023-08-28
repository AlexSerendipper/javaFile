package acm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * 描述
 * 编写一个程序，将输入字符串中的字符按如下规则排序。
 *
 * 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。
 * 如，输入： Type 输出： epTy
 *
 * 规则 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。
 * 如，输入： BabA 输出： aABb
 *
 * 规则 3 ：非英文字母的其它字符保持原来的位置。
 * 如，输入： By?e 输出： Be?y
 *
 * 输入描述：输入字符串
 * 输出描述：输出字符串
 @author Alex
 @create 2023-08-17-9:15
 */
public class HJ26 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        //  先将英文字母收集起来
        List<Character> letters = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) {
                letters.add(ch);
            }
        }

        //  将英文字母先排序好（这里没有二级排序，即如果碰到aA这种，也是按照默认的先后顺序进行排序）
        letters.sort(new Comparator<Character>() {
            public int compare(Character o1, Character o2) {
                return Character.toLowerCase(o1) - Character.toLowerCase(o2);
            }
        });

        // 若是非英文字母则直接添加
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 0; i < str.length(); i++) {
            // 这里比较巧妙，当str中位置为字符时，从letters中取一个添加到sb当中，当str中位置为非字符时，直接添加即可
            if (Character.isLetter(str.charAt(i))) {
                result.append(letters.get(j++));
            } else {
                result.append(str.charAt(i));
            }
        }

        System.out.println(result.toString());
    }
}
