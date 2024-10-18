package interview_computer1;

import java.util.HashMap;
import java.util.Scanner;

/**
 * 滴滴3.18
 * 小明正在模拟陨石对地质的危害。在小明的模型下，将地面从0,1,2.,直到N依次从左到右进行标号。
 * 每次陨石i坠落，会使得标号在[Li,Ri]这个区间范围内的地面受到一次陨石打击(即Li,...,Li+1,...,Ri)共Ri-Li+1个位置都会受到打击。
 * 在 M 次陨石坠落后，小明想知道某些指定地面在刚才M次陨石坠落中受到了多少次陨石打击。
 *
 * 输入描述
 * 第一行两个正整数 N,M，含义如题面
 * 接下来一行 M 个数，分别为L1,L2,...,LM表示这 M 次陨石打击的左边界。
 * 接下来一行 M 个数，分别为R1,R2,,...RM表示这 M 次陨石打击的右边界
 * 接下来一个数 Q，表示小明询问次数。
 * 接下来一行 Q 个数 x，表示小明想知道标号为 x的地面在刚才 M 次陨石坠落中受到了多少次打击。
 *
 * 样例输入
 * 4 3
 * 1 2 2
 * 2 3 4
 * 5
 * 0 1 2 3 4
 * 样例输出
 * 0 1 3 2 1
 @author Alex
 @create 2024-03-17-17:28
 */

// ac=82%，有点超时
public class DD01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] left = new int[M];  // 左边界
        int[] right = new int[M];

        for (int i = 0; i < M; i++) {
            left[i] = sc.nextInt();
        }

        for (int i = 0; i < M; i++) {
            right[i] = sc.nextInt();
        }


        int Q = sc.nextInt();
        int[] query = new int[Q];  // 询问次数
        for (int i = 0; i < Q; i++) {
            query[i] = sc.nextInt();
        }

        // 键为地面编号，值为次数
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= N; i++) {
            map.put(i,0);
        }

        for (int i = 0; i < M; i++) {
            for (int j = left[i]; j <= right[i]; j++) {
                map.put(j,map.get(j)+1);
            }
        }

        for (int i = 0; i < query.length; i++) {
            System.out.print(map.get(query[i])+" ");
        }

    }
}
