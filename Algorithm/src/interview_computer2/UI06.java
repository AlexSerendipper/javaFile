package interview_computer2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 23/9/2美团测试开发工程师第二题
 *
 * 小美在玩一个好评如潮的卡牌游戏，游戏里有很多的卡牌，每张卡牌都有一个点数。
 * 小美正在玩一个武将，这个武将的技能是:若当前使用牌的点数严格小于上一张牌，则摸一张牌 (使用第一张牌时无法摸牌)
 * 小美只记得她摸了n张牌，且每次摸牌时候的点数为ai;
 * 但是小美不记得她的出牌顺序是什么了，只记得出牌数量不超过 2n，她想知道，她可能的出牌序列是什么，如果有多种答案，输出任意种即可。
 * 例如，摸牌时候的点数是 a =[2,1,5,4]，那么小美的出牌顺序可以为[3,2,1,6,5,4]。
 * 若小美第一次出牌是3，第二次是2，则小美可以摸牌，同时记录下2.第三次出牌是1，小美可以摸牌，同时记录下1。第四次是6，不可以摸牌。第五次是5，小美可以摸牌，同时记录下5。第六次是4.小美可以摸牌，同时记录下4。
 * 符合小美的摸牌时候的点数a =[2,1,5,4]
 * 此外。小美可以的出牌顺序也可以为[3,2,1,11,12,5,4]，在这种出牌情况下，小美的摸牌的点数同样为[2,1,5,4]
 @author Alex
 @create 2023-09-02-19:46
 */
public class UI06 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }

        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(nums[0]+1);
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]<arr.get(j++)){
                arr.add(nums[i]);
            }else {
                // 如果不是递减~
                arr.add(nums[i]+1);
                i--;
            }
        }
        System.out.println(arr.size());
        for(int i:arr){
            System.out.printf(i+" ");
        }
    }
}
