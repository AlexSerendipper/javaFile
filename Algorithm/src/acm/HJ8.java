package acm;

import java.util.Scanner;
import java.util.TreeMap;

/**
 * 合并表记录：https://www.nowcoder.com/practice/de044e89123f4a7482bd2b214a685201?tpId=37&tqId=21231&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 数据表记录包含表索引index和数值value（int范围的正整数），请对表索引相同的记录进行合并，即将相同索引的数值进行求和运算，输出按照index值升序进行输出。
 *
 * 输入描述：
 * 先输入键值对的个数n（1 <= n <= 500）
 * 接下来n行每行输入成对的index和value值，以空格隔开
 *
 * 输出描述：
 * 输出合并后的键值对（多行）
 *
 * 输入：
 * 4
 * 0 1
 * 0 2
 * 1 2
 * 3 4
 *
 * 输出：
 * 0 3
 * 1 2
 * 3 4
 @author Alex
 @create 2023-07-01-10:10
 */
public class HJ8 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int times = Integer.parseInt(scanner.nextLine());
        TreeMap<Integer, Integer> treemap = new TreeMap<>();
        String  keyValue;
        int key;
        int value;
        for (int i = 0; i < times; i++) {
            keyValue = scanner.nextLine();
            String[] s = keyValue.split(" ");
            key = Integer.parseInt(s[0]);
            value = Integer.parseInt(s[1]);
            if(treemap.containsKey(key)){
                treemap.put(key,treemap.get(key) + value);
            }else {
                treemap.put(key,value);
            }
        }


        for (Object o : treemap.entrySet()) {
            String s = o.toString();
            System.out.println(s.replace('=',' '));
        }

    }
}
