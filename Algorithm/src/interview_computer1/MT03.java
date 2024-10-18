package interview_computer1;

import java.util.Scanner;

/**
 * 美团2024.3.9 测试方向第二题
 *
 * 小美拿到了一个由正整数组成的数组，但其中有一些元素是未知的(用0来表示)。现在小美想知道，
 * 如果那些未知的元素在区间范围内随机取值的话，数组所有元素之和的最小值和最大值分别是多少?共有q次询问。
 *
 * 输入描述
 * 第一行输入两个正整数n,q，代表数组大小和询问次数。
 * 第二行输入n个整数ai,其中如果输入的ai为 0，那么说明ai是未知的。
 * 接下来的q行，每行输入两个正整数l,r，代表一次询问。
 *
 * 输出描述
 * 输出9行，每行输出两个正整数，代表所有元素之和的最小值和最大值,
 *
 * 示例输入
 * 3 2
 * 1 0 3
 * 1 2
 * 4 4
 * 输出
 * 5 6
 * 8 8
 * 解释
 * 就是只有0是未知数，未知数都取范围内最小值就对应元素之和最小呗
 * 未知数都取最大值就对应元素之和为最大值
 @author Alex
 @create 2024-03-09-11:41
 */
public class MT03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        long[] arr = new long[n];
        long sum = 0; // 已知元素的和
        int unknownCount=0; // 未知元素数量

        // 遍历求已知元素的和以及未知元素的数量
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextLong();
            if(arr[i]==0) {
                unknownCount++;
            }else {
                sum += arr[i];
            }
        }

        // 询问
        for (int i = 0; i < q; i++) {
            long l = sc.nextLong();  // 实际上这个不就是最小值
            long r = sc.nextLong();  // 实际上这个不就是最大值

            long minSum = sum;
            long maxSum = sum;

            minSum += unknownCount*l;
            maxSum += unknownCount*r;
            System.out.println(minSum + " "+maxSum);
        }
        sc.close();
    }
}
