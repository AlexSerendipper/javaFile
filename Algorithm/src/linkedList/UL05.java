package linkedList;

/**
 * 删除链表的倒数第 N 个结点: https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 *
 *
 *
 @author Alex
 @create 2023-06-17-10:16
 */
public class UL05 {

        public static void main(String[] args) {
               ListNode head = new ListNode(1);
               head.next = new ListNode(2);
               head.next.next = new ListNode(3);
               head.next.next.next = new ListNode(4);
               head.next.next.next.next = new ListNode(5);

            int n = 2;

            ListNode listNode = new UL05().removeNthFromEnd(head, n);



        }


    // 定义快慢指针: fast和slow同时指向虚拟节点,N为整个链表的长度（包含null,其中dummyHead为0,null为5）
    //              fast先走n + 1步，然后fast和slow同时移动，直到fast指向null时，slow指向删除节点的上一个节点
    // 原因：fast先走n + 1步，此时距离null节点的距离为N-(n+1)
    //       N-(n+1)即为slow节点走的距离，即slow节点停止的位置
    //       而 N-n-1 恰好就是 被删除节点的前一个节点


    public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummyHead = new ListNode(0);
            dummyHead.next = head;  // 定义虚拟头节点
            ListNode fast = dummyHead;  // 定义快慢指针
            ListNode slow = dummyHead;  // 定义快慢指针

            // fast先走n + 1步
            for (int i = 0; i <= n; i++) {
                fast = fast.next;
            }

            // 同时移动直到fast为null
            while(fast!=null){
                fast = fast.next;
                slow = slow.next;
            }

            // 删除
            slow.next = slow.next.next;
            return dummyHead.next;

    }

}
