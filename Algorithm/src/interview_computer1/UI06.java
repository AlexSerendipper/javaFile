package interview_computer1;

import java.util.Scanner;

/**
 * 9.9美团测试方向第一题
 * 小美记录了n 次考试的成绩，小美希望自己的平均分达到 90分，请问最少还需要几次考试才可能做到，满分为 100 分。
 @author Alex
 @create 2023-09-09-10:23
 */
public class UI06 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 考试科目数
        int sum = 0;  // 小美目前分数和
        for (int i = 0; i < n; i++) {
            sum = sum + sc.nextInt();
        }

        int count = n;
        while(sum/count<90){
            sum = sum + 100;
            count++;
        }

        System.out.println(count - n);
    }
}

