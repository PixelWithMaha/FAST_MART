package com.example.assignment3.data.model;

public class User {
    public String userId, fullName, email, accountType, phoneNumber, gender, country, address, dob;

    public User() {}

    public User(String userId, String fullName, String email, String accountType,
                String phoneNumber, String gender, String country, String address, String dob) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.accountType = accountType;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.country = country;
        this.address = address;
        this.dob = dob;
    }
}