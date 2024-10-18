package interview_computer1;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 广东电信 3.23
 * 近日某运营商在本地多个商业广场中心广场同时举办促销活动，但目前仅有一辆可使用的货车，把装有礼品的箱子从公司送到各个活动现场，受空同和或重限制，货车每次运输的箱子总欧和总重最有限
 * 规给定箱子数组 boxes 和三个整数 portsCount, maxBoxes和maxWeight;其中boxes=[portsi, weighi].portsi表示第i个箱子需要送达的活动现场，welghtsi是第i个箱子的重量，portsCount 是活动现场的数目。
 * maxBoxes 和 maxWeight 分别是货车每趟运输箱子数目和重是的限制箱子需要按照数组顺序运输，同时每次运输需要遭循以下步要:
 * 货车从 boxes 队列中按顺序取出若干个子，但不能违反maxBoxes 和 maxWeight 限制,
 * 对于在货车上的箱子，需要 按顺序处理;货车会通过一趟行程,将最前面的袖子送到目的地活动现场并卸货，如果货车已经在对应的活动现场，则不需要须外行程，箱子也会立马被郅哉。
 * 货车上所有箱子都被卸货后，需要再花一趟行程返回到仓库，从子队列里再取出一些箱子。在将所有箱子运输并卸货后，最后必须返回到公司，
 * 请编程计算并返回将所有箱子送到相应活动场地的最少行程次数。
 *
 * 输入插述
 * 第一行输入一组整数，分别表示n，portsCount，maxBoxes，maxWeight.
 * 后n行每行输入两个整数，表示 boxes。
 *
 * 输出插述
 * 一个整数，表示将所有箱子送到相应活动场地的最少行程次数。
 *
 * 示例输入
3 2 3 3
1 1
2 1
1 1
 * 示例输出
4
 @author Alex
 @create 2024-03-23-10:18
 */
public class DX {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int portsCount = sc.nextInt();  // 活动现场
        int maxBoxes = sc.nextInt();  // 箱子数目
        int maxWeight = sc.nextInt();  // 数量限制

//        int[][] boxes = new int[n][2];
        LinkedList<int[]> boxes = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int[] temp = {sc.nextInt(),sc.nextInt()};
            boxes.push(temp);
//                int[i][j] = sc.nextInt();

        }

        // 开始送货
        int posi = 0;  // 记录当前位置
        int count = maxBoxes; // 记录当前最大数量
        int weight = maxWeight;  // 记录当前最大重量
        int c = 0;  // 代表行程数

        LinkedList<int[]> goods = new LinkedList<>();  // 货物

        while(!boxes.isEmpty()){
            // 装货
            for (int i = 0; i < n; i++) {
                if(boxes.isEmpty()){
                    break;
                }

                if(boxes.peek()[1]<= maxWeight && count>=0){
                    weight = weight-boxes.peek()[1];
                    count--;
                    goods.push(boxes.poll());
                }
            }

//            int size = goods.size();
            // 卸货
            while (!goods.isEmpty()) {
                int[] good = goods.poll();
                // 当前已经卸完
//                if(good == null){
//                    c++;
//                    break;
//                }
                // 同一地点直接卸货
                if(posi==good[0]){
                    continue;
                }
                posi = good[0];
                c++;
            }
            // 必须要回去，卸完+1
            c++;
        }



        System.out.println(c);
    }
}
