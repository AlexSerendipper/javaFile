package linkedList;

/**
 * 链表相交:https://leetcode.cn/problems/intersection-of-two-linked-lists-lcci/
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
 *
 * 注意：！！！求交点不是数值相等！！而是指针相等！！所以不用写calculateEqual()，直接比较两个节点是否==即可。
 @author Alex
 @create 2023-06-18-10:11
 */
public class UL07 {
    public static void main(String[] args) {
        ListNode head1 = new ListNode(5);
        head1.next = new ListNode(0);
        head1.next.next = new ListNode(1);
        head1.next.next.next = new ListNode(8);
        head1.next.next.next.next = new ListNode(5);

        ListNode head2 = new ListNode(4);
        head2.next = new ListNode(1);
        head2.next.next = new ListNode(8);
        head2.next.next.next = new ListNode(5);

        ListNode head3 = new ListNode(4);
        head3.next = new ListNode(1);
        head3.next.next = new ListNode(6);
        head3.next.next.next = new ListNode(5);

        boolean b = calculateEqual(head2, head3, 4);
        System.out.println(b);

        ListNode intersectionNode = new UL07().getIntersectionNode(head1, head2);
    }



    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 计算链表的长度
        int lengthA = calculateLength(headA);
        int lengthB = calculateLength(headB);
        ListNode idx1;
        ListNode idx2;

        // 将指针移动到链表末尾对齐
        if(lengthA>lengthB){
            idx1 = headA;  // 指针1
            for (int i = 0; i < lengthA - lengthB; i++) {
                idx1 = idx1.next;
            }
            idx2 = headB;  // 指针2
        }else {
            idx2 = headB;  // 指针2
            for (int i = 0; i < lengthB - lengthA; i++) {
                idx2 = idx2.next;
            }
            idx1 = headA;  // 指针1
        }

        // 计算两个子节点是否相同
        int length = lengthA>lengthB?lengthB:lengthA;
        while(length>0){
            if(idx1==idx2){
                return idx1;
            }else{
                idx1 = idx1.next;
                idx2 = idx2.next;
                length--;
            }
        }
        return null;
    }



    public static int calculateLength(ListNode head){
        int length = 0;
        while(head!=null){
            length++;
            head = head.next;
        }
        return length;
    }



    // 计算两个相同长度（length）的节点的值是否相同
    public static boolean calculateEqual(ListNode node1,ListNode node2,int length){
        if(node1.val != node2.val){
            return false;
        }

        for (int i = 0; i < length-1; i++) {
            if(node1.next.val!=node2.next.val){
                return false;
            }
            node1 = node1.next;
            node2 = node2.next;
        }

        return true;
    }

}
