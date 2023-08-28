package linkedList;

/**
 * 两两交换链表中的节点：https://leetcode.cn/problems/swap-nodes-in-pairs/
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 @author Alex
 @create 2023-06-16-11:14
 */
//public class UL04 {
//    public ListNode swapPairs(ListNode head) {
//        ListNode dumyhead = new ListNode(-1);  // 设置一个虚拟头结点
//        dumyhead.next = head;  // 将虚拟头结点指向head，这样方面后面做删除操作
//        ListNode cur = dumyhead;  // 指针初始指向虚拟头节点
//        ListNode temp;  // 临时节点，保存两个节点后面的节点
//        ListNode firstnode;  // 临时节点，保存两个节点之中的第一个节点
//        ListNode secondnode; // 临时节点，保存两个节点之中的第二个节
//
//        while(cur.next!=null && cur.next.next!=null){
//            temp = cur.next.next.next;
//            firstnode = cur.next;
//            secondnode = cur.next.next;
//
//            cur.next = secondnode;  // 步骤一
//            secondnode.next = firstnode;  // 步骤二
//            firstnode.next = temp;  // 步骤三
//
//            cur = firstnode;  // 这里一定要注意，firstnode后是第三个节点
//        }
//        return dumyhead.next;
//    }
//}
