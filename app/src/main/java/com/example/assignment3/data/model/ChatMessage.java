package com.example.assignment3.data.model;

public class ChatMessage {
    public String senderId, receiverId, messageText;
    public long timestamp;

    public ChatMessage() {} // Required for Firebase

    public ChatMessage(String senderId, String receiverId, String messageText, long timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }
}