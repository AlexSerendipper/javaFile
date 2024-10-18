package interview_computer1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * 小红是游戏《H炉》的一个主播。她经常去小红书发布关于H炉的卡牌研究攻略。H炉有一张牌叫做碾压墙，可以消灭敌方最左边和最右边的随从，
 * 另一张牌叫做致命射击，可以随机消灭一个敌方随从。如果小红使用两张致命射击恰好消灭了敌方最左边和最右边的随从(恰好造成了一张碾压墙的效果)，
 * 就会有人在评论区发布“碾压墙”。注意:两张致命射击的结算有先后顺序，即两张致命射击不会消灭同一个敌方随从。
 * 现在有 n个敌方随从，小红想知道她使用两张致命射击后，恰好造成一张碾压墙的效果的概率是多少。你的答案请四舍五入保留10位小数。
 *
 * 输入描述
 * 第一行输入一个整数n
 * 输出描述
 * 输出一个实数表示答案(四舍五入保留10位小数)。
 *
 * 示例输入
 * 3
 * 输出
 * 0.3333333333
 *
 @create 2024-03-17-19:39
 */

// 照理来说下面就是正确答案，但是ac55%，不知道为何
// 有人说直接用Double数据类型就行，不用这个四舍五入，就是可能公式是对的，但是用这个bigDecimal错了
public class XHS01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double n = sc.nextDouble();
//      BigDecimal bd = new BigDecimal(1/(n * (n - 1) / 2));
        System.out.println(2/(n*(n-1)));
        BigDecimal bd = new BigDecimal(2/(n*(n-1)));
        bd = bd.setScale(10, RoundingMode.HALF_DOWN);
        System.out.println(bd);
    }
}
