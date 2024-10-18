package interview_computer1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 小米：3/12
 * 题目：小明正在参加一次选举。这次选举一共有n个人参与投票，每个人都最多投1票目必须投1票。
 * 每一个人都隶属于且仅隶属于一个阵营，第i个人隶属的阵营编号为a:。
 * 小明隶属的阵营编号为x，所有隶属于该阵营的人(允许没有人属于该阵营的情况出现)都一定会投票给小明，
 * 其他阵营的人都不会主动投票给小明。小明自己不参与投票，这 n个人当中也不包含小明。在最终投票前，
 * 小明决定再游说 1个阵营投票给自己。如果小明游说成功则隶属于被游说阵营的所有人都会投票给小明。
 * 小明会选择1个阵营进行游说，使得最后获得的总票数尽可能多。
 * 如果在小明游说前这n个人已经全部隶属于小明所在的阵营，则小明不会展开游说。
 * 假设小明游说一定成功，则小明在最终投票中最多可以获得多少票?
 *
 * 输入：
 * 输入第一行包含两个整数n和x，分别表示参与投票的总人数和小明隶属的阵营编号。
 * 输入第二行包含n个整数，其中第1个整数ai表示了第i个人录属的阵营编号
 *
 * 输出描述
 * 输出一行，一个整数，表示小明在最终投票中最多可以获得的票数
 *
 * 样例输入
 * 6 2
 * 1 2 2 2 3 3
 *
 * 样例输出
 * 5
 *
 * 提示
 * 小明隶属于编号为2的阵营，获得3票;小明选择游说编号为3的阵营，再获得2票5票。
 *
 @author Alex
 @create 2024-03-12-18:42
 */
public class XM01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int xm = sc.nextInt();

        HashSet<Integer> set = new HashSet<>();  // 存储游说过的阵营
        set.add(xm);

        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = sc.nextInt();
        }

        int maxCount = 0;  // 存储最多的投票数
//        ArrayList<Integer> arrs = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            int count=0;
//            int[] arrTemp = arr;
            int[] arrTemp = Arrays.copyOf(arr, arr.length);
            int old = arrTemp[i];
            if(set.contains(arrTemp[i])){
                continue;
            }else {  // 否则进行替换并计算个数
                // 替换
                for (int j = 0; j < num; j++) {
                    if(old == arrTemp[j]){
                        arrTemp[j]=xm;
                    }
                }

                // 计算数量
                for (int k = 0; k < num; k++) {
                    if(arrTemp[k]==xm){
                        count++;
                    }
                }

                // 是否最大
                maxCount = count>maxCount?count:maxCount;

                // 添加到set中
                set.add(old);
            }
        }
        System.out.println(maxCount);
    }
}
