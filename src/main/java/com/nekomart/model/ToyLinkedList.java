package com.nekomart.model;

public class ToyLinkedList {
    private Node head;
    private int size;

    public ToyLinkedList() {
        head = null;
        size = 0;
    }

    public Node getHead() {
        return head;
    }

    public void add(Toy toy) {
        Node newNode = new Node(toy);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void remove(int id) {
        if (head == null) return;
        
        if (head.data.getId() == id) {
            head = head.next;
            size--;
            return;
        }
        
        Node current = head;
        while (current.next != null) {
            if (current.next.data.getId() == id) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    public Toy get(int id) {
        Node current = head;
        while (current != null) {
            if (current.data.getId() == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public Toy[] getAll() {
        Toy[] toyArray = new Toy[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            toyArray[index++] = current.data;
            current = current.next;
        }
        return toyArray;
    }

    public void update(Toy toy) {
        Node current = head;
        while (current != null) {
            if (current.data.getId() == toy.getId()) {
                current.data = toy;
                return;
            }
            current = current.next;
        }
    }

    public void sortByAgeGroup() {
        SelectionSort.sortByAgeGroup(this);
    }

    public int size() {
        return size;
    }
}
