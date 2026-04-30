package com.example.assignment3.data.model;

import java.io.Serializable;

public class Product implements Serializable {
    public String id, name, type, price, description, sellerId;
    public int imageRes;
    public boolean isDeal; // CRITICAL: Added for real-time sorting on Home Screen

    public Product() {} // Required for Firebase

    public Product(String id, String name, String type, String price, String description, String sellerId, int imageRes, boolean isDeal) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
        this.imageRes = imageRes;
        this.isDeal = isDeal;
    }
}