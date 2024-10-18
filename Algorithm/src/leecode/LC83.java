package leecode;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 删除排序链表中的重复元素
 @author Alex
 @create 2024-04-09-13:07
 */
// 以下解题思路其实复杂化了，实际上由于链表已经排序过了因此重复的元素在链表中出现的位置是连续的，
// 因此我们只需要对链表进行一次遍历，就可以删除重复的元素。
// 具体地，cur指向链表的头节点，随后开始对链表进行遍历。
// 如果当前cur与cur.next对应的元素相同，那么我们就将cur.next从链表中移除

public class LC83 {
    public static void main(String[] args) {
        ListNode ln1 = new ListNode(1);
        ListNode ln2 = new ListNode(1);
        ListNode ln3 = new ListNode(2);
        ListNode ln4 = new ListNode(3);
        ListNode ln5 = new ListNode(3);
        ln1.next = ln2;
        ln2.next = ln3;
        ln3.next = ln4;
        ln4.next = ln5;

        ListNode head = new LC83().deleteDuplicates(ln1);

    }
    public ListNode deleteDuplicates(ListNode head) {
        HashSet<Integer> set = new HashSet<>();

        ListNode pre = null;  // 指针，指向当前指针的前一个
        ListNode idx = head;  // 指针，指向当前指针的前一个
        while(idx!=null){
            if(!set.contains(idx.val)){
                set.add(idx.val);
                idx = idx.next;
                if(pre==null){
                    pre = head;
                }else {
                    pre = pre.next;
                }
            }else {
                pre.next = idx.next;
                idx = idx.next;
//                pre = pre.next;
            }
        }
        return head;
    }
}
