package linkedList;

/**
 * 环形链表 II：https://leetcode.cn/problems/linked-list-cycle-ii/
 *
 * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。
 * 注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 * 不允许修改 链表。
 @author Alex
 @create 2023-06-19-11:16
 */
public class UL07 {
    public ListNode detectCycle(ListNode head) {
        // 寻找相遇节点
        ListNode fast = head;
        ListNode slow = head;
        do{
            if(fast == null || fast.next==null){
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }while (fast!=slow);


        ListNode encounterNode = fast;  // 找到相遇节点（这部分的数学推导还是看代码随想录把，有点难的）
        // 寻找环入口
        slow = head;
        while(encounterNode != slow){
            encounterNode = encounterNode.next;
            slow = slow.next;
        }

        return slow;
    }
}
