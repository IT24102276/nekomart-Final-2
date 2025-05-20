package com.nekomart.model;

/**
 * ToyLinkedList class - DSA part
 * OOP Concepts:
 * 1. Encapsulation: Private fields with controlled access
 * 2. Data Abstraction: Hiding implementation details of linked list
 * 3. Inner Class: Node class is encapsulated within ToyLinkedList
 */
public class ToyLinkedList {
    private Node head;
    private int size;

    public ToyLinkedList() {
        head = null;
        size = 0;
    }
    /**
     * CREATE operation
     * Adds a new toy to the end of the linked list
     */
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

    /**
     * DELETE operation
     * Removes a toy with the specified ID from the list
     */
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

    /**
     * READ operation
     * Retrieves a specific toy by ID
     */
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

    /**
     * READ operation
     * Retrieves all toys in the list
     */
    public Toy[] getAll() {
        Toy[] toys = new Toy[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            toys[index++] = current.data;
            current = current.next;
        }
        return toys;
    }

    /**
     * UPDATE operation
     * Updates information of an existing toy
     */
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

    /**
     * Utility method for sorting toys by age group
     */
    public void sortByAgeGroup() {
        if (head == null || head.next == null) return;

        Node current = head;
        while (current != null) {
            Node min = current;
            Node temp = current.next;

            while (temp != null) {
                if (temp.data.getAgeGroup() < min.data.getAgeGroup()) {
                    min = temp;
                }
                temp = temp.next;
            }

            if (min != current) {
                Toy tempData = current.data;
                current.data = min.data;
                min.data = tempData;
            }

            current = current.next;
        }
    }

    // Utility methods
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Inner Node class
     * OOP Concepts:
     * 1. Encapsulation: Private fields for data and next node
     * 2. Composition: Node is composed of Toy data
     */
    public static class Node {
        Toy data;    // Composition: Node contains Toy object
        Node next;   // Self-referential: Node points to another Node

        Node(Toy data) {
            this.data = data;
            this.next = null;
        }
    }
}
