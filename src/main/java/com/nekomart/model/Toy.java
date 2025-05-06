package com.nekomart.model;

public class Toy {
    private int id;
    private String name;
    private String description;
    private double price;
    private int ageGroup; // e.g., 3, 7, 12
    private String image; // Store image filename

    public Toy(int id, String name, String description, double price, int ageGroup, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ageGroup = ageGroup;
        this.image = image;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getAgeGroup() { return ageGroup; }
    public String getImage() { return image; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setAgeGroup(int ageGroup) { this.ageGroup = ageGroup; }
    public void setImage(String image) { this.image = image; }
}
