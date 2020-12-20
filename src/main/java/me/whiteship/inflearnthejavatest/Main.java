package me.whiteship.inflearnthejavatest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by jojoldu@gmail.com on 2020-11-22
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class Main {
    public static void main(String[] args) {
        ListNode head = new ListNode();
        ListNode first = new ListNode(23);
        ListNode last = new ListNode(23);

        assertEquals(first, ListNode.add(head, first, 1));

    }
}
