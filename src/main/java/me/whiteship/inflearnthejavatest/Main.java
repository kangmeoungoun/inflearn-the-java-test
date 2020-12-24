package me.whiteship.inflearnthejavatest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Main {
    public static void main(String[] args) {
        ListNode head = new ListNode();
        ListNode first = new ListNode(23);
        ListNode last = new ListNode(23);

        assertEquals(first, ListNode.add(head, first, 1));

    }
}
