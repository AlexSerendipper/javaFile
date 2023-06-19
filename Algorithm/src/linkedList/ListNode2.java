package linkedList;

/**
 * 链表结构定义：双向链表
 @author Alex
 @create 2023-06-09-13:23
 */
public class ListNode2 {
    // 结点的值
    int val;

    // 下一个结点
    ListNode2 next,prev;

    // 节点的构造函数(无参)
    public ListNode2() {
    }

    // 节点的构造函数(有一个参数)
    public ListNode2(int val) {
        this.val = val;
    }
}
