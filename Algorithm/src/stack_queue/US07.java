package stack_queue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 前 K 个高频元素：https://leetcode.cn/problems/top-k-frequent-elements/
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案
 *
 *
 @author Alex
 @create 2023-07-06-9:59
 */
public class US07 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,1,1,1,2,2,3,3,3};
        new US07().topKFrequent(arr,2);

    }

    // 首先统计元素出现的频率，这一类的问题可以使用map来进行统计。
    // 然后是对频率进行排序，这里我们可以使用一种 容器适配器就是优先级队列。
    // (优先级队列。。其实就是一个披着队列外衣的堆，因为优先级队列对外接口只是从队头取元素，从队尾添加元素，再无其他取元素的方式，所以看起来就是一个队列)
    // 缺省情况下priority_queue利用max-heap（大顶堆）完成对元素的排序
    // 堆本质上是一棵完全二叉树，树中每个结点的值都 大于/小于 其左右孩子的值。
    // (大顶堆 则为所有结点都大于其左右孩子的值。。小顶堆则相反)
    // (为什么不用快排呢， 即对map的所有值进行快速排序，因为使用快排要将map转换为vector的结构，然后对整个数组进行排序。。。 而题干这种场景下，我们其实只需要维护k个有序的序列就可以了，所以使用优先级队列是最优的)
    // 最终，我们应当使用小顶堆，因为要统计最大前k个元素，只有小顶堆每次将最小的元素弹出，最后小顶堆里积累的才是前k个最大元素。

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();  //key为数组元素值,val为对应出现次数
        for(int num:nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }

        // 构建小顶堆的优先级队列，排序时按照int[]的第二个元素进行排序（小顶堆）
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                new Comparator<int[]>() {
                    @Override
                    public int compare(int[] o1, int[] o2) {
                        return o1[1] - o2[1];
                    }
                }
        );

        for (Map.Entry<Integer,Integer> entry:map.entrySet()) {
            // 小顶堆元素个数小于k个时直接加
            if(pq.size()<k){
                pq.add(new int[]{entry.getKey(),entry.getValue()});
            }else {
                if(entry.getValue() > pq.peek()[1]){
                    pq.poll();
                    pq.add(new int[]{entry.getKey(),entry.getValue()});
                }
            }
        }

        int[] arr = new int[k];
        // 依次弹出小顶堆
        int size = pq.size();
        for (int i = 0; i < size; i++) {
            arr[i] = pq.poll()[0];
        }
        return arr;
    }



}
