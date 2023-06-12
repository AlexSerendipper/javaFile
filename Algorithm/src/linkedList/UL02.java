package linkedList;

/**
 * 反转链表:https://leetcode.cn/problems/reverse-linked-list/
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 *
 @author Alex
 @create 2023-06-12-15:12
 */
public class UL02 {
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while(cur!=null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
