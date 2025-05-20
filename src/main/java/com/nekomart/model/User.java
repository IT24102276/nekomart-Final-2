package com.nekomart.model;

public class User {
    // Private fields for encapsulation
    private String username;
    private String password;
    private String role; // "user" or "admin"

    /**
     * Constructor for creating a new user
     * CRUD Operation: CREATE
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Getters for user properties
     * CRUD Operation: READ
     */
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    /**
     * Setters for user properties
     * CRUD Operation: UPDATE
     */
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
