package com.nekomart.model;

public class Review {
    private int id;
    private int toyId;
    private String username;
    private int rating;
    private String comment;
    private String date;

    public Review(int id, int toyId, String username, int rating, String comment, String date) {
        this.id = id;
        this.toyId = toyId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getId() { return id; }
    public int getToyId() { return toyId; }
    public String getUsername() { return username; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getDate() { return date; }

    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
} 