package interview_computer2;

import java.util.HashMap;
import java.util.Scanner;

/**
 * 9.9科大讯飞笔试第一题
 * 给定一个长度为n的数组arr，其中一个数字出现了b次，其他数字要么出现a次，要么不出现，现在请你找到这个出现b次的数字
 *
 * 输入描述：
 * 第一行输入三个整数： n,a,b
 * 第二行给出n个整数 以空格间隔：
 *
 * 输出描述：
 * 输出一个正整数表示答案
 @author Alex
 @create 2023-09-05-14:22
 */
public class UI08 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int a = sc.nextInt();
        int b = sc.nextInt();
        int[] nums = new int[n];
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int num = sc.nextInt();
            nums[i] = num;
            map.put(num,map.getOrDefault(num,0)+1);
        }

        // 输出出现了b次是数字
        for (Integer key : map.keySet()) {
            if(map.get(key)==b){
                System.out.println(key);
            }
        }
    }
}



