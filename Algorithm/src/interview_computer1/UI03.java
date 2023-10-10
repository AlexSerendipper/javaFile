package interview_computer1;

import java.util.Scanner;

/**
 * 小明是一家店铺的专职外卖员。小明每天会接到很多不同地方的n个外卖订单，其中第i个订单会在s;时刻下单，
 * 花费小明t;时间往返，并赚取a;元酬劳。小明每次只能送1单外卖，订单一旦错过就会被派给其他外卖员。
 * 如果小明提前知道了今天的全部订单，请你帮小明选择最优的接单方式，使得今天赚取的酬劳最多
 *
 * 输入描述
 * 对于每一组数据，包含4行数据
 * 第一行是外卖订单数:n。
 * 第二行有n个数字Si;(=1,2,3..,n)表示第的订单的下单时刻为Si
 * 第三行有n个数字ti;(i=1,2,3,.,n)表示第的订单的往返时间为ti;
 * 第四行有n个数字ai;(i=12,3,..,n)表示第的订单的酬劳为ai
 *
 * 样例输入:
 * 5
 * 1 3 6 7 11
 * 4 3 4 3 9
 * 2 5 5 3 4
 *
 * 样例输出:
 * 14
 *
 * 提示
 * 小明选择接2、3、5单可以赶在下一单到来前结束当前订单，并可以赚取14元。
 * 提示:如果小明在t时刻返回，她可以接包括t时刻以及之后到来的订单，但不能接t-1时刻以及之前的订单。
 *
 @author Alex
 @create 2023-09-06-15:34
 */


public class UI03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int [] S = new int[n];
        int [] T = new int[n];
        int [] A = new int[n];
        for (int i = 0; i < n; i++) {
            S[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            T[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            A[i] = sc.nextInt();
        }
        int maxValue = 0;
        int res = 0;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            maxValue = 0;
            int [] M = new int[n];
            for (int j = i; j < n; j++) {
                if(j==i){  // 默认送了当前的这一单，即i这一单
                    maxValue = A[j];
                    M[j] = 1;
                    continue;
                }

                // 判断上一个订单是哪一个订单
                for (int k = j; k >=0 ; k--) {
                    if(M[k]!=0){
                        temp = k;
                        break;
                    }
                }

                // ，然后判断是否已经送完
                if(S[temp]+T[temp]<=S[j]){
                    maxValue += A[j];
                    M[j] = 1;
                }else {
                    continue;
                }
            }

            // 结束一次遍历
            if(maxValue>res){
                res = maxValue;
            }
        }

        System.out.println(res);

    }
}
