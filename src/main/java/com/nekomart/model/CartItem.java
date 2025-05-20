package com.nekomart.model;

/**
 * CartItem class - Cart Management Component
 * OOP Concepts:
 * 1. Encapsulation: Private fields with public getters/setters
 * 2. Data Abstraction: Review properties are abstracted into a class
 */

public class CartItem {
    private Toy toy;
    private int quantity;

    /**
     * Constructor for creating a new cart item
     * CRUD Operation: CREATE
     * Composition: CartItem contains a Toy object
     */
    public CartItem(Toy toy, int quantity) {
        this.toy = toy;
        this.quantity = quantity;
    }

    /**
     * Getters for cart item properties
     * CRUD Operation: READ
     */
    public Toy getToy() { return toy; }
    public int getQuantity() { return quantity; }

    /**
     * Setter for updating quantity
     * CRUD Operation: UPDATE
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
