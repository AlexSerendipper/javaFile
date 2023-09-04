package interview_computer2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 23/9/2美团测试开发工程师第三题（没做出来）
 *
 * 小美有很多的彩虹糖，每颗彩虹糖都有一个颜色，她每天可以吃两颗彩虹糖，如果今天吃的彩虹糖组合是之前没吃过的组合，则小美今天会很高兴。
 * 例如，小美有 6 颗彩虹糖，颜色分别是1,1,4,5,1,4
 * 小红第一天吃一组颜色为 1和 4 的彩虹糖，小美会很高兴
 * 第二天吃一组颜色为 4 和 1的彩虹糖，小美不会很高兴
 * 第三天小美吃一组颜色为 1 和 5 的彩虹糖，小美会很高兴，此时小
 * 美共有 2 天很高兴。小美想知道，她最多有几天会很高兴。
 *
 *
 * 本质上这题的意思是：一个数组N，每次从里面取两个数字出来，直到把数组取为空为止，有多少个不一样的组合？
 * 数组中会有重复的数字（（1，4）和（4，1）算同一种），每次取出数字之后数组中就会消失这几个数字。
 *
 *
 @author Alex
 @create 2023-09-02-20:03
 */


public class UI07 {
    static class Pair{
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair other = (Pair) o;

            return (this.x==other.x&&this.y==other.y)||(this.x==other.y&&this.y==other.x);
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> N = new ArrayList<>();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            N.add(sc.nextInt());
        }


        int count = 0;
        HashSet<Pair> set = new HashSet<>();
        while(!N.isEmpty()){


            for (int i=0; i < N.size(); i++) {
                for (int j = i+1; j < N.size(); j++) {
                    int x = N.get(i);
                    int y = N.get(j);
                    Pair pair = new Pair(x, y);
                    if(!set.contains(pair)){
                        set.add(pair);
                        count++;
                    }
                    N.remove(i);
                    N.remove(j-1);
                    break;
                }
                break;
            }


        }
        System.out.println(count);
    }
}
