package interview_computer2;

import java.util.Scanner;

/**
 * 23/9/12 百度测开笔试第二题
 * 小明所在的团队在接下来n天已经有 m个任务需要执行，第i个任务从第li天开始执行，直到第ri天结束时完成。
 * 该团队希望再接受第 m+1 个任务, 该任务需要连续的 k 天时间来完成，任务的开始时间由团队自行决定。
 * 为了保证所有任务的完成质量，团队任意一天同时执行的任务数量不能超过a个
 * 小明希望你帮忙计算，如果接受第 m+1 个任务，则从第1天到第n天，有多少天可以作为第m+1个任务的开始时间。使得该任务可以在第 n 天结束前完成。
 *
 * 输入描述:
 * 输入第一行包含四个正整数n，m，k，a，分别表示总天数、已接受的任务数量、执行第m+1个任务所需的天数、团队最多能同时执行的任务数量。
 * 输入第二行包含m个整数，第i个整数表示第i个任务开始的时间li。
 * 输入第三行包含m个整数，第i个整数表示第i个任务完成的时间ri。
 *
 * 输出描述:
 * 输出包含一行，一个整数，表示有多少天可以作为第m+1个任务的开始时间
 *
 * 输入：
 * 10 3 3 2
 * 1 5 4
 * 4 10 5
 *
 * 输出：
 * 4
 *
 * 解释：
 * 可以在1 6 7 8天进行任务
 *
 @author Alex
 @create 2023-09-12-19:46
 */
public class UI10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalDay = sc.nextInt();
        int taskCount = sc.nextInt();
        int newTaskDay = sc.nextInt();
        int k = sc.nextInt();

        int[] tasks = new int[taskCount];
        int[] days = new int[taskCount];
        int[][] schedules = new int[taskCount+1][totalDay];


        for (int i = 0; i < taskCount; i++) {
            tasks[i] = sc.nextInt();
        }

        for (int i = 0; i < taskCount; i++) {
            days[i] = sc.nextInt();
        }


        for (int i = 0; i < taskCount; i++) {
            for (int j = tasks[i]-1; j < days[i]; j++) {
                schedules[i][j] = 1;
            }
        }

        int count = 0;
        boolean flag = true;
        for (int i = 0; i < totalDay; i++) {
            //
            if(i+newTaskDay>totalDay){
                break;
            }
            for (int j = i; j < i+newTaskDay; j++) {
                // 按列求和
                int temp = 0;
                for (int l = 0; l < taskCount; l++) {
                    temp = temp + schedules[l][j];
                }

                if(temp>=k){
                    flag = false;
                }
            }
            if(flag==true){
                count++;
            }
            flag = true;
        }

        System.out.println(count);
    }
}
