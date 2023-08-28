package acm;

import java.util.ArrayList;
import java.util.Scanner;

/**
 @author Alex
 @create 2023-08-26-9:20
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String nm = in.nextLine();
        String[] strs = nm.split(" ");
        int n = Integer.parseInt(strs[0]);
        int m = Integer.parseInt(strs[1]);

        // 使用一个二维数组来装所有人对菜的喜欢值
        int[][] ints = new int[n][m];
        for (int i = 0; i < n; i++) {
            String s = in.nextLine();
            String[] strings = s.split(" ");
            for (int j = 0; j < strings.length; j++) {
                ints[i][j] = Integer.parseInt(strings[j]);
            }
        }
        // 数组复制
        int[][] ints1 = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ints1[i][j] = ints[i][j];
            }
        }

        // 小兴点菜
        int[] xiaoxing = ints[0];
        int idx1=0;
        int idx2=0;
        int idx3=0;
        ArrayList<Integer> idx = new ArrayList<>();
        for (int i = 0; i < xiaoxing.length; i++) {
            if(xiaoxing[i]>idx1){
                idx1 = i;
            }
        }
        xiaoxing[idx1] = 0;
        idx.add(idx1);
        for (int i = 0; i < xiaoxing.length; i++) {
            if(xiaoxing[i]>idx2){
                idx2 = i;
            }
        }
        xiaoxing[idx2] = 0;
        idx.add(idx2);
        for (int i = 0; i < xiaoxing.length; i++) {
            if(xiaoxing[i]>idx3){
                idx3 = i;
            }
        }
        idx.add(idx3);

        // 其他人点菜(先根据idx数组划掉菜)
        for (int i = 1; i < n; i++) {
            int[] others = ints[i];
            for (int j = 0; j < idx.size(); j++) {
                others[idx.get(j)]=0;
            }

            int min = Integer.MIN_VALUE;
            for (int j = 0; j < others.length; j++) {
                if(others[j]>min){
                    min = j;
                }
            }
            idx.add(min);
        }


        // 根据idx 计算所有菜品分数
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < idx.size(); j++) {
//                System.out.println(idx.get(j));
                res += ints1[i][idx.get(j)];
            }
        }

        System.out.println(res);
    }
}

