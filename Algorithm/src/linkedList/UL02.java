package linkedList;

/**
 * 移除链表元素: https://leetcode.cn/problems/remove-linked-list-elements/
 * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
 * 注意：移除头节点时（1）可以设置一个虚拟头结点（2）针对头结点做特殊的处理，把第二个元素当做头结点
 @author Alex
 @create 2023-06-09-13:23
 */
// 单链表
public class UL02 {
    // 方式一：定义虚拟头节点
    public ListNode removeElements(ListNode head, int val) {
        if(head == null){
            return null;
        }
        // 定义虚拟节点
        ListNode dummyNode = new ListNode(-1,head);
        ListNode curNode = head;
        ListNode preNode = dummyNode;  // 用于指向前一个节点
        while(curNode!=null){
            if(curNode.val==val){
                preNode.next = curNode.next;
            }else {
                preNode = curNode;
            }
            curNode = curNode.next;
        }
        // 这里要注意，return的不要包含虚拟节点
        return dummyNode.next;
    }



    // 方式二：不添加虚拟头节点
    public ListNode removeElements2(ListNode head, int val) {
        // 排除移除头节点的情况！这里用while，是排除连续出现几个头结点的值都为val
        while(head!=null && head.val==val){
            head = head.next;
        }

        if(head == null){
            return null;
        }

        //
        ListNode curNode = head.next;
        ListNode preNode = head;  // 用于指向前一个节点
        while(curNode!=null){
            if(curNode.val==val){
                preNode.next = curNode.next;
            }else {
                preNode = curNode;
            }
            curNode = curNode.next;
        }
        // 这里要注意，return的不要包含虚拟节点
        return head;
    }
}
