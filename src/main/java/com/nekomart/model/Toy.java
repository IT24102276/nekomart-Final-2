package com.nekomart.model;

/**
 * Toy - Toy Information

 * OOP Concepts:
 * 1. Encapsulation: We keep all toy information together, like having all details on one toy card
 * 2. Data Abstraction: We only show what's needed, like a toy card only showing important details
 * 3. Information Hiding: We keep some information private, like how a toy card keeps its details organized
 */
public class Toy {
    // Private fields for encapsulation
    private int id;
    private String name;
    private String description;
    private double price;
    private int ageGroup;
    private String image; // stores toy images

    /**
     * Constructor for creating a new toy
     * CRUD Operation: CREATE
     */
    public Toy(int id, String name, String description, double price, int ageGroup, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ageGroup = ageGroup;
        this.image = image;
    }

    /**
     * Getters for toy properties
     * CRUD Operation: READ
     */
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getAgeGroup() { return ageGroup; }
    public String getImage() { return image; }

    /**
     * Setters for toy properties
     * CRUD Operation: UPDATE
     */
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setAgeGroup(int ageGroup) { this.ageGroup = ageGroup; }
    public void setImage(String image) { this.image = image; }
}
