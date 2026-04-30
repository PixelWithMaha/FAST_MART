package com.example.assignment3.data.model;

import java.util.List;

public class Order {
    public String orderId;
    public List<CartItem> items;
    public String status; // e.g., "Delivered", "Processing"
    public String date;

    public Order() {} // Required for Firebase

    // Helper method to get total price
    public double getTotalPrice() {
        double total = 0;
        if (items != null) {
            for (CartItem item : items) {
                total += Double.parseDouble(item.getProduct().price) * item.getQuantity();
            }
        }
        return total;
    }

    // Requirement 4: Helper to convert list of products to a single string
    public String getProductSummary() {
        StringBuilder sb = new StringBuilder();
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                CartItem item = items.get(i);
                sb.append(item.getProduct().name).append(" (x").append(item.getQuantity()).append(")");
                if (i < items.size() - 1) sb.append("\n");
            }
        }
        return sb.toString();
    }
}