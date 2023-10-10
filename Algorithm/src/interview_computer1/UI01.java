package interview_computer1;

import java.util.Scanner;

/**
 * 京东9.2 lyj笔试题第一题
 * 讨厌鬼的店铺要进n个货物，以及两个供货商a和b
 * 第i件货物在a供货商处需要ai元，在供货商处需要bi元。
 *
 * 讨厌鬼还有第三个选择就是在京东上网购，网购只能一次买齐n种货物，网购这n个货物的总价格为x元
 *
 * 通过三种方式购买的 n种货物都是一样的，可以在不同的供货商a和b处购买商品，
 * 我们的目标是需要买齐这n种货物。讨厌鬼想知道，进这n个货物最少需要花多少元
 *
 * 输入
 * 5 5
 * 2 1 2 1 2
 * 1 2 1 2 3
 *
 * 输出: 5
 @author Alex
 @create 2023-09-02-10:33
 */

public class UI01 {
    // 纵向比较即可，最后和网购价格比较
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int x = sc.nextInt();

        int[][] prices = new int[2][5];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                prices[i][j] = sc.nextInt();
            }
        }

        int total = 0;
        // 遍历每一列
        for (int j = 0; j < n; j++) {
            int temp = Math.min(prices[0][j], prices[1][j]);
            total += temp;
        }

        int res = total>x?x:total;
        System.out.println(res);
    }
}
