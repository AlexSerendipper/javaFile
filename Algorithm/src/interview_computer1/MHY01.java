package interview_computer1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 米哈游：2024.3.10
 * 地图上有n个格子排成一排，最左边的格子为1，最右边的格子为n。
 * 第0秒时，每个格子都有一只史莱姆。
 * 第i只史莱姆的跳跃方向用数组a表示。
 * ai=0表示史莱姆跳跃的方向是往左。若第 i 秒史莱姆位于格子z，那么第 i+1 秒史莱姆会跳到格子 x-1 。如果当前史莱姆在格子1 ，则下一秒史莱姆将跳出地图。
 * ai=1表示史莱姆跳跃的方向是往右。若第 i 秒史莱姆位于格子 x，那么第 i+1 秒史莱姆会跳到格子 x+1 。如果当前史莱姆在格子n ，则下一秒史莱姆将跳出地图。
 *
 * 米小游想知道第1-n秒，地图上有多少个格子没有史莱姆。
 *
 * 输入描述
3
1 0 1
 * 输出描述
 * 1 2 3
 * 即第1 2 3秒，分别有1 2 3个格子上没有史莱姆
 @author Alex
 @create 2024-03-09-13:17
 */
public class MHY01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        ArrayList<Integer> arr = new ArrayList<>();  // 记录跳跃方向
        for (int i = 0; i < n; i++) {
            int temp = sc.nextInt();
            int direction = temp==0?-1:1;
            arr.add(direction);
        }


        // 计算每一只要几s会跳出去，这样只能判断每秒钟有几只跳出去，没有用
//        ArrayList<Integer> times = new ArrayList<>();
//        for (int i = 0; i < arr.size(); i++) {
//            int dir = arr.get(i);
//            if(dir==1){  // 往右边跳
//                times.add(n-i);
//            }else {  // 往左边跳
//                times.add(i+1);
//            }
//        }


        // 遍历判断是否跳出去！！！还得判断格子上是否有东西！！！！
        for (int i = 1; i <= n; i++) {
            int count = 0;
            // 计算当前格子上史莱姆的数量，每个格子数量默认为0
            ArrayList<Integer> nums = new ArrayList<>();
            for (int m = 0; m < n; m++) {
                nums.add(0);
            }
            for (int j = 0; j < n; j++) {
                int idx = j + arr.get(j) * i;  // 记录移动到哪个位置上，移动到哪个位置哪个位置的数量就得加
                if(idx>=0 && idx<n){
                    nums.set(idx,nums.get(idx)+1);
                }
            }
            // 计算nums中为0的地方即可
            for (int k = 0; k < n; k++) {
                if(nums.get(k)==0){
                    count++;
                }
            }
            System.out.print(count+" ");
        }
    }
}
