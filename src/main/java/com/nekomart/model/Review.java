package com.nekomart.model;

/**
 * Review class - Review Management Component
 * OOP Concepts demonstrated:
 * 1. Encapsulation: Private fields with public getters/setters
 * 2. Data Abstraction: Review properties are abstracted into a class
 */
public class Review {
    private int id;
    private int toyId;
    private String username;
    private int rating;
    private String comment;
    private String date;

    /**
     * Constructor for creating a new review
     * CRUD Operation: CREATE
     */
    public Review(int id, int toyId, String username, int rating, String comment, String date) {
        this.id = id;
        this.toyId = toyId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Getters for review properties
     * CRUD Operation: READ
     */
    public int getId() { return id; }
    public int getToyId() { return toyId; }
    public String getUsername() { return username; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getDate() { return date; }

    /**
     * Setters for review properties
     * CRUD Operation: UPDATE
     */
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
} 