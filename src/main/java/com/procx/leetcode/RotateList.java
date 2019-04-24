package com.procx.leetcode;

import com.procx.mode.ListNode;

public class RotateList {
    public ListNode rotateRight(ListNode head, int k){
        if(head==null) return null;
        if(head.next == null) return head;
        int n;
        ListNode node = head;
        for (n = 1; node.next!=null ; n++) {
            node = node.next;
        }
        //形成个闭环
        node.next = head;

        System.out.println(node == head);
        //新的尾
        int p = n - k%n;
        ListNode new_tail = head;
        for (int i = 1; i <p ; i++) {
            new_tail = new_tail.next;
        }
        ListNode new_head = new_tail.next;
        new_tail.next = null;
        return new_head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);

    }
}
