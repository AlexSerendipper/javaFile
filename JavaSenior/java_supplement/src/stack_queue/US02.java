package stack_queue;

/**
 * 【队列】常用方法(常用deque实现队列)
 *  Deque<Integer> queue = new LinkedList<>();            # 创建一个队列的底层实现
 *  ✔peek()                     # 返回查看队首元素的值，不会删除队首元素。。。在队列元素为空的情况下，该方法会返回null。
 *  element（）                # 返回查看队首元素的值，不会删除队首元素。。。在队列元素为空的情况下，该方法会抛出异常
 *  ✔poll()                     # poll()返回队首元素的同时删除队首元素，队列为空时返回NULL
 *  remove（）                 # remove()返回队首元素的同时删除队首元素，队列为空时抛出NPE空指针异常
 *  ✔offer()                    # 向队列的队尾添加一个元素。。。在容量已满的情况下，该方法只会返回 false 。
 *  add()                      # 向队列的队尾添加一个元素。。。在容量已满的情况下，该方法会抛出异常
 *
 @author Alex
 @create 2023-07-13-9:19
 */
public class US02 {
}
