package interview;

import java.util.Scanner;

/**
 * 网易雷火游戏：测试开发工程师
 * 有一组性能采样数据，采样了一段时间内服务器某进程的CPU占用率，请找出这组数据中CPU占用率持续增长的最长时间。
 *
 * 输入描述：输入一个数组，数组中每个元素代表该时刻CPU占用率
 * 输出描述：输出CPU占用率持续增长的最长时间长度
 * 示例1：输入[0.1,0.2,0.3]
 * 输出：0.1，0.2，0.3的过程持续增长，增长过程长度为3
 *
 @author Alex
 @create 2023-08-20-14:16
 */
public class UI02 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String s1 = s.replace("[", "").replace("]","");

        String[] strs = s1.split(",");
        Double[] arrs = new Double[strs.length];

        for (int i = 0; i < strs.length; i++) {
            arrs[i] = Double.parseDouble(strs[i]);
        }

        // 查看递增的数量
        int count = 1;
        int maxCount=0;
        for (int i = 0; i < arrs.length-1; i++) {
            if(arrs[i+1]>arrs[i]){
                count++;
                maxCount = count;
            }else {
                if(count>maxCount){
                    maxCount = count;
                }
                count = 1;
            }
        }

        System.out.println(maxCount);
    }
}
