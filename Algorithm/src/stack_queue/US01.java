package stack_queue;

import java.util.Stack;

/**
 * 用栈实现队列：https://leetcode.cn/problems/implement-queue-using-stacks/
 * 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
 *
 * 实现 MyQueue 类：
 * void push(int x) 将元素 x 推到队列的末尾
 * int pop() 从队列的开头移除并返回元素
 * int peek() 返回队列开头的元素
 * boolean empty() 如果队列为空，返回 true ；否则，返回 false
 *
 * 说明：
 * 你 只能 使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 *
 @author Alex
 @create 2023-07-01-9:55
 */

public class US01 {

    // 将stack分为 进栈和出栈
    // 在push数据的时候，只要数据放进 输入栈 就好，
    // 但在 pop/peek 的时候，操作就复杂一些，输出栈如果为空，就把进栈数据全部导入进来（注意是全部导入），再从出栈弹出数据，如果输出栈不为空，则直接从出栈弹出数据就可以了。
    // 最后如何判断队列为空呢？如果进栈和出栈都为空的话，说明模拟的队列为空了。
    Stack<Integer> stackIn;
    Stack<Integer> stackOut;
    public US01() {
        stackIn = new Stack<>(); // 负责进栈
        stackOut = new Stack<>(); // 负责出栈
    }

    public void push(int x) {
        stackIn.push(x);
    }

    public int pop() {
        dumpstackIn();
        return stackOut.pop();
    }

    public int peek() {
        dumpstackIn();
        return stackOut.peek();
    }

    public boolean empty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }

    private void dumpstackIn(){
        // 如果出栈不为空，直接返回，  如果出栈为空，则将进栈所有数据导入
        if(!stackOut.isEmpty()){
            return;
        }
        while(!stackIn.isEmpty()){
            stackOut.push(stackIn.pop());
        }
    }
}
