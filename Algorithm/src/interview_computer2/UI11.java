package interview_computer2;

import java.util.Scanner;

/**
 * 华为23/9/13笔试第一题
 * 快递公司有一个业务要求，所有当天下发到快递中转站的快递，最迟在第二天送达用户手中。假设已经知道接下来n天每天下发到快递中转站的快递重量。
 * 快递中转站负责人需要使用快递运输车运输给用户，每一辆运输车最大只能装k重量的快递
 * 每天可以出车多次，也可以不出车，也不要求运输车装满。当天下发到快递中转站的快递，最晚留到第二天就要运输走送给用户。快递中转站负责人希望出车次数最少，完成接下来n天的快递运输
 *
 * 示例1：
 * 输入:3 2
 *      3 2 1
 * 输出: 3
 * 解释:第一天的快递出车一次送走2个重量，留1个重量到第二天。第二天送走第一天留下的1个重量和当前的1个重量，留1个重量到第三天送走。
 *
 * 样例2：
 * 输入:3 2
 *      1 0 1
 * 输出:2
 * 解释:第一天或者第二天出车一次送走1个重量，第三天出车一次送走1个重量
 @author Alex
 @create 2023-09-13-19:06
 */
public class UI11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();  // k不一定等于2，注意
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        // 运送次数
        int count = 0;
        // 用来判定是否有昨天剩的
        boolean flag = true;
        // 剩下了多少
        int remain = 0;
        for (int i = 0; i < n; i++) {

            int num = flag==true?nums[i]:nums[i]+remain;
            int initNum = num;

            while(num>0){
                // 此时昨天剩的那一个必须要送出去了
                if(!flag&&initNum<k){
                    count++;
                    flag = true;
                    num = 999;
                    break;
                }
                // 如果是最后一天，也得送出去
                if(i==n-1&&num<k){
                    count++;
                    flag = true;
                    num = 999;
                    break;
                }
                num = num - k;
                count++;
            }
            // 刚好全部送出去了
            if(num==0){
                flag = true;
            }

            // 说明当天有剩
            if(num<0){
                remain = num+k;
                count--;
                flag = false;
            }
        }
        System.out.println(count);

    }
}

