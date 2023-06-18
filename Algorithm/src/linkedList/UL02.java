package linkedList;

/**
 * 设计链表:https://leetcode.cn/problems/design-linked-list/
 *
 * 你可以选择使用单链表或者双链表，设计并实现自己的链表。
 * 单链表中的节点应该具备两个属性：val 和 next 。val 是当前节点的值，next 是指向下一个节点的指针/引用。
 * 如果是双向链表，则还需要属性 prev 以指示链表中的上一个节点。假设链表中的所有节点下标从 0 开始。
 *
 * 实现 MyLinkedList 类：
 * MyLinkedList() 初始化 MyLinkedList 对象。
 * int get(int index) 获取链表中下标为 index 的节点的值。如果下标无效，则返回 -1 。
 * void addAtHead(int val) 将一个值为 val 的节点插入到链表中第一个元素之前。在插入完成后，新节点会成为链表的第一个节点。
 * void addAtTail(int val) 将一个值为 val 的节点追加到链表中作为链表的最后一个元素。
 * void addAtIndex(int index, int val) 将一个值为 val 的节点插入到链表中下标为 index 的节点之前。如果 index 等于链表的长度，那么该节点会被追加到链表的末尾。如果 index 比长度更大，该节点将 不会插入 到链表中。
 * void deleteAtIndex(int index) 如果下标有效，则删除链表中下标为 index 的节点。
 *
 @author Alex
 @create 2023-06-10-9:41
 */
public class UL02 {

}

// 单向链表
// 建议使用虚拟头节点！！
class MyLinkedList{
    // 定义链表的大小
    int size;
    ListNode head;

    public MyLinkedList(){
        size = 0;
        // 定义头结点 值为-1
        head = new ListNode(0);
    }

    // 获取链表中下标为 index 的节点的值。如果下标无效，则返回 -1 。
    public int get(int index) {
        if(index<0 || index>=size){
            return -1;
        }

        ListNode curNode = head;
        for (int i = 0; i <= index; i++) {
            curNode = curNode.next;
        }
        return curNode.val;
    }

    // void addAtHead(int val) 将一个值为 val 的节点插入到链表中第一个元素之前。在插入完成后，新节点会成为链表的第一个节点。
    public void addAtHead(int val) {
        addAtIndex(0,val);
    }

    // void addAtTail(int val) 将一个值为 val 的节点追加到链表中作为链表的最后一个元素。
    public void addAtTail(int val) {
        addAtIndex(size,val);

    }
    // 将一个值为 val 的节点插入到链表中下标为 index 的节点之前。
    // 如果 index 等于链表的长度，那么该节点会被追加到链表的末尾。如果 index 比长度更大，该节点将 不会插入 到链表中。
    public void addAtIndex(int index, int val) {
       if(index < 0 || index > size){
           return ;
       }
       size++;

       ListNode preNode = head;
       // 返回要插入元素的上一个节点
       for (int i = 0; i < index; i++) {
            preNode = preNode.next;
       }

       ListNode newNode = new ListNode(val);
       newNode.next = preNode.next;
       preNode.next = newNode;
    }

    // 如果下标有效，则删除链表中下标为 index 的节点。
    public void deleteAtIndex(int index) {
        if(index < 0 || index>=size){
            return;
        }
        size--;

        ListNode preNode = head;
        // 得到要被删除的节点的前一个节点
        for (int i = 0; i < index; i++) {
            preNode = preNode.next;
        }

        preNode.next = preNode.next.next;
    }
}


// 双向链表
// 建议使用虚拟头节点！！
class MyLinkedList2{
    // 定义链表的大小
    int size;
    // 记录链表的头结点和尾接点
    ListNode2 head,tail;

    public MyLinkedList2(){
        size = 0;
        // 定义头结点 值为0
        head = new ListNode2(0);
        tail = new ListNode2(0);
        // 注意：这一步非常关键，否则在加入头结点的操作中会出现null.next的错误！！！
        head.next=tail;
        tail.prev=head;
    }

    // 获取链表中下标为 index 的节点的值。如果下标无效，则返回 -1 。
    public int get(int index) {
        if(index<0 || index>=size){
            return -1;
        }

        ListNode2 curNode = head;
        // 得到当前Index的节点，查看从头开始遍历和从尾开始遍历哪个比较快
        if(index < size/2){
            for (int i = 0; i <= index; i++) {
                curNode = curNode.next;
            }
        }else {
            curNode = tail;
            for (int i = 0; i < size-index; i++) {
                curNode = curNode.prev;
            }
        }
        return curNode.val;
    }

    // void addAtHead(int val) 将一个值为 val 的节点插入到链表中第一个元素之前。在插入完成后，新节点会成为链表的第一个节点。
    public void addAtHead(int val) {
        addAtIndex(0,val);
    }

    // void addAtTail(int val) 将一个值为 val 的节点追加到链表中作为链表的最后一个元素。
    public void addAtTail(int val) {
        addAtIndex(size,val);

    }

    // 将一个值为 val 的节点插入到链表中下标为 index 的节点之前。
    // 如果 index 等于链表的长度，那么该节点会被追加到链表的末尾。如果 index 比长度更大，该节点将 不会插入 到链表中。
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > size){
            return ;
        }
        size++;

        ListNode2 preNode = head;
        // 返回要插入元素的上一个节点
        for (int i = 0; i < index; i++) {
            preNode = preNode.next;
        }

        ListNode2 newNode = new ListNode2(val);

        newNode.prev = preNode;
        newNode.next = preNode.next;
        preNode.next.prev = newNode;
        preNode.next = newNode;
    }

    // 如果下标有效，则删除链表中下标为 index 的节点。
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        size--;

        ListNode2 preNode = head;
        // 得到要被删除的节点的前一个节点
        for (int i = 0; i < index; i++) {
            preNode = preNode.next;
        }
        preNode.next.next.prev = preNode;
        preNode.next = preNode.next.next;
    }
}
