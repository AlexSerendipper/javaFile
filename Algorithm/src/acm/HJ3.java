package acm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 明明的随机数：https://www.nowcoder.com/practice/3245215fffb84b7b81285493eae92ff0?tpId=37&tqId=21226&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3Fpage%3D2%26tpId%3D37%26type%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * 描述：明明生成了N个1到500之间的随机整数。请你删去其中重复的数字，即相同的数字只保留一个，把其余相同的数去掉，
 *      然后再把这些数从小到大排序，按照排好的顺序输出。
 * 输入描述：第一行先输入随机整数的个数N。接下来的N行每行输入一个整数，代表明明生成的随机数。具体格式可以参考下面的"示例"。
 * 输出描述：✔输出多行✔，表示输入数据处理后的结果
 *
 @author Alex
 @create 2023-06-25-10:08
 */
public class HJ3 {
    // 使用treeSet更快，直接完成排序和去重
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            int a = in.nextInt();
//            int b = in.nextInt();
//            System.out.println(a + b);
//        }

        int N = in.nextInt();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < N; i++) {
            int temp = in.nextInt();
            set.add(temp);
        }

        Integer[] arr = set.toArray(new Integer[1]);
        Arrays.sort(arr);

        for(int i:arr){
            System.out.println(i);
        }

    }
}
