package me.whiteship.inflearnthejavatest;


public class ListNode {
    private Integer value;
    private ListNode next;
    public ListNode(){}
    public ListNode(Integer value){
        this.value=value;
        this.next=null;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    static public ListNode get(ListNode head,int position){
        if(position <=0){
            System.out.println("Position shoule be positive");
            return null;
        }
        ListNode current = head;
        while (position-- > 0){
            current = current.getNext();
            if(current ==null & position > 0){
                System.out.println("out of range");
                return null;
            }
        }
        return current;
    }
    static public ListNode add(ListNode head, ListNode nodeToAdd, int position) {
        if (position <= 0) {
            System.out.println("Invalid position");
            return null;
        }
        ListNode before = null;
        ListNode current = head;
        while (position-- > 0){
            before = current;
            current = current.getNext();
            if(current ==null & position > 0){
                System.out.println("out of range");
                return null;
            }
        }
        before.setNext(nodeToAdd);
        nodeToAdd.setNext(current);
        return nodeToAdd;
    }
    static public ListNode remove(ListNode head, int positionToRemove) {
        if (positionToRemove <= 0) {
            System.out.println("Invalid position");
            return null;
        }
        ListNode before = null;
        ListNode current = head;
        while (positionToRemove-- > 0) {
            before = current;
            current = current.getNext();
            if (current == null && positionToRemove >= 0) {
                System.out.println("Out of range");
                return null;
            }
        }
        ListNode next = current.getNext();
        if (next == null) {
            before.setNext(null);
        } else {
            before.setNext(next);
        }
        return current;
    }


}
