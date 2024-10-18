package stack_queue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 滑动窗口最大值:https://leetcode.cn/problems/sliding-window-maximum/
 *
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回 滑动窗口中的最大值 。
 *
 * 示例 1：
 *
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 @author Alex
 @create 2023-07-05-9:53
 */

// 使用单调队列(维护队列中元素的单调性)来解决上述问题
//
public class US06 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length==1){
            return nums;
        }

        // 创建 用于存放结果元素 的数组
        int[] res = new int[nums.length - k + 1];

        MyQueue myQueue = new MyQueue();

        // 将前k个元素放入队列
        for (int i = 0; i < k; i++) {
            myQueue.add(nums[i]);
        }
        int n = 0;
        res[n++] = myQueue.getMaxValue();

        // 滑动
        for (int i = k; i < nums.length; i++) {
            // 这里是关键，移除的是最前面的元素
            myQueue.poll(nums[i-k]);
            // 添加最后边元素
            myQueue.add(nums[i]);
            res[n++] = myQueue.getMaxValue();
        }
        return res;
    }
}

class MyQueue{
    Deque<Integer> queue = new LinkedList<>();
    // pop出滑动窗口中不需要的元素
    // ✔规则是，如果Pop的元素==队列的出口处的元素，则队列弹出该元素。。。。 否则不用任何操作
    public void poll(int value){
        if(!queue.isEmpty() && value==queue.peek()){
            queue.poll();
        }
    };

    // push进滑动后 进入的新元素
    // ✔push的规则是，若 新加入的元素比之前的元素大，则弹出之前的所有元素
    public void add(int value){
        while(!queue.isEmpty() && value>queue.getLast()){
            queue.removeLast();
        }
        queue.add(value);
    }

    // 获得滑动窗口内的最大值
    public int getMaxValue(){
        return queue.peek();
    }
}
