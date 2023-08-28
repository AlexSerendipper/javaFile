package stack_queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用队列实现栈：https://leetcode.cn/problems/implement-stack-using-queues/
 * 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。
 *
 * 实现 MyStack 类：
 * void push(int x) 将元素 x 压入栈顶。
 * int pop() 移除并返回栈顶元素。
 * int top() 返回栈顶元素。
 * boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
 *
 *
 * Queue中的常用方法(常用deque实现队列)
 * element（）               # 返回查看队首元素的值，不会删除队首元素。。。在队列元素为空的情况下，该方法会抛出异常
 * peek()                    # 返回查看队首元素的值，不会删除队首元素。。。在队列元素为空的情况下，该方法会返回null。
 * poll()                    # poll()返回队首元素的同时删除队首元素，队列为空时返回NULL
 * remove（）                # remove()返回队首元素的同时删除队首元素，队列为空时抛出NPE空指针异常
 *
 *
 @author Alex
 @create 2023-07-02-10:44
 */
public class US02 {
    Queue<Integer> queue;
    public US02() {
         queue = new LinkedList<>();
    }

    // 添加元素
    public void push(int x) {
        queue.add(x);
    }

    // 弹出元素
    public int pop() {
        // 将除最后一个元素外的所以元素添加到末尾，然后弹出第一个元素
        int size = queue.size();
        for (int i = 0; i < size - 1; i++) {
            queue.add(queue.poll());
        }
        return queue.poll();
    }

    public int top() {
        // 将除最后一个元素外的所以元素添加到末尾，然后弹出第一个元素
        int size = queue.size();
        for (int i = 0; i < size - 1; i++) {
            queue.add(queue.poll());
        }
        int result = queue.poll();
        queue.add(result);
        return result;
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
